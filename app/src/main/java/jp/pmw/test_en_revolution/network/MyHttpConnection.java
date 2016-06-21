package jp.pmw.test_en_revolution.network;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by si on 2016/05/31.
 */
public class MyHttpConnection {
    static OkHttpClient sClient = new OkHttpClient();

    private static MyHttpConnection instance = null;
    public static MyHttpConnection getInstance() {
        if (instance == null) {
            instance = new MyHttpConnection();
        }
        return instance;
    }

    private static void generateInstance(){
        if(instance == null){
            getInstance();
        }
    }

    public static String run(String url) throws IOException {
        generateInstance();
        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response = sClient.newCall(request).execute();
        return response.body().string();
    }

}