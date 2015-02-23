package jp.pmw.test_en_revolution.history;

/**
 * Created by scr on 2015/01/07.
 */
public class History {
    //授業回数
    private int classRoomCount;
    //授業日
    private String classRoomDate;
    //出席状態
    private int attendanceState;
    //ESL忘れe
    private int forgotESL;

    History(int classRoomCount,String classRoomDate,
            int attendanceState,int forgotESL){
        this.classRoomCount = classRoomCount;
        this.classRoomDate = classRoomDate;
        this.attendanceState = attendanceState;
        this.forgotESL = forgotESL;
    }

    public int getClassRoomCount(){
        return this.classRoomCount;
    }
    public String getClassRoomDate(){
        return this.classRoomDate;
    }
    public int getAttendanceState(){
        return this.attendanceState;
    }
    public int getForgotESL(){
        return this.forgotESL;
    }

}
