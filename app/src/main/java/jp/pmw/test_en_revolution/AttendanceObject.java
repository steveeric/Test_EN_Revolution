package jp.pmw.test_en_revolution;

import com.google.gson.annotations.SerializedName;

import jp.pmw.test_en_revolution.attendee.Indicator;
import jp.pmw.test_en_revolution.dialog.StudentInfoDialogFragnemt;


/**
 * Created by si on 2016/01/29.
 * 出欠オブジェクトクラス
 */
public class AttendanceObject {
    //  過渡状態
    public static final int STATE_TRANSIENT = 0;
    //  出席状態
    public static final int STATE_ATTENDANCE = 1;
    //  欠席状態
    public static final int STATE_ABSENCE = 2;
    //  遅刻状態
    public static final int STATE_LATE = 3;
    //  早退状態
    public static final int STATE_LEAVE = 4;

    //  手動でない
    public static final int MANUAL_NOT = 0;
    //  手動出席申請
    public static final int MANUAL_ATTENDANCE = 1;
    //  手動遅刻申請
    public static final int MANULA_LATE = 2;

    //出欠ID
    @SerializedName("attendance_id")
    private String attendanceId;

    public String getAttendanceId() {
        return this.attendanceId;
    }

    //手動初回アクセス時刻
    @SerializedName("manual_request_first_access")
    private int manualRequestFirstAccess;

    public void setManualRequestFirstAccess(int request) {
        this.manualRequestFirstAccess = request;
    }

    public int getManualRequestFirstAccess() {
        return this.manualRequestFirstAccess;
    }

    //初回アクセス時刻(入室ACK時刻)
    @SerializedName("first_access_time")
    private String firstAccessTime;
    public void setFirstAccessTime(String firstAccessTime) {
        this.firstAccessTime = firstAccessTime;
    }
    public String getFirstAccessTime() {
        return this.firstAccessTime;
    }

    //手動出席時刻
    @SerializedName("manual_request_attendance")
    private int manualRequestAttendance;
    public void setManualRequestAttendance(int request) {
        this.manualRequestAttendance = request;
    }
    public int getManualRequestAttendance() {
        return this.manualRequestAttendance;
    }

    //出席確認時刻(出席ACK時刻-そのだ教授)
    @SerializedName("attendance_time")
    private String attendanceTime;
    public void setAttendanceTime(String attendanceTime) {
        this.attendanceTime = attendanceTime;
    }
    public String getAttendanceTime() {
        return this.attendanceTime;
    }

    //手動再出席時刻
    @SerializedName("manual_request_re_attendance")
    public int manualRequestReAttendance;
    public int getManualRequestReAttendance() {
        return this.manualRequestReAttendance;
    }

    //  再出席調査対象者
    @SerializedName("re_attendance_target")
    public String mReAttendanceTarget;

    //再出席確認時刻(出席ACK時刻-そのだ教授+1)
    @SerializedName("re_attendance_time")
    public String reAttendancetime;
    public String getReAttendancetime() {
        return this.reAttendancetime;
    }

    //忘れ適用時刻
    @SerializedName("forgotapply_time")
    private String forgotApplyTime;
    public void setFogotApplytTime(String forgotApplyTime) {
        this.forgotApplyTime = forgotApplyTime;
    }
    public String getForgotApplyTime() {
        return this.forgotApplyTime;
    }

    //手動要求プライバシー保護
    @SerializedName("manual_request_class_last_ack")
    private int manualRequestClassLastAck;
    public void setManualRequestClassLastAck(int request) {
        this.manualRequestClassLastAck = request;
    }
    public int getManualRequestClassLastAck() {
        return this.manualRequestClassLastAck;
    }

    //プライバシー保護ACK確認時刻(かなえちゃん)
    @SerializedName("class_last_ack_time")
    private String classLastAckTime;
    public void setClassLastAckTime(String classLastAckTime) {
        this.classLastAckTime = classLastAckTime;
    }
    public String getClassLastAckTime() {
        return this.classLastAckTime;
    }

    //  ACKインジケーターについて
    @SerializedName("indicator")
    public Indicator mIndicator;

    //  早退日時
    @SerializedName("leave_time")
    public String mLeaveTime;

    //  出席関係情報セット
    public void setAttendanceObject(AttendanceObject ao) {
        //
        this.mIndicator = ao.mIndicator;
        //  初回手動パラメータ
        this.manualRequestFirstAccess = ao.manualRequestFirstAccess;
        //
        this.firstAccessTime = ao.getFirstAccessTime();
        //
        this.manualRequestAttendance = ao.manualRequestAttendance;
        //
        this.attendanceTime = ao.getAttendanceTime();
        //
        this.manualRequestReAttendance = ao.getManualRequestReAttendance();
        //
        this.reAttendancetime = ao.getReAttendancetime();
        //
        this.manualRequestClassLastAck = ao.getManualRequestClassLastAck();
        //
        this.classLastAckTime = ao.getClassLastAckTime();
    }

