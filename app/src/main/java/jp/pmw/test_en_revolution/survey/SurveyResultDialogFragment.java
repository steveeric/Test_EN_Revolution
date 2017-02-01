package jp.pmw.test_en_revolution.survey;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.DialogFragment;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Timer;

import jp.pmw.test_en_revolution.MainActivity;
import jp.pmw.test_en_revolution.R;
import jp.pmw.test_en_revolution.StudentObject;
import jp.pmw.test_en_revolution.attendee.RosterCustomAdapter_2;
import jp.pmw.test_en_revolution.confirm_class_plan.Student;
import jp.pmw.test_en_revolution.questionnaire.QuestionnaireResultAdapter;

/**
 * Created by si on 2016/08/18.
 * SurveyResultDialogFragmentクラス
 * スマホ クリッカー(アンケート)の詳細な結果を表示する
 */
public class SurveyResultDialogFragment  extends DialogFragment {
    public static final String TAP_POSITION = "tap_position";
    public static final String SURVEY     = "survey";
    public static final String TAP_STR     = "tap_str";
    public static final String SURVEY_RESULT_DIALOG_FRAGMENT = "survey_result_dialog_fragment";
    public static SurveyResultDialogFragment newInstance(){
        SurveyResultDialogFragment instance = new SurveyResultDialogFragment();
        return instance;
    }
    //  グリッドビューのカラム数
    private static final int COLUMN_COUNT = 2;
    //  UIハンドラー
    Handler mHandler = new Handler(Looper.myLooper());
    //
    ProgressBar mLoadingPb;
    //  選択回答文言「はい」又は「いいえ」
    LinearLayout mLinearLayout;
    TextView mSelectItemTv;
    GridView mAnswerGv;
    TextView mNoAnswerTv;
    TextView mExistAnswerTv;
    Button mUpdateBtn;
    Button mCloseBtn;
    QuestionnaireResultAdapter mAdapter;
    int mTapPosition;
    String mTapStr;
    Survey mSurvey;
    Timer mSurveyResultTimer;

