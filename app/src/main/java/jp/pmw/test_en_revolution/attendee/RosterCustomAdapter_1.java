package jp.pmw.test_en_revolution.attendee;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import jp.pmw.test_en_revolution.R;
import jp.pmw.test_en_revolution.config.Config;
import jp.pmw.test_en_revolution.confirm_class_plan.Attendance;
import jp.pmw.test_en_revolution.confirm_class_plan.Student;

/**
 * Created by scr on 2014/12/25.
 */
public class RosterCustomAdapter_1 extends ArrayAdapter<Student> {

    private Context _context;
    private int _textViewResourceId;
    private LayoutInflater _inflater;

    public static final int ALL_LAYOUT = 0;
    public static final int ONLY_STUDENT_NAME_LAYOUT = 1;

    /**
     * RosterCustomAdapter_1は、2か所のフラグメントが使用します.
     * requestNumberが
     * 0 : ESL忘れやメッセージレイアウトあり
     * 1 : 学籍番号と名前だけレイアウト
     * **/
    private int _requestNumber;

    public RosterCustomAdapter_1(Context context, int resourceId, List<Student> items,int requestNumber) {
        super(context, resourceId, items);
        _context = context;
        _textViewResourceId = resourceId;
        _inflater = (LayoutInflater) _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        _requestNumber = requestNumber;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        View view;

        //出欠席者の情報
        Student student = getItem(position);
        //出席情報クラス
        Attendance att = student.getAttendance();

        if(att.getStatus() == Config.ALREADY_ATTENDANCE){
            view = _inflater.inflate(R.layout.row_roster_item_2, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }else if(att.getStatus() == Config.FOGOT_ESL_APPLY) {
            view = _inflater.inflate(R.layout.row_roster_item_2, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }else{
            view = _inflater.inflate(R.layout.row_roster_item_1, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }





        /*リセット*/
        //副コンテンツレイアウトを消す
        /*holder.subContentLinearLayout.setVisibility(View.GONE);
        holder.forgotESLTextView.setVisibility(View.GONE);
        holder.messageTextView.setVisibility(View.GONE);*/

        holder.subContentLinearLayout.setVisibility(View.INVISIBLE);
        holder.forgotESLTextView.setVisibility(View.INVISIBLE);
        holder.messageTextView.setVisibility(View.INVISIBLE);

        //
        //holder.attendanceStateColorTextView.setVisibility(View.VISIBLE);
        holder.attendanceStateColorTextView.setVisibility(View.GONE);


        int attendColor = 0;
        if (att.getStatus() == Config.DO_NOT_SEND_INFRARED) {
            //仮出席状態なので、グレー
            attendColor = this.getContext().getApplicationContext().getResources().getColor(R.color.semitransparenttranslucent_gray);
        } else {
            if (att.getStatus() == Config.ALREADY_ATTENDANCE) {
                //正常に出席完了
                //attendColor = this.getContext().getApplicationContext().getResources().getColor(R.color.semitransparenttranslucent_green);
                //holder.attendanceStateColorTextView.setVisibility(View.INVISIBLE);

                //  2016/01/28 10:05
                attendColor = this.getContext().getApplicationContext().getResources().getColor(R.color.blue);
            } else if (att.getStatus() == Config.FOGOT_ESL_APPLY) {
                //ESL忘れで出席
                //attendColor = this.getContext().getApplicationContext().getResources().getColor(R.color.semitransparenttranslucent_green);
                //holder.subContentLinearLayout.setVisibility(View.VISIBLE);
                holder.forgotESLTextView.setVisibility(View.VISIBLE);

                //  2016/01/28 10:05
                attendColor = this.getContext().getApplicationContext().getResources().getColor(R.color.blue);

            } else {
                //ACK取れなかった
                //欠席？
                //attendColor = this.getContext().getApplicationContext().getResources().getColor(R.color.semitransparenttranslucent_darkRed);

                //  2016/01/28 10:05
                attendColor = this.getContext().getApplicationContext().getResources().getColor(R.color.red);

            }
        }
        holder.orijinalStudentIdTextView.setText(student.getOriginalstudentId());
        holder.fullNameTextView.setText(student.getFullName());
        holder.attendanceStateColorTextView.setBackgroundColor(attendColor);

        holder.orijinalStudentIdTextView.setTextColor(attendColor);
        holder.fullNameTextView.setTextColor(attendColor);
        holder.fullNameTextView.setTextColor(attendColor);

        return view;
    }

    private static class ViewHolder {
        TextView orijinalStudentIdTextView;
        TextView fullNameTextView;
        TextView attendanceStateColorTextView;
        LinearLayout subContentLinearLayout;
        TextView forgotESLTextView;
        TextView messageTextView;
        public ViewHolder(View view) {
            this.orijinalStudentIdTextView = (TextView) view.findViewById(R.id.roster_orijinal_student_id_textView);
            this.fullNameTextView = (TextView) view.findViewById(R.id.roster_full_name_textView);
            this.attendanceStateColorTextView = (TextView) view.findViewById(R.id.roster_attendance_state_color_textView);
            this.subContentLinearLayout = (LinearLayout) view.findViewById(R.id.roster_attendance_sub_content_linearLayout);
            this.forgotESLTextView = (TextView) view.findViewById(R.id.roster_forgot_esl_textView);
            this.messageTextView = (TextView) view.findViewById(R.id.roster_have_messge_textView);
        }
    }
}