package jp.pmw.test_en_revolution.confirm_class_plan;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import jp.pmw.test_en_revolution.grouping.Group;

/**
 * Created by scr on 2014/12/24.
 * 学生を表すオブジェクトです.
 */
public class Student implements Serializable {
    //大学独自の学籍番号
    @SerializedName("student_id_number")
    private String originalstudentId;
    //システム内部で処理するための学生ID
    @SerializedName("student_id")
    private String studentId;
    //検索用の苗字(ふりがな)
    @SerializedName("furigana_family_name")
    private String furiganaFamilyName;
    //検索用の名前(ふりがな)
    @SerializedName("furigana_given_name")
    private String furiganaGivenName;
    //苗字
    @SerializedName("family_name")
    private String familyName;
    //名前
    @SerializedName("given_name")
    private String givenName;
    //フルネーム(氏名)
    @SerializedName("full_name")
    private String fullName;

    @SerializedName("attendance")
    private Attendance attendance;
    //着席位置
    @SerializedName("seat")
    private Seat sitPosition;

    @SerializedName("group")
    private Group group;
    //private String nowSitSeatId;
    //出席状態
    //private AttendanceState attState;
    //この授業での状態を管理するクラス
   // private ThisClassTime thisClassTime;

    private TotalAttendance totalAttendance;
    //メッセージ
    private Message message;

    public Student(String originalstudentId,String studentId,
                   String furiganaFamilyName,String furiganaGivenName,
                   String familyName,String givenName,
                   String fullName,/*String nowSitSeatId,ThisClassTime thisClassTime,*/TotalAttendance totalAttendance,Message message/*Seat sitPosition*/){
        this.originalstudentId = originalstudentId;
        this.studentId = studentId;
        this.furiganaFamilyName = furiganaFamilyName;
        this.furiganaGivenName = furiganaGivenName;
        this.familyName = familyName;
        this.givenName = givenName;
        this.fullName = fullName;
        //this.sitPosition = sitPosition;
        //this.nowSitSeatId = nowSitSeatId;
        //this.attendance = attendance;
        //this.thisClassTime = thisClassTime;
        this.totalAttendance = totalAttendance;
        this.message = message;
    }

    public String getOriginalstudentId(){
        return this.originalstudentId;
    }
    public String getStudentId(){
        return this.studentId;
    }
    public String getFuriganaFamilyName(){
        return this.furiganaFamilyName;
    }
    public String getFuriganaGivenName(){
        return this.furiganaGivenName;
    }
    public String getFamilyName(){
        return this.familyName;
    }
    public String getGivenName(){
        return this.givenName;
    }
    public String getFullName(){
        return this.fullName;
    }
    public Seat getSitSeatInfo(){
        return this.sitPosition;
    }
    public Group getGroup(){return this.group;}
    /*public String getNowSitSeatId(){
        return this.nowSitSeatId;
    }*/
    /*public AttendanceState getAttendance(){
        return this.attState;
    }*/

    public Attendance getAttendance(){return this.attendance;}
    /*public ThisClassTime getThisClassTime(){
        return this.thisClassTime;
    }*/
    public TotalAttendance getTotalAttendance(){return this.totalAttendance;}
    public Message getMessage(){return this.message;}

}
