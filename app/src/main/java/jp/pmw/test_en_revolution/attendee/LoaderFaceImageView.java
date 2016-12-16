package jp.pmw.test_en_revolution.attendee;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by si on 2016/12/14.
 */

public class LoaderFaceImageView extends ImageView
        implements LoaderManager.LoaderCallbacks<Bitmap>{

    private String mUrl;

    public LoaderFaceImageView(Context context) {
        super(context);
    }

    public LoaderFaceImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LoaderFaceImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    // 表示する画像のURLをセット
    public void     setUrl(String url){
        this.mUrl = url;
    }

    @Override
    public Loader<Bitmap> onCreateLoader(int id, Bundle args) {
        return new HttpAsyncFaceImageLoader(getContext(), this.mUrl);
    }

    @Override
    public void onLoadFinished(Loader<Bitmap> loader, Bitmap data) {
        setImageBitmap(data);
    }

    @Override
    public void onLoaderReset(Loader<Bitmap> loader) {

    }
}