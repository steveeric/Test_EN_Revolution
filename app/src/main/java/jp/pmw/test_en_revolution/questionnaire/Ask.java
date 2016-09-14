package jp.pmw.test_en_revolution.questionnaire;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by scr on 2014/12/25.
 * アンケートで尋ねる内容を管理するクラスです.
 */
public class Ask implements Serializable {
    @SerializedName("question_id")
    private int askId;
    //尋ねる問番号
    @SerializedName("question_number")
    private String askNumber;
    @SerializedName("question_number_word")
    private String askNumberWord;
    //尋ねる内容
    @SerializedName("question")
    private String askContent;
    //回答情報
    @SerializedName("choices")
    private List<Choice> choice;

    public int getAskId(){return this.askId;}
    public String getAskNumber(){
        return this.askNumber;
    }
    public String getAskNumberWord(){return this.askNumberWord;}
    public String getAskContent(){
        return this.askContent;
    }
    public List<Choice> getChoices(){return this.choice;}
}
