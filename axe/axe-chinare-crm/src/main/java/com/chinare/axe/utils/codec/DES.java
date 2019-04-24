package com.chinare.axe.utils.codec;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.repo.Base64;

/**
 * 
 * @author kerbores
 *
 */
public class DES {

	private DES() {
	}

	private static final String DES_NAME = "DES";

	public static final String DEFAULT_KEY = "abcdefgh";

	private static Log log = Logs.get();

	public static String encrypt(String data) {
		return encrypt(data, DEFAULT_KEY);
	}

	public static String decrypt(String data) {
		return decrypt(data, DEFAULT_KEY);
	}

	/**
	 * Description 根据键值进行加密
	 * 
	 * @param data 数据
	 * @param key  加密键byte数组
	 * @return 密文
	 */
	public static String encrypt(String data, String key) {
		byte[] bt = null;
		try {
			bt = encrypt(data.getBytes(), key.getBytes());
		} catch (Exception e) {
			log.error(e);
		}
		return Base64.encodeToString(bt, false);
	}

	/**
	 * Description 根据键值进行解密
	 * 
	 * @param data 数据
	 * @param key  加密键byte数组
	 * @return 明文
	 */
	public static String decrypt(String data, String key) {
		if (data == null)
			return null;

		byte[] buf = Base64.decode(data);
		byte[] bt = null;
		try {
			bt = decrypt(buf, key.getBytes());
		} catch (Exception e) {
			log.error(e);
		}
		return new String(bt);
	}

	/**
	 * Description 根据键值进行加密
	 * 
	 * @param data 数据
	 * @param key  加密键byte数组
	 * @return 密文
	 * @throws InvalidKeyException
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws InvalidKeySpecException
	 * @throws BadPaddingException
	 * @throws IllegalBlockSizeException
	 */
	private static byte[] encrypt(byte[] data, byte[] key) throws InvalidKeyException, NoSuchAlgorithmException,
			NoSuchPaddingException, InvalidKeySpecException, IllegalBlockSizeException, BadPaddingException {
		SecureRandom sr = new SecureRandom();

		DESKeySpec dks = new DESKeySpec(key);

		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES_NAME);
		SecretKey securekey = keyFactory.generateSecret(dks);

		Cipher cipher = Cipher.getInstance(DES_NAME);

		cipher.init(Cipher.ENCRYPT_MODE, securekey, sr);

		return cipher.doFinal(data);
	}

	/**
	 * Description 根据键值进行解密
	 * 
	 * @param data 数据
	 * @param key  加密键byte数组
	 * @return 明文
	 * @throws InvalidKeyException
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 * @throws NoSuchPaddingException
	 * @throws BadPaddingException
	 * @throws IllegalBlockSizeException
	 */
	private static byte[] decrypt(byte[] data, byte[] key) throws InvalidKeyException, NoSuchAlgorithmException,
			InvalidKeySpecException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
		SecureRandom sr = new SecureRandom();

		DESKeySpec dks = new DESKeySpec(key);

		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES_NAME);
		SecretKey securekey = keyFactory.generateSecret(dks);

		Cipher cipher = Cipher.getInstance(DES_NAME);

		cipher.init(Cipher.DECRYPT_MODE, securekey, sr);

		return cipher.doFinal(data);
	}
}
