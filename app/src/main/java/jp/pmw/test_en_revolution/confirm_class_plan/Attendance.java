package jp.pmw.test_en_revolution.confirm_class_plan;

import com.google.gson.annotations.SerializedName;

/**
 * Created by scr on 2015/02/25.
 */
public class Attendance {

    @SerializedName("attendance_id")
    private String attendanceId;
    @SerializedName("status")
    private int status;
    @SerializedName("time")
    private String time;
    @SerializedName("forgotapply_time")
    private String fogotApplytTime;

    public void setStatus(int status){
           this.status = status;
    }
    public void setTime(String time){
        this.time = time;
    }
    public void setFogotApplytTime(String applyTime){this.fogotApplytTime = fogotApplytTime;}


    public String getAttendanceId(){return this.attendanceId;}
    public String getTime(){
        return this.time;
    }
    public int getStatus(){
        return this.status;
    }
    public String getFogotApplytTime(){return this.fogotApplytTime;}

}
