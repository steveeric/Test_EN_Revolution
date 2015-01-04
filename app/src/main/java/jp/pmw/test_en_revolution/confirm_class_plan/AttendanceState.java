package jp.pmw.test_en_revolution.confirm_class_plan;

import java.io.Serializable;

/**
 * Created by scr on 2014/12/24.
 */
public class AttendanceState implements Serializable {
    //仮出席状態かどうか
    private int tempAttendanceState;
    //ESL忘れ申請時間
    private String requestForgotESLTime;
    //出席確認した時間
    private String confirmTime;

    public AttendanceState(int tempAttendanceState,
                           String requestForgotESLTime,
                           String confirmTime){
        this.tempAttendanceState = tempAttendanceState;
        this.requestForgotESLTime = requestForgotESLTime;
        this.confirmTime = confirmTime;
    }
    /**
     * ESL忘れ申請
     * **/
    public void forgotESLRequest(){
        this.requestForgotESLTime = "";
    }
    /**
     * ESL忘れ申請解除
     * **/
    public void forgotESLRelease(){
        this.requestForgotESLTime = null;
        this.confirmTime = null;
    }

    public int getTempAttendanceState(){
        return this.tempAttendanceState;
    }
    public String getRequestForgotESLTime(){
        return this.requestForgotESLTime;
    }
    public String getConfirmTime(){
        return this.confirmTime;
    }

}
