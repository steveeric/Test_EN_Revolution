package jp.pmw.test_en_revolution;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.Timer;

import jp.pmw.test_en_revolution.common.CommonDialogFragment;
import jp.pmw.test_en_revolution.confirm_class_plan.ConfirmClassPlanActivity;
import jp.pmw.test_en_revolution.confirm_class_plan.Roster;
import jp.pmw.test_en_revolution.confirm_class_plan.Seat;
import jp.pmw.test_en_revolution.confirm_class_plan.Student;
import jp.pmw.test_en_revolution.confirm_class_plan.dummy.DummyContentClass;
import jp.pmw.test_en_revolution.drawer.NavigationDrawerFragment;
import jp.pmw.test_en_revolution.dummy.DummyRosterContent;
import jp.pmw.test_en_revolution.grouping.GroupingFragment;
import jp.pmw.test_en_revolution.grouping.GroupingManagement;
import jp.pmw.test_en_revolution.history.SearchHistoryDialogFragment;
import jp.pmw.test_en_revolution.one_cushion.select_teacher.Teacher;
import jp.pmw.test_en_revolution.one_cushion.select_teacher.dummy.CSVCtrl;
import jp.pmw.test_en_revolution.questionnaire.Question;
import jp.pmw.test_en_revolution.questionnaire.Questionnaire;
import jp.pmw.test_en_revolution.questionnaire.QuestionnaireFragment;
import jp.pmw.test_en_revolution.questionnaire.QuestionnaireResultFragment;
import jp.pmw.test_en_revolution.questionnaire.dummy.DummyQuestionContent;

public class MainActivity extends MyFragmentActivity {

/**
 * Used to store the last screen title. For use in {@link #restoreActionBar()}.
 */

    public static final String CSV_FILE_NAME_SEATS_MST_241 = "SEATS_MST_241.csv";
    public static final String CSV_FILE_NAME_SEATS_MST_1317 = "SEATS_MST_1317.csv";
    public static final String CSV_FILE_NAME_SEATS_MST_135 = "SEATS_MST_135.csv";

    public static final String CSV_FILE_NAME_SEATS_ID_241 = "SEAT_ID_241.csv";
    public static final String CSV_FILE_NAME_SEATS_ID_1317 = "SEAT_ID_1317.csv";
    public static final String CSV_FILE_NAME_SEATS_ID_135 = "SEAT_ID_135.csv";

    //10分後にアプリが落ちるようにセットする.
    private static final long END_MANUAL_CLASS_ROOM_TIME = 600000;
    private static final long ONE_MINUTE_INTERVALS = 60000;
private CharSequence mTitle;
/*教員情報を保持する(授業情報も保持しています.)*/
public Teacher mTeacher;
/*ナビゲーションドロワーのリスト項目*/
private ListView mNavigationDrawerList;
/*ナビゲーションドロワーの50音順に戻るボタン*/
private Button mNavigationDrawerButton;
//アンケート画面で現在見ている問題トピック番号
public int mNowSeeQuestionTopic=0;
    //ドロワーフラグメントListViewのどの項目を選択しているかを保持する.
    public int mNavigationDrawerItemSelected = 0;
@Override
protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    //授業終了タイマー
    this.endClassRoomTimer = new Timer();
    //デモバージョンは終了時間を自分で決める.
    //onCreateしてから5分後に落ちるように設定!
    long endTime = System.currentTimeMillis() + END_MANUAL_CLASS_ROOM_TIME;
    this.endClassRoomTimer.schedule(new FinishTimer(this,endTime), 0,ONE_MINUTE_INTERVALS);