    MainActivity getMainAcitivty(){
        return (MainActivity) getActivity();
    }

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getActivity());
        // タイトル非表示
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        // フルスクリーン
        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        dialog.setContentView(R.layout.dialog_fragnemt_questionnaire_result);
        // 背景を透明にする
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int dialogWidth = (int) (metrics.widthPixels * 0.8);
        int dialogHeight = (int) (metrics.heightPixels * 0.8);
        lp.width = dialogWidth;
        lp.height = dialogHeight;
        dialog.getWindow().setAttributes(lp);
        //  レイアウトインスタンス生成
        layout(dialog);
        //  引数で渡されたパラメータを取得する.
        getMyArguments();
        //  「はい」又は「いいえ」の文字列がセットされます.
        setTextSelectItem();
        return dialog;
    }
    public void onResume(){
        super.onResume();
        //  プログレスバーを表示し、それ以外はgone.
        mLoadingPb.setVisibility(View.VISIBLE);
        mLinearLayout.setVisibility(View.GONE);
        //mSurveyResultTimer = new Timer();
        //mSurveyResultTimer.schedule(new SurveyResultTimerTask(this), 0 ,3000);
        processSurveyResultAsyncTask();
    }
    /**
     * processSurveyResultAsyncTaskメソッド
     * 回答状況をネットワークから取得します.
     * @author Ito Shota
     * @since  2016/08/19
     **/
    void processSurveyResultAsyncTask(){
        //  アンケート(スマホ クリッカー)の回答状況を取得する.
        new SurveyResultAsyncTask(this, mSurvey, mTapStr).execute();
    }


    public void onPause(){
        super.onPause();
        if( mSurveyResultTimer != null ){
            mSurveyResultTimer.cancel();
            mSurveyResultTimer = null;
        }
    }

    /**
     * layoutメソッド
     * レイアウト用インスタンス生成.
     * @param   Dialog  d   ダイアログ
     * @author Ito Shota
     * @since  2016/08/07
     **/
    void layout(Dialog d){
        this.mUpdateBtn = (Button) d.findViewById(R.id.dialog_fragment_questionnaire_result_update_btn);
        this.mLinearLayout = (LinearLayout) d.findViewById(R.id.dialog_fragment_questionnaire_result_ll);
        this.mLoadingPb = (ProgressBar) d.findViewById(R.id.dialog_fragment_questionnaire_result_loding_pb);
        this.mSelectItemTv = (TextView) d.findViewById(R.id.dialog_fragment_questionnaire_result_text_tv);
        this.mAnswerGv   = (GridView) d.findViewById(R.id.dialog_fragment_questionnaire_result_answer_gv);
        this.mExistAnswerTv = (TextView)d.findViewById(R.id.dialog_fragment_questionnaire_result_not_exist_answer_tv);
        this.mCloseBtn = (Button)d.findViewById(R.id.dialog_fragment_questionnaire_result_close_btn);
        //  グリッドビューのカラム数
        setColumn();
        //
        setUpdateBtnListener();
        //
        setCloseBtnListener();
    }
    /**
     * setColumnメソッド
     * グリッドビューにカラム数をセットする.
     * @author Ito Shota
     * @since  2016/08/07
     **/
    void setColumn(){
        this.mAnswerGv.setNumColumns(COLUMN_COUNT);
    }
    /**
     * setUpdateBtnListenerメソッド
     * 更新ボタン
     * @author Ito Shota
     * @since  2016/08/19
     **/
    void setUpdateBtnListener(){
        this.mUpdateBtn.setOnClickListener( updateBtnListener );
    }
    View.OnClickListener updateBtnListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mLoadingPb.setVisibility(View.VISIBLE);
            mLinearLayout.setVisibility(View.GONE);
            processSurveyResultAsyncTask();
        }
    };
    /**
     * setCloseBtnListenerメソッド
     * 「×」ボタンのリスナー
     * @author Ito Shota
     * @since  2016/08/08
     **/
    void setCloseBtnListener(){
        this.mCloseBtn.setOnClickListener( closeBtnListener );
    }
    View.OnClickListener closeBtnListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            dismiss();
        }
    };
    /**
     * getMyArgumentsメソッド
     * 引数をセットする.
     * @author Ito Shota
     * @since  2016/08/08
     **/
    void getMyArguments(){
        //
        mTapStr         = getArguments().getString(TAP_STR);
        //
        mTapPosition    = getArguments().getInt(TAP_POSITION);
        //
        mSurvey         = (Survey)getArguments().getSerializable(SURVEY);
    }
    /**
     * setTextSelectItemメソッド
     * 選択された文字列をセットする.
     * @author Ito Shota
     * @since  2016/08/08
     **/
    void setTextSelectItem(){
        String str = mSurvey.getQuestion().mChoices[getTapChoice()].mChoice;
        this.mSelectItemTv.setText(str);
    }
    /**
     * setItmeメソッド
     * ネットワークから取得したデータを表示レイアウトインスタンスにセットします.
     * @author Ito Shota
     * @since  2016/08/08
     **/
    public void setItme(final StudentObject[] sos){
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                //  更新ボタン
                mUpdateBtn.setVisibility(View.VISIBLE);
                if( sos.length > 0 ){
                    setMyAdapter( sos );
                    mAnswerGv.setAdapter( mAdapter );
                    mAnswerGv.setVisibility(View.VISIBLE);
                    mExistAnswerTv.setVisibility(View.GONE);
                }else{
                    mExistAnswerTv.setVisibility(View.VISIBLE);
                }
                mLoadingPb.setVisibility(View.GONE);
                mLinearLayout.setVisibility(View.VISIBLE);
            }
        });
    }
    /**
     * setMyAdapterメソッド
     * アダプターをセットする.
     * @author Ito Shota
     * @since  2016/08/19
     **/
    public void setMyAdapter(StudentObject[] sos){
        String color = mSurvey.getQuestion().mChoices[getTapChoice()].mChoiceIndexColor;
        mAdapter = new QuestionnaireResultAdapter(getActivity(), RosterCustomAdapter_2.ALL_LAYOUT, color, sos);
    }

    /**
     * getTapChoiceメソッド
     * タップされたグリッドビューのインデックスを取得する.
     * @author Ito Shota
     * @since  2016/08/18
     **/
    int getTapChoice(){
        int index = 0;
        Question q = this.mSurvey.getQuestion();
        Choice[] choices = q.mChoices;
        for(int i = 0; i < choices.length; i++){
            String strChoice = choices[i].mChoiceId;
            if( strChoice.equals(this.mTapStr) ){
                index = i;
                break;
            }
        }
        return index;
    }
}
