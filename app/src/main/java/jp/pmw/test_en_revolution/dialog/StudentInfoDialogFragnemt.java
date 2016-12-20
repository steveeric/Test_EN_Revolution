package jp.pmw.test_en_revolution.dialog;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.DialogFragment;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import io.realm.Realm;
import io.realm.RealmResults;
import jp.pmw.test_en_revolution.AppController;
import jp.pmw.test_en_revolution.AttendanceObject;
import jp.pmw.test_en_revolution.AttendeeFragment;
import jp.pmw.test_en_revolution.MainActivity;
import jp.pmw.test_en_revolution.MessageObject;
import jp.pmw.test_en_revolution.NumOfAttendanceEntity;
import jp.pmw.test_en_revolution.PastAttendanceCount;
import jp.pmw.test_en_revolution.R;
import jp.pmw.test_en_revolution.SeatObject;
import jp.pmw.test_en_revolution.SeatSituationFragment;
import jp.pmw.test_en_revolution.StudentInfoCustomDialog;
import jp.pmw.test_en_revolution.StudentObject;
import jp.pmw.test_en_revolution.TransmitStateObject;
import jp.pmw.test_en_revolution.attendee.FaceImageRealmObject;
import jp.pmw.test_en_revolution.attendee.FaceImageTask;
import jp.pmw.test_en_revolution.config.URL;
import jp.pmw.test_en_revolution.network.MyAsyncTask;
import jp.pmw.test_en_revolution.network.MyHttpConnection;
import jp.pmw.test_en_revolution.network.MyIOException;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by si on 2016/02/05.
 * 受講者一覧から受講者情報を確認する
 * ダイアログクラス
 */
public class StudentInfoDialogFragnemt extends DialogFragment {
    //
    public static final String STUDENT_INFO_DIALOG_FRAGMENT = "student_info_dialog_fragment";
    //  ダイアログフラグメントパラメータ
    public static final String DIALOG_FRAGMENT_PARAMETA = "dialog_fragment_parameta";
    //  授業識別番号パラメータ
    public static final String SAME_CLASS_NUMBER = "same_class_number";
    //  学生オブジェクトパラメータ
    public static final String STUDENT_INFO = "student_info";
    //  送信オブジェクトパラメータ
    public static final String TRANMIST_STAT_IFNO = "tranmit_state_info";

    //  忘れ申請パラメータ
    public static final int PARAMETA_FORGOT_ESL = 1;
    //  忘れ解除パラメータ
    public static final int PARAMETA_RELEASE_FORGOT = 0;

    //  申請手動
    public static final int MANUAL_REQUEST = 1;
    //  解除手動
    public static final int MANUAL_NOT_REQUEST = 0;

    //  出席リクエスト
    private static final int MANUAL_REQUEST_TYPE_ATTENDANCE = 2;
    private static final int MANUAL_REQUEST_TYPE_RE_ATTENDANCE = 3;
    private static final int MANUAL_REQUEST_TYPE_UNDO = 4;

    //
    private static StudentInfoDialogFragnemt instance = null;

    //  授業識別番号
    public String sameClassNumber;

    //  学生情報オブジェクト
    public StudentObject tapStudent;

    //  送信状態オブジェクト
    public TransmitStateObject transmitStateObject;

    //  出席フラグメント
    public AttendeeFragment af;
    public void setAttendeeFragment(AttendeeFragment af) {
        this.af = af;
    }
    public SeatSituationFragment sf;
    public void setSeatSituationFragment(SeatSituationFragment sf){
        this.sf = sf;
    }

    //  忘れ申請ボタン
    private Button forgotReleaseBtn;
    //  忘れ解除ボタン
    private Button forgotApplyBtn;
    //  手動申請解除ボタン
    private Button manualAttRequestBtn;
    //  手動申要求請ボタン
    private Button manualAttReleaseBtn;
    //
    public Dialog dialog;

    //  どのリクエストタイプが選択されているか
    private int selectRequestType;
    //  申請手動か申請解除どちらか
    private int selectApplyType;

    //  現在の出席状態
    private int nowAttendanceStage;

    //  みなすコンテンツクラス
    //RegardedAs mRegardedAs;

    // UIハンドラー
    public Handler mHandler;


    /**
     * ファクトリーメソッド
     */
    public static StudentInfoDialogFragnemt newInstance() {

        if (instance == null) {
            instance = new StudentInfoDialogFragnemt();
        } else {
            //  2重起動防止用(効果あり?)
            instance.dismiss();
            return null;
        }
        return instance;
    }

    //
    public MainActivity getMainActivity() {
        return (MainActivity) this.getActivity();
    }


    //  選択された学生の出席オブジェクト取得
    public AttendanceObject getAttObject() {
        return this.tapStudent.getAttendanceObject();
    }

    //  座席オブジェクト
    public SeatObject getSeatObject() {
        return this.tapStudent.getSeatObject();
    }


    //  ライナーレイアウト取得
    public LinearLayout getLinearLayout(Dialog d, int resorce) {
        return (LinearLayout) d.findViewById(resorce);
    }

    //  ラジオボタン
    public RadioButton getRadioButton(int resorce){
       return (RadioButton) dialog.findViewById(resorce);
    }

