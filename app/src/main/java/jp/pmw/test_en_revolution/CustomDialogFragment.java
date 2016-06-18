package jp.pmw.test_en_revolution;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import org.json.JSONObject;

import jp.pmw.test_en_revolution.config.URL;
import jp.pmw.test_en_revolution.history.HistoryDialogFragment;

/**
 * Created by scr on 2015/01/02.
 */
public class CustomDialogFragment extends DialogFragment{

    public static final int PARAMETA_FORGOT_ESL = 1;
    public static final int PARAMETA_RELEASE_FORGOT = 0;

    public static final int ATTENDEE = 1;
    public static final int FORGOT_ESL = 2;
    public static final int ABSENTEE = 3;

    public static final int MANUAL_REQUEST = 1;
    public static final int MANUAL_NOT_REQUEST = 0;

    private static final int MANUAL_REQUEST_TYPE_FORGOT_ESL = 1;
    private static final int MANUAL_REQUEST_TYPE_ATTENDANCE = 2;
    private static final int MANUAL_REQUEST_TYPE_RE_ATTENDANCE = 3;
    private static final int MANUAL_REQUEST_TYPE_UNDO = 4;


    //
    public static final String ATTENDANCE_STUDENT_INFO = "attendance_student_info";
    //
    public static final String TRANSMIT_STATE_INFO = "tranmit_state_info";
    //
    public static final String SAME_CLASS_NUMBER = "same_class_number";
    //個人履歴を確認するボタン
    private ImageButton historyImageBtn;
    //ESL忘れ申請ボタン
    private Button releaseBtn;
    //ESL忘れ解除ボタン
    private Button requestBtn;
    //  手動申請解除ボタン
    private Button manualRequestBtn;
    public void setManualRequestBtn(Dialog d, int resorce){
        this.manualRequestBtn = getButton(d, resorce);
    }
    //  手動申要求請ボタン
    private Button manualReleaseBtn;
    public void setManualReleaseBtn(Dialog d, int resorce){
        this.manualReleaseBtn = getButton(d, resorce);
    }
    //とじるボタン
    private Button closeBtn;
    //タップされた出席者の情報
    //private Student attendanceStudent;
    private StudentObject tapStudent;
    //同一授業識別番号
    private String sameClassNumber;

    private AttendeeFragment attendeeFragment;
    //  送信オブジェクト
    private TransmitStateObject transmitStateObject;
    //  どのコンテンツをタップしたか
    private int selectRequestType = -1;
    //  適用タイプ(申請or解除)
    private int selectApplyType = -1;

    /**
     * ファクトリーメソッド
     */
    public static CustomDialogFragment newInstance(/*String title, String message, int type*/){
        CustomDialogFragment instance = new CustomDialogFragment();
       return instance;
    }

    /**
    * コールバック用のリスナー
    * **/
    private OnOkClickListener mListener;
    public interface OnOkClickListener {
        public void onOkClicked(Bundle args);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mListener = (OnOkClickListener) getTargetFragment();
        /*if (mListener instanceof OnOkClickListener == false) {
            throw new ClassCastException("実装エラー");
        }*/
    }

    public void setAttendeeFragment(AttendeeFragment af){
        this.attendeeFragment = af;
    }

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //
        sameClassNumber = getArguments().getString(SAME_CLASS_NUMBER);
        //
        //attendanceStudent = (Student)getArguments().getSerializable(ATTENDANCE_STUDENT_INFO);
        tapStudent          = (StudentObject)getArguments().getSerializable(ATTENDANCE_STUDENT_INFO);
        //
        transmitStateObject = (TransmitStateObject)getArguments().getSerializable(TRANSMIT_STATE_INFO);

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
        orijinalStudentIdTextView.setText(this.tapStudent.getStudentIdNumber());
        //学生名をセットする.
        TextView fullNameTextview = (TextView)dialog.findViewById(R.id.title_full_name_textView);
        fullNameTextview.setText(this.tapStudent.getFullName());

        //個人履歴を確認するボタン
        //this.historyImageBtn = (ImageButton)dialog.findViewById(R.id.dialog_custom_history_confirm_button);
        //this.historyImageBtn.setOnClickListener(historyConfirmBtnListener);

