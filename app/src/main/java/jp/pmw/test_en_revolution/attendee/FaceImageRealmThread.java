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
    public String mLastUpdateTime;
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
            byte[] bytes = getByte();
            if(registeredCount == 0){
                FaceImageRealmObject obj = realm.createObject(FaceImageRealmObject.class);
                obj.setUrl(mUrl);
                obj.setFaceImage(bytes);
                obj.setLastUpdateTime(mLastUpdateTime);
            }else{
                results.get(0).setLastUpdateTime( mLastUpdateTime );
                results.get(0).setFaceImage( bytes );
            }
            realm.commitTransaction();
            Looper.loop();
        } finally {
            if (realm != null) {
                realm.close();
            }
        }
    }
    byte[] getByte(){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] bytes = baos.toByteArray();
        return bytes;
    }

}