package jp.pmw.test_en_revolution.confirm_class_plan;

/**
 * Created by scr on 2015/01/03.
 * 授業内での総合出欠席回数を保持するクラスです.
 */
public class TotalAttendance {

    private int totalAttenteeCount;
    private int totalAbsenteeCount;

    public TotalAttendance(int totalAttendee,int totalAbsentee){
        this.totalAttenteeCount = totalAttendee;
        this.totalAbsenteeCount = totalAbsentee;
    }

    public int getTotalAttenteeCount(){return this.totalAttenteeCount;}
    public int getTotalAbsenteeCount(){return this.totalAbsenteeCount;}

}
