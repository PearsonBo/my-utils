package utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Random;

/**
 * 字符串工具类
 *
 * @author zhuhaitao
 * @date 2017/12/29 上午11:22
 */
public class StringUtils {

    public static Gson gson = new GsonBuilder().create();

    /**
     * 字符串是否为空
     * <ul>
     * <li>StringUtils.isEmpty(null) = true</li>
     * <li>StringUtils.isEmpty("") = true</li>
     * <li>StringUtils.isEmpty("   ") = true</li>
     * <li>StringUtils.isEmpty("abc") = false</li>
     * </ul>
     *
     * @param value 被校验字符串
     * @return true/false
     */
    public static boolean isEmpty(String value) {
        int strLen;
        if (value == null || (strLen = value.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if ((Character.isWhitespace(value.charAt(i)) == false)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 字符串不为空校验
     *
     * @param value 被校验字符串
     * @return true/false
     */
    public static boolean isNotEmpty(String value) {
        return isEmpty(value) ? false : true;
    }

    /**
     * 生产随机字符串
     *
     * @param length 范围
     * @return 随机数
     */
    public static String getRandom(int length) {
        Random random = new Random();
        StringBuilder ret = new StringBuilder();
        for (int i = 0; i < length; i++) {
            boolean isChar = random.nextInt(2) % 2 == 0;
            if (isChar) {
                int choice = random.nextInt(2) % 2 == 0 ? 65 : 97;
                ret.append((char) (choice + random.nextInt(26)));
            } else {
                ret.append(Integer.toString(random.nextInt(10)));
            }
        }
        return ret.toString();
    }


}