        //とじるボタン
        closeBtn = (Button)dialog.findViewById(R.id.positive_button);
        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        //出欠カラーを取得
        int mainFrameResorceColor = getFrameColorResorce();
        //フレーム全体の色を選択
        setFrameBackgoundColor(dialog, mainFrameResorceColor);
        //グループ情報
        //setThisClassGroupInfo(dialog);
        //着座していセット
        //setThisClassTimeSittingPosition(dialog);
        //総出席状況セット
        //setTotalAttendanceSituation(dialog);
        //メッセージセット
        //setMessage(dialog);
        //ESL忘れセット＆出席確認時刻セット
        setForgotESL(dialog);
        //  出席ACKが得られなかった学生用
        setAttend(dialog);
        //  出席再調査でACKが得られなかった学生用
        setReAttend(dialog);
        //  持ち出し用名札でACKが得られなかった学生用
        setUndo(dialog);
        //出席確認時間セット
        //setConfirmTodayAttendanceTime(dialog);
        return dialog;
    }


    private String getAttendanceId(){
        return this.tapStudent.getAttendanceObject().getAttendanceId();
    }



    /**
     * Created by scr on 2015/1/3.
     * getAttendanceStateメソッド
     * 学生の今回の授業の出席状態を返します.
     * @return AttendanceState タップされた今回の授業出席状態
     */
    /*private AttendanceState getAttendanceState(){
        return this.attendanceStudent.getThisClassTime().getThisClassAttendanceState();
    }*/

    /**
     * Created by scr on 2015/1/3.
     * getFrameColorResorceメソッド
     * 本日の出席状態カラーを取得します.
     * @return int 本日の出席カラー
     */
    private int getFrameColorResorce(){
        int resorce = this.getResources().getColor(R.color.gray);
        AttendanceObject att = this.tapStudent.getAttendanceObject();
        //AttendanceState todayAtt = getAttendanceState();
        /*if(todayAtt.getTempAttendanceState() == 1){
            //グレー
            //まだ出席未確認
        }else{
            //緑か赤
            if(todayAtt.getConfirmTime() != null || todayAtt.getRequestForgotESLTime() != null){
                //出席者
                resorce = this.getResources().getColor(R.color.yellowGreen);
            }else{
                //欠席者
                resorce = this.getResources().getColor(R.color.darkRed);
            }
        }*/
    /*
        if(todayAtt.getTempAttendanceState() == 1
                &&todayAtt.getConfirmTime() == null
                && todayAtt.getRequestForgotESLTime() == null){
            //未確認
            resorce = this.getResources().getColor(R.color.gray);
        }else{
           if(todayAtt.getTempAttendanceState() == 0
                   &&todayAtt.getConfirmTime() == null
                   && todayAtt.getRequestForgotESLTime() == null){
               //欠席
               resorce = this.getResources().getColor(R.color.darkRed);
           }else{
               //出席
               resorce = this.getResources().getColor(R.color.yellowGreen);
           }
        }
        */
        /*if(att.getStatus() == Config.DO_NOT_SEND_INFRARED){
            //未確認
            resorce = this.getResources().getColor(R.color.gray);
        }else {
            if (att.getStatus() == Config.ABSENT) {
                //欠席
                resorce = this.getResources().getColor(R.color.darkRed);
            } else {
                //出席
                resorce = this.getResources().getColor(R.color.yellowGreen);
            }
        }*/

        if(this.tapStudent.getAttendanceObject().getAttendanceTime() != null){
            //resorce = this.getResources().getColor(R.color.yellowGreen);
            resorce = this.getResources().getColor(R.color.blue);
        }else{
            if(this.transmitStateObject.getAttendanceTranmitEndTime() != null){
                resorce = this.getResources().getColor(R.color.darkRed);
            }else{
                resorce = this.getResources().getColor(R.color.gray);
            }
        }

        return resorce;
    }
    /**
     * Created by scr on 2015/1/6.
     * setThisClassGroupInfoメソッド
     * グループ番号を表示します.
     * @param d ダイアログ
     */
    /*private void setThisClassGroupInfo(Dialog d) {
        LinearLayout groupInfoLayout = getLinearLayout(d, R.id.today_group_info_linearLayout);
        Group group = this.attendanceStudent.getGroup();
        if(group == null){
            //グループ情報を表示しない.
            groupInfoLayout.setVisibility(View.GONE);
        }else{
            TextView gpnumberTV = getTextView(d,R.id.today_group_info_textView);
            gpnumberTV.setText(group.getGroupName());
            //
            groupInfoLayout.setVisibility(View.VISIBLE);
        }

    }*/

    /**
     * Created by scr on 2015/1/3.
     * setThisClassTimeSittingPositionメソッド
     * 本日の着席位置を表示します..
     * @param d ダイアログ
     */
    /*private void setThisClassTimeSittingPosition(Dialog d){
        LinearLayout sittingPosition = getLinearLayout(d, R.id.today_seat_positon_linearLayout);
        Seat thisClassSitting = this.attendanceStudent.getSitSeatInfo();
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
    }*/

    /**
     * Created by scr on 2015/1/3～2015/1/4.
     * setFrameBackgoundColorメソッド
     * 本日の出席状態カラーを取得します.
     * @param d ダイアログ
     * @param resorce 出席カラーのリソース
     */
    private void setFrameBackgoundColor(Dialog d,int resorce){
        LinearLayout outerFrameLayout = getLinearLayout(d,R.id.linearLayout);
        LinearLayout titleLayout = getLinearLayout(d, R.id.title_linearLayout);
        //outerFrameLayout.setBackgroundColor(resorce);
        titleLayout.setBackgroundColor(resorce);

        // 閉じるボタンのカラーをセットする.
        closeBtn.setBackgroundColor(resorce);
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
    /*private void setTotalAttendanceSituation(Dialog d){
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
            //出席回数
            str = this.getString(R.string.total_attendee) + total.getTotalAttenteeCount() + this.getString(R.string.number_of_times);
            attendeeTV.setText(str);
            str = "";
            //欠席回数
            if(total.getTotalAbsenteeCount()!=0){
                //一回でも欠席をしたことがある.
                str = this.getString(R.string.total_absentee) + total.getTotalAbsenteeCount() + this.getString(R.string.number_of_times);
            }else{
                //一回も欠席をしたことがない.
                str = this.getString(R.string.total_no_absentee);
            }
            absenteeTV.setText(str);
        }
    }*/
    /**
     * Created by scr on 2015/1/2.
     * setMessageメソッド
     * メッセージを表示するために使用します.
     * @param d ダイアログのインスタンス
     */
    /*private void setMessage(Dialog d){
        LinearLayout layout = getLinearLayout(d, R.id.message_linearLayout);
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
    }*/
    /**
     * Created by scr on 2015/1/2.
     * setForgotESLメソッド
     * ESL忘れを状況を表示します.
     * @param d ダイアログのインスタンス
     */
    private void setForgotESL(Dialog d){
        //ESL忘れレイアウト
        LinearLayout layout = getLinearLayout(d, R.id.forgot_esl_linearLayout);
        //AttendanceState attState = this.attendanceStudent.getThisClassTime().getThisClassAttendanceState();

        AttendanceObject ao = this.tapStudent.getAttendanceObject();

        if(ao.getFirstAccessTime() != null){
            //  入室時にACKを一度でも返している
            return;
        }

        if(ao.getManualRequestAttendance() == this.MANUAL_NOT_REQUEST
                && ao.getAttendanceTime() != null){
            return;
        }

        //if(att.getStatus() == Config.ALREADY_ATTENDANCE){
        if(ao.getManualRequestAttendance() == 0 && ao.getAttendanceTime() != null){
            //ACKですでに出席済み.
            layout.setVisibility(View.GONE);
        }else{
            //ESL忘れレイアウト
            layout.setVisibility(View.VISIBLE);

            releaseBtn = getButton(d,R.id.dialog_custom_forgot_esl_release_button);
            releaseBtn.setOnClickListener(forgotESLBtnListener);
            requestBtn = getButton(d,R.id.dialog_custom_forgot_esl_button);
            requestBtn.setOnClickListener(forgotESLBtnListener);


            //if(att.getStatus() == Config.FOGOT_ESL_APPLY){
            if(ao.getForgotApplyTime() != null){
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

        /*if(attState.getTempAttendanceState() == 0
                &&attState.getRequestForgotESLTime() == null
                &&attState.getConfirmTime() != null){
            //ACKですでに出席済み.
            layout.setVisibility(View.GONE);
        }else{
            //ESL忘れレイアウト
            layout.setVisibility(View.VISIBLE);
            releaseBtn = getButton(d,R.id.dialog_custom_forgot_esl_release_button);
            releaseBtn.setOnClickListener(forgotESLBtnListener);
            requestBtn = getButton(d,R.id.dialog_custom_forgot_esl_button);
            requestBtn.setOnClickListener(forgotESLBtnListener);

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
        }*/
    }
    /**
     * Created by scr on 2016/1/28.
     * setAttendメソッド
     * そのだ教授でACkが得られなかった学生への対応
     * @param d ダイアログのインスタンス
     */
    private void setAttend(Dialog d){
        //  そのだ教授が全員に一回でも送信を終えている.
        String attendEndTime      = this.transmitStateObject.getAttendanceTranmitEndTime();
        String reAttendEndTime    = this.transmitStateObject.getReAttendanceEndTime();
        String undoTranmitEndTime = this.transmitStateObject.getUndoTransmitEndTime();

        if(attendEndTime == null || reAttendEndTime != null || undoTranmitEndTime != null){
            //  次のコンテンツなのでリターン
            return;
        }
        //if(this.attendanceStudent.getAttendance().getStatus() == Config.ALREADY_ATTENDANCE){
        if(this.tapStudent.getAttendanceObject().getManualRequestAttendance() == this.MANUAL_NOT_REQUEST
                && this.tapStudent.getAttendanceObject().getAttendanceTime() != null){
            //  正常に出席がとれているのでリターン
            return;
        }

        //  そのだ教授レイアウト
        LinearLayout attendApplyLinearLayout = getLinearLayout(d, R.id.attend_apply_linearLayout);
        attendApplyLinearLayout.setVisibility(View.VISIBLE);

        setManualReleaseBtn(d, R.id.dialog_custom_attend_apply_release_button);
        manualReleaseBtn.setOnClickListener(manualRequestBtnListener);
        setManualRequestBtn(d, R.id.dialog_custom_attend_apply_button);
        manualRequestBtn.setOnClickListener(manualRequestBtnListener);

        if(this.tapStudent.getAttendanceObject().getManualRequestAttendance() == this.MANUAL_NOT_REQUEST){
            //  申請用ボタンのみ表示
            manualRequestBtn.setVisibility(View.VISIBLE);
            manualReleaseBtn.setVisibility(View.INVISIBLE);
        }else {
            //  解除ボタンのみ表示
            manualRequestBtn.setVisibility(View.INVISIBLE);
            manualReleaseBtn.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Created by scr on 2016/1/28.
     * setReAttendメソッド
     * 出席再調査でACkが得られなかった学生への対応
     * @param d ダイアログのインスタンス
     */
    private void setReAttend(Dialog d){
        //  そのだ教授が全員に一回でも送信を終えている.
        String attendEndTime      = this.transmitStateObject.getAttendanceTranmitEndTime();
        String reAttendEndTime    = this.transmitStateObject.getReAttendanceEndTime();
        String undoTranmitEndTime = this.transmitStateObject.getUndoTransmitEndTime();

        if(attendEndTime == null){
            return;
        }

        if(reAttendEndTime == null){
            return;
        }

        if(this.tapStudent.getAttendanceObject().getForgotApplyTime() != null){
            //  ESLを忘れているの在室関係には参加させない.
            return;
        }

        if(this.tapStudent.getAttendanceObject().getAttendanceTime() == null){
            //  出席の確認ができていない学生には参加させない
            return;
        }

        if(undoTranmitEndTime != null){
            //  次のコンテンツなのでリターン
            return;
        }
        //if(this.attendanceStudent.getAttendance().getStatus() == Config.ALREADY_ATTENDANCE){
        if(this.tapStudent.getAttendanceObject().getManualRequestReAttendance() == this.MANUAL_NOT_REQUEST
                &&this.tapStudent.getAttendanceObject().getReAttendancetime() != null){
            //  再調査後の出席がとれているのでリターン
            return;
        }

        //  再出席調査レイアウト
        LinearLayout attendApplyLinearLayout = getLinearLayout(d, R.id.re_attend_apply_linearLayout);
        attendApplyLinearLayout.setVisibility(View.VISIBLE);

        /*releaseBtn = getButton(d,R.id.dialog_custom_re_attend_apply_release_button);
        releaseBtn.setOnClickListener(forgotESLBtnListener);
        requestBtn = getButton(d,R.id.dialog_custom_re_attend_apply_button);
        requestBtn.setOnClickListener(forgotESLBtnListener);*/

        setManualReleaseBtn(d, R.id.dialog_custom_re_attend_apply_release_button);
        manualReleaseBtn.setOnClickListener(manualRequestBtnListener);
        setManualRequestBtn(d, R.id.dialog_custom_re_attend_apply_button);
        manualRequestBtn.setOnClickListener(manualRequestBtnListener);

        if(this.tapStudent.getAttendanceObject().getManualRequestReAttendance() == this.MANUAL_NOT_REQUEST){
            //  在室確認ボタンのみ表示
            manualRequestBtn.setVisibility(View.VISIBLE);
            manualReleaseBtn.setVisibility(View.INVISIBLE);
        }else {
            //  不在ボタンのみ表示
            manualRequestBtn.setVisibility(View.INVISIBLE);
            manualReleaseBtn.setVisibility(View.VISIBLE);
        }

    }

    /**
     * Created by scr on 2016/1/28.
     * setUndoメソッド
     * 外部持ち出し用名札でACKが得られなかった学生への対応
     * @param d ダイアログのインスタンス
     */
    private void setUndo(Dialog d){
        //  そのだ教授が全員に一回でも送信を終えている.
        //String attendEndTime      = this.transmitStateObject.getAttendanceTranmitEndTime();
        //String reAttendEndTime    = this.transmitStateObject.getReAttendanceEndTime();
        String undoTranmitEndTime = this.transmitStateObject.getUndoTransmitEndTime();
        AttendanceObject ao = this.tapStudent.getAttendanceObject();
        if(undoTranmitEndTime == null){
            return;
        }


        if(ao.getAttendanceTime() == null){
            //  出席をしていない
            return;
        }

        if(ao.getForgotApplyTime() != null){
            //  ESLを忘れているの在室関係には参加させない.
            return;
        }

        if(ao.getAttendanceTime() != null && ao.getManualRequestClassLastAck() == this.MANUAL_NOT_REQUEST && ao.getClassLastAckTime() != null){
            //  ①   出席時刻を確認している
            //  ②   手動で授業最終申請をしていない
            //  ③   授業最終ACKを確認している
            return;
        }

        //  再出席調査レイアウト
        LinearLayout attendApplyLinearLayout = getLinearLayout(d, R.id.undo_apply_linearLayout);
        attendApplyLinearLayout.setVisibility(View.VISIBLE);

        //manualRequestBtn = getButton(d,R.id.dialog_custom_undo_apply_button);
        //manualReleaseBtn = getButton(d,R.id.dialog_custom_undo_apply_release_button);
        this.setManualRequestBtn(d, R.id.dialog_custom_undo_apply_button);
        this.setManualReleaseBtn(d, R.id.dialog_custom_undo_apply_release_button);
        manualReleaseBtn.setOnClickListener(manualRequestBtnListener);
        manualRequestBtn.setOnClickListener(manualRequestBtnListener);

        if(ao.getManualRequestClassLastAck() == PARAMETA_FORGOT_ESL
                && ao.getClassLastAckTime() != null) {
            //  かえでちゃん解除
            manualReleaseBtn.setVisibility(View.VISIBLE);
        } else {
            //  かえでちゃん申請
            manualRequestBtn.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Created by scr on 2015/1/7.
     * View.OnClickListenerメソッド
     * 個人履歴確認リスナー.
     */
    /*private View.OnClickListener historyConfirmBtnListener = new View.OnClickListener() {
        public void onClick(View v) {
            switch(v.getId()) {
                case R.id.dialog_custom_history_confirm_button:
                    //個人履歴確認画面へ
                    moveToHistoryCustomDialogFragment();
                    break;
            }
        }
    };*/
    /**
     * Created by scr on 2015/1/4.
     * moveToHistoryCustomDialogFragmentメソッド
     * 個人履歴確認フラグメントへ移動する.
     */
    private void moveToHistoryCustomDialogFragment(){
        HistoryDialogFragment customDialog = HistoryDialogFragment.newInstance();
        customDialog.setTargetFragment(CustomDialogFragment.this, 0);
        Bundle bundle = new Bundle();
        bundle.putSerializable(ATTENDANCE_STUDENT_INFO, tapStudent);
        customDialog.setArguments(bundle);
        customDialog.show(this.getActivity().getSupportFragmentManager(), HistoryDialogFragment.HISTORY_DIALO_FRAGMENT);
    }

    /**
     * Created by scr on 2015/1/4.
     * View.OnClickListenerメソッド
     * ESL忘れ関係ボタンのリスナーです.
     */
    private View.OnClickListener forgotESLBtnListener = new View.OnClickListener() {
        public void onClick(View v) {
            switch(v.getId()) {
                case R.id.dialog_custom_forgot_esl_release_button:
                    //ESL忘れ解除
                    forgotESLRelease();
                    break;
                case R.id.dialog_custom_forgot_esl_button:
                    //ESL忘れ登録
                    forgotESLRequest();
                    break;
                case R.id.dialog_custom_attend_apply_release_button:

                    break;
                case R.id.dialog_custom_attend_apply_button:
                    break;
                case R.id.dialog_custom_re_attend_apply_button:
                    //再調査漏れ申請
                    reAttendApplyRequest();
                    break;
                case R.id.dialog_custom_re_attend_apply_release_button:
                    //
                    break;

            }
        }
    };

    /**
     * Created by scr on 2016/2/2.
     * manualRequestBtnListener
     * 手動リクエストリスナー
     */
    private View.OnClickListener manualRequestBtnListener = new View.OnClickListener() {
        public void onClick(View v) {
            switch (v.getId()) {

                case R.id.dialog_custom_attend_apply_release_button:
                    //  出席調査ACK漏れ対応解除
                    attendApplyRelease();
                    break;

                case R.id.dialog_custom_attend_apply_button:
                    //  出席調査ACK漏れ対応申請
                    attendApplyRequest();
                    break;
                case R.id.dialog_custom_re_attend_apply_release_button:
                    reAttendApplyRelease();
                    break;

                case R.id.dialog_custom_re_attend_apply_button:
                    reAttendApplyRequest();
                    break;

                case R.id.dialog_custom_undo_apply_release_button:
                    //  かなえちゃんACk漏れ対応解除
                    undoApplyRelease();
                    break;

                case R.id.dialog_custom_undo_apply_button:
                    //  かなえちゃんACk漏れ対応申請
                    undoApplyRequst();
                    break;
            }
        }
    };

                /**
                 * Created by scr on 2015/1/4.
                 * forgotESLReleaseメソッド
                 * ESL忘れ解除手続きをこちらでおこいます.
                 */
    private void forgotESLRelease(){
        //解除
        forgotAppy(this.PARAMETA_RELEASE_FORGOT);
    }
    /**
     * Created by scr on 2015/3/13.
     * forgotAppyメソッド
     * ESL忘れ又はESL忘れ解除をネットワークDBに保存する.
     * @param parameta 1 ESL忘れ 0:ESL忘れ解除
     */
    private void forgotAppy(int parameta){
        this.selectApplyType = parameta;
        AttendanceObject ao = this.tapStudent.getAttendanceObject();
        String url = URL.getForgotAppy(selectApplyType,ao.getAttendanceId());

        JsonObjectRequest request = new JsonObjectRequest(url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //setQuestionnaire(response);
                        forgotApplyNextProcess(response);
                    }
                }, new Response.ErrorListener() {
            @Override public void onErrorResponse(VolleyError error) {
                int a =0;
                a = 12;
                Toast.makeText(getActivity(), "Unable to fetch data: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        AppController.getInstance(this.getActivity()).getRequestQueue().add(request);
    }

    void forgotApplyNextProcess(JSONObject response){
        AttendanceObject att = this.tapStudent.getAttendanceObject();
        if(this.selectApplyType == this.PARAMETA_FORGOT_ESL){

            //att.setStatus(FORGOT_ESL);
            att.setManualRequestFirstAccess(1);
            att.setFirstAccessTime("");
            att.setManualRequestAttendance(1);
            att.setAttendanceTime("");
            att.setFogotApplytTime("");
            //解除非表示
            this.releaseBtn.setVisibility(View.VISIBLE);
            //申請表示
            this.requestBtn.setVisibility(View.INVISIBLE);
        }else{
            //att.setStatus(ABSENTEE);
            att.setManualRequestFirstAccess(0);
            att.setFirstAccessTime(null);
            att.setManualRequestAttendance(0);
            att.setAttendanceTime(null);
            att.setFogotApplytTime(null);

            //解除非表示
            this.releaseBtn.setVisibility(View.INVISIBLE);
            //申請表示
            this.requestBtn.setVisibility(View.VISIBLE);
        }
        callBack();
    }



    private void callBack(){
        //出欠カラーを取得
        int mainFrameResorceColor = getFrameColorResorce();
        Dialog dialog = super.getDialog();
        //フレーム全体の色を選択
        setFrameBackgoundColor(dialog, mainFrameResorceColor);
        Bundle arg = new Bundle();
        // MyFragmentのonOkClickedをコール
        mListener.onOkClicked(arg);

        //総出席回数変更
        //setTotalAttendanceSituation(dialog);
        //  グリッドビュー再描画
        //attendeeFragment.attendeeGridView.invalidate();
        //attendeeFragment.adapter.notifyDataSetChanged();
        attendeeFragment.setReAttendanceStatusTextView();
    }

    /**
     * Created by scr on 2015/1/4.
     * forgotESLReleaseメソッド
     * ESL忘れ登録手続きをこちらでおこいます.
     */
    private void forgotESLRequest(){
        //申請
        forgotAppy(this.PARAMETA_FORGOT_ESL);
        /*
        //解除非表示
        this.releaseBtn.setVisibility(View.VISIBLE);
        //申請表示
        this.requestBtn.setVisibility(View.INVISIBLE);
        callBack();
        */
    }
    /**
     * Created by scr on 2016/1/28.
     * attendApplyReleaseメソッド
     * そのだ教授解除
     */
    private void attendApplyRelease(){
        this.selectRequestType = this.MANUAL_REQUEST_TYPE_ATTENDANCE;
        selectApplyType = this.MANUAL_NOT_REQUEST;
        String attId = getAttendanceId();
        String url = URL.getAttendLeak(attId, selectApplyType);
        ackLeakrequest(url);
    }
    /**
     * Created by scr on 2016/1/28.
     * attendApplyRequestメソッド
     * そのだ教授申請漏れへの対応
     */
    private void attendApplyRequest(){
        this.selectRequestType = this.MANUAL_REQUEST_TYPE_ATTENDANCE;
        selectApplyType = this.MANUAL_REQUEST;
        String attId = getAttendanceId();
        String url = URL.getAttendLeak(attId, selectApplyType);
        ackLeakrequest(url);
    }
    /**
     * Created by scr on 2016/2/4.
     * reAttendApplyReleaseメソッド
     * 再出席調査申請漏れへの対応
     */
    private void reAttendApplyRelease(){
        this.selectRequestType = this.MANUAL_REQUEST_TYPE_RE_ATTENDANCE;
        selectApplyType = this.MANUAL_NOT_REQUEST;
        String attId = getAttendanceId();
        String url = URL.getReAttendLeak(attId, selectApplyType);
        ackLeakrequest(url);
    }
    /**
     * Created by scr on 2016/1/28.
     * reAttendApplyResultメソッド
     * 再出席調査申請漏れへの対応
     */
    private void reAttendApplyRequest(){
        this.selectRequestType = this.MANUAL_REQUEST_TYPE_RE_ATTENDANCE;
        selectApplyType = this.MANUAL_REQUEST;
        String attId = getAttendanceId();
        String url = URL.getReAttendLeak(attId, selectApplyType);
        ackLeakrequest(url);
    }
    /**
     * Created by scr on 2016/2/2.
     * undoApplyReleaseメソッド
     * かなえちゃんACk漏れ解除
     */
    private void undoApplyRelease(){
        //
        this.selectRequestType = this.MANUAL_REQUEST_TYPE_UNDO;
        String attId = getAttendanceId();
        selectApplyType = this.MANUAL_NOT_REQUEST;
        String url = URL.getUndoLeak(attId, selectApplyType);
        ackLeakrequest(url);
    }

    /**
     * Created by scr on 2016/1/28.
     * undoApplyRequstメソッド
     * かなえちゃんACk漏れへの対応
     */
    private void undoApplyRequst(){
        //
        this.selectRequestType = this.MANUAL_REQUEST_TYPE_UNDO;
        String attId = getAttendanceId();
        selectApplyType = this.MANUAL_REQUEST;
        String url = URL.getUndoLeak(attId, selectApplyType);
        ackLeakrequest(url);
    }

    /**
     * ackLeakrequestメソッド
     * ACK応答がなかった場合への対応
     * DBに状態を反映する.
     * **/
    private void ackLeakrequest(String url){
        JsonObjectRequest request = new JsonObjectRequest(url, null,
                   new Response.Listener<JSONObject>() {
                       @Override
                       public void onResponse(JSONObject response) {
                           setAttTime(response);
                       }
                   }, new Response.ErrorListener() {
               @Override public void onErrorResponse(VolleyError error) {
                   int a =0;
                   a = 12;
                   //Toast.makeText(getActivity(), "Unable to fetch data: " + error.getMessage(), Toast.LENGTH_SHORT).show();
               }
            });
        AppController.getInstance(this.getActivity()).getRequestQueue().add(request);
    }


    class AttendanceRelation{
        @SerializedName("attendance_relation")
        AttendanceObject ao;
    }

    /**
     * setAttTimeメソッド
     * 架空出席時刻を記録する
     * **/
    private void setAttTime(JSONObject response) {
        AttendanceRelation newAttendanceRelation = new Gson().fromJson(response.toString(), AttendanceRelation.class);
        this.tapStudent.getAttendanceObject().setAttendanceObject(newAttendanceRelation.ao);
        AttendanceObject ao = this.tapStudent.getAttendanceObject();
        if(this.selectRequestType == this.MANUAL_REQUEST_TYPE_ATTENDANCE){
            doAttendance();
        }else if(this.selectRequestType == this.MANUAL_REQUEST_TYPE_RE_ATTENDANCE){
            doReAttendance();
        }else if(this.selectRequestType == this.MANUAL_REQUEST_TYPE_UNDO){
            //  プライバシー保護画面
            doUndo();
        }

        //  グリッドビュー再描画
        attendeeFragment.adapter.notifyDataSetChanged();
    }
    /**
     * Created by scr on 2016/2/4.
     * doLeakメソッド
     * ACk漏れ申請又は解除後のダイアログの選択ボタンの対応を行います.
     */
    private void doLeak(){
        if(this.selectApplyType == this.MANUAL_NOT_REQUEST){
            //解除非表示
            this.manualReleaseBtn.setVisibility(View.INVISIBLE);
            //申請表示
            this.manualRequestBtn.setVisibility(View.VISIBLE);
        }else {
            //解除非表示
            this.manualReleaseBtn.setVisibility(View.VISIBLE);
            //申請表示
            this.manualRequestBtn.setVisibility(View.INVISIBLE);
        }
        callBack();
    }

    /**
     * Created by scr on 2016/2/2.
     * doAttendanceメソッド
     * 出席漏れへの対応を行います.
     */
    private void doAttendance(){
        doLeak();
    }
    /**
     * Created by scr on 2016/2/2.
     * doReAttendanceメソッド
     * 再出席漏れ（他コンテンツACK漏れ）への対応を行います.
     */
    private void doReAttendance(){
        /*if(this.selectApplyType == this.MANUAL_NOT_REQUEST){
            //解除非表示
            this.manualReleaseBtn.setVisibility(View.INVISIBLE);
            //申請表示
            this.manualRequestBtn.setVisibility(View.VISIBLE);
        }else {
            //解除非表示
            this.manualReleaseBtn.setVisibility(View.VISIBLE);
            //申請表示
            this.manualRequestBtn.setVisibility(View.INVISIBLE);
        }
        callBack();*/

        doLeak();
    }

    /**
     * Created by scr on 2016/2/2.
     *  doUndoメソッド
     *
     * */
    private void doUndo(){
        /*if(this.selectApplyType == this.MANUAL_NOT_REQUEST){
            //ao.setManualRequestClassLastAck(1);
            //ao.setClassLastAckTime("");
            //解除非表示
            this.manualReleaseBtn.setVisibility(View.INVISIBLE);
            //申請表示
            this.manualRequestBtn.setVisibility(View.VISIBLE);
        }else{
            //ao.setManualRequestClassLastAck(0);
            //ao.setClassLastAckTime(null);
            //解除非表示
            this.manualReleaseBtn.setVisibility(View.VISIBLE);
            //申請表示
            this.manualRequestBtn.setVisibility(View.INVISIBLE);
        }*/
        doLeak();
    }

    /**
     * ACK漏れ申請結果クラス
     * **/
    class RequstAttResult{
        @SerializedName("attendance_id")
        String  attendanceId;
        @SerializedName("att_time")
        String  attTime;
        @SerializedName("att_status")
        int     attStatus;
    }


    /**
     * Created by scr on 2015/1/2.
     * setConfirmTodayAttendanceTimeメソッド
     * 出席確認時間.
     * @param d ダイアログのインスタンス
     */
    /*private void setConfirmTodayAttendanceTime(Dialog d){
        //出席時刻確認レイアウト
        LinearLayout confirmLayout = this.getLinearLayout(d,R.id.attendee_confirm_time_linearLayout);

        /*
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
        */
    //}

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