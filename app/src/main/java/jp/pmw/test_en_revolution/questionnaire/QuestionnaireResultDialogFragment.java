package jp.pmw.test_en_revolution.questionnaire;

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
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.echo.holographlibrary.Line;

import jp.pmw.test_en_revolution.MainActivity;
import jp.pmw.test_en_revolution.R;
import jp.pmw.test_en_revolution.StudentObject;
import jp.pmw.test_en_revolution.attendee.RosterCustomAdapter_2;

/**
 * Created by si on 2016/08/07.
 * QuestionnaireResultDialogFragmentクラス
 * クリッカーの「はい」と「いいえ」を答えた学生を表示します.
 */
public class QuestionnaireResultDialogFragment extends DialogFragment {
    public static final String TAP_POSITION = "tap_position";
    public static final String QUESTION     = "question";
    public static final String QUESTIONNNAIRE_RESULT_DIALOG_FRAGMENT = "questionnaire_result_dialog_fragment";
     public static QuestionnaireResultDialogFragment newInstance(){
        QuestionnaireResultDialogFragment instance = new QuestionnaireResultDialogFragment();
        return instance;
    }
    //  グリッドビューのカラム数
    private static final int COLUMN_COUNT = 2;
    //  「はい」をタップ
    private static final int TAP_POSITON_YES = 0;
    //  「いいえ」をタップ
    private static final int TAP_POSITION_NO = 1;
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
    Button   mCloseBtn;
    QuestionnaireResultAdapter mAdapter;
    int mTapPosition;
    Question mQuestion;

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
        mLinearLayout.setVisibility(View.GONE);;
        //  回答者の情報をネットワークから取得
        String questionId = mQuestion.getQuestionId();
        new QuestionnaireResultAsyncTask(this, questionId, mTapPosition).execute();
    }

    /**
     * layoutメソッド
     * レイアウト用インスタンス生成.
     * @param   Dialog  d   ダイアログ
     * @author Ito Shota
     * @since  2016/08/07
     **/
    void layout(Dialog d){
        this.mLinearLayout = (LinearLayout) d.findViewById(R.id.dialog_fragment_questionnaire_result_ll);
        this.mLoadingPb = (ProgressBar) d.findViewById(R.id.dialog_fragment_questionnaire_result_loding_pb);
        this.mSelectItemTv = (TextView) d.findViewById(R.id.dialog_fragment_questionnaire_result_text_tv);
        this.mAnswerGv   = (GridView) d.findViewById(R.id.dialog_fragment_questionnaire_result_answer_gv);
        this.mExistAnswerTv = (TextView)d.findViewById(R.id.dialog_fragment_questionnaire_result_not_exist_answer_tv);
        this.mCloseBtn = (Button)d.findViewById(R.id.dialog_fragment_questionnaire_result_close_btn);
        //  グリッドビューのカラム数
        setColumn();
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
        mTapPosition = getArguments().getInt(TAP_POSITION);
        //
        mQuestion = (Question)getArguments().getSerializable(QUESTION);
    }
    /**
     * setTextSelectItemメソッド
     * 選択された文字列をセットする.
     * @author Ito Shota
     * @since  2016/08/08
     **/
    void setTextSelectItem(){
        String str = this.mQuestion.getAsks().get(0).getChoices().get(mTapPosition).getChoice();
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
                if( sos.length > 0 ){
                    mAdapter = new QuestionnaireResultAdapter(getActivity(), RosterCustomAdapter_2.ALL_LAYOUT ,sos);
                    mAnswerGv.setAdapter( mAdapter );
                    mAnswerGv.setVisibility(View.VISIBLE);
                }else{
                    mExistAnswerTv.setVisibility(View.VISIBLE);
                }
                mLoadingPb.setVisibility(View.GONE);
                mLinearLayout.setVisibility(View.VISIBLE);
            }
        });
    }
    /*void setDummyYes(){
        StudentObject[] sos = getDummy();
        mYesAdapter = new QuestionnaireResultAdapter(getActivity(), RosterCustomAdapter_2.ALL_LAYOUT ,sos);
        mAnswerGv.setAdapter( mYesAdapter );
    }*/
    StudentObject[] getDummy(){
        StudentObject[] sos = new StudentObject[5];
        sos[0]  = new StudentObject("J07001","アンドウ タカシ","安藤 隆", null, null);
        sos[1]  = new StudentObject("J07002","イトウ ショウタ","伊藤 翔太", null, null);
        sos[2]  = new StudentObject("J07003","カトウ タカシ","加藤 隆", null, null);
        sos[3]  = new StudentObject("J070010","サトウ ミチアキ","佐藤 道明", null, null);
        sos[4]  = new StudentObject("J070032","ナイトウ ノブテル","内藤 伸晃", null, null);
        return sos;
    }




}