    if(this.getIntent()!=null){
        Intent intent = this.getIntent();
        Teacher data = (Teacher)intent.getSerializableExtra(ConfirmClassPlanActivity.TEACHER);
        /*ダミーの受講者を代入*/
        setDummyAttendance(data);
        /*ダミーのアンケートを代入*/
        setDummyQuestionnaire(data);
        mTeacher = data;
    }
        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.navigation_drawer);

        //タイトルバー初期化
        mTitle = "";
        //mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
        R.id.navigation_drawer,
        (DrawerLayout) findViewById(R.id.drawer_layout));
}
    /**
     * Created by scr on 2015/1/5.
     * setDummyQuestionnaireメソッド
     * 通常はネットワークDBから取得するアンケートに関するデータを
     * ローカル端末内設置されたダミーデータから読み取る
     */
    private void setDummyQuestionnaire(Teacher teacher){
        /*グルーピング*/
        GroupingManagement groupingManagement = new GroupingManagement();
        teacher.setGroupingManagement(groupingManagement);
        /*アンケート*/
        List<Question> questions = DummyQuestionContent.getQuestions();
        Questionnaire que = new Questionnaire(questions);
        teacher.setQuestionnaire(que);
    }
    /**
     * Created by scr on 2015/1/5.
     * setLastSeeQuestionIdメソッド
     * 最後に開始したQuestionidを保持させる.     */
    public void setLastSeeQuestionId(String questionId){
        this.mTeacher.getQuestionnaire().setLastQuestionId(questionId);
    }

    /**
     * Created by scr on 2015/1/4.
     * setDummyAttendanceメソッド
     * 通常はネットワークDBから取得する出席者に関するデータを
     * ローカル端末内に設置されたCSVファイルから読み込む
     */
    private void setDummyAttendance(Teacher teacher){
        Roster roster = DummyRosterContent.getDummyRoster();
        teacher.setRoster(roster);

        String roomId = teacher.getClassPlan().getPlace().getRoom().getRoomId();
        String seatIdFileName = "";
        String fileName = "";
        if(roomId.equals(DummyContentClass.ROOM_ID_135)){
            fileName =  this.CSV_FILE_NAME_SEATS_MST_135;
            seatIdFileName = this.CSV_FILE_NAME_SEATS_ID_135;
        }else if(roomId.equals(DummyContentClass.ROOM_ID_1317)){
            fileName =  this.CSV_FILE_NAME_SEATS_MST_1317;
            seatIdFileName = this.CSV_FILE_NAME_SEATS_ID_1317;
        }else{
            fileName =  this.CSV_FILE_NAME_SEATS_MST_241;
            seatIdFileName = this.CSV_FILE_NAME_SEATS_ID_241;
        }

        /*ダミー座席位置を読み込み*/
        List<Seat> seats = readDummySeatMst(fileName);
        List<String>seatIds = readDummySeatId(seatIdFileName);

        //ダミーの座席IDをセットする.
        for(int i = 0;i < roster.getRosterList().size(); i++){
            roster.getRosterList().get(i).getThisClassTime().setThisClassSittingPositionId(seatIds.get(i));
        }


        /**/

        /*ダミー座席位置をセット*/
        for(int i = 0; i < roster.getRosterList().size(); i++){
            String sitSeatId = roster.getRosterList().get(i).getThisClassTime().getThesClassSittingPositionId();
            for(int j=0; j<seats.size();j++){
                String seatId = seats.get(j).getSeatId();
                if(seatId.equals(sitSeatId)){
                    Seat seat =  seats.get(j);
                    roster.getRosterList().get(i).getThisClassTime().setThisClassSittingPosition(seat);
                    break;
                }
            }
        }
    }
    /**
     * Created by scr on 2015/1/4.
     * readDummySeatMstメソッド
     * 通常はネットワークDBから取得するSEAT_MSTを
     * ローカル端末内にあるCSVファイルから読み込む.
     */
    public List<Seat> readDummySeatMst(){
        List<Seat> seats = new ArrayList<Seat>();
        String fileName = CSV_FILE_NAME_SEATS_MST_241;
        CSVCtrl csv = new CSVCtrl();
        List<StringTokenizer> tokenizers =  csv.readCSV(this, fileName);
        for(int i = 0; i < tokenizers.size(); i++){
            StringTokenizer token = tokenizers.get(i);
            int count = 0;
            String[] items = new String[5];
            while(token.hasMoreTokens()) {
                String item = token.nextToken();
                items[count] = item;
                ++count;
            }
            String seatId = items[0];
            int row = Integer.valueOf(items[3]);
            int column = Integer.valueOf(items[4]);
            Seat seat = new Seat(seatId,row,column);
            seats.add(seat);
        }
        return seats;
    }
    public List<Seat> readDummySeatMst(String fileName){
        List<Seat> seats = new ArrayList<Seat>();
        CSVCtrl csv = new CSVCtrl();
        List<StringTokenizer> tokenizers =  csv.readCSV(this, fileName);
        for(int i = 0; i < tokenizers.size(); i++){
            StringTokenizer token = tokenizers.get(i);
            int count = 0;
            String[] items = new String[5];
            while(token.hasMoreTokens()) {
                String item = token.nextToken();
                items[count] = item;
                ++count;
            }
            String seatId = items[0];
            int row = Integer.valueOf(items[3]);
            int column = Integer.valueOf(items[4]);
            Seat seat = new Seat(seatId,row,column);
            seats.add(seat);
        }
        return seats;
    }

    public List<String> readDummySeatId(String fileName){
            List<String> seats = new ArrayList<String>();
            CSVCtrl csv = new CSVCtrl();
            List<StringTokenizer> tokenizers =  csv.readCSV(this, fileName);
            for(int i = 0; i < tokenizers.size(); i++){
                StringTokenizer token = tokenizers.get(i);
                int count = 0;
                String[] items = new String[1];
                while(token.hasMoreTokens()) {
                    String item = token.nextToken();
                    items[count] = item;
                    ++count;
                }
                String seatId = items[0];
                seats.add(seatId);
            }
            return seats;
        }
    public void setTimer(Timer timer){
        demoCallTheRollTimer = timer;
    }
    //出席調査タイマー
    private Timer demoCallTheRollTimer;
    //授業終了タイマー
    private Timer endClassRoomTimer;

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

    public void demoCallTheRollCancel(){
        if(demoCallTheRollTimer!=null){
            demoCallTheRollTimer.cancel();
        }
        showEndCallTheRollAlertDialog();
    }
    public void onResume(){
        super.onResume();
        /**
         * エラー処理を行う.
         * **/
        if(this.mTeacher == null){
            //メモリから教員に関すること(授業情報、受講生など)が消えた.
            //エラーなので初めからやり直すように促す.
        }else{
            //エラーではないので処理を始める
            if(this.mTeacher.getEndAttendanceFlag() == false){
                demoCallTheRollTimer = new Timer();
                DemoReCollTheRollTimer reTimer
                        = new DemoReCollTheRollTimer(this,DemoReCollTheRollTimer.MODE_MAINACTIVITY,this.mTeacher.getRoster().getRosterList());
                this.demoCallTheRollTimer.schedule(reTimer, 10000,1000);
            }
        }
    }

    public void onStop(){
        super.onStop();
        if(this.demoCallTheRollTimer!=null){
            demoCallTheRollTimer.cancel();
        }
    }
    public void onDestroy(){
        super.onDestroy();
        if(this.mTeacher!=null){
            //グループ情報初期化
            initializeGrouping();
            initializeStudent();
            this.mTeacher = null;
        }
        //授業終了タイマー初期化
        if(endClassRoomTimer != null){
            this.mTeacher = null;
        }
    }

    private void initializeStudent(){
        for(int i=0;i<this.mTeacher.getRoster().getRosterList().size();i++){
            Student student = this.mTeacher.getRoster().getRosterList().get(i);
            student = null;
        }
    }


    private void initializeGrouping(){
        for(int i=0;i<this.mTeacher.getRoster().getRosterList().size();i++){
            this.mTeacher.getRoster().getRosterList().get(i).getThisClassTime().setThisClassGroup(null);
        }
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
        /*ListView listView = mNavigationDrawerFragment.getDrawerListView();

        mNavigationDrawerFragment.changeTap(listView,position);
        mNavigationDrawerFragment.selectItem(position);*/

        fragmentManager.beginTransaction()
                .replace(R.id.container, SeatSituationFragment.newInstance(position))
                .commit();
    }
