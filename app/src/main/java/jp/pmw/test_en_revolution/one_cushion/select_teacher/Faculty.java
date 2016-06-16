package jp.pmw.test_en_revolution.one_cushion.select_teacher;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import jp.pmw.test_en_revolution.confirm_class_plan.ClassPlan;
import jp.pmw.test_en_revolution.confirm_class_plan.Roster;
import jp.pmw.test_en_revolution.confirm_class_plan.RoomInfoObject;
import jp.pmw.test_en_revolution.grouping.GroupingManagement;
import jp.pmw.test_en_revolution.questionnaire.Questionnaire;

/**
 * Created by scr on 2014/12/04.
 */

public class Faculty implements Serializable {
    @SerializedName("faculty_id")
    private String facultyId;
    @SerializedName("full_name")
    private String fullName;

    public Faculty(String facultyId, String fullName){
        this.facultyId = facultyId;
        this.fullName = fullName;
    }

    //授業情報
    private ClassPlan classPlan;
    //受講者(オブジェクト)
    private Roster roster;
    //
    private RoomInfoObject tmpRoomInfo;
    //アンケート
    private Questionnaire questionnaire;
    //グルーピング管理クラス
    private GroupingManagement groupingManagement;


    //出席調査を終えたかどうか
    private boolean endAttendanceFlag = true;
    //最後にグルーピングを行った日時
    private String lastGroupingTime;

    public void resetRoster(){
        this.roster = null;
    }
    public void resetTmpRoomInfo(){
        this.tmpRoomInfo = null;
    }

    public void setLastGroupingTime(String lastGroupingTime){
        this.lastGroupingTime = lastGroupingTime;
    }

    //授業情報セッター
    public void setClassPlan(ClassPlan classPlan){
       this.classPlan = classPlan;
    }
    //受講者セッター
    public void setRoster(Roster roster){
        this.roster = roster;
    }
    public void setTmpRoomInfo(RoomInfoObject tmpRoomInfo){
        this.tmpRoomInfo = tmpRoomInfo;
    }

    //グルーピングセッター
    public void setGroupingManagement(GroupingManagement groupingManagement){
        this.groupingManagement = groupingManagement;
    }
    //アンケートセッター
    public void setQuestionnaire(Questionnaire questionnaire){
        this.questionnaire = questionnaire;
    }
    //  送信状態オブジェクト
    /*public void setTransmitStateObject(TransmitStateObject tso){
        this.transmitStateObject = tso;
    }*/

    public void setEndAttendanceFlag(boolean flag){
        this.endAttendanceFlag = flag;
    }

    public String getFacultyId(){
        return this.facultyId;
    }
    public String getFullName(){
        return this.fullName;
    }
    //授業情報ゲッター
    public ClassPlan getClassPlan(){return this.classPlan;}
    //受講者ゲッター
    public Roster getRoster(){return this.roster;}
    public RoomInfoObject getTmpRoomInfo(){return this.tmpRoomInfo;}
    //グルーピングマネージメントゲッター
    public GroupingManagement getGroupingManagement(){return this.groupingManagement;}
    //アンケートゲッター
    public Questionnaire getQuestionnaire(){return this.questionnaire;}
    //
    public boolean getEndAttendanceFlag(){return this.endAttendanceFlag;}
    //


    //
    public String getLastGroupingTime(){return this.lastGroupingTime;}
}
