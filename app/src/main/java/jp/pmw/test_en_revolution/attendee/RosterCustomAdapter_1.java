package jp.pmw.test_en_revolution.attendee;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import jp.pmw.test_en_revolution.R;
import jp.pmw.test_en_revolution.confirm_class_plan.Attendance;
import jp.pmw.test_en_revolution.confirm_class_plan.Student;

/**
 * Created by scr on 2014/12/25.
 */
public class RosterCustomAdapter_1 extends ArrayAdapter<Student> {

    private Context _context;
    private int _textViewResourceId;
    private List<Student> _items;
    private LayoutInflater _inflater;

    public RosterCustomAdapter_1(Context context, int resourceId, List<Student> items) {
        super(context, resourceId, items);
        _context = context;
        _textViewResourceId = resourceId;
        _items = items;
        _inflater = (LayoutInflater) _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        if (convertView != null) {
            view = convertView;
        } else {
            view = _inflater.inflate(R.layout.row_roster_item_1, null);
        }
        //出欠席者の情報
        Student student = _items.get(position);
        Attendance attendance = student.getAttendance();

        /*出席に関して*/
        int attendanceState = attendance.getTempAttendanceState();
        int attendColor = -1;
        if(attendanceState == 0){
            if(attendance.getConfirmTime() != null){
                //正常に出席完了
                attendColor = this.getContext().getApplicationContext().getResources().getColor(R.color.green);
            }else{
                //ACK取れなかった
                //欠席？
                attendColor = this.getContext().getApplicationContext().getResources().getColor(R.color.red);
            }
        }else{
            //仮出席状態なので、グレー
            attendColor = this.getContext().getApplicationContext().getResources().getColor(R.color.gray);
        }
        ((TextView) view.findViewById(R.id.roster_orijinal_student_id_textView)).setText(student.getOriginalstudentId());
        ((TextView) view.findViewById(R.id.roster_full_name_textView)).setText(student.getFullName());
        ((TextView) view.findViewById(R.id.roster_orijinal_student_id_textView)).setTextColor(attendColor);
        ((TextView) view.findViewById(R.id.roster_full_name_textView)).setTextColor(attendColor);

        /*メッセージあり*/
        if(student.getMessage()!=null){
            ((TextView) view.findViewById(R.id.roster_have_messge_textView)).setVisibility(View.VISIBLE);
        }
        /*ESL忘れ*/
        if(student.getAttendance().getRequestForgotESLTime()!=null){
            ((TextView) view.findViewById(R.id.roster_forgot_esl_textView)).setVisibility(View.VISIBLE);
        }

        return view;
    }
}