package jp.pmw.test_en_revolution.questionnaire;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by scr on 2015/03/04.
 */
public class Choice implements Serializable {
    @SerializedName("choice_id")
    private int choiceId;
    @SerializedName("choice_number")
    private String choiceNumber;
    @SerializedName("choice")
    private String choice;
    @SerializedName("choice_color")
    private String choiceIndexColor;

    private QuestionResult questionResult;

    public void setQuestionResult(QuestionResult qr){
        this.questionResult = qr;
    }

    public void setChoice(String choice){
        this.choice = choice;
    }
    public void setChoiceColor(String color){
        this.choiceIndexColor = color;
    }


    public int getChoiceId(){return this.choiceId;}
    public String getChoiceNumber(){return this.choiceNumber;}
    public String getChoice(){return this.choice;}
    public String getChoiceIndexColor(){return this.choiceIndexColor;}
    public QuestionResult getQuestionResult(){return this.questionResult;}


}
