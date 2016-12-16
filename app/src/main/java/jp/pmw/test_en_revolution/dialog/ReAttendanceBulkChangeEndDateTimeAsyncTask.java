package jp.pmw.test_en_revolution.dialog;

import java.io.IOException;

import jp.pmw.test_en_revolution.MyAsyncTask;
import jp.pmw.test_en_revolution.config.URL;
import jp.pmw.test_en_revolution.network.MyHttpConnection;
import jp.pmw.test_en_revolution.network.MyIOException;

/**
 * Created by si on 2016/12/16.
 * 在室確認ダイアログを以降表示しないようにネットワークDBに記録します.
 */

public class ReAttendanceBulkChangeEndDateTimeAsyncTask extends MyAsyncTask {
    String mSameClassNumber;
    public ReAttendanceBulkChangeEndDateTimeAsyncTask(String sameClassNumber){
        this.mSameClassNumber = sameClassNumber;
    }
    @Override
    protected String doInBackground(Void... params) {
        try {
            String url = URL.getUrlRecordReAttendanceBulkChangeEndDateTime(this.mSameClassNumber);
            String result = MyHttpConnection.run(url);
        } catch(IOException e) {
            MyIOException.absorbIOException(e);
        }
        return null;
    }
}
