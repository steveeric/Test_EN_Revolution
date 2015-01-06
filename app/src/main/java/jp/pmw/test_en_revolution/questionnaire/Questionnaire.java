package jp.pmw.test_en_revolution.questionnaire;

import java.util.List;

/**
 * Created by scr on 2015/01/05.
 */
public class Questionnaire {
    private String lastSeeQuestionId = null;
    private List<Question>questions;
    public Questionnaire(List<Question> question){
        this.questions = question;
    }

    public void setLastQuestionId(String questionId){
        this.lastSeeQuestionId = questionId;
    }

    public String getLastSeeQuestionId(){
        return this.lastSeeQuestionId;
    }
    public List<Question> getQuestions(){
        return this.questions;
    }
}
