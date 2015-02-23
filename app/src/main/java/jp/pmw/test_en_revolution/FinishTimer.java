package jp.pmw.test_en_revolution;

import java.util.TimerTask;

/**
 * Created by scr on 2015/01/14.
 * 授業終了時間になったらアプリ画面をダウンさせるタイマー
 */
public class FinishTimer  extends TimerTask {
    private MainActivity activity;
    private long endTime;
    FinishTimer(MainActivity activity,long endTime){
        this.activity = activity;
        this.endTime = endTime;
    }


    @Override
    public void run() {
        long nowTime = System.currentTimeMillis();
        if(endTime < nowTime){
            //アプリ終了
            endClassRoom();
        }
    }

    private void endClassRoom(){
        //アプリ画面を落とす.
        this.activity.endClassRoom();
    }

}
