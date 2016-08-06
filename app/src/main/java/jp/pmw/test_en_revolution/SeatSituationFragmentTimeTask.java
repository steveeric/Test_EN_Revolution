package jp.pmw.test_en_revolution;

import android.widget.GridView;

import java.util.TimerTask;

/**
 * Created by si on 2016/02/16.
 * 座席に着席している学生の出欠状況の取得随時行う
 * タイマータスククラス.
 */
public class SeatSituationFragmentTimeTask extends TimerTask {
    private MainActivity activity;
    private SeatSituationFragment seatSituationFragment;
    SeatSituationFragmentTimeTask(MainActivity activity, SeatSituationFragment seatSituationFragment){
        this.activity = activity;
        this.seatSituationFragment = seatSituationFragment;
    }
    public void run(){
        //chkReDrawSeatSituationFragment();
        chkAttendanceRelationshipInfo();
    }

    /*private void chkReDrawSeatSituationFragment(){
        boolean reDrawFlag = this.activity.reDrawAttendFlag;
        if(reDrawFlag){
            this.activity.reDrawAttendFlag = false;
            seatSituationFragment.initStudentObject();
        }
    }*/

    //  出席関係の情報を取得する.
    private void chkAttendanceRelationshipInfo(){
        if(this.activity.getClassObject().getStudentObject() != null
                && activity.chkAttendanceRelationshipInfoRetrieving == false){
            //  再描画
            seatSituationFragment.displayRoomViewHandlerThread();
            //  新しい情報取得
            activity.getClassHttpRequest().getAttendanceRelationShipInto();
            //
            activity.chkAttendanceRelationshipInfoRetrieving = true;
            //getAttendanceRelationShipInto();
        }
    }
}
