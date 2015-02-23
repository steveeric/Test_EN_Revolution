package jp.pmw.test_en_revolution.history;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import jp.pmw.test_en_revolution.R;

/**
 * Created by scr on 2015/01/07.
 */
public class HistoryLayout extends LinearLayout {
    // 授業回数
    private TextView numberCount;
    // 授業日
    private TextView date;
    // 出席状態
    private TextView attendance;
    // ESL忘れ状態
    private TextView forgotesl;


    public HistoryLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        this.numberCount = getTextView(R.id.row_history_item_class_room_colunt_textView);
        this.date = getTextView(R.id.row_history_item_class_room_date_textView);
        this.attendance = getTextView(R.id.row_history_item_class_room_attendance_state_textView);
        this.forgotesl = getTextView(R.id.row_history_item_class_room_forgot_esl_state_textView);
    }

    private TextView getTextView(int resorce){
        return (TextView) findViewById(resorce);
    }

    /***
     *
     ***/
    public void bindView(History h) {
        String classRoomCount = h.getClassRoomCount() + this.getResources().getString(R.string.history_dialog_frament_class_room_count);

        this.numberCount.setText(classRoomCount);
        this.date.setText(h.getClassRoomDate());

        String attendanceState = "";
        if(h.getAttendanceState() == 1){
            //出席
            attendanceState = this.getResources().getString(R.string.history_dialog_frament_attendee_state);
        }else{
            //欠席
            attendanceState = this.getResources().getString(R.string.history_dialog_frament_absentee_state);
        }
        this.attendance.setText(attendanceState);

        if(h.getForgotESL() == 1) {
            String str = this.getResources().getString(R.string.history_dialog_frament_forgot_esl);
            this.forgotesl.setText(str);
        }else{
            this.forgotesl.setVisibility(View.INVISIBLE);
        }
    }

}
