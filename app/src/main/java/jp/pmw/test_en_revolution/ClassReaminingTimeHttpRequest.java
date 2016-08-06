package jp.pmw.test_en_revolution;

import org.json.JSONObject;

import java.util.Timer;

import jp.pmw.test_en_revolution.config.URL;

/**
 * Created by si on 2016/01/29.
 */
public class ClassReaminingTimeHttpRequest extends ClassHttpRequest{
    ClassReaminingTimeHttpRequest(MainActivity acitvity){
        super(acitvity);
    }

    /**
     * Created by si on 2016/01/29.
     * getClassReamingTimeFromNetWrokDBメソッド
     * 授業残り時間を取得します.
     * **/
    public void getClassReamingTimeFromNetWrokDB(){
        String url = URL.getClassReaminingTime(sameClassNumber);
        //this.doOkHttp(url);
        //activity.doVolley(url);
    }
    public void jsonObjectParser(JSONObject jsonObject){
        ClassReamingTime crTime =  gson.fromJson(jsonObject.toString(), ClassReamingTime.class);
        setClassReamingTime(crTime);
    }

    /**
     * Created by si on 2016/01/29.
     * setClassReamingTimeメソッド
     * 授業残り時間をセットします.
     *  @param crTime       授業残り時間オブジェクト
     * **/
    private void setClassReamingTime(ClassReamingTime crTime){
        activity.getClassObject().setClassTimeReaming(crTime.reamingTime);
        //  授業残り時間[msec]
        long classReamingTime = activity.getClassObject().getClassTimeReaming();

        if(activity.endClassRoomTimer == null){
            //授業終了タイマー
            activity.endClassRoomTimer = new Timer();
        }else{
            activity.endClassRoomTimer.cancel();
            activity.endClassRoomTimer = null;
            activity.endClassRoomTimer = new Timer();
        }
        //授業終了タイマー
        activity.endClassRoomTimer.schedule(new FinishTimer(activity), classReamingTime);
        if(classReamingTime > 0){
            //  まだ授業中であれば
            //  授業データを取得する.
            this.activity.getClassActivityController().getClassData();

            //activity.chkTrxTranmitState();
        }
    }

}
