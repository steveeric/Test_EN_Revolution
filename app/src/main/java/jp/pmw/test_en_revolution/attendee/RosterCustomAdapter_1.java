package jp.pmw.test_en_revolution.attendee;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import jp.pmw.test_en_revolution.R;
import jp.pmw.test_en_revolution.confirm_class_plan.AttendanceState;
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
        ViewHolder holder = null;
        View view;
        if (convertView == null) {
            view = _inflater.inflate(R.layout.row_roster_item_1, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            view = convertView;
            holder = (ViewHolder) view.getTag();
        }
        //出欠席者の情報
        Student student = _items.get(position);
        AttendanceState attendance = student.getThisClassTime().getThisClassAttendanceState();

        /*出席に関して*/
        int attendanceState = attendance.getTempAttendanceState();
        int attendColor = -1;

        /*if(attendanceState == 0){
            if(attendance.getConfirmTime() != null){
                //正常に出席完了
                attendColor = this.getContext().getApplicationContext().getResources().getColor(R.color.semitransparenttranslucent_green);
            }else if(attendance.getRequestForgotESLTime() != null){
                attendColor = this.getContext().getApplicationContext().getResources().getColor(R.color.semitransparenttranslucent_green);
            }else{
                //ACK取れなかった
                //欠席？
                attendColor = this.getContext().getApplicationContext().getResources().getColor(R.color.semitransparenttranslucent_darkRed);
            }
        }else{
            //仮出席状態なので、グレー
            attendColor = this.getContext().getApplicationContext().getResources().getColor(R.color.semitransparenttranslucent_gray);
        }*/

        if(attendance.getTempAttendanceState() == 1
                &&attendance.getConfirmTime() == null
                && attendance.getRequestForgotESLTime() == null){
            //仮出席状態なので、グレー
            attendColor = this.getContext().getApplicationContext().getResources().getColor(R.color.semitransparenttranslucent_gray);
        }else{
            if(attendance.getTempAttendanceState() == 0
                    &&attendance.getConfirmTime() == null
                    && attendance.getRequestForgotESLTime() == null){
                //ACK取れなかった
                //欠席？
                attendColor = this.getContext().getApplicationContext().getResources().getColor(R.color.semitransparenttranslucent_darkRed);
            }else{
                //正常に出席完了
                attendColor = this.getContext().getApplicationContext().getResources().getColor(R.color.semitransparenttranslucent_green);
            }
        }

        holder.orijinalStudentIdTextView.setText(student.getOriginalstudentId());
        holder.fullNameTextView.setText(student.getFullName());
        //((TextView) view.findViewById(R.id.roster_orijinal_student_id_textView)).setTextColor(attendColor);
        //((TextView) view.findViewById(R.id.roster_full_name_textView)).setTextColor(attendColor);
        holder.attendanceStateColorTextView.setBackgroundColor(attendColor);


        /*ESL忘れ*/
        if(student.getThisClassTime().getThisClassAttendanceState().getRequestForgotESLTime() != null){
            holder.forgotESLTextView.setVisibility(View.VISIBLE);
        }else{
            //忘れてない
            holder.forgotESLTextView.setVisibility(View.INVISIBLE);
        }

        /*メッセージあり*/
        if(student.getMessage()!=null){
            holder.messageTextView.setVisibility(View.VISIBLE);
        }else{
            holder.messageTextView.setVisibility(View.INVISIBLE);
        }

        return view;
    }

    private static class ViewHolder {
        TextView orijinalStudentIdTextView;
        TextView fullNameTextView;
        TextView attendanceStateColorTextView;
        TextView forgotESLTextView;
        TextView messageTextView;
        public ViewHolder(View view) {
            this.orijinalStudentIdTextView = (TextView) view.findViewById(R.id.roster_orijinal_student_id_textView);
            this.fullNameTextView = (TextView) view.findViewById(R.id.roster_full_name_textView);
            this.attendanceStateColorTextView = (TextView) view.findViewById(R.id.roster_attendance_state_color_textView);
            this.forgotESLTextView = (TextView) view.findViewById(R.id.roster_forgot_esl_textView);
            this.messageTextView = (TextView) view.findViewById(R.id.roster_have_messge_textView);
        }
    }
}