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
    @SerializedName("late_count")
    private int lateCount;
    public int getLateCount(){return lateCount;}
    //  欠席した回数
    @SerializedName("absented_count")
    private int absentedCount;
    public int getAbsentedCount(){return absentedCount;}
}