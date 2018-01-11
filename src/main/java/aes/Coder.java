package aes;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 基础加密模块
 *
 * @author tom
 * @since 1.0
 */
public class Coder {
    /**
     * 防止自己被实例化
     */
    Coder() {
    }

    private static final String KEY_SHA = "SHA";
    private static final String KEY_MD5 = "MD5";
    /**
     * 可以选择以下几种加密算法
     * HmacMD5
     * HmacSHA1
     * HmacSHA256
     * HmacSHA384
     * HmacSHA512
     */
    private static final String KEY_MAC = "HmacMD5";

    /**
     * BASE64解密
     *
     * @param key 加密内容
     * @return 结果集
     */
    public static byte[] decryptBASE64(String key) {
        return Base64.decodeBase64(key);
    }

    /**
     * BASE64加密
     *
     * @param key 原始数据集
     * @return 结果字符串
     */
    public static String encryptBASE64(byte[] key) {
        return Base64.encodeBase64String(key);
    }

    /**
     * MD5加密
     *
     * @param data 十六进制字符串
     * @return 结果字符串
     * @throws NoSuchAlgorithmException 选择加密算法异常
     * @throws NullPointerException     data空指针
     */
    public static String encryptMD5(String data) throws NoSuchAlgorithmException {
        MessageDigest md5 = MessageDigest.getInstance(KEY_MD5);
        byte[] bytes = data.getBytes();
        md5.update(bytes);
        return parseByte2HexStr(md5.digest());
    }

    /**
     * SHA加密
     *
     * @param data 原始数据
     * @return 结果集
     * @throws NoSuchAlgorithmException 选择加密算法异常
     */
    public static byte[] encryptSHA(byte[] data) throws NoSuchAlgorithmException {
        MessageDigest sha = MessageDigest.getInstance(KEY_SHA);
        sha.update(data);
        return sha.digest();
    }

    /**
     * 初始化HMAC密钥
     *
     * @return 结果字符串
     * @throws NoSuchAlgorithmException 选择加密算法异常
     */
    public static String initMacKey() throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(KEY_MAC);
        SecretKey secretKey = keyGenerator.generateKey();
        return encryptBASE64(secretKey.getEncoded());
    }

    /**
     * HMAC加密
     *
     * @param data 原始数据
     * @param key  密钥
     * @return 结果集
     * @throws NoSuchAlgorithmException 选择加密算法异常
     * @throws InvalidKeyException      初始化密钥异常
     */
    public static byte[] encryptHMAC(byte[] data, String key) throws NoSuchAlgorithmException, InvalidKeyException {
        SecretKey secretKey = new SecretKeySpec(decryptBASE64(key), KEY_MAC);
        Mac mac = Mac.getInstance(secretKey.getAlgorithm());
        mac.init(secretKey);
        return mac.doFinal(data);
    }

    /**
     * 二进制转为十六进制
     *
     * @param buf 二进制内容
     * @return 十六进制字符串
     */
    static String parseByte2HexStr(byte[] buf) {
        StringBuilder sb = new StringBuilder();
        for (byte b : buf) {
            String hex = Integer.toHexString(b & 0xFF);
            sb.append((hex.length() == 1 ? '0' + hex : hex).toUpperCase());
        }
        return sb.toString();
    }

    /**
     * 十六进制转为二进制
     *
     * @param hexStr 十六进制字符串
     * @return 二进制结果集
     */
    static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1) {
            return null;
        }
        byte[] result = new byte[hexStr.length() / 2];
        Integer halfLength = hexStr.length() / 2;
        for (int i = 0; i < halfLength; i++) {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }
}
