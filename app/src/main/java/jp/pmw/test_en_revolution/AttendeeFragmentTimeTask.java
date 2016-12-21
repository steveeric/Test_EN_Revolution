package jp.pmw.test_en_revolution;

import android.widget.GridView;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.TimerTask;

import jp.pmw.test_en_revolution.config.URL;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by si on 2016/01/28.
 */
public class AttendeeFragmentTimeTask extends TimerTask {
    private MainActivity activity;
    private AttendeeFragment attendeeFragment;
    private GridView attendeeGridView;
    AttendeeFragmentTimeTask(MainActivity activity, AttendeeFragment attendeeFragment, GridView attendeeGridView){
        this.activity = activity;
        this.attendeeFragment = attendeeFragment;
        this.attendeeGridView = attendeeGridView;
    }
    public void run(){
        chkReDrawAttendeeFragment();
        //  出席関係の情報を取得する.
        chkAttendanceRelationshipInfo();
        //  出席認定一括変更ダイアログを表示(状況に応じて表示されます)
        attendeeFragment.showAttendanceChangeStatusDialogFrament();
        //  在室確認NACK一括変更ダイアログを表示(状況に応じて表示されます)
        attendeeFragment.showReAttendanceChangeStatusDialogFrament();
    }

    private void chkReDrawAttendeeFragment(){
        boolean reDrawFlag = this.activity.reDrawAttendFlag;
        if(reDrawFlag){
            this.activity.reDrawAttendFlag = false;
            attendeeFragment.displayToastThroughHandlerThread();
        }
    }

    //  出席関係の情報を取得する.
    private void chkAttendanceRelationshipInfo(){
        if(this.activity.getClassObject().getStudentObject() != null
                && activity.chkAttendanceRelationshipInfoRetrieving == false){
            //  出席・出席(忘れ)・遅刻・遅刻(忘れ)・欠席者数の変更
            NumOfAttendanceEntity noae = this.activity.getClassObject().getNumOfAttendanceEnttity();
            attendeeFragment.setAttendanceStatusTextView(noae);
            //  再描画
            attendeeFragment.displayRedrawGridViewHandlerThread();
            //  新しい情報取得
            activity.getClassHttpRequest().getAttendanceRelationShipInto();
            //
            activity.chkAttendanceRelationshipInfoRetrieving = true;
        }
    }


}
