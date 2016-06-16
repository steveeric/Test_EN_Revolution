package jp.pmw.test_en_revolution.grouping;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import jp.pmw.test_en_revolution.MainActivity;
import jp.pmw.test_en_revolution.MyMainFragment;
import jp.pmw.test_en_revolution.R;
import jp.pmw.test_en_revolution.common.CommonDialogFragment;
import jp.pmw.test_en_revolution.confirm_class_plan.Seat;
import jp.pmw.test_en_revolution.room.dummy.DummyRoomMapContent;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link GroupingFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link GroupingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GroupingFragment extends MyMainFragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static GroupingFragment newInstance(int sectionNumber) {
        GroupingFragment fragment = new GroupingFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }
    //キーボードを隠すため
    private InputMethodManager inputMethodManager;

    private LinearLayout duaringAttendance;
    //グループ関係を選ぶレイアウト
    private LinearLayout startLinearLayout;
    //数字を入力する関係のレイアウト
    private LinearLayout inputLinearLayout;
    private LinearLayout selectOneGroupCountLinearLayout;
    private TextView assistFrontTextView;
    private TextView assistTextView;
    private LinearLayout endLinearLayout;

    private RadioGroup groupButton;
    private TextView selectContentTextView;
    private EditText inputEditText;
    private Button runBtn;
    private Button confirmSitBtn;

    private TextView assistMessageTextView;

    public GroupingFragment() {
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(getArguments().getInt(ARG_SECTION_NUMBER));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_grouping, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.duaringAttendance = (LinearLayout)this.getActivity().findViewById(R.id.fragment_grouping_during_attendance_linearLayout);
        this.startLinearLayout = (LinearLayout)this.getActivity().findViewById(R.id.fragment_grouping_start_linearLayout);
        this.inputLinearLayout = (LinearLayout)this.getActivity().findViewById(R.id.fragment_grouping_input_linearLayout);
        this.selectOneGroupCountLinearLayout = (LinearLayout)this.getActivity().findViewById(R.id.select_one_group_count_linearLayout);
        this.assistFrontTextView = (TextView)this.getActivity().findViewById(R.id.fragment_grouping_number_input_assist1_textView);
        this.assistTextView = (TextView)this.getActivity().findViewById(R.id.fragment_grouping_number_input_assist_textView);
        this.endLinearLayout = (LinearLayout)this.getActivity().findViewById(R.id.fragment_grouping_end_linearLayout);

        this.assistMessageTextView = (TextView)this.getActivity().findViewById(R.id.fragment_grouping_number_assist_message_textView);

        this.selectContentTextView = (TextView)this.getActivity().findViewById(R.id.select_content_message_textView);
        this.groupButton = (RadioGroup)this.getActivity().findViewById(R.id.fragment_grouping_grouping_radioGroup);
        this.inputEditText = (EditText)this.getActivity().findViewById(R.id.fragment_grouping_number_input_editText);
        this.inputEditText.addTextChangedListener(watchHandler);
        //キーボード表示を制御するためのオブジェクト
        inputMethodManager = (InputMethodManager)this.getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

        this.runBtn = (Button)this.getActivity().findViewById(R.id.fragment_grouping_run_button);
        //this.runBtn.setOnClickListener(runListener);
        // 指定した ID の RadioButton を選択
        int checkResorce = R.id.fragment_grouping_group_number_radioButton;
        //this.groupButton.check(checkResorce);
        //setSelectContentTextView(checkResorce);

        this.groupButton.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            // ラジオグループのチェック状態が変更された時に呼び出されます
            // チェック状態が変更されたラジオボタンのIDが渡されます
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                selectRadio(group,checkedId);
            }
        });
        this.confirmSitBtn = (Button)this.getActivity().findViewById(R.id.fragment_grouping_end_group_confirm_button);
        this.confirmSitBtn.setOnClickListener(confirmSitLister);
    }

    @Override
    public void onResume(){
        super.onResume();
        MainActivity activity = (MainActivity)this.getActivity();
        this.duaringAttendance.setVisibility(View.GONE);
        this.startLinearLayout.setVisibility(View.VISIBLE);
        this.endLinearLayout.setVisibility(View.GONE);
        /*if(activity.mTeacher.getEndAttendanceFlag() == true){
            GroupingManagement gpManagement = activity.mTeacher.getGroupingManagement();
            if(gpManagement.getDoGroupingFlag() == true){
                //
                this.duaringAttendance.setVisibility(View.GONE);
                this.startLinearLayout.setVisibility(View.GONE);
                this.endLinearLayout.setVisibility(View.VISIBLE);
            }else{
                //グルーピング開始.
                this.duaringAttendance.setVisibility(View.GONE);
                this.startLinearLayout.setVisibility(View.VISIBLE);
                this.endLinearLayout.setVisibility(View.GONE);
            }
        }else{
            this.duaringAttendance.setVisibility(View.VISIBLE);
            this.startLinearLayout.setVisibility(View.GONE);
            this.endLinearLayout.setVisibility(View.GONE);
        }*/

    }

    /**
     * Created by scr on 2015/1/6.
     * setSelectContentTextViewメソッド
     * 選択されたグループボタンの内容に応じて何内を変えます.
     */
    int[] assistList = {2,3,4};
   /* private void setSelectContentTextView(int checkResorce) {
        MainActivity activity = (MainActivity)this.getActivity();
        String str = "";
        String assist = "";
        //new int[3];
        oneGroupAssistCount.clear();
        int attendeeCount = activity.mTeacher.getRoster().getTodayAttendeeCount();
        int group = R.id.fragment_grouping_group_number_radioButton;
        int member = R.id.fragment_grouping_member_number_radioButton;
        if (checkResorce == group) {

            //グループ数選択
            assist = this.getActivity().getString(R.string.grouping_group_select_group_assist);
            str = this.getActivity().getString(R.string.grouping_group_select_group_number);
            this.assistFrontTextView.setVisibility(View.INVISIBLE);

            /*
            for(int i=0;i<assistList.length;i++){
                int amari = attendeeCount % assistList[i];
                if(amari == 0){
                    int aa = attendeeCount / assistList[i];
                    oneGroupAssistCount.add(aa);
                }
            }
            */
           /* oneGroupAssistCount.clear();
            String roomId = activity.mTeacher.getClassPlan().getPlace().getRoom().getRoomId();
            if(roomId.equals(DummyRoomMapContent.ROOM_135_ID)){
                oneGroupAssistCount.add(1);
                oneGroupAssistCount.add(2);
                oneGroupAssistCount.add(3);
            }else if(roomId.equals(DummyRoomMapContent.ROOM_1317_ID)){
                oneGroupAssistCount.add(1);
                oneGroupAssistCount.add(2);
                oneGroupAssistCount.add(3);
                oneGroupAssistCount.add(4);
                oneGroupAssistCount.add(5);
            }else if(roomId.equals(DummyRoomMapContent.ROOM_241_ID)){
                oneGroupAssistCount.add(1);
                oneGroupAssistCount.add(2);
                oneGroupAssistCount.add(3);
                oneGroupAssistCount.add(4);
            }


            //昇順でソートする
            Collections.sort(oneGroupAssistCount);

        String message = "グループ総数は"+oneGroupAssistCount.get(0)+"～"+oneGroupAssistCount.get(oneGroupAssistCount.size()-1)+"のどれかを入力できます.";
        assistMessageTextView.setText(message);

        } else if (checkResorce == member) {
            //メンバー数選択
            assist = this.getActivity().getString(R.string.grouping_group_select_menber_assist);
            str = this.getActivity().getString(R.string.grouping_group_select_menber_number);
            this.assistFrontTextView.setVisibility(View.VISIBLE);


            for(int i=0;i<assistList.length;i++){
                int amari = attendeeCount % assistList[i];
                if(amari == 0){
                    oneGroupAssistCount.add(assistList[i]);
                }
            }

            String message = "";
            if(this.oneGroupAssistCount.size() > 0) {
                for (int i = 0; i < this.oneGroupAssistCount.size(); i++) {
                    message = message + "[" + oneGroupAssistCount.get(i) + "]";
                }
                message = message+"を入力することができます.";
            }else{
                message = "均等にグループを分けることができません.";
            }

            assistMessageTextView.setText(message);
        }

        this.selectContentTextView.setText(str);
        this.assistTextView.setText(assist);
    }*/

    private List<Integer> oneGroupAssistCount = new ArrayList<Integer>();

    /**
     * Created by scr on 2015/1/6.
     * selectRadioメソッド
     * グループラジオボタンの変化をキャッチするメソッド.
     */
    private int _chekedId;
    private void selectRadio(RadioGroup group,int checkedId){
        _chekedId = checkedId;
        RadioButton radioButton = (RadioButton)this.getActivity().findViewById(checkedId);
        //setSelectContentTextView(checkedId);
       // this.selectContentTextView.setText(radioButton.getText() + this.getResources().getString(R.string.grouping_group_enter));
        //入力画面出現
        this.selectOneGroupCountLinearLayout.setVisibility(View.VISIBLE);
    }

    /**
     * Created by scr on 2015/1/6.
     * 実行ボタン用のリスナー
     */
    /*private View.OnClickListener runListener = new View.OnClickListener() {
        public void onClick(View v) {
            switch(v.getId()) {
                case R.id.fragment_grouping_run_button:
                    checkRunGouping();
                    break;
            }
        }
    };*/
    /**
     * Created by scr on 2015/1/6.
     * 着席状況確認用のリスナー
     */
    private View.OnClickListener confirmSitLister = new View.OnClickListener() {
        public void onClick(View v) {
            switch(v.getId()) {
                case R.id.fragment_grouping_end_group_confirm_button:
                    moveToSitTituationFragment();
                    break;
            }
        }
    };
    /**
     * Created by scr on 2015/1/6.
     * テキストフィールド監視ハンドラ―
     */
    private TextWatcher watchHandler = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }
        @Override
        public void afterTextChanged(Editable s) {
            if(s.length() > 0){
                int value = Integer.valueOf(s.toString());
                /*if(value > 0) {
                    runBtn.setVisibility(View.VISIBLE);
                }*/
                int group = R.id.fragment_grouping_group_number_radioButton;
                int member = R.id.fragment_grouping_member_number_radioButton;

                if (_chekedId == group) {

                    //グループ数選択
                    if(oneGroupAssistCount.get(0) <= value && value <= oneGroupAssistCount.get(oneGroupAssistCount.size()-1)){
                        runBtn.setVisibility(View.VISIBLE);
                    }else{
                        //受け付けることができませんアラート出す.
                        canNotAcceptInputValue();
                    }
                } else if (_chekedId == member) {
                    //メンバー数選択
                    boolean b = false;
                    for(int i=0;i<oneGroupAssistCount.size();i++){
                        if(value == oneGroupAssistCount.get(i)){
                            b = true;
                            runBtn.setVisibility(View.VISIBLE);
                        }
                    }
                    if(b == false){
                        //受け付けることができませんアラート出す.
                        canNotAcceptInputValue();
                    }
                }




            }else{
                runBtn.setVisibility(View.INVISIBLE);
            }
        }
    };

    private void canNotAcceptInputValue(){
        MainActivity activity = (MainActivity)this.getActivity();
        activity.openNotAcceptInputValueAlertDialog();
    }


    /**
     * Created by scr on 2015/1/6.
     * moveToSitTituationFragmentメソッド
     * 着席状況確認フラグメントに移動する.
     */
    private void moveToSitTituationFragment(){
        MainActivity activity = (MainActivity)this.getActivity();

        //ダミーグルーピングデータをセットします.
        //setTestDummyGroupingDate(activity);
        //ダミーグルーピングデータをセットします.(Ver2)
        //setTestDummySelectTotalGroupCount(activity);


        //着席一覧
        activity.moveToSeatSituationFragment();
    }

    /**
     * Created by scr on 2015/1/7.
     * setDummySeatForFirstGroupingメソッド
     * グルーピング用のグループ内最初以外の学生が座る座席位置をセットします.
     * @param  maxVerticalCount 座席列方向にどれだけいくか
     * @param  row 座席行
     * @param  column 座席列
     * @param  nextColumn 次の座席列
     * @param useSeat 使用可能な座席リスト
     * @param  nexts グループ内の2番目以降の学生が使用する座席リスト
     */
    private void setDummySeatForFirstGrouping(int maxVerticalCount,int row,int column,
                                             int nextColumn,List<Seat> useSeat,List<Seat>nexts){
        for (int k = 0; k < maxVerticalCount; k++) {
            int nextRow = row + (k + 1) * 2;
            for (int j = 0; j < nexts.size(); j++) {
                if (nexts.get(j).getRow() == nextRow
                        && nexts.get(j).getColumn() == column) {
                    Seat nextSeat = nexts.get(j);
                    useSeat.add(nextSeat);
                    //過去の座席列情報
                    nextColumn = column+2;
                    break;
                }
            }
        }
    }


   /* private void setColumnSeat(int[] membersCount,int checker,int nextColumn,List<Seat> groupingSeat,int oneGroupCounter,int nowRow,List<Seat> verticalSeats,int maxVerticalSeatCount,int vercitalNowCount,int nowColun){
        int nextRow = nowRow + 2;
        for (int j = 0; j < verticalSeats.size(); j++) {
            if (vercitalNowCount == (maxVerticalSeatCount - 1)) {
                //もう列方向はいらない.
                int a = 0;
                a = 12;
                //nextColumn = nextColumn + 2;

                break;
            } else {
                if (verticalSeats.get(j).getRow() == nextRow
                        && verticalSeats.get(j).getColumn() == nowColun) {

                    Seat verticalSeat = verticalSeats.get(j);
                    //加える
                    groupingSeat.add(verticalSeat);
                    ++oneGroupCounter;

                    ++vercitalNowCount;
                    nextRow = nextRow + 2;
                    if(oneGroupCounter == membersCount[checker]){
                        int a = 0;
                        a = 12;
                        break;
                    }
                }
            }
        }
    }*/

   /* private void setTestDummySelectTotalGroupCount(MainActivity activity,int requestCount){
        Faculty teacher = activity.mTeacher;
        GroupingManagement groupingManagement = teacher.getGroupingManagement();

        //本日の出席者
        //List<Student> attendee = activity.mTeacher.getRoster().getTodayAttendee();
        //本日の出席者数
        //int attendeeCount = attendee.size();

        //4グループ
        //int requestCount = 3;

        //int[] membersCount = groupingManagement.setRequestGroupingMode(0, requestCount,attendeeCount);

        //新たな着席位置を取得する.



        //列用シート
        //List<Seat> verticalSeats = activity.readDummySeatMst();
        //横用シート
        //List<Seat> horizontalSeats = activity.readDummySeatMst();
        /*
        String roomId = teacher.getClassPlan().getPlace().getRoom().getRoomId();
        String fileName = this.getGroupingSeatMst(roomId);
        //列用シート
        List<Seat> verticalSeats = activity.readDummySeatMst(fileName);
        //横用シート
        List<Seat> horizontalSeats = activity.readDummySeatMst(fileName);

        //座席シートの割り当て方法を算出する.
        int maxVerticalSeatCount = 0;
        int preVertical = membersCount[0] / 2;
        //maxVerticalSeatCount = preVertical;
        if(preVertical % 2 != 0) {
            //割れなかった.
            //maxVerticalSeatCount = ++preVertical;
            int pre = membersCount[0] + 1;
            maxVerticalSeatCount = pre /2;
        }else {
            maxVerticalSeatCount = preVertical;
        }


        //グルーピング用の座席
        List<Seat> groupingSeat = new ArrayList<Seat>();

        int checker = 0;
        //1グループに何人いるかを数える.
        int oneGroupCounter = 0;
        int nextRow = 0;
        int nextColumn = 0;
        boolean firstFlag = true;
        for(int i = 0; i < horizontalSeats.size(); i++) {
            boolean nextRowAddFlag = false;
            int vercitalNowCount = 0;
            Seat horizontalSeat = horizontalSeats.get(i);
            int nowRow = horizontalSeat.getRow();
            int nowColun = horizontalSeat.getColumn();
           if(firstFlag == true) {
            //初回なので無常家で座席を加える.
               //加える
               groupingSeat.add(horizontalSeat);
               ++oneGroupCounter;
               nextColumn = nowColun + 2;

               firstFlag = false;
               nextRowAddFlag = true;
           }else{
               if(nextColumn == nowColun){
                   groupingSeat.add(horizontalSeat);
                   ++oneGroupCounter;
                   nextColumn = nowColun + 2;
                   if (oneGroupCounter < (membersCount[checker] - 1)) {
                       //縦に席を取りに行く
                       nextRowAddFlag = true;
                   }else{
                       nextRowAddFlag = false;
                   }
               }
           }
            if(nextRowAddFlag == true) {
                //列方向の座席を加える
                //setColumnSeat(membersCount,checker,nextColumn,groupingSeat,oneGroupCounter,nowRow, verticalSeats, maxVerticalSeatCount, vercitalNowCount, nowColun);

                nextRow = nowRow + 2;
                for (int j = 0; j < verticalSeats.size(); j++) {
                    if (vercitalNowCount == (maxVerticalSeatCount - 1)) {
                        //もう列方向はいらない.
                        int a = 0;
                        a = 12;
                        //nextColumn = nextColumn + 2;

                        break;
                    } else {
                        if (verticalSeats.get(j).getRow() == nextRow
                                && verticalSeats.get(j).getColumn() == nowColun) {

                            Seat verticalSeat = verticalSeats.get(j);
                            //加える
                            groupingSeat.add(verticalSeat);
                            ++oneGroupCounter;

                            ++vercitalNowCount;
                            nextRow = nextRow + 2;
                            if(oneGroupCounter == membersCount[checker]){
                                int a = 0;
                                a = 12;
                                break;
                            }
                        }
                    }
                }
                nextRowAddFlag = false;
            }
            //チェッカー
            //if ((oneGroupCounter) >= (membersCount[checker] -1)) {
            if ((oneGroupCounter) == (membersCount[checker])) {
                //次のグループに移動
                oneGroupCounter = 0;
                ++checker;
                if(checker == (membersCount.length) ){
                    horizontalSeat = horizontalSeats.get(i);
                    groupingSeat.add(horizontalSeat);
                    break;
                }
            }
        }

        //初期化
        checker = 0;
        oneGroupCounter = 0;
        int groupNumber = 1;
        //座席に学生を割り当てる
        for(int i = 0; i < attendee.size(); i++){
            if(oneGroupCounter == (membersCount[checker])){
                //次のグループへ
                ++groupNumber;
                oneGroupCounter = 0;
            }

            //座席を割り振る
            attendee.get(i).getThisClassTime().setThisClassSittingPosition(groupingSeat.get(i));
            //グループ番号を割り当てる.
            attendee.get(i).getThisClassTime().setThisClassGroup(new Group(""+groupNumber));
            ++oneGroupCounter;
        }


        //学生のグルーピング情報をセット
        activity.mTeacher.getGroupingManagement().setGroupings(attendee);

        //グルーピング終わり.
        groupingManagement.setDoGroupingFlag();
    }*/
    /*private void setTestDummyGroupingDate(MainActivity activity){
        Faculty teacher = activity.mTeacher;
        GroupingManagement groupingManagement = teacher.getGroupingManagement();

        //11人出席していて,
        //グループ数３の場合
        int mode = 1;
        int requestCount = 4;
        int mini = 3;
        int max = 4;


        //本日の出席者
        List<Student> attendee = activity.mTeacher.getRoster().getTodayAttendee();
        //グループを割り当てる.
        //List<Group> groups = new ArrayList<Group>();
        //本日の出席者数
        int attendeeCount = attendee.size();

        int maxVerticalCount = 0;
        //偶数フラグ
        int evenFlag = 0;
        //お願いします.
        //ぐ
        int[] members = groupingManagement.setRequestGroupingMode(mode, requestCount,attendeeCount);
        //最大と最少をセット
        groupingManagement.setGroupingInfomagion(members[0],members[1]);


        int totalGroupCount = 5;
        //①本日の出席者/グループ数
        int ress = attendeeCount%totalGroupCount;
        //①ダッシュ
        boolean b = false;
        if(ress == 1) {
            //偶数から奇数フラグに変更
            evenFlag = 0;
            while (!b) {
                ++attendeeCount;
                int l = attendeeCount % totalGroupCount;
                if(l == 0){
                    b = true;
                    break;
                }
            }
        }
        //②一グループ何人になるかを出す.
        int oneGroupMemberCount = attendeeCount/totalGroupCount;

        //③列方向にどれだけ行くかを出す.
        //先頭列は頭数から抜く
        maxVerticalCount = (oneGroupMemberCount / 2);
        if(maxVerticalCount > 1){
            --maxVerticalCount;
        }

        //新たな着席位置を取得する.
        List<Seat> seats = activity.readDummySeatMst();
        List<Seat> nexts = activity.readDummySeatMst();

        List<Seat> useSeat = new ArrayList<Seat>();

        //いくつ次の行を検索するか

        int oneGroupCounter = 0;

        int verticalCount = 0;
        int nextColumn = 0;
        for(int i =0 ;i<seats.size();i++) {
            Seat nowSeat = seats.get(i);
            int row = nowSeat.getRow();
            int column = nowSeat.getColumn();
            if (nextColumn == 0) {
                useSeat.add(nowSeat);
                for (int k = 0; k < maxVerticalCount; k++) {
                    int nextRow = row + (k + 1) * 2;
                    for (int j = 0; j < nexts.size(); j++) {
                        if (nexts.get(j).getRow() == nextRow
                                && nexts.get(j).getColumn() == column) {
                            Seat nextSeat = nexts.get(j);
                            useSeat.add(nextSeat);
                            //過去の座席列情報
                            nextColumn = column + 2;
                            oneGroupCounter++;
                            break;
                        }
                    }
                }
                //setDummySeatForFirstGrouping(maxVerticalCount,row,column,nextColumn,useSeat,nexts);
            } else {
                if (seats.get(i).getRow() == row
                        && seats.get(i).getColumn() == nextColumn) {
                    useSeat.add(nowSeat);
                    for (int k = 0; k < maxVerticalCount; k++) {
                        int nextRow = row + (k + 1) * 2;
                        for (int j = 0; j < nexts.size(); j++) {
                            if (nexts.get(j).getRow() == nextRow
                                    && nexts.get(j).getColumn() == nextColumn) {
                                Seat nextSeat = nexts.get(j);
                                useSeat.add(nextSeat);
                                //過去の座席列情報
                                nextColumn = column + 2;
                                oneGroupCounter++;
                                break;
                            }
                        }
                    }
                }
            }
        }*/

        /*for(int i = 0; i < attendee.size(); i++){
            attendee.get(i).getThisClassTime().setThisClassSittingPosition(seats.get(i));
            attendee.get(i).getThisClassTime().setThisClassGroup(group);
        }*/