@Override
public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        //FragmentManager fragmentManager = getFragmentManager();
        FragmentManager fragmentManager = getSupportFragmentManager();
        /*fragmentManager.beginTransaction()
                .replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
                .commit();*/

        // update the main content by replacing fragments
        //FragmentManager fragmentManager = getSupportFragmentManager();
        //FragmentManager fragmentManager = getSupportFragmentManager();
    mNavigationDrawerItemSelected = position;

        if(position == MainFragmentConfig.BOTTOM_FRAGMENT){
            fragmentManager.beginTransaction()
                    .replace(R.id.container, MainBottomFragment.newInstance(position))
                    .commit();
        }else if(position == MainFragmentConfig.SEAT_SITUATION_FRAGMENT){
            /*着席状況一覧*/
            //moveToSeatSituationFragment();
                     /*着座状況*/
            fragmentManager.beginTransaction()
                    .replace(R.id.container, SeatSituationFragment.newInstance(position))
                    .commit();
        }else if(position == MainFragmentConfig.PARTICIPANTS_FRAGNEMT){
            /*出席者一覧*/
        fragmentManager.beginTransaction()
        .replace(R.id.container, AttendeeFragment.newInstance(position))
        .commit();
        }else if(position == MainFragmentConfig.GROUPING_FRAGMENT){
            /*グルーピング*/
            fragmentManager.beginTransaction()
                    .replace(R.id.container, GroupingFragment.newInstance(position))
                    .commit();
        }else if(position == MainFragmentConfig.QUESTIONNAIRE_FRAGMENT){
            /*クリッカー*/
            /*2014年12月15日に,
            複数あるので関数化しました.*/
            doClickerDistributeFragment(position,1);
        }/*else if(position == 13){
            //ヘルプ
            fragmentManager.beginTransaction()
                    .replace(R.id.container, HelpFragment.newInstance(position + 1))
                    .commit();
        }*/else{
        fragmentManager.beginTransaction()
        .replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
        .commit();
        }
        //ドロワーの必要個所をオープンにする.
        //openNavigationDrawer();
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
            fragmentManager.beginTransaction()
                    .replace(R.id.container, QuestionnaireFragment.newInstance(position))
                    .commit();
        }else if(position == MainFragmentConfig.QUESTIONNAIRE_FRAGMENT && groundPosition == 2) {
            //クリッカーの回答結果を表示するフラグメント生成
            fragmentManager.beginTransaction()
                    .replace(R.id.container, QuestionnaireResultFragment.newInstance(position))
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
     * Created by Shota Ito on 2014/12/11～2014/12/11.
     * openDialogShowCellInfoメソッド
     * RoomViewをユーザーがタップした場合にアラートが立ち上がる.
     * タップしたセル位置の情報をアラートダイアログに出力する.
     * @param cell 教室マップのセルを管理するクラス.
     */
    /*public void openDialogShowCellInfo(Cell cell) {
       // String str = cell.getSeat().getSeatId()+":"+cell.getSeat().getSeatRowNumber()+"-"+cell.getSeat().getSeatColumnNumber();
        Bundle args = new Bundle();
        args.putInt(CommonDialogFragment.FIELD_TITLE, R.string.infomation_sitter);
        // 定義されてる文字なら
        //args.putIntArray(CommonDialogFragment.FIELD_LIST_ITEMS, new int[] {R.string.item1, R.string.item2});
        // ソースで動的に文字列をつくるなら
        //args.putStringArray(CommonDialogFragment.FIELD_LIST_ITEMS_STRING, new String[] {"座席ID : "+cell.getSeat().getSeatId(), "セルの行 : "+cell.getSeat().getSeatRowNumber(),"セルの列 : "+cell.getSeat().getSeatColumnNumber()});
        args.putStringArray(CommonDialogFragment.FIELD_LIST_ITEMS_STRING, new String[] {"学籍番号 : "+cell.getAttendee().getOriginalstudentId(), "氏  名 : "+cell.getAttendee().getFullName()});
        args.putInt(CommonDialogFragment.FIELD_LABEL_POSITIVE, R.string.close);

        CommonDialogFragment dialogFragment = new CommonDialogFragment();
        dialogFragment.setArguments(args);
        dialogFragment.show(getSupportFragmentManager(), "openDialogShowCellInfo");
    }*/

    /*Timer timer;
    public void test(final RoomView rv,final Cell[][] cells){
        //タイマー
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                Log.d("run", "TimerTask Thread id = " + Thread.currentThread().getId());
                try {
                    // 10回に1回2500msの遅延
                    //Thread.sleep(count % 10 == 0 ? 2500 : 0);
                    Thread.sleep(3000);
                    for(int i=0;i<cells.length;i++){
                        for(int j=0;j<cells[i].length;j++){
                            if(cells[i][j].getPreAttendee() == 1){
                                if(cells[i][j].getPreAttendee()==1){
                                    if(cells[i][j].getSeat().getPreAttendeeState()==0){
                                        int ran=(int)(Math.random()*10);
                                        int state = 2;
                                        if(ran > 2){
                                            state = 1;
                                        }
                                        cells[i][j].getSeat().setPreAttendeeState(state);
                                        rv.postInvalidate();
                                        Thread.sleep(250);
                                    }
                                }
                            }
                        }
                    }
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }, 1000,2000);
    }
    @Override
    protected void onStop(){
        super.onStop();
        try {
            //タイマーを停止させる
            timer.cancel();
            //アプリがストップしてから１０秒後にタイマーを終了させる。
            TimeUnit.SECONDS.sleep(0);
            //Log.d(TAG,"停止しました。");
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }*/

    public void onSectionAttached(int number) {
        switch (number) {
            case MainFragmentConfig.BOTTOM_FRAGMENT:
                mTitle = getString(R.string.title_section0);
                break;
            case MainFragmentConfig.SEAT_SITUATION_FRAGMENT:
                //mTitle = getString(R.string.title_section1);
                //着座状況
                String roomName = this.mTeacher.getClassPlan().getPlace().getRoom().getRoomName();
                mTitle = getString(R.string.title_section1)+" ("+roomName+")";
                break;
            case MainFragmentConfig.PARTICIPANTS_FRAGNEMT:
                mTitle = getString(R.string.title_section2);
                break;
            case MainFragmentConfig.GROUPING_FRAGMENT:
                mTitle = getString(R.string.title_section5);
                break;
            case MainFragmentConfig.QUESTIONNAIRE_FRAGMENT:
                mTitle = getString(R.string.title_section6);
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
        // Only show items in the action bar relevant to this screen
        // if the drawer i  s not showing. Otherwise, let the drawer
        // decide what to show in the action bar.

        //getMenuInflater().inflate(R.menu.main, menu);
        if(mNavigationDrawerFragment.getCurrentSelectedPosition() ==  MainFragmentConfig.SEAT_SITUATION_FRAGMENT) {
            //着席状況把握の場合
            //ハンズフリー出席管理用のメニュー
            //主に再出席調査メニューが含まれています.
            getMenuInflater().inflate(R.menu.menu_hands_free_attendance, menu);
        }else if(mNavigationDrawerFragment.getCurrentSelectedPosition() ==  MainFragmentConfig.PARTICIPANTS_FRAGNEMT){
            //受講者一覧の場合.
            getMenuInflater().inflate(R.menu.menu_roster, menu);
        }else if(mNavigationDrawerFragment.getCurrentSelectedPosition() == 5){
            /**
             * クリッカー
             * **/
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
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    //noinspection SimplifiableIfStatement
    if (id == R.id.action_settings) {
        return true;
    }else if(id == R.id.menu_hands_free_attendance_student_history){
        //未実装
        //openAlertDialogUnimplemented();
       //学生個人履歴フラグメント
        SearchHistoryDialogFragment customDialog = SearchHistoryDialogFragment.newInstance();
        customDialog.setTargetFragment(null,0);
        Bundle bundle = new Bundle();
        //受講生一覧
        Roster roster = this.mTeacher.getRoster();
        bundle.putSerializable(SearchHistoryDialogFragment.ROSTER,roster);
        customDialog.setArguments(bundle);
        customDialog.show(getSupportFragmentManager(), SearchHistoryDialogFragment.SEARCH_HISTORY_DIALOG_FRAGMENT);

    }else if(id == R.id.menu_hands_free_attendance_re_call_the_roll){
        //
        //openAlertDialog("まだ全員の出席が確認できていないので使用することが出来ません.");
        //openAlertDialogDontReCallTheRoll();

        FragmentManager fragmentManager = getSupportFragmentManager();
        /*SeatSituationFragment fragment = SeatSituationFragment.newInstance(MainFragmentConfig.SEAT_SITUATION_FRAGMENT);
        fragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
                .commit();*/

        if(this.mTeacher.getEndAttendanceFlag() == true) {
            this.mTeacher.setEndAttendanceFlag(false);

            List<Student> attendance = this.mTeacher.getRoster().getRosterList();
            //初期化
            for (int i = 0; i < attendance.size(); i++) {
                Student st = attendance.get(i);
                //if(st.getThisClassTime().getThisClassAttendanceState().getRequestForgotESLTime() != null){
                st.getThisClassTime().getThisClassAttendanceState().setTempAttendanceState(1);
                st.getThisClassTime().getThisClassAttendanceState().setConfirmTime(null);
                //}
            }
            //fragment.onResume();
            //送信中になる...
            if(mNavigationDrawerItemSelected == MainFragmentConfig.SEAT_SITUATION_FRAGMENT) {
                SeatSituationFragment fragment = (SeatSituationFragment) fragmentManager.findFragmentById(R.id.container);
                fragment.demo(attendance);
            }else if(mNavigationDrawerItemSelected == MainFragmentConfig.PARTICIPANTS_FRAGNEMT){
                AttendeeFragment fragment = (AttendeeFragment) fragmentManager.findFragmentById(R.id.container);
                fragment.demo(attendance);
            }
        }else{
            //現在再出席調査中です...
            showAlertDialogSendState();
        }
    }else if(id == R.id.menu_action_questionnaire_survey){
        /*クリッカー調査実施画面へ*/
        //Toast.makeText(this,"クリッカー問題送信画面", Toast.LENGTH_SHORT).show();
        doClickerDistributeFragment(5,1);
    }else if(id == R.id.menu_action_questionnaire_result){
        /*クリッカーの回答結果を見る*/
        //Toast.makeText(this,"クリッカーの回答結果を見る.", Toast.LENGTH_SHORT).show();
        doClickerDistributeFragment(5, 2);
    }
    return super.onOptionsItemSelected(item);
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
