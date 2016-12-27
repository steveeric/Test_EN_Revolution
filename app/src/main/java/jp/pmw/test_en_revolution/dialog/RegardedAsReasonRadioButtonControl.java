package jp.pmw.test_en_revolution.dialog;

import android.view.View;
import android.widget.RadioButton;
import java.util.ArrayList;
import java.util.List;
import jp.pmw.test_en_revolution.AttendanceObject;
import jp.pmw.test_en_revolution.ManulReason;
import jp.pmw.test_en_revolution.StudentObject;

import static jp.pmw.test_en_revolution.ManulReason.ACCEPTED_ATTENDANCE_ACK_AVAILABLE;

/**
 * Created by si on 2016/12/27.
 * 理由ラジオグループボタンの制御を行います.
 */

public class RegardedAsReasonRadioButtonControl {
    private StudentObject mStudentObject;
    private ManulReason[] mManulReasons;
    //  理由ラジオボタン(マスタ)
    public List<RegardedAsReasonRadioButton> mReasonRbList = new ArrayList<RegardedAsReasonRadioButton>();
    //  出席ラジオボタン(SonoRIsにより出席認定した)
    public RadioButton mAttendanceACKRb;
    //  出席ラジオボタン(SonoRIsが反応しなかった)
    public RadioButton mAttendanceNACKRb;
    //  出席ラジオボタン(SonoRIs忘れ)
    public RadioButton mAttendanceForgotRb;
    //  出席ラジオボタン(その他)
    public RadioButton mAttendanceOtherRb;
    //  遅刻ラジオボタン(SonoRIsが反応しなかった)
    public RadioButton mLateNACKRb;
    //  遅刻ラジオボタン(SonoRIs忘れ)
    public RadioButton mLateForgotRb;
    //  遅刻ラジオボタン(その他)
    public RadioButton mLateOtherRb;
    //  欠席ラジオボタン(私的理由)
    public RadioButton mAbsentPrivateRb;
    //  欠席ラジオボタン(公欠)
    public RadioButton mAbsentPublicRb;
    //  欠席ラジオボタン(忌引き)
    public RadioButton mAbsentBelevementRb;
    //  早退(その１)
    public RadioButton mLeaveOther1Rb;
    //  早退(その２)
    public RadioButton mLeaveOther2Rb;
    //  早退(その３)
    public RadioButton mLeaveOther3Rb;
    RegardedAsReasonRadioButtonControl( StudentObject so,ManulReason[] mrs){
        this.mStudentObject = so;
        this.mManulReasons = mrs;
    }
    /**
     * initRadioButtonメソッド
     * ラジオボタンの文字列をWEBDBから取得した文字列に置換する.
     * */
    public void initRadioButton(){
        for( int i = 0; i < this.mReasonRbList.size(); i++ ){
            RegardedAsReasonRadioButton rb = mReasonRbList.get(i);
           for( int j = 0; j < this.mManulReasons.length; j++ ){
                if( rb.mReasonId.equals( mManulReasons[j].mReasonId ) ){
                    String str = mManulReasons[j].mReason;
                    rb.mRadioButton.setText( str );
                    rb.mManulReason = mManulReasons[j];
                    rb.mRadioButton.setTextSize( 35.0f );
                    break;
                }
           }
        }
    }
    /**
     * initRadioGroupDisplayStateメソッド
     * 表示状態理由ラジオボタンの初期化を行う.
     * */
    public void initRadioGroupDisplayState(){
        for( int i = 0; i < this.mReasonRbList.size(); i++ ){
            RegardedAsReasonRadioButton rb = this.mReasonRbList.get(i);
            rb.mRadioButton.setVisibility(View.GONE);
        }
    }
    /**
     * setReasonRadioGroupメソッド
     * 理由ラジオグループをセットする.
     * */
    public void setReasonRadioGroup(int checkBoxSelectedState){
        if( AttendanceObject.STATE_ATTENDANCE == checkBoxSelectedState ){
            showReasonAttendanceRadioGroup();
        }else if( AttendanceObject.STATE_LATE == checkBoxSelectedState ){
            showReasonLateRadioGroup();
        }else if( AttendanceObject.STATE_ABSENCE == checkBoxSelectedState ){
            showReasonAbsentRadioGroup();
        }else if( AttendanceObject.STATE_LEAVE == checkBoxSelectedState ){
            showReasonLeaveRadioGroup();
        }
    }
    /**
     * setCheckedメソッド
     * 理由ラジオグループに該当するラジオボタンを選択済みにする.
     * */
    void setChecked(){
        for( int i = 0; i < this.mReasonRbList.size(); i++ ){
            RegardedAsReasonRadioButton rb = this.mReasonRbList.get(i);
            if( rb.mReasonId.equals( this.mStudentObject.mReasonId ) ){
                rb.mRadioButton.setChecked( true );
                break;
            }
        }
    }
    /**
     * getDefaultChekedメソッド
     * デフォルトで選択されている理由を取得する.
     * */
    ManulReason getDefaultCheked(){
        for( int i = 0; i < this.mReasonRbList.size(); i++ ){
            RegardedAsReasonRadioButton rb = this.mReasonRbList.get(i);
            if( rb.mReasonId.equals( this.mStudentObject.mReasonId ) ){
                return rb.mManulReason;
            }
        }
        return null;
    }
    /**
     * showReasonAttendanceRadioGroupメソッド
     * 出席チェックボックスにチェックが入ると表示される
     * 出席関係のラジオグループ
     * */
    void showReasonAttendanceRadioGroup(){
        boolean chk = this.mStudentObject.getAttendanceObject().getAttendanceByACK();
        if( chk ){
            this.mAttendanceACKRb.setVisibility(View.VISIBLE);
            this.mAttendanceNACKRb.setVisibility(View.GONE);
            showReasonForgotRadioGroup( mAttendanceForgotRb );
            this.mAttendanceOtherRb.setVisibility(View.GONE);
        }else{
            this.mAttendanceACKRb.setVisibility(View.GONE);
            this.mAttendanceNACKRb.setVisibility(View.VISIBLE);
            showReasonForgotRadioGroup( mAttendanceForgotRb );
            this.mAttendanceOtherRb.setVisibility(View.VISIBLE);
        }
    }
    /**
     * showReasonForgotRadioGroupメソッド
     * SonoRIs忘れを場合に応じて表示する.
     * */
    void showReasonForgotRadioGroup(RadioButton rb){
        boolean oneAck = this.mStudentObject.getAttendanceObject().getReturndedResponse();
        if( oneAck ){
            rb.setVisibility(View.GONE);
        }else{
            rb.setVisibility(View.VISIBLE);
        }
    }
    /**
     * showReasonLateRadioGroupメソッド
     * 遅刻チェックボックスにチェックが入ると表示される
     * 遅刻関係のラジオグループ
     * */
    void showReasonLateRadioGroup(){
        this.mLateNACKRb.setVisibility(View.VISIBLE);
        showReasonForgotRadioGroup( this.mLateForgotRb );
        this.mLateOtherRb.setVisibility(View.VISIBLE);
    }
    /**
     * showReasonAbsentRadioGroupメソッド
     * 欠席チェックボックスにチェックが入ると表示される
     * 欠席関係のラジオグループ
     * */
    void showReasonAbsentRadioGroup(){
        this.mAbsentPrivateRb.setVisibility(View.VISIBLE);
        this.mAbsentPublicRb.setVisibility(View.VISIBLE);
        this.mAbsentBelevementRb.setVisibility(View.VISIBLE);
    }
    /**
     * showReasonLeaveRadioGroupメソッド
     * 早退チェックボックスにチェックが入ると表示される
     * 早退関係のラジオグループ
     * */
    void showReasonLeaveRadioGroup(){
        this.mLeaveOther1Rb.setVisibility(View.VISIBLE);
        this.mLeaveOther2Rb.setVisibility(View.VISIBLE);
        this.mLeaveOther3Rb.setVisibility(View.VISIBLE);
    }
    /**
     * getManualReasonSameResorceIdメソッド
     * 引数のresorceIdに該当する理由(マスタ)を返す.
     * */
    ManulReason getManualReasonSameResorceId(RadioButton radioButton){
        for( int i = 0; i < this.mReasonRbList.size(); i++ ){
            RegardedAsReasonRadioButton rb = this.mReasonRbList.get(i);
            if( rb.mRadioButton.equals( radioButton ) ){
                return rb.mManulReason;
            }
        }
        return null;
    }
}
