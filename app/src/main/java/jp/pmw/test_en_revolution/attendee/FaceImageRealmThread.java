package jp.pmw.test_en_revolution.attendee;

import android.graphics.Bitmap;
import android.os.Looper;

import java.io.ByteArrayOutputStream;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by si on 2016/12/14.
 */

public class FaceImageRealmThread  extends Thread {
    private Realm realm;
    public String mUrl;
    public Bitmap bmp;
    @Override
    public void run() {
        Looper.prepare();
        try {
            realm = Realm.getDefaultInstance();

            realm.beginTransaction();
            RealmResults<FaceImageRealmObject> results = realm.where(FaceImageRealmObject.class)
                    .equalTo("url", this.mUrl)
                    .findAll();
            int registeredCount = results.size();
            if(registeredCount == 0){
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] bytes = baos.toByteArray();
                FaceImageRealmObject obj = realm.createObject(FaceImageRealmObject.class);
                obj.setUrl(mUrl);
                obj.setFaceImage(bytes);
            }
            realm.commitTransaction();
            Looper.loop();
        } finally {
            if (realm != null) {
                realm.close();
            }
        }
    }
}