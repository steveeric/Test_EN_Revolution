package jp.pmw.test_en_revolution.group_readjustment;

import android.os.Handler;
import android.widget.ListView;
import android.widget.TextView;

import java.util.TimerTask;

import jp.pmw.test_en_revolution.GroupReAdjustmentFragment;
import jp.pmw.test_en_revolution.R;

/**
 * Created by si on 2016/05/18.
 * グループ調整画面で2つのリストビューを再描画するタイマークラスです.
 * ・座席移動対象学生群リストビュー  (group_readjustment_fragment_after_listview)
 * ・全グループ情報群リストビュー(group_readjustment_fragment_all_groups_state_listView)
 */
public class RedrawListViewTimer extends TimerTask {
    //  UIハンドラー用
    private Handler mHandler = new Handler();
    //  フラグメント
    private GroupReAdjustmentFragment fragment;
    //  移動対象学生リストビュー
    private ListView mAfterListView;
    //  全グループ状態リストビュー
    private ListView mAllGroupStateListView;
    //  移動対象学生アダプター
    private AdjustmentAfterAdapter mAfterAdapter;
    //  全グループ状態アダプター
    private AllGroupStateAdapter mAllGroupStateAdapter;

    public RedrawListViewTimer(GroupReAdjustmentFragment f, ListView afterListView,
                               AdjustmentAfterAdapter afterAdapter,
                               ListView allGroupStateListView,
                               AllGroupStateAdapter mAllGroupStateAdapter){
        this.fragment               = f;
        this.mAfterListView         = afterListView;
        this.mAllGroupStateListView = allGroupStateListView;
        this.mAfterAdapter          = afterAdapter;
        this.mAllGroupStateAdapter  = mAllGroupStateAdapter;
    }

    @Override
    public void run() {
        mHandler.post( new Runnable() {
            public void run() {
                ReAdjustmentOjbect rao = fragment.getMainActivity().getClassObject().getReAdjustment();
                if( rao != null ){
                    mAfterAdapter.updateContactedDateTime(rao.getMoveds());
                    mAllGroupStateAdapter.updateAllGroupState(rao.getAllGroupStates());
                    mAfterAdapter.notifyDataSetChanged();
                    mAfterListView.invalidateViews();
                    mAllGroupStateAdapter.notifyDataSetChanged();
                    mAllGroupStateListView.invalidateViews();
                    //  全員に伝達済み
                    fragment.chkToaldToEveyone(rao);
                }
            }
        });
    }
}
