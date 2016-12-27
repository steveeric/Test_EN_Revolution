package jp.pmw.test_en_revolution.dialog;
import android.content.Context;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import jp.pmw.test_en_revolution.AttendanceObject;
import jp.pmw.test_en_revolution.ManulReason;
import jp.pmw.test_en_revolution.R;
import jp.pmw.test_en_revolution.StudentObject;
import jp.pmw.test_en_revolution.config.URL;
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
    //  早退チェックボックス状態
    boolean mLeaveCbState = false;
    //
    private ManulReason mDefaulManulReason;
    //
    private ManulReason mNowTapManulReason;
    //
    private RegardedAsReasonRadioButtonControl mReasonContol;

    //  コンストラクタ
    RegardedAsCb(StudentInfoDialogFragnemt sidf){
        this.mStudentInfoDialogFragnemt = sidf;
        StudentObject so = this.mStudentInfoDialogFragnemt.tapStudent;
        ManulReason[] mrs = this.mStudentInfoDialogFragnemt.af.mManulReasons;
        this.mReasonContol = new RegardedAsReasonRadioButtonControl( so, mrs );
        initLayout();
        setOnClickListener();
        initCheckBoxState();
        this.mReasonContol.initRadioButton(); //(必ずinitLayout後に行うこと)
        this.mDefaulManulReason = this.mReasonContol.getDefaultCheked();
        setReasonLayout( mDefaulManulReason );
        show();
    }
    /**
     *  initLayoutメソッド
     *  レイアウトの初期化を行う
     * */
    void initLayout(){
        mStudentInfoDialogFragnemt.mRegaredeAsReasonLl = mStudentInfoDialogFragnemt.getLinearLayout(mStudentInfoDialogFragnemt.dialog, R.id.dialog_custom_regarded_as_reason_ll);
        //  出・遅・欠・早のチェックボックス
        mStudentInfoDialogFragnemt.mRegaredeAsAttendanceCb = mStudentInfoDialogFragnemt.getCheckBox(mStudentInfoDialogFragnemt.dialog, R.id.dialog_custom_regarded_as_attendance_cb);
        mStudentInfoDialogFragnemt.mRegaredeAsLateCb = mStudentInfoDialogFragnemt.getCheckBox(mStudentInfoDialogFragnemt.dialog, R.id.dialog_custom_regarded_as_late_cb);
        mStudentInfoDialogFragnemt.mRegaredeAsAbsentCb = mStudentInfoDialogFragnemt.getCheckBox(mStudentInfoDialogFragnemt.dialog, R.id.dialog_custom_regarded_as_absent_cb);
        mStudentInfoDialogFragnemt.mRegaredeAsLeaveCb = mStudentInfoDialogFragnemt.getCheckBox(mStudentInfoDialogFragnemt.dialog, R.id.dialog_custom_regarded_as_leave_cb);
        //  理由ラジオグループ
        mStudentInfoDialogFragnemt.mRegarededAsReasonRg = (RadioGroup) mStudentInfoDialogFragnemt.dialog.findViewById(R.id.dialog_custom_regarded_as_reason_rg);
        mStudentInfoDialogFragnemt.mRegarededAsReasonRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if( checkedId != -1 ){
                    RadioButton rb = (RadioButton)mStudentInfoDialogFragnemt.dialog.findViewById(checkedId);
                    // 選択されている理由IDと同じか
                    ManulReason mr = mReasonContol.getManualReasonSameResorceId( rb );
                    if( mr != null ){
                        mNowTapManulReason = mr;
                        setReasonLayout( mr );
                    }
                }
            }
        });
        //  出席関係の理由ラジオボタン
        initLayoutAttendanceReasonRb();
        //  遅刻関係の理由ラジオボタン
        initLayoutLateReasonRb();
        //  理由ラジオボタン(欠席)
        initLayoutAbsentReasonRb();
        //  理由ラジオボタン(早退)
        initLayoutLeaveReasonRb();
        //  変更適用ボタン
        mStudentInfoDialogFragnemt.mRegaredeAsApplyBtn = mStudentInfoDialogFragnemt.getButton(mStudentInfoDialogFragnemt.dialog, R.id.dialog_custom_regarded_as_apply_btn);
        //  キャンセルボタン
        mStudentInfoDialogFragnemt.mRegaredeAsUndoBtn = mStudentInfoDialogFragnemt.getButton(mStudentInfoDialogFragnemt.dialog, R.id.dialog_custom_regarded_as_return_undo_btn);
    }
    /**
     *  initLayoutAttendanceReasonRbメソッド
     *  出席関係の理由ラジオボタン
     * */
    void initLayoutAttendanceReasonRb(){
        //  理由ラジオボタン(出席)
        mReasonContol.mAttendanceACKRb = (RadioButton)this.mStudentInfoDialogFragnemt.dialog.findViewById(R.id.dialog_custom_regarded_as_reason_attendance_ack_rb);
        mReasonContol.mAttendanceNACKRb = (RadioButton)this.mStudentInfoDialogFragnemt.dialog.findViewById(R.id.dialog_custom_regarded_as_reason_attendance_nack_rb);
        mReasonContol.mAttendanceForgotRb = (RadioButton)this.mStudentInfoDialogFragnemt.dialog.findViewById(R.id.dialog_custom_regarded_as_reason_attendance_forgot_rb);
        mReasonContol.mAttendanceOtherRb = (RadioButton)this.mStudentInfoDialogFragnemt.dialog.findViewById(R.id.dialog_custom_regarded_as_reason_attendance_other_rb);
        initSetReasonRadioButtonList( RegardedAsReasonRadioButton.AT00, mReasonContol.mAttendanceACKRb );
        initSetReasonRadioButtonList( RegardedAsReasonRadioButton.AT01, mReasonContol.mAttendanceNACKRb );
        initSetReasonRadioButtonList( RegardedAsReasonRadioButton.AT02, mReasonContol.mAttendanceForgotRb );
        initSetReasonRadioButtonList( RegardedAsReasonRadioButton.AT03, mReasonContol.mAttendanceOtherRb );
    }
    /**
     *  initLayoutLateReasonRbメソッド
     *  遅刻関係の理由ラジオボタン
     * */
    void initLayoutLateReasonRb(){
        //  理由ラジオボタン(遅刻)
        mReasonContol.mLateNACKRb = (RadioButton)this.mStudentInfoDialogFragnemt.dialog.findViewById(R.id.dialog_custom_regarded_as_reason_late_nack_rb);
        mReasonContol.mLateForgotRb = (RadioButton)this.mStudentInfoDialogFragnemt.dialog.findViewById(R.id.dialog_custom_regarded_as_reason_late_forgot_rb);
        mReasonContol.mLateOtherRb = (RadioButton)this.mStudentInfoDialogFragnemt.dialog.findViewById(R.id.dialog_custom_regarded_as_reason_late_other_rb);
        initSetReasonRadioButtonList( RegardedAsReasonRadioButton.LA01,mReasonContol.mLateNACKRb );
        initSetReasonRadioButtonList( RegardedAsReasonRadioButton.LA02,mReasonContol.mLateForgotRb );
        initSetReasonRadioButtonList( RegardedAsReasonRadioButton.LA03,mReasonContol.mLateOtherRb );
    }
    /**
     *  initLayoutAbsentReasonRbメソッド
     *  欠席関係の理由ラジオボタン
     * */
    void initLayoutAbsentReasonRb(){
        mReasonContol.mAbsentPrivateRb = (RadioButton)this.mStudentInfoDialogFragnemt.dialog.findViewById(R.id.dialog_custom_regarded_as_reason_absent_private_rb);
        mReasonContol.mAbsentPublicRb = (RadioButton)this.mStudentInfoDialogFragnemt.dialog.findViewById(R.id.dialog_custom_regarded_as_reason_absent_public_rb);
        mReasonContol.mAbsentBelevementRb = (RadioButton)this.mStudentInfoDialogFragnemt.dialog.findViewById(R.id.dialog_custom_regarded_as_reason_absent_bereavement_rb);
        initSetReasonRadioButtonList( RegardedAsReasonRadioButton.AB01,mReasonContol.mAbsentPrivateRb );
        initSetReasonRadioButtonList( RegardedAsReasonRadioButton.AB02,mReasonContol.mAbsentPublicRb );
        initSetReasonRadioButtonList( RegardedAsReasonRadioButton.AB03,mReasonContol.mAbsentBelevementRb );
    }
    /**
     *  initLayoutLeaveReasonRbメソッド
     *  早退関係の理由ラジオボタン
     * */
    void initLayoutLeaveReasonRb(){
        mReasonContol.mLeaveOther1Rb = (RadioButton)this.mStudentInfoDialogFragnemt.dialog.findViewById(R.id.dialog_custom_regarded_as_reason_leave_othr1_rb);
        mReasonContol.mLeaveOther2Rb = (RadioButton)this.mStudentInfoDialogFragnemt.dialog.findViewById(R.id.dialog_custom_regarded_as_reason_leave_othr2_rb);
        mReasonContol.mLeaveOther3Rb = (RadioButton)this.mStudentInfoDialogFragnemt.dialog.findViewById(R.id.dialog_custom_regarded_as_reason_leave_othr3_rb);
        initSetReasonRadioButtonList( RegardedAsReasonRadioButton.LE01,mReasonContol.mLeaveOther1Rb );
        initSetReasonRadioButtonList( RegardedAsReasonRadioButton.LE02,mReasonContol.mLeaveOther2Rb );
        initSetReasonRadioButtonList( RegardedAsReasonRadioButton.LE03,mReasonContol.mLeaveOther3Rb );
    }
    /**
     *  initSetReasonRadioButtonListメソッド
     *  理由ラジオボタン配列に格納する.
     *  一斉に非表示にするときに使用
     * */
    void initSetReasonRadioButtonList(String reasonId, RadioButton rb){
        mReasonContol.mReasonRbList.add( new RegardedAsReasonRadioButton( reasonId, rb ) );
    }
    /**
     *  setOnClickListenerメソッド
     *  リスナーをセットする
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
     *  setReasonLayoutメソッド
     * */
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
     * initCheckBoxStateメソッド
     * 初期状態にチェックボックスをセットする.
     * */
    void initCheckBoxState(){
        undoCheckBoxState();
        setCheckBoxLayout();
    }
    /**
     * setReasonRadioGroupメソッド
     * 理由ラジオグループをセットする.
     * */
    void setReasonRadioGroup() {
        //  ラジオグループボタンの表示状態を全てGONEする
        this.mReasonContol.initRadioGroupDisplayState();
        int checkBoxSelectedState = getCheckBoxSelectedState();
        if (checkBoxSelectedState == -1) {
            return;
        }
        this.mReasonContol.setReasonRadioGroup(checkBoxSelectedState);
        this.mReasonContol.setChecked();
    }
    /**
     * getCheckBoxSelectedStateメソッド
     * チェックボックスで現在選択されている出遅欠早状態を取得する.
     * @return 出遅欠早状態
     * */
    int getCheckBoxSelectedState(){
        int checkBoxSelectedState = -1;
        if( mAttendanceCbState == true && mLateCbState == false && mAbsentCbState == false && mLeaveCbState == false ){
            checkBoxSelectedState = AttendanceObject.STATE_ATTENDANCE;
        }else if( mAttendanceCbState == false && mLateCbState == true && mAbsentCbState == false && mLeaveCbState == false ){
            checkBoxSelectedState = AttendanceObject.STATE_LATE;
        }else if( mAttendanceCbState == false && mLateCbState == false && mAbsentCbState == true && mLeaveCbState == false ){
            checkBoxSelectedState = AttendanceObject.STATE_ABSENCE;
        }else if( mAttendanceCbState == false && mLateCbState == false && mAbsentCbState == false && mLeaveCbState == true ){
            checkBoxSelectedState = AttendanceObject.STATE_LEAVE;
        }
        return checkBoxSelectedState;
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
    }
    /**
     *  setCheckBoxLayoutメソッド
     *  チェックボックスレイアウトをセットする.
     * */
    void setCheckBoxLayout(){
        AttendanceObject ao = mStudentInfoDialogFragnemt.getAttObject();
        if( ao.getAttendanceTime() != null ){
            if(ao.getManualRequestAttendance() == StudentInfoDialogFragnemt.MANUAL_NOT_REQUEST){
                //  出席認定でACK (遅刻・欠席選択不可)
                mStudentInfoDialogFragnemt.mRegaredeAsLateCb.setVisibility(View.INVISIBLE);
                mStudentInfoDialogFragnemt.mRegaredeAsAbsentCb.setVisibility(View.INVISIBLE);
            }
            //  出席認定日時あり (早退選択可)
            mStudentInfoDialogFragnemt.mRegaredeAsLeaveCb.setVisibility(View.VISIBLE);
        }else{
            //  出席認定日時なし (早退選択不可)
            mStudentInfoDialogFragnemt.mRegaredeAsLeaveCb.setVisibility(View.INVISIBLE);
        }
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
        setChangeSelectedState();
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
        setReasonRadioGroup();
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
        setChangeSelectedState();
        setReasonRadioGroup();
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
        this.mLeaveCbState = false;
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
     *  updateCheckBoxメソッド
     *  チェックボックスに選択された状態をDBや端末のメモリデータに反映する.
     * */
    void updateCheckBox(){
        //  現在選択されている理由
        ManulReason mr = mNowTapManulReason;
        if( this.mLeaveCbState ){
            //  早退
            updateCheckBoxLeaveState( mr );
        }else if( this.mAbsentCbState ){
            //  欠席とみなします
            updateCheckBoxAbsentState( mr );
        }else if( this.mLateCbState ){
            //  遅刻
            updateCheckBoxLateState( mr );
        }else if( this.mAttendanceCbState ){
            //  出席
            updateCheckBoxAttendanceState( mr );
        }
    }
    /**
     * updateCheckBoxAttendanceStateメソッド
     * 出席とみなす状態に更新します.
     * */
    void updateCheckBoxAttendanceState(ManulReason mr){
        AttendanceObject ao = this.mStudentInfoDialogFragnemt.getAttObject();
        ao.setManualRequestAttendance(AttendanceObject.MANUAL_ATTENDANCE);
        ao.setAttendanceTime("");
        ao.mLeaveTime           = null;
        //  URLパラメータ
        String  attendanceId    =   ao.getAttendanceId();
        int     forgot          =   updateCheckBoxForgotState(ao, mr);
        String  reaseon         =   mr.mReasonId;

        //  httpアクセス
        String url = URL.getUrlRegardedAsAttendance(attendanceId, reaseon, forgot);
        MyOkhttp.newInstance().getRequest(getMyContext(), url);

        //  適用後
        apply( mr );
    }
    /**
     *  updateCheckBoxLateStateメソッド
     *  遅刻とみなす状態に更新します.
     * */
    void updateCheckBoxLateState(ManulReason mr){
        AttendanceObject ao = this.mStudentInfoDialogFragnemt.getAttObject();
        ao.setManualRequestAttendance(AttendanceObject.MANULA_LATE);
        ao.setAttendanceTime("");
        ao.mLeaveTime           = null;
        //  URLパラメータ
        String  attendanceId    =   ao.getAttendanceId();
        int     forgot          =   updateCheckBoxForgotState(ao, mr);
        String  reaseon         =   mr.mReasonId;

        //  httpアクセス
        String url = URL.getUrlRegardedAsLate(attendanceId, reaseon, forgot);
        MyOkhttp.newInstance().getRequest(getMyContext(), url);

        //  適用後
        apply( mr );
    }
    /**
     *  updateCheckBoxAbsentStateメソッド
     *  欠席状態に更新します.
     * */
    void updateCheckBoxAbsentState(ManulReason mr){
        AttendanceObject ao = this.mStudentInfoDialogFragnemt.getAttObject();
        ao.setManualRequestAttendance(0);
        ao.setAttendanceTime(null);
        ao.setFogotApplytTime(null);
        ao.mLeaveTime           = null;
        String reasonId         =   mr.mReasonId;
        //  URLパラメータ
        String  attendanceId    =   ao.getAttendanceId();
        //  httpアクセス
        String url = URL.getUrlRegardedAsAbsent(attendanceId, reasonId);
        MyOkhttp.newInstance().getRequest(getMyContext(), url);
        //  適用後
        apply( mr );
    }
    /**
     *  updateCheckBoxLeaveStateメソッド
     *  早退状態に更新します.
     * */
    void updateCheckBoxLeaveState(ManulReason mr) {
        AttendanceObject ao = this.mStudentInfoDialogFragnemt.getAttObject();
        ao.mLeaveTime = "";
        String reasonId = mr.mReasonId;
        //  URLパラメータ
        String attendanceId = ao.getAttendanceId();
        //  httpアクセス
        String url = URL.getUrlRegardedAsLeave(attendanceId, reasonId);
        MyOkhttp.newInstance().getRequest(getMyContext(), url);
        //  適用後
        apply(mr);
    }
    /**
     *  updateCheckBoxForgotStateメソッド
     *  忘れ状態に更新します.
     *  @param AttendanceObject ao 出席オブジェクト
     *  @param RegardedAsReasonEnum reasonEnum 理由Enum
     *  @return {1} SonoRIs忘れ {0}違う
     * */
    int updateCheckBoxForgotState(AttendanceObject ao,ManulReason mr){
        int forgot = mr.mForgot;
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
    void apply(ManulReason mr){
        //
        this.mStudentInfoDialogFragnemt.tapStudent.mReasonId = mr.mReasonId;
        //
        this.mDefaulManulReason = mr;
        //
        this.setReasonLayout( mr );
        setCheckBoxLayout();
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