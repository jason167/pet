package com.michael.utils.algorithm;
import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;

import org.apache.commons.codec.binary.Base64;

public class Des3Util {
	// 向量
	private final static String iv = "01234567";
	// 加解密统一使用的编码方式
	private final static String encoding = "utf-8";

	public static final Base64 base64 = new Base64();

	/**
	 * 3DES加密
	 * @param plainText 普通文本
	 * @return
	 * @throws Exception
	 */
	public static String encrypt(String plainText, String key) throws Exception {
		Key deskey = null;
		DESedeKeySpec spec = new DESedeKeySpec(key.getBytes());
		SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
		deskey = keyfactory.generateSecret(spec);

		Cipher cipher = Cipher.getInstance("desede/CBC/PKCS5Padding");
		IvParameterSpec ips = new IvParameterSpec(iv.getBytes(encoding));
		cipher.init(Cipher.ENCRYPT_MODE, deskey, ips);
		byte[] encryptData = cipher.doFinal(plainText.getBytes(encoding));
		return new String(base64.encode(encryptData));
	}
	

	/**
	 * 3DES解密
	 * @param encryptText 加密文本
	 * @return
	 * @throws Exception
	 */
	public static String decrypt(String encryptText, String key)
			throws Exception {
		Key deskey = null;
		DESedeKeySpec spec = new DESedeKeySpec(key.getBytes(encoding));
		SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
		deskey = keyfactory.generateSecret(spec);
		Cipher cipher = Cipher.getInstance("desede/CBC/PKCS5Padding");
		IvParameterSpec ips = new IvParameterSpec(iv.getBytes(encoding));
		cipher.init(Cipher.DECRYPT_MODE, deskey, ips);
		byte[] decryptData = cipher.doFinal(base64.decode(encryptText));
		return new String(decryptData, encoding);
	}
	
	
/*	 public static void main(String[] args){
	    	//加密
	    	String decrptStr;
			try {
				decrptStr = encrypt("1356610101011111201445214521","1a0dcc06af4585e83a1c4967d148821c");
				System.out.println(decrptStr);
		    	//解密
		    	System.out.println(decrypt(decrptStr,"1a0dcc06af4585e83a1c4967d148821c"));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	
	    }*/

}