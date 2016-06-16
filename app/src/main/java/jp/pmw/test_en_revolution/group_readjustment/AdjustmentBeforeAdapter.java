package jp.pmw.test_en_revolution.group_readjustment;

import android.content.Context;
import android.content.res.Resources;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import jp.pmw.test_en_revolution.R;

/**
 * Created by si on 2016/05/18.
 *  メンバー不足情報をリストビューに格納するアダプタークラスです.
 */
public class AdjustmentBeforeAdapter extends ArrayAdapter<String> {
    private LayoutInflater layoutInflater_;

    public AdjustmentBeforeAdapter(Context context, int textViewResourceId, List<String> objects) {
        super(context, textViewResourceId, objects);
        layoutInflater_ = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
    public void updateAllGroupState(List<String> newString){
        for( int i = 0; i < getTotalItemCount(); i++ ){
            String sr = getItem(i);
            sr = newString.get(i);
        }
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String item = (String)getItem(position);
        if (null == convertView) {
            convertView = layoutInflater_.inflate(R.layout.row_onw_item, null);
        }
        //
        ((TextView)convertView.findViewById(R.id.row_text)).setText(item);
        //  センターリング
        ((TextView)convertView.findViewById(R.id.row_text)).setGravity(Gravity.CENTER);
        //  文字サイズ
        Resources res = getContext().getResources();
        float fontSize = res.getDimension(R.dimen.textsize_xlarge);
        ((TextView)convertView.findViewById(R.id.row_text)).setTextSize(fontSize);
        return convertView;
    }
}