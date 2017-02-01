package jp.pmw.test_en_revolution.dialog;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.io.IOException;

import jp.pmw.test_en_revolution.MyAsyncTask;
import jp.pmw.test_en_revolution.config.URL;
import jp.pmw.test_en_revolution.network.MyHttpConnection;
import jp.pmw.test_en_revolution.network.MyIOException;

/**
 * Created by si on 2016/12/16.
 * 在室確認でACKが得られなかった学生のみ取得
 */

public class ReAttendanceChangeStatusAsyncTask extends MyAsyncTask {
    ReAttendanceChangeStatusDialogFragment mDialogFragment;
    String mSameClassNumber;
    public ReAttendanceChangeStatusAsyncTask(ReAttendanceChangeStatusDialogFragment df,String sameClassNumber){
        this.mDialogFragment  =   df;
        this.mSameClassNumber = sameClassNumber;
    }

    @Override
    protected String doInBackground(Void... params) {
        try {
            String url = URL.getUrlNackReAttendance( mSameClassNumber );
            String result = MyHttpConnection.run(url);
            NackReAttendance model = new Gson().fromJson(result,NackReAttendance.class);
            ReAttendanceNackChangeStatus[] absents = model.mReAttendanceNackChangeStatus;
            process( absents );
        } catch(IOException e) {
            MyIOException.absorbIOException(e);
        }
        return null;
    }

    void process( ReAttendanceNackChangeStatus[] sos ){
        if( this.mDialogFragment == null ){
            return;
        }
        if( sos.length == 0 ){
            this.mDialogFragment.cloaseProcess();
        }else{
            this.mDialogFragment.setItme( sos );
        }
    }

    public class NackReAttendance{
        @SerializedName("nack_re_attendances")
        public ReAttendanceNackChangeStatus[] mReAttendanceNackChangeStatus;
    }

}
