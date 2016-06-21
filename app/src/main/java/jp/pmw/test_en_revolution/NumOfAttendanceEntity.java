package jp.pmw.test_en_revolution;

import com.google.gson.annotations.SerializedName;

/**
 * Created by si on 2016/06/21.
 */
public class NumOfAttendanceEntity {
    //  出席者(忘れでない)
    @SerializedName("attendance_not_forgot")
    public int mAttendanceNotForgot;
    //  出席者(忘れでない)
    @SerializedName("attendance_forgot")
    public int mAttendanceForgot;
    //  出席者(忘れでない)
    @SerializedName("late_not_forgot")
    public int mLateNotForgot;
    //  出席者(忘れでない)
    @SerializedName("late_forgot")
    public int mLateForgot;
    //  出席者(忘れでない)
    @SerializedName("absent")
    public int mAbsent;

}
