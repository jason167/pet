import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import javax.imageio.ImageIO;

/**
 * 
 *
 *
 * @author 610273
 * @version $Id: ImageUtil.java 2015年10月29日 上午8:39:46 $
 */
public class ImageUtil {

	private static int CONNECT_TIMEOUT = 10 * 1000;
	private static int READ_TIMEOUT = 0;
	private static String SAVETO = "./";
	private static String URL = "http://localhost:8081/fsp-loan/id/q";
	private final static AtomicInteger COUNTER = new AtomicInteger();

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			initProperties();

			if (args == null || args.length <= 0) {
				System.out.println("Params can't be null");
				System.out.println("Example:java -Dsf.url=http://localhost:8081/fsp-loan/id/q ImageUtil emp.csv");
				return;
			}

			File file = new File(SAVETO);
			if (!file.exists()) {
				file.mkdirs();
			}else{
//				File[] listFiles = file.listFiles();
//				for (File subFile : listFiles) {
//					subFile.delete();
//				}
			}
			
			String fileName = args[0];
			List<IdCard> idCardList = toList(fileName);
			for (IdCard idCard : idCardList) {
				download(idCard.getEmpid(), idCard.getName(), idCard.getNo());
			}
			
			System.out.println("Done!");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	private static List<IdCard> toList(String fileName) throws Exception{
		
		BufferedReader bufferedReader = null;
		try {
			List<IdCard> idCardList = new ArrayList<IdCard>();
			bufferedReader = new BufferedReader(new FileReader(new File(fileName)));
			String line = null;
			int counter = 0;
			while((line = bufferedReader.readLine()) != null){
				line = line.trim();
				if (line != null && line.length() > 0) {
					String[] info = line.split(",");
					if (info != null && info.length == 3) {
						idCardList.add(new IdCard(info[0], info[1], info[2]));
						counter ++;
					}
				}
			}
			
			System.out.println("Total records count is " + counter);
			return idCardList;
		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
		finally{
			if (bufferedReader != null) {
				bufferedReader.close();
			}
		}
	}
	
	private static void download(String empid, String name, String no) throws Exception {

		System.out.print(COUNTER.incrementAndGet() + ",Downloading ["+ empid +"]["+ name +"]["+ no +"] ......");
		InputStream inputStream = null;
		try {

//			name = new String(name.getBytes("utf8"), "iso-8859-1");
//			name = URLEncoder.encode(name, "utf8");
			HttpURLConnection httpConn = getConnection(URL);
			
			StringBuffer append = new StringBuffer();
			String params = 
				  append.append("name").append("=").append(name)
						.append("&")
						.append("no").append("=").append(no)
						.toString();
			
			httpConn.setRequestMethod("POST");
			httpConn.setRequestProperty("accept", "*/*");  
			httpConn.setRequestProperty("connection", "Keep-Alive");  
			httpConn.setRequestProperty("Content-Length", String  
                    .valueOf(params.length())); 
			
			httpConn.connect();
			
			OutputStream out = null;
			try {
				out = httpConn.getOutputStream();
				out.write(params.getBytes("utf8"));
				out.flush();
			} catch (Exception e) {
				// TODO: handle exception
				throw e;
			}
			finally{
				if (out != null) {
					out.close();
				}
			}
			
			
			int responseCode = httpConn.getResponseCode();
			if (responseCode != 200) {
				throw new IllegalStateException(" response code " + responseCode);
			} else {
				inputStream = httpConn.getInputStream();

				byte[] imageBuffer = read(inputStream);

				String pathname = SAVETO + File.separator + empid + ".jpg";
				OutputStream outputStream = new FileOutputStream(new File(pathname));
				write(imageBuffer, outputStream);
				
				System.out.println(" successful.");
			}

		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(" failed.");
			throw e;

		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
				}
			}
		}

	}

	private static HttpURLConnection getConnection(String url) throws Exception {
		try {
			URLConnection urlConnection = new URL(url).openConnection();
			HttpURLConnection httpConn = (HttpURLConnection) urlConnection;
			httpConn.setDoOutput(true);
			httpConn.setConnectTimeout(CONNECT_TIMEOUT);
			httpConn.setReadTimeout(READ_TIMEOUT);
			return (HttpURLConnection) urlConnection;
		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
	}

	private static byte[] read(InputStream inputStream) throws Exception {
		try {
			if (inputStream != null) {
				byte[] imageTempBuffer = new byte[1024 * 4];
				byte[] imageBuffer = new byte[0];
				int read = 0;
				while ((read = inputStream.read(imageTempBuffer)) > 0) {
					byte[] t = new byte[imageBuffer.length + read];
					if (imageBuffer.length > 0) {
						System.arraycopy(imageBuffer, 0, t, 0, imageBuffer.length);
					}
					System.arraycopy(imageTempBuffer, 0, t, imageBuffer.length, read);
					imageBuffer = t;
				}
				System.out.print(" image size:" + imageBuffer.length / 1024 +"kb ......");
				return imageBuffer;
			} else {
				throw new IllegalStateException("httpConn inputStream is null");
			}
		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
	}

	private static void write(byte[] imageBuffer, OutputStream outputStream) throws Exception {
		try {
			ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(imageBuffer);
			BufferedImage bufferedImage = ImageIO.read(byteArrayInputStream);
			ImageIO.write(bufferedImage, "jpeg", outputStream);
		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
	}

	private static void initProperties() {
		// TODO Auto-generated method stub
		String _connTimeout = System.getProperty("sf.conn.timeout");
		if (_connTimeout != null) {
			CONNECT_TIMEOUT = Integer.parseInt(_connTimeout) * 1000;
		}

		String _readTimeout = System.getProperty("sf.read.timeout");
		if (_readTimeout != null) {
			READ_TIMEOUT = Integer.parseInt(_readTimeout) * 1000;
		}

		String _saveto = System.getProperty("sf.saveto");
		if (_saveto != null) {
			SAVETO = _saveto;
		}

		String _url = System.getProperty("sf.url");
		if (_url == null) {
			throw new IllegalArgumentException("sf.url param can't be null");
		}

		URL = _url;

	}
	
	static class IdCard{
		private final String empid;
		private final String name;
		private final String no;
		
		public IdCard(String empid, String name, String no) {
			// TODO Auto-generated constructor stub
			this.empid = empid;
			this.name = name;
			this.no = no;
		}
		
		public String getEmpid() {
			return empid;
		}
		
		public String getName() {
			return name;
		}
		
		public String getNo() {
			return no;
		}

		@Override
		public String toString() {
			return "IdCard [empid=" + empid + ", name=" + name + ", no=" + no + "]";
		}

		
	}


}
