package jp.pmw.test_en_revolution.history;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.SearchView;

import java.util.List;

import jp.pmw.test_en_revolution.CustomDialogFragment;
import jp.pmw.test_en_revolution.R;
import jp.pmw.test_en_revolution.attendee.RosterCustomAdapter_1;
import jp.pmw.test_en_revolution.confirm_class_plan.Roster;
import jp.pmw.test_en_revolution.confirm_class_plan.Student;

/**
 * Created by scr on 2015/01/07.
 */
public class SearchHistoryDialogFragment extends MyDialogFragment {

    public static final String ROSTER = "roster";

    public static final String SEARCH_HISTORY_DIALOG_FRAGMENT = "searchHistoryDialogFragment";
    private static double WIDTH_SCALE = 0.8;
    private static double HEIGHT_SCALE = 0.8;
    private static int SEARCH_HISTORY_GRIDVIEW_COLUMNS_COUNT = 2;

    private Button closeBtn;
    private GridView gridView;
    private SearchView searchView;
    private RosterCustomAdapter_1 adapter;

    private String searchWord;

    /**
     * ファクトリーメソッド
     */
    public static SearchHistoryDialogFragment newInstance(/*String title, String message, int type*/) {
        SearchHistoryDialogFragment instance = new SearchHistoryDialogFragment();
        return instance;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Roster roster = (Roster)getArguments().getSerializable(ROSTER);
        //受講生一覧取得
        List<Student> studentList = roster.getRosterList();

        adapter = new RosterCustomAdapter_1(this.getActivity(),0,studentList,RosterCustomAdapter_1.ONLY_STUDENT_NAME_LAYOUT);

        Dialog dialog = new Dialog(getActivity());
        // タイトル非表示
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        // フルスクリーン
        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        dialog.setContentView(R.layout.dialog_custom_search_history);
        // 背景を透明にする
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //ダイアログリサイズ
        resizeDialog(dialog,WIDTH_SCALE,HEIGHT_SCALE);

        /*検索windows*/
        /*searchView = getSerchView(dialog,R.id.dialog_custom_search_history_student_searchView);  // 虫眼鏡アイコンを最初表示するかの設定
        searchView.setIconifiedByDefault(true);

        // Submitボタンを表示するかどうか
        searchView.setSubmitButtonEnabled(false);*/

        /*学生一覧GridView*/
        gridView = getGridView(dialog,R.id.dialog_custom_search_history_student_gridView);
        gridView.setNumColumns(SEARCH_HISTORY_GRIDVIEW_COLUMNS_COUNT);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // 選択アイテムを取得
                GridView gridView = (GridView) parent;
                Student tapStudent = (Student) gridView.getItemAtPosition(position);
                moveToHistoryDialogFragment(tapStudent);
            }
        });


        /*「とじる」ボタン*/
        closeBtn = getButton(dialog,R.id.dialog_custom_search_history_close_button);
        closeBtn.setOnClickListener(closeBtnListener);

        return dialog;
    }


    private View.OnClickListener closeBtnListener = new View.OnClickListener() {
        public void onClick(View v) {
            switch(v.getId()) {
                case R.id.dialog_custom_search_history_close_button:
                    //とじる
                    dismiss();
                    break;
            }
        }
    };
    /**
     * Created by scr on 2015/1/7
     * moveToHistoryDialogFragmentメソッド
     * 個人履歴フラグメントへ移動します.
     * @param student 選択された学生
     */
    private void moveToHistoryDialogFragment(Student student){
        HistoryDialogFragment customDialog = HistoryDialogFragment.newInstance();
        customDialog.setTargetFragment(SearchHistoryDialogFragment.this,0);
        Bundle bundle = new Bundle();
        bundle.putSerializable(CustomDialogFragment.ATTENDANCE_STUDENT_INFO,student);
        customDialog.setArguments(bundle);
        customDialog.show(this.getActivity().getSupportFragmentManager(), HistoryDialogFragment.HISTORY_DIALO_FRAGMENT);
    }

    private SearchView.OnQueryTextListener onQueryTextListener = new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String searchWord) {
            // SubmitボタンorEnterKeyを押されたら呼び出されるメソッド
            return false;//self.setSearchWord(searchWord);
        }

        @Override
        public boolean onQueryTextChange(String newText) {
            // 入力される度に呼び出される
            return false;
        }
    };

    private boolean setSearchWord(String searchWord) {
        ActionBar actionBar = ((ActionBarActivity)this.getActivity()).getSupportActionBar();
        actionBar.setTitle(searchWord);
        actionBar.setDisplayShowTitleEnabled(true);
        if (searchWord != null && !searchWord.equals("")) {
            // searchWordがあることを確認
            this.searchWord = searchWord;
        }
        // 虫眼鏡アイコンを隠す
        this.searchView.setIconified(false);
        // SearchViewを隠す
        this.searchView.onActionViewCollapsed();
        // Focusを外す
        this.searchView.clearFocus();
        return false;
    }

}
