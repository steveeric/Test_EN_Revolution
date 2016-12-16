package jp.pmw.test_en_revolution.dialog;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.DialogFragment;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import jp.pmw.test_en_revolution.AttendeeFragment;
import jp.pmw.test_en_revolution.MainActivity;
import jp.pmw.test_en_revolution.R;
import jp.pmw.test_en_revolution.attendee.AttendanceChangeStatusAdapter;

/**
 * Created by si on 2016/12/15.
 * 赤外線で出席認定にならなかった学生を表示するダイアログです.
 */

public class AttendanceChangeStatusDialogFragment extends DialogFragment {
    public static final String ATTENDANCE_CHANGE_STATUS_DIALOG_FRAGMENT = "attendance_change_status_dialog_fragment";
    public static final String SAME_CLASS_NUMBER = "same_class_number";
    private static AttendanceChangeStatusDialogFragment instance = null;
    public static AttendanceChangeStatusDialogFragment newInstance() {
        if (instance == null) {
            instance = new AttendanceChangeStatusDialogFragment();
        } else {
            //  2重起動防止用
            instance.dismiss();
            return null;
        }
        return instance;
    }
    @Override
    public void onDetach() {
        super.onDetach();
        dismiss();
        this.instance = null;
    }

    public AttendeeFragment mAttendeeFragment;
    Dialog mDialog;
    ProgressBar mProgressBar;
    LinearLayout mLinearLayout;
    GridView mAbsentGv;
    Button mCloseBtn;
    AttendanceChangeStatusAdapter mAttendanceChangeStatusAdapter;
    Handler mHandler = new Handler(Looper.myLooper());
    public boolean oneClose = false;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        init();
        return mDialog;
    }
    /**
     *  Created by si on 2016/12/15.
     *  initメソッド
     *  初期動作
     **/
    void init(){
        //
        String sameClassNumber = getArguments().getString(SAME_CLASS_NUMBER);
        mDialog = new Dialog(getActivity());
        //
        setLayout();
        //
        setSizeDialogFragment();
        //
        new AttendanceChangeStatusAsyncTask(this, sameClassNumber).execute();
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
            if( mAttendeeFragment != null ){
                //  受講者一覧を再描画する
                mAttendeeFragment.initFragment();
                MainActivity ma = mAttendeeFragment.getMainActivity();
                ma.getClassObject().setStudentObject( null );
                mAttendeeFragment.showWaitFragment();
                mAttendeeFragment.reGetStudentFlag = true;
                oneClose = true;
            }
            dismiss();
        }
    }
    public void setItme(final AttendanceChangeStatus[] acss){
        if( getActivity() != null ) {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    //
                    mLinearLayout = (LinearLayout) mDialog.findViewById(R.id.dialog_custom_attendance_change_status_ll);
                    //
                    mAbsentGv = (GridView) mDialog.findViewById(R.id.dialog_custom_attendance_change_status_gv);
                    //
                    mAttendanceChangeStatusAdapter = getAdapter(acss);
                    //
                    mAbsentGv.setAdapter(mAttendanceChangeStatusAdapter);
                    //
                    mLinearLayout.setVisibility(View.VISIBLE);
                    //
                    mProgressBar.setVisibility(View.GONE);
                }
            });
        }
    }

    /**
     *  Created by si on 2016/12/15.
     *  setAdapterメソッド
     *  アダプターをセットする
     **/
    AttendanceChangeStatusAdapter getAdapter(AttendanceChangeStatus[] acss){
        AttendanceChangeStatusAdapter adapter =
                 new AttendanceChangeStatusAdapter(
                getActivity(),
                R.layout.row_attendance_change_status,
                         acss);
        return adapter;
    }

}
