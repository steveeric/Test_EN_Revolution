package jp.pmw.test_en_revolution.grouping;

/**
 * Created by scr on 2015/01/06.
 * 一つのグループを管理するクラスです.
 */
public class Group {
    //グループ名
    private String groupName;
    //グループ内の学生
    //private List<Student> menbers;

    Group(String groupName/*, List<Student> members*/){
        this.groupName = groupName;
        //this.menbers = members;
    }

    public String getGroupName(){return this.groupName;}
    //public List<Student> getMenbers(){return this.menbers;}

}
