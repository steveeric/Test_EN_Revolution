package jp.pmw.test_en_revolution.questionnaire;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by si on 2016/01/27.
 */
public class Result implements Serializable {
    @SerializedName("yes_content")
    private String yesContent;
    @SerializedName("no_content")
    private String noContent;

    @SerializedName("yes_count")
    private int yesCount;
    @SerializedName("no_count")
    private int noCount;

    @SerializedName("yes_ratio")
    private float yesRatio;
    @SerializedName("no_ratio")
    private float noRatio;

    @SerializedName("yes_color")
    private String yesColor;
    @SerializedName("no_color")
    private String noColor;

    public void setYesCount(int yesCount){
        this.yesCount = yesCount;
    }
    public void setNoCount(int noCount){this.noCount = noCount;}
    public void setYesRatio(float yesRatio){this.yesRatio = yesRatio;}
    public void setNoRatio(float noRatio){this.noRatio = noRatio;}

    public String getYesContent(){return this.yesContent;}
    public String getNoContent(){return this.noContent;}

    public int getYesCount(){
        return this.yesCount;
    }
    public int getNoCount(){
        return this.noCount;
    }

    public float getYesRatio(){return this.yesRatio;}
    public float getNoRatio(){return this.noRatio;}

    public String getYesColor(){return this.yesColor;}
    public String getNoColor(){return this.noColor;}
}