    //  ボタン
    public Button getButton(Dialog d, int resorce) {
        return (Button) d.findViewById(resorce);
    }
    //  チェックボックス
    public CheckBox getCheckBox(Dialog d, int resorce){
        return (CheckBox) d.findViewById(resorce);
    }


            //  テキストビュー取得
    public TextView getTextView(Dialog d, int resorce) {
        return (TextView) d.findViewById(resorce);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.instance = null;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //  UIハンドラー
        this.mHandler = new Handler(Looper.myLooper());
        //  どのコンテンツをフラグメントに表示するかのパラメータ
        int parameta = getArguments().getInt(DIALOG_FRAGMENT_PARAMETA);
        //  授業識別番号
        sameClassNumber = getArguments().getString(SAME_CLASS_NUMBER);
        //  学生オブジェクト
        tapStudent = (StudentObject) getArguments().getSerializable(STUDENT_INFO);
        //  送信オブジェクト
        transmitStateObject = (TransmitStateObject) getArguments().getSerializable(TRANMIST_STAT_IFNO);

        dialog = new Dialog(getActivity());
        // タイトル非表示
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        // フルスクリーン
        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        //  レイアウトセット
        dialog.setContentView(R.layout.dialog_custom_20161217);
        // 背景を透明にする
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //  ダイアログフラグメント
        setSizeDialogFragment();
        //  ダイアログフレーム色
        int dialogFrameColor = getDialogFrameColor();
        //  フレーム色セット
        setFrameBackgoundColor(dialogFrameColor);
        //
        String studentIdNumber = this.tapStudent.getStudentIdNumber();
        String furigana = this.tapStudent.getFurigana();
        String fullName = this.tapStudent.getFullName();
        //  学籍番号とフリガナと名前をセット
        setFixStudentInfo(studentIdNumber, furigana, fullName);
        //  位置に関する文字列を表示する.
        showPosition();
        //  過去(今回の授業除く)の出欠回数
        showPastAttendanceCount();
        //  メッセージセット
        showMessage();
        //  出席・遅刻・忘れ　手動セットレイアウト
        showRegardedAsCheckBoxLayout();
        //  在室確認レイアウトを表示する.
        showBeInRoomLayout();
        //  フレームを描きなおす
        redrawFrame();
        //  顔画像
        setFaceImage( tapStudent );

        return dialog;
    }
    /**
     *  Created by si on 2016/06/16.
     *  setDialogFragmentメソッド
     *  ダイアログのサイズをセットする
     **/
    void setSizeDialogFragment(){
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int dialogWidth = (int) (metrics.widthPixels * 0.9);
        int dialogHeight = (int) (metrics.heightPixels * 0.95);
        lp.width = dialogWidth;
        lp.height = dialogHeight;
        dialog.getWindow().setAttributes(lp);
    }

    /**
     * Created by si on 2016/02/05.
     * getDialogFrameColorメソッド
     * ダイアログフレーム色を取得する
     *
     * @return 色リソース
     **/
    public int getDialogFrameColor() {
        AttendanceObject ao = getAttObject();
        return ao.getAttendanceStateColor(getMainActivity(), this.transmitStateObject);
    }

    /**
     * Created by scr on 2015/1/3～2015/1/4.
     * setFrameBackgoundColorメソッド
     * 本日の出席状態カラーを取得します.
     *
     * @param resorce 出席カラーのリソース
     */
    public void setFrameBackgoundColor(int resorce) {
        LinearLayout titleLayout = getLinearLayout(dialog, R.id.title_linearLayout);
        titleLayout.setBackgroundColor(resorce);
    }

    /**
     * Created by scr on 2016/02/05
     * setFixStudentInfoメソッド
     * 学生の固定情報をセットします.
     *
     * @param studentIdNumber 学籍番号
     * @param furigana        フリガナ
     * @param fullName        氏名
     */
    private void setFixStudentInfo(String studentIdNumber, String furigana, String fullName) {
        //  学籍番号
        setStudentIdNumber(studentIdNumber);

        //  フリガナ
        setFurigana(furigana);

        //  学生名
        setName(fullName);
    }

    /**
     * Created by scr on 2016/02/05
     * setStudentIdNumberメソッド
     * 学籍番号をテキストにセットします.
     */
    private void setStudentIdNumber(String studentIdNumber) {
        TextView studentIdNumerTextView = getTextView(dialog, R.id.title_orijinal_student_id_textView);
        studentIdNumerTextView.setText(studentIdNumber);
    }
    /**
     * Created by scr on 2016/05/24
     * setFuriganaメソッド
     * フリガナをテキストにセットします.
     */
    private void setFurigana(String furigana){
        TextView furiganaTextView = getTextView(dialog, R.id.title_furigana_textView);
        furiganaTextView.setText(furigana);
    }


    /**
     * Created by scr on 2016/02/05
     * setNameメソッド
     * 学生名をテキストにセットします.
     *
     * @param fullName 氏名
     */
    private void setName(String fullName) {
        TextView nameTextView = getTextView(dialog, R.id.title_full_name_textView);
        nameTextView.setText(fullName);
    }

