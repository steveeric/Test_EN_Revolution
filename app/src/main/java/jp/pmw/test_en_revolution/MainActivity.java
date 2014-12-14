package jp.pmw.test_en_revolution;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import jp.pmw.test_en_revolution.attendee.AttendeeFragment;
import jp.pmw.test_en_revolution.common.CommonDialogFragment;
import jp.pmw.test_en_revolution.drawer.NavigationDrawerFragment;
import jp.pmw.test_en_revolution.for_got_esl.ForgotESLFragment;
import jp.pmw.test_en_revolution.re_call_the_roll.ReCallTheRollFragment;
import jp.pmw.test_en_revolution.room.Cell;

public class MainActivity extends FragmentActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

/**
 * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
 */
private jp.pmw.test_en_revolution.drawer.NavigationDrawerFragment mNavigationDrawerFragment;

/**
 * Used to store the last screen title. For use in {@link #restoreActionBar()}.
 */
private CharSequence mTitle;

/**
 * 教室IDを保持しておく
 * **/
private String mRoomId="30605800000000";
/**
* 講義IDを保持しておく
* **/
private String mClassPlanId="141212013060581400000";
/**
* 教室IDのゲッター
* **/
public String getRoomId(){
    return this.mRoomId;
}
/**
* 教室IDのゲッター
* **/
public String getClassPlanId(){
        return this.mClassPlanId;
    }


@Override
protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
        int a = 0;
        a = 12;
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
        if(position == 1){
        fragmentManager.beginTransaction()
        .replace(R.id.container, SeatSituationFragment.newInstance(position + 1))
        .commit();
        }else if(position == 2){
        fragmentManager.beginTransaction()
        .replace(R.id.container, AttendeeFragment.newInstance(position + 1))
        .commit();
        }else if(position == 3){
        fragmentManager.beginTransaction()
        .replace(R.id.container, ReCallTheRollFragment.newInstance(position + 1))
        .commit();
        }else if(position == 4){
        fragmentManager.beginTransaction()
        .replace(R.id.container, ForgotESLFragment.newInstance(position + 1))
        .commit();
        }else {
        fragmentManager.beginTransaction()
        .replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
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
        args.putInt(CommonDialogFragment.FIELD_TITLE, R.string.app_name);
        // 定義されてる文字なら
        //args.putIntArray(CommonDialogFragment.FIELD_LIST_ITEMS, new int[] {R.string.item1, R.string.item2});
        // ソースで動的に文字列をつくるなら
        //args.putStringArray(CommonDialogFragment.FIELD_LIST_ITEMS_STRING, new String[] {"座席ID : "+cell.getSeat().getSeatId(), "セルの行 : "+cell.getSeat().getSeatRowNumber(),"セルの列 : "+cell.getSeat().getSeatColumnNumber()});
        args.putStringArray(CommonDialogFragment.FIELD_LIST_ITEMS_STRING, new String[] {"学籍番号 : "+cell.getAttendee().mStudentId, "氏  名 : "+cell.getAttendee().mFullName});
        args.putInt(CommonDialogFragment.FIELD_LABEL_POSITIVE, android.R.string.ok);

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
            case 2:
                mTitle = getString(R.string.title_section1);
                break;
            case 3:
                mTitle = getString(R.string.title_section2);
                break;
            case 4:
                mTitle = getString(R.string.title_section3);
                break;
            case 5:
                mTitle = getString(R.string.title_section4);
                break;
            case 7:
                mTitle = getString(R.string.title_section5);
                break;
            case 8:
                mTitle = getString(R.string.title_section6);
                break;
            case 10:
                mTitle = getString(R.string.title_section7);
                break;
            case 11:
                mTitle = getString(R.string.title_section8);
                break;
            case 13:
                mTitle = getString(R.string.title_section9);
                break;
            case 14:
                mTitle = getString(R.string.title_section10);
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
        // if the drawer is not showing. Otherwise, let the drawer
        // decide what to show in the action bar.
        getMenuInflater().inflate(R.menu.main, menu);
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
