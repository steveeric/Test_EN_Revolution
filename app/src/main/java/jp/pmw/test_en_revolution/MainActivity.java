package jp.pmw.test_en_revolution;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import java.io.Serializable;
//import com.android.volley.Response;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import org.json.JSONObject;
import java.util.Timer;
import jp.pmw.test_en_revolution.common.CommonDialogFragment;
import jp.pmw.test_en_revolution.config.TimerConfig;
import jp.pmw.test_en_revolution.config.TransmitTypeConfig;
import jp.pmw.test_en_revolution.config.URL;
import jp.pmw.test_en_revolution.confirm_class_plan.ConfirmClassPlanActivity;
import jp.pmw.test_en_revolution.confirm_class_plan.Roster;
import jp.pmw.test_en_revolution.confirm_class_plan.Student;
import jp.pmw.test_en_revolution.confirm_class_plan.TmpClassInfo;
import jp.pmw.test_en_revolution.drawer.NavigationDrawerFragment;
import jp.pmw.test_en_revolution.grouping.GroupingFragment_1;
import jp.pmw.test_en_revolution.help.HelpSignalDialogFragnemt;
import jp.pmw.test_en_revolution.history.SearchHistoryDialogFragment;
import jp.pmw.test_en_revolution.one_cushion.select_teacher.Faculty;
import jp.pmw.test_en_revolution.questionnaire.Questionnaire;
import jp.pmw.test_en_revolution.questionnaire.QuestionnaireDialogFragment;
import jp.pmw.test_en_revolution.questionnaire.QuestionnaireFragment;
import jp.pmw.test_en_revolution.questionnaire.QuestionnaireResultFragment;
import jp.pmw.test_en_revolution.survey.SurveyFragment;
import okhttp3.OkHttpClient;

public class MainActivity extends MyFragmentActivity {

    private static final long END_CLASS_TIMER_WAKE_UP_INTERVAL = 60000;
    private CharSequence mTitle;
    /*教員情報を保持する(授業情報も保持しています.)*/
    public Faculty mTeacher;
    /*ナビゲーションドロワーのリスト項目*/
    private ListView mNavigationDrawerList;
    /*ナビゲーションドロワーの50音順に戻るボタン*/
    private Button mNavigationDrawerButton;
    //アンケート画面で現在見ている問題トピック番号
    public int mNowSeeQuestionTopic=0;
    //ドロワーフラグメントListViewのどの項目を選択しているかを保持する.
    public int mNavigationDrawerItemSelected = 0;

    //  授業画面コントローラ
    private ClassAcitvityContoller caController;
    public ClassAcitvityContoller getClassActivityController(){return this.caController;}

    //  授業オブジェクト
    private ClassObject classObject;
    public ClassObject getClassObject(){return this.classObject;}

    //  送信状態管理タイマー
    private TransmitStateTimerTask transmitStateTimerTask;
    public TransmitStateTimerTask getTransmitStateTimerTask(){return this.transmitStateTimerTask;}

    //  授業オブジェクトデータ取得Http管理クラス
    private ClassHttpRequest classHttpRequest;
    public ClassHttpRequest getClassHttpRequest(){return this.classHttpRequest;}

    private OkHttpClient client = new OkHttpClient();
    public OkHttpClient getOkHttpClient(){return this.client;}

