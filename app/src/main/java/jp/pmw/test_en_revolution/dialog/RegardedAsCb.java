package jp.pmw.test_en_revolution.dialog;
import android.content.Context;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import jp.pmw.test_en_revolution.AttendanceObject;
import jp.pmw.test_en_revolution.R;
import jp.pmw.test_en_revolution.config.URL;
import jp.pmw.test_en_revolution.confirm_class_plan.Attendance;
import jp.pmw.test_en_revolution.network.MyOkhttp;

/**
 * Created by si on 2016/06/18.
 */
public class RegardedAsCb {

    //  忘れ
    private static final int S_FORGOT = 1;
    //  忘れでない
    private static final int S_NOT_FORGOT = 0;

    //  学生ダイアログフラグメント
    StudentInfoDialogFragnemt mStudentInfoDialogFragnemt;
    //  出席チェックボックス状態
    boolean mAttendanceCbState = false;
    //  遅刻チェックボックス状態
    boolean mLateCbState = false;
    //  欠席チェックボックス状態
    boolean mAbsentCbState = false;
    //  忘れとみとめるチェックボックス状態
    boolean mForgotCbState = false;

    //  コンストラクタ
    RegardedAsCb(StudentInfoDialogFragnemt sidf){
        this.mStudentInfoDialogFragnemt = sidf;
        onCreate();
        setOnClickListener();
        initCheckBoxState();
    }
    /**
     *  onCreateメソッド
     * */
    void onCreate(){
        //  おおもとレイアウト
        mStudentInfoDialogFragnemt.mRegaredeAsCbLl = mStudentInfoDialogFragnemt.getLinearLayout(mStudentInfoDialogFragnemt.dialog, R.id.dialog_custom_regarded_as_checkbox_ll);
        //  選択レイアウト
        mStudentInfoDialogFragnemt.mRegaredeSelectLl = mStudentInfoDialogFragnemt.getLinearLayout(mStudentInfoDialogFragnemt.dialog, R.id.dialog_custom_regarded_as_child1_ll);
        mStudentInfoDialogFragnemt.mRegaredeAsAttendanceCb = mStudentInfoDialogFragnemt.getCheckBox(mStudentInfoDialogFragnemt.dialog, R.id.dialog_custom_regarded_as_attendance_cb);
        mStudentInfoDialogFragnemt.mRegaredeAsLateCb = mStudentInfoDialogFragnemt.getCheckBox(mStudentInfoDialogFragnemt.dialog, R.id.dialog_custom_regarded_as_late_cb);
        mStudentInfoDialogFragnemt.mRegaredeAsAbsentCb = mStudentInfoDialogFragnemt.getCheckBox(mStudentInfoDialogFragnemt.dialog, R.id.dialog_custom_regarded_as_absent_cb);
        //mStudentInfoDialogFragnemt.mRegaredeAsForgotCb = mStudentInfoDialogFragnemt.getCheckBox(mStudentInfoDialogFragnemt.dialog, R.id.dialog_custom_regarded_as_forgot_cb);
        //  理由レイアウト
        mStudentInfoDialogFragnemt.mRegaredeAsReasonLl = mStudentInfoDialogFragnemt.getLinearLayout(mStudentInfoDialogFragnemt.dialog, R.id.dialog_custom_regarded_as_reason_ll);
        mStudentInfoDialogFragnemt.mRegarededAsReasonRg = (RadioGroup) mStudentInfoDialogFragnemt.dialog.findViewById(R.id.dialog_custom_regarded_as_reason_rg);

        //mStudentInfoDialogFragnemt.mRegaredeAsAssistTv = mStudentInfoDialogFragnemt.getTextView(mStudentInfoDialogFragnemt.dialog, R.id.dialog_custom_regarded_as_assist_tv);

        mStudentInfoDialogFragnemt.mRegaredeAsApplyBtn = mStudentInfoDialogFragnemt.getButton(mStudentInfoDialogFragnemt.dialog, R.id.dialog_custom_regarded_as_apply_btn);
        mStudentInfoDialogFragnemt.mRegaredeAsUndoBtn = mStudentInfoDialogFragnemt.getButton(mStudentInfoDialogFragnemt.dialog, R.id.dialog_custom_regarded_as_return_undo_btn);

        //  確認レイアウト
        //mStudentInfoDialogFragnemt.mRegaredeConfirmLl = mStudentInfoDialogFragnemt.getLinearLayout(mStudentInfoDialogFragnemt.dialog, R.id.dialog_custom_regarded_as_child2_ll);
        //mStudentInfoDialogFragnemt.mmRegaredeConfirmTv = mStudentInfoDialogFragnemt.getTextView(mStudentInfoDialogFragnemt.dialog, R.id.dialog_custom_regarded_as_child2_tv);
    }
    /**
     *  setOnClickListenerメソッド
     * */
    void setOnClickListener() {
        mStudentInfoDialogFragnemt.mRegaredeAsAttendanceCb.setOnClickListener(AttendanceCheckBoxClickListener);
        mStudentInfoDialogFragnemt.mRegaredeAsLateCb.setOnClickListener(LateCheckBoxClickListener);
        mStudentInfoDialogFragnemt.mRegaredeAsAbsentCb.setOnClickListener(AbsentCheckBoxClickListener);
        //mStudentInfoDialogFragnemt.mRegaredeAsForgotCb.setOnClickListener(ForgotCheckBoxClickListener);
        mStudentInfoDialogFragnemt.mRegaredeAsApplyBtn.setOnClickListener(ApplyBtnClickListener);
        mStudentInfoDialogFragnemt.mRegaredeAsUndoBtn.setOnClickListener(UndoBtnClickListener);
    }
    /**
     * initCheckBoxStateメソッド
     * 初期状態にチェックボックスをセットする.
     * */
    void initCheckBoxState(){
        undoCheckBoxState();
        show();
    }
    /**
     *  showメソッド
     *  ダイアログを表示する.(もしひょじする必要がなければ表示しない)
     * */
    void show(){
        if(mStudentInfoDialogFragnemt.transmitStateObject.getAttendanceTranmitEndTime() == null){
            //  出欠確認が終わっていれば表示もしない.
            return;
        }
        AttendanceObject ao = mStudentInfoDialogFragnemt.getAttObject();
        if(ao.getManualRequestAttendance() == StudentInfoDialogFragnemt.PARAMETA_RELEASE_FORGOT
                && ao.getAttendanceTime() != null){
            //  ACkで出席を得ているから表示しない
            return;
        }
        mStudentInfoDialogFragnemt.mRegaredeAsCbLl.setVisibility(View.VISIBLE);
    }
    /**
     *  getMyContextメソッド
     *  コンテキストを取得する.
     * */
    Context getMyContext(){
        return this.mStudentInfoDialogFragnemt.getActivity();
    }

