package jp.pmw.test_en_revolution.questionnaire;

import com.google.gson.annotations.SerializedName;

/**
 * Created by si on 2016/02/06.
 * アンケートに関する送信で、
 * 最後に行っている又は行った
 * ACk情報とNPD_IDを
 * 保持するオブジェクトクラスです.
 */
public class LastQuestionBmpTransmitLogObject {
    @SerializedName("last_question_npd_id")
    private String lastQuestionNpdId;
    public String getLastQuestionNpdId(){return this.lastQuestionNpdId;}

    @SerializedName("last_transmit_type")
    private int lastTransmitType;
    public int getLastTransmitType(){return this.lastTransmitType;}

    @SerializedName("last_bmp_transmit_start_time")
    private String lastBmpTransmitStartTime;
    public String getLastBmpTransmitStartTime(){return this.lastBmpTransmitStartTime;}

    @SerializedName("last_bmp_transmit_end_time")
    private String lastBmpTransmitEndTime;
    public String getLastBmpTransmitEndTime(){return this.lastBmpTransmitEndTime;}

    @SerializedName("last_npd_wording")
    private String lastNpdWording;
    public String getLastNpdWording(){return this.lastNpdWording;}

    //  NACKが「NULL」の場合
    @SerializedName("nack_attendance_ids")
    private String[] nackAttendanceIds;
    public String[] getNackAttendanceIds(){return this.nackAttendanceIds;}

    //  ACK「NOT NULL」の場合
    @SerializedName("ack_attendance_ids")
    private String[] ackAttendanceIds;
    public String[] getAckAttendanceIds(){return this.ackAttendanceIds;}
}
