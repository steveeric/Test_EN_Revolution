package jp.pmw.test_en_revolution;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by scr on 2014/12/23.
 */
public class MyMainFragment extends Fragment {
    public static final long RELOAD_INTERVAL = 3000;
    //  再度ネットワークから学生情報を取得フラグ
    public boolean reGetStudentFlag = false;

    public FrameLayout loadingFrameLayout;
    //
    public Handler mHandler = new Handler(Looper.getMainLooper());

    public LinearLayout contentLayout;
    //  学生の詳細を確認するダイアログフラグメント
    public StudentInfoCustomDialog studentInfoDialogFragnemt;

    //フラグメント再読み込み用のタイマー
    private Timer loadTimer;
    public MainActivity getMainActivity(){
        return (MainActivity)this.getActivity();
    }
    /**
     * Created by scr on 2016/1/31.
     * showWaitFragmentメソッド
     * フラグメント表示を待機させる
     */
    //abstract void showWaitFragment();
    /**
     * Created by scr on 2015/3/12.
     * loadFragmentメソッド
     * フラグメント自体を再読み込みします.
     */
    public void loadFragment(){
        //タイマー生成.
        if(this.loadTimer == null){
            this.loadTimer = new Timer();
            this.loadTimer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //再オンレジューム
                            onResume();
                        }
                    });
                }
            }, 0, RELOAD_INTERVAL);
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public void initFragment(){
        if(this.loadingFrameLayout == null){
            this.loadingFrameLayout = (FrameLayout)getActivity().findViewById(R.id.loadingLayout);
        }
        if(this.contentLayout == null){
            this.contentLayout      = (LinearLayout)getActivity().findViewById(R.id.content_layout);
        }
        this.loadingFrameLayout.setVisibility(View.VISIBLE);
        this.contentLayout.setVisibility(View.GONE);
    }

    @Override
    public void onPause(){
        super.onPause();
        if(studentInfoDialogFragnemt != null){
            studentInfoDialogFragnemt.dismiss();
        }
    }

    @Override
    public void onResume(){
        super.onResume();
        //ドロワーの必要個所をオープンにする.
        MainActivity activity = (MainActivity)this.getActivity();
        if( activity != null ){
            activity.openNavigationDrawer();
        }
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        cancelTimer();
    }

    /**
     * Created by scr on 2016/1/31
     * switchContentScreenメソッド
     * 読み込み中...レイアウトを消し、
     * コンテンツ画面を表示する.
     */
    public void switchContentScreen(){
        this.loadingFrameLayout.setVisibility(View.GONE);
        this.contentLayout.setVisibility(View.VISIBLE);
    }


    /**
     * Created by scr on 2015/3/12.
     * cancelTimerメソッド
     * フラグメント自体を再読み込みします.
     */
    public void cancelTimer(){
        if(this.loadTimer != null){
            //フラグメント再読み込みを解除する.
            this.loadTimer.cancel();
            this.loadTimer = null;
        }
    }

}
