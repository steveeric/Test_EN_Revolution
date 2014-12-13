package jp.pmw.test_en_revolution.room;

import java.util.TimerTask;

import jp.pmw.test_en_revolution.SeatSituationFragment;

/**
 *
 * Created by scr on 2014/12/12.
 * 出欠席者の情報をどれくらいの間隔でアクセスすべきかを管理するタイマークラス.
 * @author Shota Ito
 * @version 1.0
 */
public class AccessTimerTask extends TimerTask implements Runnable{
    private SeatSituationFragment fragment;
    public AccessTimerTask(SeatSituationFragment fragment){
        this.fragment=fragment;
    }

    @Override
    public void run(){
        //出席者の情報を取得しに行く
        fragment.getNetworkAttendanceInfo();
    }
}
