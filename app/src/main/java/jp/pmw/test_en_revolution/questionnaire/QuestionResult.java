package jp.pmw.test_en_revolution.questionnaire;

import com.google.gson.annotations.SerializedName;

/**
 * Created by scr on 2015/03/04.
 */
public class QuestionResult {
    @SerializedName("question_id")
    private String questionId;
    @SerializedName("choice_id")
    private int choiceId;
    @SerializedName("response")
    private int response;
    @SerializedName("answer_count")
    private String answerCount;
    @SerializedName("ratio")
    private float ratio;
    @SerializedName("percentage")
    private String percentage;

    public void setAnswerCount(String answerCount){
        this.answerCount = answerCount;
    }

    public void setRatio(float ratio){
        this.ratio = ratio;
    }


    public String getQuestionId(){
        return this.questionId;
    }
    public int getChoiceId(){
        return this.choiceId;
    }
    public int getResponse(){return this.response;}
    public String getAnswerCount(){
        return this.answerCount;
    }
    public float getRatio(){return this.ratio;}
    public String getPercentage(){
        return this.ratio+"";
    }
}
