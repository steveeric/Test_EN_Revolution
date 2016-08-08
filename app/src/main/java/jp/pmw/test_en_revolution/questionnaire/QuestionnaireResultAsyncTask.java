package jp.pmw.test_en_revolution.questionnaire;

import android.app.DialogFragment;

import com.google.gson.annotations.SerializedName;

import java.io.IOException;

import jp.pmw.test_en_revolution.StudentObject;
import jp.pmw.test_en_revolution.config.URL;
import jp.pmw.test_en_revolution.network.MyAsyncTask;
import jp.pmw.test_en_revolution.network.MyHttpConnection;
import jp.pmw.test_en_revolution.network.MyIOException;

/**
 * Created by si on 2016/08/08.
 * QuestionnaireResultAsyncTaskクラス
 */
public class QuestionnaireResultAsyncTask extends MyAsyncTask {
    QuestionnaireResultDialogFragment  mDialogFragment;
    String          mQuestionid;
    int             mAnswerType;
    public QuestionnaireResultAsyncTask(QuestionnaireResultDialogFragment df, String questionId, int answerType){
        this.mDialogFragment    =   df;
        this.mQuestionid        =   questionId;
        this.mAnswerType        =   answerType;
    }

    @Override
    protected String doInBackground(Void... params) {
        try {
            String url = URL.getUrlgetQuestionAnswers( mQuestionid, mAnswerType );
            String result = MyHttpConnection.run(url);
            QuestionAnswer model = mGson.fromJson(result,QuestionAnswer.class);
            StudentObject[] sos = model.mStudentObjects;
            process( sos );
        } catch(IOException e) {
            MyIOException.absorbIOException(e);
        }
        return null;
    }

    void process( StudentObject[] sos ){
        if( this.mDialogFragment == null ){
            return;
        }
        this.mDialogFragment.setItme( sos );
    }

    public class QuestionAnswer{
        @SerializedName("question_answers")
        public StudentObject[] mStudentObjects;
    }

}
