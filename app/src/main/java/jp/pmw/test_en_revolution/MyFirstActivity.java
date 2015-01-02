package jp.pmw.test_en_revolution;

import android.app.Activity;
import android.view.KeyEvent;
import android.widget.Toast;

/**
 * Created by scr on 2014/12/26.
 */
public class MyFirstActivity extends Activity {
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
        finish();
    }
}
