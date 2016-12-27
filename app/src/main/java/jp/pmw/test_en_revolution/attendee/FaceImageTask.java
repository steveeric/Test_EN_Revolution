package jp.pmw.test_en_revolution.attendee;


import java.io.ByteArrayOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import jp.pmw.test_en_revolution.R;

/**
 * Created by si on 2016/12/14.
 */

public class FaceImageTask extends AsyncTask<String, Void, Bitmap> {
    public ImageView imageView;
    public String tag;
    public Context context;
    public String mUrl;
    public String mLastUpdateTime;

    public FaceImageTask(Context context, ImageView imageView, String url, String lastUpdateTime) {
        this.imageView = imageView;
        this.context = context;
        this.tag = imageView.getTag().toString();
        this.mUrl = url;
        this.mLastUpdateTime = lastUpdateTime;
    }
    @Override
    protected Bitmap doInBackground(String... urls) {
        synchronized (context) {
            try {
                URL url = new URL(mUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                return BitmapFactory.decodeStream(connection.getInputStream());
            } catch (Exception e) {
                e.printStackTrace();
                setInCaseOfFaceImageError();
            }
        }
        return null;
    }
    //  顔画像が正常に取得できなかった
    void setInCaseOfFaceImageError(){
        if (this.tag.equals(this.imageView.getTag())) {
            Drawable drawable = context.getResources().getDrawable(R.drawable.img_sonoda_professor);
            this.imageView.setImageDrawable(drawable);
        }
    }
    @Override
    protected void onPostExecute(Bitmap result) {
        if (this.tag.equals(this.imageView.getTag())) {
            //if (result == null) return;
            if( result != null ){
                this.imageView.setImageBitmap(result);
                this.imageView.setVisibility(View.VISIBLE);
                saveFaceImageLocalDB(result);
            }else{
                setInCaseOfFaceImageError();
            }
        }
    }
    public void saveFaceImageLocalDB(Bitmap bmp){
        FaceImageRealmThread fi = new FaceImageRealmThread();
        fi.mUrl = this.mUrl;
        fi.bmp = bmp;
        fi.mLastUpdateTime = this.mLastUpdateTime;
        fi.start();
    }
}