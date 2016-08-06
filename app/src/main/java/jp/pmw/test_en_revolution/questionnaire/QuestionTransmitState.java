package jp.pmw.test_en_revolution.questionnaire;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by si on 2016/02/02.
 */
public class QuestionTransmitState implements Serializable {
    @SerializedName("question_id")
    private String questionId;
    public String getQuestionId(){return this.questionId;}

    @SerializedName("open_start_time")
    private String openStartTime;
    public String getOpenStartTime(){return this.openStartTime;}

    @SerializedName("open_end_time")
    private String openEndTime;
    public String getOpenEndTime(){return this.openEndTime;}

    @SerializedName("answercheck_start_time")
    private String answerCheckStartTime;
    public String getAnswerCheckStartTime(){return this.answerCheckStartTime;}

    @SerializedName("answercheck_end_time")
    private String answerCheckEndTime;
    public String getAnswerCheckEndTime(){return this.answerCheckEndTime;}

    @SerializedName("answerresult_start_time")
    private String answerResultStartTime;
    public String getAnswerResultStartTime(){return this.answerResultStartTime;}

    @SerializedName("answerresult_end_time")
    private String answerResultEndTime;
    public String getAnswerResultEndTime(){return this.answerResultEndTime;}
}
