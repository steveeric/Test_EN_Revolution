package jp.pmw.test_en_revolution.one_cushion.select_teacher;

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

import jp.pmw.test_en_revolution.MyFragmentActivity;
import jp.pmw.test_en_revolution.R;
import jp.pmw.test_en_revolution.drawer.NavigationDrawerFragment;

public class JapaneseAlphabeticalOrderFragmentActivity extends MyFragmentActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_japanese_alphabetical_order_fragment);
        /*if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .replace(android.R.id.content,  new JapaneseAlphabeticalOrderFragment())
                    .commit();
        }*/
        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.navigation_drawer);
        if(mNavigationDrawerFragment!=null) {
            // Set up the drawer.
            mNavigationDrawerFragment.setUp(
                    R.id.navigation_drawer,
                    (DrawerLayout) findViewById(R.id.drawer_layout));
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
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new JapaneseAlphabeticalOrderFragment())
                .commit();
    }

    /**
     * Created by scr on 2014/12/12.
     * doChangeSelectAcitivtyメソッド
     * 50音順で選択された文字をキーにSelectAcitivtyに遷移する.
     */
    public void doChangeSelectAcitivty(){
        /*Intent intent = new Intent();
        intent.setClassName(Packcage.ONE_CUSHION, Packcage.SELECT_ACTIVITY);
        startActivity(intent);*/
        Intent intent = new Intent(this,AllSelectActivity.class);
        startActivity(intent);
        // 疑似スタックに詰める
        //ActivityStack.stackHistory(this);
    }
    /**
     * Created by scr on 2014/12/12.
     * 最終更新日:2014/12/14
     * doChangeKanaIndexSelectAcitivtyメソッド
     * 50音順で選択された文字をキーにSelectAcitivtyに遷移する.
     * @parm initial 教員苗字の頭文字
     */
    public void doChangeKanaIndexSelectAcitivty(int tapId){
        /*Intent intent = new Intent();
        intent.setClassName(Packcage.ONE_CUSHION, Packcage.SELECT_ACTIVITY);
        startActivity(intent);*/
        Intent intent = new Intent(this,KanaIndexSelectActivity.class);
        intent.putExtra(KanaIndexSelectActivity.SELECT_INITIAL,tapId);
        startActivity(intent);
        //
        finish();

        // 疑似スタックに詰める
        //ActivityStack.stackHistory(this);
    }

    @Override
    public void openNavigationDrawer(){
       //個々ではドロワーに何も表示させなくていいので何もしない.
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_japanese_alphabetical_order, menu);
        return true;
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

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_japanese_alphabetical_order2, container, false);
            return rootView;
        }
    }
}
