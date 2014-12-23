package jp.pmw.test_en_revolution.confirm_class_plan;

import java.io.Serializable;

/**
 * Created by scr on 2014/12/21.
 */
public class Subject implements Serializable {
    //科目ID
    private String subjectId;
    //科目名
    private String subjectName;

    public Subject(String subjectId,String subjectName){
        this.subjectId = subjectId;
        this.subjectName = subjectName;
    }

    public String getSubjectId(){return this.subjectId;}
    public String getSubjectName(){return this.subjectName;}
}
