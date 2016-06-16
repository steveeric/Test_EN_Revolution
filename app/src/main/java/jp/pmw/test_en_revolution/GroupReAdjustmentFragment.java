package jp.pmw.test_en_revolution;

import android.app.Activity;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import java.util.List;
import java.util.Timer;
import jp.pmw.test_en_revolution.display.DisplaySizeCheck;
import jp.pmw.test_en_revolution.group_readjustment.AdjustmentAfterAdapter;
import jp.pmw.test_en_revolution.group_readjustment.AllGroupState;
import jp.pmw.test_en_revolution.group_readjustment.AllGroupStateAdapter;
import jp.pmw.test_en_revolution.group_readjustment.Moved;
import jp.pmw.test_en_revolution.group_readjustment.ReAdjustmentOjbect;
import jp.pmw.test_en_revolution.group_readjustment.RedrawListViewTimer;

/**
 * グループ調整フラグメントクラス
 *
 * */
public class GroupReAdjustmentFragment extends MyMainFragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static GroupReAdjustmentFragment newInstance(int sectionNumber) {
        GroupReAdjustmentFragment fragment = new GroupReAdjustmentFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public GroupReAdjustmentFragment() {
    }
    //  再描画間隔時間[s]
    private static final long REDRAW_PERIOD = 1000;

    //  ⓪ グルーピング授業でないためグループ調整じたい行うことができないレイアウト
    private static final int ZERO = 0;
    //  ① 出席確定前でグループ調整を行うことができないレイアウト
    private static final int FIRST = 1;
    //  ② 出席確定後でグループ調整必要なしレイアウト
    private static final int SECONDE = 2;
    //  ③ 出席確定後でグループ調整必要ありレイアウト
    private static final int THIRD = 3;
    //  ④ 出席確定後でグループ調整実施後レイアウト
    private static final int FOURTH = 4;
    //  出席者数が不十分のためグループ調整を行うことができないレイアウト
    private static final int CAN_NOT_GROUP_ADJUST = 6;
    //  タイマー
    private Timer mTimer;
    //  リストビューをリフレッシュするためのタイマークラス
    private RedrawListViewTimer mRedrawListViewTimer;
    //  グループ調整オブジェクト
    private ReAdjustmentOjbect rao;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_group_re_adjustment, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
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

    @Override
    public void onStart(){
        super.onStart();
        //  初期化
        initObject();
    }


    @Override
    public void onResume() {
        super.onResume();
        toAllHideLayout();
        //  グループ調整情報をWEBから取得する
        getMainActivity().getClassHttpRequest().getGroupAdjustmentStatus();
        showWaitFragment();
    }
    @Override
    public void onPause(){
        super.onPause();
        // 座席移動伝達時のタイマーをストップする.
        stopReDrawTimer();
        //  フラグメント自体のタイマーキャンセルする.
        cancelTimer();
    }
    /**
     * グループ調整オブジェクト取得する.
     */
    ReAdjustmentOjbect getAdjustmentObject() {
        return getMainActivity().getClassObject().getReAdjustment();
    }

    /**
     * グループ調整に関するデータがDBから取得できるまで待機する.
     */
    public void showWaitFragment() {
        MainActivity activity = (MainActivity) this.getActivity();
        rao = getAdjustmentObject();
        if (rao != null) {
            displaySelectLayout();
        } else {
            //  数秒待機して再読み込み...
            super.loadFragment();
        }
    }

    /**
     * 出席確認を終えているかどうかを確認します.
     *
     * @return {@code -1} 送信状態取得前,{@code 0} 出席認定送信前,{@code 1} 出席認定送信後
     */
    int chkAttendanceFinished() {
        MainActivity ma = getMainActivity();
        TransmitStateObject tso = ma.getClassObject().getTransmitStateObject();
        if (tso == null) {
            return -1;
        }
        if (tso.getAttendanceTranmitEndTime() == null) {
            //  出席確定前
            return 0;

        } else {
            //  出席確定後
            return 1;
        }
    }

