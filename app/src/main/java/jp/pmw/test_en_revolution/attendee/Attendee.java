package jp.pmw.test_en_revolution.attendee;

import com.google.gson.annotations.SerializedName;

/**
 * Created by scr on 2014/12/14.
 */
public class Attendee {
    //出席ID
    @SerializedName("attendance_id")
    public String mAtteandanceId;
    //学生ID
    @SerializedName("student_id")
    public String mStudentId;
    //苗字
    @SerializedName("family_name")
    public String mFamilyName;
    //ふりがな苗字
    @SerializedName("furigana_family_name")
    public String mFriganaFamilyName;
    //名前
    @SerializedName("given_name")
    public String mGivineName;
    //ふりがな名前
    @SerializedName("furigana_givne_name")
    public String mFuriganaGivenName;
    //氏名
    @SerializedName("full_name")
    public String mFullName;
    //ESL忘れ申請時間
    @SerializedName("forgot_esl_offer_time")
    public String mForgotEslOfferTime;
    //出席確認時間
    @SerializedName("attendance_confirmation_receive_time")
    public String mAttendanceConfirmationReceivetime;
}
