package jp.pmw.test_en_revolution;

import android.app.Activity;
import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;

import jp.pmw.test_en_revolution.attendee.Attendee;
import jp.pmw.test_en_revolution.attendee.CustomAdapter;
import jp.pmw.test_en_revolution.attendee.RosterCustomAdapter_2;
import jp.pmw.test_en_revolution.config.TimerConfig;
import jp.pmw.test_en_revolution.confirm_class_plan.Roster;
import jp.pmw.test_en_revolution.confirm_class_plan.Student;
import jp.pmw.test_en_revolution.dialog.AttendanceChangeStatusDialogFragment;
import jp.pmw.test_en_revolution.dialog.ReAttendanceChangeStatusDialogFragment;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AttendeeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AttendeeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AttendeeFragment extends MyMainFragment implements CustomDialogFragment.OnOkClickListener{
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static AttendeeFragment newInstance(int sectionNumber) {
        AttendeeFragment fragment = new AttendeeFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    private OnFragmentInteractionListener mListener;

    //  出席者確定前のレイアウト(GridViewの上段)
    private LinearLayout beforeAttendanceStatusLinearLayout;
    //  出席者確定後のレイアウト(GridViewの上段)
    private LinearLayout afterAttendanceStatusLinearLayout;

    private TextView attendanceStatusTextView;  //  出席:
    private TextView lateStatusTextView;        //  遅刻:
    private TextView absentStatusTextView;      //  欠席:
    private TextView leavEarlyStatusTextView;   //  早退:
    private TextView attendeeStatusTextView;
    //private ProgressBar attendeeLoadProgressBar;
    private ListView attendeeListView;
    public GridView attendeeGridView;
    //  出席・遅刻などの状態スタータスを変更時の理由
    public ManulReason[] mManulReasons;
    //  受講生アダプター
    public RosterCustomAdapter_2 adapter;
    //  出席者取得タイマー
    private AttendeeFragmentTimeTask transmitStateTimerTask;


    public AttendeeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getMainActivity().getClassObject().setStudentObject(null);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_attendee2, container, false);
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //
        super.initFragment();
        //  出席確定前LinearLayout
        this.beforeAttendanceStatusLinearLayout     = (LinearLayout)this.getActivity().findViewById(R.id.before_attendance_status_linearLayout);

        //  出席確定後LinearLayout
        this.afterAttendanceStatusLinearLayout      = (LinearLayout)this.getActivity().findViewById(R.id.after_attendance_status_linearLayout);

        this.attendanceStatusTextView   = (TextView)this.getActivity().findViewById(R.id.attendee_total_status_textView);
        this.lateStatusTextView   = (TextView)this.getActivity().findViewById(R.id.late_total_status_textView);
        this.absentStatusTextView       = (TextView)this.getActivity().findViewById(R.id.absent_total_status_textView);
        this.leavEarlyStatusTextView = (TextView)this.getActivity().findViewById(R.id.leav_eary_total_status_textView);
        this.attendeeStatusTextView = (TextView)this.getActivity().findViewById(R.id.attendee_status_message_textView);
        this.attendeeListView = (ListView)this.getActivity().findViewById(R.id.attendee_list);
        //this.attendeeLoadProgressBar = (ProgressBar)this.getActivity().findViewById(R.id.attendee_load_progressBar);
        this.attendeeGridView = (GridView)this.getActivity().findViewById(R.id.attendee_gridView);
        this.attendeeGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // 選択アイテムを取得
                GridView gridView = (GridView) parent;
                StudentObject so = (StudentObject) gridView.getItemAtPosition(position);
                showStudentInfoCustomDialog(so);
            }
        });
         //  学生データ読み込み
        this.getMainActivity().getClassHttpRequest().getChkAttendance();
    }

    /*public void testShowCustomDialog(Student student){
        MainActivity activity = (MainActivity)this.getActivity();
        TransmitStateObject tso = activity.mTeacher.getClassPlan().getTransmitStateObject();
        CustomDialogFragment customDialog = CustomDialogFragment.newInstance();
        customDialog.setTargetFragment(AttendeeFragment.this, 0);
        Bundle bundle = new Bundle();
        bundle.putSerializable(CustomDialogFragment.ATTENDANCE_STUDENT_INFO, student);
        bundle.putSerializable(CustomDialogFragment.TRANSMIT_STATE_INFO, tso);
        customDialog.setArguments(bundle);
        customDialog.setAttendeeFragment(this);
        customDialog.show(activity.getSupportFragmentManager(), "customDialog");
    }*/
    public void testShowCustomDialog(StudentObject student){
        /*MainActivity activity = (MainActivity)this.getActivity();
        TransmitStateObject tso = activity.getClassObject().getTransmitStateObject();
        CustomDialogFragment customDialog = CustomDialogFragment.newInstance();
        customDialog.setTargetFragment(AttendeeFragment.this, 0);
        Bundle bundle = new Bundle();
        bundle.putSerializable(CustomDialogFragment.ATTENDANCE_STUDENT_INFO, student);
        bundle.putSerializable(CustomDialogFragment.TRANSMIT_STATE_INFO, tso);
        customDialog.setArguments(bundle);
        customDialog.setAttendeeFragment(this);
        customDialog.show(activity.getSupportFragmentManager(), "customDialog");*/
    }

    //  学生の個別情報を出力
    public void showStudentInfoCustomDialog(StudentObject so){

        studentInfoDialogFragnemt = new StudentInfoCustomDialog();
        studentInfoDialogFragnemt.showForAttendeeFragment(this, so);

        /*MainActivity activity = (MainActivity)this.getActivity();
        String sameClassNumber  =   activity.getClassObject().getSameClassNumber();
        TransmitStateObject tso = activity.getClassObject().getTransmitStateObject();
        StudentInfoDialogFragnemt dialogFragment = StudentInfoDialogFragnemt.newInstance();
        if(dialogFragment != null){
            dialogFragment.setTargetFragment(AttendeeFragment.this, 0);
            Bundle bundle = new Bundle();
            bundle.putSerializable(StudentInfoDialogFragnemt.SAME_CLASS_NUMBER, sameClassNumber);
            bundle.putSerializable(StudentInfoDialogFragnemt.STUDENT_INFO, so);
            bundle.putSerializable(StudentInfoDialogFragnemt.TRANMIST_STAT_IFNO, tso);
            dialogFragment.setArguments(bundle);
            dialogFragment.setAttendeeFragment(this);
            dialogFragment.show(activity.getSupportFragmentManager(), StudentInfoDialogFragnemt.STUDENT_INFO_DIALOG_FRAGMENT);
        }*/
    }


    @Override
    public void onResume(){
        super.onResume();
        //  タイマータスク
        if(transmitStateTimerTask == null){
            MainActivity acitivty = (MainActivity)this.getActivity();
            transmitStateTimerTask = new AttendeeFragmentTimeTask(acitivty, this, attendeeGridView);   //タイマータスククラスのインスタンス
            java.util.Timer timer = new java.util.Timer();                                      //タイマークラスのインスタンス
            timer.schedule(transmitStateTimerTask, 0, TimerConfig.TIMER_INTERVAL_TIME_ATTENDANCE);                                    //起動時0秒後から10秒間隔で起動
        }
        showWaitFragment();

        //受講者クラス
        /*Roster roster = activity.mTeacher.getRoster();
        //出席者の人数を文字列でセットする.
        setAttendanceStatusTextView(roster);
        if(roster != null){
            //受講生一覧表示
            addRoster(roster);
        }else{
            //リロード
            super.loadFragment();
        }*/
    }

    //  学生が表示されているGridViewの情報を更新するHandlerThreadメソッドです.
    public void displayRedrawGridViewHandlerThread(){
        Handler mHandler = new Handler(Looper.getMainLooper());
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                redrawGridView();
            }
        });
    }

    public void redrawGridView(){
        if(adapter != null){
            //  グリッドビュー再描画
            //attendeeGridView.invalidateViews();
            //Toast.makeText(getMainActivity(), "ERROR:", Toast.LENGTH_SHORT).show();
            this.adapter.notifyDataSetChanged();
        }
        if(attendeeListView != null){
            this.attendeeListView.invalidate();
        }
    }


    //  欠席数、出席数に応じて順番を入れ替える際に使用するメソッドです.
    public void displayToastThroughHandlerThread() {
        ((MainActivity) this.getActivity()).getClassObject().setStudentObject(null);
        this.getMainActivity().getClassHttpRequest().getChkAttendance();
        final HandlerThread ht = new HandlerThread("TestThread#3");
        ht.start();

        Handler h = new Handler(ht.getLooper());
        h.post(new Runnable() {
            @Override
            public void run() {
                //showToast();

                showWaitFragment();
            }
        });

        // 別スレッドを停止
        //ht.quit();
    }

    public void showWaitFragment(){
        MainActivity activity = (MainActivity)this.getActivity();
        this.mManulReasons = activity.getClassObject().mManulReasons;
        NumOfAttendanceEntity noae = activity.getClassObject().getNumOfAttendanceEnttity();
        StudentObject[] sos = activity.getClassObject().getStudentObject();
        if(sos != null){
            //  再描画キャンセル
            cancelTimer();
            addNewRoster(sos);
            //  出席者数セット
            setAttendanceStatusTextView(noae);
            //  出席状態レイアウトセット
            setAttendanceStatusLayout();
            //  コンテンツ画面表示
            switchContentScreen();
        } else{

            if(this.reGetStudentFlag){
                //  再度出席状況を一から取り直す
                this.getMainActivity().getClassHttpRequest().getChkAttendance();
                this.reGetStudentFlag = false;
            }

            //  数秒待機して再読み込み...
            super.loadFragment();
        }

    }
    /**
     * setReAttendanceStatusTextViewメソッド
     * 出席と欠席の人数をTextViewに再描画しセットします.
     * **/
    public void setReAttendanceStatusTextView(){
        MainActivity activity = (MainActivity)this.getActivity();
        setAttendanceStatusTextView(activity.getClassObject().getNumOfAttendanceEnttity());
    }

    /**
     * setAttendanceStatusTextViewメソッド
     * 出席と欠席の人数をTextViewにセットします.
     * **/
    /*private void setAttendanceStatusTextView(Roster roster){
        int[] count = getAttendanceCount(roster);
        int attendCount = count[0];
        int absentCount = count[1];
        int forgotCount = count[2];
        int totalAttendCount = attendCount + forgotCount;
        //出席
        String strAtt = getString(R.string.total_attendee);
        //欠席
        String strAbs = getString(R.string.total_absentee);
        //人
        String strPer = getString(R.string.number_of_people);
        //this.attendanceStatusTextView.setText(strAtt + " " + totalAttendCount +  strPer + "("+forgotCount+")" + "   " + strAbs+" "+absentCount+strPer);
        //  出席者数セット
        if(forgotCount == 0){
            //this.attendanceStatusTextView.setText(strAtt + " " + totalAttendCount +  strPer + "   " + strAbs+" "+absentCount+strPer);
            this.attendanceStatusTextView.setText(strAtt + " " + attendCount);
        }else{
            //this.attendanceStatusTextView.setText(strAtt + " " + totalAttendCount + strPer + "(" + forgotCount + ")" + "   " + strAbs + " " + absentCount + strPer);
            this.attendanceStatusTextView.setText(strAtt + " " + attendCount + " + " +forgotCount);
        }
        //  欠席者数セット
        this.absentStatusTextView.setText(strAbs+" "+absentCount);
    }*/

    void setAttendanceStatusTextView(NumOfAttendanceEntity noae){
        int attNotForgot    = noae.mAttendanceNotForgot;
        int attForgot       = noae.mAttendanceForgot;
        int lateNotForgot   = noae.mLateNotForgot;
        int lateForgot      = noae.mLateForgot;
        int absent          = noae.mAbsent;
        int leave           = noae.mLeave;

        String strAtt = getString(R.string.total_attendee) + " "+ attNotForgot;
        if( attForgot > 0 ){
            strAtt = strAtt + "+"+attForgot;
        }
        String strLate = getString(R.string.total_late) + " "+ lateNotForgot;
        if( lateForgot > 0 ){
            strLate = strLate + "+"+lateForgot;
        }
        String strAbsent = getString(R.string.total_absentee) + " "+ absent;

        String strLeave = getString(R.string.leave_early) + " " + leave;

        final String fStrAtt = strAtt;
        final String fStrLat = strLate;
        final String fStrAbs = strAbsent;
        final String fStrLeave = strLeave;

        mHandler.post(new Runnable() {
            public void run() {
                attendanceStatusTextView.setText(fStrAtt);
                lateStatusTextView.setText(fStrLat);
                absentStatusTextView.setText(fStrAbs);
                leavEarlyStatusTextView.setText(fStrLeave);
            }
        });
    }


    /*private void setAttendanceStatusTextView(StudentObject[] sos){
        int[] count = getAttendanceCount(sos);
        int attendCount = count[0];
        int absentCount = count[1];
        int forgotCount = count[2];
        int totalAttendCount = attendCount + forgotCount;
        //出席
        String strAtt = getString(R.string.total_attendee);
        //欠席
        String strAbs = getString(R.string.total_absentee);
        //人
        String strPer = getString(R.string.number_of_people);
        //this.attendanceStatusTextView.setText(strAtt + " " + totalAttendCount +  strPer + "("+forgotCount+")" + "   " + strAbs+" "+absentCount+strPer);
        //  出席者数セット
        if(forgotCount == 0){
            //this.attendanceStatusTextView.setText(strAtt + " " + totalAttendCount +  strPer + "   " + strAbs+" "+absentCount+strPer);
            this.attendanceStatusTextView.setText(strAtt + " " + attendCount);
        }else{
            //this.attendanceStatusTextView.setText(strAtt + " " + totalAttendCount + strPer + "(" + forgotCount + ")" + "   " + strAbs + " " + absentCount + strPer);
            this.attendanceStatusTextView.setText(strAtt + " " + attendCount + " + " +forgotCount);
        }
        //  欠席者数セット
        this.absentStatusTextView.setText(strAbs+" "+absentCount);
    }*/
    /**
     * getAttendanceCountメソッド
     * 出席と欠席の人数を取得します.
     * **/
    /*private int[] getAttendanceCount(Roster roster){
        int attendCount = 0;
        int forgotCount = 0;
        int absentCount = 0;
        int[] count = new int[3];
        for(int i = 0; i < roster.getRosterList().size(); i++){
            Student st = roster.getRosterList().get(i);
            //if(st.getAttendance().getTime() != null && st.getAttendance().getFogotApplytTime() == null){
            if(st.getAttendance().getStatus() == Config.ALREADY_ATTENDANCE){
                ++attendCount;
            //}else if(st.getAttendance().getTime() != null && st.getAttendance().getFogotApplytTime() != null){
            }else if(st.getAttendance().getStatus() == Config.FOGOT_ESL_APPLY){
                ++forgotCount;
            }else{
                ++absentCount;
            }
        }
        count[0] = attendCount;
        count[1] = absentCount;
        count[2] = forgotCount;
        return count;
    }*/
    private int[] getAttendanceCount(StudentObject[] sos){
        int attendCount = 0;
        int forgotCount = 0;
        int absentCount = 0;
        int[] count = new int[3];
        for(int i = 0; i < sos.length; i++){
            StudentObject so = sos[i];
            AttendanceObject ao = so.getAttendanceObject();
            //Student st = roster.getRosterList().get(i);
            //if(st.getAttendance().getTime() != null && st.getAttendance().getFogotApplytTime() == null){
            if(ao.getAttendanceTime() != null && ao.getForgotApplyTime() == null){
                ++attendCount;
                //}else if(st.getAttendance().getTime() != null && st.getAttendance().getFogotApplytTime() != null){
            }else if(ao.getAttendanceTime() != null && ao.getForgotApplyTime() != null){
                ++forgotCount;
            }else{
                ++absentCount;
            }
        }
        count[0] = attendCount;
        count[1] = absentCount;
        count[2] = forgotCount;
        return count;
    }

    /**
     * Created by scr on 2014/12/14.
     * getNetworkAttendanceInfoメソッド
     * ネットワークのデータベースに出席者の情報を取得しにいく.
     */
    /*public void getNetworkAttendanceInfo(){
        MainActivity activity = (MainActivity)this.getActivity();
        //教室情報を取得しに行く.
        //String url = URL.ATTENDEE_LIST+"/"+activity.getClassPlanId();
        String url = URL.ATTENDEE_LIST+"/"+activity.mTeacher.getClassPlan();
        JsonArrayRequest request = new JsonArrayRequest(
                url,
                new Response.Listener<JSONArray>() {
                    @Override public void onResponse(JSONArray response) {
                        // レスポンス受け取り時の処理...
                        attendanceJsonParser(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override public void onErrorResponse(VolleyError error) {
                        // エラー時の処理...
                        Toast.makeText(getActivity(), "Unable to fetch data: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                        faileProcess();
                    }
                });
        AppController.getInstance(this.getActivity()).getRequestQueue().add(request);
    }*/

    /**
     * Created by scr on 2014/12/14.
     * attendanceJsonParserメソッド
     * ネットワークDBから取得した出欠席Jsonを解析する.
     * @param jsonArray ネットワークDBから取得した出欠席情報
     */
    public void attendanceJsonParser(JSONArray jsonArray){
        Gson mygson = new Gson();
        Type collectionType = new TypeToken<Collection<Attendee>>(){}.getType();
        //出欠席者を取得する
        List<Attendee> attendance = mygson.fromJson(jsonArray.toString(),collectionType);
        addAttendandeList(attendance);
    }

    /**
     * Created by scr on 2014/12/14.
     * addAttendandeListメソッド
     * ネットワークDBから取得した出欠席Jsonを解析する.
     * @param attendee ネットワークDBから取得した出欠席情報
     */
    public void addAttendandeList(List<Attendee> attendee){
        //アダプター作成
        CustomAdapter adapter = new CustomAdapter(this.getActivity(),0,attendee);
        //ListViewにセットする.
        this.attendeeListView.setAdapter(adapter);
        //ListViewの再描画
        this.attendeeListView.invalidate();
        //処理を無事に終えたのでListViewを表示する.
        successProcess();
    }

    /**
     * Created by scr on 2014/12/14.
     * successProcessメソッド
     * 処理が正常に終了.
     */
    public void successProcess(){
        int nowSetCount = this.attendeeListView.getAdapter().getCount();
        if(nowSetCount > 0){
            //一人以上学生の出席を確認できた.
            //this.attendeeLoadProgressBar.setVisibility(View.GONE);
            this.attendeeListView.setVisibility(View.VISIBLE);
        }else{
            //TODO:何時頃より出席調査を開始しますので,お待ちください.
            /**
             *
             * **/

            //TODO:出席者がいません.
            //this.attendeeLoadProgressBar.setVisibility(View.GONE);
            //出席者がいませんをセットする.
            this.attendeeStatusTextView.setText(R.string.message_no_attendee);
            this.attendeeStatusTextView.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Created by scr on 2014/12/14.
     * successProcessメソッド
     * 処理が正常に終了.
     */
    private void successProcessShowGridView(){
        int nowSetCount = this.attendeeGridView.getAdapter().getCount();
        if(nowSetCount > 0){
            //一人以上学生の出席を確認できた.
            //this.attendeeLoadProgressBar.setVisibility(View.GONE);
            this.attendeeGridView.setVisibility(View.VISIBLE);
        }else{
            //TODO:何時頃より出席調査を開始しますので,お待ちください.
            /**
             *
             * **/

            //TODO:出席者がいません.
            //this.attendeeLoadProgressBar.setVisibility(View.GONE);
            //出席者がいませんをセットする.
            this.attendeeStatusTextView.setText(R.string.message_no_attendee);
            this.attendeeStatusTextView.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Created by scr on 2014/12/14.
     * faileProcessメソッド
     * 処理が処理に失敗した.
     */
    private void faileProcess(){
        //this.attendeeLoadProgressBar.setVisibility(View.GONE);
        //失敗しました.
        this.attendeeStatusTextView.setText(R.string.faile_process);
        this.attendeeStatusTextView.setVisibility(View.VISIBLE);
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        /*try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }*/
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(getArguments().getInt(ARG_SECTION_NUMBER));
    }
    @Override
    public void onPause(){
        super.onPause();
        if(this.transmitStateTimerTask != null){
            this.transmitStateTimerTask.cancel();
            this.transmitStateTimerTask = null;
        }
        //  一括出席認定変更ダイアログを閉じる
        if( this.mAttendanceChangeStatusDialogFragment != null ){
            mAttendanceChangeStatusDialogFragment.dismiss();
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

    /**
     * Created by scr on 2014/12/24.
     * testDummyメソッド
     * ダミーデータで開始する.
     */
    private void testDummy(){
        //受講者一覧を取得
        //Roster roster = DummyRosterContent.getDummyRoster();
        MainActivity activity = super.getMainActivity();
        Roster roster = activity.mTeacher.getRoster();
        //受講者を渡す.
        addRoster(roster);
    }
    /**
     * Created by scr on 2016/2/1.
     * setAttendanceStatusLayoutメソッド
     * ・出席確定前   GridViewの上段に「教室にいる学生を調査中です...」を表示
     * ・出席確定後   GridViewの上段に「出席 ○○  欠席  ○○」を表示
     */
    public void setAttendanceStatusLayout(){
        MainActivity activity = (MainActivity)getActivity();
        TransmitStateObject tso = activity.getClassObject().getTransmitStateObject();

        if(tso == null){
            //  送信状態がNULLの場合
            return ;
        }

        if(tso.getAttendanceTranmitEndTime() == null){
            //  出席確定前
            this.beforeAttendanceStatusLinearLayout.setVisibility(View.VISIBLE);
            this.afterAttendanceStatusLinearLayout.setVisibility(View.GONE);
        }else{
            //  出席確定後
            this.beforeAttendanceStatusLinearLayout.setVisibility(View.GONE);
            this.afterAttendanceStatusLinearLayout.setVisibility(View.VISIBLE);
        }
    }

    //  受講者Add
    private void addNewRoster(StudentObject[] sos){
        //adapter = new RosterCustomAdapter_1(this.getActivity(),0,rosterList,RosterCustomAdapter_1.ALL_LAYOUT);
        TransmitStateObject tso = super.getMainActivity().getClassObject().getTransmitStateObject();
        adapter = new RosterCustomAdapter_2(this.getActivity(),0,tso,sos,RosterCustomAdapter_2.ALL_LAYOUT);

        this.attendeeGridView.setNumColumns(2);
        this.attendeeGridView.setAdapter(adapter);
        this.attendeeGridView.invalidateViews();
        successProcessShowGridView();
    }


    /**
     * Created by scr on 2014/12/24.
     * addRosterメソッド
     * 受講者ListViewに加える.
     */
    private void addRoster(Roster roster){
        List<Student> rosterList = roster.getRosterList();

        /*領域右側に状態カラーバージョン*/
        //RosterCustomAdapter adapter = new RosterCustomAdapter(this.getActivity(),0,rosterList);
        /**/

        //adapter = new RosterCustomAdapter_1(this.getActivity(),0,rosterList,RosterCustomAdapter_1.ALL_LAYOUT);
        //adapter = new RosterCustomAdapter_1(this.getActivity(),0,rosterList,RosterCustomAdapter_1.ALL_LAYOUT);

        this.attendeeGridView.setNumColumns(2);
        this.attendeeGridView.setAdapter(adapter);
        successProcessShowGridView();

        //ListViewにセットする.
        //this.attendeeListView.setAdapter(adapter);
        //ListViewの再描画
        //this.attendeeListView.invalidate();
        //ListViewを表示する.
        //successProcess();
    }
    /**
     * Created by scr on 2015/1/4.
     * onOkClickedメソッド
     * CustomDialogFramentからのコールバックで使用します.
     */
    @Override
    public void onOkClicked(Bundle args) {
        //int selectedId = args.getInt("KEY_MYDIALOG");
        //String text = "none";
        //
        //this.attendeeGridView.getAdapter().notify();
        this.adapter.notifyDataSetChanged();
        this.attendeeGridView.invalidate();
    }
    AttendanceChangeStatusDialogFragment mAttendanceChangeStatusDialogFragment;
    ReAttendanceChangeStatusDialogFragment mReAttendanceChangeStatusDialogFragment;
    /**
     * Created by scr on 2016/12/14.
     * showAttendanceChangeStatusDialogFramentメソッド
     * 出席認定一括変更ダイアログを表示する.
     */
    void showAttendanceChangeStatusDialogFrament(){
        if( getMainActivity().getClassObject().getTransmitStateObject() != null ){
            if( getMainActivity().getClassObject().getTransmitStateObject().getAttendanceTranmitEndTime() != null ){
                if( getMainActivity().getClassObject().getTransmitStateObject().mAttendanceBulkChangeEndDateTime == null ){
                    if( mAttendanceChangeStatusDialogFragment == null ){
                        mAttendanceChangeStatusDialogFragment = AttendanceChangeStatusDialogFragment.newInstance();
                    }
                    if( getMainActivity().getClassObject().getTransmitStateObject().mAttendanceBulkChangeEndDateTime == null ){
                        mAttendanceChangeStatusDialogFragment.mAttendeeFragment = this;
                        mAttendanceChangeStatusDialogFragment.mHandler = this.mHandler;
                        Bundle bundle = new Bundle();
                        String sameClassNumber = getMainActivity().getClassObject().getSameClassNumber();
                        bundle.putString(AttendanceChangeStatusDialogFragment.SAME_CLASS_NUMBER, sameClassNumber);
                        mAttendanceChangeStatusDialogFragment.setArguments(bundle);
                        mAttendanceChangeStatusDialogFragment.show(getActivity().getSupportFragmentManager(), AttendanceChangeStatusDialogFragment.ATTENDANCE_CHANGE_STATUS_DIALOG_FRAGMENT);
                    }
                }
            }
        }
    }
    /**
     * Created by scr on 2016/12/16
     * showReAttendanceChangeStatusDialogFramentメソッド
     * 在室確認一括変更ダイアログを表示する.
     */
    void showReAttendanceChangeStatusDialogFrament(){
        if(getMainActivity().getClassObject().getTransmitStateObject() != null){
            if( getMainActivity().getClassObject().getTransmitStateObject().mReAttendanceBulkChangeShow == TransmitStateObject.RE_ATTENDANCE_BULK_CHANGE_SHOW ) {
                if( !ReAttendanceChangeStatusDialogFragment.newInstance().browsing ) {
                    ReAttendanceChangeStatusDialogFragment.newInstance().mHandler = this.mHandler;
                    Bundle bundle = new Bundle();
                    String sameClassNumber = getMainActivity().getClassObject().getSameClassNumber();
                    bundle.putString(AttendanceChangeStatusDialogFragment.SAME_CLASS_NUMBER, sameClassNumber);
                    ReAttendanceChangeStatusDialogFragment.newInstance().setArguments(bundle);
                    ReAttendanceChangeStatusDialogFragment.newInstance().show(getActivity().getSupportFragmentManager(), ReAttendanceChangeStatusDialogFragment.RE_ATTENDANCE_CHANGE_STATUS_DIALOG_FRAGMENT);
                }
            }
        }
    }
}
