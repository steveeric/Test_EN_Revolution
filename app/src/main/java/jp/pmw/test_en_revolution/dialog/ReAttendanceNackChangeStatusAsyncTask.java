package jp.pmw.test_en_revolution.dialog;

import java.io.IOException;
import jp.pmw.test_en_revolution.MyAsyncTask;
import jp.pmw.test_en_revolution.config.URL;
import jp.pmw.test_en_revolution.network.MyHttpConnection;
import jp.pmw.test_en_revolution.network.MyIOException;

/**
 * Created by si on 2016/12/16.
 * 在室確認赤外線で、ACKを返さなかった人をSonoRIs MTから申請します.
 *
 */

public class ReAttendanceNackChangeStatusAsyncTask extends MyAsyncTask {
    //  出席ID
    String mAttendanceId;
    //  0:取消し, 1:目視で在室を確認
    int mParameta;
    public ReAttendanceNackChangeStatusAsyncTask(String attendanceId, int parameta){
        this.mAttendanceId = attendanceId;
        this.mParameta = parameta;
    }

    @Override
    protected String doInBackground(Void... params) {
        try {
            String url = URL.getReAttendLeak(mAttendanceId, mParameta);
            String result = MyHttpConnection.run(url);
        } catch(IOException e) {
            MyIOException.absorbIOException(e);
        }
        return null;
    }
}