    //  出席関係取得中フラグ(false:取得中でない, true:取得中)
    public boolean chkAttendanceRelationshipInfoRetrieving = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        createNavigationDrawer();
    }
    /**
     * Created by scr on 2016/1/29.
     * initメソッド
     * 初期起動群
     */
    private void init(){
        //  授業オブジェクト生成
        this.classObject            = new ClassObject();

        if(this.getIntent()!=null){
            Intent intent = this.getIntent();
            Faculty data = (Faculty)intent.getSerializableExtra(ConfirmClassPlanActivity.TEACHER);
            //mTeacher = data;
            String sameClassNumber = data.getClassPlan().getSameClassNumber();
            this.classObject.setFacultyObject(data);
            this.classObject.setSameClassNumber(sameClassNumber);
        }
        //
        //  TODO:全画面からintentされた教員データを格納すること!!
        //  授業画面コントローラ
        this.caController           = new ClassAcitvityContoller(this);
        //
        this.classHttpRequest       = new ClassHttpRequest(this);

    }
    /**
     * Created by scr on 2016/1/29.
     * showErrorToast
     * エラートーストを表示します.
     * @param errorMsg エラーメッセージ
     */
    public void showErrorToast(String errorMsg){
        Toast.makeText(this, "ERROR:"+errorMsg, Toast.LENGTH_SHORT).show();
    }
    public void showToast(){
        Toast.makeText(this, "showToast", Toast.LENGTH_SHORT).show();
    }

    /**
     * Created by scr on 2016/2/1.
     * showAnotherErrorToast
     * 別スレッドからのトースト
     * @param errorMsg エラーメッセージ
     */
    public void showAnotherErrorToast(final String errorMsg){
        /*final Handler handler = new Handler();
        new Thread(new Runnable(){
            @Override
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        Toast.makeText(MainActivity.this, "ERROR:"+errorMsg, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }).start();*/
        Handler mHandler = new Handler(Looper.getMainLooper());
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity.this, "ERROR:"+errorMsg, Toast.LENGTH_SHORT).show();
            }
        });
    }

    //授業終了タイマー
    public Timer endClassRoomTimer;

    /**
     * Created by scr on 2015/1/14.
     * endClassRoomメソッド
     * アプリ画面を自動で落とします.
     */
    public void endClassRoom(){
        if(endClassRoomTimer != null){
            endClassRoomTimer.cancel();
        }
        //アプリ画面自動で落とす
        finish();
    }
    public void onResume(){
        super.onResume();
        //  onResume時に初期化
        initOnResume();
        //  起動時
        this.caController.startUp();
        String roomId       =   this.getClassObject().getFacultyObject().getClassPlan().getPlace().getRoomId();
        //  教室情報取得
        this.classHttpRequest.getRoomFloaMapInfo(roomId);
        //  クリッカー取得
        this.classHttpRequest.getQuestionnaire();
        //  アンケート取得
        this.classHttpRequest.getSurvey();
    }
    /**
     * Created by si on 2016/01/31.
     * createNavigationDrawerメソッド
     * ナビゲーションドロワーを作成する.
     * **/
    public void createNavigationDrawer(){
        if(mNavigationDrawerFragment == null){
            mNavigationDrawerFragment = (NavigationDrawerFragment)
                    getFragmentManager().findFragmentById(R.id.navigation_drawer);

            //タイトルバー初期化
            mTitle = "";

            // Set up the drawer.
            mNavigationDrawerFragment.setUp(
                    R.id.navigation_drawer,
                    (DrawerLayout) findViewById(R.id.drawer_layout));
        }
    }
    /**
     * Created by si on 2016/01/29.
     * setClassReamingTimeメソッド
     * 授業残り時間をセットします.
     *  @param crTime       授業残り時間オブジェクト
     * **/
    private void setClassReamingTime(ClassReamingTime crTime){
        this.getClassObject().setClassTimeReaming(crTime.reamingTime);
        //  授業残り時間[msec]
        long classReamingTime = this.getClassObject().getClassTimeReaming();

        if(this.endClassRoomTimer == null){
            //授業終了タイマー
            this.endClassRoomTimer = new Timer();
        }else{
            this.endClassRoomTimer.cancel();
            this.endClassRoomTimer = null;
            this.endClassRoomTimer = new Timer();
        }
        //授業終了タイマー
        this.endClassRoomTimer.schedule(new FinishTimer(this), classReamingTime);
        if(classReamingTime > 0){
            //  まだ授業中であれば
            //  授業データを取得する.
            this.getClassActivityController().getClassData();
        }
    }
    /**
     * Created by si on 2016/01/29.
     * doChkAttendanceメソッド
     * 授業参加学生群を授業オブジェクトにセットします.
     *  @param students       学生群
     * **/
    public void doChkAttendance(StudentObject[] sos){
        this.getClassObject().setStudentObject(sos);
    }
    /*
    * 学生情報と出欠データ取得で使用クラス
    * */
    class RegistrationObject{
        //
        @SerializedName("students")
        public StudentObject[] sos;
    }

    /**
     * Created by scr on 2016/01/29.
     * initOnResumeメソッド
     * onResume時に授業オブジェクトの初期化を行う.
     */
    public void initOnResume(){
        //  送信状態確認オブジェクト
        chkTrxTranmitState();

        //  送信状態オブジェクト初期化
        //this.getClassObject().setTransmitStateObject(null);
        getFragmentManager().invalidateOptionsMenu();
        //getMenuInflater().inflate(R.menu.main, menu);
        //  強制的に戻す
        mNavigationDrawerItemSelected = 0;
        ListView lv = mNavigationDrawerFragment.getDrawerListView();
        mNavigationDrawerFragment.changeTap(lv, mNavigationDrawerItemSelected);
        mNavigationDrawerFragment.selectItem(mNavigationDrawerItemSelected);
        showBottomFragment();
    }

    /**
     * Created by scr on 2016/01/31.
     * showBottomFragmentメソッド
     * ナビゲーションドロワーを使ってね!
     * フラグメントを表示する
     */
    public void showBottomFragment(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, MainBottomFragment.newInstance(0))
                .commit();
    }

    /**
     * Created by scr on 2016/1/28.
     * chkTrxTranmitState
     * 赤外線の送信状態をDBへ確認します.
     */
    public void chkTrxTranmitState(){
        if(this.transmitStateTimerTask == null){
            //  送信状態タイマークラス
            this.transmitStateTimerTask = new TransmitStateTimerTask(this);
            java.util.Timer timer = new java.util.Timer();          //  タイマークラスのインスタンス
            timer.schedule(transmitStateTimerTask,
                    TimerConfig.TIMER_START_TRANSMIT_STATE,
                    TimerConfig.TIMER_INTERVAL_TRANSMIT_STATE);     //  起動時0秒後から10秒間隔で起動
        }
    }

    /**
     * Created by scr on 2015/2/24.
     * jsonParserClassInfoメソッド
     * 取得した授業情報のJSONを解析する.
     */
    private TmpClassInfo jsonParserClassInfo(JSONObject response){
        Gson gson = new Gson();
       return gson.fromJson(response.toString(), TmpClassInfo.class);
    }
    /**
     * Created by scr on 2015/2/24.
     * setTmpClassInfoメソッド
     * 取得した授業情報のメモリに保持する.
     */
    public boolean reDrawAttendFlag = false;
    private void setTmpClassInfo(TmpClassInfo tmp){
        mTeacher.setLastGroupingTime(tmp.lastGroupingTime);

        reDrawAttendFlag = false;
        if(mTeacher.getRoster() != null){
           //  受講者一覧再描画が必要
            if(this.mNavigationDrawerItemSelected == MainFragmentConfig.PARTICIPANTS_FRAGNEMT){
                reDrawAttendFlag = true;
            }
        }
        Roster r = new Roster(tmp.rosterList);
        mTeacher.setRoster(r);
        mTeacher.setTmpRoomInfo(tmp.tmpRoomInfo);

    }

    /**
     * Created by scr on 2015/3/4.
     * jsonParserQuestionnaireInfoメソッド
     * 取得したアンケート情報のJSONを解析する.
     */
    private Questionnaire jsonParserQuestionnaireInfo(JSONObject response){
        Gson gson = new Gson();
        Questionnaire que = gson.fromJson(response.toString(), Questionnaire.class);
        return que;
    }
    /**
     * Created by scr on 2015/3/4.
     * setQuestionnaireメソッド
     * アンケート情報をセットする.
     */
    private void setQuestionnaire(JSONObject response){
        Questionnaire questionnaire = jsonParserQuestionnaireInfo(response);
        this.mTeacher.setQuestionnaire(questionnaire);
    }

    public void onStop(){
        super.onStop();
        //  送信状態タイマータスク
        if(this.transmitStateTimerTask != null){
            //  送信状態タイマータスクキャンセル
            this.transmitStateTimerTask.cancel();
            this.transmitStateTimerTask = null;

        }
    }
    public void onDestroy(){
        super.onDestroy();
    }

    /**
     * Created by scr on 2014/12/11.
     * openNavigationDrawerメソッド
     * ドロワーフラグメントの必要個所をオープンにする.
     */
    public void openNavigationDrawer(){
        if(this.mNavigationDrawerList==null){
            this.mNavigationDrawerList = (ListView)this.findViewById(R.id.navigation_drawer_list);
            this.mNavigationDrawerList.setVisibility(View.VISIBLE);
        }
        if(this.mNavigationDrawerButton==null){
            this.mNavigationDrawerButton = (Button)this.findViewById(R.id.fragment_navigation_drawer_return_top_button);
           this.mNavigationDrawerButton.setVisibility(View.VISIBLE);
        }
    }
    /**
     * Created by scr on 2015/1/6.
     * moveToSeatSituationFragmentメソッド
     * 着席状況確認フラグメントに移動する.
     */
    public void moveToSeatSituationFragment(){
        int position = MainFragmentConfig.SEAT_SITUATION_FRAGMENT;
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, SeatSituationFragment.newInstance(position))
                .commit();
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        mNavigationDrawerItemSelected = position;
        if(position == MainFragmentConfig.BOTTOM_FRAGMENT){
            fragmentManager.beginTransaction()
                    .replace(R.id.container, MainBottomFragment.newInstance(position))
                    .commit();
        }else if(position == MainFragmentConfig.SEAT_SITUATION_FRAGMENT){
            /*着席状況一覧*/
            fragmentManager.beginTransaction()
                    .replace(R.id.container, SeatSituationFragment.newInstance(position))
                    .commit();
        }else if(position == MainFragmentConfig.PARTICIPANTS_FRAGNEMT){
            /*出席者一覧*/
            fragmentManager.beginTransaction()
                    .replace(R.id.container, AttendeeFragment.newInstance(position))
                    .commit();
        }/*else if(position == MainFragmentConfig.GROUPING_FRAGMENT){
            //  グルーピング
            //fragmentManager.beginTransaction()
            //        .replace(R.id.container, GroupingFragment.newInstance(position))
            //        .commit();
            fragmentManager.beginTransaction()
                    .replace(R.id.container, GroupingFragment_1.newInstance(position))
                    .commit();
        }*/else if(position == MainFragmentConfig.QUESTIONNAIRE_FRAGMENT){
            /*クリッカー*/
            /*2014年12月15日に,
            複数あるので関数化しました.*/
            doClickerDistributeFragment(position,1);
        }else if(position == MainFragmentConfig.GROUP_READJUSTMENT){
            //  グループ調整画面
            fragmentManager.beginTransaction()
                    .replace(R.id.container, GroupReAdjustmentFragment.newInstance(position))
                    .commit();
        }else if( position == MainFragmentConfig.SURVEY_FRAGMENT ){
            //  アンケート画面
            fragmentManager.beginTransaction()
                    .replace(R.id.container, SurveyFragment.newInstance(position))
                    .commit();
        }else{
            fragmentManager.beginTransaction()
                    .replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
                    .commit();
        }
    }

    /**
     * Created by Shota Ito on 2014/12/15.
     * doClickerFragmentメソッド.
     * クリッカーに関するフラグメント生成を振り分ける関数
     * @param potision ドロワーフラグメントでタップされたポジション
     * @param groundPosition アクションバー上のメニューに配置されたメニューボタン番号
     */
    public void doClickerDistributeFragment(int position,int groundPosition){
        FragmentManager fragmentManager = getSupportFragmentManager();
        if(position == MainFragmentConfig.QUESTIONNAIRE_FRAGMENT && groundPosition == 1) {
            //クリッカー調査フラグメント生成
            //FragmentManager fragmentManager = getFragmentManager();
            String shelfLabels = getResources().getString(R.string.shelf_labels);
            fragmentManager.beginTransaction()
                    .replace(R.id.container, QuestionnaireFragment.newInstance(position,shelfLabels))
                    .commit();
        }else if(position == MainFragmentConfig.QUESTIONNAIRE_FRAGMENT && groundPosition == 2) {
            //クリッカーの回答結果を表示するフラグメント生成
            fragmentManager.beginTransaction()
                    .replace(R.id.container, QuestionnaireResultFragment.newInstance(MainFragmentConfig.QUESTIONNAIRE_RESULT_FRAGMENT))
                    .commit();
        }
    }

    // title & message
    public void openDialogMessageType() {
        Bundle args = new Bundle();
        args.putInt(CommonDialogFragment.FIELD_TITLE, R.string.app_name);
        args.putInt(CommonDialogFragment.FIELD_MESSAGE, R.string.app_name);
        args.putInt(CommonDialogFragment.FIELD_LABEL_POSITIVE, android.R.string.ok);
        CommonDialogFragment dialogFragment = new CommonDialogFragment();
        dialogFragment.setArguments(args);
        dialogFragment.show(getSupportFragmentManager(), "dialog1");
    }
    public void openAlertDialogUnimplemented() {
        Bundle args = new Bundle();
        args.putInt(CommonDialogFragment.FIELD_TITLE, R.string.alert_title_message_unimplemented);
        args.putInt(CommonDialogFragment.FIELD_MESSAGE, R.string.alert_message_unimplemented);
        args.putInt(CommonDialogFragment.FIELD_LABEL_POSITIVE, android.R.string.ok);
        CommonDialogFragment dialogFragment = new CommonDialogFragment();
        dialogFragment.setArguments(args);
        dialogFragment.show(getSupportFragmentManager(), "dialog1");
    }
    public void openAlertDialogSitllGetRegistration() {
        Bundle args = new Bundle();
        args.putInt(CommonDialogFragment.FIELD_TITLE, R.string.alert_title_message_still_get_roster);
        args.putInt(CommonDialogFragment.FIELD_MESSAGE, R.string.alert_message_still_get_roster);
        args.putInt(CommonDialogFragment.FIELD_LABEL_POSITIVE, android.R.string.ok);
        CommonDialogFragment dialogFragment = new CommonDialogFragment();
        dialogFragment.setArguments(args);
        dialogFragment.show(getSupportFragmentManager(), "dialog1");
    }
    public void openAlertDialogNoUse() {
        Bundle args = new Bundle();
        args.putInt(CommonDialogFragment.FIELD_TITLE, R.string.alert_title_message_nouse);
        args.putInt(CommonDialogFragment.FIELD_MESSAGE, R.string.alert_message_nouse);
        args.putInt(CommonDialogFragment.FIELD_LABEL_POSITIVE, android.R.string.ok);
        CommonDialogFragment dialogFragment = new CommonDialogFragment();
        dialogFragment.setArguments(args);
        dialogFragment.show(getSupportFragmentManager(), "dialog1");
    }
    public void openAlertDialogDontReCallTheRoll() {
        Bundle args = new Bundle();
        args.putInt(CommonDialogFragment.FIELD_TITLE, R.string.alert_title_message);
        args.putInt(CommonDialogFragment.FIELD_MESSAGE, R.string.alert_message_donot_re_call_the_roll);
        args.putInt(CommonDialogFragment.FIELD_LABEL_POSITIVE, android.R.string.ok);
        CommonDialogFragment dialogFragment = new CommonDialogFragment();
        dialogFragment.setArguments(args);
        dialogFragment.show(getSupportFragmentManager(), "dialog1");
    }

    public void openNotAcceptInputValueAlertDialog() {
        Bundle args = new Bundle();
        args.putInt(CommonDialogFragment.FIELD_TITLE, R.string.alert_title_message);
        args.putInt(CommonDialogFragment.FIELD_MESSAGE, R.string.alert_message_cannot_accept_input_value);
        args.putInt(CommonDialogFragment.FIELD_LABEL_POSITIVE, android.R.string.ok);
        CommonDialogFragment dialogFragment = new CommonDialogFragment();
        dialogFragment.setArguments(args);
        dialogFragment.show(getSupportFragmentManager(), "dialog1");
    }
    /**
     *  onSectionAttachedメソッド
     *  ドロワーナビゲーションで選択された項目名称が、
     *  ActionBar内(画面左上)の文言に反映されます.
     *  @param int number ドロワーナビゲーションで選択された番号
     * */
    public void onSectionAttached(int number) {
        switch (number) {
            case MainFragmentConfig.BOTTOM_FRAGMENT:
                mTitle = getString(R.string.title_section0);
                break;
            case MainFragmentConfig.SEAT_SITUATION_FRAGMENT:
                //着座状況
                String roomName = this.getClassObject().getFacultyObject().getClassPlan().getPlace().getRoomName();
                mTitle = getString(R.string.title_section1)+" ("+roomName+")";
                break;
            case MainFragmentConfig.PARTICIPANTS_FRAGNEMT:
                mTitle = getString(R.string.title_section2);
                break;
            case MainFragmentConfig.GROUP_READJUSTMENT:
                //  グループ再調整
                mTitle = getString(R.string.title_section12);
                break;
            case MainFragmentConfig.QUESTIONNAIRE_FRAGMENT:
                mTitle = getString(R.string.title_section6);
                break;
            case MainFragmentConfig.SURVEY_FRAGMENT:
                //  アンケート
                mTitle = getString(R.string.title_section13);
                break;
        }
    }

     public void restoreActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
     }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            if(mNavigationDrawerFragment.getCurrentSelectedPosition() ==  MainFragmentConfig.SEAT_SITUATION_FRAGMENT) {
                //着席状況把握の場合
                //ハンズフリー出席管理用のメニュー
                //主に再出席調査メニューが含まれています.
                getMenuInflater().inflate(R.menu.menu_hands_free_attendance, menu);
            }else if(mNavigationDrawerFragment.getCurrentSelectedPosition() ==  MainFragmentConfig.PARTICIPANTS_FRAGNEMT){
                //受講者一覧の場合.
                getMenuInflater().inflate(R.menu.menu_roster, menu);
            }else if(mNavigationDrawerFragment.getCurrentSelectedPosition() == MainFragmentConfig.QUESTIONNAIRE_FRAGMENT){
                //  クリッカー
                getMenuInflater().inflate(R.menu.menu_questionnaire, menu);
            }else{
                getMenuInflater().inflate(R.menu.main, menu);
            }
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    /**現在送信中です...**/
    public void showAlertDialogSendState() {
        Bundle args = new Bundle();
        args.putInt(CommonDialogFragment.FIELD_TITLE, R.string.re_call_the_roll_alert_dialog_title);
        args.putInt(CommonDialogFragment.FIELD_MESSAGE, R.string.re_call_the_roll_alert_dialog_message);
        args.putInt(CommonDialogFragment.FIELD_LABEL_POSITIVE, android.R.string.ok);
        CommonDialogFragment dialogFragment = new CommonDialogFragment();
        dialogFragment.setArguments(args);
        dialogFragment.show(getSupportFragmentManager(), "re_call_the_rool_alert_dialog");
    }
    /**再調査終了しました.**/
    public void showEndCallTheRollAlertDialog() {
        Bundle args = new Bundle();
        args.putInt(CommonDialogFragment.FIELD_TITLE, R.string.re_call_the_roll_end_alert_dialog_title);
        args.putInt(CommonDialogFragment.FIELD_MESSAGE, R.string.re_call_the_roll_end_alert_dialog_message);
        args.putInt(CommonDialogFragment.FIELD_LABEL_POSITIVE, android.R.string.ok);
        CommonDialogFragment dialogFragment = new CommonDialogFragment();
        dialogFragment.setArguments(args);
        dialogFragment.show(getSupportFragmentManager(), "re_call_the_rool_alert_dialog");
    }
