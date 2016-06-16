package jp.pmw.test_en_revolution.group_readjustment;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.List;
import jp.pmw.test_en_revolution.R;

/**
 * Created by si on 2016/05/19.
 */
public class AllGroupStateAdapter extends ArrayAdapter<AllGroupState> {
    private LayoutInflater layoutInflater_;

    public AllGroupStateAdapter(Context context, int textViewResourceId, List<AllGroupState> objects) {
        super(context, textViewResourceId, objects);
        layoutInflater_ = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    /**
     * タップ不可にする
     * */
    @Override
    public boolean isEnabled(int position) {
        return false;
    }
    /**
     * 登録されているアイテム総数
     * */
    public int getTotalItemCount(){
        return this.getCount();
    }

    /**
     *  データを更新します.
     * */
    public void updateAllGroupState(List<AllGroupState> newAllGroupState){
        for( int i = 0; i < getTotalItemCount(); i++ ){
            //  警告色（ 0 : 黒色に, 1 : 赤色に)
            int warningColor        = newAllGroupState.get(i).getWarningColor();
            //  リストビューにセットする文字列
            String allGroupState    = newAllGroupState.get(i).getAllGroupState();
            //  アイテムをセットする.
            this.getItem(i).setItems(warningColor, allGroupState);
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        AllGroupState item = (AllGroupState)getItem(position);
        if (null == convertView) {
            convertView = layoutInflater_.inflate(R.layout.row_onw_item, null);
        }
        //  文字列をセットする.
        ((TextView)convertView.findViewById(R.id.row_text)).setText(item.getAllGroupState());
        //  センターリング
        ((TextView)convertView.findViewById(R.id.row_text)).setGravity(Gravity.CENTER);

        //
        if( item.getWarningColor() == 1 ){
            //  グループ最低人数を満たしていない場合は、文字列を赤色にする.
            ((TextView)convertView.findViewById(R.id.row_text)).setTextColor(Color.RED);
        }else{
            //  グループ最低人数を満たしている.
            ((TextView)convertView.findViewById(R.id.row_text)).setTextColor(Color.BLACK);
        }

        return convertView;
    }
}