package jp.pmw.test_en_revolution;

import android.os.Handler;
import android.widget.GridView;
import android.widget.Toast;

import java.util.List;
import java.util.Random;
import java.util.TimerTask;

import jp.pmw.test_en_revolution.attendee.RosterCustomAdapter_1;
import jp.pmw.test_en_revolution.confirm_class_plan.Student;
import jp.pmw.test_en_revolution.room.RoomView;

/**
 * Created by scr on 2015/01/07.
 */
public class DemoReCollTheRollTimer extends TimerTask {
    public static final int MODE_MAINACTIVITY = 0;
    public static final int MODE_SEATSITUATION_FRAGMENT = 1;
    public static final int MODE_ATTENDEE_FRAGMENT = 2;

    private int mode;
    private List<Student> students;
    private RoomView roomview;
    private MainActivity activity;
    //private SeatSituationFragment fragment;
    //private AttendeeFragment attendeeFragment;
    private Handler handler;
    private GridView gv;
    private RosterCustomAdapter_1 adapter;

    DemoReCollTheRollTimer(MainActivity activity,int mode,List<Student> teacher/*,RoomView rv*//*,SeatSituationFragment rv*/){
        this.activity = activity;
        this.mode = mode;
        this.students = teacher;
        showToast(activity);
    }

    private void showToast(MainActivity activity){
        String str = "";
        if(MODE_MAINACTIVITY == mode){
            str = "出席調査中です...";
        }else{
            str = "再出席調査を開始します.";
        }
        Toast.makeText(activity, str, Toast.LENGTH_LONG).show();
    }

   /* public void setMainActivity(MainActivity ac){
        this.activity = ac;
        showToast(ac);
    }*/

    /*public void setSeatSituationFragment(SeatSituationFragment flag){
        this.fragment = flag;
        MainActivity ac = (MainActivity)this.fragment.getActivity();
        showToast(ac);
    }*/
    public void setAttendeeFragment(Handler handler,GridView gv,RosterCustomAdapter_1 adapter){
        //this.attendeeFragment = flag;
        this.handler = handler;
        this.gv = gv;
        this.adapter = adapter;
        //MainActivity ac = (MainActivity)this.attendeeFragment.getActivity();
        //showToast(ac);
    }


    Random rnd = new Random();


    @Override
    public void run() {
        int counter = 0;
        //学生全員取得
        //List<Student> students = students.getRoster().getRosterList();
        for(int i = 0; i < students.size(); i++){
            Student st = students.get(i);

            /*if(st.getThisClassTime().getThisClassAttendanceState().getTempAttendanceState() == 0
                    || st.getThisClassTime().getThisClassAttendanceState().getRequestForgotESLTime() !=null){
                ++counter;
            }

            if(st.getThisClassTime().getThisClassAttendanceState().getTempAttendanceState() == 1
                    && st.getThisClassTime().getThisClassAttendanceState().getRequestForgotESLTime() == null){
                //仮出席状態
                int ran = rnd.nextInt(10);
                st.getThisClassTime().getThisClassAttendanceState().setTempAttendanceState(0);
                if(ran < 9) {
                    st.getThisClassTime().getThisClassAttendanceState().setConfirmTime(" ");
                }else{
                    //欠席扱い
                    st.getThisClassTime().getThisClassAttendanceState().setConfirmTime(null);
                }
                break;
            }
        }

        if(mode == MODE_ATTENDEE_FRAGMENT){
            handler.post(new Runnable() {
                @Override
                public void run() {
                    adapter.notifyDataSetChanged();
                    gv.invalidate();
                }
            });
        }

        if(students.size() == counter){
            activity.mTeacher.setEndAttendanceFlag(true);
            activity.demoCallTheRollCancel();

           /* if(mode == MODE_MAINACTIVITY){
                activity.mTeacher.setEndAttendanceFlag(true);
                activity.demoCallTheRollCancel();
            }else if(mode == MODE_SEATSITUATION_FRAGMENT) {
                //MainActivity ac = (MainActivity) this.fragment.getActivity();
                //boolean deadOrAliveFrag = checkDeadOrAliveFragment(ac);
                //送信終了をセットする.
                activity.mTeacher.setEndAttendanceFlag(true);
                //Toast.makeText(activity, "再出席調査が終了しました.", Toast.LENGTH_LONG).show();
                activity.showEndCallTheRollAlertDialog();
                fragment.demoClancel();
            }else if(mode == MODE_ATTENDEE_FRAGMENT){
                MainActivity ac = (MainActivity) this.attendeeFragment.getActivity();
                boolean deadOrAliveFrag = checkDeadOrAliveFragment(ac);
                if(deadOrAliveFrag == true) {
                    ac.mTeacher.setEndAttendanceFlag(true);
                    ac.showEndCallTheRollAlertDialog();
                    attendeeFragment.demoClancel();
                }else{
                    activity.mTeacher.setEndAttendanceFlag(true);
                    activity.demoCallTheRollCancel();
                }
            }*/
        }
    }

    /*private boolean checkDeadOrAliveFragment(MainActivity ac){
        boolean b = false;
        if(ac != null){
            b = true;
        }
        return b;
    }*/

}
