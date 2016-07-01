package jp.pmw.test_en_revolution.dialog;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import java.io.IOException;
import jp.pmw.test_en_revolution.network.MyIOException;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by si on 2016/06/13.
 * 顔画像をダウンロードしてくるタスククラスです.
 */
public class DownloadFaceImageTask extends AsyncTask<String, Void, Bitmap> {
    Handler mHandler;
    LinearLayout mLinearLayout;
    ImageView mImageView;
    public DownloadFaceImageTask(Handler handler, LinearLayout linearLayout,ImageView imageView) {
        this.mHandler = handler;
        this.mLinearLayout = linearLayout;
        this.mImageView = imageView;
    }

    protected Bitmap doInBackground(String... urls) {
        final OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(urls[0])
                .build();
        Response response = null;
        Bitmap mIcon11 = null;
        try {
            response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                try {
                    mIcon11 = BitmapFactory.decodeStream(response.body().byteStream());
                    if( mIcon11 != null ){
                        show(mIcon11);
                    }
                } catch (Exception e) {
                    MyIOException.absorbException(e);
                }
            }
        } catch (IOException e) {
            MyIOException.absorbIOException(e);
        }
        return mIcon11;
    }

    protected void onPostExecute(Bitmap result) {}

    /**
     * showメソッド
     * 顔画像を表示します.
     * */
    void show(final Bitmap bmp){
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                //  顔画像セット
                mImageView.setImageBitmap(bmp);
                //  顔画像レイアウトを表示する.
                mLinearLayout.setVisibility(View.VISIBLE);
            }
        });

    }


}