    /**
     * Created by si on 2016/06/16.
     * getAttendanceByACKメソッド
     * ACKによる出席かを確認します.
     * @return {@return true} ACKによる出席 or {@return false}そうでない.
     **/
    public boolean getAttendanceByACK(){
        boolean byACK = false;
        if( this.manualRequestAttendance == 0
                && this.attendanceTime != null){
            byACK = true;
        }
        return byACK;
    }

    /**
     *  chkLateStateメソッド
     *  遅刻状態かを確認する.
     * @return {@return ture}遅刻者 or {@return false}それ以外
     **/
    public boolean chkLateState(){
        if(this.manualRequestAttendance == MANULA_LATE
                && this.attendanceTime != null){
            //  遅刻状態
            return true;
        }
        return false;
    }
    /**
     *  chkManualAttendanceStateメソッド
     *  手動出席状態かを確認する.
     * @return {@return ture}手動出席 or {@return false}それ以外
     * **/
    public boolean chkManualAttendanceState(){
        if(this.manualRequestAttendance == MANUAL_ATTENDANCE
                && this.attendanceTime != null){
            //  手動出席状態
            return true;
        }
        return false;
    }


    /**
     * Created by si on 2016/02/05.
     * getStateAttendanceメソッド
     * 出席状態を取得する.
     * @return {@return STATE_ABSENT} or {@return STATE_ATTENDANCE} or {@return STATE_LATE}
     **/
    public int getStateAttendance() {
        int state = this.STATE_ABSENCE;
        if( this.mLeaveTime != null ){
            //  早退
            return this.STATE_LEAVE;
        }
        if (this.attendanceTime != null) {
            if(this.manualRequestAttendance == MANULA_LATE){
                //  遅刻
                state = this.STATE_LATE;
            }else{
                //  出席
                state = this.STATE_ATTENDANCE;
            }
        }
        return state;
    }

    /**
     * Created by si on 2016/02/05.
     * getAttendanceStateColorメソッド
     * ダイアログフレーム色を取得する
     * @param   context コンテキスト
     * @param   tso     送信状態オブジェクト
     * @return 色リソース
     * **/
    public int getAttendanceStateColor(MainActivity context,TransmitStateObject tso) {
        //  過渡状態
        int resorce = context.getResources().getColor(R.color.darkGray);
        //  送信状態取得
        int tansmitState = tso.getNowTransmitStatus();
        //  出席後(そのだ教授後)
        if (tansmitState >= TransmitStateObject.ATTENDANCE_END_TIME_STATUS) {
            //  出席状態取得
            int stateAttendance = getStateAttendance();
            if (stateAttendance == AttendanceObject.STATE_ATTENDANCE) {
                resorce = context.getResources().getColor(R.color.blue);
            } else if (stateAttendance == AttendanceObject.STATE_ABSENCE) {
                resorce = context.getResources().getColor(R.color.red);
            } else if(stateAttendance == AttendanceObject.STATE_LATE){
                resorce = context.getResources().getColor(R.color.tuyukusairo);
            } else if( stateAttendance == AttendanceObject.STATE_LEAVE ){
                resorce = context.getResources().getColor(R.color.leave_color);
            }
        }
        return resorce;
    }
    /**
     * Created by si on 2016/02/29.
     * getReturndedResponseメソッド
     * @return {return:true}ACKを1度でも検出,{return:false}ACKを全ACK未検出
     * **/
    public boolean getReturndedResponse(){
        boolean response = false;
        if( (this.getFirstAccessTime() != null)
                ||(this.getManualRequestAttendance() == StudentInfoDialogFragnemt.MANUAL_NOT_REQUEST && this.getAttendanceTime() != null)
                || (this.getManualRequestReAttendance() == StudentInfoDialogFragnemt.MANUAL_NOT_REQUEST && this.getReAttendancetime() != null)
                || (this.getManualRequestClassLastAck() == StudentInfoDialogFragnemt.MANUAL_NOT_REQUEST && this.getClassLastAckTime() != null)){
            response = true;
        }
        return response;
    }
    /**
     * Created by si on 2016/12/21.
     * toStateManualAttendanceメソッド
     * ローカル上で手動出席認定状態にする.
     * **/
    public void toStateManualAttendance(){
        setManualRequestAttendance( AttendanceObject.MANUAL_ATTENDANCE );
        setAttendanceTime(" ");
        setFogotApplytTime(null);
    }
    /**
     * Created by si on 2016/12/21.
     * toStateManualAttendanceメソッド
     * ローカル上で手動出席認定状態にする.
     * **/
    public void toStateForgotManualAttendance(){
        setManualRequestAttendance( AttendanceObject.MANUAL_ATTENDANCE );
        setAttendanceTime(" ");
        setFogotApplytTime(" ");
    }
    /**
     * Created by si on 2016/12/21.
     * toStateManualAbsentメソッド
     * ローカル上で手動欠席状態にする.
     * **/
    public void toStateManualAbsent(){
        setManualRequestAttendance( AttendanceObject.MANUAL_NOT );
        setAttendanceTime(null);
        setFogotApplytTime(null);
    }
}
