package jp.pmw.test_en_revolution.survey;

import java.io.IOException;
import jp.pmw.test_en_revolution.config.URL;
import jp.pmw.test_en_revolution.network.MyAsyncTask;
import jp.pmw.test_en_revolution.network.MyHttpConnection;
import jp.pmw.test_en_revolution.network.MyIOException;

/**
 * Created by si on 2016/08/17.
 * SurveyAsyncTaskクラス
 * 最新のアンケート情報を取得するタスク処理です.
 */
public class SurveyAsyncTask extends MyAsyncTask {
    SurveyFragment mSurveyFragment;
    public SurveyAsyncTask( SurveyFragment surveyFragment ){
        this.mSurveyFragment = surveyFragment;
    }

    @Override
    protected String doInBackground(Void... params) {
        try {
            String sameClassNumber = mSurveyFragment.getMainActivity().getClassObject().getSameClassNumber();
            String url = URL.getUrlSurveyState( sameClassNumber );
            String result = MyHttpConnection.run(url);
            SurveyState surveyState = mGson.fromJson(result.toString(), SurveyState.class);
            Survey survey = surveyState.mSurvey;
            process( survey );
        } catch(IOException e) {
            MyIOException.absorbIOException(e);
        }
        return null;
    }
    /**
     *  processメソッド
     *  処理を行います.
     * */
    void process( Survey newSurvey ) {
        if( !chkAliveFragment( this.mSurveyFragment ) ){
            return;
        }
        //  アンケート開始時刻
        mSurveyFragment.mSurvey.mStartTime  =   newSurvey.mStartTime;
        //  アンケート終了時刻
        mSurveyFragment.mSurvey.mEndTime    =   newSurvey.mEndTime;
        //  各問の回答状況を更新
        Survey oldSurvey = mSurveyFragment.mSurvey;
        for( int i = 0; i < oldSurvey.mQuestions.length; i++ ){
            String oldSurveyQuestionId =  oldSurvey.mQuestions[i].mQuestionId;
            for( int j = 0; j < newSurvey.mQuestions.length; j++ ){
                String newSurveyQuestionId      =   oldSurvey.mQuestions[i].mQuestionId;
                if( oldSurveyQuestionId.equals( newSurveyQuestionId ) ){
                    //  新しいアンケート群に入れ替える.
                    oldSurvey.mQuestions[i]     =   newSurvey.mQuestions[i];
                }
            }
        }
        //  UIハンドラー
        mSurveyFragment.mHandler.post(new Runnable() {
            @Override
            public void run() {
                mSurveyFragment.chkSurveyState();
                mSurveyFragment.assingProcess();
            }
        });
    }
}
