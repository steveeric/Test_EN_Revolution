package jp.pmw.test_en_revolution.questionnaire;

import android.app.Dialog;
import android.content.Context;
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
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import org.json.JSONObject;

import jp.pmw.test_en_revolution.AppController;
import jp.pmw.test_en_revolution.ClassReamingTime;
import jp.pmw.test_en_revolution.ForciblyTransmit;
import jp.pmw.test_en_revolution.MainActivity;
import jp.pmw.test_en_revolution.MainFragmentConfig;
import jp.pmw.test_en_revolution.R;
import jp.pmw.test_en_revolution.TransmitChecker;
import jp.pmw.test_en_revolution.config.URL;

/**
 * Created by scr on 2015/01/05.
 */
public class QuestionnaireDialogFragment extends DialogFragment{

    public static final String QUESTION_NAIRE = "questionnaire";
    public static final String QUESTION_NAIRE_DIALOG_FRAGMENT = "questionnaireDialog";
    private Question question;
    private QuestionnaireFragment questionnaireFragment;

    private Context context;

        /**
         * ファクトリーメソッド
         */
    public static QuestionnaireDialogFragment newInstance(/*String title, String message, int type*/){
        QuestionnaireDialogFragment instance = new QuestionnaireDialogFragment();
        return instance;
    }

    public void setQuestionnaireFragment (QuestionnaireFragment q){
        this.questionnaireFragment = q;

    }


    private MainActivity getMainActivity(){
        return (MainActivity)context;
    }



    /**
     * 強制的に何かを送信中にします.
     * */
    private void forciblyTranmitBmpTransmitId(){
        new ForciblyTransmit().forciblyTranmitBmpTransmitId(getMainActivity());
    }

