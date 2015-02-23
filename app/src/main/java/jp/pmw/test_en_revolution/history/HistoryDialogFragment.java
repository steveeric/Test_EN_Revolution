package jp.pmw.test_en_revolution.history;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import jp.pmw.test_en_revolution.CustomDialogFragment;
import jp.pmw.test_en_revolution.R;
import jp.pmw.test_en_revolution.confirm_class_plan.Student;

/**
 * Created by scr on 2015/01/07.
 * 個人履歴を確認するフラグメントになります.
 */
    public class HistoryDialogFragment extends MyDialogFragment {

    public static final String HISTORY_DIALO_FRAGMENT = "historyDialogFragment";
    private static double WIDTH_SCALE = 0.8;
    private static double HEIGHT_SCALE = 0.8;
    private Student student;

    private Button closeBtn;
    private ListView historyListView;
    private HistoryCustomAdapter adapter;

    /**
     * ファクトリーメソッド
     */
    public static HistoryDialogFragment newInstance(/*String title, String message, int type*/) {
        HistoryDialogFragment instance = new HistoryDialogFragment();
        return instance;
    }

    private List<History> getDummyHistory(){
        List<History> histories = new ArrayList<History>();
        histories.add(new History(1,"2014年9月8日",1,0));
        histories.add(new History(2,"2014年9月15日",1,0));
        histories.add(new History(3,"2014年9月22日",1,0));
        histories.add(new History(4,"2014年9月29日",1,0));
        histories.add(new History(5,"2014年10月6日",1,0));
        histories.add(new History(6,"2014年10月13日",0,0));
        histories.add(new History(7,"2014年10月20日",1,1));
        histories.add(new History(8,"2014年10月27日",1,0));
        histories.add(new History(9,"2014年11月3日",1,0));
        histories.add(new History(10,"2014年11月10日",1,0));
        histories.add(new History(11,"2014年11月17日",1,0));
        histories.add(new History(12,"2014年11月24日",1,1));
        histories.add(new History(13,"2014年12月1日",1,0));
        histories.add(new History(14,"2014年12月8日",1,0));
        histories.add(new History(15,"2015年1月5日",1,0));
        return histories;
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

        student = (Student)getArguments().getSerializable(CustomDialogFragment.ATTENDANCE_STUDENT_INFO);

        Dialog dialog = new Dialog(getActivity());
        // タイトル非表示
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        // フルスクリーン
        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        dialog.setContentView(R.layout.dialog_custom_history);
        // 背景を透明にする
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //ダイアログリサイズ
        resizeDialog(dialog,WIDTH_SCALE,HEIGHT_SCALE);

        TextView orijinalIdTV = getTextView(dialog,R.id.dialog_custom_history_orijinal_studentid_textView);
        TextView fullNameTV = getTextView(dialog,R.id.dialog_custom_history_full_name_textView);

        orijinalIdTV.setText(student.getOriginalstudentId());
        fullNameTV.setText(student.getFullName());

        /*ListView*/
        historyListView = getListView(dialog,R.id.dialog_custom_history_listView);
        List<History> histories = getDummyHistory();
        adapter = new HistoryCustomAdapter(this.getActivity(),R.layout.row_history_item,histories);
        historyListView.setAdapter(adapter);

        /*ボタン*/
        closeBtn = getButton(dialog,R.id.dialog_custom_history_close_button);
        closeBtn.setOnClickListener(closeBtnListener);

        return dialog;
    }


    private View.OnClickListener closeBtnListener = new View.OnClickListener() {
        public void onClick(View v) {
            switch(v.getId()) {
                case R.id.dialog_custom_history_close_button:
                    //とじる
                    dismiss();
                    break;
            }
        }
    };

}
