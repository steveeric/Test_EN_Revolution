package jp.pmw.test_en_revolution.confirm_class_plan;

import java.util.List;

/**
 * Created by scr on 2014/12/24.
 * 受講者を管理するクラスです.
 */
public class Roster {
    //受講者一覧です.
    private List<Student> rosterList;
    public Roster(List<Student> roters){
        this.rosterList = roters;
    }
    public List<Student> getRosterList(){
        return this.rosterList;
    }
}
