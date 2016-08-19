package jp.pmw.test_en_revolution.survey;

import java.io.IOException;

import jp.pmw.test_en_revolution.StudentObject;
import jp.pmw.test_en_revolution.config.URL;
import jp.pmw.test_en_revolution.network.MyAsyncTask;
import jp.pmw.test_en_revolution.network.MyHttpConnection;
import jp.pmw.test_en_revolution.network.MyIOException;
import jp.pmw.test_en_revolution.questionnaire.QuestionnaireResultAsyncTask;

/**
 * Created by si on 2016/08/18.
 * SurveyResultAsyncTaskクラス
 *
 */
public class SurveyResultAsyncTask  extends MyAsyncTask {
    public static final String S_STR_UNANSWER = "未回答";
    public static final String S_STR_YES = "はい";
    public static final String S_STR_NO  = "いいえ";
    SurveyResultDialogFragment mSurveyResultDialogFragment;
    Survey mSurvey;
    String mTapStr;
    //
    String mViewNumber;
    //
    String mSurveyId;
    //
    String mSelectedId;
    public SurveyResultAsyncTask(SurveyResultDialogFragment surveyResultDialogFragment,Survey survey,String tapStr) {
        this.mSurveyResultDialogFragment = surveyResultDialogFragment;
        //
        mSurvey         =   survey;
        //
        mTapStr         =   tapStr;
        Question question   = mSurvey.getQuestion();
        //  閲覧問番号
        mViewNumber   = question.mNumberOnly;
        //  アンケートID
        mSurveyId     = mSurvey.mSurveyId;
        //
        mSelectedId   = getSelectedId( mSurveyId );
    }
    /**
     * getSelectedIdメソッド
     * ネットワークDB検索用、選択されたID.
     * @param   String  surveyId   選択ID
     * @author Ito Shota
     * @since  2016/08/18
     **/
    String getSelectedId(String surveyId){
        if( mTapStr.equals( S_STR_YES ) ){
            surveyId = surveyId + "_01";
        }else if( mTapStr.equals( S_STR_NO ) ){
            surveyId = surveyId + "_02";
        }else if( mTapStr.equals( S_STR_UNANSWER ) ){
            surveyId = surveyId + "_00";
        }
        return surveyId;
    }

    @Override
    protected String doInBackground(Void... params) {
        try {
            String url = URL.getUrlGetSurveyAnswers( mSurveyId, mViewNumber, mSelectedId );
            String result = MyHttpConnection.run(url);
            QuestionnaireResultAsyncTask.QuestionAnswer model = mGson.fromJson(result,QuestionnaireResultAsyncTask.QuestionAnswer.class);
            StudentObject[] sos = model.mStudentObjects;
            process( sos );
        } catch(IOException e) {
            MyIOException.absorbIOException(e);
        }
        return null;
    }

    void process( StudentObject[] sos ){
        if( this.mSurveyResultDialogFragment == null ){
            return;
        }
        this.mSurveyResultDialogFragment.setItme( sos );
    }
}
