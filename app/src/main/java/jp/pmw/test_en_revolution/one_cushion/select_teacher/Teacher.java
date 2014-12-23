package jp.pmw.test_en_revolution.one_cushion.select_teacher;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import jp.pmw.test_en_revolution.confirm_class_plan.ClassPlan;

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

    //授業情報
    private ClassPlan classPlan;

    public void setClassPlan(ClassPlan classPlan){
       this.classPlan = classPlan;
    }

    public String getId(){
        return staffId;
    }
    public String getName(){
        return this.staffName;
    }
    public ClassPlan getClassPlan(){return this.classPlan;}
}
