package jp.pmw.test_en_revolution.confirm_class_plan;

/**
 * Created by scr on 2015/01/03.
 */
public class ThisClassTime {
    private String thisClassSittingPositionId;
    private AttendanceState thisClassAttendanceState;
    private Seat thisClassSittingPosition;

    public ThisClassTime(String thisClassSittingPositionId,AttendanceState thisClassAttendanceState){
        this.thisClassSittingPositionId = thisClassSittingPositionId;
        this.thisClassAttendanceState = thisClassAttendanceState;
    }

    public void setThisClassSittingPosition(Seat seat){
        this.thisClassSittingPosition = seat;
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

}
