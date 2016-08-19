package jp.pmw.test_en_revolution.survey;

import java.util.TimerTask;

/**
 * Created by si on 2016/08/17.
 * SurveyTimerTaskクラス
 */
public class SurveyTimerTask extends TimerTask{
    SurveyFragment mSurveyFragment;
    public SurveyTimerTask( SurveyFragment surveyFragment ){
        this.mSurveyFragment = surveyFragment;
    }
    public void run() {
        new SurveyAsyncTask(mSurveyFragment).execute();
    }
}
