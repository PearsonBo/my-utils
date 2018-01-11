package utils;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * IpUtils
 *
 * @author zhuhaitao
 * @date 2017/12/29 下午12:04
 */
public class IpUtil {
    /**
     * 本机 ip
     */
    private static String LOCALHOST_IP = "127.0.0.1";

    private IpUtil() {

    }

    /**
     * 获取当前 ip
     *
     * @return
     */
    public static String getHostAddress() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return LOCALHOST_IP;
        }
    }

    public static void main(String[] args) {
        String hostAddress = IpUtil.getHostAddress();
        System.out.println(hostAddress);
    }
}
