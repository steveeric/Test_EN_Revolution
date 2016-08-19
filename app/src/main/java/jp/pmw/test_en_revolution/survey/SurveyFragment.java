package jp.pmw.test_en_revolution.survey;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.echo.holographlibrary.PieGraph;
import com.echo.holographlibrary.PieSlice;
import java.util.Timer;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jp.pmw.test_en_revolution.MainActivity;
import jp.pmw.test_en_revolution.MyMainFragment;
import jp.pmw.test_en_revolution.R;
import jp.pmw.test_en_revolution.TransmitReAttChecker;
import jp.pmw.test_en_revolution.questionnaire.*;

/**
 * SurveyFragmentクラス
 * アンケート画面です.
 * @author Ito Shota
 * @since  2016/08/17
 **/
public class SurveyFragment  extends MyMainFragment {
    private static final String ARG_SECTION_NUMBER = "section_number";
    /**
     * シングルトン
     * */
    public static SurveyFragment newInstance( int sectionNumber ) {
        SurveyFragment fragment = new SurveyFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }
    //  アンケートクラス
    Survey          mSurvey;
    //  アダプター
    ChoiceAdapter   mChoiceAdapter;

    //  アンケートを開始するボタン
    @BindView(R.id.fragment_survey_before_start_btn)
    Button mBeforeStartBtn;
    //  アンケート開始後のレイアウト
    @BindView(R.id.fragment_survey_end_start_ll)
    LinearLayout mEndStartLl;
    //  「前のアンケート問題に戻る」ボタン
    @BindView(R.id.fragment_survey_end_start_backpage_btn)
    Button      mBackPageBtn;
    //  「次のアンケート問題に進む」ボタン
    @BindView(R.id.fragment_survey_end_start_nextpage_btn)
    Button      mNextPageBtn;
    //  アンケート番号をセットする.
    @BindView(R.id.fragment_survey_end_start_question_number_tv)
    TextView    mNumberTv;
    //  アンケート問題をセットする
    @BindView(R.id.fragment_survey_end_start_title_tv)
    TextView    mTitleTv;
    //  円グラフ
    @BindView(R.id.fragment_survey_end_start_piegraph_tv)
    PieGraph    mPieGraph;
    //  アンケート問題をセットする
    @BindView(R.id.fragment_survey_end_start_choices_gv)
    GridView    mChoicesGv;

