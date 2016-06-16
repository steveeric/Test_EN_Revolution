package jp.pmw.test_en_revolution;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.widget.Toast;

import jp.pmw.test_en_revolution.drawer.NavigationDrawerFragment;

/**
 * Created by scr on 2014/12/15.
 * FragmentAcitvityのリターンキーを制御するクラスです.
 */
abstract public class MyFragmentActivity extends FragmentActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks,ConnectionReceiver.Observer{

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    public jp.pmw.test_en_revolution.drawer.NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Created by scr on 2014/12/11.
     * openNavigationDrawerメソッド
     * ドロワーフラグメントの必要個所をオープンにする.
     */
    abstract public void openNavigationDrawer();
    private ConnectionReceiver mConnectionReceiver;

    public void setReciver(){
        IntentFilter filter = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
        mConnectionReceiver = new ConnectionReceiver(this);
        registerReceiver(mConnectionReceiver, filter);
    }

    @Override
    public void onConnect() {
        //ネットワーク接続検出
        //Toast.makeText(getApplicationContext(), "onConnect", Toast.LENGTH_LONG).show();
    }
    @Override
    public void onDisconnect() {
        //ネットワーク切断検出
        //Toast.makeText(getApplicationContext(), "onDisconnect", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this,DisconectActivity.class);
        startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //ネットワークレシーバー
        setReciver();
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

    @Override
    public void onDestroy(){
        super.onDestroy();
        if(mConnectionReceiver != null){
            unregisterReceiver(mConnectionReceiver);
        }
        finish();
    }

}
