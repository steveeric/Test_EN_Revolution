package jp.pmw.test_en_revolution.questionnaire;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.echo.holographlibrary.PieGraph;
import com.echo.holographlibrary.PieGraph.OnSliceClickedListener;
import com.echo.holographlibrary.PieSlice;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import jp.pmw.test_en_revolution.AppController;
import jp.pmw.test_en_revolution.MainActivity;
import jp.pmw.test_en_revolution.MyMainFragment;
import jp.pmw.test_en_revolution.R;
import jp.pmw.test_en_revolution.config.URL;
import jp.pmw.test_en_revolution.one_cushion.select_teacher.Faculty;

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

    private MainActivity activity;

    //アンケート問題や回答結果が格納されている変数
    private List<Question> questions;
    //現在見ている問題番号
    private int nowSeePage=0;
    private int nowSeeTopic=0;

    //
    private ProgressBar questionResultLoadProgressBar;
    //アンケートがまだ開始されていないレイアウト
    private LinearLayout donotStartQuestionLinearLayout;
    //過去に一度でもアンケートをすでに実施しているレイアウト
    private LinearLayout startQuestionLinearlayout;

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
    //
    ChoiceCustomAdapter mChoiceCustomAdapter;


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
        this.questionResultLoadProgressBar = (ProgressBar)this.getActivity().findViewById(R.id.fragment_clicker_result_progressBar);
        this.donotStartQuestionLinearLayout = (LinearLayout)this.getActivity().findViewById(R.id.fragment_clicker_result_donot_start_questionnaire_linearLayout);
        this.startQuestionLinearlayout = (LinearLayout)this.getActivity().findViewById(R.id.fragment_clicker_result_start_questionnaire_linearLayout);

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
        this.resultContentGridView = (GridView)this.getActivity().findViewById(R.id.fragment_clicker_result_answer_contents_gridView);
        this.resultContentGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showQuestionnaireAnswerResult( position );
            }
        });
    }
    /**
     * showQuestionnaireAnswerResultメソッド
     * 誰がそのように回答したかを確認する.
     * @param 	int     position    グリッドビューのインデックス
     * @return 	質問回答結果群
     * @since 	2016/08/08
     **/
    public void showQuestionnaireAnswerResult(int position){
        Question question = questions.get(nowSeeTopic);
        Bundle args = new Bundle();
        args.putInt(QuestionnaireResultDialogFragment.TAP_POSITION, position);
        args.putSerializable(QuestionnaireResultDialogFragment.QUESTION, question);
        QuestionnaireResultDialogFragment qrdf = QuestionnaireResultDialogFragment.newInstance();
        qrdf.setArguments(args);
        qrdf.show(activity.getSupportFragmentManager(), QuestionnaireResultDialogFragment.QUESTIONNNAIRE_RESULT_DIALOG_FRAGMENT);
    }


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        activity = (MainActivity)this.getActivity();
        this.questions = activity.getClassObject().getQuestionnaire().getQuestions();
    }

    @Override
    public void onResume(){
        super.onResume();
        //トピック番号を取得する.
        //MainActivity activity = (MainActivity)this.getActivity();
        //回答結果をネットワークDBに取得に行く
        //getQuestionResultInfoFromNetWrokDB(activity.mTeacher);
        //getQuestionResultFromNetWrokDB(activity.mTeacher);

        //  再度フラグメント表示のバグに対応

        getQuestionResultFromNetWrokDB();
    }
    @Override
    public void onStop(){
        super.onStop();
        //  ボトムフラグメント画面に戻すために行う.
        activity.getClassObject().getQuestionnaire().setLastSeeQuestionTitleNumber(null);
    }


    //  アンケート結果を一問づつ取得する
    private void getQuestionResultFromNetWrokDB(){
       // final MainActivity activity = (MainActivity)this.getActivity();
        String sameClassNumber = activity.getClassObject().getSameClassNumber();
        String url = URL.getQuestionnaireInfo(sameClassNumber);
        JsonObjectRequest request = new JsonObjectRequest(url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        setAnswerResults(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //activity.showAnotherErrorToast(error.getMessage());
                //Toast.makeText(getActivity(), "Unable to fetch data: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        AppController.getInstance(getActivity()).getRequestQueue().add(request);
    }

    void setAnswerResults(JSONObject response){
        Gson gson = new Gson();
        Questionnaire que = gson.fromJson(response.toString(), Questionnaire.class);
        List<Question> qList = que.getQuestions();
        for(int i = 0; i < qList.size(); i++){
            String questionId   = qList.get(i).getQuestionId();
            int yes             = qList.get(i).getResult().getYesCount();
            int no              = qList.get(i).getResult().getNoCount();
            int yesRatio      = qList.get(i).getResult().getYesRatio();
            int noRatio       = qList.get(i).getResult().getNoRatio();
            if(yes != -1 && no != -1){
                setAnswerResult(questionId,yes,no,yesRatio,noRatio);
            }
        }
        processStart();
    }
    //  問題に回答結果を保持させる
    void setAnswerResult(String targetQuestionId,int yesCount,int noCount,int yesRatio,int noRatio){

        this.questions = activity.getClassObject().getQuestionnaire().getQuestions();
        for(int i = 0; i < questions.size(); i++){
            String questionId = questions.get(i).getQuestionId();
            //
            if(questionId.equals(targetQuestionId)){
                questions.get(i).getResult().setNoCount(noCount);
                questions.get(i).getResult().setYesCount(yesCount);
            }
            //  ①はい、②いいえ (棚札のみのため2択)2016-01-27
            List<Ask> askList = questions.get(i).getAsks();
            for(int j = 0; j < askList.size(); j++){
                List<Choice> choiceList = askList.get(j).getChoices();
                for(int k = 0; k < choiceList.size(); k++){
                    if(k == 0){
                        QuestionResult qr = new QuestionResult();
                        //  回答(ACK)数
                        qr.setAnswerCount("" + yesCount);
                        qr.setRatio(yesRatio);
                        choiceList.get(k).setQuestionResult(qr);
                    }else{
                        QuestionResult qr = new QuestionResult();
                        //  回答(NACK)数
                        qr.setAnswerCount("" + noCount);
                        qr.setRatio(noRatio);
                        choiceList.get(k).setQuestionResult(qr);
                    }
                }
            }
        }
    }
    private void processStart(){
        //Questionnaire questionnaire = activity.mTeacher.getQuestionnaire();
        Questionnaire questionnaire = activity.getClassObject().getQuestionnaire();
        if(questionnaire == null){
            super.loadFragment();
        }else{
            //ロード画面を非表示に
            this.questionResultLoadProgressBar.setVisibility(View.GONE);
            if(questionnaire.getLastSeeQuestionTitleNumber() == null){
                //まだ一度もアンケートを実施していない
                this.donotStartQuestionLinearLayout.setVisibility(View.VISIBLE);
                this.startQuestionLinearlayout.setVisibility(View.GONE);
            }else{
                //初期位置に戻す.
                nowSeePage=0;

                String lastSeeQuestionId = questionnaire.getLastSeeQuestionTitleNumber();
            /*for(int i = 0; i < this.questions.size(); i++){
                String questionId = this.questions.get(i).getQuestionId();
                if(lastSeeQuestionId.equals(questionId)){
                    this.nowSeeTopic = i;
                }
            }*/
                for(int i = 0; i < this.questions.size(); i++){
                    String quetionTitleNumber = this.questions.get(i).getQuestionNumberWord();
                    if(lastSeeQuestionId.equals(quetionTitleNumber)){
                        this.nowSeeTopic = i;
                    }
                }

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

                this.donotStartQuestionLinearLayout.setVisibility(View.GONE);
                this.startQuestionLinearlayout.setVisibility(View.VISIBLE);
            }
        }
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

        //再描画ゾーンをきれいにする.
        removeZone();

        //String number = this.questions.get(nowSeeQuestionTopic).getQuestionNumber();
        String number = this.questions.get(nowSeeTopic).getAsks().get(nowSeePage).getAskNumberWord();
        String content = this.questions.get(nowSeeTopic).getAsks().get(nowSeePage).getAskContent();
        List<Choice> choice =  this.questions.get(nowSeeTopic).getAsks().get(nowSeePage).getChoices();
        Result answerReuslt = this.questions.get(nowSeeTopic).getResult();
        //「Q」番号
        this.setQuestionNumber(number);
        //タイトルセット
        this.setQuestionContent(content);

        //int[] pieColorResorce = PieColor.getInstance().pieColors(this.getActivity());

        int yesCount = answerReuslt.getYesCount();
        int noCount  = answerReuslt.getNoCount();

        for(int i = 0; i < choice.size(); i++){
            PieSlice pieSlice = new PieSlice();
            //回答タイトル
            String title = choice.get(i).getChoice();
            //回答パーセント
            //float percent = choice.get(i).getQuestionResult().getRatio();
            //float percent = 10.0f;
            //float percent = 0.0f;
            int count = 0;
            if(i == 0){
                count = yesCount;
            }else{
                count = noCount;
            }


            //int color = pieColorResorce[i];
            if(count > 0){
                String color = choice.get(i).getChoiceIndexColor();
                pieSlice.setColor(Color.parseColor(color));
                pieSlice.setValue(count);
                pieSlice.setTitle(title);
                pieGraph.addSlice(pieSlice);
            }
        }
        //回答の選択内容と選択数を入れる
        //setQuestionAnswerContentResults(choice);
        setQuestionAnswerContentResult(answerReuslt);
    }

    /**
     * Created by scr on 2014/12/31.
     * setQuestionNumber
     * アンケート番号を表示する.
     */
    private void setQuestionNumber(String number){
        this.questionNumberTextView.setText(number);
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
    private void setQuestionAnswerContentResults(List<Choice> answers){
        ChoiceCustomAdapter adapter = new ChoiceCustomAdapter(this.getActivity(),R.layout.row_question_answer_result_item,answers);
        resultContentGridView.setAdapter(adapter);
        resultContentGridView.invalidate();
    }

    /**
     * アンケートの選択肢を強制的に作成
     * **/
    private void setQuestionAnswerContentResult(Result result){
        List<Choice> cList = new ArrayList<Choice>();
        //  「はい」（ACK)
        Choice yesChoice = new Choice();
        QuestionResult yesQr = new QuestionResult();
        yesQr.setRatio(result.getYesRatio());
        yesQr.setAnswerCount("" + result.getYesCount());
        yesChoice.setChoice(result.getYesContent());
        yesChoice.setChoiceColor(result.getYesColor());
        yesChoice.setQuestionResult(yesQr);

        //  「いいえ」（NACK)
        Choice noChoice = new Choice();
        QuestionResult noQr = new QuestionResult();
        noQr.setRatio(result.getNoRatio());
        noQr.setAnswerCount("" + result.getNoCount());
        noChoice.setChoice(result.getNoContent());
        noChoice.setChoiceColor(result.getNoColor());
        noChoice.setQuestionResult(noQr);


        cList.add(yesChoice);
        cList.add(noChoice);

        //
        mChoiceCustomAdapter = new ChoiceCustomAdapter(this.getActivity(), R.layout.row_question_answer_result_item, cList);
        resultContentGridView.setAdapter(mChoiceCustomAdapter);
        resultContentGridView.invalidate();
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
