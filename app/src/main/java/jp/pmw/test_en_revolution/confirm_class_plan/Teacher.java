package jp.pmw.test_en_revolution.confirm_class_plan;

import java.io.Serializable;

/**
 * Created by scr on 2014/12/21.
 */
public class Teacher implements Serializable {
    //教員ID
    private String teacherId;
    //教員名
    private String teacherName;
    //所属カテゴリ
    private int category;

    public String getTeacherId(){return this.teacherId;}
    public String getTeacherName(){return this.teacherName;}
    public int getCategory(){return this.category;}
}
