package jp.pmw.test_en_revolution;

import com.google.gson.annotations.SerializedName;

import org.json.JSONObject;

import jp.pmw.test_en_revolution.config.URL;

/**
 * Created by si on 2016/01/29.
 */
public class ChkAttendanceHttpRequest extends ClassHttpRequest{

    ChkAttendanceHttpRequest(MainActivity activity){
        super(activity);
    }

    /**
     * Created by si on 2016/01/29.
     * getChkAttendanceメソッド
     * 授業参加予定学生と出欠状態を取得します.
     * **/
    public void getChkAttendance(){
        String url = URL.getUrlChkAttendance(sameClassNumber);
        //this.doVolley(url);
        //this.doOkHttp(url);
    }

    public void jsonObjectParser(JSONObject jsonObject){
        RegistrationObject ro =  gson.fromJson(jsonObject.toString(), RegistrationObject.class);
        doChkAttendance(ro.sos);
    }
    /*
    * 学生情報と出欠データ取得で使用クラス
    * */
    class RegistrationObject{
        //
        @SerializedName("students")
        public StudentObject[] sos;
    }

    /**
     * Created by si on 2016/01/29.
     * doChkAttendanceメソッド
     * 授業参加学生群を授業オブジェクトにセットします.
     *  @param students       学生群
     * **/
    public void doChkAttendance(StudentObject[] sos){
        activity.getClassObject().setStudentObject(sos);
    }
}
