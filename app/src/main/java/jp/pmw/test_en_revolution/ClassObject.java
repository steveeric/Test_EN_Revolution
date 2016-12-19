package jp.pmw.test_en_revolution;

import com.google.gson.annotations.SerializedName;

import jp.pmw.test_en_revolution.confirm_class_plan.RoomInfoObject;
import jp.pmw.test_en_revolution.group_readjustment.ReAdjustmentOjbect;
import jp.pmw.test_en_revolution.one_cushion.select_teacher.Faculty;
import jp.pmw.test_en_revolution.questionnaire.Questionnaire;
import jp.pmw.test_en_revolution.room.Room;
import jp.pmw.test_en_revolution.survey.Survey;

/**
 * Created by si on 2016/01/29.
 * 授業オブジェクトクラス
 */
public class ClassObject {

    //  授業識別番号
    private String sameClassNumber;
    public void setSameClassNumber(String sameClassNumber){
        this.sameClassNumber = sameClassNumber;
    }

    public String getSameClassNumber(){return this.sameClassNumber;}

    private Faculty facultyObject;
    public void setFacultyObject(Faculty facultyObject){
        this.facultyObject = facultyObject;
    }
    public Faculty getFacultyObject(){return this.facultyObject;}

    //  教室オブジェクト
    private RoomInfoObject roomInfoObject;
    public void setRoomInfoObject(RoomInfoObject roomInfoObject){this.roomInfoObject = roomInfoObject;}
    public RoomInfoObject getRoomInfoObject(){return this.roomInfoObject;}

    //  授業設定オブジェクト
    private ClassConfigObject classConfigObject = new ClassConfigObject();
    public ClassConfigObject getClassConfigObject(){return this.classConfigObject;}

    //  残りの授業時間
    private long classTimeReaming;
    public void setClassTimeReaming(long classTimeReaming){this.classTimeReaming = classTimeReaming;}
    public long getClassTimeReaming(){return this.classTimeReaming;}

    //  出席・出席(忘れ)・遅刻・遅刻(忘れ)・欠席　者の数
    private NumOfAttendanceEntity mNumOfAttendanceEnttity;
    public void setNumOfAttendanceEnttity(NumOfAttendanceEntity numOfAttendanceEnttity){
        this.mNumOfAttendanceEnttity = numOfAttendanceEnttity;
    }
    public NumOfAttendanceEntity getNumOfAttendanceEnttity(){return this.mNumOfAttendanceEnttity;}
    //  出席・遅刻などの手動変更時の理由
    public ManulReason[] mManulReasons;

    //  学生群(本日の履修生)
    private StudentObject[] sos;
    public void setStudentObject(StudentObject[] sos){this.sos = sos;}
    public StudentObject[] getStudentObject(){return sos;}

    //  送信状態オブジェクト
    private TransmitStateObject tso;
    public void setTransmitStateObject(TransmitStateObject tso){this.tso = tso;}
    public TransmitStateObject getTransmitStateObject(){return this.tso;}

    //  クリッカー(アンケート)オブジェクト
    private Questionnaire questionnaire;
    public void setQuestionnaire(Questionnaire q){
        this.questionnaire = q;
    }
    public Questionnaire getQuestionnaire(){return this.questionnaire;}

    //  アンケートオブジェクト
    private Survey survey;
    public void setSurvey(Survey s){this.survey = s;}
    public Survey getSurvey(){return this.survey;}

    //  グループ調整オブジェクト
    private ReAdjustmentOjbect reAdjustment;
    public void setReAdjustment(ReAdjustmentOjbect rao){this.reAdjustment = rao;}
    public ReAdjustmentOjbect getReAdjustment(){return this.reAdjustment;}

}