@Override
public boolean onOptionsItemSelected(MenuItem item) {
    int id = item.getItemId();
    if (id == R.id.action_settings) {
        return true;
    }else if(id == R.id.menu_hands_free_help_signal){
        showHelpSignalFragment();
    }else if (id == R.id.menu_hands_free_attendance_re_call_the_roll) {
        //  在室確認実施へ
        confirmReAttendance();
    } else if (id == R.id.menu_action_questionnaire_survey) {
        //  クリッカー問リスト画面へ
        doClickerDistributeFragment(MainFragmentConfig.QUESTIONNAIRE_FRAGMENT, 1);
    }
    return super.onOptionsItemSelected(item);
}
    /**
     * Created by scr on 2016/02/04.
     * showHelpSignalFragmentメソッド
     * ACKシグナルのマニュアルフラグメント表示
     */
    private void showHelpSignalFragment(){
        HelpSignalDialogFragnemt hsDf = HelpSignalDialogFragnemt.newInstance();
        hsDf.show(this.getSupportFragmentManager(), HelpSignalDialogFragnemt.HELP_SIGNAL_DIALOG_FRAGMENT);
    }
    /**
     * Created by scr on 2016/02/20.
     * confirmReAttendance
     * 在室確認を行いますか？の確認ダイアログ
     */
    private void confirmReAttendance(){

        boolean transmitPossible = chkTransmitReAttPossible();
        if(!transmitPossible){
            //   在室確認をおこなうことができない
            return;
        }
        new AlertDialog.Builder(this)
                .setTitle("在室確認")
                .setMessage("在室確認をおこないますか？")
                .setPositiveButton("はい", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        doReAttendance();
                    }
                })
                .setNegativeButton("いいえ", null)
                .show();
    }

    /**
     * Created by scr on 2016/02/04.
     * doReAttendanceメソッド
     * 在室確認を行う
     */
    private void doReAttendance(){
        requestReAttendance();
    }
    /**
     * Created by scr on 2016/02/04.
     * requestReAttendanceメソッド
     * 在室確認要請をDBにリクエストする.
     */
    private void requestReAttendance(){
        //  サーバからのレスポンスがくるまでの時差を埋めるためにメモリ上は送信状態にする.
        this.getClassObject().getTransmitStateObject().setBmpTransmitId(TransmitTypeConfig.TRANSMIT_TYPE_RE_ATTENDAN);
        this.classHttpRequest.getReAttendanceStart();
    }

    /**
     * Created by scr on 2016/02/04.
     * chkTransmitReAttPossibleメソッド
     * @return false    :   送信中のため、送信NG
     *          true    :   送信していないので、送信OK
     * **/
    private boolean chkTransmitReAttPossible(){
        return new TransmitReAttChecker(this).chkTransmitPossible();
    }




    /**
 * A placeholder fragment containing a simple view.
 */
public static class PlaceholderFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static PlaceholderFragment newInstance(int sectionNumber) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public PlaceholderFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(
                getArguments().getInt(ARG_SECTION_NUMBER));
    }
}

}
