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
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import java.util.Timer;
import jp.pmw.test_en_revolution.MainActivity;
import jp.pmw.test_en_revolution.MyActivity;
import jp.pmw.test_en_revolution.R;
import jp.pmw.test_en_revolution.confirm_class_plan.dummy.DummyContentClass;
import jp.pmw.test_en_revolution.one_cushion.select_teacher.Teacher;

public class ConfirmClassPlanActivity extends MyActivity {
    public static final String TEACHER = "teacher";
    //タイマーを起動するまでの時間
    private static final long TIMER_START_INTERVAL = 10000;
    //タイマーを起動するまでの時間
    private static final long TIMER_START_SECONDE_INTERVAL = 2000;
    //タイマーを1秒後に起動
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
        private LinearLayout notTodayClassButNextClassPlanLinearLayout;
        private TextView noClassPlanCounterTextView;
        //授業内容確認の次に進みますか.「はい」ボタン
        private Button confirmNearRestClassPlanPositiveButton;
        //授業内容確認の次に進みますか.「いいえ」ボタン
        private Button confirmNearRestClassPlanNegativeButton;
        private ClassPlan nowClassPlan=null;

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
            //本日講義はないが今期にまだ次回の講義ある場合のレイアウト
            this.notTodayClassButNextClassPlanLinearLayout = (LinearLayout)this.getActivity().findViewById(R.id.confirm_class_plan_no_today_class_but_next_class_linearLayout);

