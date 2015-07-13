package com.michael.utils;

import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;

import javax.crypto.Cipher;

/**
#################################################################

	使用openssl产生RSA密钥对

#################################################################

1.生成私钥
openssl genrsa -out rsa_private_key.pem 1024 
2.对私钥进行PKCS#8编码
openssl pkcs8 -topk8 -in rsa_private_key.pem -out pkcs8_rsa_private_key.pem -nocrypt
3.生成公钥
openssl rsa -in rsa_private_key.pem -out rsa_public_key.pem -pubout 

 *
 */
public class RSAUtil {

	/**
	 * RSA/ECB/PKCS1Padding
	 * RSA/ECB/NoPadding
	 */
	private final static String ALGORITHM = "RSA/ECB/NoPadding";
	private final KeyPair keyPair;
	public RSAUtil() throws NoSuchAlgorithmException {
		// TODO Auto-generated constructor stub
		KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA", new org.bouncycastle.jce.provider.BouncyCastleProvider());
        keyPairGen.initialize(1024, new SecureRandom());
        keyPair = keyPairGen.generateKeyPair();
	}
	
	/**
	 * 使用模和指数生成RSA公钥
	 * 注意：【此代码用了默认补位方式，为RSA/None/PKCS1Padding，不同JDK默认的补位方式可能不同，
	 * 如Android默认是RSA/None/NoPadding】
	 * @param modulus
	 * @param exponent
	 * @return RSAPublicKey
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 */
	private RSAPublicKey generatePublicKey(BigInteger modulus, BigInteger exponent) throws NoSuchAlgorithmException, InvalidKeySpecException {
		KeyFactory keyFactory = KeyFactory.getInstance("RSA", new org.bouncycastle.jce.provider.BouncyCastleProvider());
		RSAPublicKeySpec keySpec = new RSAPublicKeySpec(modulus, exponent);
		return (RSAPublicKey) keyFactory.generatePublic(keySpec);
	}

	/**
	 * 使用模和指数生成RSA私钥
	 * 注意：【此代码用了默认补位方式，为RSA/None/PKCS1Padding，不同JDK默认的补位方式可能不同，如Android默认是RSA
	 * /None/NoPadding】
	 * @param modulus
	 * @param exponent
	 * @return RSAPrivateKey
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 */
	private RSAPrivateKey generatePrivateKey(BigInteger modulus, BigInteger exponent) throws NoSuchAlgorithmException, InvalidKeySpecException {
		KeyFactory keyFactory = KeyFactory.getInstance("RSA", new org.bouncycastle.jce.provider.BouncyCastleProvider());
		RSAPrivateKeySpec keySpec = new RSAPrivateKeySpec(modulus, exponent);
		return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
	}

	/**
	 * 加密
	 * @param data
	 * @return String
	 * @throws Exception
	 */
	public String encrypt(String data)
			throws Exception {
		
		RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
		RSAPublicKey encryptPublicKey = generatePublicKey(publicKey.getModulus(), publicKey.getPublicExponent());
		
		Cipher cipher = Cipher.getInstance(ALGORITHM, new org.bouncycastle.jce.provider.BouncyCastleProvider());
		cipher.init(Cipher.ENCRYPT_MODE, encryptPublicKey);
		// 模长
		int key_len = encryptPublicKey.getModulus().bitLength() / 8;
		// 加密数据长度 <= 模长-11
		String[] datas = splitString(data, key_len - 11);
		String mi = "";
		//如果明文长度大于模长-11则要分组加密
		for (String s : datas) {
			mi += bcd2Str(cipher.doFinal(s.getBytes()));
		}
		return mi;
	}

	/**
	 * 解密
	 * @param data
	 * @param privateKey
	 * @return String
	 * @throws Exception
	 */
	public String decrypt(String data)
			throws Exception {
		
		RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
//		RSAPrivateKey decryptPrivateKey = generatePrivateKey(privateKey.getModulus(), privateKey.getPrivateExponent());
		
		Cipher cipher = Cipher.getInstance(ALGORITHM, new org.bouncycastle.jce.provider.BouncyCastleProvider());
		cipher.init(Cipher.DECRYPT_MODE, privateKey);
		//模长
		int key_len = privateKey.getModulus().bitLength() / 8;
		byte[] bytes = data.getBytes();
		byte[] bcd = ASCII_To_BCD(bytes, bytes.length);
		System.err.println(bcd.length);
		//如果密文长度大于模长则要分组解密
		String ming = "";
		byte[][] arrays = splitArray(bcd, key_len);
		for(byte[] arr : arrays){
			ming += new String(cipher.doFinal(arr));
		}
		return ming;
	}
	
	/**
	 * ASCII码转BCD码
	 * 
	 */
	private byte[] ASCII_To_BCD(byte[] ascii, int asc_len) {
		byte[] bcd = new byte[asc_len / 2];
		int j = 0;
		for (int i = 0; i < (asc_len + 1) / 2; i++) {
			bcd[i] = asc_to_bcd(ascii[j++]);
			bcd[i] = (byte) (((j >= asc_len) ? 0x00 : asc_to_bcd(ascii[j++])) + (bcd[i] << 4));
		}
		return bcd;
	}
	
	private byte asc_to_bcd(byte asc) {
		byte bcd;

		if ((asc >= '0') && (asc <= '9'))
			bcd = (byte) (asc - '0');
		else if ((asc >= 'A') && (asc <= 'F'))
			bcd = (byte) (asc - 'A' + 10);
		else if ((asc >= 'a') && (asc <= 'f'))
			bcd = (byte) (asc - 'a' + 10);
		else
			bcd = (byte) (asc - 48);
		return bcd;
	}
	
	/**
	 * BCD转字符串
	 */
	private String bcd2Str(byte[] bytes) {
		char temp[] = new char[bytes.length * 2], val;

		for (int i = 0; i < bytes.length; i++) {
			val = (char) (((bytes[i] & 0xf0) >> 4) & 0x0f);
			temp[i * 2] = (char) (val > 9 ? val + 'A' - 10 : val + '0');

			val = (char) (bytes[i] & 0x0f);
			temp[i * 2 + 1] = (char) (val > 9 ? val + 'A' - 10 : val + '0');
		}
		return new String(temp);
	}
	
	/**
	 * 拆分字符串
	 */
	private String[] splitString(String string, int len) {
		int x = string.length() / len;
		int y = string.length() % len;
		int z = 0;
		if (y != 0) {
			z = 1;
		}
		String[] strings = new String[x + z];
		String str = "";
		for (int i=0; i<x+z; i++) {
			if (i==x+z-1 && y!=0) {
				str = string.substring(i*len, i*len+y);
			}else{
				str = string.substring(i*len, i*len+len);
			}
			strings[i] = str;
		}
		return strings;
	}
	
	/**
	 *拆分数组 
	 */
	private byte[][] splitArray(byte[] data,int len){
		int x = data.length / len;
		int y = data.length % len;
		int z = 0;
		if(y!=0){
			z = 1;
		}
		byte[][] arrays = new byte[x+z][];
		byte[] arr;
		for(int i=0; i<x+z; i++){
			arr = new byte[len];
			if(i==x+z-1 && y!=0){
				System.arraycopy(data, i*len, arr, 0, y);
			}else{
				System.arraycopy(data, i*len, arr, 0, len);
			}
			arrays[i] = arr;
		}
		return arrays;
	}
}
