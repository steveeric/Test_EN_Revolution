package jp.pmw.test_en_revolution.one_cushion.select_teacher;

import com.google.gson.annotations.SerializedName;

/**
 * Created by scr on 2014/12/04.
 */

public class Teacher {
    @SerializedName("STAFF_ID")
    private String id;
    @SerializedName("STAFF_NAME")
    private String name;

    public String getId(){
        return id;
    }

    public String getName(){
        return this.name;
    }
}
