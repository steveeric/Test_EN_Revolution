package jp.pmw.test_en_revolution;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

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

    /*public StudentObject(String studentIdNumber,String furigana, String fullName, SeatObject seatObject,SeatObject seatAfterMoving){
        this.studentIdNumber = studentIdNumber;
        this.furigana = furigana;
        this.fullName = fullName;
        this.seatObject = seatObject;
        this.seatAfterMoving = seatAfterMoving;
    }*/


}
