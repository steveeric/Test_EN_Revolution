package jp.pmw.test_en_revolution.questionnaire;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

import jp.pmw.test_en_revolution.config.Config;

/**
 * Created by scr on 2014/12/25.
 * アンケートの主となるワードを管理するクラスです.
 */
public class Question implements Serializable {
    @SerializedName("question_title_number")
    private String questionNumber;
    @SerializedName("question_title_number_word")
    private String questionNumberWord;
    //アンケートタイトル
    @SerializedName("question_title")
    private String questionTitle;
    //アンケート問題送信日時
    @SerializedName("question_text_open_start_time")
    private String quesiontStartDateTime;
    //アンケート問題送信終了日時
    @SerializedName("question_text_open_end_time")
    private String quesiontEndDateTime;
    //アンケートチェック開始日時
    @SerializedName("question_check_start_time")
    private String questionCheckStartDateTime;
    //アンケートチェック終了日時
    @SerializedName("question_check_end_time")
    private String questionCheckEndDateTime;

    @SerializedName("question_result_start_time")
    private String questionResultStartDateTime;

    @SerializedName("question_result_end_time")
    private String questionResultEndDateTime;

    @SerializedName("open_ack_count")
    public int mOpenACKCount;
    @SerializedName("open_nack_count")
    public int mOpenNACKCount;
    @SerializedName("yes_count")
    public int mYesCount;
    @SerializedName("no_count")
    public int mNoCount;
    @SerializedName("answerresult_ack_count")
    public int mAnswerresultACKCount;
    @SerializedName("answerresult_nack_count")
    public int mAnswerresultNACKCount;

    //
    @SerializedName("question_topic")
    private List<Ask> asks;

    @SerializedName("answer_result")
    private Result result;

    public Question(String questionNumber,String questionTitle,List<Ask> asks){
        this.questionNumber = questionNumber;
        this.questionTitle = questionTitle;
        this.asks = asks;
    }
    //  アンケート文送信送球時刻
    public void setQuestionStartdateTime(String currentDateTime){
        this.quesiontStartDateTime = currentDateTime;
    }
    //  回答状況確認要求開始時刻
    public void setQuestionCheckStartDateTime(String currentDateTime){
        this.questionCheckStartDateTime = currentDateTime;
    }
    //  回答結果閲覧要求開始時刻
    public void setQuestionResultStartDateTime(String currentDateTime){
        this.questionResultStartDateTime = currentDateTime;
    }

    public String getQuestionId(){
        String str = "";
        for(int i=0;i<this.asks.size();i++){
            str = str + this.asks.get(i).getAskId();
            if(this.asks.size()-1 != i){
                str = str + Config.SEPARATOR;
            }
        }
        return str;
    }

    public void setQuestionTransmitState(QuestionTransmitState qts){
        this.quesiontStartDateTime  = qts.getOpenStartTime();
        this.quesiontEndDateTime    = qts.getOpenEndTime();
        this.questionCheckStartDateTime =   qts.getAnswerCheckStartTime();
        this.questionCheckEndDateTime   =   qts.getAnswerCheckEndTime();
        this.questionResultStartDateTime    =   qts.getAnswerResultStartTime();
        this.questionResultEndDateTime      =   qts.getAnswerResultEndTime();
        this.mOpenACKCount                  =   qts.mOpenACKCount;
        this.mOpenNACKCount                 =   qts.mOpenNACKCount;
        this.mYesCount                      =   qts.mYesCount;
        this.mNoCount                       =   qts.mNoCount;
        this.mAnswerresultACKCount          =   qts.mAnswerresultACKCount;
        this.mAnswerresultNACKCount         =   qts.mAnswerresultNACKCount;
    }



    public String getQuestionNumber(){return this.questionNumber;}
    public String getQuestionNumberWord(){return this.questionNumberWord;}
    public String getQuestionTitle(){return this.questionTitle;}
    public String getQuesiontStartDateTime(){return this.quesiontStartDateTime;}
    public String getQuestionEndDateTime(){return this.quesiontEndDateTime;}

    public String getQuestionCheckStartDateTime(){return this.questionCheckStartDateTime;}
    public String getQuestionCheckEndDateTime(){return this.questionCheckEndDateTime;}

    public String getQuestionResultStartDateTime(){return this.questionResultStartDateTime;}
    public String getQuestionResultEndDateTime(){return this.questionResultEndDateTime;}

    public List<Ask> getAsks(){return this.asks;}
    public Result getResult(){
        return this.result;
    }
}
