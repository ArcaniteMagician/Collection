package com.shanghaigm.cmat.http;

import android.util.Base64;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * 加密工具类
 */
public class SecurityUtils {

	private static final String TAG = "SecurityUtils";

	private static final String TRANSFORMATION = "AES/CBC/PKCS5Padding";
	private static final String SECRET_KEY_HASH_TRANSFORMATION = "SHA-256";
	private static final String CHARSET = "UTF-8";
	private static final String IV_STR = "shegridchumicmat"; //16bytes

	private Cipher writer;
	private Cipher reader;
	private static SecurityUtils instance;

	private SecurityUtils() {/*Do not new me*/};

	public static SecurityUtils getInstance(String secureKey){
		instance = new SecurityUtils();
		instance.initCiphers(secureKey);
		return instance;
	}

	/**
	 * 初始化
	 */
	public void initCiphers(String secureKey){

		try {
			this.writer = Cipher.getInstance(TRANSFORMATION);
			this.reader = Cipher.getInstance(TRANSFORMATION);

			IvParameterSpec ivSpec = getIvParamSpec();
			SecretKeySpec secretKey = getSecretKey(secureKey);

			writer.init(Cipher.ENCRYPT_MODE, secretKey, ivSpec); // 编码
			reader.init(Cipher.DECRYPT_MODE, secretKey, ivSpec); // 解码

		} catch (NoSuchAlgorithmException|NoSuchPaddingException
				|InvalidKeyException|UnsupportedEncodingException
				|InvalidAlgorithmParameterException e) {
			e.printStackTrace();
		}
	}

	private IvParameterSpec getIvParamSpec() {

		byte[] iv = new byte[writer.getBlockSize()];
		System.arraycopy(IV_STR.getBytes(), 0,
				iv, 0, writer.getBlockSize());
		return new IvParameterSpec(iv);
	}

	private SecretKeySpec getSecretKey(String key)
			throws UnsupportedEncodingException, NoSuchAlgorithmException {

		byte[] keyBytes = createKeyBytes(key);
		return new SecretKeySpec(keyBytes, TRANSFORMATION);
	}

	//SHA-256 散列码
	private byte[] createKeyBytes(String key)
			throws UnsupportedEncodingException, NoSuchAlgorithmException {

		MessageDigest messageDigest = MessageDigest.getInstance(SECRET_KEY_HASH_TRANSFORMATION);
		messageDigest.reset();
		return messageDigest.digest(key.getBytes(CHARSET));
	}

	/**
	 * 加密
	 * @param value value
	 * @return encodedValue
	 */
	public String encrypt(String value) {
		if(value == null){
			return null;
		}

		byte[] secureValue = null;
		try {
			secureValue = writer.doFinal(value.getBytes(CHARSET));
		} catch (IllegalBlockSizeException|BadPaddingException|UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return Base64.encodeToString(secureValue, Base64.NO_WRAP);
	}

	/**
	 * 解密
	 * @param encodedValue encodedValue
	 * @return realValue
	 */
	public String decrypt(String encodedValue) {

		if(encodedValue == null){
			return null;
		}

		byte[] securedValue = Base64.decode(encodedValue, Base64.NO_WRAP);
		String realValue = null;
		try {
			byte[] value = reader.doFinal(securedValue);
			realValue = new String(value, CHARSET);
		} catch (UnsupportedEncodingException|IllegalBlockSizeException|BadPaddingException e) {
			e.printStackTrace();
		}
		return realValue;
	}

}
