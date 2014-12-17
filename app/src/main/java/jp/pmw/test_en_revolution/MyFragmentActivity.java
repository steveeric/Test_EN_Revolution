package jp.pmw.test_en_revolution;

import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.widget.Toast;

import jp.pmw.test_en_revolution.drawer.NavigationDrawerFragment;

/**
 * Created by scr on 2014/12/15.
 * FragmentAcitvityのリターンキーを制御するクラスです.
 */
abstract public class MyFragmentActivity extends FragmentActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

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
