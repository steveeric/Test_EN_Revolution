package jp.pmw.test_en_revolution;

import com.google.gson.annotations.SerializedName;

/**
 *
 * Created by scr on 2014/12/12.
 * 出欠席の情報を管理するクラス
 * @author Shota Ito
 * @version 1.0
 */
public class Atteandance {
    //出席ID
    @SerializedName("attendance_id")
    public String mAtteandanceId;
    //学籍ID
    /*@SerializedName("student_id")
    public String mStudentId;*/
    @SerializedName("student")
    public Student mStudent;

    //仮出席
    @SerializedName("temp_attendance")
    public int mTempAttedance;
    //座席ID
    @SerializedName("seat_id")
    public String mSeatid;
    //教室ID
    @SerializedName("room_id")
    public String mRoomId;
    //ESL忘れ申請時間
    @SerializedName("forgt_esl_offer_time")
    public String mForgotEslOfferTime;
    //出席確認時間(ACK応答時間)
    @SerializedName("attendance_confirmation_receive_time")
    public String mAttendanceConfirmainReceiveTime;

}
