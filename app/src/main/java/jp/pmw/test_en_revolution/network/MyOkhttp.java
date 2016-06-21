package jp.pmw.test_en_revolution.network;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by si on 2016/06/18.
 */
public class MyOkhttp {
    //  ネットワークアクセス
    OkHttpClient client = new OkHttpClient();

    private static MyOkhttp instance = null;
    public static MyOkhttp newInstance() {
        if (instance == null) {
            instance = new MyOkhttp();
        }
        return instance;
    }

    public void getRequest(final Context context, final String url){
        new MyAsyncTask() {
            @Override
            protected String doInBackground(Void... params) {
                String result = null;
                try {
                    result = getRun(url);
                } catch(IOException e) {
                    showError(context, e.toString());
                }
                return result;
            }
        }.execute();
    }
    public String getRun(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    void showError(final Context context, final String error){
        Handler handler = new Handler(Looper.myLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context, error, Toast.LENGTH_LONG).show();
            }
        });
    }

}
