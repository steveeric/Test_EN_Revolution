package jp.pmw.test_en_revolution;

import android.app.Activity;
import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
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
import java.util.Timer;

import jp.pmw.test_en_revolution.attendee.Attendee;
import jp.pmw.test_en_revolution.attendee.CustomAdapter;
import jp.pmw.test_en_revolution.attendee.RosterCustomAdapter_1;
import jp.pmw.test_en_revolution.config.URL;
import jp.pmw.test_en_revolution.confirm_class_plan.Roster;
import jp.pmw.test_en_revolution.confirm_class_plan.Student;

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

    private TextView attendeeStatusTextView;
    private ProgressBar attendeeLoadProgressBar;
    private ListView attendeeListView;
    private GridView attendeeGridView;

    private RosterCustomAdapter_1 adapter;

    public AttendeeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
        this.attendeeStatusTextView = (TextView)this.getActivity().findViewById(R.id.attendee_status_message_textView);
        this.attendeeListView = (ListView)this.getActivity().findViewById(R.id.attendee_list);
        this.attendeeLoadProgressBar = (ProgressBar)this.getActivity().findViewById(R.id.attendee_load_progressBar);
        this.attendeeGridView = (GridView)this.getActivity().findViewById(R.id.attendee_gridView);
        this.attendeeGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // 選択アイテムを取得
                GridView gridView = (GridView)parent;
                Student attendanceStudent = (Student)gridView.getItemAtPosition(position);
                testShowCustomDialog(attendanceStudent);
            }
        });
    }

    public void testShowCustomDialog(Student student){
        MainActivity activity = (MainActivity)this.getActivity();
        CustomDialogFragment customDialog = CustomDialogFragment.newInstance();
        customDialog.setTargetFragment(AttendeeFragment.this,0);
        Bundle bundle = new Bundle();
        bundle.putSerializable(CustomDialogFragment.ATTENDANCE_STUDENT_INFO,student);
        customDialog.setArguments(bundle);
        customDialog.show(activity.getSupportFragmentManager(), "customDialog");
    }

    @Override
    public void onResume(){
        super.onResume();

        testDummy();

        //出席者の方法をネットワークDBに取得しに行く
        //getNetworkAttendanceInfo();
    }

    /**
     * Created by scr on 2014/12/14.
     * getNetworkAttendanceInfoメソッド
     * ネットワークのデータベースに出席者の情報を取得しにいく.
     */
    public void getNetworkAttendanceInfo(){
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
    }

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
    private void successProcess(){
        int nowSetCount = this.attendeeListView.getAdapter().getCount();
        if(nowSetCount > 0){
            //一人以上学生の出席を確認できた.
            this.attendeeLoadProgressBar.setVisibility(View.GONE);
            this.attendeeListView.setVisibility(View.VISIBLE);
        }else{
            //TODO:何時頃より出席調査を開始しますので,お待ちください.
            /**
             *
             * **/

            //TODO:出席者がいません.
            this.attendeeLoadProgressBar.setVisibility(View.GONE);
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
            this.attendeeLoadProgressBar.setVisibility(View.GONE);
            this.attendeeGridView.setVisibility(View.VISIBLE);
        }else{
            //TODO:何時頃より出席調査を開始しますので,お待ちください.
            /**
             *
             * **/

            //TODO:出席者がいません.
            this.attendeeLoadProgressBar.setVisibility(View.GONE);
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
        this.attendeeLoadProgressBar.setVisibility(View.GONE);
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
     * Created by scr on 2014/12/24.
     * addRosterメソッド
     * 受講者ListViewに加える.
     */
    private void addRoster(Roster roster){
        List<Student> rosterList = roster.getRosterList();

        /*領域右側に状態カラーバージョン*/
        //RosterCustomAdapter adapter = new RosterCustomAdapter(this.getActivity(),0,rosterList);
        /**/

        adapter = new RosterCustomAdapter_1(this.getActivity(),0,rosterList,RosterCustomAdapter_1.ALL_LAYOUT);

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

    private Handler handler = new Handler();


    public void demo(List<Student> attendance){
        Timer mainTimer = new Timer();
        MainActivity activity = (MainActivity)this.getActivity();
        activity.setTimer(mainTimer);
        DemoReCollTheRollTimer reTimer  = new DemoReCollTheRollTimer(activity,DemoReCollTheRollTimer.MODE_ATTENDEE_FRAGMENT,attendance);
        reTimer.setAttendeeFragment(handler,this.attendeeGridView,this.adapter);
        mainTimer.schedule(reTimer, 6000,1000);
    }
}
