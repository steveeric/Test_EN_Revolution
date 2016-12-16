package jp.pmw.test_en_revolution.dialog;

import java.io.IOException;

import jp.pmw.test_en_revolution.MyAsyncTask;
import jp.pmw.test_en_revolution.config.URL;
import jp.pmw.test_en_revolution.network.MyHttpConnection;
import jp.pmw.test_en_revolution.network.MyIOException;

/**
 * Created by si on 2016/12/16.
 * 出席認定一括変更記録終了日時をDBに記録します.
 */
public class AttendanceBulkChangeEndDateTimeAsyncTask  extends MyAsyncTask {
    String mSameClassNumber;
    public AttendanceBulkChangeEndDateTimeAsyncTask(String sameClassNumber){
        this.mSameClassNumber = sameClassNumber;
    }
    @Override
    protected String doInBackground(Void... params) {
        try {
            String url = URL.getUrlRecordAttendanceBulkChangeEndDateTime(this.mSameClassNumber);
            String result = MyHttpConnection.run(url);
        } catch(IOException e) {
            MyIOException.absorbIOException(e);
        }
        return null;
    }
}
