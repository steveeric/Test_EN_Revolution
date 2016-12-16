package jp.pmw.test_en_revolution.dialog;

import java.io.IOException;
import jp.pmw.test_en_revolution.MyAsyncTask;
import jp.pmw.test_en_revolution.config.URL;
import jp.pmw.test_en_revolution.network.MyHttpConnection;
import jp.pmw.test_en_revolution.network.MyIOException;

/**
 * Created by si on 2016/12/16.
 * 手動出席認定申請(SonoRIs忘れ含む)・取り消しを行う
 */

public class ManualRequestAttendanceAsyncTask extends MyAsyncTask {
    //  取消し
    public static final int CANCEL = 0;
    //  SonoRIsが反応しない
    public static final int NO_RESPONSE = 1;
    //  SonoRIs忘れ
    public static final int FORGOT = 2;
    //  忘れ申請パラメータ
    public static final int PARAMETA_FORGOT_ESL = 1;
    //  忘れ解除パラメータ
    public static final int PARAMETA_RELEASE_FORGOT = 0;
    AttendanceChangeStatus mAttendanceChangeStatus;
    int mSelected = -1;
    public ManualRequestAttendanceAsyncTask(AttendanceChangeStatus acs, int selected){
        this.mAttendanceChangeStatus = acs;
        this.mSelected = selected;
    }
    @Override
    protected String doInBackground(Void... params) {
        try {
            String url = null;
            String attendanceId = mAttendanceChangeStatus.getAttendanceObject().getAttendanceId();
            if( CANCEL == mSelected ){
                url = URL.getUrlRegardedAsAbsent(attendanceId);
            }else if( NO_RESPONSE == mSelected ){
                url = URL.getUrlRegardedAsAttendance(attendanceId, NO_RESPONSE, PARAMETA_RELEASE_FORGOT);
            }else if( FORGOT == mSelected ){
                url = URL.getUrlRegardedAsAttendance(attendanceId, FORGOT, PARAMETA_FORGOT_ESL);
            }
            if( url != null ){
                String result = MyHttpConnection.run(url);
            }
        } catch(IOException e) {
            MyIOException.absorbIOException(e);
        }
        return null;
    }
}
