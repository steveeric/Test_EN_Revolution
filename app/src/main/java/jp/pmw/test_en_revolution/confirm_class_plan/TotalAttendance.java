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


    /**
     *
     * **/
    public void setForGotESLRequest(AttendanceState state){
        int temp = state.getTempAttendanceState();
        String confirmTime = state.getConfirmTime();
        ++this.totalAttenteeCount;
        if(temp == 1){
            //まだ出席が行われていない
        }else if(temp == 0){
            //すでに欠席とみなされていた場合
            //欠席をマイナスにする.
            int nowAbsentee = this.totalAbsenteeCount;
            --nowAbsentee;
            if(nowAbsentee < 0){
                //何もしない
            }else{
                //欠席回数を減らす.
                this.totalAbsenteeCount = nowAbsentee;
            }
        }
    }
    public void setForGotESLRelease(AttendanceState state){
       --this.totalAttenteeCount;
        if(state.getTempAttendanceState() == 1
                && state.getRequestForgotESLTime() != null
                && state.getConfirmTime() == null){
            //まだ未確認なのでなにもしない.
        }else{
           //すでに欠席とみなされているので欠席を戻す.
           ++this.totalAbsenteeCount;
        }
    }


    public int getTotalAttenteeCount(){return this.totalAttenteeCount;}
    public int getTotalAbsenteeCount(){return this.totalAbsenteeCount;}

}
