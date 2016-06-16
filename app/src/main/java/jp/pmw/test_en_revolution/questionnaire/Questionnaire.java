package jp.pmw.test_en_revolution.questionnaire;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by scr on 2015/01/05.
 */
public class Questionnaire {
    private String lastSeeQuestionTitleNumber = null;

    @SerializedName("questions")
    private List<Question>questions;
    public Questionnaire(List<Question> question){
        this.questions = question;
    }

    public void setLastSeeQuestionTitleNumber(String questionTitleNumber){
        this.lastSeeQuestionTitleNumber = questionTitleNumber;
    }

    public String getLastSeeQuestionTitleNumber(){
        return this.lastSeeQuestionTitleNumber;
    }
    public List<Question> getQuestions(){
        return this.questions;
    }
}