            //最寄り講義が遠い場合のレイアウト
            this.farClassPlanLinearLayout = (LinearLayout)this.getActivity().findViewById(R.id.confirm_class_plan_show_confirm_far_class_plan_linearLayout);
            //最寄り講義がある場合のレイアウト
            this.nearestClassPlanLinearLayout = (LinearLayout)this.getActivity().findViewById(R.id.confirm_class_plan_show_confirm_nearest_class_plan_linearLayout);
            //最寄り講義がある場合の「はい」ボタン
            this.confirmNearRestClassPlanPositiveButton = (Button)this.getActivity().findViewById(R.id.confirm_class_plan_show_confirm_nearest_class_plan_positive_button);
            this.confirmNearRestClassPlanPositiveButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                   //MainActivityに遷移する.
                    moveToMainActivity();
                }
            });
            //最寄り講義がある場合の「今は何もしない」ボタン
            this.confirmNearRestClassPlanNegativeButton = (Button)this.getActivity().findViewById(R.id.confirm_class_plan_show_confirm_nearest_class_plan_negative_button);
            this.confirmNearRestClassPlanNegativeButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    //finishする.
                    moveToFinishActivity();
                }
            });
        }

        @Override
        public void onResume() {
            super.onResume();
            //レイアウトを初期状態に戻す.
            initLayout();
            //ローカルのダミーデータでスタート
            dummyStart();
        }
        /**
         * Created by scr on 2014/12/22.
         * dummyStartメッソド
         * 内部のダミーデータでテストする場合に使います.
         */
        private void dummyStart(){
           /*
            //本日授業があるか
            int today = DummyContentClass.ITEMS.get(0).getToday();
            //今又は次の授業は何か
            ClassPlan classPlan = DummyContentClass.ITEMS.get(0).getClassPlan();
            //今学期はどこに属するか.
            int semester = DummyContentClass.ITEMS.get(0).getSemester();
           */
            /*教員ID指定でダミーデータからデータを取り出す.*/
            ConfirmClassPlanActivity activity = (ConfirmClassPlanActivity)this.getActivity();
            Teacher teacher = activity.mTeacher;
            Teaching teaching = DummyContentClass.getDummyTeaching(teacher.getId());
            //本日授業があるか
            int today = teaching.getToday();
            //今又は次の授業は何か
            ClassPlan classPlan = teaching.getClassPlan();
            //今学期はどこに属するか.
            int semester = teaching.getSemester();

            if(today!=0){
                //本日授業あり.
                if(today==1){
                    //今授業授業時間帯ですよ.
                    nowClassPlan = classPlan;
                    nowInClass(classPlan);
                }else{
                    //今の時限以降に授業
                    todayNextInClass(classPlan);
                }
            }else{
                //本日授業なし.
                if(classPlan==null){
                    //かつ今期の授業は終了
                    noClassInSameSemester(semester);
                }else{
                    //今学期に行う、現在から一番近い授業を取得する.
                    //本日行う講義はありません.
                    //次回の授業を表示する.
                    notTodayInClassShowNextClass(classPlan);
                }
            }
        }

        /**
         * Created by scr on 2014/12/22.
         * nowInClassメソッド
         * 現在、授業時間帯の場合にこのメソッドを使用します.
         * @param classPlan 授業の詳細情報を持つ.
         */
        private void nowInClass(ClassPlan classPlan){
            //現在の授業を他の誰かが閲覧していた場合に、何人が閲覧中なのかを表示する.
            if(classPlan.getNowBrowsingCount() > 0){
                int browsingresorce = R.id.confirm_class_plan_show_confirm_nearest_class_plan_now_browsing_count;
                TextView browingTextView = createInstanceTimerTextView(browsingresorce);
                browingTextView.setText(this.getActivity().getString(R.string.other)+classPlan.getNowBrowsingCount()+getString(R.string.confirm_nearest_class_plan_now_brosing));
                //非表示になっているの表示する.
                browingTextView.setVisibility(View.VISIBLE);
            }
            //授業日
            int dateResorce = R.id.confirm_class_plan_show_confirm_nearest_class_plan_datetime_textView;
            //教室名
            int inRoomResoce = R.id.confirm_class_plan_show_confirm_nearest_class_plan_in_room_textView;
            //授業科目
            int subjectResorce = R.id.confirm_class_plan_show_confirm_nearest_class_plan_subject_textView;
            //セットするテキストビューの領域インスタンス生成
            createInstanceConfirmTextViewZone(dateResorce,inRoomResoce,subjectResorce);
            //講義情報セット
            setItemConfirmTextView(classPlan);

            //レイアウトを表示する.
            nearestClassPlanLayout();
        }
        private TextView doClassDateTextView,doClassInRoomTextView,doSubjectTextView;
        /**
         * Created by scr on 2014/12/22.
         * createInstanceConfirmTextViewZoneメソッド
         * 講義情報を表示するtextViewのフィールドが同じだったので共通化しました.
         * @param dateResorce dateのリソース
         * @param inRoomResorce inRoomのリソース
         * @param subjectResorce subjectのリソース
         */
        private void createInstanceConfirmTextViewZone(int dateResorce,int inRoomResorce,int subjectResorce){
            //日時
            doClassDateTextView = (TextView)this.getActivity().findViewById(dateResorce);
            //教室名
            doClassInRoomTextView = (TextView)this.getActivity().findViewById(inRoomResorce);
            //授業科目
            doSubjectTextView = (TextView)this.getActivity().findViewById(subjectResorce);
        }
        /**
         * Created by scr on 2014/12/22.
         * setItemConfirmTextViewメソッド
         * 講義情報をTextViewにセットするメソッド
         * @param classPlan 講義情報
         * */
        private void setItemConfirmTextView(ClassPlan classPlan){
            String str = "";
            String contents = "";
            String space = getString(R.string.space);
            str = this.getString(R.string.confirm_for_class_plan_class_date);
            contents = classPlan.getWhen().getYear()
                    +getString(R.string.year)
                    +classPlan.getWhen().getMonth()
                    +getString(R.string.month)
                    +classPlan.getWhen().getDay()
                    +getString(R.string.day)
                    +getString(R.string.space)
                    +classPlan.getTimeZone().getTimeZoneName();
            doClassDateTextView.setText(str+space+contents);

            //教室名
            str = this.getString(R.string.confirm_for_class_plan_class_in_roomname);
            contents = classPlan.getPlace().getBuilding().getBuildingName()+classPlan.getPlace().getRoom().getRoomName();
            doClassInRoomTextView.setText(str+space+contents);

            //科目名
            str = this.getString(R.string.confirm_for_class_plan_class_subjectname);
            contents = classPlan.getSubject().getSubjectName();
            doSubjectTextView.setText(str+space+contents);

        }

        /**
         * Created by scr on 2014/12/22.
         * autoAppFinishメソッド
         * 現在のActvityをFinishするメソッドです.
         * 今のActivity上で設定されているタイマー後に起動を開始し、
         * 自動でActivityをFinishします.
         * @param timeTextView アプリが自動で落ちるまでのタイムカウントが表示されます.
         */
        private void autoAppFinish(TextView timeTextView,long waitTimer){
            timerTask = new MyTimerTask(this.getActivity(),this.mHandler,timeTextView,FNISH_SECONDE);
            mTimer = new Timer(true);
            mTimer.schedule(timerTask, waitTimer, TIMER_SECONDE_INTERVAL);
        }

        /**
         * Created by scr on 2014/12/22～2014/12/23
         * todayNextInClassメソッド
         * 本日授業があるがまだ、その時間帯ではない場合
         * @param classPlan 授業の詳細情報を持つ.
         */
        private void todayNextInClass(ClassPlan classPlan){
            //授業日
            int dateResorce = R.id.confirm_class_plan_show_confirm_far_class_plan_datetime_textView;
            //教室名
            int inRoomResoce = R.id.confirm_class_plan_show_confirm_far_class_plan_in_room_textView;
            //授業科目
            int subjectResorce = R.id.confirm_class_plan_show_confirm_far_class_plan_subject_textView;
            //セットするテキストビューの領域インスタンス生成
            createInstanceConfirmTextViewZone(dateResorce,inRoomResoce,subjectResorce);
            //講義情報セット
            setItemConfirmTextView(classPlan);

            //2014年12月23日に追加しました.
            //次の講義内容をセットする.
            TextView nextClassPlan = createInstanceTimerTextView(R.id.confirm_class_plan_show_confirm_far_class_plan_next_class_plan_textView);
            //「担当の授業開始前です.\r\n〇時限目に再度選択をしてください.」と表示する.
            String str = getString(R.string.confirm_for_class_plan_re_select_prelude) + classPlan.getTimeZone().getTimeZoneName() + getString(R.string.confirm_for_class_plan_re_select);
            nextClassPlan.setText(str);

            //レイアウトを表示
            farClassPlanLayout();

            //タイマーを表示するためのテキストビュー
            //TextView tv = (TextView)this.getActivity().findViewById(R.id.confirm_class_plan_show_confirm_far_class_plan_exitcounter_textView);
            TextView timerTextView = createInstanceTimerTextView(R.id.confirm_class_plan_show_confirm_far_class_plan_exitcounter_textView);
            autoAppFinish(timerTextView, TIMER_START_INTERVAL);
        }

        /**
         * Created by scr on 2014/12/22.
         * createInstanceTimerTextViewメソッド
         * アプリが自動で何秒後に落ちるかを案内するためのテキストビュー
         * @param timerTextResorce タイマー案内のテキストビューリソース
         * @return TextView タイマー案内のテキストビュー
         */
        private TextView createInstanceTimerTextView(int timerTextResorce){
            return (TextView)this.getActivity().findViewById(timerTextResorce);
        }


        /**
         * Created by scr on 2014/12/22～2014/12/23
         * notTodayInClassShowNextClassメソッド
         * 本日の授業はもうないが、今期まだ授業があるので、
         * 現在から一番近い授業を取得し案内する.
         * @param classPlan 授業の詳細情報を持つ.
         */
        private void notTodayInClassShowNextClass(ClassPlan classPlan){
            //授業日
            int dateResorce = R.id.confirm_class_plan_no_today_class_but_next_class_date_textView;
            //教室名
            int inRoomResoce = R.id.confirm_class_plan_no_today_class_but_next_class_in_roomname_textView;
            //授業科目
            int subjectResorce = R.id.confirm_class_plan_no_today_class_but_next_class_subjectname_textView;
            //セットするテキストビューの領域インスタンス生成
            createInstanceConfirmTextViewZone(dateResorce,inRoomResoce,subjectResorce);
            //講義情報セット
            setItemConfirmTextView(classPlan);

            //2014年12月23日に追加しました.
            //次の講義内容をセットする.
            TextView nextClassPlan = createInstanceTimerTextView(R.id.confirm_class_plan_no_today_class_but_next_class_next_class_plan_textView);
            //「2014年11月23日の〇時限目に再選択をしてください.」と表示する.
            String date = classPlan.getWhen().getYear() + getString(R.string.year) + classPlan.getWhen().getMonth() + getString(R.string.month) + classPlan.getWhen().getDay() + getString(R.string.day);
            String str = getString(R.string.confirm_for_class_plan_re_select_prelude) + date + " " + classPlan.getTimeZone().getTimeZoneName() + getString(R.string.confirm_for_class_plan_re_select);
            nextClassPlan.setText(str);
            //レイアウトを表示
            notTodayClassButNextClass();

           int timerResoce = R.id.confirm_class_plan_no_today_class_but_next_class_exitcounter_textView;
           TextView tv = createInstanceTimerTextView(timerResoce);
           tv.setText(getString(R.string.confirm_not_today_class_but_next_class_re_select));
           autoAppFinish(tv,TIMER_START_INTERVAL);
        }

        /**
         * !まだ製作中ですよ!
         * Created by scr on 2014/12/22～.
         * noClassInSameSemesterメソッド
         * 今期の授業はもう無いですよレイアウトを表示します.
         * @param semester 学期情報.
         */
        private void noClassInSameSemester(int nowSemester){
            TextView guideTextView = (TextView)this.getActivity().findViewById(R.id.confirm_class_plan_show_confirm_no_class_plan_guide_textView);
            /**
             * 学期案内をしたい場合はするべき.
             * **/
            //今学期の授業は終了.
            /*if(nowSemester == 1){
                //今は、前期間
                tv.setText();
            }else if(nowSemester == 2){
                //今は後期間
            }*/
            int timerResoce = R.id.confirm_class_plan_show_confirm_no_class_plan_exitcounter_textView;
            TextView tv = createInstanceTimerTextView(timerResoce);
            autoAppFinish(tv,TIMER_START_SECONDE_INTERVAL);
            noClassPlanLayout();
        }

        @Override
        public void onDestroy(){
            super.onDestroy();
            //タイマーの停止処理
            if(mTimer != null) {
                mTimer.cancel();
                mTimer = null;
            }
        }

        private void showNoClassPlanScreen(){
            noClassPlanLayout();
        }

        /**
         * Created by Shota Ito on 2014/12/22
         * moveToMainActivityメソッド
         * 授業のメインActivityに遷移します.
         */
        private void moveToFinishActivity(){
          this.getActivity().finish();
        }

        /**
         * Created by Shota Ito on 2014/12/22
         * moveToMainActivityメソッド
         * 授業のメインActivityに遷移します.
         */
        private void moveToMainActivity(){
            ConfirmClassPlanActivity activity = (ConfirmClassPlanActivity)this.getActivity();
            //教員に授業内容をセットする.
            if(nowClassPlan!=null) {
                activity.mTeacher.setClassPlan(nowClassPlan);
            }
            Intent intent = new Intent(activity, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra(ConfirmClassPlanActivity.TEACHER, activity.mTeacher);
            intent.setAction(Intent.ACTION_VIEW);
            //
            startActivity(intent);
            //
            this.getActivity().finish();
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
         * Created by scr on 2014/12/22.
         * notTodayClassButNextClassメソッド
         * 本日授業がないが、今期まだ授業がある場合.
         */
        private void notTodayClassButNextClass(){
            hideProgressBar();
            this.notTodayClassButNextClassPlanLinearLayout.setVisibility(View.VISIBLE);
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
