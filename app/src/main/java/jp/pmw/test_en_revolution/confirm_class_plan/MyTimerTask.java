package jp.pmw.test_en_revolution.confirm_class_plan;

import android.content.Context;
import android.os.Handler;
import android.widget.TextView;

import java.util.TimerTask;

/**
 * Created by scr on 2014/12/17.
 */
public class MyTimerTask extends TimerTask implements Runnable{
    int sec;
    Context context;
    Handler mHandler;
    TextView tv;
    MyTimerTask(Context context,Handler handler,TextView tv,int sec){
        this.context = context;
        this.mHandler = handler;
        this.tv = tv;
        this.sec=(sec+1);
    }

    @Override
    public void run() {
        // mHandlerを通じてUI Threadへ処理をキューイング
        mHandler.post( new Runnable() {
            public void run() {
                //現在のLapTime
                --sec;
                tv.setText(sec+"秒後にアプリが自動で終了します.");
                if(sec == 0){
                    //終了!
                    ConfirmClassPlanActivity activity = (ConfirmClassPlanActivity)context;
                    activity.finish();
                }
            }
        });
    }
}
