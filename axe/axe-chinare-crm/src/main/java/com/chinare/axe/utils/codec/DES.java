package com.chinare.axe.utils.codec;

import java.security.SecureRandom;

import javax.crypto.Cipher;
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

    private final static String DES = "DES";

    public static String DEFAULT_KEY = "abcdefgh";

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
     * @param data
     *            数据
     * @param key
     *            加密键byte数组
     * @return 密文
     */
    public static String encrypt(String data, String key) {
        byte[] bt = null;
        try {
            bt = encrypt(data.getBytes(), key.getBytes());
        }
        catch (Exception e) {
            log.error(e);
        }
        return Base64.encodeToString(bt, false);
    }

    /**
     * Description 根据键值进行解密
     * 
     * @param data
     *            数据
     * @param key
     *            加密键byte数组
     * @return 明文
     */
    public static String decrypt(String data, String key) {
        if (data == null)
            return null;

        byte[] buf = Base64.decode(data);
        byte[] bt = null;
        try {
            bt = decrypt(buf, key.getBytes());
        }
        catch (Exception e) {
            log.error(e);
        }
        return new String(bt);
    }

    /**
     * Description 根据键值进行加密
     * 
     * @param data
     *            数据
     * @param key
     *            加密键byte数组
     * @return 密文
     * @throws Exception
     *             加密算法时
     */
    private static byte[] encrypt(byte[] data, byte[] key) throws Exception {
        SecureRandom sr = new SecureRandom();

        DESKeySpec dks = new DESKeySpec(key);

        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
        SecretKey securekey = keyFactory.generateSecret(dks);

        Cipher cipher = Cipher.getInstance(DES);

        cipher.init(Cipher.ENCRYPT_MODE, securekey, sr);

        return cipher.doFinal(data);
    }

    /**
     * Description 根据键值进行解密
     * 
     * @param data
     *            数据
     * @param key
     *            加密键byte数组
     * @return 明文
     * @throws Exception
     *             加密算法时
     */
    private static byte[] decrypt(byte[] data, byte[] key) throws Exception {
        SecureRandom sr = new SecureRandom();

        DESKeySpec dks = new DESKeySpec(key);

        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
        SecretKey securekey = keyFactory.generateSecret(dks);

        Cipher cipher = Cipher.getInstance(DES);

        cipher.init(Cipher.DECRYPT_MODE, securekey, sr);

        return cipher.doFinal(data);
    }
}