    /**
     * グループ調整フラグメントで、
     * ４レイアウトのいずれを表示するかを選択し表示します.
     * ① 出席確定前でグループ調整を行うことができないレイアウト
     * ② 出席確定後でグループ調整必要なしレイアウト
     * ③ 出席確定後でグループ調整必要ありレイアウト
     * ④ 出席確定後でグループ調整実施後レイアウト
     */
    void displaySelectLayout() {
        int state = getStateOfGroupReAdjustment();
        switch (state) {
            case ZERO:
                zero();
                break;
            case FIRST:
                first();
                break;
            case SECONDE:
                second();
                break;
            case THIRD:
                third();
                break;
            case FOURTH:
                fourth();
                break;
            case CAN_NOT_GROUP_ADJUST:
                canNotGroupAdjast();
                break;
        }
        //  ロードディンぐレイアウト非表示に
        (getActivity().findViewById(R.id.group_readjustment_fragment_loading_linearLayout)).setVisibility(View.GONE);
    }

    /**
     * 現在のグループ調整状態を取得し返します.
     */
    int getStateOfGroupReAdjustment() {
        //  グループ調整オブジェクト
        rao = getAdjustmentObject();
        if (rao.getNotGrouping() != null) {
            //  そもそもグルーピング授業でない
            this.cancelTimer();
            return ZERO;
        }
        int state = -1;
        //  出席認定FOURTH状況
        int attState = chkAttendanceFinished();
        if (attState == 1) {
            this.cancelTimer(); //  3秒に1回onResumeになるのを防ぐ(必要ないので)

            //  グループ調整不可能か調べる
            //  true : グループ調整不可能 false : グループ調整可能
            boolean canNotFlag = rao.chkCanNotAdjustment();
            if (canNotFlag) {

                //  グループ調整不可能
                return CAN_NOT_GROUP_ADJUST;
            }

            //  グループ調整を行う必要があるかを取得する.
            boolean doingReAdjustmet = rao.getDoingReAdjustmet();
            if (doingReAdjustmet) {
                //  必要あり
                state = THIRD;
            } else {

                //  必要なし
                state = SECONDE;
            }

            //  座席移動を行った者がいるかを確認する
            //  true : グループ調整実行済み
            //  false : グループ調整実行前
            boolean processd = rao.chkProcessdGroupAdjust();
            if (processd) {

                state = FOURTH;
            }
        } else {
            //  出席確定前
            state = FIRST;
        }
        return state;
    }

    /**
     * androidグループ調整フラグメント上のライナーレイアウトを全て非表示にし、
     * ロードレイアウトのみ表示します.
     */
    void toAllHideLayout() {
        (getActivity().findViewById(R.id.group_readjustment_fragment_loading_linearLayout)).setVisibility(View.VISIBLE);
        (getActivity().findViewById(R.id.group_readjustment_fragment_can_not_process_linearLayout)).setVisibility(View.GONE);
        (getActivity().findViewById(R.id.group_readjustment_fragment_first_linearLayout)).setVisibility(View.GONE);
        (getActivity().findViewById(R.id.group_readjustment_fragment_second_linearLayout)).setVisibility(View.GONE);
        (getActivity().findViewById(R.id.group_readjustment_fragment_third_linearLayout)).setVisibility(View.GONE);
        (getActivity().findViewById(R.id.group_readjustment_fragment_fourth_linearLayout)).setVisibility(View.GONE);
        //(getActivity().findViewById(R.id.group_readjustment_fragment_all_groups_state_linearLayout)).setVisibility(View.INVISIBLE);
    }

    /**
     * androidグループ調整フラグメント上のライナーレイアウトを表示します.
     */
    void showLayout(LinearLayout layout) {
        layout.setVisibility(View.VISIBLE);
    }

