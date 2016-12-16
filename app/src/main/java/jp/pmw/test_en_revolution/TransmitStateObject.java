package jp.pmw.test_en_revolution;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import jp.pmw.test_en_revolution.questionnaire.QuestionTransmitState;

/**
 * Created by si on 2016/01/28.
 * 送信状態オブジェクトクラス
 */
public class TransmitStateObject implements Serializable {
    //  入室送信ステータス
    public static final int ENTERING_A_ROOM_STATUS = 1;
    //  出席確認(そのだ教授)後
    public static final int ATTENDANCE_END_TIME_STATUS = 2;
    //  プライバシー保護画面(かなえちゃん)後
    public static final int UNDO_TRANMIT_END_TIME_STATUS = 3;
    //  再調査後
    public static final int RE_ATTENDANCE_END_TIME_STATUS = 4;
    //  クリッカー送信の最小値
    public static final int CLIKCER_TRANMIST_MINIMUM      = 10;
    //  クリッカー一回のコンテンツ数
    public static final int CLIKCER_TRANMIST_CONTENT_COUNT = 4;
    //  クリッカー問の送信
    public static final String CLIKCER_TRANSMIT_TEXT         = "01";
    //  クリッカー回答取得送信
    public static final String CLIKCER_TRANMIST_ANSWER       = "02";
    //  クリッカー結果送信
    public static final String CLIKCER_TRANSMIT_RESULT       = "03";
    //  在室確認ACK一括変更ダイアログを表示する.
    public static final int RE_ATTENDANCE_BULK_CHANGE_SHOW = 1;

    @SerializedName("attendance_transmit_end_time")
    public String attendanceTranmitEndTime;

    @SerializedName("attendance_bulk_change_end_date_time")
    public String mAttendanceBulkChangeEndDateTime = null;

    @SerializedName("re_attendance_bulk_change_show")
    public int mReAttendanceBulkChangeShow = 0;

    @SerializedName("bmp_transmit_id")
    public String bmpTransmitId;
    public void setBmpTransmitId(String bmpTransmitId){
        this.bmpTransmitId = bmpTransmitId;
    }

    @SerializedName("re_attendance_start_time")
    public String reAttendanceStartTime;
    public void setReAttendanceStartTime(String reAttendanceStartTime){
        this.reAttendanceStartTime = reAttendanceStartTime;
    }

    @SerializedName("re_attendance_end_time")
    public String reAttendanceEndTime;
    @SerializedName("undo_transmit_start_reaming_time")
    public String undoTranmitStartReamingTime;
    @SerializedName("undo_transmit_end_time")
    public String undoTransmitEndTime;

    @SerializedName("question_transmits")
    private QuestionTransmitState[] questionTransmitState;
    public QuestionTransmitState[] getQuestionTransmitState(){return this.questionTransmitState;}

    TransmitStateObject(){}

    public void setObject(TransmitStateObject tso){
        this.attendanceTranmitEndTime         = tso.getAttendanceTranmitEndTime();
        this.bmpTransmitId                    = tso.getBmpTransmitId();
        this.reAttendanceEndTime              = tso.getReAttendanceEndTime();
        this.undoTranmitStartReamingTime      = tso.getUndoTranmitStartReamingStrTime();
        this.undoTransmitEndTime              = tso.getUndoTransmitEndTime();
    }

    public void setAttendanceTranmitEndTime(String attendanceTranmitEndTime){
        this.attendanceTranmitEndTime = attendanceTranmitEndTime;
    }

    /**
     * Created by si on 2016/02/01.
     * getNowTransmitStatusメソッド
     * 現在の送信状態を取得します.
     * **/
    public int getNowTransmitStatus(){
        if(this.attendanceTranmitEndTime == null){
            //  入室時
            return this.ENTERING_A_ROOM_STATUS;
        }else{
            if(this.attendanceTranmitEndTime != null && this.reAttendanceEndTime == null && this.undoTransmitEndTime == null){
                //  出席確認 (そのだ教授)後
                return this.ATTENDANCE_END_TIME_STATUS;
            }else if(this.attendanceTranmitEndTime != null && this.reAttendanceEndTime != null && this.undoTransmitEndTime == null) {
                //  出席確認（そのだ教授)とプライバシー保護（かなえちゃん)の間
                return this.RE_ATTENDANCE_END_TIME_STATUS;
            }else{
                //  プライバシー保護 (かなえちゃん)後
                return this.UNDO_TRANMIT_END_TIME_STATUS;
            }
        }
    }


    public String getAttendanceTranmitEndTime(){
        return this.attendanceTranmitEndTime;
    }
    public String getBmpTransmitId(){
        return this.bmpTransmitId;
    }
    public String getReAttendanceStartTime(){return this.reAttendanceStartTime;}
    public String getReAttendanceEndTime(){return this.reAttendanceEndTime;}
    public String getUndoTranmitStartReamingStrTime(){return this.undoTranmitStartReamingTime;}
    public long getUndoTranmitStartReamingTime(){
        return Long.valueOf(this.undoTranmitStartReamingTime);
    }
    public String getUndoTransmitEndTime(){
        return this.undoTransmitEndTime;
    }

}