    /**
     * Created by scr on 2016/02/05
     * setCloseBtnメソッド
     * とじるボタンをセットします.
     *
     * @param resorce 出席カラーのリソース
     */
    private void setCloseBtn(int resorce) {
        //とじるボタン
        Button closeBtn = (Button) dialog.findViewById(R.id.dialog_custom_upper_right_cloase_btn);
        //とじるボタンカラー
        closeBtn.setTextColor(resorce);
        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    /**
     * Created by si on 2016/02/05.
     * chkMessageExistanceメソッド
     * メッセージ存在を確認する.
     *
     * @return {@code NULL} メッセージなし {@code Not NULL} メッセージあり
     **/
    private String chkMessageExistance() {
        String messageContent = null;
        MessageObject mo = this.tapStudent.getMessageObject();
        if (mo.getMessageContent() != null) {
            messageContent = mo.getMessageContent();
        }
        return messageContent;
    }

    /**
     * Created by scr on 2016/06/16
     * showPositionメソッド
     * 位置に関する文字列を表示します..
     */
    void showPosition(){
        SeatObject so = getSeatObject();
        String groupNumber = so.getGroupName();
        String seatName = so.getSeatName();
        TextView todayPositionTv = getTextView(dialog, R.id.today_positon_tv);
        String strGroupNumber    = this.getString(R.string.group_number);
        String strDemiliter      = this.getString(R.string.position_delimiter);
        String strSeatPosition   = this.getString(R.string.seatposition);
        String str = "";
        if(groupNumber != null){
            if( groupNumber.length() > 0 ){
                //  グループ名称関係
                str = str + strGroupNumber +" "+ groupNumber + strDemiliter;
            }
        }
        if( seatName != null ){
            //  座席名称関係
            str = str + strSeatPosition +" "+ seatName;
        }
        if( (!str.equals("")) ){
            todayPositionTv.setText(str);
            todayPositionTv.setVisibility(View.VISIBLE);
        }
    }
    /**
     * Created by scr on 2016/02/05
     * setMessageメソッド
     * メッセージを表示する
     */
    private void showMessage() {
        //メッセージ
        String messageContent = chkMessageExistance();
        LinearLayout layout = getLinearLayout(dialog, R.id.message_linearLayout);
        //  メッセージあり
        if (messageContent != null) {
            //  メッセージレイアウト表示
            TextView simpleMessageTV = getTextView(dialog, R.id.simple_message_textView);
            simpleMessageTV.setText(messageContent);
            layout.setVisibility(View.VISIBLE);
            return;
        }
        //  メッセージなし
        layout.setVisibility(View.GONE);
    }
    /**
     * Created by scr on 2016/05/25.
     * showPastAttendanceCountメソッド
     * 過去(一つ前の授業まで)の出欠回数を表示します.
     */
    private void showPastAttendanceCount(){
         //  出・遅・欠 表示ローディング
        LinearLayout loadingLl = getLinearLayout(dialog, R.id.total_attendance_situation_loding_display_ll);
        //  出・遅・欠 表示レイアウト
        LinearLayout ll = getLinearLayout(dialog, R.id.total_attendance_situation_linearLayout);
        //  前回までの出欠レイアウトを表示する
        ll.setVisibility(View.VISIBLE);
        //
        if( tapStudent.getPastAttendanceCount() == null ){
            //  過去の出・遅・欠席回数を取得する.
            getPastAttendanceCount();
        }else{
            //  過去の出・遅・欠席回数を表示する.
            setPastAttendanceCount();
        }
    }
    /**
     * Created by scr on 2016/06/28.
     * getPastAttendanceCountメソッド
     * 過去(一つ前の授業まで)の出欠回数をネットワークを介しDBから取得します.
     */
    void getPastAttendanceCount(){
        String attendanceId  =this.tapStudent.getAttendanceObject().getAttendanceId();
        //  URL
        String url = URL.getPastTotalAttendanceCount(attendanceId);
        //  アクセス
        requestToWebApi(url);
    }
    /**
     * requestToWebApiメソッド
     * WEBAPIにアクセスします.
     * @param String url URL
     * */
    void requestToWebApi(final String url){
        new MyAsyncTask() {
            @Override
            protected String doInBackground(Void... params) {
                try {
                    String result = MyHttpConnection.run(url);
                    Gson g = new Gson();
                    PastAttendanceCount pac = g.fromJson(result.toString(), PastAttendanceCount.class);
                    setPastAttendanceCountEntity(pac);
                } catch(IOException e) {
                    MyIOException.absorbIOException(e);
                }
                return null;
            }
        }.execute();
    }

    /**
     * Created by scr on 2016/06/28.
     * setPastAttendanceCountEntityメソッド
     * 過去の出・遅・欠席回数をセットします.
     */
    public void setPastAttendanceCountEntity(PastAttendanceCount pac){
        this.tapStudent.setPastAttendanceCount(pac);
        handlerSetPastAttendanceCount();
    }
    /**
     * Created by scr on 2016/06/28.
     * setPastAttendanceCountメソッド
     * 過去の出・遅・欠席回数をセットします.
     */
    void handlerSetPastAttendanceCount(){
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if(getMainActivity() != null){
                    setPastAttendanceCount();
                }
            }
        });
    }
    /**
     * Created by scr on 2016/06/28.
     * handlerSetPastAttendanceCountメソッド
     * 過去の出・遅・欠席回数をセットするUIハンドラー
     */
    void setPastAttendanceCount(){
        //  出・遅・欠 表示ローディング
        LinearLayout loadingLl = getLinearLayout(dialog, R.id.total_attendance_situation_loding_display_ll);
        //  出・遅・欠 回数レイアウト
        LinearLayout cdLl = getLinearLayout(dialog, R.id.total_attendance_situation_count_display_ll);
        //  過去の出席回数を表示する.
        PastAttendanceCount pac = tapStudent.getPastAttendanceCount();

        //  出席した回数(WEBと同じ手法での出席回数)
        int attenedCount = pac.getAttendedCount();
        //  遅刻した回数
        int lateCount   = pac.getLatedCount();
        //  欠席した回数(WEBと同じ手法での欠席回数)
        int absentedCount =pac.getAbsentedCount();
        //  早退した回数
        int leaveEarly = pac.getLeaveEarlyCount();

        TextView attendedTextView   = getTextView(dialog, R.id.total_attendance_attendee_situation_textView);
        TextView lateTextView       = getTextView(dialog, R.id.total_attendance_late_situation_textView);
        TextView absentedTextView   = getTextView(dialog, R.id.total_attendance_absentee_situation_textView);
        TextView laveEaryTextView   = getTextView(dialog, R.id.total_attendance_leave_early_situation_textView);

        //  出欠席値をセットする.
        attendedTextView.setText(getStrTotalAttended(attenedCount));
        lateTextView.setText(getStrTotalLate(lateCount));
        absentedTextView.setText(getStrTotalAbsended(absentedCount));
        laveEaryTextView.setText(getStrTotalLeaveEarly(leaveEarly));

        //  ローディングレイアウト非表示にする.
        loadingLl.setVisibility(View.GONE);
        //  出・遅・欠 回数レイアウト
        cdLl.setVisibility(View.VISIBLE);
    }




    /**
     * Created by scr on 2016/05/25.
     * getStrTotalAttendedメソッド
     * 出席 〇 回という文字列を返します
     * return 出席 〇 回
     */
    String getStrTotalAttended(int attended){
        String str = this.getMainActivity().getString(R.string.total_attendee)
                + attended
                + this.getMainActivity().getString(R.string.number_of_times);
        return str;
    }
    /**
     * Created by scr on 2016/06/16.
     * getStrTotalLateメソッド
     * 遅刻 〇 回という文字列を返します
     * return 遅刻 〇 回
     */
    String getStrTotalLate(int late){
        String str = this.getMainActivity().getString(R.string.total_late)
                + late
                + this.getMainActivity().getString(R.string.number_of_times);
        return str;
    }


    /**
     * Created by scr on 2016/05/25.
     * getStrTotalAbsendedメソッド
     * 欠席 〇 回という文字列を返します
     * return 欠席 〇 回
     */
    String getStrTotalAbsended(int absented){
        String str = this.getMainActivity().getString(R.string.total_absentee)
                + absented
                + this.getMainActivity().getString(R.string.number_of_times);
        return str;
    }
    /**
     * Created by scr on 2016/12/13.
     * getStrTotalLeavEarlyメソッド
     * 早退 〇 回という文字列を返します
     * return 早退 〇 回
     */
    String getStrTotalLeaveEarly(int leaveEarly){
        String str = this.getMainActivity().getString(R.string.leave_early)
                + leaveEarly
                + this.getMainActivity().getString(R.string.number_of_times);
        return str;
    }





    /**
     * Created by scr on 2015/1/2.
     * setForgotESLメソッド
     * ESL忘れをセットするかどうか
     */
    private void setForgotESL(){

        if(this.transmitStateObject.getAttendanceTranmitEndTime() == null){
            //  出欠確認が終わっていれば表示もしない.
            return;
        }

        AttendanceObject ao = this.getAttObject();

        if(ao.getFirstAccessTime() != null){
            //  一度でもACKを返している
            return;
        }

        if(ao.getManualRequestAttendance() == PARAMETA_RELEASE_FORGOT
                && ao.getAttendanceTime() != null){
            //  ACkで出席を得ているから表示しない
            return;
        }

        showForgotESL();
    }

    /**
     * Created by scr on 2016/2/5.
     * setManualAttendanceメソッド
     * 手動による出席申請を行うかどうか
     */
    private void setManualAttendance(){
        if(this.transmitStateObject.getAttendanceTranmitEndTime() == null){
            //  出欠確認が終わっていれば表示もしない.
            return;
        }

        AttendanceObject ao = this.getAttObject();

        if(ao.getManualRequestAttendance() == PARAMETA_RELEASE_FORGOT
                && ao.getAttendanceTime() != null){
            //  ACkで出席を得ているから表示しない
            return;
        }

        showManualAttendance();
    }

    /**
     * Created by scr on 2016/2/5.
     * setReManulAttendanceメソッド
     * 手動による出席申請を行うかどうか
     */
    private void setReManualAttendance(){
        if(this.transmitStateObject.getReAttendanceEndTime() == null){
            if(this.transmitStateObject.getReAttendanceStartTime() != null){
                //  現在調査中です...
                getLinearLayout(dialog, R.id.be_in_room_wait_apply_linearLayout).setVisibility(View.VISIBLE);
            }
            //  再確認が終わっていれば表示もしない.
            return;
        }
        AttendanceObject ao = this.getAttObject();

        if(ao.getManualRequestReAttendance() == PARAMETA_RELEASE_FORGOT
                && ao.getReAttendancetime() != null){
            //  ACkで在室確認を得ているから表示しない
            return;
        }

        showManualBeInRoom();
    }
    /**
     * Created by scr on 2016/2/6.
     * setUndoManualAttendanceメソッド
     * プライバシー保護画面
     */
    private void setUndoManualAttendance(){
        if(this.transmitStateObject.getUndoTransmitEndTime() == null){
            //  プライバシー画面送信が終わっていれば表示もしない.
            return;
        }
        AttendanceObject ao = this.getAttObject();

        if(ao.getManualRequestClassLastAck() == PARAMETA_RELEASE_FORGOT
                && ao.getClassLastAckTime() != null){
            //  ACkで在室確認を得ているから表示しない
            return;
        }

        showManualBeInRoom();
    }

    /**
     * Created by scr on 2016/2/5.
     * setClassLastACKメソッド
     * 手動による授業最終ACK申請
     * */
    private void setClassLastACK(){
        if(this.transmitStateObject.getUndoTransmitEndTime() == null){
            //  プライバシー保護画面送信がが終わっていれば表示もしない.
            return;
        }
        AttendanceObject ao = this.getAttObject();

        if(ao.getManualRequestClassLastAck() == PARAMETA_RELEASE_FORGOT
                && ao.getClassLastAckTime() != null){
            //  ACkで在室確認を得ているから表示しない
            return;
        }

        showManualBeInRoom();
    }




    /**
     * Created by scr on 2015/1/2.
     * setForgotESLメソッド
     * ESL忘れを状況を表示します.
     */
    private void showForgotESL(){
        if(this.getAttObject().getReturndedResponse()){
            //  一度でもACKを返していれば以降進まない
            return;
        }
        //ESL忘れレイアウト
        LinearLayout layout = getLinearLayout(dialog, R.id.forgot_esl_linearLayout);
        //  解除ボタン
        forgotReleaseBtn     = getButton(dialog, R.id.dialog_custom_forgot_esl_release_button);
        //  申請ボタン
        forgotApplyBtn       = getButton(dialog, R.id.dialog_custom_forgot_esl_button);

        forgotReleaseBtn.setOnClickListener(forgotESLBtnListener);
        forgotApplyBtn.setOnClickListener(forgotESLBtnListener);

        //  android5.0から英字は大文字のみしか表示しなくなってしまった.
        //  それを防ぐため
        forgotReleaseBtn.setAllCaps(false);
        forgotApplyBtn.setAllCaps(false);

        //  忘れボタンのトグル機能
        togleForgotESLBtn();
        //  レイアウト表示
        layout.setVisibility(View.VISIBLE);
    }

    /**
     * Created by scr on 2016/2/5.
     * togleForgotESLBtnメソッド
     * ESL忘れボタンのトグル切り替え
     */
    private void togleForgotESLBtn(){
        AttendanceObject ao =   this.getAttObject();
        if(ao.getForgotApplyTime() != null){
            if(forgotReleaseBtn != null && forgotApplyBtn != null) {

                //ESL忘れ申請をすでに行っているので
                //解除ボタンを出す.
                forgotReleaseBtn.setVisibility(View.VISIBLE);
                forgotApplyBtn.setVisibility(View.INVISIBLE);
            }
        }else{
            if(forgotReleaseBtn != null && forgotApplyBtn != null){
                //ESL忘れ申請を行っていない.
                forgotReleaseBtn.setVisibility(View.INVISIBLE);
                forgotApplyBtn.setVisibility(View.VISIBLE);
            }
        }
    }
    /**
     * Created by scr on 2016/2/5.
     * togleManualAttBtnメソッド
     * 出席申請ボタンのトグル切り替え
     */
    private void togleManualAttBtn() {
        AttendanceObject ao = this.getAttObject();
        String attendanceTime = ao.getAttendanceTime();
        if (attendanceTime != null) {
            //  解除
            this.manualAttReleaseBtn.setVisibility(View.VISIBLE);
            this.manualAttRequestBtn.setVisibility(View.INVISIBLE);
        } else {
            //  申請
            this.manualAttReleaseBtn.setVisibility(View.INVISIBLE);
            this.manualAttRequestBtn.setVisibility(View.VISIBLE);
        }
    }
    /**
     * Created by scr on 2016/2/6.
     * togleBeInRoomBtnメソッド
     * 在室申請ボタンのトグル切り替え
     */
    private void togleBeInRoomBtn() {
        AttendanceObject ao = this.getAttObject();
        String reAttendancetime = ao.getReAttendancetime();
        String classLastAckTime = ao.getClassLastAckTime();

        if(nowAttendanceStage == this.MANUAL_REQUEST_TYPE_RE_ATTENDANCE){
            if (reAttendancetime != null) {
                //解除
                this.manualAttReleaseBtn.setVisibility(View.VISIBLE);
                this.manualAttRequestBtn.setVisibility(View.INVISIBLE);
            } else {
                //申請
                this.manualAttReleaseBtn.setVisibility(View.INVISIBLE);
                this.manualAttRequestBtn.setVisibility(View.VISIBLE);
            }
        }else if(nowAttendanceStage == this.MANUAL_REQUEST_TYPE_UNDO){
            if (classLastAckTime != null) {
                //解除
                this.manualAttReleaseBtn.setVisibility(View.VISIBLE);
                this.manualAttRequestBtn.setVisibility(View.INVISIBLE);
            } else {
                //申請
                this.manualAttReleaseBtn.setVisibility(View.INVISIBLE);
                this.manualAttRequestBtn.setVisibility(View.VISIBLE);
            }
        }
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
            }
        }
    };


    /**
     * Created by scr on 2015/1/4.
     * View.manualAttBtnListener
     * ESL忘れ関係ボタンのリスナーです.
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
            }
        }
    };
    /**
     * Created by scr on 2016/2/6.
     * beInRoomtBtnListener
     * 在室確認ボタンリスナー.
     */
    private View.OnClickListener beInRoomtBtnListener = new View.OnClickListener() {
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.dialog_custom_be_in_room_apply_release_button:
                    //  在室取り消し
                    beInRoomRelease();
                    break;

                case R.id.dialog_custom_be_in_room_apply_button:
                    //  在室確認した
                    beInRoomRequest();
                    break;
            }
        }
    };

    /**
     * Created by scr on 2016/1/28.
     * attendApplyReleaseメソッド
     * そのだ教授解除
     */
    private void attendApplyRelease(){
        this.selectRequestType = this.MANUAL_REQUEST_TYPE_ATTENDANCE;
        selectApplyType = this.MANUAL_NOT_REQUEST;
        String attId = this.getAttObject().getAttendanceId();
        String url = URL.getAttendLeak(attId, selectApplyType);
        ackLeakrequest(url);
    }
    /**
     * Created by scr on 2016/2/6.
     * beInRoomReleaseメソッド
     * 在室取り消しメソッド
     */
    private void beInRoomRelease(){
        this.selectRequestType = this.MANUAL_REQUEST_TYPE_RE_ATTENDANCE;
        selectApplyType = this.MANUAL_NOT_REQUEST;
        String attId = this.getAttObject().getAttendanceId();
        String url = URL.getReAttendLeak(attId, selectApplyType);
        this.tapStudent.getAttendanceObject().manualRequestReAttendance = this.MANUAL_NOT_REQUEST;
        this.tapStudent.getAttendanceObject().reAttendancetime = null;
        ackLeakrequest(url);
    }


    /**
     * Created by scr on 2016/1/28.
     * attendApplyRequestメソッド
     * そのだ教授申請漏れへの対応
     */
    void attendApplyRequest(){
        this.selectRequestType = this.nowAttendanceStage;
        selectApplyType = this.MANUAL_REQUEST;
        String attId = this.getAttObject().getAttendanceId();
        String url = URL.getAttendLeak(attId, selectApplyType);
        ackLeakrequest(url);
    }

    /**
     * Created by scr on 2016/2/6.
     * beInRoomRequestメソッド
     * 在室確認した
     */
    private void beInRoomRequest(){
        this.selectRequestType = this.nowAttendanceStage;
        selectApplyType = this.MANUAL_REQUEST;
        String attId = this.getAttObject().getAttendanceId();
        this.tapStudent.getAttendanceObject().manualRequestReAttendance = this.MANUAL_REQUEST;
        this.tapStudent.getAttendanceObject().reAttendancetime = " ";
        String url = URL.getReAttendLeak(attId, selectApplyType);
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
            doBeInRoom();
        }else if(this.selectRequestType == this.MANUAL_REQUEST_TYPE_UNDO){
            //  プライバシー保護画面
            doBeInRoom();
        }

        if(af != null){
            //  グリッドビュー再描画
            af.adapter.notifyDataSetChanged();
        }
        if(sf != null){
            if(sf.roomView != null){
                sf.roomView.invalidate();
            }
        }
    }

    class AttendanceRelation{
        @SerializedName("attendance_relation")
        AttendanceObject ao;
    }

    /**
     * Created by scr on 2016/2/4.
     * doLeakメソッド
     * ACk漏れ申請又は解除後のダイアログの選択ボタンの対応を行います.
     */
    private void doAttendance(){
        if(this.selectApplyType == this.MANUAL_NOT_REQUEST){
            //  解除非表示
            this.manualAttReleaseBtn.setVisibility(View.INVISIBLE);
            //  申請表示
            this.manualAttRequestBtn.setVisibility(View.VISIBLE);
            //  ESL忘れ受付も取り消す
            forgotESLRelease();
        }else {
            //  解除表示
            this.manualAttReleaseBtn.setVisibility(View.VISIBLE);
            //  申請非表示
            this.manualAttRequestBtn.setVisibility(View.INVISIBLE);
        }
        //  手動出席切り替え
        this.togleManualAttBtn();
        //
        redrawAttendanceCount();

        if( this.getMainActivity() != null ){
            //  フレーム再描画
            redrawFrame();
            //
            int dialogFrameColor = getDialogFrameColor();
            setCloseBtn(dialogFrameColor);
        }
    }

    private void doBeInRoom(){
        this.togleBeInRoomBtn();
    }


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
     * Created by scr on 2015/1/4.
     * forgotESLReleaseメソッド
     * ESL忘れ登録手続きをこちらでおこいます.
     */
    private void forgotESLRequest(){
        //申請
        forgotAppy(this.PARAMETA_FORGOT_ESL);
    }


    /**
     * Created by scr on 2015/3/13.
     * forgotAppyメソッド
     * ESL忘れ又はESL忘れ解除をネットワークDBに保存する.
     * @param parameta 1 ESL忘れ 0:ESL忘れ解除
     */
    private void forgotAppy(final int parameta){
        AttendanceObject ao = this.getAttObject();
        String url = URL.getForgotAppy(parameta, ao.getAttendanceId());
        JsonObjectRequest request = new JsonObjectRequest(url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        forgotApplyNextProcess(response, parameta);
                    }
                }, new Response.ErrorListener() {
            @Override public void onErrorResponse(VolleyError error) {

                //Toast.makeText(this.sidf.getActivity(), "Unable to fetch data: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        AppController.getInstance(this.getActivity()).getRequestQueue().add(request);
    }
    /**
     * forgotApplyNextProcessメソッド
     * 忘れ受付次の処理
     */
    private void forgotApplyNextProcess(JSONObject response,int parameta){
        AttendanceObject att = this.getAttObject();
        //  値をメモリに強制セット
        if(parameta == StudentInfoDialogFragnemt.PARAMETA_FORGOT_ESL){
            //att.setManualRequestAttendance(1);
            //att.setAttendanceTime("");
            att.setFogotApplytTime("");
        }else{
            //att.setManualRequestAttendance(0);
            //att.setAttendanceTime(null);
            att.setFogotApplytTime(null);
        }
        //  忘れボタンのトグル切り替え
        togleForgotESLBtn();
        //  フレーム再描画
        redrawFrame();
        //
        redrawAttendanceCount();
        //
        int dialogFrameColor = getDialogFrameColor();
        setCloseBtn(dialogFrameColor);
    }

    /**
     * Created by scr on 2016/02/05
     * showManualAttendanceメソッド
     * 手動出席申請レイアウト表示
     */
    private void showManualAttendance(){
        //手動出席レイアウト
        LinearLayout layout = getLinearLayout(dialog, R.id.attend_apply_linearLayout);
        //  解除ボタン
        manualAttReleaseBtn     = getButton(dialog, R.id.dialog_custom_attend_apply_release_button);
        //  申請ボタン
        manualAttRequestBtn       = getButton(dialog, R.id.dialog_custom_attend_apply_button);

        manualAttReleaseBtn.setOnClickListener(manualRequestBtnListener);
        manualAttRequestBtn.setOnClickListener(manualRequestBtnListener);


        //  手動出席トグル機能
        togleManualAttBtn();
        //  レイアウト表示
        layout.setVisibility(View.VISIBLE);
    }

    /**
     * Created by scr on 2016/02/06
     * showManualBeInRoomメソッド
     * 在室確認レイアウト表示
     */
    private void showManualBeInRoom(){
        //在室確認レイアウト
        LinearLayout layout = getLinearLayout(dialog, R.id.be_in_room_apply_linearLayout);
        //  解除ボタン
        manualAttReleaseBtn     = getButton(dialog, R.id.dialog_custom_be_in_room_apply_release_button);
        //  申請ボタン
        manualAttRequestBtn       = getButton(dialog, R.id.dialog_custom_be_in_room_apply_button);

        manualAttReleaseBtn.setOnClickListener(beInRoomtBtnListener);
        manualAttRequestBtn.setOnClickListener(beInRoomtBtnListener);

        togleBeInRoomBtn();
        //  レイアウト表示
        layout.setVisibility(View.VISIBLE);
    }

    /**
     * Created by scr on 2016/12/16
     * showBeInRoomLayoutメソッド
     * 在室確認時に在室確認対象から漏れた学生のみ表示するレイアウト
     * 在室確認NACKに対する個別対応用
     * ⓪ 在室確認対象学生 ① 在室確認を手動、② 在室ACK無し
     * のいずれかを満たす場合、在室ACKを手動で変更可能なレイアウトが表示される
     */
    void showBeInRoomLayout(){
        String reTransmitEndTime = getMainActivity().getClassObject().getTransmitStateObject().getReAttendanceEndTime();
        String reAttendanceTarget = tapStudent.getAttendanceObject().mReAttendanceTarget;
        int reAttendanceManual = tapStudent.getAttendanceObject().getManualRequestReAttendance();
        String reAttendanceTime = tapStudent.getAttendanceObject().getReAttendancetime();
        if( reTransmitEndTime != null && reAttendanceTarget != null){
            if( reAttendanceManual == AttendanceObject.MANUAL_ATTENDANCE
                || reAttendanceTime == null){
                LinearLayout beInRoomLayout = (LinearLayout) dialog.findViewById(R.id.dialog_custom_be_in_room_apply_ll);
                beInRoomLayout.setVisibility(View.VISIBLE);
                RadioGroup radioGroup = (RadioGroup) dialog.findViewById(R.id.dialog_custom_be_in_room_apply_rg);
                // ラジオグループのチェック状態が変更された時に呼び出されるコールバックリスナーを登録します
                radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    // ラジオグループのチェック状態が変更された時に呼び出されます
                    // チェック状態が変更されたラジオボタンのIDが渡されます
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        RadioButton radioButton = (RadioButton) dialog.findViewById(checkedId);
                        if( checkedId == R.id.dialog_custom_be_in_room_apply_rb ){
                            //  手動在室申請
                            beInRoomRequest();
                        }else if( checkedId == R.id.dialog_custom_not_be_in_room_apply_rb ){
                            //  手動不在申請
                            beInRoomRelease();
                        }
                        //  授業者一覧リストビューを再描画
                        af.adapter.notifyDataSetChanged();
                    }
                });
                if( reAttendanceTime != null ){
                    radioGroup.check(R.id.dialog_custom_be_in_room_apply_rb);
                }else{
                    radioGroup.check(R.id.dialog_custom_not_be_in_room_apply_rb);
                }
            }
        }
    }



    /**
     * Created by scr on 2016/02/05
     * redrawFrameメソッド
     * 個人情報確認フレームを再描画する
     */
    public void redrawFrame(){
        //  ダイアログフレーム色
        int dialogFrameColor = this.getDialogFrameColor();
        //  フレーム色セット
        this.setFrameBackgoundColor(dialogFrameColor);
        //  閉じるボタン
        this.setCloseBtn(dialogFrameColor);
    }

    /**
     * Created by scr on 2016/02/05
     * redrawAttendanceCountメソッド
     * タイトルバーの出欠者をセットしなおす.
     */
    private void redrawAttendanceCount(){
        if(af != null){
            //  出席者数の再描画を行う.
            this.af.setReAttendanceStatusTextView();
        }
    }


    /**
     * Created by si on 2016/02/05.
     * switchDialogFragmentメソッド
     * ダイアログフラグメントに表示する内容の切り替えを行います.
     **/
    private void switchDialogFragment() {
        AttendanceObject ao = getAttObject();
        int attState = ao.getStateAttendance();
        switch (attState) {
            case AttendanceObject.STATE_ABSENCE:

                break;

            case AttendanceObject.STATE_ATTENDANCE:
                break;
        }

    }
    //  顔画像をセットする
    void setFaceImage(StudentObject so){
        /*LinearLayout layout = getLinearLayout(dialog, R.id.dialog_custom_face_image_ll);
        ImageView iv =  (ImageView) dialog.findViewById(R.id.dialog_custom_face_image_iv);
        layout.setVisibility(View.VISIBLE);
        String url = so.mFaceUrl;
        Bitmap bitmap = getFaceImageFromLocalDB( url );
        if(  bitmap != null ){
            //  ローカルDBの顔画像を使用する
            iv.setImageBitmap(bitmap);
        }else{
            //  ネットワークに取得しに行く
            iv.setVisibility(View.INVISIBLE);
            iv.setTag( url );
            FaceImageTask mFaceImageTask = new FaceImageTask(this.getActivity().getApplicationContext(), iv, url);
            mFaceImageTask.execute(url);
        }*/
        ImageView iv =  (ImageView) dialog.findViewById(R.id.dialog_custom_face_image_iv);
        String url = so.mFaceUrl;
        Bitmap bitmap = getFaceImageFromLocalDB( url );
        if(  bitmap != null ){
            //  ローカルDBの顔画像を使用する
            iv.setImageBitmap(bitmap);
        }else{
            //  ネットワークに取得しに行く
            iv.setVisibility(View.INVISIBLE);
            iv.setTag( url );
            FaceImageTask mFaceImageTask = new FaceImageTask(this.getActivity().getApplicationContext(), iv, url);
            mFaceImageTask.execute(url);
        }
    }

    Bitmap getFaceImageFromLocalDB(String url){
        Bitmap bitmap = null;
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        RealmResults<FaceImageRealmObject> results = realm.where(FaceImageRealmObject.class)
                .equalTo("url", url)
                .findAll();
        int registeredCount = results.size();
        if(registeredCount == 1){
            byte[] faceImageByts = results.get(0).getFaceImage();
            if( faceImageByts != null ){
                bitmap = BitmapFactory.decodeByteArray(faceImageByts, 0, faceImageByts.length);
            }
        }
        realm.commitTransaction();
        realm.close();
        return bitmap;
    }
    /***
     *  出席・遅刻・欠席とみなすゾーン
     * **/
    LinearLayout mRegaredeSelectLl;     //  選択レイアウト
    LinearLayout mRegaredeAsCbLl;
    CheckBox mRegaredeAsAttendanceCb;
    CheckBox mRegaredeAsLateCb;
    CheckBox mRegaredeAsAbsentCb;
    CheckBox mRegaredeAsLeaveCb;
    LinearLayout mRegaredeAsReasonLl;
    RadioGroup mRegarededAsReasonRg;
    RadioButton mReasonForgotRb;    //  理由のSonoRIs忘れ項目
    Button   mRegaredeAsApplyBtn;
    Button   mRegaredeAsUndoBtn;
    RegardedAsCb mRegardedAsCb;
    TextView mAlertUnselectedReasonTv;

    /**
     * Created by si on 2016/06/18.
     * showRegardedAsCheckBoxLayoutメソッド
     * 出席とみなす＆出席とみなさない　と 遅刻とみなす＆遅刻とみなさないの表示をコントロールします.
     **/
    void showRegardedAsCheckBoxLayout(){
        mRegardedAsCb = new RegardedAsCb(this);
    }
    /***
     *  顔画像ゾーン
     * **/


}
