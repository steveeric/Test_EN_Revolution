package jp.pmw.test_en_revolution.confirm_class_plan;


import jp.pmw.test_en_revolution.grouping.Group;

/**
 * Created by scr on 2015/01/03.
 */
public class ThisClassTime {
    public static final int UNCONFIRMED_ATTENDANCE_STATE = 0;
    public static final int ABSENTEE_ATTENDANCE_STATE = 3;
    public static final int ATTENDEE_ATTENDANCE_STATE = 1;
    public static final int FORGOT_APPLY_STATE = 2;

    private String thisClassSittingPositionId;
    private AttendanceState thisClassAttendanceState;
    private Seat thisClassSittingPosition;
    private Group thisClassGroup;

    public ThisClassTime(String thisClassSittingPositionId,AttendanceState thisClassAttendanceState){
        this.thisClassSittingPositionId = thisClassSittingPositionId;
        this.thisClassAttendanceState = thisClassAttendanceState;
    }

    public void setThisClassSittingPositionId(String seatId){
        this.thisClassSittingPositionId = seatId;
    }

    public void setThisClassGroup(Group group){
        this.thisClassGroup = group;
    }

    public void setThisClassSittingPosition(Seat seat){
        this.thisClassSittingPosition = null;
        this.thisClassSittingPosition = seat;
    }
    /**
     * getAttendanceStateメソッド
     * この授業での出席状態を返します.
     * @return 0 まだ確認をとっていない学生
     * @return 1 欠席学生
     * @return 2 出席学生
     */
    public int getAttendanceState(){
        int state = 0;
        if(getThisClassAttendanceState().getTempAttendanceState() == 1){
            if(getThisClassAttendanceState().getRequestForgotESLTime() == null
                    && getThisClassAttendanceState().getConfirmTime() == null){
                //不明
                state = UNCONFIRMED_ATTENDANCE_STATE;
            }else if(getThisClassAttendanceState().getRequestForgotESLTime() != null
                    && getThisClassAttendanceState().getConfirmTime() == null){
                //ESL忘れで仮出席状態
                state = ATTENDEE_ATTENDANCE_STATE;
            }
        }else{
            if(getThisClassAttendanceState().getConfirmTime() != null){
                //出席
                state = ATTENDEE_ATTENDANCE_STATE;
            }else if(getThisClassAttendanceState().getRequestForgotESLTime() != null){
                //ESL忘れで出席
                state = ATTENDEE_ATTENDANCE_STATE;
            }else{
                //欠席
                state = ABSENTEE_ATTENDANCE_STATE;
            }
        }
        /*
        if(getThisClassAttendanceState().getTempAttendanceState() == 1
                && getThisClassAttendanceState().getRequestForgotESLTime() == null
                && getThisClassAttendanceState().getConfirmTime() == null){
            state = UNCONFIRMED_ATTENDANCE_STATE;
        }else if(getThisClassAttendanceState().getTempAttendanceState() == 0
                && getThisClassAttendanceState().getRequestForgotESLTime() == null
                && getThisClassAttendanceState().getConfirmTime() == null ){
            state = this.ABSENTEE_ATTENDANCE_STATE;
        }else if(getThisClassAttendanceState().getTempAttendanceState() == 0
                && getThisClassAttendanceState().getRequestForgotESLTime() != null
                getThisClassAttendanceState().getConfirmTime() != null){
            state = this.ATTENDEE_ATTENDANCE_STATE;
        }
        */
        return state;
    }

    public String getThesClassSittingPositionId(){
        return this.thisClassSittingPositionId;
    }
    public AttendanceState getThisClassAttendanceState(){
        return this.thisClassAttendanceState;
    }
    public Seat getThisClassSittingPosition(){
        return this.thisClassSittingPosition;
    }
    public Group getThisClassGroup(){return this.thisClassGroup;}

}
