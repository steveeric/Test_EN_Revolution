package jp.pmw.test_en_revolution.confirm_class_plan;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by scr on 2014/12/23.
 */
public class Timetable implements Serializable {
    //時間帯名
    @SerializedName("timetable_name")
    private String timeZoneName;
    //講義時間開始時刻
    @SerializedName("class_start_time")
    private String classStartTime;
    //講義時間終了時刻
    @SerializedName("class_end_time")
    private String classEndTime;

    public Timetable(String timeZoneName, String classStartTime, String classEndTime){
        this.timeZoneName = timeZoneName;
        this.classStartTime = classStartTime;
        this.classEndTime = classEndTime;
    }

    public String getTimeZoneName(){
        return this.timeZoneName;
    }
    public String getClassStartTime(){
        return this.classStartTime;
    }
    public String getClassEndTime(){
        return this.classEndTime;
    }
}