    //  タイマー処理(アンケートを随時取得します.)
    Timer mSurveyTimer;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view   =   inflater.inflate(R.layout.fragment_survey, container, false);
        mUnbinder = ButterKnife.bind(this, view);
        return  view;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        //
        this.mSurvey   =   new Survey();
    }
    @Override
    public void onResume() {
        super.onResume();
        MainActivity activity = (MainActivity)this.getActivity();
        Survey survey = activity.getClassObject().getSurvey();
        if(survey == null){
            //リロードフラグメント
            super.loadFragment();
        }else{
            this.mSurvey    =   survey;
            chkSurveyState();
            assingProcess();
            mSurveyTimer = new Timer();
            mSurveyTimer.schedule( new SurveyTimerTask( this ), 0, RELOAD_INTERVAL );
        }
    }
    @Override
    public void onPause(){
        super.onPause();
        if( mSurveyTimer != null ){
            mSurveyTimer.cancel();
            mSurveyTimer = null;
        }
    }

    /**
     * assingProcessメソッド
     * アンケート実施状況に応じて処理を振り分けます.
     * @author Ito Shota
     * @since  2016/08/17
     **/
    void assingProcess(){
        if( mSurvey.mSurveyState        ==  Survey.S_BEFORE_START ){
            this.mBeforeStartBtn.setVisibility(View.VISIBLE);
            this.mEndStartLl.setVisibility(View.GONE);
        }else if( mSurvey.mSurveyState  ==  Survey.S_END_START ){
            endStartPtocess();
        }else{
            //  エラー
        }
    }
    /**
     * chkSurveyStateメソッド
     * アンケート実施状態を調べる.
     * ①　アンケート開始前(赤外線送信前)
     * ②　アンケート開始後
     * @return int 処理状況
     * @author Ito Shota
     * @since  2016/08/17
     **/
    void chkSurveyState(){
        this.mSurvey.setSurveyState();
    }
    /**
     * onClickBeforeStartBtnメソッド
     * 「アンケートを開始する」ボタンのリスナー
     * @author Ito Shota
     * @since  2016/08/17
     **/
    @OnClick(R.id.fragment_survey_before_start_btn)
    public void onClickBeforeStartBtn() {
        MainActivity ma     =   getMainActivity();
        boolean nextProcess = new TransmitReAttChecker( ma ).chkTransmitPossible();
        if( !nextProcess ){
            //  出席認定送信が終了していなければ、スマホ クリッカー(アンケート)を実施不可です.
            return;
        }
        new StartSurveyAsynckTask( this ).execute();
        //  強制的に、アンケート開始にする.
        this.mSurvey.mStartTime = "";
        assingProcess();
    }
    /**
     * showEndStartProcessメソッド
     * アンケート開始後の画面を表示する.
     * @author Ito Shota
     * @since  2016/08/17
     **/
    void showEndStartProcess(){
        this.mBeforeStartBtn.setVisibility(View.GONE);
        this.mEndStartLl.setVisibility(View.VISIBLE);
    }
    /**
     * endStartPtocessメソッド
     * アンケート開始後の処理を行う..
     * @author Ito Shota
     * @since  2016/08/17
     **/
    void endStartPtocess(){
        //ドーナツのように真ん中を空ける
        mPieGraph.setInnerCircleRatio(80);
        //間にパディングをいれる
        mPieGraph.setPadding(1);
        setEndStartItem();
        showPageBtn();
        showEndStartProcess();
    }
    /**
     * setEndStartItemメソッド
     * アンケート回答状況をセットする.
     * @author Ito Shota
     * @since  2016/08/17
     **/
    void setEndStartItem(){
        //this.mSurvey.questions  =   getDummyQuestions();

    }

    @OnClick(R.id.fragment_survey_end_start_backpage_btn)
    void onClickBackPageBtn(){
        int viewPage = this.mSurvey.mViewPageNumber;
        int afterViewPage = --viewPage;
        if( afterViewPage >= 0 ){
            this.mSurvey.mViewPageNumber    =   afterViewPage;
        }
        showPageBtn();
    }
    @OnClick(R.id.fragment_survey_end_start_nextpage_btn)
    void onClickNextPageBtn(){
        int viewPage = this.mSurvey.mViewPageNumber;
        int afterViewPage = ++viewPage;
        if( afterViewPage < this.mSurvey.mQuestions.length ){
            this.mSurvey.mViewPageNumber    =   afterViewPage;
        }
        showPageBtn();
    }

    /**
     * showPageBtnメソッド
     * ページ送りするボタンを表示する.
     * @author Ito Shota
     * @since  2016/08/17
     **/
    void showPageBtn(){
        int viewPage = this.mSurvey.mViewPageNumber;
        if( viewPage == 0 ){
            mNextPageBtn.setVisibility(View.VISIBLE);
            mBackPageBtn.setVisibility(View.INVISIBLE);
        }else if( viewPage == (mSurvey.mQuestions.length - 1) ){
            mNextPageBtn.setVisibility(View.INVISIBLE);
            mBackPageBtn.setVisibility(View.VISIBLE);
        }else{
            mNextPageBtn.setVisibility(View.VISIBLE);
            mBackPageBtn.setVisibility(View.VISIBLE);
        }
        setItem();
    }
    /**
     * setItemメソッド
     * アンケートアイテムの文字列をテキストビューにセットします.
     * @author Ito Shota
     * @since  2016/08/17
     **/
    void setItem(){
        this.mNumberTv.setText( mSurvey.getQuestionNumber() );
        this.mTitleTv.setText( mSurvey.getQuestionTitle() );
        Choice[] choices     =   this.mSurvey.getQuestion().mChoices;
        //  円グラフを描く
        this.mPieGraph.removeSlices();
        for( int i = 0; i < choices.length; i++ ){
            if( choices[i].mAnswer > 0 ){
                PieSlice pieSlice = new PieSlice();
                Choice choice = choices[i];
                String color = choice.mChoiceIndexColor;
                pieSlice.setColor(Color.parseColor(color));
                pieSlice.setValue(choice.mAnswer);
                mPieGraph.addSlice(pieSlice);
            }
        }
        //  リストビュー
        this.mChoiceAdapter = new ChoiceAdapter(getActivity(), R.layout.row_survey_answer_result_item, choices);
        this.mChoicesGv.setAdapter( mChoiceAdapter );
        this.mChoicesGv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Choice c = (Choice)parent.getItemAtPosition(position);
                showSurveyResultDialogFragment( position, c.mChoice );
            }
        });
    }
    /**
     * showSurveyResultDialogFragmentメソッド
     * アンケート回答詳細情報表示フラグメント
     * @author Ito Shota
     * @since  2016/08/19
     **/
    void showSurveyResultDialogFragment(int position, String choice){
        Bundle args = new Bundle();
        args.putString(SurveyResultDialogFragment.TAP_STR, choice);
        args.putInt(SurveyResultDialogFragment.TAP_POSITION, position);
        args.putSerializable(SurveyResultDialogFragment.SURVEY, mSurvey);
        SurveyResultDialogFragment srdf = SurveyResultDialogFragment.newInstance();
        srdf.setArguments(args);
        srdf.show(getActivity().getSupportFragmentManager(), SurveyResultDialogFragment.SURVEY_RESULT_DIALOG_FRAGMENT);
    }
    /*Question[] getDummyQuestions(){
        Question[] questions = new Question[4];

        Choice[] c1s = new Choice[2];
        Choice c1y    =   new Choice();
        c1y.mChoice   =     "はい";
        c1y.mAnswer   =     15;
        c1y.mStrAnswer=     "15人";
        c1y.mChoiceIndexColor = "#0000FF";
        c1y.mPercentage       = "15%";
        c1s[0]          =   c1y;

        Choice c1n    =   new Choice();
        c1n.mChoice   =     "いいえ";
        c1n.mAnswer   =     45;
        c1n.mStrAnswer=     "45人";
        c1n.mChoiceIndexColor = "#FF0000";
        c1n.mPercentage       = "45%";
        c1s[1]          =   c1n;


        Question q0 = new Question();
        q0.mNumber = "問0";
        q0.mTitle  = "授業は理解できましたか？";
        q0.mChoices = c1s;

        Question q1 = new Question();
        q1.mNumber = "問1";
        q1.mTitle  = "子どもが逆上がりができないのは親が運動音痴だからだ";
        q1.mChoices = c1s;

        Choice[] c2s = new Choice[2];
        Choice c2y    =   new Choice();
        c2y.mChoice   =     "はい";
        c2y.mAnswer   =     32;
        c2y.mStrAnswer=     "32人";
        c2y.mChoiceIndexColor = "#0000FF";
        c2y.mPercentage       = "32%";
        c2s[0]          =   c2y;

        Choice c2n    =   new Choice();
        c2n.mChoice   =     "いいえ";
        c2n.mAnswer   =     13;
        c2n.mStrAnswer=     "13人";
        c2n.mChoiceIndexColor = "#FF0000";
        c2n.mPercentage       = "13%";
        c2s[1]          =   c2n;

        Question q2 = new Question();
        q2.mNumber = "問2";
        q2.mTitle  = "子どもの歌が下手なのは親が音痴だからだ";
        q2.mChoices = c2s;

        Question q3 = new Question();
        q3.mNumber = "問3";
        q3.mTitle  = "子どもの成績が悪いのは親が馬鹿だからだ";
        q3.mChoices = c1s;

        questions[0] = q0;
        questions[1] = q1;
        questions[2] = q2;
        questions[3] = q3;
        return questions;
    }*/




}