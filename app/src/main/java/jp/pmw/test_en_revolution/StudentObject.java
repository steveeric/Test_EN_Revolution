package jp.pmw.test_en_revolution;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import jp.pmw.test_en_revolution.attendee.Indicator;

/**
 * Created by si on 2016/01/29.
 * 学生オブジェクトクラス
 */
public class StudentObject implements Serializable {
    //学生ID
    @SerializedName("student_id")
    public String studentId;
    public String getStudentId(){return this.studentId;}

    //学籍番号
    @SerializedName("student_id_number")
    private String studentIdNumber;
    public String getStudentIdNumber(){return this.studentIdNumber;}

    //苗字フリガナ
    @SerializedName("furigana_family_name")
    private String furiganaFamilyname;
    public String getFuriganaFamilyname(){return this.furiganaFamilyname;}

    //名前フリガナ
    @SerializedName("furigana_givne_name")
    private String furiganaGivenName;
    public String getFuriganaGivenName(){return this.furiganaGivenName;}

    //  フリガナ(苗字フリガナ 名前フリガナ)
    //  (add 2016/05/24 09:37)
    @SerializedName("furigana")
    private String furigana;
    public String getFurigana(){return this.furigana;}

    @SerializedName("shorten_furigana")
    private String shortenFurigana;
    public String getShortenFurigana(){return this.shortenFurigana;}
    //苗字
    @SerializedName("family_name")
    private String familyName;
    public String getFamilyName(){return this.familyName;}

    //名前
    @SerializedName("given_name")
    private String givenName;
    public String getGivenName(){return this.givenName;}

    //氏名
    @SerializedName("full_name")
    private String fullName;
    public String getFullName(){return this.fullName;}

    //氏名
    @SerializedName("shorten_full_name")
    private String shortenFullName;
    public String getShortenFullName(){return this.shortenFullName;}

    //補助者有無
    @SerializedName("assistant")
    private int assistant;
    public int getAssistant(){return this.assistant;}

    //  補助者オブジェクト群
    @SerializedName("assistants")
    private Assistant[] assistants;
    public Assistant[] getAssistants(){return this.assistants;}

    //出席オブジェクト
    @SerializedName("attendance")
    private AttendanceObject ao;
    public AttendanceObject getAttendanceObject(){return this.ao;}

    //着席情報オブジェクト
    @SerializedName("seat")
    private SeatObject seatObject;
    public SeatObject getSeatObject() {
        return this.seatObject;
    }

    //座席移動オブジェクト
    @SerializedName("seat_after_moving")
    private SeatObject seatAfterMoving;
    public SeatObject getSeatAfterMoving() {
        return this.seatAfterMoving;
    }

    //メッセージオブジェクト
    @SerializedName("message")
    private MessageObject mo;
    public MessageObject getMessageObject(){return this.mo;}

    //  一つ前の授業までの出欠回数
    @SerializedName("past_attendance_count")
    private PastAttendanceCount pac;
    public void setPastAttendanceCount(PastAttendanceCount pac){this.pac = pac;}
    public PastAttendanceCount getPastAttendanceCount(){return this.pac;}

    @SerializedName("url_face_image")
    public String mFaceUrl;
    @SerializedName("last_update_time")
    public String mLastUpdateTime;
    //  ACKインジケーターについて
    @SerializedName("reason_id")
    public String mReasonId;
    //  前回までの出欠回数について
    @SerializedName("attendance_total")
    public AttendanceTotal mAttendanceTotal;
}
