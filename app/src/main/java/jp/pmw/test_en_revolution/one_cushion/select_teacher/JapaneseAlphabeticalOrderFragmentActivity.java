package jp.pmw.test_en_revolution.one_cushion.select_teacher;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.activeandroid.query.Select;

import java.util.List;
import java.util.StringTokenizer;

import jp.pmw.test_en_revolution.MyFirstActivity;
import jp.pmw.test_en_revolution.R;
import jp.pmw.test_en_revolution.one_cushion.select_teacher.dummy.CSVCtrl;
import jp.pmw.test_en_revolution.one_cushion.select_teacher.dummy.DummyStaffsMst;
import jp.pmw.test_en_revolution.one_cushion.select_teacher.dummy.StaffsMst;

/*わ  ら  や  ま  は  な  た  さ  か  あ
1   2   3   4   5   6   7   8   9   10
　　り　　　み　ひ　に　ち　し　き　い
　　12      14  15  16  17  18  19  20
　　る　ゆ　む　ふ　ぬ　つ　す　く　う
    22　23  24  25  26  27  28  29  30
　　れ　　　め　へ　ね　て　せ　け　え
    32      34  35  36  37  38  39  40
　　ろ　よ　も　ほ　の　と　そ　こ　お
    42  43  44  45  46  47  48  49  50
*/
public class JapaneseAlphabeticalOrderFragmentActivity extends MyFirstActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_japanese_alphabetical_order_fragment);

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .replace(android.R.id.content,  new JapaneseAlphabeticalOrderFragment())
                    .commit();
        }
        //レシーバをセットする.
        super.setReciver();
    }
    /**
     * Created by Shota Ito on 2015/1/1
     * setDummyTestTeacherMstメソッド
     * ダミーのテストデータをCSVから読み出し端末のローカルDBに格納します.
     */
    private void setDummyTestTeacherMst(){
        //
        String fileName = DummyStaffsMst.CSV_FILE_NAME_STAFFS_MST;
        CSVCtrl csv = new CSVCtrl();
        List<StringTokenizer> tokenizers =  csv.readCSV(this, fileName);

        for(int i = 0; i < tokenizers.size(); i++){
            StringTokenizer token = tokenizers.get(i);
            int count = 0;
            String[] items = new String[10];
            while(token.hasMoreTokens()) {
               String item = token.nextToken();
               items[count] = item;
               ++count;
            }
            StaffsMst mst = getItemByStaffId(items[0]);
            if(mst == null){
                //DBに登録されていないので登録する.
                StaffsMst item = new StaffsMst();
                item.staffId = items[0];
                item.registrationStatus = items[1];
                item.staffFamilyFriganaName = items[2];
                item.staffGivenFriganaName = items[3];
                item.staffName = items[4];
                item.teacherOrStaff = items[5];
                item.category = items[6];
                item.campusId = items[7];
                item.registerDateTime = items[8];
                item.lastUpdateTime = items[9];

                item.save();
            }
        }

    }

    /**
     * Created by Shota Ito on 2015/1/1
     * getItemByStaffIdメソッド
     * 端末のローカルDB内に同じIDがあるかをチェックする.
     */
    public static StaffsMst getItemByStaffId(String staffId) {
        return new Select()
                .from(StaffsMst.class)
                .where("STAFF_ID = ?", staffId)
                .executeSingle();
    }


    @Override
    public void onResume(){
        super.onResume();
    }

    /*@Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getFragmentManager();
        //fragmentManager.beginTransaction()
        //        .replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
        //        .commit();

        // update the main content by replacing fragments
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new JapaneseAlphabeticalOrderFragment())
                .commit();
    }*/

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
     * 最終更新日:2014/12/　　
     * doChangeKanaIndexSelectAcitivtyメソッド
     * 50音順で選択された文字をキーにSelectAcitivtyに遷移する.
     * @parm initial 教員苗字の頭文字
     */
    public void doChangeKanaIndexSelectAcitivty(int tapId){
        /*Intent intent = new Intent();
        intent.setClassName(Packcage.ONE_CUSHION, Packcage.SELECT_ACTIVITY);
        startActivity(intent);*/
        Intent intent = new Intent(this,KanaIndexSelectActivity.class);
        intent.putExtra(KanaIndexSelectActivity.SELECT_INITIAL, tapId);
        startActivity(intent);
        //
        finish();

        // 疑似スタックに詰める
        //ActivityStack.stackHistory(this);
    }

    /*@Override
    public void openNavigationDrawer(){
       //個々ではドロワーに何も表示させなくていいので何もしない.
    }*/

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
