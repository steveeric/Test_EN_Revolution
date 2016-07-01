package jp.pmw.test_en_revolution.dialog;

import android.app.Dialog;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.google.gson.Gson;
import java.io.IOException;
import jp.pmw.test_en_revolution.R;
import jp.pmw.test_en_revolution.config.URL;
import jp.pmw.test_en_revolution.network.MyAsyncTask;
import jp.pmw.test_en_revolution.network.MyHttpConnection;
import jp.pmw.test_en_revolution.network.MyIOException;

/**
 * Created by si on 2016/07/01.
 */
public class FaceImage {
    //  学生ダイアログフラグメントクラス
    StudentInfoDialogFragnemt mStudentInfoDialogFragment;
    //  顔画像レイアウト
    LinearLayout mFaceImageLl;
    //  顔画像をせっとするImageVIew
    ImageView mFaceImageIv;
    //  顔画像エンティティー
    FaceImageEntity mFaceImageEntity;
    /**
     * コンストラクタ
     * */
    FaceImage(StudentInfoDialogFragnemt sidf){
        this.mStudentInfoDialogFragment = sidf;
        init();
    }
    /**
     *  getUIHandlerメソッド
     *  UIハンドラーを取得する.
     * */
    Handler getUIHandler(){
        return this.mStudentInfoDialogFragment.mHandler;
    }
    /**
     * initメソッド
     * 初期起動
     * */
    void init(){
        Dialog d = this.mStudentInfoDialogFragment.dialog;
        //  ライナーレイアウト
        mFaceImageLl = mStudentInfoDialogFragment.getLinearLayout(d, R.id.dialog_custom_face_image_ll);
        //  顔画像出力用イメージビューレイアウト
        mFaceImageIv = (ImageView) d.findViewById(R.id.dialog_custom_face_image_iv);
        //  顔画像をネットワークサーバーから取得する.
        chkExistenceFaceImage();
    }
    /**
     *  getStudentIdメソッド
     *  学生IDを取得します.
     * */
    String getStudentId(){
        return this.mStudentInfoDialogFragment.tapStudent.studentId;
    }

    /**
     * chkExistenceFaceImageメソッド
     * 顔画像があるかを取得する.
     * */
    void chkExistenceFaceImage(){
        //  学生IDを取得する.
        String studentId = getStudentId();
        //  URL
        String url = URL.getUrlchkExistenceFaceImage(studentId);
        //  顔画像をネットワークから取得する.
        requestToWebApi( url );
    }
    /**
     * requestToWebApiメソッド
     * WEBAPIにアクセスします.
     * @param String url URL
     * */
    void requestToWebApi(final String url){
        new MyAsyncTask() {
            @Override
            protected String doInBackground(Void... params) {
                try {
                    String result = MyHttpConnection.run(url);
                    //  顔画像が存在するかを確認後
                    chkExistenceFaceImage(result);
                } catch(IOException e) {
                    MyIOException.absorbIOException(e);
                    invisibleFaceImageLinearLayout();
                }
                return null;
            }
        }.execute();
    }
    /**
     *  invisibleFaceImageLinearLayoutメソッド
     *  顔画像レイアウトを非表示します.
     * */
    void invisibleFaceImageLinearLayout(){
        Handler h = getUIHandler();
        h.post(new Runnable() {
            @Override
            public void run() {
                mFaceImageLl.setVisibility(View.GONE);
            }
        });
    }
    /**
     * analyzeメソッド
     * ネットワークから取得したデータを解析します.
     * */
    void chkExistenceFaceImage(String result){
        Gson g = new Gson();
        mFaceImageEntity = g.fromJson(result, FaceImageEntity.class);
        if( mFaceImageEntity.mFaceImageExistance == FaceImageEntity.S_EXIST){
            //  何か顔画像が存在する.
            getFaceImage();
        }else{
            //  顔画像レイアウトを非表示にする.
            invisibleFaceImageLinearLayout();
        }
    }
    /**
     * getFaceImageメソッド
     * 顔画像取得します.
     * */
    void getFaceImage(){
        //  学生IDを取得する.
        String studentId = getStudentId();
        String url = URL.getUrlgetFaceImage(studentId);
        DownloadFaceImageTask dfit = new DownloadFaceImageTask(this.getUIHandler(), this.mFaceImageLl, this.mFaceImageIv);
        dfit.doInBackground(url);
    }

}
