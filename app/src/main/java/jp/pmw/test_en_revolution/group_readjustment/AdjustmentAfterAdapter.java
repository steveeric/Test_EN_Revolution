package jp.pmw.test_en_revolution.group_readjustment;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;
import jp.pmw.test_en_revolution.R;

/**
 * Created by si on 2016/05/09.
 * グループ調整対象学生リストビュー
 */
public class AdjustmentAfterAdapter extends ArrayAdapter<Moved> {
    private LayoutInflater layoutInflater_;

    public AdjustmentAfterAdapter(Context context, int textViewResourceId, List<Moved> objects) {
        super(context, textViewResourceId, objects);
        layoutInflater_ = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    /**
     * 学生に座席移動先を伝達した日時をセットします.
     * */
    public void updateContactedDateTime(List<Moved> newMoveds){
        //  過去にAdjustmentAfterAdapterに登録したアイテム総数
        int pastMovedsCount = this.getTotalItemCount();
        //  新しい座席移動データ
        for( int i = 0; i < newMoveds.size(); i++ ){
            //  座席移動ID
            String newSeatMovingId  = newMoveds.get(i).getMovedSeatId();
            //  伝達日時
            String newContactedTime = newMoveds.get(i).getContactDateTime();
            if( newContactedTime != null ){
                for( int j = 0; j < pastMovedsCount; j++ ){
                    Moved pastMoved = (Moved)this.getItem(j);
                    //  過去の座席移動ID
                    String pastSeatMovingId = pastMoved.getMovedSeatId();
                    //  過去の座席移動伝達日時
                    String pastContactedTime = pastMoved.getContactDateTime();
                    //  座席移動未伝達の場合は
                    if( newSeatMovingId.equals(pastSeatMovingId)
                            && pastContactedTime == null ){
                        this.getItem(j).setContactDateTime(newContactedTime);
                    }
                }
            }
        }
    }

    /**
     * 全アイテム数
     * */
    public int getTotalItemCount(){
        return this.getCount();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Moved item = (Moved)getItem(position);
        if (null == convertView) {
            convertView = layoutInflater_.inflate(R.layout.row_group_readjustmented, null);
        }
        //  基本的な情報をセットします.
        setBasicInfo( convertView, item );
        //  連絡済みのため右寄せにする.
        //  デフォルトでは、左寄せにする.
        if(item.getContactDateTime() != null){
            tell( convertView, item );
        }else{
            notTell( convertView, item );
        }
        return convertView;
    }
    /**
     *  setBasicInfoメソッド
     *  基本情報をセットします.
     * */
    void setBasicInfo( View convertView, Moved item ){
        //  (学籍番号 氏名)をセットする.
        TextView tv = (TextView)convertView.findViewById(R.id.row_group_readjustmented_line1);
        tv.setText(item.getLine1());
        //  座席名称 (グループ名称)をセットする.
        ((TextView)convertView.findViewById(R.id.row_group_readjustmented_line2)).setText(item.getLine2());
        //
        ((TextView)convertView.findViewById(R.id.row_group_readjustmented_line3)).setText(item.getLine3());
    }
    /**
     *  tellメソッド
     *  グループ移動先伝達済み
     * */
    void tell( View convertView, Moved item ){
        //  学生にグループ移動連絡済み
        //  グループと座席移動後
        ((TextView)convertView.findViewById(R.id.row_group_readjustmented_position_tv)).setText(item.getPosition());
        ((ImageView)convertView.findViewById(R.id.row_group_readjustmented_finished_iv)).setVisibility(View.VISIBLE);
        ((TextView)convertView.findViewById(R.id.row_group_readjustmented_position_tv)).setTextColor(Color.BLUE);
    }
    /**
     *  notTellメソッド
     *  グループ移動先未伝達
     * */
    void notTell( View convertView, Moved item ){
        //  学生にグループ移動未伝達
        //  グループと座席位置移動前
        ((TextView)convertView.findViewById(R.id.row_group_readjustmented_position_tv)).setText(item.getNowPosition());
        ((ImageView)convertView.findViewById(R.id.row_group_readjustmented_finished_iv)).setVisibility(View.INVISIBLE);
        ((TextView)convertView.findViewById(R.id.row_group_readjustmented_position_tv)).setTextColor(Color.RED);
    }

}
