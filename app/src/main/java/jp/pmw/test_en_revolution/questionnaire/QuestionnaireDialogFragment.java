package jp.pmw.test_en_revolution.questionnaire;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import jp.pmw.test_en_revolution.MainActivity;
import jp.pmw.test_en_revolution.MainFragmentConfig;
import jp.pmw.test_en_revolution.R;

/**
 * Created by scr on 2015/01/05.
 */
public class QuestionnaireDialogFragment extends DialogFragment{

    public static final String QUESTION_NAIRE = "questionnaire";
    public static final String QUESTION_NAIRE_DIALOG_FRAGMENT = "questionnaireDialog";
    private Question question;

        /**
         * ファクトリーメソッド
         */
    public static QuestionnaireDialogFragment newInstance(/*String title, String message, int type*/){
        QuestionnaireDialogFragment instance = new QuestionnaireDialogFragment();
        return instance;
    }

    /**
     * コールバック用のリスナー
     * **/
    /*private OnOkClickListener mListener;
    public interface OnOkClickListener {
        public void onOkClicked(Bundle args);
    }*/

    /*@Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mListener = (OnOkClickListener) getTargetFragment();
        if (mListener instanceof OnOkClickListener == false) {
            throw new ClassCastException("実装エラー");
        }
    }*/

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        question = (Question)getArguments().getSerializable(QUESTION_NAIRE);


        Dialog dialog = new Dialog(getActivity());
        // タイトル非表示
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        // フルスクリーン
        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        dialog.setContentView(R.layout.dialog_custom_questionnaire);
        // 背景を透明にする
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextView questionTitle = (TextView)dialog.findViewById(R.id.dialog_custom_questionnaire_title);
        questionTitle.setText(question.getQuestionTitle());

        LinearLayout startLayout = (LinearLayout)dialog.findViewById(R.id.dialog_custom_questionnaire_start_linearlayout);
        LinearLayout resultLayout = (LinearLayout)dialog.findViewById(R.id.dialog_custom_questionnaire_result_linearlayout);

        TextView actionTV = (TextView)dialog.findViewById(R.id.dialog_custom_questionnaire_action_textView);

        if(this.question.getQuesiontStartDateTime()==null){
            actionTV.setText(this.getResources().getString(R.string.questionnaire_custom_dialog_start));
            Button donotStartNegativeBtn = (Button)dialog.findViewById(R.id.dialog_custom_questionnaire_dontstart_negative_button);
            Button startPositiveBtn = (Button)dialog.findViewById(R.id.dialog_custom_questionnaire_start_positive_button);
            donotStartNegativeBtn.setOnClickListener(questionnaireStartBtnListener);
            startPositiveBtn.setOnClickListener(questionnaireStartBtnListener);
            startLayout.setVisibility(View.VISIBLE);
            resultLayout.setVisibility(View.GONE);
        }else{
            actionTV.setText(this.getResources().getString(R.string.questionnaire_custom_dialog_result));

            Button resultNegativeBtn = (Button)dialog.findViewById(R.id.dialog_custom_questionnaire_reslut_negative_button);
            Button resultPositiveBtn = (Button)dialog.findViewById(R.id.dialog_custom_questionnaire_reslut_positive_button);

            resultNegativeBtn.setOnClickListener(questionnaireresultBtnListener);
            resultPositiveBtn.setOnClickListener(questionnaireresultBtnListener);

            startLayout.setVisibility(View.GONE);
            resultLayout.setVisibility(View.VISIBLE);
        }

        return dialog;
    }

    private View.OnClickListener questionnaireresultBtnListener = new View.OnClickListener() {
        public void onClick(View v) {
            switch(v.getId()) {
                case R.id.dialog_custom_questionnaire_reslut_negative_button:
                    //いいえ
                    dismiss();
                    break;
                case R.id.dialog_custom_questionnaire_reslut_positive_button:
                    //はい
                    //回答結果フラグメントに移動
                    moveToResultFragment();
                    dismiss();
                    break;
            }
        }
    };

    private void moveToResultFragment(){
        MainActivity activity = (MainActivity)this.getActivity();
        String questionId = this.question.getQuestionId();
        activity.setLastSeeQuestionId(questionId);
        activity.doClickerDistributeFragment(MainFragmentConfig.QUESTIONNAIRE_FRAGMENT,MainFragmentConfig.QUESTIONNAIRE_FRAGMENT_RESULT);
    }



    private View.OnClickListener questionnaireStartBtnListener = new View.OnClickListener() {
        public void onClick(View v) {
            switch(v.getId()) {
                case R.id.dialog_custom_questionnaire_dontstart_negative_button:
                   //いいえ
                    dismiss();
                    break;
                case R.id.dialog_custom_questionnaire_start_positive_button:
                    //はい
                    startQuestionnaire();
                    //回答結果確認画面へ
                    moveToResultFragment();
                    dismiss();
                    break;
            }
        }
    };

    private void startQuestionnaire(){
        this.question.setQuestionStartdateTime();
        MainActivity activity = (MainActivity)this.getActivity();
        String questionId = this.question.getQuestionId();
        activity.setLastSeeQuestionId(questionId);
    }

  }