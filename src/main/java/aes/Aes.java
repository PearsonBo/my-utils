package aes;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * Aes加解密方法
 *
 * @author tom
 */
public class Aes extends Coder {
    /**
     * 防止自己被实例化
     */
    private Aes() {
    }

    /**
     * 加密
     *
     * @param content  需加密内容
     * @param password 秘钥
     * @return 加密结果
     * @throws NoSuchAlgorithmException     选择加密算法错误
     * @throws NoSuchPaddingException       没有相应的格式化方法
     * @throws UnsupportedEncodingException 不支持的加密方法
     * @throws InvalidKeyException          初始化密钥错误
     * @throws BadPaddingException          格式化出错
     * @throws IllegalBlockSizeException    非法块大小
     */
    public static String encrypt(String content, String password) throws NoSuchAlgorithmException,
            NoSuchPaddingException, UnsupportedEncodingException, InvalidKeyException, BadPaddingException,
            IllegalBlockSizeException {
        KeyGenerator kg = KeyGenerator.getInstance("AES");
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
        random.setSeed(password.getBytes());
        kg.init(128, random);
        SecretKey secretKey = kg.generateKey();
        byte[] enCodeFormat = secretKey.getEncoded();
        SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        byte[] byteContent = content.getBytes("utf-8");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] result = cipher.doFinal(byteContent);
        return parseByte2HexStr(result);
    }

    /**
     * 解密
     *
     * @param con      需解密内容
     * @param password 秘钥
     * @return 解密结果
     * @throws NoSuchAlgorithmException  选择加密算法错误
     * @throws NoSuchPaddingException    没有相应的格式化方法
     * @throws InvalidKeyException       初始化密钥错误
     * @throws BadPaddingException       格式化出错
     * @throws IllegalBlockSizeException 非法块大小
     */
    public static String decrypt(String con, String password) throws NoSuchAlgorithmException,
            NoSuchPaddingException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        byte[] content = parseHexStr2Byte(con);
        if (content == null) {
            return null;
        }
        KeyGenerator kg = KeyGenerator.getInstance("AES");
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
        random.setSeed(password.getBytes());
        kg.init(128, random);
        SecretKey secretKey = kg.generateKey();
        byte[] enCodeFormat = secretKey.getEncoded();
        SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] result = cipher.doFinal(content);
        return new String(result);
    }

    public static void main(String[] args) {
        String content = "toAes";
        String salt = "hjb";
        try {
            String encrypt = Aes.encrypt(content, salt);
            System.out.println(encrypt);
            String decrypt = Aes.decrypt(encrypt, salt);
            System.out.println(decrypt);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

