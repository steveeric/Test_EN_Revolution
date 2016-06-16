package jp.pmw.test_en_revolution.grouping;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import jp.pmw.test_en_revolution.AppController;
import jp.pmw.test_en_revolution.MainActivity;
import jp.pmw.test_en_revolution.MyMainFragment;
import jp.pmw.test_en_revolution.R;
import jp.pmw.test_en_revolution.config.URL;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link GroupingFragment_1.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link GroupingFragment_1#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GroupingFragment_1 extends MyMainFragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static GroupingFragment_1 newInstance(int sectionNumber) {
        GroupingFragment_1 fragment = new GroupingFragment_1();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public GroupingFragment_1() {
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(getArguments().getInt(ARG_SECTION_NUMBER));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_grouping_1, container, false);
        return view;
    }
    //
    private LinearLayout firstLinearLayout;
    //
    private LinearLayout endLinearLayout;
    //リストビュー
    private ListView groupingSelectListView;
    //
    private TextView assistTextView;
    //実行ボタン
    private Button runBtn;
    //
    public Grouping selectGrouping;
    //着席状況フラグメントへ確認ボタン
    private Button confirmSeatBtn;
    //
    private GroupingAdapter adapter;

    public void setSelectGrouping(Grouping g){
        this.selectGrouping = g;
        this.adapter.setSelectGrouping(g);
        this.adapter.notifyDataSetChanged();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        this.firstLinearLayout = (LinearLayout)this.getActivity().findViewById(R.id.fragment_grouping_1_first_linearLayout);
        this.firstLinearLayout.setVisibility(View.VISIBLE);
        this.groupingSelectListView = (ListView)this.getActivity().findViewById(R.id.fragment_grouping_1_listView);
        // アイテムクリック時ののイベントを追加
        this.groupingSelectListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent,
                                    View view, int pos, long id) {
                // 選択アイテムを取得
                ListView listView = (ListView)parent;
                Grouping item = (Grouping)listView.getItemAtPosition(pos);
                setSelectGrouping(item);
                assistTextView.setText("１グループ"+selectGrouping.getGroupingName()+"が選択されています.");
                runBtn.setVisibility(View.VISIBLE);
            }
        });
        //this.runBtn = (Button)this.getActivity().findViewById(R.id.fragment_grouping_1_run_button);
        //this.runBtn.setOnClickListener(runListener);
        this.assistTextView = (TextView)this.getActivity().findViewById(R.id.fragment_grouping_1_assist_textView);

        this.endLinearLayout = (LinearLayout)this.getActivity().findViewById(R.id.fragment_grouping_1_end_linearLayout);
        this.endLinearLayout.setVisibility(View.GONE);

        this.confirmSeatBtn = (Button)this.getActivity().findViewById(R.id.fragment_grouping_1_end_group_confirm_button);
        this.confirmSeatBtn.setOnClickListener(confirmListener);
    }

    @Override
    public void onResume(){
        super.onResume();

        List<Grouping> demoGrouping = new ArrayList<Grouping>();
        demoGrouping.add(new Grouping(2,"2人"));
        demoGrouping.add(new Grouping(4,"4人"));
        demoGrouping.add(new Grouping(6,"6人"));

        adapter = new GroupingAdapter(this.getActivity(),0,demoGrouping);
        groupingSelectListView.setAdapter(adapter);

    }

    /*private View.OnClickListener runListener = new View.OnClickListener() {
        public void onClick(View v) {
            switch(v.getId()) {
                case R.id.fragment_grouping_1_run_button:
                    doGrouping();
                    break;
            }
        }
    };*/

    private View.OnClickListener confirmListener = new View.OnClickListener() {
        public void onClick(View v) {
            switch(v.getId()) {
                case R.id.fragment_grouping_1_end_group_confirm_button:
                    moveToSitTituationFragment();
                    break;
            }
        }
    };

    /**
     * Created by scr on 2015/1/6.
     * moveToSitTituationFragmentメソッド
     * 着席状況確認フラグメントに移動する.
     */
    private void moveToSitTituationFragment(){
        MainActivity activity = (MainActivity)this.getActivity();
        //着席一覧
        activity.moveToSeatSituationFragment();
    }

    /*private void doGrouping(){
        MainActivity activity = (MainActivity)this.getActivity();
        //授業ID
        String classId = activity.mTeacher.getClassPlan().getClassId();
        int member = selectGrouping.getGroupingCount();
        String url = URL.getDoGrouping(classId,member);
        JsonObjectRequest request = new JsonObjectRequest(url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        reload();
                        paserJson(response);
                    }
                }, new Response.ErrorListener() {
            @Override public void onErrorResponse(VolleyError error) {
                showAlertDialog("エラー","グルーピングを行うことができませんでした.");
                Toast.makeText(getActivity(), "Unable to fetch data: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        AppController.getInstance(this.getMainActivity()).getRequestQueue().add(request);
    }

    public void reload(){
        MainActivity activity = (MainActivity)this.getActivity();
        activity.mTeacher.resetRoster();
        activity.mTeacher.resetTmpRoomInfo();
        activity.onResume();
    }

    public void paserJson(JSONObject response){
        Gson gson = new Gson();
        GroupingResult result = gson.fromJson(response.toString(), GroupingResult.class);
        if(result.getResult() != 0){
            //エラー
            showAlertDialog("エラー",result.getReason());
        }else{
            //初期画面を消す
            this.firstLinearLayout.setVisibility(View.GONE);
            //座席確認画面を表示する
            this.endLinearLayout.setVisibility(View.VISIBLE);
        }
    }*/

    public void showAlertDialog(String title,String message){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this.getActivity());
        alertDialogBuilder.setTitle(title);
        alertDialogBuilder.setMessage(message);
        alertDialogBuilder.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });

        // アラートダイアログのキャンセルが可能かどうかを設定します
        alertDialogBuilder.setCancelable(false);
        AlertDialog alertDialog = alertDialogBuilder.create();
        // アラートダイアログを表示します
        alertDialog.show();
    }

}
