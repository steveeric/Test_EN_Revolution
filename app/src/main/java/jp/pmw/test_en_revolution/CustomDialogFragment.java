package jp.pmw.test_en_revolution;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import jp.pmw.test_en_revolution.confirm_class_plan.AttendanceState;
import jp.pmw.test_en_revolution.confirm_class_plan.Message;
import jp.pmw.test_en_revolution.confirm_class_plan.Seat;
import jp.pmw.test_en_revolution.confirm_class_plan.Student;
import jp.pmw.test_en_revolution.confirm_class_plan.TotalAttendance;

/**
 * Created by scr on 2015/01/02.
 */
public class CustomDialogFragment extends DialogFragment{
    public static final String ATTENDANCE_STUDENT_INFO = "attendance_student_info";

    //タップされた出席者の情報
    private static Student attendanceStudent;

    /**
     * ファクトリーメソッド
     */
    public static CustomDialogFragment newInstance(/*String title, String message, int type*/){
        CustomDialogFragment instance = new CustomDialogFragment();
       return instance;
    }

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        attendanceStudent = (Student)getArguments().getSerializable(ATTENDANCE_STUDENT_INFO);

        Dialog dialog = new Dialog(getActivity());
        // タイトル非表示
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        // フルスクリーン
        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        dialog.setContentView(R.layout.dialog_custom);
        // 背景を透明にする
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        //大学独自のオリジナル学籍番号をセットする.
        TextView orijinalStudentIdTextView = (TextView)dialog.findViewById(R.id.title_orijinal_student_id_textView);
        orijinalStudentIdTextView.setText(this.attendanceStudent.getOriginalstudentId());
        //学生名をセットする.
        TextView fullNameTextview = (TextView)dialog.findViewById(R.id.title_full_name_textView);
        fullNameTextview.setText(this.attendanceStudent.getFullName());

        //出欠カラーを取得
        int mainFrameResorceColor = getFrameColorResorce();

        //フレーム全体の色を選択
        setFrameBackgoundColor(dialog,mainFrameResorceColor);
        //着座していセット
        setThisClassTimeSittingPosition(dialog);
        //総出席状況セット
        setTotalAttendanceSituation(dialog);
        //メッセージセット
        setMessage(dialog);
        //ESL忘れセット＆出席確認時刻セット
        setForgotESL(dialog);
        //出席確認時間セット
        //setConfirmTodayAttendanceTime(dialog);

