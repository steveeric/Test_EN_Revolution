package jp.pmw.test_en_revolution.confirm_class_plan;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by scr on 2014/12/24.
 * 受講者を管理するクラスです.
 */
public class Roster implements Serializable {
    //受講者一覧です.
    private List<Student> rosterList;
    public Roster(List<Student> roters){
        this.rosterList = roters;
    }
    public List<Student> getRosterList(){
        return this.rosterList;
    }

    /**
     * Created by Shota Ito on 2015/1/6.
     * getTodayAttendeeCountメソッド
     * 本日の出席者総数をかえします.
     * @return int 出席者数
     */
    public int getTodayAttendeeCount(){
        int attendeeCount = 0;
        for (int i = 0; i < this.rosterList.size(); i++) {
            AttendanceState state = this.rosterList.get(i).getThisClassTime().getThisClassAttendanceState();
            if(state.getRequestForgotESLTime() != null){
                //ESL忘れによる出席
                ++attendeeCount;
            }else if(state.getConfirmTime() != null){
                //ACk受信による出席
                ++attendeeCount;
            }
        }
        return attendeeCount;
    }
    /**
     * Created by Shota Ito on 2015/1/6.
     * getTodayAttendeeメソッド
     * 本日の出席者総数をかえします.
     * @return List<StudentT> 出席者
     */
    public List<Student> getTodayAttendee(){
        List<Student> attendee = new ArrayList<Student>();
        for (int i = 0; i < this.rosterList.size(); i++) {
            AttendanceState state = this.rosterList.get(i).getThisClassTime().getThisClassAttendanceState();
            if(state.getRequestForgotESLTime() != null && state.getConfirmTime() == null){
                attendee.add(this.rosterList.get(i));
            }else if(state.getRequestForgotESLTime() == null && state.getConfirmTime() != null){
                //ACk受信による出席
                attendee.add(this.rosterList.get(i));
            }
        }
        return attendee;
    }

}
