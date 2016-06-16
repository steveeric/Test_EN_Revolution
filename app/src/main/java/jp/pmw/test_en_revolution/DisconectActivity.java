package jp.pmw.test_en_revolution;

import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class DisconectActivity extends FragmentActivity implements ConnectionReceiver.Observer{
    //レシーバー
    private ConnectionReceiver mConnectionReceiver;

    public void setReciver(){
        IntentFilter filter = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
        mConnectionReceiver = new ConnectionReceiver(this);
        registerReceiver(mConnectionReceiver, filter);
    }

    @Override
    public void onConnect() {
        finish();
    }
    @Override
    public void onDisconnect() {
        //ネットワーク切断検出

    }
    @Override
    public void onDestroy(){
        super.onDestroy();
        unregisterReceiver(mConnectionReceiver);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disconect);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
            //レシーバー登録
            setReciver();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_disconect, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        /*if (id == R.id.action_settings) {
            return true;
        }*/

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
            View rootView = inflater.inflate(R.layout.fragment_disconect, container, false);
            return rootView;
        }
    }
    @Override
    public boolean dispatchKeyEvent(KeyEvent e) {
        // キーコード表示
        //tv01.setText("KeyCode:"+e.getKeyCode());
        // 戻るボタンが押されたとき
        if (e.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            // ボタンが押されたとき
            if (e.getAction() == KeyEvent.ACTION_DOWN) {
                Toast.makeText(this, "リターンキーは使用できません.", Toast.LENGTH_SHORT).show();
                return true;
            }
        }
        return super.dispatchKeyEvent(e);
    }
}
