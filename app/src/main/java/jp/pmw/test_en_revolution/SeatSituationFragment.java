package jp.pmw.test_en_revolution;

import android.app.Activity;
import android.app.Fragment;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

import jp.pmw.test_en_revolution.confirm_class_plan.Student;
import jp.pmw.test_en_revolution.confirm_class_plan.RoomInfoObject;
import jp.pmw.test_en_revolution.room.RoomView;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SeatSituationFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SeatSituationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SeatSituationFragment extends MyMainFragment implements CustomDialogFragment.OnOkClickListener{
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static SeatSituationFragment newInstance(int sectionNumber) {
        SeatSituationFragment fragment = new SeatSituationFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }
    /*教室情報を呼ぶのに必要*/
    ProgressBar loadRoomMapProgressBar;
    LinearLayout mSeatSituationLl;
    ImageView mFaceIv;
    TextView mStudentIdNumberTv;
    TextView mFuriganaTv;
    TextView mFullNameTv;
    //TextView mPositionTv;
    /*教室用のView*/
    public RoomView roomView;
    //   授業参加学生を取得するまで待機するタイマー
    private Timer waitTimer;
    /*出欠席者の情報を再取得しに行くためのタイマー*/
    private Timer mTimer;


    public SeatSituationFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_seat_situation, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
         /*ロード用*/
        loadRoomMapProgressBar = (ProgressBar)this.getActivity().findViewById(R.id.seat_situation_load);
        mSeatSituationLl = (LinearLayout)this.getActivity().findViewById(R.id.seat_situation_ll);
        mFaceIv = (ImageView)this.getActivity().findViewById(R.id.seat_situation_ll_face_iv);
        mStudentIdNumberTv = (TextView)this.getActivity().findViewById(R.id.seat_situation_ll_student_id_number_tv);
        mFuriganaTv = (TextView)this.getActivity().findViewById(R.id.seat_situation_ll_furigana_tv);
        mFullNameTv = (TextView)this.getActivity().findViewById(R.id.seat_situation_ll_full_name_tv);
        //mPositionTv = (TextView)this.getActivity().findViewById(R.id.seat_situation_ll_position_name_tv);
        /*教室マップを描くよう*/
        roomView = (RoomView)this.getActivity().findViewById(R.id.view);
    }
    /**
     * コールバック用のリスナー
     * **/
    private OnOkClickListener mListener;
    public interface OnOkClickListener {
        public void onOkClicked(Student student);
    }

    @Override
    public void onResume(){
        super.onResume();

        //コールバック用に
        roomView.setSeatSituationFragment(this);

        MainActivity activity = (MainActivity)this.getActivity();
        //Roster roster = activity.mTeacher.getRoster();
        //RoomInfoObject tmpRoomInfo = activity.mTeacher.getTmpRoomInfo();
        //  教室フロア情報(座席情報も)
        RoomInfoObject  rio      =   activity.getClassObject().getRoomInfoObject();


        if(rio != null){
            super.cancelTimer();
            roomView.setRoomInfoObject(rio);
            //roomView.seatedStudents(sos);
            roomView.invalidate();
            showRoomMap();
            getStudentsTimerTask();
        }else{
            //まだ取得できていないので
            //ロード画面
            super.loadFragment();
        }
    }



    /**
     * Created by scr on 2016/02/16.
     * getStudentsTimerTaskメソッド
     * 本日授業に参加する予定の学生群をネットワークDBから取得する.
     * 学生群を取得でき次第、学生の着席位置を描画する.
     */
    private void getStudentsTimerTask(){
        MainActivity activity = (MainActivity)this.getActivity();
        StudentObject[] sos      =   activity.getClassObject().getStudentObject();
        if(sos == null){
            getMainActivity().getClassHttpRequest().getChkAttendance();
            waitTimer = new Timer();
            waitTimer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            StudentObject[] sos = getMainActivity().getClassObject().getStudentObject();
                            if (sos != null) {
                                roomView.seatedStudents(sos);
                                timerTask();
                            }
                        }
                    });
                }
            }, 0, RELOAD_INTERVAL);
        }else{
            roomView.seatedStudents(sos);
            timerTask();
        }
    }
    /**
     * Created by scr on 2016/02/16.
     * timerTaskメソッド
     * 本日の授業参加者の状態を随時取得するタイマータスクを起動します.
     */
    private void timerTask(){

        if(waitTimer != null){
            //  学生取得用タイマー
            waitTimer.cancel();
            waitTimer = null;
        }

        if(mTimer == null){
            //  出席者情報随時取得用タイマー
            MainActivity activity = (MainActivity)this.getActivity();
            mTimer = new Timer();
            mTimer.schedule(new SeatSituationFragmentTimeTask(activity, this), 0, this.RELOAD_INTERVAL);
        }
    }
    /**
     * Created by scr on 2016/02/16.
     * initStudentObjectメソッド
     * インスタンスにある本日の授業参加学生データを初期化する.
     */
    /*public void initStudentObject(){
        (getMainActivity()).getClassObject().setStudentObject(null);
        (getMainActivity()).getClassHttpRequest().getChkAttendance();
        (getMainActivity()).showAnotherErrorToast("initStudentObject!");
    }*/

    //  学生の出席状態を更新するHandlerThreadメソッドです.
    public void displayRoomViewHandlerThread(){
        Handler mHandler = new Handler(Looper.getMainLooper());
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                redrawRoomView();
            }
        });
    }
    //  再描画
    public void redrawRoomView(){
        if(roomView != null){
           roomView.invalidate();
        }
        /*if(mTimer!=null){
            MainActivity activity = (MainActivity)this.getActivity();
            TransmitStateObject tso = activity.getClassObject().getTransmitStateObject();
            if(tso != null){
                if(tso.getAttendanceTranmitEndTime() != null){
                    this.mTimer.cancel();
                    this.mTimer = null;
                }
            }
        }*/
    }



    /**
     * Created by scr on 2014/12/11.
     * getNetworkRoomInfomationメソッド
     * ネットワーク上のDBに教室情報を取得しにいく.
     */
    /*private void getNetworkRoomInfomation(){
        MainActivity activity = (MainActivity)this.getActivity();
        //教室情報を取得しに行く.
        //String url = URL.ROOM_MAP+"/"+activity.getRoomId();
        String url = URL.ROOM_MAP+"/"+activity.mTeacher.getClassPlan().getPlace().getRoom().getRoomId();
                JsonObjectRequest request = new JsonObjectRequest(
                JsonObjectRequest.Method.GET,
                url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //Log.d(TAG, response.toString());
                        roomJsonParser(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //VolleyLog.d(TAG, "Error: " + error.getMessage());
                // hide the progress dialog
                // エラー時の処理...
                Toast.makeText(getActivity(), "Unable to fetch data: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        AppController.getInstance(this.getActivity()).getRequestQueue().add(request);
    }*/

    /**
     * Created by scr on 2014/12/11.
     * roomJsonParserメソッド
     * ネットワークから取得した教室情報Jsonを解析する.
     * @param json ネットワークDBから取得した教室情報
     */
    /*private void roomJsonParser(JSONObject json){
        Gson mygson = new Gson();
        //List<Teacher> teachers = new ArrayList<Teacher>();
        //Type collectionType = new TypeToken<Room>(){}.getType();
        Room room = mygson.fromJson(json.toString(),Room.class);
        setRoomInfomation(room);
    }*/

    /**
     * Created by scr on 2014/12/11.
     * setRoomInfomationメソッド
     * RoomViewクラスにネットワークDBから取得した教室情報を流す
     */
    /*private void setRoomInfomation(Room room){
        this.roomView.setRoomMap(room);
        //教室オープン
        showRoomMap();
        //出席者を取得し続ける
        mTimer = new Timer();
        mTimer.schedule(new AccessTimerTask(this), AccessConfig.WAIT_TIME, AccessConfig.RE_ATTENDANCE_ACCESS_TIME);
    }*/

    /**
     * Created by scr on 2014/12/12.
     * getNetworkAttendanceInfoメソッド
     * ネットワークのデータベースに出席者の情報を取得しにいく.
     */
    /*public void getNetworkAttendanceInfo(){
        MainActivity activity = (MainActivity)this.getActivity();
        //教室情報を取得しに行く.
        //String url = URL.ATTENDANCE_INFO+"/"+activity.getClassPlanId();
        String url =  URL.ATTENDANCE_INFO+"/"+activity.mTeacher.getClassPlan().getClassId();
        JsonArrayRequest request = new JsonArrayRequest(
                url,
                new Response.Listener<JSONArray>() {
                    @Override public void onResponse(JSONArray response) {
                        // レスポンス受け取り時の処理...
                        //attendanceJsonParser(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override public void onErrorResponse(VolleyError error) {
                        // エラー時の処理...
                        Toast.makeText(getActivity(), "Unable to fetch data: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        AppController.getInstance(this.getActivity()).getRequestQueue().add(request);
    }*/

    /**
     * Created by scr on 2014/12/12.
     * attendanceJsonParserメソッド
     * ネットワークDBから取得した出欠席Jsonを解析する.
     * @param jsonArray ネットワークDBから取得した出欠席情報
     */
    /*public void attendanceJsonParser(JSONArray jsonArray){
        Gson mygson = new Gson();
        Type collectionType = new TypeToken<Collection<Atteandance>>(){}.getType();
        //出欠席者を取得する
        List<Atteandance> attendance = mygson.fromJson(jsonArray.toString(),collectionType);
        //教室Viewに出欠席者の情報を渡す.
        this.roomView.setAttendance(attendance);
    }*/

    /**
     * Created by scr on 2014/12/11.
     * showRoomMapメソッド
     * ロード画面を非表示にし、
     * 教室画面をひょうじする.
     */
    public void showRoomMap(){
        loadRoomMapProgressBar.setVisibility(View.GONE);
        mSeatSituationLl.setVisibility(View.VISIBLE);
    }

    @Override
    public void onPause(){
        super.onPause();
        if(mTimer != null){
            mTimer.cancel();
            mTimer = null;
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(getArguments().getInt(ARG_SECTION_NUMBER));
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }
    int imgCounter = 0;
    /**
     * Created by Shota Ito on 2015/1/4
     * openDialogFragmentShowCellInfoメソッド
     * RoomViewをユーザーがタップした場合にアラートが立ち上がる.
     * タップしたセル位置の情報をアラートダイアログに出力する.
     * @param student 学生クラス
     */
    public void openDialogFragmentShowCellInfo(StudentObject so){
        Drawable drawable = null;
        if( imgCounter % 2 == 0 ){
            drawable = getResources().getDrawable(R.drawable.k16125);
        }else{
            drawable = getResources().getDrawable(R.drawable.k13097);
        }
        ++imgCounter;
        mFaceIv.setImageDrawable( drawable );
        mStudentIdNumberTv.setText( so.getStudentIdNumber() + " (" + getSeatPosition(so.getSeatObject())  + ")" );
        mFuriganaTv.setText( so.getFurigana() );
        mFullNameTv.setText(  so.getFullName() );
        //mPositionTv.setText( getSeatPosition(so.getSeatObject()) );
        //studentInfoDialogFragnemt = new StudentInfoCustomDialog();
        //studentInfoDialogFragnemt.showForSeatSituationFragment(this, so);
    }
    /**
     * Created by Shota Ito on 2016/12/13
     * getSeatPositionメソッド
     * 座席位置(グループ名称)を取得します.
     * @param student 学生クラス
     */
    String getSeatPosition(SeatObject so){
        String groupNumber = so.getGroupName();
        String seatName = so.getSeatName();
        String strGroupNumber    = "グループ ";
        String strDemiliter      = "　";
        String strSeatPosition   = "着席位置 ";
        String str = "";
        if(groupNumber != null){
            if( groupNumber.length() > 0 ){
                //  グループ名称関係
                str = str + strGroupNumber +" "+ groupNumber + strDemiliter;
            }
        }
        if( seatName != null ){
            //  座席名称関係
            str = str + strSeatPosition +" "+ seatName;
        }
        return str;
    }

    /*public void openDialogFragmentShowCellInfo(Student student){
        MainActivity activity = (MainActivity)this.getActivity();
        CustomDialogFragment customDialog = CustomDialogFragment.newInstance();
        customDialog.setTargetFragment(SeatSituationFragment.this,1);
        Bundle bundle = new Bundle();
        String sameClassNumber = activity.mTeacher.getClassPlan().getSameClassNumber();
        bundle.putString(CustomDialogFragment.SAME_CLASS_NUMBER,sameClassNumber);
        bundle.putSerializable(CustomDialogFragment.ATTENDANCE_STUDENT_INFO,student);
        customDialog.setArguments(bundle);
        customDialog.show(activity.getSupportFragmentManager(), "customDialog");
    }*/

    @Override
    public void onOkClicked(Bundle args) {
        //座席再描画
        this.roomView.invalidate();
    }
    /*
    public void demo(List<Student> attendance){
       Timer mainTimer = new Timer();
        MainActivity activity = (MainActivity)this.getActivity();
        activity.setTimer(mainTimer);
        DemoReCollTheRollTimer reTimer  = new DemoReCollTheRollTimer(activity,DemoReCollTheRollTimer.MODE_SEATSITUATION_FRAGMENT,attendance);
        mainTimer.schedule(reTimer, 6000,1000);
        this.onResume();
    }
    */
}