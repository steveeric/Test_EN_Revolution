package jp.pmw.test_en_revolution.survey;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by si on 2016/08/17.
 * Surveyクラス
 * アンケートデータクラス
 */
public class Survey implements Serializable{
    //  処理開始前
    public static final int S_BEFORE_START = 0;
    //  処理開始後
    public static final int S_END_START    = 1;
    //  アンケート状態
    public int mSurveyState                = -1;
    //  アンケートID
    @SerializedName("survey_id")
    public String mSurveyId;
    //  アンケート開始時刻
    @SerializedName("start_time")
    public String mStartTime;
    //  アンケート終了時刻
    @SerializedName("end_time")
    public String mEndTime;
    //  アンケート群
    @SerializedName("questions")
    public Question[] mQuestions;

    /**
     *  現在閲覧している画面のページ番号
     * */
    public int mViewPageNumber = 0;
    /**
     *  質問内容エンティティー
     * */
    public Question getQuestion(){
        return mQuestions[mViewPageNumber];
    }
    /**
     *  現在閲覧している画面のタイトル文
     * */
    public String getQuestionTitle(){
        return mQuestions[mViewPageNumber].mTitle;
    }
    /**
     *  現在閲覧している画面のタイトル文
     * */
    public String getQuestionNumber(){
        return mQuestions[mViewPageNumber].mNumber;
    }
    /**
     *  現在の状態を数値でセットする.
     * */
    public void setSurveyState(){
        if( this.mStartTime != null ){
            this.mSurveyState = Survey.S_END_START;
        }else{
            this.mSurveyState = Survey.S_BEFORE_START;
        }
    }

}
