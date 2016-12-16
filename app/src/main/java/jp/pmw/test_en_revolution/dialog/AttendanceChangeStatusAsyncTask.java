package jp.pmw.test_en_revolution.dialog;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import java.io.IOException;
import jp.pmw.test_en_revolution.MyAsyncTask;
import jp.pmw.test_en_revolution.config.URL;
import jp.pmw.test_en_revolution.network.MyHttpConnection;
import jp.pmw.test_en_revolution.network.MyIOException;

/**
 * Created by si on 2016/12/15.
 */

public class AttendanceChangeStatusAsyncTask extends MyAsyncTask {
    AttendanceChangeStatusDialogFragment mDialogFragment;
    String mSameClassNumber;
    public AttendanceChangeStatusAsyncTask(AttendanceChangeStatusDialogFragment df,String sameClassNumber){
        this.mDialogFragment  =   df;
        this.mSameClassNumber = sameClassNumber;
    }

    @Override
    protected String doInBackground(Void... params) {
        try {
            String url = URL.getOnlyAbsent( mSameClassNumber );
            String result = MyHttpConnection.run(url);
            Absent model = new Gson().fromJson(result,Absent.class);
            AttendanceChangeStatus[] absents = model.mAttendanceChangeStatus;
            process( absents );
        } catch(IOException e) {
            MyIOException.absorbIOException(e);
        }
        return null;
    }

    void process( AttendanceChangeStatus[] sos ){
        if( this.mDialogFragment == null ){
            return;
        }
        this.mDialogFragment.setItme( sos );
    }

    public class Absent{
        @SerializedName("absents")
        public AttendanceChangeStatus[] mAttendanceChangeStatus;
    }

}
