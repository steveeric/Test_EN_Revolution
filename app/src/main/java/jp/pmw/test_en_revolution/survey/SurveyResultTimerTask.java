package jp.pmw.test_en_revolution.survey;

import java.util.TimerTask;

/**
 * Created by si on 2016/08/19.
 * SurveyResultTimerTaskクラス
 */
public class SurveyResultTimerTask extends TimerTask {
    SurveyResultDialogFragment mSurveyResultDialogFragment;
    public SurveyResultTimerTask( SurveyResultDialogFragment surveyResultDialogFragment ){
        this.mSurveyResultDialogFragment = surveyResultDialogFragment;
    }
    public void run() {
        Survey survey = mSurveyResultDialogFragment.mSurvey;
        String tapStr = mSurveyResultDialogFragment.mTapStr;
        //  アンケート(スマホ クリッカー)の回答状況を取得する.
        new SurveyResultAsyncTask(mSurveyResultDialogFragment, survey, tapStr).execute();
    }
}

