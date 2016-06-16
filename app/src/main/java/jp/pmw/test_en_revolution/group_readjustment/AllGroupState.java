package jp.pmw.test_en_revolution.group_readjustment;

import com.google.gson.annotations.SerializedName;


/**
 * Created by si on 2016/05/19.
 */
public class AllGroupState {

    @SerializedName("warning_color")
    public int warningColor;
    public void setWarningColor(int warningColor){
        this.warningColor = warningColor;
    }

    public int getWarningColor(){return this.warningColor;}

    @SerializedName("all_group_state")
    public String allGroupState;
    public void setAllGroupState(String state){
        this.allGroupState = state;
    }
    public String getAllGroupState(){return this.allGroupState;}

    /**
     *
     * */
    public void setItems(int warningColor, String allGroupState){
        this.warningColor = warningColor;
        this.allGroupState = allGroupState;
    }

}
