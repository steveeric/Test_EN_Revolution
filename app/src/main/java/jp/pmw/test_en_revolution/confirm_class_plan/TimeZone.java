package jp.pmw.test_en_revolution.confirm_class_plan;

import java.io.Serializable;

/**
 * Created by scr on 2014/12/23.
 */
public class TimeZone implements Serializable {
    //時間帯名
    private String timeZoneName;
    //講義時間開始時刻
    private String classStartTime;
    //講義時間終了時刻
    private String classEndTime;

    public TimeZone(String timeZoneName,String classStartTime,String classEndTime){
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
