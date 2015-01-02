package jp.pmw.test_en_revolution;

import android.app.Activity;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.Toast;

import jp.pmw.test_en_revolution.one_cushion.select_teacher.JapaneseAlphabeticalOrderFragmentActivity;

/**
 * Created by scr on 2014/12/23.
 * DrowerFragmentActivityがない画面のBaseとなるActivityです.
 * 主にリターンキーを制御しています.
 */
abstract public class MyActivity extends Activity {

    @Override
    public void onResume(){
        super.onResume();
        //アクションバーをタップ可能に
        getActionBar().setDisplayHomeAsUpEnabled(true);
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
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        if (android.R.id.home == item.getItemId()) {
            Intent intent = new Intent(this, JapaneseAlphabeticalOrderFragmentActivity.class);
            startActivity(intent);
            finish();
        }
        return super.onMenuItemSelected(featureId, item);
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
        finish();
    }
}
