package jp.pmw.test_en_revolution;

import android.app.Activity;
import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;
import java.util.Timer;

import jp.pmw.test_en_revolution.config.URL;
import jp.pmw.test_en_revolution.room.AccessConfig;
import jp.pmw.test_en_revolution.room.AccessTimerTask;
import jp.pmw.test_en_revolution.room.Room;
import jp.pmw.test_en_revolution.room.RoomView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SeatSituationFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SeatSituationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SeatSituationFragment extends Fragment {
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
    private ProgressBar loadRoomMapProgressBar;
    /*教室用のView*/
    private RoomView roomView;
    /*出欠席者の情報を再取得しに行くためのタイマー*/
    private Timer mTimer;

    public SeatSituationFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_seat_situation, container, false);

        /*RoomView rv = new RoomView(getActivity());
        rv.setRoomMap();*/
        /*テスト用*/
        /*MainActivity main = (MainActivity)getActivity();
        main.test(rv,rv.getRoomMap().getCells());*/
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        /*ロード用*/
        loadRoomMapProgressBar = (ProgressBar)this.getActivity().findViewById(R.id.seat_situation_load);
        /*教室マップを描くよう*/
        roomView = (RoomView)this.getActivity().findViewById(R.id.view);
    }

    @Override
    public void onResume(){
        super.onResume();
        /*テスト確認用*/
        //roomView.setRoomMap();
        //showRoomMap();
        /*テスト確認用*/

        /*教室情報を取得しに行く*/
        getNetworkRoomInfomation();

    }

    /**
     * Created by scr on 2014/12/11.
     * getNetworkRoomInfomationメソッド
     * ネットワーク上のDBに教室情報を取得しにいく.
     */
    private void getNetworkRoomInfomation(){
        MainActivity activity = (MainActivity)this.getActivity();
        //教室情報を取得しに行く.
        String url = URL.ROOM_MAP+"/"+activity.getRoomId();
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
    }

    /**
     * Created by scr on 2014/12/11.
     * roomJsonParserメソッド
     * ネットワークから取得した教室情報Jsonを解析する.
     * @param json ネットワークDBから取得した教室情報
     */
    private void roomJsonParser(JSONObject json){
        Gson mygson = new Gson();
        //List<Teacher> teachers = new ArrayList<Teacher>();
        //Type collectionType = new TypeToken<Room>(){}.getType();
        Room room = mygson.fromJson(json.toString(),Room.class);
        setRoomInfomation(room);
    }

    /**
     * Created by scr on 2014/12/11.
     * setRoomInfomationメソッド
     * RoomViewクラスにネットワークDBから取得した教室情報を流す
     */
    private void setRoomInfomation(Room room){
        this.roomView.setRoomMap(room);
        //教室オープン
        showRoomMap();
        /*出席者を取得し続ける*/
        mTimer = new Timer();
        mTimer.schedule(new AccessTimerTask(this), AccessConfig.WAIT_TIME, AccessConfig.RE_ATTENDANCE_ACCESS_TIME);
    }

    /**
     * Created by scr on 2014/12/12.
     * getNetworkAttendanceInfoメソッド
     * ネットワークのデータベースに出席者の情報を取得しにいく.
     */
    public void getNetworkAttendanceInfo(){
        MainActivity activity = (MainActivity)this.getActivity();
        //教室情報を取得しに行く.
        String url = URL.ATTENDANCE_INFO+"/"+activity.getClassPlanId();
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
                    }
                });
        AppController.getInstance(this.getActivity()).getRequestQueue().add(request);
    }

    /**
     * Created by scr on 2014/12/12.
     * attendanceJsonParserメソッド
     * ネットワークDBから取得した出欠席Jsonを解析する.
     * @param jsonArray ネットワークDBから取得した出欠席情報
     */
    public void attendanceJsonParser(JSONArray jsonArray){
        Gson mygson = new Gson();
        Type collectionType = new TypeToken<Collection<Atteandance>>(){}.getType();
        //出欠席者を取得する
        List<Atteandance> attendance = mygson.fromJson(jsonArray.toString(),collectionType);
        //教室Viewに出欠席者の情報を渡す.
        this.roomView.setAttendance(attendance);
    }

    /**
     * Created by scr on 2014/12/11.
     * showRoomMapメソッド
     * ロード画面を非表示にし、
     * 教室画面をひょうじする.
     */
    public void showRoomMap(){
        loadRoomMapProgressBar.setVisibility(View.GONE);
        roomView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onStop(){
        super.onStop();
        //タイマーキャンセル
        this.mTimer.cancel();
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
}