    /**
     * ⓪ そもそもグルーピング授業でない場合のレイアウト
     */
    void zero() {
        rao = getAdjustmentObject();
        LinearLayout firstLayout = (LinearLayout) getActivity().findViewById(R.id.group_readjustment_fragment_can_not_process_linearLayout);
        TextView tv = (TextView) getActivity().findViewById(R.id.group_readjustment_fragment_can_not_process_textView);
        tv.setText(rao.getNotGrouping());
        setListViewAllGroupsState();
        //  ２つのレイアウトを画面内にうまく収まるように調整します.
        adjustTwoLayouts(firstLayout);
        showLayout(firstLayout);
    }


    /**
     * ① 出席確定前でグループ調整を行うことができないレイアウト
     * 画面に「出席者が確定するまでお待ちください.」と文面を表示する.
     */
    void first() {
        LinearLayout firstLayout = (LinearLayout) getActivity().findViewById(R.id.group_readjustment_fragment_first_linearLayout);
        setListViewAllGroupsState();
        //  ２つのレイアウトを画面内にうまく収まるように調整します.
        adjustTwoLayouts(firstLayout);
        showLayout(firstLayout);
    }

    /**
     * ② 出席確定後でグループ調整必要なしレイアウト
     * 画面に「グループ調整を行う必要はありません」と文面を表示する.
     */
    void second() {
        //
        LinearLayout secondLayout = (LinearLayout) getActivity().findViewById(R.id.group_readjustment_fragment_second_linearLayout);
        //  全グループ状態
        setListViewAllGroupsState();
        //  ２つのレイアウトを画面内にうまく収まるように調整します.
        adjustTwoLayouts(secondLayout);
        showLayout(secondLayout);
    }

    /**
     * ③ 出席確定後でグループ調整必要ありレイアウト
     * 画面に「グループ最低人数を満たしていないグループがあります」と文面を表示する.
     * 画面に最低人数を満たしていないグループが何グループあるかをリストビューで表示する.
     * 画面にグループ調整を開始するボタンを表示する.
     */
    void third() {
        LinearLayout thirdLayout = (LinearLayout) getActivity().findViewById(R.id.group_readjustment_fragment_third_linearLayout);
        //  グルーピング時に最低人数を満たしていないグループを表示するリストビュー
        ListView lv = (ListView) getActivity().findViewById(R.id.group_readjustment_fragment_adjustment_needed_listView);
        setDoesNotMeetMemberState(lv);
        //  グループ調整開始ボタン
        Button groupAdjustmentStartBtn = (Button) getActivity().findViewById(R.id.group_readjustment_fragment_startBtn);
        //  グループ調整開始ボタンの機能
        groupAdjustmentStartBtn.setOnClickListener(groupAdjustmentStartBtnListener);
        //  全グループ状態
        setListViewAllGroupsState();

        //  ２つのレイアウトを画面内にうまく収まるように調整します.
        adjustTwoLayouts(thirdLayout);

        showLayout(thirdLayout);
    }

