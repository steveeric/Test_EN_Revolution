package jp.pmw.test_en_revolution.dialog;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.DialogFragment;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import jp.pmw.test_en_revolution.R;

/**
 * Created by si on 2016/12/16.
 * 在室確認ACKがなかった学生を
 * SonoRIs MTから手動でACKがあったことにするダイアログを表示します.
 */

public class ReAttendanceChangeStatusDialogFragment  extends DialogFragment {
    public static final int CANCEL = 0;
    public static final int REQUEST = 1;
    public static final String RE_ATTENDANCE_CHANGE_STATUS_DIALOG_FRAGMENT = "re_attendance_change_status_dialog_fragment";
    public static final String SAME_CLASS_NUMBER = "same_class_number";

    private static final String TITLE = "在室確認で学生のSonoRIsから反応がなかったリスト";
    private static ReAttendanceChangeStatusDialogFragment instance = null;
    public static ReAttendanceChangeStatusDialogFragment newInstance() {
        if (instance == null) {
            instance = new ReAttendanceChangeStatusDialogFragment();
        }
        return instance;
    }
    @Override
    public void onDetach() {
        super.onDetach();
        if( !foreverNotBrowsing ){
            browsing = false;
        }
        dismiss();
    }
    public boolean browsing = false;
    //  永久的にこのダイアログを出したくないかどうか
    //  false:出て欲しい、true:出したくない
    boolean foreverNotBrowsing = false;
    String mSameClassNumber;
    Dialog mDialog;
    ProgressBar mProgressBar;
    LinearLayout mLinearLayout;
    TextView mTitleTextView;
    GridView mNackReAttendanceGv;
    Button mCloseBtn;
    ReAttendanceChangeStatusAdapter mReAttendanceChangeStatusAdapter;
    public Handler mHandler;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        browsing = true;
        init();
        return mDialog;
    }
    /**
     *  Created by si on 2016/12/16.
     *  initBrowsingメソッド
     *  閲覧可能状態を初期化する.
     **/
    public void initBrowsing(){
        this.browsing = false;
        this.foreverNotBrowsing = false;
    }
    /**
     *  Created by si on 2016/12/15.
     *  initメソッド
     *  初期動作
     **/
    void init(){
        //
        mSameClassNumber = getArguments().getString(SAME_CLASS_NUMBER);
        mDialog = new Dialog(getActivity());
        //
        setLayout();
        //
        setSizeDialogFragment();
        //
        new ReAttendanceChangeStatusAsyncTask(this, mSameClassNumber).execute();
    }
    /**
     *  Created by si on 2016/12/15.
     *  setLayoutメソッド
     *  レイアウトをセットする
     **/
    void setLayout(){
        // タイトル非表示
        mDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        // フルスクリーン
        mDialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        //  レイアウトセット
        mDialog.setContentView(R.layout.dialog_custom_attendance_change_status);
        // 背景を透明にする
        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //
        mProgressBar = (ProgressBar) mDialog.findViewById(R.id.dialog_custom_attendance_change_status_loding_pb);
        //
        mCloseBtn = (Button) mDialog.findViewById(R.id.dialog_custom_attendance_change_status_close_btn);
        mCloseBtn.setOnClickListener(new CloseBtnListener());
    }
    /**
     *  Created by si on 2016/06/16.
     *  setDialogFragmentメソッド
     *  ダイアログのサイズをセットする
     **/
    void setSizeDialogFragment(){
        WindowManager.LayoutParams lp = mDialog.getWindow().getAttributes();
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int dialogWidth = (int) (metrics.widthPixels * 1);
        int dialogHeight = (int) (metrics.heightPixels * 1);
        lp.width = dialogWidth;
        lp.height = dialogHeight;
        mDialog.getWindow().setAttributes(lp);
    }
    //  閉じるボタン
    public class CloseBtnListener implements View.OnClickListener {
        @Override
        public void onClick(View v){
            cloaseProcess();
        }
    }
    public void cloaseProcess(){
        new ReAttendanceBulkChangeEndDateTimeAsyncTask( mSameClassNumber ).execute();
        foreverNotBrowsing = true;
        dismiss();
    }

    public void setItme(final ReAttendanceNackChangeStatus[] rencs){
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mLinearLayout = (LinearLayout) mDialog.findViewById(R.id.dialog_custom_attendance_change_status_ll);
                //
                mTitleTextView = (TextView) mDialog.findViewById(R.id.dialog_custom_attendance_change_status_tv);
                mTitleTextView.setText( TITLE );
                //
                mNackReAttendanceGv = (GridView) mDialog.findViewById(R.id.dialog_custom_attendance_change_status_gv);
                //
                mReAttendanceChangeStatusAdapter = getAdapter(rencs);
                //
                mNackReAttendanceGv.setAdapter(mReAttendanceChangeStatusAdapter);
                //
                mLinearLayout.setVisibility(View.VISIBLE);
                //
                mProgressBar.setVisibility(View.GONE);
            }
        });
    }

    /**
     *  Created by si on 2016/12/15.
     *  setAdapterメソッド
     *  アダプターをセットする
     **/
    ReAttendanceChangeStatusAdapter getAdapter(ReAttendanceNackChangeStatus[] rencs){
        ReAttendanceChangeStatusAdapter adapter =
                new ReAttendanceChangeStatusAdapter(
                        getActivity(),
                        R.layout.row_attendance_change_status,
                        rencs);
        return adapter;
    }

}
