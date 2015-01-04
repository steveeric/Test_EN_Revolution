package jp.pmw.test_en_revolution.confirm_class_plan;

import java.io.Serializable;

/**
 * Created by scr on 2014/12/24.
 * 学生を表すオブジェクトです.
 */
public class Student implements Serializable {
    //大学独自の学籍番号
    private String originalstudentId;
    //システム内部で処理するための学生ID
    private String studentId;
    //検索用の苗字(ふりがな)
    private String furiganaFamilyName;
    //検索用の名前(ふりがな)
    private String furiganaGivenName;
    //苗字
    private String familyName;
    //名前
    private String givenName;
    //フルネーム(氏名)
    private String fullName;
    //着席位置
    //private Seat sitPosition;
    //private String nowSitSeatId;
    //出席状態
    //private AttendanceState attendance;
    //この授業での状態を管理するクラス
    private ThisClassTime thisClassTime;

    private TotalAttendance totalAttendance;
    //メッセージ
    private Message message;

    public Student(String originalstudentId,String studentId,
                   String furiganaFamilyName,String furiganaGivenName,
                   String familyName,String givenName,
                   String fullName,/*String nowSitSeatId,*/ThisClassTime thisClassTime,TotalAttendance totalAttendance,Message message/*Seat sitPosition*/){
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
        this.thisClassTime = thisClassTime;
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
    /*public Seat getSitPosition(){
        return this.sitPosition;
    }*/
    /*public String getNowSitSeatId(){
        return this.nowSitSeatId;
    }*/
    /*public AttendanceState getAttendance(){
        return this.attendance;
    }*/
    public ThisClassTime getThisClassTime(){
        return this.thisClassTime;
    }
    public TotalAttendance getTotalAttendance(){return this.totalAttendance;}
    public Message getMessage(){return this.message;}

}
