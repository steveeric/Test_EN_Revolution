package jp.pmw.test_en_revolution.grouping;

import com.google.gson.annotations.SerializedName;

/**
 * Created by scr on 2015/03/02.
 */
public class GroupingResult {
    @SerializedName("grouping_result")
    private int result;
    @SerializedName("reason")
    private String reason;

    public int getResult(){
        return this.result;
    }
    public String getReason(){
        return this.reason;
    }
}

