package jp.pmw.test_en_revolution.one_cushion.select_teacher;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.text.Spannable;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.activeandroid.query.Select;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import jp.pmw.test_en_revolution.AppController;
import jp.pmw.test_en_revolution.MainActivity;
import jp.pmw.test_en_revolution.MyActivity;
import jp.pmw.test_en_revolution.R;
import jp.pmw.test_en_revolution.config.URL;
import jp.pmw.test_en_revolution.confirm_class_plan.ConfirmClassPlanActivity;
import jp.pmw.test_en_revolution.one_cushion.select_teacher.dummy.DummyStaffsMst;
import jp.pmw.test_en_revolution.one_cushion.select_teacher.dummy.StaffsMst;

/**
 *
 * Created by scr on 2014/12/12～.
 * 最終更新日:2014/12/14
 * JapaneseAlphabeticalOrderFragmentActivityで選択された「かな」を引数に
 * 教員名を検索してくるActivityです.
 * @author Shota Ito
 * @version 1.0
 */
public class KanaIndexSelectActivity extends MyActivity
    {
    public static final String SELECT_INITIAL = "SELECT_INITIAL";
    private static final String CONFIRM_TAP_CONTENT_ALERT_DIALOG = "doConfirmTapContentAlertDialog";
    private static final String SHOW_POPUP_CONFIRM_CLASS_PLAN  = "showPopupConfirmClassPlan";

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

        /*mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.navigation_drawer);

        if(mNavigationDrawerFragment!=null) {
            // Set up the drawer.
            mNavigationDrawerFragment.setUp(
                    R.id.navigation_drawer,
                    (DrawerLayout) findViewById(R.id.drawer_layout));
        }*/

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }

   /* @Override
    public void openNavigationDrawer(){
        if(mDrawaNavigationButton==null){
            mDrawaNavigationButton = (Button)this.findViewById(R.id.fragment_navigation_drawer_return_top_button);
            mDrawaNavigationButton.setVisibility(View.VISIBLE);
        }
    }*/


    /*@Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
                .commit();

        // update the main content by replacing fragments
        getFragmentManager().beginTransaction()
                .add(R.id.container, new PlaceholderFragment())
                .commit();
    }*/

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
         * Created by scr on 2014/12/17.
         * doTeacherConfirmTapSelectContent
         * タップした教員名で間違いがないかを画面上に表示するメソッド.
         * @param  selectTeacher タップされた教員情報
         */
        public void doTeacherConfirmTapSelectContent(Teacher selectTeacher) {
           //TODO:タップされた教員情報を画面上に出力する.

        }


    /**
     * Created by scr on 2014/12/14.
     * doConfirmTapContentメソッド
     * タップした教員名や授業の確認を促すアラートダイアログを出す.
     */
    /*public void doConfirmTapContentAlertDialog(Teacher item) {
        Bundle args = new Bundle();
        args.putInt(CommonDialogFragment.FIELD_TITLE, R.string.confirm_select_content);
        args.putStringArray(CommonDialogFragment.FIELD_LIST_ITEMS_STRING, new String[]{"教員名 : " + item.getName()});
        args.putInt(CommonDialogFragment.FIELD_LABEL_NEGATIVE, R.string.no);
        args.putInt(CommonDialogFragment.FIELD_LABEL_POSITIVE, R.string.yes);

        CommonDialogFragment dialogFragment = new CommonDialogFragment();
        dialogFragment.setArguments(args);
        dialogFragment.show(getSupportFragmentManager(), CONFIRM_TAP_CONTENT_ALERT_DIALOG);
    }*/

        /**
         * Created by scr on 2014/12/16.
         * showPopupConfirmClassPlanメソッド
         * 選択された教員の最寄りの講義情報(いつどこでどの科目の講義を行うかを表示する).
         * @param  teachr 教員ID,教員名,教員が開講する最寄りの講義情報をポップアップで表示する.
         */
        /*public void showPopupConfirmClassPlan(Teacher teachr) {
            Bundle args = new Bundle();
            args.putInt(CommonDialogFragment.FIELD_TITLE, R.string.confirm_select_content);
            // 自分で定義したレイアウト
            args.putInt(CommonDialogFragment.FIELD_LAYOUT, R.layout.dialog_prof);
            //「はい」ボタン
            args.putInt(CommonDialogFragment.FIELD_LABEL_POSITIVE, R.string.yes);
            args.putInt(CommonDialogFragment.FIELD_LABEL_NEGATIVE, R.string.no);
            //「いいえ」ボタン
            CommonDialogFragment dialogFragment = new CommonDialogFragment();
            dialogFragment.setArguments(args);
            dialogFragment.show(getSupportFragmentManager(), SHOW_POPUP_CONFIRM_CLASS_PLAN);
        }*/


    /*@Override
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
    }*/

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
        //private static final String STR1 = "選択名 : ";

        //(かな)行を選択するものが詰め込まれているレイアウト.
        private LinearLayout kanaIndexSelectBelongToTheInitialNameLinearLayout;
        private TextView teacherFamilyNameInitialTextView;
        private TextView teacherFamilyNameNoRegisterTextView;

        private TextView teacherFamilyNameLoadProgressBar;
        //private ProgressBar teacherFamilyNameLoadProgressBar;
        private ListView teacherFamilyNameListListView;

        //選択された教員情報を確認するレイアウト
        private LinearLayout kanaIndexSelectSelectTeacherConfirmLinearLayout;
        //選択された教員名を入れる.
        private TextView kanaIndexSelectSelectTeacherNameTextView;
        //いいえボタン(選択教員を間違えた)
        private Button kanaIndexSelectConfirmNegativeButton;
        //はいボタン(教員名が正しい.)
        private Button kanaIndexSelectConfirmPositiveButton;

        //取得失敗レイアウト
        private LinearLayout failedLinearLayout;
        private Button regainButton;

        //選択された教員名
        private Teacher selectTeacher;
        public Teacher getTeacher(){return this.selectTeacher;}


        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            //View rootView = inflater.inflate(R.layout.fragment_kana_index_select, container, false);
            View rootView = inflater.inflate(R.layout.fragment_kana_index_select_1, container, false);
            return rootView;
        }
        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
            this.kanaIndexSelectBelongToTheInitialNameLinearLayout = (LinearLayout)this.getActivity().findViewById(R.id.kana_index_select_belong_to_the_initial_name_linearLayout);
            teacherFamilyNameInitialTextView = (TextView)this.getActivity().findViewById(R.id.teacher_family_name_initial_textView);
            teacherFamilyNameNoRegisterTextView = (TextView)this.getActivity().findViewById(R.id.teacher_family_name_no_register_textView);

            /*ロード処理*/
            teacherFamilyNameLoadProgressBar = (TextView)this.getActivity().findViewById(R.id.teacher_family_name_load_textView);
            //teacherFamilyNameLoadProgressBar = (ProgressBar)this.getActivity().findViewById(R.id.teacher_family_name_load_progressBar);

            teacherFamilyNameListListView = (ListView)this.getActivity().findViewById(R.id.teacher_family_name_initial_list_listView);
            teacherFamilyNameListListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent,
                                        View view, int pos, long id) {
                    // 選択アイテムを取得
                    ListView listView = (ListView)parent;
                    Teacher item = (Teacher)listView.getItemAtPosition(pos);

                    //選択した教員情報をアラートダイヤログで確認.
                    //doConfirmTapContentAlertDialog(item);
                    //選択した教員情報を画面上で確認
                    doSelectTeacherConfirmScreen(item);
                }
            });

            this.kanaIndexSelectSelectTeacherConfirmLinearLayout = (LinearLayout)this.getActivity().findViewById(R.id.kana_index_select_select_teacher_confirm_linearLayout);
            this.kanaIndexSelectSelectTeacherNameTextView = (TextView)this.getActivity().findViewById(R.id.kana_index_select_select_teacher_name_textView);
            this.kanaIndexSelectConfirmNegativeButton = (Button)this.getActivity().findViewById(R.id.kana_index_select_select_negative_button);
            this.kanaIndexSelectConfirmNegativeButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    //起動画面に戻る
                    returnStartUpScreen();
                }
            });
            this.kanaIndexSelectConfirmPositiveButton = (Button)this.getActivity().findViewById(R.id.kana_index_select_select_positive_button);
            this.kanaIndexSelectConfirmPositiveButton.setOnClickListener((new View.OnClickListener() {
                public void onClick(View v) {
                    //講義内容確認画面へ行く.
                    motoToActivityConfirmClassPlanActivity();
                }
            }));

            this.failedLinearLayout = (LinearLayout)this.getActivity().findViewById(R.id.kana_index_select_network_reaccess_linearLayout);
            this.regainButton = (Button)this.getActivity().findViewById(R.id.kana_index_select_regain_button);
            this.regainButton.setOnClickListener((new View.OnClickListener() {
                public void onClick(View v) {
                    //やり直す.
                    kanaIndexSelectBelongToTheInitialNameLinearLayout.setVisibility(View.VISIBLE);
                    failedLinearLayout.setVisibility(View.GONE);
                    onResume();
                }
            }));
            //ドロワーの必要個所をオープンにする.
            //openNavigationDrawer();
        }

        /**
         * Created by scr on 2014/12/17.
         * returnStartUpScreenメソッド
         * 起動画面に戻る.
         */
       /*public void returnStartUpScreen(){
            mNavigationDrawerFragment.moveToTopActivity();
        }*/
        public void returnStartUpScreen(){
            //50音表に戻る
            KanaIndexSelectActivity activity = (KanaIndexSelectActivity)this.getActivity();
            Intent intent = new Intent(activity,JapaneseAlphabeticalOrderFragmentActivity.class);
            startActivity(intent);
            activity.finish();
        }
        /**
         * Created by scr on 2014/12/17
         * motoToActivityConfirmClassPlanActivityメソッド
         * 講義内容を確認する画面へ飛ぶ
         */
        public void motoToActivityConfirmClassPlanActivity(){
            Intent intent = new Intent(this.getActivity(), ConfirmClassPlanActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra(ConfirmClassPlanActivity.TEACHER, this.getTeacher());
            intent.setAction(Intent.ACTION_VIEW);
            //
            startActivity(intent);
            //
            finish();
        }

        @Override
        public void onResume(){
            super.onResume();
            //JapaneseAlphabeticalOrderFragmentActivityより渡された値がNULLでなければ.

            //必ず画面を教員を羅列するListViewに戻す.
            mustReturnKanaIndexSelectFirstScreen();

            /**
             * ネットワークから教員名を取得する.(本番ではこちらを使用!)**/
            //getNetWorkDBAccessSearchTeacherFamilyName();

            /**
             * ローカルDBから取得する.(テストではこちらを使用!)
             * */
            dummyDataGetLocalDBSearchTeacherFamilyName();

        }

        /**
         * Created by scr on 2015/1/1.
         * dummyDataGetLocalDBSearchTeacherFamilyNameメソッド
         * 端末のローカルDBから教員名を取得する.
         **/
        private void dummyDataGetLocalDBSearchTeacherFamilyName(){
            KanaIndexSelectActivity activity = (KanaIndexSelectActivity)this.getActivity();
            /*ネットワーク~取得する場合*/
            if(activity.getTapId() != -1) {
                processReload();
                int tapId = activity.getTapId();
                String selectInitial = DummyStaffsMst.getTapKanaItem(--tapId);
                teacherFamilyNameInitialTextView.setText(selectInitial);

                List<StaffsMst> msts = getAllQualifiedPerson(selectInitial);
                //登録教員がいた場合
                if (msts.size() > 0) {
                    List<Teacher> teachers = new ArrayList<Teacher>();
                    for (StaffsMst i : msts) {
                        teachers.add(new Teacher(i.staffId, i.staffName, i.staffFamilyFriganaName, i.staffGivenFriganaName));
                    }
                    CustomAdapter adapter = new CustomAdapter(this.getActivity(), 0, teachers);
                    teacherFamilyNameListListView.setAdapter(adapter);

                    //正常に完了
                    processSucess();
                } else {
                    //正常に終えたが名前が一件もなかった.
                    processSucessNoName();
                }
            }
        }
        public List<StaffsMst> getAllQualifiedPerson(String familyKanaIndex) {
            return new Select().from(StaffsMst.class).where("STAFF_FAMILY_FURIGANA_NAME LIKE ?", familyKanaIndex+"_%")
                    .orderBy("STAFF_FAMILY_FURIGANA_NAME,STAFF_GIVEN_FURIGANA_NAME ASC")
                    .execute();
        }


        /**
         * Created by scr on 2015/1/1.
         * getNetWorkDBAccessSearchTeacherFamilyNameメソッド
         * ネットワークから教員名を取得する.
         **/
        private void getNetWorkDBAccessSearchTeacherFamilyName(){
            KanaIndexSelectActivity activity = (KanaIndexSelectActivity)this.getActivity();
            /*ネットワーク~取得する場合*/
            if(activity.getTapId() != -1){
                processReload();
                int tapId = activity.getTapId();
                doNetWorkDBAccessSearchTeacherFamilyName(tapId);
                String selectInitial = getSelectInitial(tapId);
                teacherFamilyNameInitialTextView.setText(selectInitial);
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
                            //Toast.makeText(getActivity(), "Unable to fetch data: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                            kanaIndexSelectBelongToTheInitialNameLinearLayout.setVisibility(View.GONE);
                            failedLinearLayout.setVisibility(View.VISIBLE);
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
                /*teacherFamilyNameListListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> parent,
                                            View view, int pos, long id) {
                        // 選択アイテムを取得
                        ListView listView = (ListView)parent;
                        Teacher item = (Teacher)listView.getItemAtPosition(pos);

                        //選択した教員情報をアラートダイヤログで確認.
                        //doConfirmTapContentAlertDialog(item);
                        //選択した教員情報を画面上で確認
                        doSelectTeacherConfirmScreen(item);
                    }
                });*/
                //正常に完了
                processSucess();
            }else{
                //正常に終えたが名前が一件もなかった.
                processSucessNoName();
            }
        }

        /**
         * Created by scr on 2014/12/17.
         * mustReturnKanaIndexSelectFirstScreenメソッド
         * 必ず、現在のActivity上のボトム画面に戻す.
         * @parm id タップされたボタンのID
         * @return tapInitialId タップされたボタンIDをひらがなに
         */
        private void mustReturnKanaIndexSelectFirstScreen(){
            //(カナ)から教員選択レイアウト
            kanaIndexSelectBelongToTheInitialNameLinearLayout.setVisibility(View.VISIBLE);
            //選択された教員内容を確認するレイアウト
            kanaIndexSelectSelectTeacherConfirmLinearLayout.setVisibility(View.GONE);
        }
        /**
         * Created by scr on 2014/12/17.
         * mustReturnKanaIndexSelectFirstScreenメソッド
         * 選択された教員情報を画面上に出力する.
         * @param  selectTeacher 選択された教員情報
         */
        private void doSelectTeacherConfirmScreen(Teacher selectTeacher){
            this.selectTeacher = selectTeacher;
            String teacherName = selectTeacher.getName();
            //
            KanaIndexSelectActivity activity = (KanaIndexSelectActivity)this.getActivity();

            //String str = activity.getString(R.string.kana_index_select_select_name);
            String text = /*str+*/teacherName;
            // 1. ファクトリーにおまかせ
            Spannable t = Spannable.Factory.getInstance().newSpannable(text);
            // 2. 下線オブジェクト(他にも種類ある)
            UnderlineSpan us = new UnderlineSpan();
            // 3. 装飾セット(装飾オブジェクト、開始位置、終了位置、装飾オブジェクト用？フラグ)
            t.setSpan(us, 0, text.length(), t.getSpanFlags(us));
            // 4. TextViewにセット
            this.kanaIndexSelectSelectTeacherNameTextView.setText(t, TextView.BufferType.SPANNABLE);

            //(カナ)から教員選択レイアウト
            kanaIndexSelectBelongToTheInitialNameLinearLayout.setVisibility(View.GONE);
            //選択された教員内容を確認するレイアウト
            kanaIndexSelectSelectTeacherConfirmLinearLayout.setVisibility(View.VISIBLE);
        }

        /**
         * Created by scr on 2014/12/16.
         * processReloadメソッド
         * 処理再読み込み.
         */
        private void processReload(){
            this.teacherFamilyNameLoadProgressBar.setVisibility(View.VISIBLE);
            this.teacherFamilyNameListListView.setVisibility(View.GONE);
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
