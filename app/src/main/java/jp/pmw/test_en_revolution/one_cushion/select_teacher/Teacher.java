package jp.pmw.test_en_revolution.one_cushion.select_teacher;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import jp.pmw.test_en_revolution.confirm_class_plan.ClassPlan;
import jp.pmw.test_en_revolution.confirm_class_plan.Roster;
import jp.pmw.test_en_revolution.grouping.GroupingManagement;
import jp.pmw.test_en_revolution.questionnaire.Questionnaire;

/**
 * Created by scr on 2014/12/04.
 */

public class Teacher implements Serializable {
    @SerializedName("staff_id")
    private String staffId;
    @SerializedName("staff_name")
    private String staffName;
    @SerializedName("staff_frigana_family_name")
    private String staffFriganaFamilyname;
    @SerializedName("staff_frigana_given_name")
    private String staffFriganaGivenName;

    public Teacher(String staffId,String staffName,String staffFriganaFamilyname,String staffFriganaGivenName){
        this.staffId = staffId;
        this.staffName = staffName;
        this.staffFriganaFamilyname = staffFriganaFamilyname;
        this.staffFriganaGivenName = staffFriganaGivenName;
    }

    //授業情報
    private ClassPlan classPlan;
    //受講者(オブジェクト)
    private Roster roster;
    //アンケート
    private Questionnaire questionnaire;
    //グルーピング管理クラス
    private GroupingManagement groupingManagement;
    //出席調査を終えたかどうか
    private boolean endAttendanceFlag = true;


    //授業情報セッター
    public void setClassPlan(ClassPlan classPlan){
       this.classPlan = classPlan;
    }
    //受講者セッター
    public void setRoster(Roster roster){
        this.roster = roster;
    }
    //グルーピングセッター
    public void setGroupingManagement(GroupingManagement groupingManagement){
        this.groupingManagement = groupingManagement;
    }
    //アンケートセッター
    public void setQuestionnaire(Questionnaire questionnaire){
        this.questionnaire = questionnaire;
    }
    public void setEndAttendanceFlag(boolean flag){
        this.endAttendanceFlag = flag;
    }

    public String getId(){
        return staffId;
    }
    public String getName(){
        return this.staffName;
    }
    //授業情報ゲッター
    public ClassPlan getClassPlan(){return this.classPlan;}
    //受講者ゲッター
    public Roster getRoster(){return this.roster;}
    //グルーピングマネージメントゲッター
    public GroupingManagement getGroupingManagement(){return this.groupingManagement;}
    //アンケートゲッター
    public Questionnaire getQuestionnaire(){return this.questionnaire;}
    //
    public boolean getEndAttendanceFlag(){return this.endAttendanceFlag;}
}