    /**
     * 現在何か送信中かを調べる.
     * @return false    :   送信中のため、送信NG
     *          true    :   送信していないので、送信OK
     * **/
    private boolean chkTransmitPossible(){
        return new TransmitChecker(getMainActivity()).chkTransmitPossible();
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

        context = getActivity();
        Dialog dialog = new Dialog(context);
        // タイトル非表示
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        // フルスクリーン
        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        dialog.setContentView(R.layout.dialog_custom_questionnaire);
        // 背景を透明にする
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextView questionTitle = (TextView)dialog.findViewById(R.id.dialog_custom_questionnaire_title);
        //questionTitle.setText(question.getQuestionTitle());

        LinearLayout startLayout = (LinearLayout)dialog.findViewById(R.id.dialog_custom_questionnaire_start_linearlayout);
        LinearLayout checkLayout = (LinearLayout)dialog.findViewById(R.id.dialog_custom_questionnaire_check_linearlayout);
        LinearLayout resultLayout = (LinearLayout)dialog.findViewById(R.id.dialog_custom_questionnaire_result_linearlayout);
        //  実行中ですレイアウト
        LinearLayout anotherLayout = (LinearLayout)dialog.findViewById(R.id.dialog_custom_questionnaire_do_another_executing_linearlayout);

        TextView actionTV = (TextView)dialog.findViewById(R.id.dialog_custom_questionnaire_action_textView);

        if(question.getQuestionResultStartDateTime() == null){
            boolean transmitPossible = chkTransmitPossible();
            if(!transmitPossible){
                dismiss();
                return dialog;
            }
        }


        String strNo = "";
        if(this.question.getQuesiontStartDateTime()==null && this.question.getQuestionCheckStartDateTime() == null){
            //  問題文を送信します.
            String title = this.getResources().getString(R.string.questionnaire_topic) + question.getQuestionNumber() + strNo
                    + this.getResources().getString(R.string.questionnaire_custom_dialog_start);
            //String str = this.getResources().getString(R.string.questionnaire_custom_dialog_warning1);

            //questionTitle.setText(title);
            actionTV.setText(title);

            Button donotStartNegativeBtn = (Button)dialog.findViewById(R.id.dialog_custom_questionnaire_dontstart_negative_button);
            Button startPositiveBtn = (Button)dialog.findViewById(R.id.dialog_custom_questionnaire_start_positive_button);
            donotStartNegativeBtn.setOnClickListener(questionnaireStartBtnListener);
            startPositiveBtn.setOnClickListener(questionnaireStartBtnListener);
            //
            startLayout.setVisibility(View.VISIBLE);
            //checkLayout.setVisibility(View.GONE);
            //resultLayout.setVisibility(View.GONE);
        }else if(this.question.getQuestionEndDateTime()!=null && this.question.getQuestionCheckStartDateTime() == null){
            //  回答状況確認赤外線を送信します.
            String title = this.getResources().getString(R.string.questionnaire_topic) + question.getQuestionNumber() + strNo
                    +this.getResources().getString(R.string.questionnaire_custom_dialog_check);
            //String str = this.getResources().getString(R.string.questionnaire_custom_dialog_warning2);

            //questionTitle.setText(title);
            actionTV.setText(title);
            Button donotStartNegativeBtn = (Button)dialog.findViewById(R.id.dialog_custom_questionnaire_check_negative_button);
            Button startPositiveBtn = (Button)dialog.findViewById(R.id.dialog_custom_questionnaire_check_positive_button);
            donotStartNegativeBtn.setOnClickListener(questionnaireCheckStartBtnListener);
            startPositiveBtn.setOnClickListener(questionnaireCheckStartBtnListener);

            //
           // startLayout.setVisibility(View.GONE);
            checkLayout.setVisibility(View.VISIBLE);
            //resultLayout.setVisibility(View.GONE);
        }else if(this.question.getQuestionEndDateTime()!=null && this.question.getQuestionCheckStartDateTime() != null && this.question.getQuestionResultStartDateTime() == null){
            String title = this.getResources().getString(R.string.questionnaire_topic) + question.getQuestionNumber() + strNo
                    +this.getResources().getString(R.string.questionnaire_custom_dialog_result);
            //String str = this.getResources().getString(R.string.questionnaire_custom_dialog_warning1);

            //questionTitle.setText(title);
            actionTV.setText(title);

            Button resultNegativeBtn = (Button)dialog.findViewById(R.id.dialog_custom_questionnaire_reslut_negative_button);
            Button resultPositiveBtn = (Button)dialog.findViewById(R.id.dialog_custom_questionnaire_reslut_positive_button);

            resultNegativeBtn.setOnClickListener(questionnaireresultBtnListener);
            resultPositiveBtn.setOnClickListener(questionnaireresultBtnListener);

            //
            //startLayout.setVisibility(View.GONE);
            //checkLayout.setVisibility(View.GONE);
            resultLayout.setVisibility(View.VISIBLE);
        }else if(this.question.getQuestionEndDateTime()!=null && this.question.getQuestionCheckStartDateTime() != null && this.question.getQuestionResultStartDateTime() != null) {
            String title = this.getResources().getString(R.string.questionnaire_topic) + question.getQuestionNumber() + strNo
                    +this.getResources().getString(R.string.questionnaire_custom_dialog_confirm_result);
            //String str = this.getResources().getString(R.string.questionnaire_custom_dialog_confirm_result);

            //questionTitle.setText(title);
            actionTV.setText(title);

            Button resultNegativeBtn = (Button)dialog.findViewById(R.id.dialog_custom_questionnaire_reslut_negative_button);
            Button resultPositiveBtn = (Button)dialog.findViewById(R.id.dialog_custom_questionnaire_reslut_positive_button);

            resultNegativeBtn.setOnClickListener(questionnaireresultBtnListener);
            resultPositiveBtn.setOnClickListener(questionnaireresultBtnListener);

            //
            //startLayout.setVisibility(View.GONE);
            //checkLayout.setVisibility(View.GONE);
            resultLayout.setVisibility(View.VISIBLE);
        }else{
            //  実行中のためしばらく待ってください.
            actionTV.setText(this.getResources().getString(R.string.questionnaire_custom_dialog_another));
            Button closeBtn = (Button)dialog.findViewById(R.id.dialog_custom_questionnaire_do_another_executing_closeBtn);
            closeBtn.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    dismiss();
                }
            });
            anotherLayout.setVisibility(View.VISIBLE);
        }

        return dialog;
    }

    //  回答状況確認ボタンリスナー
    private View.OnClickListener questionnaireCheckStartBtnListener = new View.OnClickListener() {
        public void onClick(View v) {
            switch(v.getId()) {
                case R.id.dialog_custom_questionnaire_check_negative_button:
                    //いいえ
                    dismiss();
                    break;
                case R.id.dialog_custom_questionnaire_check_positive_button:
                   //はい
                   //TODO:赤外線送信要求を行う
                    startQruestionnaireCheck();
                    reSetNextActionTextView();
                    dismiss();
                    break;
            }
        }
    };

    //  回答結果確認ボタンリスナー
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
                    //moveToResultFragment();
                    //startQruestionnaireResult();
                    judgeQuestionnaireResult();
                    reSetNextActionTextView();
                    dismiss();
                    break;
            }
        }
    };

    private void moveToResultFragment(){
        MainActivity activity = (MainActivity)context;
        //String questionId = this.question.getQuestionId();
        String titleNumber = this.question.getQuestionNumber();
        activity.getClassObject().getQuestionnaire().setLastSeeQuestionTitleNumber(titleNumber);
        activity.doClickerDistributeFragment(MainFragmentConfig.QUESTIONNAIRE_FRAGMENT,MainFragmentConfig.QUESTIONNAIRE_FRAGMENT_RESULT);
    }


    private void reSetNextActionTextView(){
        this.questionnaireFragment.setNextActionTextView("");
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
                    startQuestionnaireText();
                    reSetNextActionTextView();
                    dismiss();
                    break;
            }
        }
    };

    /*private void startQuestionnaire(){
        this.question.setQuestionStartdateTime();
        MainActivity activity = (MainActivity)this.getActivity();
        String questionId = this.question.getQuestionId();
        activity.setLastSeeQuestionId(questionId);
    }*/
    /**
     * 問題文送信要求DBに出す.
     * **/
    private void startQuestionnaireText(){

        //
        String questionIds = this.question.getQuestionId();
        final String questionNumber = this.question.getQuestionNumber();
        String url = URL.getQuestionnaireTextStart(questionIds);
        JsonObjectRequest request = new JsonObjectRequest(url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        setStartQuestionnaireTextTime(response);
                    }
                }, new Response.ErrorListener() {
            @Override public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Q"+questionNumber+"の問題送信が開始できませんでした." + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        AppController.getInstance(this.getActivity()).getRequestQueue().add(request);
    }

    /*問題文送信開始時刻をJSON形式で受け取りメモリに加える*/
    void setStartQuestionnaireTextTime(JSONObject response){
        Gson gson = new Gson();
        TransmitQuestion tq =  gson.fromJson(response.toString(), TransmitQuestion.class);
        this.question.setQuestionStartdateTime(tq.startTextTime);
        forciblyTranmitBmpTransmitId();
    }

    /*
    * アンケート回答調査送信開始要求
    * */
    private void startQruestionnaireCheck(){

        /*boolean transmitPossible = chkTransmitPossible();
        if(!transmitPossible){
            return;
        }*/

        String questionIds = this.question.getQuestionId();
        final String questionNumber = this.question.getQuestionNumber();
        String url = URL.getQuestionnaireCheckStart(questionIds);
        JsonObjectRequest request = new JsonObjectRequest(url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        setQuestionnaireCheckTime(response);
                    }
                }, new Response.ErrorListener() {
            @Override public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Q"+questionNumber+"の回答調査送信が開始できませんでした." + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        AppController.getInstance(this.getActivity()).getRequestQueue().add(request);
    }
    /*回答調査送信開始時刻をJSON形式で受け取りメモリに加える*/
    void setQuestionnaireCheckTime(JSONObject response){
        Gson gson = new Gson();
        TransmitQuestion tq =  gson.fromJson(response.toString(), TransmitQuestion.class);
        this.question.setQuestionCheckStartDateTime(tq.startCheckTime);
        forciblyTranmitBmpTransmitId();
    }

    private void judgeQuestionnaireResult(){
        if(this.question.getQuestionResultStartDateTime() == null){
            startQruestionnaireResult();
        }else{
            getOnlyQuestionResult();
        }
    }

    /*
    * アンケート結果送信要求と回答者数の取得
    * */
    private void startQruestionnaireResult(){
        if(this.question.getQuestionResultStartDateTime() != null){
            moveToResultFragment();
            return;
        }
        String questionIds = this.question.getQuestionId();
        final String sameClassNumber = this.getMainActivity().getClassObject().getSameClassNumber();
        final String questionNumber = this.question.getQuestionNumber();
        String url = URL.getQuestionResult(sameClassNumber,questionIds);
        JsonObjectRequest request = new JsonObjectRequest(url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        setQuestionnaireResultTime(response);
                    }
                }, new Response.ErrorListener() {
            @Override public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Q"+questionNumber+"の回答結果送信が開始できませんでした." + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        AppController.getInstance(this.getActivity()).getRequestQueue().add(request);
    }

    /**
     * アンケート結果のみを取得
     * **/
    private void getOnlyQuestionResult() {
        String questionIds = this.question.getQuestionId();
        final String sameClassNumber = this.getMainActivity().getClassObject().getSameClassNumber();
        final String questionNumber = this.question.getQuestionNumber();
        String url = URL.getOnlyQuestionResult(sameClassNumber, questionIds);
        JsonObjectRequest request = new JsonObjectRequest(url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        setQuestionnaireResultTime(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Q" + questionNumber + "の回答結果送信が開始できませんでした." + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        AppController.getInstance(this.getActivity()).getRequestQueue().add(request);
    }


    /*回答送信開始時刻をJSON形式で受け取りメモリに加える*/
    void setQuestionnaireResultTime(JSONObject response){
        Gson gson = new Gson();
        TransmitQuestion tq =  gson.fromJson(response.toString(), TransmitQuestion.class);
        this.question.setQuestionResultStartDateTime(tq.startResultTime);
        this.question.getResult().setYesCount(tq.yesCount);
        this.question.getResult().setNoCount(tq.noCount);
        this.question.getResult().setYesRatio(tq.yesRatio);
        this.question.getResult().setNoRatio(tq.noRatio);

        moveToResultFragment();
    }



    class TransmitQuestion{
        @SerializedName("question_id")
        String questionid;
        @SerializedName("question_text_open_start_time")
        String startTextTime;
        @SerializedName("question_text_open_end_time")
        String endtextTime;
        @SerializedName("question_check_open_start_time")
        String startCheckTime;
        @SerializedName("question_check_end_time")
        String endCheckTime;
        @SerializedName("question_result_start_time")
        String startResultTime;

        @SerializedName("yes_count")
        int yesCount;
        @SerializedName("no_count")
        int noCount;

        @SerializedName("yes_ratio")
        float yesRatio;
        @SerializedName("no_ratio")
        float noRatio;


    }

  }