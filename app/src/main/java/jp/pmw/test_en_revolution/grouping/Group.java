package jp.pmw.test_en_revolution.grouping;

import com.google.gson.annotations.SerializedName;

/**
 * Created by scr on 2015/01/06.
 * 一つのグループを管理するクラスです.
 */
public class Group {
    //グループ名
    @SerializedName("group_name")
    private String groupName;
    Group(String groupName){
        this.groupName = groupName;
    }
    public String getGroupName(){return this.groupName;}

}
