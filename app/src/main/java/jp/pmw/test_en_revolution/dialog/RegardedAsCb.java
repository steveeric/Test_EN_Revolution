package jp.pmw.test_en_revolution.dialog;
import android.content.Context;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import jp.pmw.test_en_revolution.AttendanceObject;
import jp.pmw.test_en_revolution.ManulReason;
import jp.pmw.test_en_revolution.R;
import jp.pmw.test_en_revolution.config.URL;
import jp.pmw.test_en_revolution.confirm_class_plan.Attendance;
import jp.pmw.test_en_revolution.network.MyOkhttp;

import static java.lang.Math.abs;
import static jp.pmw.test_en_revolution.ManulReason.ACCEPTED_ATTENDANCE_ACK_AVAILABLE;
import static jp.pmw.test_en_revolution.ManulReason.ACCEPTED_ATTENDANCE_NACK_AVAILABLE;

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
    //  早退チェックボックス状態
    boolean mLeaveCbState = false;
    //
    List<ManulReason> mSelectable = new ArrayList<ManulReason>();
    //
    private int mBrowsReasonStartNumber;
    //
    private boolean mBrowReasonFirst = false;
    //
    private ManulReason mDefaulManulReason;


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
        mStudentInfoDialogFragnemt.mRegaredeAsLeaveCb = mStudentInfoDialogFragnemt.getCheckBox(mStudentInfoDialogFragnemt.dialog, R.id.dialog_custom_regarded_as_leave_cb);
        //mStudentInfoDialogFragnemt.mRegaredeAsForgotCb = mStudentInfoDialogFragnemt.getCheckBox(mStudentInfoDialogFragnemt.dialog, R.id.dialog_custom_regarded_as_forgot_cb);
        //  理由レイアウト
        mStudentInfoDialogFragnemt.mRegarededAsReasonRg = null;
        mStudentInfoDialogFragnemt.mRegaredeAsReasonLl = mStudentInfoDialogFragnemt.getLinearLayout(mStudentInfoDialogFragnemt.dialog, R.id.dialog_custom_regarded_as_reason_ll);
        mStudentInfoDialogFragnemt.mRegarededAsReasonRg = (RadioGroup) mStudentInfoDialogFragnemt.dialog.findViewById(R.id.dialog_custom_regarded_as_reason_rg);
        mStudentInfoDialogFragnemt.mRegarededAsReasonRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if( checkedId == -1 ){
                    mBrowsReasonStartNumber = 0;
                    mBrowReasonFirst = true;
                    if(mDefaulManulReason == null){
                        mStudentInfoDialogFragnemt.mRegaredeAsApplyBtn.setVisibility(View.INVISIBLE);
                        mStudentInfoDialogFragnemt.mRegaredeAsUndoBtn.setVisibility(View.INVISIBLE);
                    }else{
                        mStudentInfoDialogFragnemt.mRegaredeAsApplyBtn.setVisibility(View.INVISIBLE);
                        mStudentInfoDialogFragnemt.mRegaredeAsUndoBtn.setVisibility(View.VISIBLE);
                    }
                }else{
                    mBrowsReasonStartNumber = checkedId;
                    if( mBrowReasonFirst ){
                        mBrowReasonFirst = false;
                        if( mDefaulManulReason == null ){
                            mDefaulManulReason = getNowSelectedManulReason();
                        }
                        ManulReason mr = getNowSelectedManulReason();
                        setReasonLayout( mr );
                    }else{
                        ManulReason mr = getNowSelectedManulReason();
                        setReasonLayout( mr );
                    }
                }
            }
        });
        //  理由項目のSonoRIs忘れ
        mStudentInfoDialogFragnemt.mReasonForgotRb = (RadioButton) mStudentInfoDialogFragnemt.dialog.findViewById(R.id.dialog_custom_regarded_as_reason_forgot_rb);

        //mStudentInfoDialogFragnemt.mRegaredeAsAssistTv = mStudentInfoDialogFragnemt.getTextView(mStudentInfoDialogFragnemt.dialog, R.id.dialog_custom_regarded_as_assist_tv);
        //  理由未選択
        mStudentInfoDialogFragnemt.mAlertUnselectedReasonTv = mStudentInfoDialogFragnemt.getTextView(mStudentInfoDialogFragnemt.dialog, R.id.dialog_custom_regarded_as_reason_alert_unselected_tv);

        mStudentInfoDialogFragnemt.mRegaredeAsApplyBtn = mStudentInfoDialogFragnemt.getButton(mStudentInfoDialogFragnemt.dialog, R.id.dialog_custom_regarded_as_apply_btn);
        mStudentInfoDialogFragnemt.mRegaredeAsUndoBtn = mStudentInfoDialogFragnemt.getButton(mStudentInfoDialogFragnemt.dialog, R.id.dialog_custom_regarded_as_return_undo_btn);
   }
    void setReasonLayout(ManulReason mr){
        if( !mDefaulManulReason.mReasonId.equals( mr.mReasonId ) ){
            mStudentInfoDialogFragnemt.mRegaredeAsApplyBtn.setVisibility(View.VISIBLE);
            mStudentInfoDialogFragnemt.mRegaredeAsUndoBtn.setVisibility(View.VISIBLE);
        }else{
            mStudentInfoDialogFragnemt.mRegaredeAsApplyBtn.setVisibility(View.INVISIBLE);
            mStudentInfoDialogFragnemt.mRegaredeAsUndoBtn.setVisibility(View.INVISIBLE);
        }
    }

    /**
     *  setOnClickListenerメソッド
     * */
    void setOnClickListener() {
        mStudentInfoDialogFragnemt.mRegaredeAsAttendanceCb.setOnClickListener(AttendanceCheckBoxClickListener);
        mStudentInfoDialogFragnemt.mRegaredeAsLateCb.setOnClickListener(LateCheckBoxClickListener);
        mStudentInfoDialogFragnemt.mRegaredeAsAbsentCb.setOnClickListener(AbsentCheckBoxClickListener);
        mStudentInfoDialogFragnemt.mRegaredeAsLeaveCb.setOnClickListener(LeaveCheckBoxClickListener);
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
     * setReasonStateメソッド
     * 理由状態をラジオボタンにセットする.
     * */
    void setReasonState(){
        ManulReason[] manulReasons = getReason();
        if( manulReasons == null ){
            return;
        }
        this.mStudentInfoDialogFragnemt.mRegarededAsReasonRg.removeAllViews();
        this.mSelectable.clear();
        for( int i = 0; i < manulReasons.length; i++ ){
            int showAccordingToACK = manulReasons[i].mShowAccordingToACK;
            String reason = manulReasons[i].mReason;
            int mDoNotShowACKResponse = manulReasons[i].mDoNotShowACKResponse;
            RadioButton radioBtn = null;
            if( ACCEPTED_ATTENDANCE_ACK_AVAILABLE == showAccordingToACK ){
                //  出席認定でACKを返しているか調べる
                boolean chk = this.mStudentInfoDialogFragnemt.tapStudent.getAttendanceObject().getAttendanceByACK();
                if(chk){
                    radioBtn  = getReasonRadioButton( manulReasons[i], reason );
                }
            }else if( mDoNotShowACKResponse == AttendanceObject.MANUAL_ATTENDANCE ){
                //  授業内に座席指定・出席認定・在室確認・プライバシー保護でACKが一度でもあったかを調べる.
                if( !(this.mStudentInfoDialogFragnemt.tapStudent.getAttendanceObject().getReturndedResponse()) ){
                    radioBtn  = getReasonRadioButton( manulReasons[i], reason );
                }
            }else if(ACCEPTED_ATTENDANCE_NACK_AVAILABLE == showAccordingToACK){
                //  出席認定でACKを返しているか調べる
                boolean chk = this.mStudentInfoDialogFragnemt.tapStudent.getAttendanceObject().getAttendanceByACK();
                if(!chk){
                    radioBtn  = getReasonRadioButton( manulReasons[i], reason );
                }
            }else{
                radioBtn  = getReasonRadioButton( manulReasons[i], reason );
            }

            if( radioBtn != null ){
                ++this.mStudentInfoDialogFragnemt.getMainActivity().mStudentDialogBrowsedReasonTotalCount;
                setReasonRadioGroup( i, manulReasons[i], radioBtn );
            }
        }
        this.mStudentInfoDialogFragnemt.mRegarededAsReasonRg.setVisibility(View.VISIBLE);
    }
    /**
     *  getReasonメソッド
     *  選択可能な理由を返します.
     * */
    ManulReason[] getReason(){
        ManulReason[] manulReasons = null;
        if( mAttendanceCbState ){
            manulReasons = getManualReasons( AttendanceObject.STATE_ATTENDANCE );
        }else if( mLateCbState ){
            manulReasons = getManualReasons( AttendanceObject.STATE_LATE );
        }else if( mAbsentCbState ){
            manulReasons = getManualReasons( AttendanceObject.STATE_ABSENCE );
        }else if( mLeaveCbState ){
            manulReasons = getManualReasons( AttendanceObject.STATE_LEAVE );
        }
        return manulReasons;
    }
    /**
     *  setReasonRadioGroupメソッド
     *  理由ラジオグループにラジオボタンをセットします.
     * */
    void setReasonRadioGroup( int i, ManulReason mr, RadioButton radioBtn ){
        this.mStudentInfoDialogFragnemt.mRegarededAsReasonRg.addView( radioBtn );
        String selectedReasonId = this.mStudentInfoDialogFragnemt.tapStudent.getAttendanceObject().mReasonId;
        if( selectedReasonId.equals( mr.mReasonId )  ){
            radioBtn.setChecked(true);
        }
    }
    /**
     *  showメソッド
     *  ダイアログを表示する.
     *  ① 出席認定が終わっていない(出席ステータス状態変更を表示しない)
     *  ②
     *  ③
     * */
    void show(){
        if(mStudentInfoDialogFragnemt.transmitStateObject.getAttendanceTranmitEndTime() == null){
            //  出席認定が終わっていない
            return;
        }
        AttendanceObject ao = mStudentInfoDialogFragnemt.getAttObject();
        /*if( ao.getFirstAccessTime() != null ){
            //  座席指定ACKあり (理由項目のSonoRIs忘れを選択不可にする.)
            mStudentInfoDialogFragnemt.mReasonForgotRb.setVisibility(View.GONE);
        }*/
        if( ao.getAttendanceTime() != null ){
            if(ao.getManualRequestAttendance() == StudentInfoDialogFragnemt.MANUAL_NOT_REQUEST){
                //  出席認定でACK (遅刻・欠席選択不可)
                mStudentInfoDialogFragnemt.mRegaredeAsLateCb.setVisibility(View.INVISIBLE);
                mStudentInfoDialogFragnemt.mRegaredeAsAbsentCb.setVisibility(View.INVISIBLE);
            }
        }else{
            //  出席認定日時なし (早退選択不可)
            mStudentInfoDialogFragnemt.mRegaredeAsLeaveCb.setVisibility(View.INVISIBLE);
        }
        //  出席状態ステータス変更レイアウト表示
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
     *  LeaveCheckBoxClickListenerリスナー
     *  早退チェックボックスのリスナー
     * **/
    View.OnClickListener LeaveCheckBoxClickListener  =new View.OnClickListener(){
        @Override
        public void onClick(View v){
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
     *  getLeaveCheckBoxCheckedChanged
     * */
    boolean getLeaveCheckBoxCheckedChanged(){
        CheckBox cb = mStudentInfoDialogFragnemt.mRegaredeAsLeaveCb;
        return cb.isChecked();
    }
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
        boolean newLeaveCbState = getLeaveCheckBoxCheckedChanged();
        ManulReason[] manulReasons = null;
        if( newAttendanceCbState == false && newLateCbState == false && newAbsentCbState == false && newLeaveCbState == false ){
            newAttendanceCbState = this.mAttendanceCbState;
            newLateCbState = this.mLateCbState;
            newAbsentCbState = this.mAbsentCbState;
            newLeaveCbState = this.mLeaveCbState;
        }else{
            //  出席
            if( newAttendanceCbState ){
                if( this.mAttendanceCbState == newAttendanceCbState ){
                    newAttendanceCbState = !(newAttendanceCbState);
                }
            }
            //  遅刻
            if( newLateCbState ){
                if( this.mLateCbState == newLateCbState ){
                    newLateCbState = !(newLateCbState);
                }
            }
            //  欠席
            if( newAbsentCbState ){
                if( this.mAbsentCbState == newAbsentCbState ){
                    newAbsentCbState = !(newAbsentCbState);
                }
            }
            //  早退
            if( newLeaveCbState ){
                if( this.mLeaveCbState == newLeaveCbState ){
                    newLeaveCbState = !(newLeaveCbState);
                }
            }
        }

        this.mAttendanceCbState = newAttendanceCbState;
        this.mLateCbState = newLateCbState;
        this.mAbsentCbState = newAbsentCbState;
        this.mLeaveCbState = newLeaveCbState;
        setReasonState();
        setChangeSelectedState();
        setAssistMessage();
    }

    /**
     *  getReasonRadioButtonメソッド
     *  変更理由ラジオボタン
     *  @return 変更理由ラジオボタン
     * */
    RadioButton getReasonRadioButton(ManulReason manulReason, String reason){
        RadioButton radioBtn = new RadioButton( getMyContext() );
        radioBtn.setText( reason );
        radioBtn.setTag( manulReason.mReasonId );
        radioBtn.setTextSize( 35.0f );
        mSelectable.add( manulReason );
        return radioBtn;
    }
    /**
     *  getManualReasonsメソッド
     *  該当の変更理由のみ取得する.
     *  @return 変更理由群
     * */
    ManulReason[] getManualReasons(int state){
        List<ManulReason> reasons = new ArrayList<ManulReason>();
        ManulReason[] orijinalReasons = this.mStudentInfoDialogFragnemt.af.mManulReasons;
        for( int i = 0; i < orijinalReasons.length; i++ ){
            if(  state == Integer.valueOf( orijinalReasons[i].mState ) ){
                reasons.add( orijinalReasons[i] );
            }
        }
        return (ManulReason[])reasons.toArray(new ManulReason[0]);
    }

    /**
     *  chkTheSameAsLastCheckBoxメソッド
     *  @return {true}変更適用前と同じ {false}違う
     * */
    boolean chkTheSameAsLastCheckBox(){
        boolean same = false;
        int beforeState = getStateAttendance();
        boolean newAttendanceCbState = this.mAttendanceCbState;
        boolean newLateCbState = this.mLateCbState;
        boolean newAbsentCbState = this.mAbsentCbState;
        boolean newLeaveCBState = this.mLeaveCbState;
        if( beforeState == AttendanceObject.STATE_ATTENDANCE){
            if( newAttendanceCbState == true && newLateCbState == false && newAbsentCbState == false && newLeaveCBState == false ){
                //  変更前出席
                same = true;
            }
        }else if( beforeState == AttendanceObject.STATE_LATE){
            if( newAttendanceCbState == false && newLateCbState == true && newAbsentCbState == false && newLeaveCBState == false ){
                //   変更前遅刻
                same = true;
            }
        }else if( beforeState == AttendanceObject.STATE_ABSENCE){
            if( newAttendanceCbState == false && newLateCbState == false && newAbsentCbState == true && newLeaveCBState == false ){
                //   変更前欠席
                same = true;
            }
        }else if( beforeState == AttendanceObject.STATE_LEAVE ){
            if( newAttendanceCbState == false && newLateCbState == false && newAbsentCbState == false && newLeaveCBState == true ){
                //   変更前早退
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
        this.mStudentInfoDialogFragnemt.mRegaredeAsLeaveCb.setChecked(this.mLeaveCbState);
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
            this.mStudentInfoDialogFragnemt.mRegaredeAsApplyBtn.setVisibility(View.INVISIBLE);
            this.mStudentInfoDialogFragnemt.mRegaredeAsUndoBtn.setVisibility(View.INVISIBLE);
            //this.mStudentInfoDialogFragnemt.mRegaredeAsReasonLl.setVisibility(View.INVISIBLE);
        }else{
            //this.mStudentInfoDialogFragnemt.mRegaredeAsApplyBtn.setVisibility(View.VISIBLE);
            this.mStudentInfoDialogFragnemt.mRegaredeAsUndoBtn.setVisibility(View.VISIBLE);
            //this.mStudentInfoDialogFragnemt.mRegaredeAsReasonLl.setVisibility(View.VISIBLE);
            if( this.mAbsentCbState ){
            }else{
                //  選択理由を初期化する.
                clearSelectedRadioGropup();
                //  変更理由が選択されていないを非表示にする.
                invisibleAlertUnselectedReasenTv();
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
            //  出席
            undoCheckBoxAttendanceState();
        }else if( beforeState == AttendanceObject.STATE_LATE ){
            //  遅刻
            undoCheckBoxLateState();
        }else if( beforeState == AttendanceObject.STATE_LEAVE ){
            //  早退
            undoCheckBoxLeaveState();
        }else{
            //  欠席
            undoCheckBoxAbsentState();
        }
        clearSelectedRadioGropup();
        setAssistMessage();
        setChangeSelectedState();
        setReasonState();
    }
    /**
     *  clearSelectedRadioGropupメソッド
     *  「キャンセル」ボタンタップ時に、
     *  ラジオボタン選択内容も初期化する.
     * */
    void clearSelectedRadioGropup(){
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
        this.mLeaveCbState = false;
    }
    /**
     *  undoCheckBoxLateStateメソッド
     *  遅刻とみなす状態に戻す
     * */
    void undoCheckBoxLateState(){
        this.mAttendanceCbState = false;
        this.mLateCbState = true;
        this.mAbsentCbState = false;
        this.mLateCbState = false;
    }

    /**
     *  undoCheckBoxAbsentStateメソッド
     *  欠席とみなす状態に戻す.
     * */
    void undoCheckBoxAbsentState(){
        this.mAttendanceCbState = false;
        this.mLateCbState = false;
        this.mAbsentCbState = true;
        this.mLeaveCbState = false;
    }
    /**
     *  undoCheckBoxLeaveStateメソッド
     *  早退とみなす状態に戻す.
     * */
    void undoCheckBoxLeaveState(){
        this.mAttendanceCbState = false;
        this.mLateCbState = false;
        this.mAbsentCbState = false;
        this.mLeaveCbState = true;
    }


    /**
     *  getSelectedReasenメソッド
     *  選択された理由を取得します.
     * */
    RegardedAsReasonEnum getSelectedReasen(){
        int resorceId = this.mStudentInfoDialogFragnemt.mRegarededAsReasonRg.getCheckedRadioButtonId();
        return RegardedAsReasonEnum.valueOf(resorceId);
    }
    /**
     *  getNowSelectedManulReasonメソッド
     *  現在選択されている理由項目
     * */
    ManulReason getNowSelectedManulReason(){
        int currentTotalEndIndex = this.mStudentInfoDialogFragnemt.getMainActivity().mStudentDialogBrowsedReasonTotalCount;
        int currentTotalStartIndex = currentTotalEndIndex - (this.mSelectable.size()-1);
        int selectNumber = mBrowsReasonStartNumber - currentTotalStartIndex;
        ManulReason selectManulReason = this.mSelectable.get(selectNumber);
        return selectManulReason;
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
        //  現在選択されている理由
        ManulReason mr = getNowSelectedManulReason();
        int  reasonId = this.mStudentInfoDialogFragnemt.mRegarededAsReasonRg.getCheckedRadioButtonId();
        if( reasonId == -1 ){
            visibleAlertUnselectedReasenTv();
        }else{
            invisibleAlertUnselectedReasenTv();
            if( this.mLeaveCbState ){
                //  早退
                int a = 0;
                a = 12;
            }else if( this.mAbsentCbState ){
                //  欠席とみなします
                //updateCheckBoxAbsentState();

                int a = 0;
                a = 12;
            }else if( this.mLateCbState ){

                int a = 0;
                a = 12;
            }else if( this.mAttendanceCbState ){

                int a = 0;
                a = 12;
            }
        }
    }

    /**
     *  visibleAlertUnselectedReasenTvメソッド
     *  理由が未選択です.を表示する.
     * */
    void visibleAlertUnselectedReasenTv(){
        this.mStudentInfoDialogFragnemt.mAlertUnselectedReasonTv.setVisibility(View.VISIBLE);
    }
    /**
     *  invisibleAlertUnselectedReasenTvメソッド
     *  理由が未選択です.を非表示する.
     * */
    void invisibleAlertUnselectedReasenTv(){
        this.mStudentInfoDialogFragnemt.mAlertUnselectedReasonTv.setVisibility(View.INVISIBLE);
    }
    /**
     *  chkSelectedReasonメソッド
     *  理由が選択されているかを確認します.
     *  @param RegardedAsReasonEnum     選択理由イーナム
     *  @return {true} 選択されている. {false} 選択されていない.
     * */
    boolean chkSelectedReason(RegardedAsReasonEnum reasonEnum) {        //  理由が選択されていない
        if (reasonEnum == RegardedAsReasonEnum.NOT_SELECTED) {
            visibleAlertUnselectedReasenTv();
            return false;
        } else {
            invisibleAlertUnselectedReasenTv();
            return true;
        }
    }

    /**
     *  updateCheckBoxAttendanceOrLateStateメソッド
     *  出席又は遅刻状態に振り分けます.
     * */
    void updateCheckBoxAttendanceOrLateState(RegardedAsReasonEnum reasonEnum){
        if( !chkSelectedReason(reasonEnum) ){
            //  遅刻理由未選択のため処理させない
            return;
        }
        if( this.mAttendanceCbState ){
            //  出席とみなす
            updateCheckBoxAttendanceState(reasonEnum);
        }else{
            //  遅刻とみなす
            updateCheckBoxLateState(reasonEnum);
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
     *  redrawFrameメソッド
     * */
    void redrawFrame(){
        //  ダイアログフレーム再描画
        this.mStudentInfoDialogFragnemt.redrawFrame();
    }
}
