package jp.pmw.test_en_revolution.dialog;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;
import jp.pmw.test_en_revolution.AttendanceObject;
import jp.pmw.test_en_revolution.R;
import jp.pmw.test_en_revolution.confirm_class_plan.Attendance;

/**
 * Created by si on 2016/06/18.
 */
public class RegardedAsCb {
    //  学生ダイアログフラグメント
    StudentInfoDialogFragnemt mStudentInfoDialogFragnemt;
    //  出席とみとめるチェックボックス状態
    boolean mAttendanceCbState = false;
    //  遅刻とみとめるチェックボックス状態
    boolean mLateCbState = false;
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
        mStudentInfoDialogFragnemt.mRegaredeAsCbLl = mStudentInfoDialogFragnemt.getLinearLayout(mStudentInfoDialogFragnemt.dialog, R.id.dialog_custom_regarded_as_checkbox_ll);
        mStudentInfoDialogFragnemt.mRegaredeAsAttendanceCb = mStudentInfoDialogFragnemt.getCheckBox(mStudentInfoDialogFragnemt.dialog, R.id.dialog_custom_regarded_as_attendance_cb);
        mStudentInfoDialogFragnemt.mRegaredeAsLateCb = mStudentInfoDialogFragnemt.getCheckBox(mStudentInfoDialogFragnemt.dialog, R.id.dialog_custom_regarded_as_absent_cb);
        mStudentInfoDialogFragnemt.mRegaredeAsForgotCb = mStudentInfoDialogFragnemt.getCheckBox(mStudentInfoDialogFragnemt.dialog, R.id.dialog_custom_regarded_as_forgot_cb);

        mStudentInfoDialogFragnemt.mRegaredeAsAssistTv = mStudentInfoDialogFragnemt.getTextView(mStudentInfoDialogFragnemt.dialog, R.id.dialog_custom_regarded_as_assist_tv);