        // 閉じるボタン ボタンのリスナ
        Button closeBtn = (Button)dialog.findViewById(R.id.positive_button);
        closeBtn.setBackgroundColor(mainFrameResorceColor);
        closeBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        return dialog;
    }
    /**
     * Created by scr on 2015/1/3.
     * getAttendanceStateメソッド
     * 学生の今回の授業の出席状態を返します.
     * @return AttendanceState タップされた今回の授業出席状態
     */
    private AttendanceState getAttendanceState(){
        return this.attendanceStudent.getThisClassTime().getThisClassAttendanceState();
    }
    /**
     * Created by scr on 2015/1/3.
     * getFrameColorResorceメソッド
     * 本日の出席状態カラーを取得します.
     * @return int 本日の出席カラー
     */
    private int getFrameColorResorce(){
        int resorce = this.getResources().getColor(R.color.gray);
        AttendanceState todayAtt = getAttendanceState();
        if(todayAtt.getTempAttendanceState() == 1){
            //グレー
        }else{
            //緑か赤
            if(todayAtt.getConfirmTime() != null){
                //出席者
                resorce = this.getResources().getColor(R.color.yellowGreen);
            }else{
                //欠席者
                resorce = this.getResources().getColor(R.color.darkRed);
            }
        }
        return resorce;
    }
    /**
     * Created by scr on 2015/1/3.
     * setThisClassTimeSittingPositionメソッド
     * 本日の着席位置を表示します..
     * @param d ダイアログ
     */
    private void setThisClassTimeSittingPosition(Dialog d){
        LinearLayout sittingPosition = getLinearLayout(d,R.id.today_seat_positon_linearLayout);
        Seat thisClassSitting = this.attendanceStudent.getThisClassTime().getThisClassSittingPosition();
        if(thisClassSitting == null){
            //着座指定なし
            sittingPosition.setVisibility(View.GONE);
        }else{
            //着座指定あり
            sittingPosition.setVisibility(View.VISIBLE);
            String seatPosition = thisClassSitting.getRow() + this.getString(R.string.seat_number_separetar) + thisClassSitting.getColumn();
            TextView tv = getTextView(d,R.id.today_seat_positon_textView);
            tv.setText(seatPosition);
        }
    }

    /**
     * Created by scr on 2015/1/3.
     * setFrameBackgoundColorメソッド
     * 本日の出席状態カラーを取得します.
     * @param d ダイアログ
     * @param resorce 出席カラーのリソース
     */
    private void setFrameBackgoundColor(Dialog d,int resorce){
        LinearLayout outerFrameLayout = getLinearLayout(d,R.id.linearLayout);
        LinearLayout titleLayout = getLinearLayout(d,R.id.title_linearLayout);
        //outerFrameLayout.setBackgroundColor(resorce);
        titleLayout.setBackgroundColor(resorce);
    }

    private void setTodaySeatPosition(Dialog d){
        /*if(){

        }else{

        }*/
    }
    /**
     * Created by scr on 2015/1/2.
     * setTotalAttendanceSituationメソッド
     * 総出席状況を表示するために使用します.
     * @param d ダイアログのインスタンス
     */
    private void setTotalAttendanceSituation(Dialog d){
        LinearLayout layout = getLinearLayout(d,R.id.total_attendance_situation_linearLayout);
        if(this.attendanceStudent.getTotalAttendance()==null){
            //総出欠席なし
            //レイアウト非表示
            layout.setVisibility(View.GONE);
        }else{
            TotalAttendance total = this.attendanceStudent.getTotalAttendance();
            //総出欠席あり
            TextView attendeeTV = getTextView(d,R.id.total_attendance_attendee_situation_textView);
            TextView absenteeTV = getTextView(d,R.id.total_attendance_absentee_situation_textView);

            String str = "";
            //出席
            str = this.getString(R.string.total_attendee)+total.getTotalAttenteeCount();
            attendeeTV.setText(str);
            //欠席
            if(total.getTotalAbsenteeCount()!=0){
                //一回でも欠席をしたことがある.
                str = this.getString(R.string.total_absentee)+total.getTotalAbsenteeCount();
            }else{
                //一回も欠席をしたことがない.
                str = this.getString(R.string.total_no_absentee);
            }
            absenteeTV.setText(str);
        }
    }
    /**
     * Created by scr on 2015/1/2.
     * setMessageメソッド
     * メッセージを表示するために使用します.
     * @param d ダイアログのインスタンス
     */
    private void setMessage(Dialog d){
        LinearLayout layout = getLinearLayout(d,R.id.message_linearLayout);
        if(this.attendanceStudent.getMessage()==null){
            //メッセージなし.
            layout.setVisibility(View.GONE);
        }else{
            //メッセージあり.
            Message message = this.attendanceStudent.getMessage();
            TextView simpleMessageTV = getTextView(d,R.id.simple_message_textView);
            simpleMessageTV.setText(message.getSimpleMessage());
            layout.setVisibility(View.VISIBLE);
        }
    }
    /**
     * Created by scr on 2015/1/2.
     * setForgotESLメソッド
     * ESL忘れを状況を表示します.
     * @param d ダイアログのインスタンス
     */
    private void setForgotESL(Dialog d){
        //ESL忘れレイアウト
        LinearLayout layout = getLinearLayout(d,R.id.forgot_esl_linearLayout);
        AttendanceState attState = this.attendanceStudent.getThisClassTime().getThisClassAttendanceState();
        if(attState.getTempAttendanceState() == 0
                &&attState.getRequestForgotESLTime() == null
                &&attState.getConfirmTime() != null){
            //ACKですでに出席済み.
            layout.setVisibility(View.GONE);
        }else{
            //ESL忘れレイアウト
            layout.setVisibility(View.VISIBLE);
            Button releaseBtn = getButton(d,R.id.dialog_custom_forgot_esl_release_button);
            Button requestBtn = getButton(d,R.id.dialog_custom_forgot_esl_button);
            
            if(attState.getRequestForgotESLTime() != null){
                //ESL忘れ申請をすでに行っているので
                //解除ボタンを出す.
                releaseBtn.setVisibility(View.VISIBLE);
                requestBtn.setVisibility(View.INVISIBLE);
            }else{
                //ESL忘れ申請を行っていない.
                releaseBtn.setVisibility(View.INVISIBLE);
                requestBtn.setVisibility(View.VISIBLE);
            }
        }
    }
    /**
     * Created by scr on 2015/1/2.
     * setConfirmTodayAttendanceTimeメソッド
     * 出席確認時間.
     * @param d ダイアログのインスタンス
     */
    private void setConfirmTodayAttendanceTime(Dialog d){
        //出席時刻確認レイアウト
        LinearLayout confirmLayout = this.getLinearLayout(d,R.id.attendee_confirm_time_linearLayout);
        AttendanceState thisClassAtt = getAttendanceState();
        if(thisClassAtt.getTempAttendanceState() == 0
                &&thisClassAtt.getRequestForgotESLTime()==null
                &&thisClassAtt.getConfirmTime() != null){
            //出席時刻確認レイアウト表示
            TextView confirmTV = this.getTextView(d,R.id.attendee_confirm_time_textViewt);
            confirmTV.setText(thisClassAtt.getConfirmTime());
            confirmLayout.setVisibility(View.VISIBLE);
        }else{
            //出席時刻確認レイアウト非表示
            confirmLayout.setVisibility(View.GONE);
        }
    }

    private LinearLayout getLinearLayout(Dialog d,int resorce){
        return (LinearLayout)d.findViewById(resorce);
    }
    private TextView getTextView(Dialog d,int resorce){
        return (TextView)d.findViewById(resorce);
    }
    private Button getButton(Dialog d,int resorce){
        return (Button)d.findViewById(resorce);
    }
}