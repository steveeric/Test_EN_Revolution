package jp.pmw.test_en_revolution.survey;

import java.io.IOException;
import jp.pmw.test_en_revolution.config.URL;
import jp.pmw.test_en_revolution.network.MyAsyncTask;
import jp.pmw.test_en_revolution.network.MyHttpConnection;
import jp.pmw.test_en_revolution.network.MyIOException;

/**
 * Created by si on 2016/08/17.
 * StartSurveyAsynckTaskクラス
 * アンケートを開始します.
 */
public class StartSurveyAsynckTask extends MyAsyncTask {
    SurveyFragment mSurveyFragment;

    public StartSurveyAsynckTask(SurveyFragment surveyFragment) {
        this.mSurveyFragment = surveyFragment;
    }
    @Override
    protected String doInBackground(Void... params) {
        try {
            String sameClassNumber = mSurveyFragment.getMainActivity().getClassObject().getSameClassNumber();
            String url = URL.getUrlStartSurvey( sameClassNumber );
            String result = MyHttpConnection.run(url);
        } catch(IOException e) {
            MyIOException.absorbIOException(e);
        }
        return null;
    }
}
