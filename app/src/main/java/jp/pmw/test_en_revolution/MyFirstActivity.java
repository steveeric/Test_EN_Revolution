package jp.pmw.test_en_revolution;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.KeyEvent;
import android.widget.Toast;

/**
 * Created by scr on 2014/12/26.
 */
public class MyFirstActivity extends Activity implements ConnectionReceiver.Observer{
    private ConnectionReceiver mConnectionReceiver;

    public void setReciver(){
        IntentFilter filter = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
        mConnectionReceiver = new ConnectionReceiver(this);
        registerReceiver(mConnectionReceiver, filter);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        unregisterReceiver(mConnectionReceiver);
        finish();
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
