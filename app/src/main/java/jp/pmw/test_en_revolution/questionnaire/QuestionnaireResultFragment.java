package jp.pmw.test_en_revolution.questionnaire;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import com.echo.holographlibrary.PieGraph;
import com.echo.holographlibrary.PieGraph.OnSliceClickedListener;
import com.echo.holographlibrary.PieSlice;
import java.util.List;
import jp.pmw.test_en_revolution.MainActivity;
import jp.pmw.test_en_revolution.MyMainFragment;
import jp.pmw.test_en_revolution.R;
import jp.pmw.test_en_revolution.questionnaire.dummy.DummyQuestionContent;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link QuestionnaireResultFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link QuestionnaireResultFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QuestionnaireResultFragment extends MyMainFragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static QuestionnaireResultFragment newInstance(int sectionNumber) {
        QuestionnaireResultFragment fragment = new QuestionnaireResultFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    //アンケート問題や回答結果が格納されている変数
    private List<Question> questions;
    //現在見ている問題番号
    private int nowSeePage=0;
    private int nowSeeTopic=0;

    //問題のページ送り
    private Button returnQuestionButton,nextQuestionButton;
    //問題番号テキストビュー
    private TextView questionNumberTextView;

    //問題タイトル
    private TextView titleTextView;
    //円グラフ
    private PieGraph pieGraph;
    //
    private GridView resultContentGridView;

    public QuestionnaireResultFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_clicker_result, container, false);
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.returnQuestionButton = (Button)this.getActivity().findViewById(R.id.fragment_clicker_result_return_question_button);
        this.returnQuestionButton.setOnClickListener(new View.OnClickListener() {
            @Override
              public void onClick(View v) {
                /*戻るボタンタップ*/
                returnQuestion();
            }
        });
        this.nextQuestionButton = (Button)this.getActivity().findViewById(R.id.fragment_clicker_result_next_question_button);
        this.nextQuestionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*進むボタンタップ*/
                nextQuestion();
            }
        });
        this.questionNumberTextView = (TextView)this.getActivity().findViewById(R.id.fragment_clicker_result_question_number_textView);
        this.titleTextView = (TextView)this.getActivity().findViewById(R.id.fragment_clicker_result_title_textView);
        this.pieGraph = (PieGraph)this.getActivity().findViewById(R.id.holo_graph_view);
            this.pieGraph.setOnSliceClickedListener(new OnSliceClickedListener() {
                @Override
                public void onClick(int index) {
                   //円グラフをタップした際の処理
                }
            });
        this.resultContentGridView = (GridView)this.getActivity().findViewById(R.id.fragment_clicker_result_answer_contents_gridView);
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        //テストなのでダミーデータを入れる.
        this.questions = DummyQuestionContent.ITEMS;
        //初期位置に戻す.
        nowSeePage=0;
    }

    @Override
    public void onResume(){
        super.onResume();

        //トピック番号を取得する.
        MainActivity activity = (MainActivity)this.getActivity();
        nowSeeTopic = activity.mNowSeeQuestionTopic;

        //ボタン表示状態
        nextQuestionButtonShowState();

        //問題が計難問あるかをチェックする.
        //その問題数におおじて戻るボタンと進むボタンをどう表示するかを決める.

        //ダミーデータた
        setTestItems();

        //ドーナツのように真ん中を空ける
        pieGraph.setInnerCircleRatio(80);
        //間にパディングをいれる
        pieGraph.setPadding(1);
    }

    /**
     * Created by scr on 2014/12/31.
     * removeZoneメソッド
     * .再描画
     */
    private void removeZone(){
        removePieGraph();
        //removeGridView();
    }


    /**
     * Created by scr on 2014/12/31.
     * removePieGraphメソッド
     * 円グラフを一度きれいにする.
     */
    private void removePieGraph(){
        //円グラフのインスタンスを生成
        if(this.pieGraph != null){
            this.pieGraph.removeSlices();
        }
    }
    /**
     * Created by scr on 2014/12/31.
     * removeGridViewメソッド
     * 円グラフの詳細情報保持ゾーンを掃除する..
     */
    private void removeGridView(){
        //円グラフのインスタンスを生成
        if(this.resultContentGridView != null){
            this.resultContentGridView.removeAllViews();
        }
    }
    /**
     * Created by scr on 2014/12/31.
     * nextQuestionメソッド
     * 次の問題へ.
     */
    private void nextQuestion(){
        //次の問題へインクリメント
        this.nowSeePage++;

        //ボタン状態変更
        nextQuestionButtonShowState();
    }
    /**
     * Created by scr on 2014/12/31.
     * returnQuestionメソッド
     * 問題を戻る.
     */
    private void returnQuestion(){
        //問題をデクリメント
        this.nowSeePage--;
        //ボタン状態変更
        nextQuestionButtonShowState();
    }


    /**
     * Created by scr on 2014/12/31.
     * nextQuestionButtonShowStateメソッド
     * 問題送りボタンをひょうじするかを決める.
     */
    private void nextQuestionButtonShowState(){

        boolean changeFlag = false;
        boolean nextFlag = checkNextNumber();
        boolean returnFlag = checkReturnNumber();

        //▶ボタンを表示
        if(nextFlag == true){
            //
            changeFlag = true;
            this.nextQuestionButton.setVisibility(View.VISIBLE);
        }else{
            this.nextQuestionButton.setVisibility(View.INVISIBLE);
        }

        //◀ボタンを表示
        if(returnFlag == true){
            changeFlag = true;
            this.returnQuestionButton.setVisibility(View.VISIBLE);
        }else{
            this.returnQuestionButton.setVisibility(View.INVISIBLE);
        }

        if(changeFlag == true){
            setTestItems();
            if(this.resultContentGridView != null){
                resultContentGridView.invalidate();
            }
        }
    }


    /**
     * Created by scr on 2014/12/31.
     * checkNextNumberメソッド
     * 次の問題があるかを確認する.
     */
    private boolean checkNextNumber(){
       boolean checkFlag = false;

        //次の架空問題番号
        int nextNumber = this.nowSeePage + 1;
        //アンケート問題数
        int totalQuestionCount = this.questions.get(nowSeeTopic).getAsks().size();
        if(nextNumber < totalQuestionCount){
            checkFlag = true;
        }

       return checkFlag;
    }

    /**
     * Created by scr on 2014/12/31.
     * checkNextNumberメソッド
     * 次の問題があるかを確認する.
     */
    private boolean checkReturnNumber(){
        boolean checkFlag = false;

        //次の架空問題番号
        int returnNumber = this.nowSeePage - 1;
        if(returnNumber >= 0){
            checkFlag = true;
        }

        return checkFlag;
    }
    /**
     * Created by scr on 2014/12/30.
     * setTestItemsメソッド
     * テスト用のダミーデータをセットする.
     */
    private void setTestItems(){
        //String number = DummyQuestionContent.ITEMS.get(0).getQuestionNumber();
        //String content = DummyQuestionContent.ITEMS.get(0).getAsks().get(0).getAskContent();
        //List<Answer> answers =  DummyQuestionContent.ITEMS.get(0).getAsks().get(0).getAnswer();

        //再描画ゾーンをきれいにする.
        removeZone();

        //String number = this.questions.get(nowSeeQuestionTopic).getQuestionNumber();
        String number = this.questions.get(nowSeeTopic).getAsks().get(nowSeePage).getAskNumber();
        String content = this.questions.get(nowSeeTopic).getAsks().get(nowSeePage).getAskContent();
        List<Answer> answers =  this.questions.get(nowSeeTopic).getAsks().get(nowSeePage).getAnswer();

        //「Q」番号
        this.setQuestionNumber(number);
        //タイトルセット
        this.setQuestionContent(content);

        //int[] pieColorResorce = PieColor.getInstance().pieColors(this.getActivity());

        for(int i = 0; i < answers.size(); i++){
            PieSlice pieSlice = new PieSlice();
            //回答タイトル
            String title = answers.get(i).getAnswerContent();
            //回答パーセント
            float percent = answers.get(i).getAnswerCount();
            //int color = pieColorResorce[i];
            String color = answers.get(i).getanswerIndexColor();

            pieSlice.setColor(Color.parseColor(color));
            pieSlice.setValue(percent);
            pieSlice.setTitle(title);
            pieGraph.addSlice(pieSlice);
        }

        //回答の選択内容と選択数を入れる
        setQuestionAnswerContentResult(answers);
    }

    /**
     * Created by scr on 2014/12/31.
     * setQuestionNumber
     * アンケート番号を表示する.
     */
    private void setQuestionNumber(String number){
        String baseIndex = this.getString(R.string.questionnaire_number);
        this.questionNumberTextView.setText(baseIndex + number);
    }

    /**
     * Created by scr on 2014/12/30.
     * setQuestionTitleメソッド
     * アンケートで聞いた問題をセットする.
     */
    private void setQuestionContent(String content){
        this.titleTextView.setText(content);
    }

    /**
     * Created by scr on 2014/12/31.
     * setQuestionAnswerContentResultメソッド
     * 選択肢や選択肢にたいする回答者数をセットする.
     */
    private void setQuestionAnswerContentResult(List<Answer> answers){
        AnswerCustomAdapter adapter = new AnswerCustomAdapter(this.getActivity(),R.layout.row_question_answer_result_item,answers);
        this.resultContentGridView.setAdapter(adapter);
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(getArguments().getInt(ARG_SECTION_NUMBER));
    }
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }
}
