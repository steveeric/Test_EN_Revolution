package jp.pmw.test_en_revolution.confirm_class_plan;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Timer;

import jp.pmw.test_en_revolution.R;
import jp.pmw.test_en_revolution.one_cushion.select_teacher.Teacher;

public class ConfirmClassPlanActivity extends Activity {

    public static final String TEACHER = "teacher";
    //タイマーを1秒後とに
    private static final long TIMER_SECONDE_INTERVAL = 1000;
    //現在のActivityをfinishするまでの時間
    private static final int FNISH_SECONDE = 5;
    //前画面で選択された教員オブジェクトを保持する.
    public Teacher mTeacher;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_class_plan);
        if(this.getIntent() != null){
            Intent intent = this.getIntent();
            Teacher data = (Teacher)intent.getSerializableExtra(TEACHER);
            mTeacher = data;
        }
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_confirm_class_plan, menu);
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
        private ProgressBar loadProgressBar;
        private LinearLayout nearestClassPlanLinearLayout;
        private LinearLayout farClassPlanLinearLayout;
        private LinearLayout noClassPlanLinearLayout;
        private TextView noClassPlanCounterTextView;

        MyTimerTask timerTask = null;       //onClickメソッドでインスタンス生成
        Timer mTimer   = null;            //onClickメソッドでインスタンス生成
        Handler mHandler = new Handler();   //UI Threadへのpost用ハンドラ

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_confirm_class_plan, container, false);
            return rootView;
        }
        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
            //ロードプログレスバー
            this.loadProgressBar = (ProgressBar)this.getActivity().findViewById(R.id.confirm_class_plan_load_class_plan);
            //最寄り講義ない場合のレイアウト
            this.noClassPlanLinearLayout = (LinearLayout)this.getActivity().findViewById(R.id.confirm_class_plan_show_confirm_no_class_plan_linearLayout);
            //最寄り講義ない場合の自動でアプリが落ちる案内
            this.noClassPlanCounterTextView = (TextView)this.getActivity().findViewById(R.id.confirm_class_plan_show_confirm_no_class_plan_exitcounter_textView);
            //
            this.noClassPlanCounterTextView = (TextView)this.getActivity().findViewById(R.id.confirm_class_plan_show_confirm_no_class_plan_exitcounter_textView);
            //最寄り講義が遠い場合のレイアウト
            this.farClassPlanLinearLayout = (LinearLayout)this.getActivity().findViewById(R.id.confirm_class_plan_show_confirm_far_class_plan_linearLayout);
            //最寄り講義がある場合のレイアウト
            this.nearestClassPlanLinearLayout = (LinearLayout)this.getActivity().findViewById(R.id.confirm_class_plan_show_confirm_nearest_class_plan_linearLayout);
        }
        @Override
        public void onResume() {
            super.onResume();
            //レイアウトを初期状態に戻す.
            initLayout();

            timerTask = new MyTimerTask(this.getActivity(),this.mHandler,this.noClassPlanCounterTextView,FNISH_SECONDE);
            mTimer = new Timer(true);
            mTimer.schedule(timerTask, TIMER_SECONDE_INTERVAL, TIMER_SECONDE_INTERVAL);

            //テスト
            noClassPlanLayout();
        }

        @Override
        public void onDestroy(){
            super.onDestroy();
            //タイマーの停止処理
            mTimer.cancel();
            mTimer = null;
        }

        /**
         * Created by scr on 2014/12/17.
         * initLayoutメソッド
         * レイアウトをロード画面に戻す.
         */
        private void initLayout(){
            loadProgressBar.setVisibility(View.VISIBLE);
            this.noClassPlanLinearLayout.setVisibility(View.GONE);
            this.farClassPlanLinearLayout.setVisibility(View.GONE);
            this.noClassPlanLinearLayout.setVisibility(View.GONE);
        }
        /**
         * Created by scr on 2014/12/17.
         * hideProgressBarメソッド
         * ロードを非表示にする.
         */
        private void hideProgressBar(){
            this.loadProgressBar.setVisibility(View.GONE);
        }
        /**
         * Created by scr on 2014/12/17.
         * initLayoutメソッド
         * レイアウトをロード画面に戻す.
         */
        private void noClassPlanLayout() {
            hideProgressBar();
            this.noClassPlanLinearLayout.setVisibility(View.VISIBLE);
        }
        /**
         * Created by scr on 2014/12/17.
         * farClassPlanLayoutメソッド
         * 講義が遠い場合のレイアウト.
         */
        private void farClassPlanLayout() {
            hideProgressBar();
            this.farClassPlanLinearLayout.setVisibility(View.VISIBLE);
        }

        /**
         * Created by scr on 2014/12/17.
         * nearestClassPlanLayoutメソッド
         * レイアウトを講義内容確認にする.
         */
        private void nearestClassPlanLayout() {
            hideProgressBar();
            this.nearestClassPlanLinearLayout.setVisibility(View.VISIBLE);
        }

        /**
         * Created by scr on 2014/12/17.
         * checkNearestClassPlanメソッド
         * 最寄りの講義をネットワーク上のDBに調べに行く.
         * 全画面より送られてきたTeacherオブジェクトのteacherIdをキー
         * 最乗の講義をネットワークから取り寄せる.
         */
        private void checkNearestClassPlan() {

        }


    }
}
