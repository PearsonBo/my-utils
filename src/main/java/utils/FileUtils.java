package utils;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by HBooo on 2018/1/11.
 */
public class FileUtils {

    public static ArrayList<String> getFiles(String filePath) {
        ArrayList<String> fileList = new ArrayList<String>();
        File root = new File(filePath);
        File[] files = root.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                /*
                 * 递归调用
                 */
                getFiles(file.getAbsolutePath());
                fileList.add(file.getAbsolutePath());
            } else {
                String picPathStr = file.getAbsolutePath();
                fileList.add(picPathStr);
            }
        }
        return fileList;
    }

    //test
    public static void main(String[] args) {
        ArrayList<String> files = getFiles("D:\\new");
        for (String string : files) {
            System.out.println(string);
        }
    }
}
