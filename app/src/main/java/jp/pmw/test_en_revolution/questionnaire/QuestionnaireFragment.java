package jp.pmw.test_en_revolution.questionnaire;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import java.util.List;
import java.util.Timer;
import jp.pmw.test_en_revolution.MainActivity;
import jp.pmw.test_en_revolution.MainFragmentConfig;
import jp.pmw.test_en_revolution.MyMainFragment;
import jp.pmw.test_en_revolution.R;
import jp.pmw.test_en_revolution.TransmitStateObject;
import jp.pmw.test_en_revolution.config.Npd;
import jp.pmw.test_en_revolution.config.TimerConfig;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link QuestionnaireFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link QuestionnaireFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QuestionnaireFragment extends MyMainFragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";
    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static QuestionnaireFragment newInstance(int sectionNumber,String sl) {
        QuestionnaireFragment fragment = new QuestionnaireFragment();
        shelfLabels = sl;
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    private ProgressBar questionnaireProgressBar;

    //アンケートが登録されていない場合のレイアウト
    private LinearLayout noQuestionLayout;
    //アンケートが登録されている場合のレイアウト
    private LinearLayout doQuestionlayout;

    //  アンケートで行うことテキストビュー
    private TextView doQuestionTextView;
    //  登録されているアンケート問題を登録するリストビュー
    private ListView questionListView;
    //  送信後の結果を表示するテキストビュー
    private TextView transmitResultTextView;

    //  最後に送信を行った問題情報取得タイマー
    private Timer lastQuestionBmpTransmitTimer;
    // 最後に送信を行った問題情報取得タイマータスク
    //private LastQuestionBmpTransmitLogTimerTask lastQuestionBmpTransmitTimerTask;
    //  棚札名称
    private static String shelfLabels;

    private LastQuestionBmpTransmitLogObject lastQuestionBmpTransmitLogObject;

    public QuestionnaireFragment() {
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_clicker, container, false);
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.questionnaireProgressBar = (ProgressBar)this.getActivity().findViewById(R.id.clicker_survey_do_question_progressBar);
        this.noQuestionLayout       =       (LinearLayout)this.getActivity().findViewById(R.id.clicker_survey_no_question_linearLayout);
        this.doQuestionlayout       =       (LinearLayout)this.getActivity().findViewById(R.id.clicker_survey_do_question_linearLayout);

        //  アンケート画面で行ってほしいことを表示するテキストビュー
        this.doQuestionTextView     =       (TextView)this.getActivity().findViewById(R.id.clicker_survey_do_question_textView);
        this.questionListView       =       (ListView)this.getActivity().findViewById(R.id.clicker_survey_do_question_listView);
        this.questionListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent,
                                    View view, int pos, long id) {
                // 選択アイテムを取得
                ListView listView = (ListView)parent;
                Question item = (Question)listView.getItemAtPosition(pos);
                checkTapList(pos);
                showQuestionnaireDialogFragment(item);
            }
        });
        //  最終送信結果を表示するテキストビュー
        //  A2の問題を2名の学生が正常に受信できませんでした.etc...
        this.transmitResultTextView =       (TextView)this.getActivity().findViewById(R.id.clicker_survey_do_question_transmit_result_textView);
    }
    private String getStrShelfLabels(){
        return this.shelfLabels;
    }
    /**
     * Created by scr on 2015/1/5.
     * showQuestionnaireDialogFragmentメソッド
     * @param Quesiton question タップされたクリッカー問
     */
    private void showQuestionnaireDialogFragment(Question question){
        MainActivity activity = (MainActivity)this.getActivity();
        String nowTapNumber = question.getQuestionNumber();
        //  クリッカーの送信を行いますか？といったダイアログを出力しないようにするかどうか
        boolean stopFlag = chkCnaBeSelected( nowTapNumber );

        if( stopFlag ){
            //  他のクリッカー問が実行中のため実行できないとダイアログを表示する.
            canNotNextProcess();
        }else{
            //
            QuestionnaireDialogFragment customDialog = QuestionnaireDialogFragment.newInstance();
            customDialog.setTargetFragment(QuestionnaireFragment.this, 0);
            Bundle bundle = new Bundle();
            bundle.putSerializable(QuestionnaireDialogFragment.QUESTION_NAIRE,question);
            customDialog.setArguments(bundle);
            customDialog.setQuestionnaireFragment(this);
            customDialog.show(activity.getSupportFragmentManager(), QuestionnaireDialogFragment.QUESTION_NAIRE_DIALOG_FRAGMENT);
        }
    }
    /**
     * Created by scr on 2016/08/03.
     * chkCanBeSelectedメソッド
     * クリッカー問をタップ時に、反応を(ポップアップを表示)させても良いかを確認します.
     * @param   int     nowTapNumber    タップされた問番号
     * @return  {true}  反応させてもよい    {false} 反応させない.
     */
    boolean chkCnaBeSelected(String nowTapNumber){
        Questionnaire questionnaire = getMainActivity().getClassObject().getQuestionnaire();
        List<Question> questionList = questionnaire.getQuestions();
        boolean stopFlag            =   false;
        //  タップされた問が最後まで終了しているかを確認する.
        for(int i = 0; i < questionList.size(); i++){
            Question q = questionList.get(i);
            if( nowTapNumber.equals( questionList.get(i).getQuestionNumber() )  ){
                String openStartTime    =   q.getQuesiontStartDateTime();
                String resultEndTime    =   q.getQuestionResultEndDateTime();
                if( resultEndTime != null ){
                    return false;
                }
            }
        }


        for(int i = 0; i < questionList.size(); i++){
            Question q = questionList.get(i);
            if( !( nowTapNumber.equals( questionList.get(i).getQuestionNumber() ) ) ){
                String openStartTime    =   q.getQuesiontStartDateTime();
                String resultEndTime    =   q.getQuestionResultEndDateTime();
                if( openStartTime != null && resultEndTime == null){
                    //  他のクリッカー問が途中です...
                    stopFlag        =   true;
                }
            }
        }
        return stopFlag;
    }
    /**
     * canNotNextProcessメソッド
     * 他のクリッカー問題が実行中のため処理を
     * @since  2016/08/09
     **/
    void canNotNextProcess(){
        new AlertDialog.Builder(getActivity())
                .setTitle("クリッカー")
                .setMessage("他の問が途中のため行うことができません.")
                .setPositiveButton("とじる", null)
                .show();
    }
    /**
     * Created by scr on 2016/08/03.
     * getClikcerNumberメソッド
     * クリッカー問番号をNPD_IDから取得します.
     * @param   String  npdId   NPD_ID
     * @return  int     クリッカー問番号
     */
    int getClikcerNumber(String npdId){
        int length          =   npdId.length();
        String num          =   npdId.substring( (length -4), (length -2) );
        int number          = Integer.valueOf( num );
        return number;
    }
    /**
     * Created by scr on 2016/08/03.
     * getClikcerNpdContentメソッド
     * クリッカー送信コンテンツ番号をNPD_IDから取得します.
     * @param   String  npdId   NPD_ID
     * @return  String  クリッカー送信コンテンツ番号
     */
    String getClikcerNpdContent(String npdId){
        int length          =   npdId.length();
        return npdId.substring( (length -2) );
    }


    /**
     * Created by scr on 2014/12/31.
     * checkTapListメソッド
     * ListViewのどの問題がタップされたかをMainActivityの変数に持たせる.
     */
    private void checkTapList(int tapPosition){
        MainActivity activity = (MainActivity)this.getActivity();
        activity.mNowSeeQuestionTopic = tapPosition;
    }
    @Override
    public void onResume(){
        super.onResume();
        MainActivity activity = (MainActivity)this.getActivity();
        Questionnaire q = activity.getClassObject().getQuestionnaire();
        if(q == null){
            //リロードフラグメント
            super.loadFragment();
        }else{
            List<Question> questions = q.getQuestions();
            if(questions.size() == 0){
                //アンケートがない.
                showNoQuestionLayout();
            }else{
                //アンケートがある.
                if(lastQuestionBmpTransmitTimer == null){
                    //  最後に送信を行った際の情報
                    //  アンケートで最後に送信を行った情報を取得する.
                    lastQuestionBmpTransmitTimer = new Timer();          //  タイマークラスのインスタンス
                    lastQuestionBmpTransmitTimer.schedule(new LastQuestionBmpTransmitLogTimerTask(this),
                            TimerConfig.TIMER_START_LAST_QUESTION_BMP_TRANSMIT_LOG,
                            TimerConfig.TIMER_INTERVAL_LAST_QUESTION_BMP_TRANSMIT_LOG);     //  起動時0秒後から3秒間隔で起動
                }
                showQuestionLayout();
                setDummyTestData(questions);
            }
        }
    }

    public void onPause(){
        super.onPause();
        if(lastQuestionBmpTransmitTimer != null){
            lastQuestionBmpTransmitTimer.cancel();
            lastQuestionBmpTransmitTimer = null;
        }
    }

    /**
     * Created by scr on 2015/3/12.
     * goneProgressBarメソッド
     * アンケートロードプログレスバーを非表示にする.
     */
    private void goneProgressBar(){
        this.questionnaireProgressBar.setVisibility(View.GONE);
    }

    /**
     * Created by scr on 2014/12/25.
     * showNoQuestionLayoutメソッド
     * アンケートが登録されていない場合に表示するレイアウト.
     */
    private void showNoQuestionLayout(){
        goneProgressBar();
        this.doQuestionlayout.setVisibility(View.GONE);
        this.noQuestionLayout.setVisibility(View.VISIBLE);
    }
    /**
     * Created by scr on 2014/12/25.
     * showNoQuestionLayoutメソッド
     * アンケートが登録されている場合に表示するレイアウト.
     */
    private void showQuestionLayout(){
        goneProgressBar();
        this.doQuestionlayout.setVisibility(View.VISIBLE);
        this.noQuestionLayout.setVisibility(View.GONE);
    }
    /**
     * Created by scr on 2014/12/25.
     * dummyTestStartメソッド
     * ダミーデータでアンケート調査画面を開始する.
     */
    private void dummyTestStart() {
//質問アイテムを取得
        //List<Question> questions = DummyQuestionContent.ITEMS;
        MainActivity activity = (MainActivity)this.getActivity();
        List<Question> questions = activity.mTeacher.getQuestionnaire().getQuestions();
        if(questions.size() == 0){
//アンケートがない.
            showNoQuestionLayout();
        }else{
//アンケートがある.
            showQuestionLayout();
            setDummyTestData(questions);
        }
    }

    private void setDummyTestData(List<Question> questions){
        QuestionnaireCustomAdapter adapter = new QuestionnaireCustomAdapter(this.getActivity(),R.layout.row_question_item,questions);
        this.questionListView.setAdapter(adapter);
    }


    /**
     * Created by scr on 2016/02/06.
     * setReDrowLastQuestionTransmitResultStateメソッド
     * 最後に送信したアンケートに関する送信状態を画面上の文字にセットする.
     */
    public void setReDrowLastQuestionTransmitResultState(LastQuestionBmpTransmitLogObject object){
        //  問題文最後の送信状態をセット
        this.lastQuestionBmpTransmitLogObject = object;

        if(object == null){
            setNextActionTextView(getStrText());
            return;
        }

        if(object.getLastQuestionNpdId() == null){
            //setNextActionTextView(object.getLastNpdWording()+"しています.");
            return;
        }
        //  最終NPD_ID
        String lastNpdId        = this.lastQuestionBmpTransmitLogObject.getLastQuestionNpdId();

        String tilteContent     =   "";
        String resultContent    =   "";
        String start            =   object.getLastBmpTransmitStartTime();
        String end              =   object.getLastBmpTransmitEndTime();
        //  送信コンテンツ名
        String lastNpdWording   =   object.getLastNpdWording();

        //  NACK数
        int nackCount   =   object.getNackAttendanceIds().length;

        if(end != null){
            MainActivity activity = (MainActivity)this.getActivity();
            if(activity == null){
                return;
            }
            if(activity.getClassObject() == null){
                return;
            }
            String bmpTransmitId = activity.getClassObject().getTransmitStateObject().getBmpTransmitId();
            if(bmpTransmitId != null){
                //  おおもとで管理している送信状態オブジェクトと同期をとるために
                //  あります.
                return;
            }
            tilteContent       =    lastNpdWording + "しました.";
            //  クリッカー送信コンテンツ
            String content = getClikcerNpdContent(lastNpdId);
            if( content.equals( TransmitStateObject.CLIKCER_TRANSMIT_TEXT ) ){
                resultContent      =    nackCount + "名の学生については、問題文を受信できません.";
            }else if( content.equals( TransmitStateObject.CLIKCER_TRANMIST_ANSWER ) ){
                resultContent      =    nackCount + "名の学生については、回答を収集できません.";
            }else if( content.equals( TransmitStateObject.CLIKCER_TRANSMIT_RESULT ) ){
                resultContent      =    nackCount + "名の学生については、回答を返信できません.";
            }
            //  出席者全員からACKありの場合.
            if(nackCount == 0){
                if( content.equals( TransmitStateObject.CLIKCER_TRANSMIT_TEXT ) ){
                    resultContent      =   "全員が問題文を受信しました.";
                }else if( content.equals( TransmitStateObject.CLIKCER_TRANMIST_ANSWER ) ){
                    resultContent      =   "全員から回答を収集しました.";
                }else if( content.equals( TransmitStateObject.CLIKCER_TRANSMIT_RESULT ) ){
                    resultContent      =    "全員に回答を返信しました.";
                }
            }
            //  次の動作内容
            doNextAction(object);

        }else{
            tilteContent       =   lastNpdWording+"しています.";
        }
        // ヘッダー
        final String    hedder  =   tilteContent;
        //
        final String    result  =   resultContent;

            Handler mHandler = new Handler(Looper.getMainLooper());
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    doQuestionTextView.setText(hedder);
                    transmitResultTextView.setText(result);
                }
            });
    }


    /**
     * Created by scr on 2016/02/07.
     * doNextActionメソッド
     * 次の動作が何かを判別します.
     * @param object アンケート送信状態オブジェクト
     */
    private void doNextAction(LastQuestionBmpTransmitLogObject object){
        //  最終NPD_ID
        String  lastNpdId   = object.getLastQuestionNpdId();
        //
        String str = "";
        //  クリッカー問番号
        int     number          = getClikcerNumber( lastNpdId );
        //  クリッカー送信コンテンツ番号
        String  contentNumber   = getClikcerNpdContent( lastNpdId );
        //  文字列を取得する.
        if( contentNumber.equals(Npd.NPD_CONTENT_Q_TEXT) ){
            str = getStrQtext( number );
        }else if( contentNumber.equals(Npd.NPD_CONTENT_ANSWER_TEXT) ){
            str = getStrAnswerText( number );
        }else if( contentNumber.equals(Npd.NPD_CONTENT_RESULT_TEXT) ){
            //  次の問の案内のために+1をしています.
            str = getStrResult();
        }
        //  テキストにセットする.
        setNextActionTextView(str);
    }
    /**
     * getStrTextメソッド
     * クリッカー問の送信をおこなってくださいメッセージ
     * @since
     **/
    String getStrText(){
        String strESL   =   getStrShelfLabels();
        return"① 問題を送信します.\r\n" +
                "② "+strESL+"を表向けて机の上に出すよう指示してください.\r\n" +
                "③ 学生が②を終えた後、問をタップしてください.";
    }
    /**
     * getStrQtextメソッド
     * 回答状況を取得する送信を行ってくださいメッセージ
     * @since
     **/
    String getStrQtext(int number){
        String strESL   =   getStrShelfLabels();
        return"① 回答を収集します.\r\n" +
                "② 「いいえ」の学生は"+strESL+"の受信部を隠すよう指示してください.\r\n" +
                "③ 学生が②を終えた後、問"+number+"をタップしてください.";
    }
    /**
     * getStrAnswerTextメソッド
     * 回答結果を反映する送信を行ってくださいメッセージ
     * @since
     **/
    String getStrAnswerText(int number){
        String strESL   =   getStrShelfLabels();
        return "① 回答を学生の"+strESL+"に返信します.\r\n" +
                "② "+strESL+"の受信部を隠していた学生は受信部を表向きにするよう指示をしてください.\r\n" +
                "③ 学生が②を終えた後、問"+number+"をタップしください.";
    }
    /**
     * getStrResultメソッド
     * 回答結果を反映する送信後のメッセージ
     * @since
     **/
    String getStrResult(){
        MainActivity activity = (MainActivity)this.getActivity();
        List<Question> questionList = activity.getClassObject().getQuestionnaire().getQuestions();
        String str = "";
        if(getEndClikcerCount() == questionList.size()){
            str = "問がすべて終了しました.";
        }else{
            str = getStrText();
        }
        return str;
    }
    /**
     * getEndClikcerCountメソッド
     * クリッカー問題が終了した数
     * ①    問題送信開始がnullでない
     * ②    回答結果送信がnullでない
     * を満たすものが終了した数になります.
     * @since  2016/08/08
     **/
    int getEndClikcerCount(){
        int endCount = 0;
        MainActivity activity = (MainActivity)this.getActivity();
        List<Question> questionList = activity.getClassObject().getQuestionnaire().getQuestions();
        for( int i = 0; i < questionList.size(); i++ ){
            Question question       =   questionList.get(i);
            String startDateTime    =   question.getQuesiontStartDateTime();
            String endDateTime      =   question.getQuestionResultEndDateTime();
            if( startDateTime != null && endDateTime != null ){
                ++endCount;
            }
        }
        return endCount;
    }
    /**
     * Created by scr on 2016/02/07.
     * setNextActionTextViewメソッド
     * 次の動作をテキストビューにセットする.
     * @param nextActionMessage 次の動作メッセージ
     */
    public void setNextActionTextView(final String nextActionMessage){
        Handler mHandler = new Handler(Looper.getMainLooper());
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if(getActivity() != null){
                    //  次の動作表示
                    TextView nextTextView = (TextView) getActivity().findViewById(R.id.clicker_survey_do_question_transmit_next_action_textView);
                    nextTextView.setText(nextActionMessage);
                }
            }
        });
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