    /**
     *  AttendanceCheckBoxClickListenerリスナー
     *  出席とみとめるチェックボックスのリスナー
     * */
    View.OnClickListener AttendanceCheckBoxClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            chkCanSelected();
        }
    };
    /**
     *  LateCheckBoxClickListenerリスナー
     *  遅刻チェックボックスのリスナー
     * */
    View.OnClickListener LateCheckBoxClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            chkCanSelected();
        }
    };
    /**
     *  AbsentCheckBoxClickListenerリスナー
     *  欠席チェックボックスのリスナー
     * **/
    View.OnClickListener AbsentCheckBoxClickListener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            chkCanSelected();
        }
    };

    /**
     *  ForgotCheckBoxClickListenerリスナー
     *  SonoRIs忘れチェックボックスのリスナー
     * */
    View.OnClickListener ForgotCheckBoxClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            chkCanSelected();
        }
    };
    /**
     *  ApplyBtnClickListenerリスナー
     * */
    View.OnClickListener ApplyBtnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            updateCheckBox();
        }
    };
    /**
     *  UndoBtnClickListenerリスナー
     * */
    View.OnClickListener UndoBtnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            undoCheckBoxState();
        }
    };

    /**
     *  getAttendanceCheckBoxCheckedChangedメソッド
     * */
    boolean getAttendanceCheckBoxCheckedChanged(){
        CheckBox cb = mStudentInfoDialogFragnemt.mRegaredeAsAttendanceCb;
        return cb.isChecked();
    }
    /**
     *  getLateCheckBoxCheckedChangedメソッド
     * */
    boolean getLateCheckBoxCheckedChanged(){
        CheckBox cb = mStudentInfoDialogFragnemt.mRegaredeAsLateCb;
        return cb.isChecked();
    }
    /**
     *  getAbsentCheckBoxCheckedChangedメソッド
     * */
    boolean getAbsentCheckBoxCheckedChanged(){
        CheckBox cb = mStudentInfoDialogFragnemt.mRegaredeAsAbsentCb;
        return cb.isChecked();
    }
    /**
     *  getForgotCheckBoxCheckedChangedメソッド
     * */
    /*boolean getForgotCheckBoxCheckedChanged(){
        CheckBox cb = mStudentInfoDialogFragnemt.mRegaredeAsForgotCb;
        return cb.isChecked();
    }*/
    /**
     *  chkCanSelectedメソッド
     *  チェックボックスに選択された状態が可能かどうかを確認します.
     *  ■受け付けないバターン
     *  ①   出席と遅刻と欠席のどちらかがチェックされていない状態で、SonoRIs忘れをチェックした
     *  ②   出席とみなすと遅刻とみなすがともにチェックされた
     * */
    void chkCanSelected(){
        boolean newAttendanceCbState = getAttendanceCheckBoxCheckedChanged();
        boolean newLateCbState = getLateCheckBoxCheckedChanged();
        boolean newAbsentCbState = getAbsentCheckBoxCheckedChanged();
        //boolean newForgotCbState = getForgotCheckBoxCheckedChanged();
        //  ①
        /*if( newForgotCbState == true && newAttendanceCbState == false && newLateCbState == false){
            String str1 = this.mStudentInfoDialogFragnemt.getActivity().getString(R.string.str_regarded_as_attendance);
            String str2 = this.mStudentInfoDialogFragnemt.getActivity().getString(R.string.str_regarded_as_late);
            Toast.makeText(this.mStudentInfoDialogFragnemt.getMainActivity(), "「"+str1+"」又は「"+str2+"」を選択してください.", Toast.LENGTH_LONG).show();
            this.mForgotCbState = false;
        }else{
            this.mForgotCbState = newForgotCbState;
        }*/
        // 　②
        /*if( newAttendanceCbState == true && newLateCbState == true  ){
            this.mAttendanceCbState = !(this.mAttendanceCbState);
            this.mLateCbState = !(this.mLateCbState);
        }else{
            this.mAttendanceCbState = newAttendanceCbState;
            this.mLateCbState = newLateCbState;
        }*/
        if( newAttendanceCbState == false && newLateCbState == false && newAbsentCbState == false ){
            newAttendanceCbState = this.mAttendanceCbState;
            newLateCbState = this.mLateCbState;
            newAbsentCbState = this.mAbsentCbState;
        }else{
            if( newAttendanceCbState ){
                if( this.mAttendanceCbState == newAttendanceCbState ){
                    newAttendanceCbState = !(newAttendanceCbState);
                }
            }
            if( newLateCbState ){
                if( this.mLateCbState == newLateCbState ){
                    newLateCbState = !(newLateCbState);
                }
            }
            if( newAbsentCbState ){
                if( this.mAbsentCbState == newAbsentCbState ){
                    newAbsentCbState = !(newAbsentCbState);
                }
            }
        }

        this.mAttendanceCbState = newAttendanceCbState;
        this.mLateCbState = newLateCbState;
        this.mAbsentCbState = newAbsentCbState;

        setChangeSelectedState();
        setAssistMessage();
    }
    /**
     *  chkTheSameAsLastCheckBoxメソッド
     *  @return {true}変更適用前と同じ {false}違う
     * */
    boolean chkTheSameAsLastCheckBox(){
        boolean same = false;

        int beforeState = getStateAttendance();

        boolean newAttendanceCbState = this.mAttendanceCbState;//getAttendanceCheckBoxCheckedChanged();
        boolean newLateCbState = this.mLateCbState;//getLateCheckBoxCheckedChanged();
        boolean newAbsentCbState = this.mAbsentCbState;
        //boolean newForgotCbState = this.mForgotCbState;//getForgotCheckBoxCheckedChanged();
        //String forgotTime = this.mStudentInfoDialogFragnemt.getAttObject().getForgotApplyTime();



        /*if( beforeState == AttendanceObject.STATE_ATTENDANCE && forgotTime != null){
            if( newAttendanceCbState == true && newLateCbState == false && newForgotCbState == true){
                //  変更前出席かつSonoRIs忘れ
                same =  true;
            }
        }else if( beforeState == AttendanceObject.STATE_LATE && forgotTime != null){
            if( newAttendanceCbState == false && newLateCbState == true && newForgotCbState == true){
                //  変更前遅刻かつSonoRIs忘れ
                same = true;
            }
        }*/

        /*if( beforeState == AttendanceObject.STATE_ATTENDANCE && forgotTime == null){
            if( newAttendanceCbState == true && newLateCbState == false && newForgotCbState == false ){
                //  変更前出席
                same = true;
            }
        }else if( beforeState == AttendanceObject.STATE_LATE && forgotTime == null){
           if( newAttendanceCbState == false && newLateCbState == true && newForgotCbState == false ){
               //   変更前遅刻
               same = true;
           }
        }else if( beforeState == AttendanceObject.STATE_ABSENCE && forgotTime == null){
           if( newAttendanceCbState == false && newLateCbState == false && newForgotCbState == false ){
               //   変更前欠席
               same = true;
           }
        }*/

        if( beforeState == AttendanceObject.STATE_ATTENDANCE){
            if( newAttendanceCbState == true && newLateCbState == false && newAbsentCbState == false ){
                //  変更前出席
                same = true;
            }
        }else if( beforeState == AttendanceObject.STATE_LATE){
            if( newAttendanceCbState == false && newLateCbState == true && newAbsentCbState == false ){
                //   変更前遅刻
                same = true;
            }
        }else if( beforeState == AttendanceObject.STATE_ABSENCE){
            if( newAttendanceCbState == false && newLateCbState == false && newAbsentCbState == true ){
                //   変更前欠席
                same = true;
            }
        }

        return same;
    }
    /**
     *  setChangeSelectedStateメソッド
     *  変更後のデータをチェックボックスに反映する.
     * */
    void setChangeSelectedState(){
        this.mStudentInfoDialogFragnemt.mRegaredeAsAttendanceCb.setChecked(this.mAttendanceCbState);
        this.mStudentInfoDialogFragnemt.mRegaredeAsLateCb.setChecked(this.mLateCbState);
        this.mStudentInfoDialogFragnemt.mRegaredeAsAbsentCb.setChecked(this.mAbsentCbState);
        /*if( chkYeaACK() ){
            this.mForgotCbState = false;
            this.mStudentInfoDialogFragnemt.mRegaredeAsForgotCb.setVisibility(View.INVISIBLE);
        }
        this.mStudentInfoDialogFragnemt.mRegaredeAsForgotCb.setChecked(this.mForgotCbState);*/
    }
    /**
     *  chkYeaACKメソッド
     *  一度でもACKを返しているか確認する.
     *  @return {true} 一度でもACKを応答あり {false}一度もACK応答なし
     * */
    boolean chkYeaACK(){
        AttendanceObject ao = this.mStudentInfoDialogFragnemt.getAttObject();
        if( ao.getFirstAccessTime() != null ){
            //  入室ACKあり
            return true;
        }
        if( ao.getManualRequestAttendance() == AttendanceObject.MANUAL_NOT
                && ao.getAttendanceTime() != null ){
            // 出席ACKあり
            return true;
        }
        if( ao.getManualRequestReAttendance() == AttendanceObject.MANUAL_NOT
                && ao.getReAttendancetime() != null ){
            //  再出席ACKあり
            return true;
        }
        if( ao.getManualRequestClassLastAck() == AttendanceObject.MANUAL_NOT
                && ao.getClassLastAckTime() != null ){
            //  授業最終ACKあり
            return true;
        }
        return false;
    }
    /**
     * setAssistMessageメソッド
     * 変更前とチェックボックスの選択内容が事なれば、
     * 「変更適用」ボタンをタップしてくださいとテキストビューにセットする
     * */
    void setAssistMessage(){
        boolean same = chkTheSameAsLastCheckBox();
        if( same ){
            //this.mStudentInfoDialogFragnemt.mRegaredeAsAssistTv.setText("　");
            this.mStudentInfoDialogFragnemt.mRegaredeAsApplyBtn.setVisibility(View.INVISIBLE);
            this.mStudentInfoDialogFragnemt.mRegaredeAsUndoBtn.setVisibility(View.INVISIBLE);
            this.mStudentInfoDialogFragnemt.mRegaredeAsReasonLl.setVisibility(View.INVISIBLE);
        }else{
            //this.mStudentInfoDialogFragnemt.mRegaredeAsAssistTv.setText("現在の選択状態はまだ適用されていません.");
            this.mStudentInfoDialogFragnemt.mRegaredeAsApplyBtn.setVisibility(View.VISIBLE);
            this.mStudentInfoDialogFragnemt.mRegaredeAsUndoBtn.setVisibility(View.VISIBLE);
            if( this.mAbsentCbState ){
                this.mStudentInfoDialogFragnemt.mRegaredeAsReasonLl.setVisibility(View.INVISIBLE);
            }else{
                //  欠席じゃなければ理由を問うレイアウトを表示する
                this.mStudentInfoDialogFragnemt.mRegaredeAsReasonLl.setVisibility(View.VISIBLE);
            }
        }
     }
     /**
     *  getStateAttendanceメソッド
     *  @return int 出席状態(int)
     * */
    int getStateAttendance(){
        return this.mStudentInfoDialogFragnemt.tapStudent.getAttendanceObject().getStateAttendance();
    }
    /**
     *  undoCheckBoxStateメソッド
     *  チェックボックスの状態を、「変更を適用する」ボタンタップ前の状態に戻す.
     * */
    void undoCheckBoxState(){
        //  変更を適用するボタンタップ前の出席・遅刻・忘れのチェックボックスの状態に戻す.
        int beforeState = getStateAttendance();
        if( beforeState == AttendanceObject.STATE_ATTENDANCE){
            undoCheckBoxAttendanceState();
        }else if( beforeState == AttendanceObject.STATE_LATE ){
            undoCheckBoxLateState();
        }else{
            undoCheckBoxAbsentState();
        }
        clearSelectedRadioGropu();
        setAssistMessage();
        setChangeSelectedState();
    }
    /**
     *  clearSelectedRadioGropuメソッド
     *  「キャンセル」ボタンタップ時に、
     *  ラジオボタン選択内容も初期化する.
     * */
    void clearSelectedRadioGropu(){
        this.mStudentInfoDialogFragnemt.mRegarededAsReasonRg.clearCheck();
    }

    /**
     *  getUndoForgotStateメソッド
     *  変更適用前の忘れ状態を
     *  @reutrn {true} 忘れの状態 {false}持っている状態
     * */
    boolean getUndoForgotState(){
        AttendanceObject ao = this.mStudentInfoDialogFragnemt.tapStudent.getAttendanceObject();
        String forgotTime = ao.getForgotApplyTime();
        if(forgotTime != null){
            return true;
        }
        return false;
    }
    /**
     *  undoCheckBoxAttendanceStateメソッド
     *  出席とみなす状態に戻す.
     * */
    void undoCheckBoxAttendanceState(){
        this.mAttendanceCbState = true;
        this.mLateCbState = false;
        this.mAbsentCbState = false;
        //this.mForgotCbState = getUndoForgotState();
    }
    /**
     *  undoCheckBoxLateStateメソッド
     *  遅刻とみなす状態に戻す
     * */
    void undoCheckBoxLateState(){
        this.mAttendanceCbState = false;
        this.mLateCbState = true;
        this.mAbsentCbState = false;
        //this.mForgotCbState = getUndoForgotState();
    }

    /**
     *  undoCheckBoxAbsentStateメソッド
     *  欠席とみなす状態に戻す.
     * */
    void undoCheckBoxAbsentState(){
        this.mAttendanceCbState = false;
        this.mLateCbState = false;
        this.mAbsentCbState = true;
        //this.mForgotCbState = false;
    }

    /**
     *  getSelectedReasenメソッド
     *  選択された理由を取得します.
     * */
    RegardedAsReasonEnum getSelectedReasen(){
        int resorceId = this.mStudentInfoDialogFragnemt.mRegarededAsReasonRg.getCheckedRadioButtonId();
        return RegardedAsReasonEnum.valueOf(resorceId);
        /*if( resorceId != -1){
            RadioButton radioButton = this.mStudentInfoDialogFragnemt.getRadioButton(resorceId);
            Toast.makeText(this.mStudentInfoDialogFragnemt.getActivity(),
                    "onCheckedChanged():" + radioButton.getText(),
                    Toast.LENGTH_SHORT).show();
        }*/
        //return reason;
    }
    /**
     *  updateCheckBoxメソッド
     *  チェックボックスに選択された状態をDBや端末のメモリデータに反映する.
     * */
    void updateCheckBox(){
        if( chkTheSameAsLastCheckBox() ){
            //前回と同じであればネットワークにアクセスしない.
            return;
        }
        //  選択された理由
        RegardedAsReasonEnum reasonEnum = getSelectedReasen();

        if( this.mAttendanceCbState ){
            //  出席とみなす
            updateCheckBoxAttendanceState(reasonEnum);
        }else if( this.mLateCbState ){
            //  遅刻とみなす
            updateCheckBoxLateState(reasonEnum);
        }else{
            //  欠席とみなします
            updateCheckBoxAbsentState();
        }

    }
    /**
     * updateCheckBoxAttendanceStateメソッド
     * 出席とみなす状態に更新します.
     * */
    void updateCheckBoxAttendanceState(RegardedAsReasonEnum reasonEnum){
        AttendanceObject ao = this.mStudentInfoDialogFragnemt.getAttObject();
        ao.setManualRequestAttendance(AttendanceObject.MANUAL_ATTENDANCE);
        ao.setAttendanceTime("");
        //  URLパラメータ
        String  attendanceId    =   ao.getAttendanceId();
        int     forgot          =   updateCheckBoxForgotState(ao, reasonEnum);
        int     reaseon         =   reasonEnum.getReasonId();

        //  httpアクセス
        String url = URL.getUrlRegardedAsAttendance(attendanceId, reaseon, forgot);
        MyOkhttp.newInstance().getRequest(getMyContext(), url);

        //  適用後
        apply();
        //  確定表示
        //showConfirm(getConfirmMessage(AttendanceObject.STATE_ATTENDANCE, forgot));
    }
    /**
     *  updateCheckBoxLateStateメソッド
     *  遅刻とみなす状態に更新します.
     * */
    void updateCheckBoxLateState(RegardedAsReasonEnum reasonEnum){
        AttendanceObject ao = this.mStudentInfoDialogFragnemt.getAttObject();
        ao.setManualRequestAttendance(AttendanceObject.MANULA_LATE);
        ao.setAttendanceTime("");
        //  URLパラメータ
        String  attendanceId    =   ao.getAttendanceId();
        int     forgot          =   updateCheckBoxForgotState(ao, reasonEnum);
        int     reaseon         =   reasonEnum.getReasonId();

        //  httpアクセス
        String url = URL.getUrlRegardedAsLate(attendanceId, reaseon, forgot);
        MyOkhttp.newInstance().getRequest(getMyContext(), url);

        //  適用後
        apply();
        //  確定表示
        //showConfirm(getConfirmMessage(AttendanceObject.STATE_LATE, forgot));
    }
    /**
     *  updateCheckBoxAbsentStateメソッド
     *  欠席状態に更新します.
     * */
    void updateCheckBoxAbsentState(){
        AttendanceObject ao = this.mStudentInfoDialogFragnemt.getAttObject();
        ao.setManualRequestAttendance(0);
        ao.setAttendanceTime(null);
        ao.setFogotApplytTime(null);

        //  URLパラメータ
        String  attendanceId    =   ao.getAttendanceId();
        //  httpアクセス
        String url = URL.getUrlRegardedAsAbsent(attendanceId);
        MyOkhttp.newInstance().getRequest(getMyContext(), url);
        //  適用後
        apply();
        //  確定表示
        //showConfirm(getConfirmMessage(AttendanceObject.STATE_ABSENCE, 0));
    }
    /**
     *  updateCheckBoxForgotStateメソッド
     *  忘れ状態に更新します.
     *  @param AttendanceObject ao 出席オブジェクト
     *  @param RegardedAsReasonEnum reasonEnum 理由Enum
     *  @return {1} SonoRIs忘れ {0}違う
     * */
    int updateCheckBoxForgotState(AttendanceObject ao,RegardedAsReasonEnum reasonEnum){
        int forgot = reasonEnum.getForgot();
        if( forgot == this.S_FORGOT ){
            ao.setFogotApplytTime("");
            return S_FORGOT;
        }else{
            ao.setFogotApplytTime(null);
            return S_NOT_FORGOT;
        }
    }
    /**
     *  getConfirmMessageメソッド
     *  確定メッセージ
     *  @param int state 状態(出席・遅刻・忘れ)
     *  @param int forgot (0:忘れでない,1:忘れ)
     * */
    String getConfirmMessage(int state, int forgot){
        String str = "";
        //  出席・遅刻
        if( state == AttendanceObject.STATE_ATTENDANCE ){
            str = this.mStudentInfoDialogFragnemt.getActivity().getString(R.string.str_regarded_as_attendance);
        }else if( state == AttendanceObject.STATE_LATE ){
            str = this.mStudentInfoDialogFragnemt.getActivity().getString(R.string.str_regarded_as_late);
        }

        //  忘れ
        if( forgot == S_FORGOT ){
            str = str + " " + this.mStudentInfoDialogFragnemt.getActivity().getString(R.string.str_regarded_as_forgot);
        }
        //  欠席状態
        if(state == AttendanceObject.STATE_ABSENCE ){
            str = this.mStudentInfoDialogFragnemt.getActivity().getString(R.string.str_regarded_as_absent);
        }

        return str;
    }


    /**
     *  showConfirmメソッド
     *  出席・遅刻・忘れ確定後を表示します.
     *  @param int state 状態(出席・遅刻・忘れ)
     *  @param int forgot (0:忘れでない,1:忘れ)
     * */
    /*void showConfirm(String str){
        //  確認レイアウト表示
        this.mStudentInfoDialogFragnemt.mmRegaredeConfirmTv.setText(str);
        this.mStudentInfoDialogFragnemt.mRegaredeSelectLl.setVisibility(View.GONE);
        this.mStudentInfoDialogFragnemt.mRegaredeConfirmLl.setVisibility(View.VISIBLE);

        //setAssistMessage();
        //  ダイアログフレーム再描画
        this.mStudentInfoDialogFragnemt.redrawFrame();
    }*/

    /**
     *  applyメソッド
     * */
    void apply(){
        //
        setAssistMessage();
        //  初期化
        this.mStudentInfoDialogFragnemt.mRegarededAsReasonRg.clearCheck();
        //  再描画
        redrawFrame();
    }

    /**
     *  inVisibleReasenメソッド
     * */
    void inVisibleReasen(){
        this.mStudentInfoDialogFragnemt.mRegaredeAsReasonLl.setVisibility(View.INVISIBLE);
    }


    /**
     *  redrawFrameメソッド
     * */
    void redrawFrame(){
        //  ダイアログフレーム再描画
        this.mStudentInfoDialogFragnemt.redrawFrame();
    }


}