    /**
     * グループ調整開始ボタンの機能
     * ③ 出席確定後でグループ調整必要ありレイアウトで使用している、
     * 「グループ調整を開始する」ボタンの機能部分
     */
    View.OnClickListener groupAdjustmentStartBtnListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //  グループ調整を開始するボタンを非表示に
            ((Button) getActivity().findViewById(R.id.group_readjustment_fragment_startBtn)).setVisibility(View.GONE);
            //  グループ調整開始ローディングを表示にする.
            ((ProgressBar) getActivity().findViewById(R.id.group_readjustment_fragment_startBtn_tap_after_progressBar)).setVisibility(View.VISIBLE);
            //  グループ調整開始URL
            getMainActivity().getClassHttpRequest().startGroupAdjustment();
            //  オブジェクトを初期化する.
            initObject();
            //  グループ調整フラグメントリロード
            showWaitFragment();
        }
    };

    /**
     * オブジェクトを初期化する.
     */
    void initObject() {
        //  グループ調整オブジェクト情報初期化
        getMainActivity().getClassObject().setReAdjustment(null);
        //  履修生の情報を初期化
        getMainActivity().getClassObject().setStudentObject(null);
    }


    /**
     * ④ 出席確定後でグループ調整実施後レイアウト
     * 画面に「グループを移動するよう学生に指示をしてください.」と文面を表示する.
     * 画面に移動前と移動後の座席位置をリストビューで表示する(学生文リストビューができあがる)
     */
    public void fourth() {
        LinearLayout fourthLayout = (LinearLayout) getActivity().findViewById(R.id.group_readjustment_fragment_fourth_linearLayout);

        //  座席移動対象群リストビュー
        ListView lv = (ListView) getActivity().findViewById(R.id.group_readjustment_fragment_after_listview);
        // スクロールバーを表示させる
        lv.setScrollbarFadingEnabled(false);
        // 座席移動対象群アダプター
        AdjustmentAfterAdapter afterAdapter = setSeatAfterMovings(lv);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent,
                                    View view, int pos, long id) {
                ListView listView = (ListView) parent;
                Moved item = (Moved) listView.getItemAtPosition(pos);
                if (item.getContactDateTime() == null) {
                    //  右揃えでなければ
                    //  座席移動ID
                    String seatAfterMovingId = item.getSeatAfterMoving();
                    tellSeatMoving(seatAfterMovingId);
                }
            }
        });

        //  全グループ情報
        AllGroup ag = setListViewAllGroupsState();
        //
        showLayout(fourthLayout);

        //  リストビュー再描画処理
            //  2つのリストビューを随時更新するためにタイマークラスを使用しております.
        mTimer = new Timer();
        mRedrawListViewTimer = new RedrawListViewTimer(
                   this,
                   lv,
                   afterAdapter,
                   ag.allGroupsListView,
                   ag.allGroupAdapter);
        mTimer.schedule(mRedrawListViewTimer, 0, REDRAW_PERIOD);
        //  ２つのレイアウトを画面内にうまく収まるように調整します.
        adjustTwoLayouts(fourthLayout);
    }
    /**
     *  ２つのレイアウトを画面内にうまく収まるように調整します.
     *
     * */
    void adjustTwoLayouts(LinearLayout linearLayout){
        FrameLayout layout = (FrameLayout) getActivity().findViewById(R.id.group_readjustment_fragment_linearLayout);
        ListView bottomListView = (ListView) getActivity().findViewById(R.id.group_readjustment_fragment_all_groups_state_listView);
        DisplaySizeCheck dsc = new DisplaySizeCheck();
        Point p = dsc.getViewSize(layout);
        int heightScreen = p.y;
        int spliteScreen = heightScreen / 4;
        linearLayout.getLayoutParams().height = spliteScreen * 3 - 100;
        bottomListView.getLayoutParams().height = spliteScreen;
    }

    /**
     * 座席移動伝達時のタイマーをストップする.
     * */
    void stopReDrawTimer(){
        //  FOURTHレイアウトの2つのリストビューを更新するタイマークラス
        if( mRedrawListViewTimer != null ){
            mRedrawListViewTimer.cancel();
            mRedrawListViewTimer = null;
        }
    }


    /**
     * 全員に座席移動伝達済みかを確認する.
     * @param object グループ調整オブジェクト
     * **/
    public void chkToaldToEveyone(ReAdjustmentOjbect object){
        if (object.toldToMoveSeatsToEveryone()) {
            //  全員に移動先を伝達したので、
            //  ヘッダー部分を終了しましたの文言に変更する.
            ((TextView) getActivity().findViewById(R.id.group_readjustment_fragment_fourth_textView))
                    .setText(getText(R.string.group_readjustment_attendance_determined_readjustmented_toal_str));
            // 座席移動伝達時のタイマーをストップする.
            stopReDrawTimer();
        }
    }

    /**
     * 移動先の座席を伝えます.
     * グループ調整実施後、
     * 座席移動対象学生リストビューに表示されている項目を
     * タップ後に
     */
    void tellSeatMoving(String seatAfterMovingId) {
        //  ロード画面に戻す
        //toAllHideLayout();
        //  グループ移動手動伝達後
        getMainActivity().getClassHttpRequest().manulaContacted(seatAfterMovingId);
        //  オブジェクト初期化
        initObject();
    }

    /**
     * グループ調整不可能レイアウト
     */
    void canNotGroupAdjast() {
        LinearLayout canNotProcessLayout = (LinearLayout) getActivity().findViewById(R.id.group_readjustment_fragment_can_not_process_linearLayout);
        TextView tv = (TextView) getActivity().findViewById(R.id.group_readjustment_fragment_can_not_process_textView);
        rao = getAdjustmentObject();
        tv.setText(rao.getUnnableMessage());
        showLayout(canNotProcessLayout);
    }


    /**
     * グループの最低人数を満たしていないグループがいくつあるかをセットする.
     */
    void setDoesNotMeetMemberState(ListView lv) {
        List<AllGroupState> messages = getMainActivity().getClassObject().getReAdjustment().getMessages();
        AllGroupStateAdapter adapter = (new AllGroupStateAdapter(getActivity(), 0, messages));
        lv.setAdapter(adapter);
    }

    /**
     * 座席移動形跡をリストビューにセットする.
     */
    AdjustmentAfterAdapter setSeatAfterMovings(ListView lv) {
        rao = getAdjustmentObject();
        AdjustmentAfterAdapter adapter = new AdjustmentAfterAdapter(getActivity(), 0, rao.getMoveds());
        lv.setAdapter(adapter);
        return adapter;
    }

    /**
     * 全グループ数の状態をリストビューにセットする.
     * androidタブレットの画面下部に設置されている
     * ListViewに全グループ数の状態をセットする.
     * 例 : androidタブレットの画面下部に表示されるリストビューサンプル
     * 4人グループ : 12
     * 3人グループ : 7
     * 2人グループ : 0
     * 1人グループ : 2
     * ※表示データは、サーバーより取得しております.
     * @return All
     */
    AllGroup setListViewAllGroupsState() {
        AllGroupStateAdapter adjustmentBeforeAdapter = null;
        rao = getAdjustmentObject();
        //  全グループ数状態リストビュー
        ListView allGroupsListView = (ListView) getActivity().findViewById(R.id.group_readjustment_fragment_all_groups_state_listView);

        List<AllGroupState> allGroupsSateList = rao.getAllGroupStates();
        if( allGroupsSateList != null ){
            //  アダプタークラス
            adjustmentBeforeAdapter = new AllGroupStateAdapter(getActivity(), 0, allGroupsSateList);
            allGroupsListView.setAdapter(adjustmentBeforeAdapter);
            //  全グループ情報表示レイアウト
            LinearLayout ll = (LinearLayout) getActivity().findViewById(R.id.group_readjustment_fragment_all_groups_state_linearLayout);
            //  全グループ情報が一つもない場合は、レイアウトを非表示にする.
            if (allGroupsSateList.size() == 0) {
                ll.setVisibility(View.INVISIBLE);
            }else{
                ll.setVisibility(View.VISIBLE);
            }
        }
        //
        return new AllGroup(allGroupsListView, adjustmentBeforeAdapter);
    }

    //再描画で使用するレイアウトの一部分クラス
    class AllGroup{
        public ListView allGroupsListView;
        public AllGroupStateAdapter allGroupAdapter;
        AllGroup(ListView lv, AllGroupStateAdapter adapter){
            allGroupsListView   = lv;
            allGroupAdapter     = adapter;
        }
    }


}