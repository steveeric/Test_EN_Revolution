package jp.pmw.test_en_revolution;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import jp.pmw.test_en_revolution.attendee.AttendeeFragment;
import jp.pmw.test_en_revolution.common.CommonDialogFragment;
import jp.pmw.test_en_revolution.confirm_class_plan.ConfirmClassPlanActivity;
import jp.pmw.test_en_revolution.drawer.NavigationDrawerFragment;
import jp.pmw.test_en_revolution.grouping.GroupingFragment;
import jp.pmw.test_en_revolution.one_cushion.select_teacher.Teacher;
import jp.pmw.test_en_revolution.questionnaire.QuestionnaireFragment;
import jp.pmw.test_en_revolution.questionnaire.QuestionnaireResultFragment;
import jp.pmw.test_en_revolution.room.Cell;

public class MainActivity extends MyFragmentActivity{

/**
 * Used to store the last screen title. For use in {@link #restoreActionBar()}.
 */

private CharSequence mTitle;
/*教員情報を保持する(授業情報も保持しています.)*/
public Teacher mTeacher;
/*ナビゲーションドロワーのリスト項目*/
private ListView mNavigationDrawerList;
/*ナビゲーションドロワーの50音順に戻るボタン*/
private Button mNavigationDrawerButton;


    //アンケート画面で現在見ている問題トピック番号
    public int mNowSeeQuestionTopic=0;
@Override
protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    if(this.getIntent()!=null){
        Intent intent = this.getIntent();
        Teacher data = (Teacher)intent.getSerializableExtra(ConfirmClassPlanActivity.TEACHER);
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


@Override
public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getFragmentManager();
        /*fragmentManager.beginTransaction()
                .replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
                .commit();*/

        // update the main content by replacing fragments
        //FragmentManager fragmentManager = getSupportFragmentManager();
        //FragmentManager fragmentManager = getSupportFragmentManager();
        if(position == MainFragmentConfig.BOTTOM_FRAGMENT){
            fragmentManager.beginTransaction()
                    .replace(R.id.container, MainBottomFragment.newInstance(position))
                    .commit();
        }else if(position == MainFragmentConfig.SEAT_SITUATION_FRAGMENT){
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
        if(position == MainFragmentConfig.QUESTIONNAIRE_FRAGMENT && groundPosition == 1) {
            //クリッカー調査フラグメント生成
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.container, QuestionnaireFragment.newInstance(position))
                    .commit();
        }else if(position == MainFragmentConfig.QUESTIONNAIRE_FRAGMENT && groundPosition == 2) {
            //クリッカーの回答結果を表示するフラグメント生成
            FragmentManager fragmentManager = getFragmentManager();
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

    /**
     * Created by Shota Ito on 2014/12/11～2014/12/11.
     * openDialogShowCellInfoメソッド
     * RoomViewをユーザーがタップした場合にアラートが立ち上がる.
     * タップしたセル位置の情報をアラートダイアログに出力する.
     * @param cell 教室マップのセルを管理するクラス.
     */
    public void openDialogShowCellInfo(Cell cell) {
       // String str = cell.getSeat().getSeatId()+":"+cell.getSeat().getSeatRowNumber()+"-"+cell.getSeat().getSeatColumnNumber();
        Bundle args = new Bundle();
        args.putInt(CommonDialogFragment.FIELD_TITLE, R.string.infomation_sitter);
        // 定義されてる文字なら
        //args.putIntArray(CommonDialogFragment.FIELD_LIST_ITEMS, new int[] {R.string.item1, R.string.item2});
        // ソースで動的に文字列をつくるなら
        //args.putStringArray(CommonDialogFragment.FIELD_LIST_ITEMS_STRING, new String[] {"座席ID : "+cell.getSeat().getSeatId(), "セルの行 : "+cell.getSeat().getSeatRowNumber(),"セルの列 : "+cell.getSeat().getSeatColumnNumber()});
        args.putStringArray(CommonDialogFragment.FIELD_LIST_ITEMS_STRING, new String[] {"学籍番号 : "+cell.getAttendee().mStudentId, "氏  名 : "+cell.getAttendee().mFullName});
        args.putInt(CommonDialogFragment.FIELD_LABEL_POSITIVE, R.string.close);

        CommonDialogFragment dialogFragment = new CommonDialogFragment();
        dialogFragment.setArguments(args);
        dialogFragment.show(getSupportFragmentManager(), "openDialogShowCellInfo");
    }

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
        if(mNavigationDrawerFragment.getCurrentSelectedPosition() == 5){
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

@Override
public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    //noinspection SimplifiableIfStatement
    if (id == R.id.action_settings) {
        return true;
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