        mStudentInfoDialogFragnemt.mRegaredeAsApplyBtn = mStudentInfoDialogFragnemt.getButton(mStudentInfoDialogFragnemt.dialog, R.id.dialog_custom_regarded_as_apply_btn);
        mStudentInfoDialogFragnemt.mRegaredeAsUndoBtn = mStudentInfoDialogFragnemt.getButton(mStudentInfoDialogFragnemt.dialog, R.id.dialog_custom_regarded_as_return_undo_btn);
    }
    /**
     *  setOnClickListenerメソッド
     * */
    void setOnClickListener() {
        mStudentInfoDialogFragnemt.mRegaredeAsAttendanceCb.setOnClickListener(AttendanceCheckBoxClickListener);
        mStudentInfoDialogFragnemt.mRegaredeAsLateCb.setOnClickListener(LateCheckBoxClickListener);
        mStudentInfoDialogFragnemt.mRegaredeAsForgotCb.setOnClickListener(ForgotCheckBoxClickListener);
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

        if(ao.getFirstAccessTime() != null){
            //  一度でもACKを返している
            return;
        }

        if(ao.getManualRequestAttendance() == StudentInfoDialogFragnemt.PARAMETA_RELEASE_FORGOT
                && ao.getAttendanceTime() != null){
            //  ACkで出席を得ているから表示しない
            return;
        }
        mStudentInfoDialogFragnemt.mRegaredeAsCbLl.setVisibility(View.VISIBLE);
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
     *  getForgotCheckBoxCheckedChangedメソッド
     * */
    boolean getForgotCheckBoxCheckedChanged(){
        CheckBox cb = mStudentInfoDialogFragnemt.mRegaredeAsForgotCb;
        return cb.isChecked();
    }

    /**
     *  chkCanSelectedメソッド
     *  チェックボックスに選択された状態が可能かどうかを確認します.
     *  ■受け付けないバターン
     *  ①   出席とみなすと遅刻とみなすのどちらかがチェックされていない状態で、SonoRIs忘れをチェックした
     *  ②   出席とみなすと遅刻とみなすがともにチェックされた
     * */
    void chkCanSelected(){
        boolean newAttendanceCbState = getAttendanceCheckBoxCheckedChanged();
        boolean newLateCbState = getLateCheckBoxCheckedChanged();
        boolean newForgotCbState = getForgotCheckBoxCheckedChanged();
        //  ①
        if( newForgotCbState == true && newAttendanceCbState == false && newLateCbState == false){
            Toast.makeText(this.mStudentInfoDialogFragnemt.getMainActivity(), "「出席とみなす」又は「遅刻とみなす」を選択してください.", Toast.LENGTH_LONG).show();
            this.mForgotCbState = false;
        }else{
            this.mForgotCbState = newForgotCbState;
        }
        // 　②
        if( newAttendanceCbState == true && newLateCbState == true  ){
            this.mAttendanceCbState = !(this.mAttendanceCbState);
            this.mLateCbState = !(this.mLateCbState);
        }else{
            this.mAttendanceCbState = newAttendanceCbState;
            this.mLateCbState = newLateCbState;
        }
        setChangeSelectedState();
        setAssistMessage();
    }

    /**
     *  chkTheSameAsLastCheckBoxメソッド
     *  @return {true}変更適用前と同じ {false}違う
     * */
    boolean chkTheSameAsLastCheckBox(){
        boolean same = false;
        boolean newAttendanceCbState = this.mAttendanceCbState;//getAttendanceCheckBoxCheckedChanged();
        boolean newLateCbState = this.mLateCbState;//getLateCheckBoxCheckedChanged();
        boolean newForgotCbState = this.mForgotCbState;//getForgotCheckBoxCheckedChanged();
        String forgotTime = this.mStudentInfoDialogFragnemt.getAttObject().getForgotApplyTime();

        int beforeState = getStateAttendance();

        if( beforeState == AttendanceObject.STATE_ATTENDANCE && forgotTime != null){
            if( newAttendanceCbState == true && newLateCbState == false && newForgotCbState == true){
                //  変更前出席かつSonoRIs忘れ
                same =  true;
            }
        }else if( beforeState == AttendanceObject.STATE_LATE && forgotTime != null){
            if( newAttendanceCbState == false && newLateCbState == false && newForgotCbState == true){
                //  変更前遅刻かつSonoRIs忘れ
                same = true;
            }
        }

        if( beforeState == AttendanceObject.STATE_ATTENDANCE && forgotTime == null){
            if( newAttendanceCbState == true && newLateCbState == false && newForgotCbState == false ){
                //  変更前出席
                same = true;
            }
        }else if( beforeState == AttendanceObject.STATE_LATE && forgotTime == null){
           if( newAttendanceCbState == false && newLateCbState == false && newForgotCbState == false ){
               //   変更前出席
               same = true;
           }
        }else if( beforeState == AttendanceObject.STATE_ABSENCE && forgotTime == null){
           if( newAttendanceCbState == false && newLateCbState == false && newForgotCbState == false ){
               //   変更前出席
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
        this.mStudentInfoDialogFragnemt.mRegaredeAsForgotCb.setChecked(this.mForgotCbState);
    }
    /**
     * setAssistMessageメソッド
     * 変更前とチェックボックスの選択内容が事なれば、
     * 「変更適用」ボタンをタップしてくださいとテキストビューにセットする
     * */
    void setAssistMessage(){
        boolean same = chkTheSameAsLastCheckBox();
        if( same ){
            this.mStudentInfoDialogFragnemt.mRegaredeAsAssistTv.setText("　");
            this.mStudentInfoDialogFragnemt.mRegaredeAsApplyBtn.setVisibility(View.INVISIBLE);
            this.mStudentInfoDialogFragnemt.mRegaredeAsUndoBtn.setVisibility(View.INVISIBLE);
        }else{
            this.mStudentInfoDialogFragnemt.mRegaredeAsAssistTv.setText("現在の選択状態はまだ適用されていません.");
            this.mStudentInfoDialogFragnemt.mRegaredeAsApplyBtn.setVisibility(View.VISIBLE);
            this.mStudentInfoDialogFragnemt.mRegaredeAsUndoBtn.setVisibility(View.VISIBLE);
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
        setAssistMessage();
        setChangeSelectedState();
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
        this.mForgotCbState = getUndoForgotState();
    }
    /**
     *  undoCheckBoxLateStateメソッド
     *  遅刻とみなす状態に戻す
     * */
    void undoCheckBoxLateState(){
        this.mAttendanceCbState = false;
        this.mLateCbState = true;
        this.mForgotCbState = getUndoForgotState();
    }

    /**
     *  undoCheckBoxAbsentStateメソッド
     *  欠席とみなす状態に戻す.
     * */
    void undoCheckBoxAbsentState(){
        this.mAttendanceCbState = false;
        this.mLateCbState = false;
        this.mForgotCbState = false;
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

        if( this.mAttendanceCbState ){
            //  出席とみなす
            updateCheckBoxAttendanceState();
        }else if( this.mLateCbState ){
            //  遅刻とみなす
            updateCheckBoxLateState();
        }else{
            //  欠席とみなします
            updateCheckBoxAbsentState();
        }

        setAssistMessage();

        //  ダイアログフレーム再描画
        this.mStudentInfoDialogFragnemt.redrawFrame();
    }
    /**
     * updateCheckBoxAttendanceStateメソッド
     * 出席とみなす状態に更新します.
     * */
    void updateCheckBoxAttendanceState(){
        AttendanceObject ao = this.mStudentInfoDialogFragnemt.getAttObject();
        ao.setManualRequestAttendance(AttendanceObject.MANUAL_ATTENDANCE);
        ao.setAttendanceTime("");
        updateCheckBoxForgotState(ao);
    }
    /**
     *  updateCheckBoxLateStateメソッド
     *  遅刻とみなす状態に更新します.
     * */
    void updateCheckBoxLateState(){
        AttendanceObject ao = this.mStudentInfoDialogFragnemt.getAttObject();
        ao.setManualRequestAttendance(AttendanceObject.MANULA_LATE);
        ao.setAttendanceTime("");
        updateCheckBoxForgotState(ao);
    }
    /**
     *  updateCheckBoxAbsentStateメソッド
     *  欠席状態に更新します.
     * */
    void updateCheckBoxAbsentState(){
        AttendanceObject ao = this.mStudentInfoDialogFragnemt.getAttObject();
        ao.setManualRequestAttendance(0);
        ao.setAttendanceTime(null);
        updateCheckBoxForgotState(ao);
    }
    /**
     *  updateCheckBoxForgotStateメソッド
     *  忘れ状態に更新します.
     * */
    void updateCheckBoxForgotState(AttendanceObject ao){
        if( this.mForgotCbState ){
            ao.setFogotApplytTime("");
        }else{
            ao.setFogotApplytTime(null);
        }
    }
}
