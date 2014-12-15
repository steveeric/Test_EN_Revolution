package jp.pmw.test_en_revolution.one_cushion.select_teacher;

import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.json.JSONArray;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;
import jp.pmw.test_en_revolution.AppController;
import jp.pmw.test_en_revolution.MainActivity;
import jp.pmw.test_en_revolution.MyFragmentActivity;
import jp.pmw.test_en_revolution.R;
import jp.pmw.test_en_revolution.common.CommonDialogFragment;
import jp.pmw.test_en_revolution.common.CommonDialogInterface;
import jp.pmw.test_en_revolution.config.URL;
import jp.pmw.test_en_revolution.drawer.NavigationDrawerFragment;

/**
 *
 * Created by scr on 2014/12/12～.
 * 最終更新日:2014/12/14
 * JapaneseAlphabeticalOrderFragmentActivityで選択された「かな」を引数に
 * 教員名を検索してくるActivityです.
 * @author Shota Ito
 * @version 1.0
 */
public class KanaIndexSelectActivity extends MyFragmentActivity
        implements CommonDialogInterface.onClickListener
    {
    public static final String SELECT_INITIAL = "SELECT_INITIAL";
    private static final String CONFIRM_TAP_CONTENT_ALERT_DIALOG = "doConfirmTapContentAlertDialog";

    private Button mDrawaNavigationButton;

    //JapaneseAlphabeticalOrderFragmentで選択された頭文字を保持する
    int mTapId=-1;
    //選択された頭文字を返す.
    public int getTapId(){return this.mTapId;}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kana_index_select);
        Intent intent = getIntent();
        if(intent!=null){
            mTapId = intent.getIntExtra(SELECT_INITIAL, -1);
        }

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.navigation_drawer);
        if(mNavigationDrawerFragment!=null) {
            // Set up the drawer.
            mNavigationDrawerFragment.setUp(
                    R.id.navigation_drawer,
                    (DrawerLayout) findViewById(R.id.drawer_layout));
        }
        /*if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }*/
    }

    @Override
    public void openNavigationDrawer(){
        if(mDrawaNavigationButton==null){
            mDrawaNavigationButton = (Button)this.findViewById(R.id.fragment_navigation_drawer_return_top_button);
            mDrawaNavigationButton.setVisibility(View.VISIBLE);
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
                .add(R.id.container, new PlaceholderFragment())
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //agetMenuInflater().inflate(R.menu.menu_kana_index_select, menu);
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
     * Created by scr on 2014/12/14.
     * doConfirmTapContentメソッド
     * タップした教員名や授業の確認を促すアラートダイアログを出す.
     */
    public void doConfirmTapContentAlertDialog(Teacher item) {
        Bundle args = new Bundle();
        args.putInt(CommonDialogFragment.FIELD_TITLE, R.string.confirm_select_content);
        args.putStringArray(CommonDialogFragment.FIELD_LIST_ITEMS_STRING, new String[]{"教員名 : " + item.getName()});
        args.putInt(CommonDialogFragment.FIELD_LABEL_NEGATIVE, R.string.no);
        args.putInt(CommonDialogFragment.FIELD_LABEL_POSITIVE, R.string.yes);

        CommonDialogFragment dialogFragment = new CommonDialogFragment();
        dialogFragment.setArguments(args);
        dialogFragment.show(getSupportFragmentManager(), CONFIRM_TAP_CONTENT_ALERT_DIALOG);
    }


    @Override
    public void onDialogButtonClick(String tag, Dialog dialog, int which) {
        if (CONFIRM_TAP_CONTENT_ALERT_DIALOG.equals(tag)) {
            // CONFIRM_TAP_CONTENT_ALERT_DIALOGのクリック
            if (DialogInterface.BUTTON_POSITIVE == which) {
                // ok ボタンがおされた
                //MainActivityに遷移する.
                moveToMainActivity();
            } else if (DialogInterface.BUTTON_NEGATIVE == which) {
                // cancel ボタンがおされた
                //クローズ
                dialog.dismiss();
            }
        }
    }

    /**
     * Created by scr on 2014/12/14.
     * moveToMainActivityメソッド
     * 出席状況などを確認するメインとなるMainActivityに画面を遷移する.
     *
     * ※本来は、講義IDをMainActivityに投げる必要がある!
     */
    public void moveToMainActivity(){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }

    /**
         * A placeholder fragment containing a simple view.
         */
    public class PlaceholderFragment extends Fragment {
        private TextView teacherFamilyNameInitialTextView;
        private TextView teacherFamilyNameNoRegisterTextView;
        private ProgressBar teacherFamilyNameLoadProgressBar;
        private ListView teacherFamilyNameListListView;

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_kana_index_select, container, false);
            return rootView;
        }
        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
            teacherFamilyNameInitialTextView = (TextView)this.getActivity().findViewById(R.id.teacher_family_name_initial_textView);
            teacherFamilyNameNoRegisterTextView = (TextView)this.getActivity().findViewById(R.id.teacher_family_name_no_register_textView);
            teacherFamilyNameLoadProgressBar = (ProgressBar)this.getActivity().findViewById(R.id.teacher_family_name_load_progressBar);
            teacherFamilyNameListListView = (ListView)this.getActivity().findViewById(R.id.teacher_family_name_initial_list_listView);

            //ドロワーの必要個所をオープンにする.
            openNavigationDrawer();
        }

        @Override
        public void onResume(){
            super.onResume();
            //JapaneseAlphabeticalOrderFragmentActivityより渡された値がNULLでなければ.
            KanaIndexSelectActivity activity = (KanaIndexSelectActivity)this.getActivity();
            if(activity.getTapId() != -1){
                int tapId = activity.getTapId();
                doNetWorkDBAccessSearchTeacherFamilyName(tapId);
                String selectInitial = getSelectInitial(tapId);
                teacherFamilyNameInitialTextView.setText(selectInitial+"行");
            }
        }

        /**
         * Created by scr on 2014/12/14.
         * getSelectInitialメソッド
         * タップされた頭文字のインデックス番号から日本語をわりだす.
         * @parm id タップされたボタンのID
         * @return tapInitialId タップされたボタンIDをひらがなに
         */
        private String getSelectInitial(int id) {
            //タップされたボタンのかなインデックスを取得
            String tapInitialId = null;
            //カウンター
            int counter = 0;
            for (int i = 0; i < JapaneseAlphabeticalOrder.ALPHABET_BLOCK.length; i++) {
                for (int j = 0; j < JapaneseAlphabeticalOrder.ALPHABET_BLOCK[i].length; j++) {
                    ++counter;
                    //もしカウンターとタップしたボタンのＩＤがなじなら
                    //選択された文字を取得する.
                    if (id == counter) {
                        tapInitialId = JapaneseAlphabeticalOrder.ALPHABET_BLOCK[i][j];
                        break;
                    }
                }
            }
            return tapInitialId;
        }

        /**
         * Created by scr on 2014/12/14.
         * doNetWorkDBAccessSearchTeacherFamilyNameメソッド
         * 選択された頭文字から条件に合う教員リストをネットワークのDBから取得する.
         * @parm initial 選択された教員苗字
         */
        private void doNetWorkDBAccessSearchTeacherFamilyName(int tapId){
            //
            String url = URL.TEACHER_FAMILY_NAME_INITIAL+"/"+(tapId - 1);
            JsonArrayRequest request = new JsonArrayRequest(
                    url,
                    new Response.Listener<JSONArray>() {
                        @Override public void onResponse(JSONArray response) {
                            // レスポンス受け取り時の処理...
                            jsonArrayParser(response);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override public void onErrorResponse(VolleyError error) {
                            // エラー時の処理...
                            Toast.makeText(getActivity(), "Unable to fetch data: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
            AppController.getInstance(this.getActivity()).getRequestQueue().add(request);
        }

        /**
         * Created by scr on 2014/12/14.
         * jsonArrayParserメソッド
         * ネットワークDBから取得した教員リストのJSONを解析する.
         */
        public void jsonArrayParser(JSONArray response){
            Gson mygson = new Gson();
            //List<Teacher> teachers = new ArrayList<Teacher>();
            Type collectionType = new TypeToken<Collection<Teacher>>(){}.getType();
            List<Teacher> teachers = mygson.fromJson(response.toString(),collectionType);
            if(teachers.size() > 0){
                CustomAdapter adapter = new CustomAdapter(this.getActivity(),0,teachers);
                teacherFamilyNameListListView.setAdapter(adapter);
                teacherFamilyNameListListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> parent,
                                            View view, int pos, long id) {
                        // 選択アイテムを取得
                        ListView listView = (ListView)parent;
                        Teacher item = (Teacher)listView.getItemAtPosition(pos);
                        doConfirmTapContentAlertDialog(item);
                    }
                });
                //正常に完了
                processSucess();
            }else{
                //正常に終えたが名前が一件もなかった.
                processSucessNoName();
            }
        }
        /**
         * Created by scr on 2014/12/14.
         * processSucessメソッド
         * 処理が正常に完了.
         */
        private void processSucess(){
            this.teacherFamilyNameLoadProgressBar.setVisibility(View.GONE);
            this.teacherFamilyNameListListView.setVisibility(View.VISIBLE);
        }

        /**
         * Created by scr on 2014/12/14.
         * processSucessNoName
         * 正常に処理を終えたが名前がなかった..
         */
        private void processSucessNoName(){
            this.teacherFamilyNameLoadProgressBar.setVisibility(View.GONE);
            this.teacherFamilyNameNoRegisterTextView.setVisibility(View.VISIBLE);
        }

    }
}
