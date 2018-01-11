package utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.*;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * OkHttpUtils
 *
 * @author zhuhaitao
 * @date 2017/11/28 上午10:07
 */
public class OkHttpUtils {

    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private static final Gson gson = new GsonBuilder().create();

    private static final OkHttpClient client = new OkHttpClient.Builder()
            .connectionPool(new ConnectionPool(5, 10, TimeUnit.MINUTES))
            .connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .build();

    static {
        System.setProperty("https.protocols", "TLSv1");
        System.setProperty("jsse.enableSNIExtension", "false");
    }

    /**
     * POST 请求
     *
     * @param url
     * @param object
     * @return
     */
    public static String doPost(String url, Object object, String header) {
        String bodyJson = null;
        RequestBody requestBody = null;
        if (null != object) {
            bodyJson = gson.toJson(object);
            requestBody = RequestBody.create(JSON, bodyJson);
        } else {
            RequestBody.create(JSON, "{}");
        }

        Request.Builder requestBuilder = new Request.Builder().url(url)
                .header("key", header)
                .post(requestBody);
        Request request = requestBuilder.build();

        try {
            String body = client.newCall(request).execute().body().string();
            return body;
        } catch (Exception e) {
            System.err.println(e);
            return "";
        }
    }

    /**
     * post form 表单提交
     *
     * @param url
     * @param map
     * @return
     */
    public static String doPostForm(String url, Map<String, Object> map, String header) {

        FormBody.Builder builder = new FormBody.Builder();
        for (String key : map.keySet()) {
            builder.add(key, gson.toJson(map.get(key)));
        }
        Request request = new Request.Builder().url(url)
                .header("key", header)
                .post(builder.build()).build();

        try {
            return client.newCall(request).execute().body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }


    /**
     * GET 请求
     *
     * @param url
     * @param params
     * @return
     */
    public static String doGet(String url, Map<String, Object> params) {
        if (null != params && params.size() > 0) {
            StringBuilder sb = new StringBuilder(url);
            if (url.contains("?")) {
                sb.append("&");
            } else {
                sb.append("?");
            }

            for (String key : params.keySet()) {
                sb.append(key).append("=").append(params.get(key)).append("&");
            }
            url = sb.substring(0, sb.length() - 1);
        }
        Request request = new Request.Builder().url(url)
//                .header("key", header)
                .build();
        try {
            Response response = client.newCall(request).execute();

            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

}