/*
        int firstRaundCount = attendeeCount / members[0];
        int firstToCount = members[0] * firstRaundCount;

        oneGroupCounter = 0;
        int groupNumber = 1;
        for(int i = 0; i < attendee.size(); i++){
            String groupNum = "";

            if(oneGroupMemberCount > oneGroupCounter){
                groupNum = String.valueOf(groupNumber);
            }else{
                ++groupNumber;
                oneGroupCounter = 0;
                groupNum = String.valueOf(groupNumber);
            }
            //着席位置も入れる.
            //attendee.get(i).getThisClassTime().setThisClassSittingPosition(seats.get(i));
            attendee.get(i).getThisClassTime().setThisClassSittingPosition(useSeat.get(i));

            //グループ情報を入れる.
            attendee.get(i).getThisClassTime().setThisClassGroup(new Group(groupNum));
            ++oneGroupCounter;
        }

        //学生のグルーピング情報をセット
        activity.mTeacher.getGroupingManagement().setGroupings(attendee);

        //グルーピング終わり.
        groupingManagement.setDoGroupingFlag();
    }

    private void setTestDummyGripingSeat(MainActivity activity){
        Faculty teacher = activity.mTeacher;
        GroupingManagement groupingManagement = teacher.getGroupingManagement();

        //本日の出席者
        List<Student> attendee = activity.mTeacher.getRoster().getTodayAttendee();
        //本日の出席者数
        int attendeeCount = attendee.size();

        //int[] membersCount = groupingManagement.setRequestGroupingMode(0, requestCount,attendeeCount);
        int a = 0;
        a = 12;
    }*/


    /**
     * Created by scr on 2015/1/6.
     * checkRunGoupingメソッド
     * 実行ボタンが押された際の処理を記述します.
     */
    /*private void checkRunGouping(){


        String inputValue = this.inputEditText.getText().toString();
        if(inputValue.length() == 0){
            //入力されていないのでアラートを表示
            showCanNotBeAccepted();
        }else{
            int number = Integer.valueOf(this.inputEditText.getText().toString());
            MainActivity activity = (MainActivity)this.getActivity();
            int nowAttendeeCount = activity.mTeacher.getRoster().getTodayAttendeeCount();
            if(number > nowAttendeeCount){
                //出席者数より多いので受け付けられません.
                showAlertDialogAttendanceIsNotEnough();
            }else{
                /*Toast.makeText(this.getActivity(),
                        "onCheckedChanged():" + number,
                        Toast.LENGTH_SHORT).show();*/
                //チェンジレイアウト
                //グルーピング完了フラグをONにする.
                /*activity.mTeacher.getGroupingManagement().setDoGroupingFlag();
                startLinearLayout.setVisibility(View.GONE);
                endLinearLayout.setVisibility(View.VISIBLE);

                int group = R.id.fragment_grouping_group_number_radioButton;
                int member = R.id.fragment_grouping_member_number_radioButton;

                List<Student> attendee = activity.mTeacher.getRoster().getTodayAttendee();
                if (_chekedId == group) {
                    //グループ数選択
                    //selectGroup(number,attendee);
                    setTestDummySelectTotalGroupCount(activity,number);
                } else if (_chekedId == member) {
                    //メンバー数選択
                    selectMemberCount(number,attendee);
                }

            }
        }
    }*/

    /*private void selectGroup(int oneGroupMemberCount,List<Student> attendee){
        MainActivity activity = (MainActivity)this.getActivity();

        int attendeeCount = attendee.size();
        int[] groupcount = {0,0,0};



        int counter = 0;
        for(int i=assistList.length;i>0;i--){
            int amari = attendeeCount%assistList[(i-1)];
            if(amari == 0){
                int cou = attendeeCount/assistList[(i-1)];
                groupcount[counter] = cou;
                break;
            }else{
                int toriaezuWari = attendeeCount/assistList[(i-1)];
                int tori = toriaezuWari * assistList[(i-1)];//人が正常にグループを組める
                groupcount[counter] = tori;
                attendeeCount = attendeeCount - tori;
            }
            ++counter;
        }

        int a = 0;
        a = 12;



    }

    private void selectMemberCount(int oneGroupMemberCount,List<Student> attendee){
        MainActivity activity = (MainActivity)this.getActivity();

        int attendeeCount = attendee.size();
        int amari = attendeeCount%oneGroupMemberCount;

        //きちんと割り振れる
        notAmariSelectMemberCount(activity,oneGroupMemberCount,attendee);


        /*if(amari == 0){

        }else{

            if(amari == 1){
                //やり直し
                ++oneGroupMemberCount;
            }else{
                String fileName = "";

                if(oneGroupMemberCount == 2){
                    fileName = "GROUPING_SEAT_241_ONE_GROUP_MAX_TOW_PAIR.csv";
                }else if(oneGroupMemberCount == 3){
                    fileName = "GROUPING_SEAT_241_ONE_GROUP_MAX_THREE_PAIR.csv";
                }else if(oneGroupMemberCount == 4){
                    fileName = "GROUPING_SEAT_241_ONE_GROUP_MAX_FOUR_PAIR.csv";
                }

                List<Seat> oneGroupSeats = activity.readDummySeatMst(fileName);

                //oneGroupMemberCount人でいくつつくるか
                int createoneGroupMemberCount = attendeeCount/oneGroupMemberCount;

                int createCount = createoneGroupMemberCount*oneGroupMemberCount;

                int groupNumber = 0;
                int nowCreateCount = 0;
                int index = 0;
               for(int i=0;i<attendee.size();i++){

                   Student st = attendee.get(i);
                   st.getThisClassTime().setThisClassSittingPosition(oneGroupSeats.get(index));
                   st.getThisClassTime().setThisClassGroup(new Group(String.valueOf(groupNumber)));

                   ++index;
                   ++nowCreateCount;


               }

            }
        }*/
        //学生のグルーピング情報をセット
        /*activity.mTeacher.getGroupingManagement().setGroupings(attendee);

        //グルーピング終わり.
        activity.mTeacher.getGroupingManagement().setDoGroupingFlag();
    }*/



    /*private void notAmariSelectMemberCount(MainActivity activity,int oneGroupMemberCount,List<Student> attendee){

        String fileName = "";

        if(activity.mTeacher.getClassPlan().getPlace().getRoom().getRoomId().equals(DummyContentClass.ROOM_ID_241)) {
            if(oneGroupMemberCount == 2){
                fileName = "GROUPING_SEAT_241_ONE_GROUP_MAX_TWO_PAIR.csv";
            }else if(oneGroupMemberCount == 3){
                fileName = "GROUPING_SEAT_241_ONE_GROUP_MAX_THREE_PAIR.csv";
            }else if(oneGroupMemberCount == 4){
                fileName = "GROUPING_SEAT_241_ONE_GROUP_MAX_FOUR_PAIR.csv";
            }
        }else if(activity.mTeacher.getClassPlan().getPlace().getRoom().getRoomId().equals(DummyContentClass.ROOM_ID_135)){
            if(oneGroupMemberCount == 2){
                fileName = "GROUPING_SEAT_135_ONE_GROUP_MAX_TWO_PAIR.csv";
            }else if(oneGroupMemberCount == 3){
                fileName = "GROUPING_SEAT_135_ONE_GROUP_MAX_THREE_PAIR.csv";
            }else if(oneGroupMemberCount == 4){
                fileName = "GROUPING_SEAT_135_ONE_GROUP_MAX_FOUR_PAIR.csv";
            }
        }else if(activity.mTeacher.getClassPlan().getPlace().getRoom().getRoomId().equals(DummyContentClass.ROOM_ID_1317)) {
            if (oneGroupMemberCount == 2) {
                fileName = "GROUPING_SEAT_1317_ONE_GROUP_MAX_TWO_PAIR.csv";
            } else if (oneGroupMemberCount == 3) {
                fileName = "GROUPING_SEAT_1317_ONE_GROUP_MAX_THREE_PAIR.csv";
            } else if (oneGroupMemberCount == 4) {
                fileName = "GROUPING_SEAT_1317_ONE_GROUP_MAX_FOUR_PAIR.csv";
            }
        }
        List<Seat> oneGroupSeats = activity.readDummySeatMst(fileName);

        int groupNumber = 1;
        int oneGroupCounter = 0;
        for(int i = 0;i<attendee.size();i++){
            ++oneGroupCounter;
            Student st = attendee.get(i);
            st.getThisClassTime().setThisClassSittingPosition(oneGroupSeats.get(i));
            st.getThisClassTime().setThisClassGroup(new Group(String.valueOf(groupNumber)));

            if(oneGroupMemberCount == oneGroupCounter){
                oneGroupCounter = 0;
                ++groupNumber;
            }
        }

    }*/



    /**
     * Created by scr on 2015/1/6.
     * showCanNotBeAcceptedメソッド
     * 数字が入力さていないので入力案内アラート出す.
     */
    public void showCanNotBeAccepted(){
        Bundle args = new Bundle();
        args.putInt(CommonDialogFragment.FIELD_TITLE, R.string.grouping_group_request_for_input);
        args.putInt(CommonDialogFragment.FIELD_MESSAGE, R.string.grouping_group_please_enter_in_number);
        args.putInt(CommonDialogFragment.FIELD_LABEL_POSITIVE, android.R.string.ok);
        CommonDialogFragment dialogFragment = new CommonDialogFragment();
        dialogFragment.setArguments(args);
        dialogFragment.show(this.getActivity().getSupportFragmentManager(), "showCanNotBeAccepted");
    }
    /**
     * Created by scr on 2015/1/6.
     * showAlertDialogAttendanceIsNotEnoughメソッド
     * 出席者数が足りませんというアラートを出力する.
     */
    public void showAlertDialogAttendanceIsNotEnough(){
        Bundle args = new Bundle();
        args.putInt(CommonDialogFragment.FIELD_TITLE, R.string.grouping_group_confirm);
        args.putInt(CommonDialogFragment.FIELD_MESSAGE, R.string.grouping_group_attendee_is_not_enough);
        args.putInt(CommonDialogFragment.FIELD_LABEL_POSITIVE, android.R.string.ok);
        CommonDialogFragment dialogFragment = new CommonDialogFragment();
        dialogFragment.setArguments(args);
        dialogFragment.show(this.getActivity().getSupportFragmentManager(), "showAlertDialogAttendanceIsNotEnough");
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }
    /**
     * EditText編集時に背景をタップしたらキーボードを閉じるようにするタッチイベントの処理
     */
    public boolean onTouchEvent(MotionEvent event) {
        //キーボードを隠す
        inputMethodManager.hideSoftInputFromWindow(startLinearLayout.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        //背景にフォーカスを移す
        startLinearLayout.requestFocus();

        return false;
    }
}
