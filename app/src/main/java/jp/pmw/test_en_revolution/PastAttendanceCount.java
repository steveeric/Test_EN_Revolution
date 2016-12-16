package jp.pmw.test_en_revolution;

import com.google.gson.annotations.SerializedName;

/**
 * Created by si on 2016/05/25.
 * 過去(一つ前の授業までの)出欠回数クラスです.
 * */
public class PastAttendanceCount{
    //  出席した回数
    @SerializedName("attended_count")
    private int attendedCount;
    public int getAttendedCount(){return attendedCount;}
    //  遅刻した回数
    @SerializedName("lated_count")
    private int latedCount;
    public int getLatedCount(){return latedCount;}
    //  欠席した回数
    @SerializedName("absented_count")
    private int absentedCount;
    public int getAbsentedCount(){return absentedCount;}
    //
    //  早退した回数
    @SerializedName("leave_early_count")
    private int leaveEarlyCount;
    public int getLeaveEarlyCount(){return leaveEarlyCount;}
}