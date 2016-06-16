package jp.pmw.test_en_revolution.group_readjustment;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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
        //  (学籍番号 氏名)をセットする.
        ((TextView)convertView.findViewById(R.id.row_group_readjustmented_line1)).setText(item.getLine1());
        //  座席名称 (グループ名称)をセットする.
        ((TextView)convertView.findViewById(R.id.row_group_readjustmented_line2)).setText(item.getLine2());
        //
        ((TextView)convertView.findViewById(R.id.row_group_readjustmented_line3)).setText(item.getLine3());
        //  座席名称 (グループ名称)をセットする.
        //((TextView)convertView.findViewById(R.id.row_group_readjustmented_line4)).setText(item.getLine4());

        //  連絡済みのため右寄せにする.
        //  デフォルトでは、左寄せにする.
        if(item.getContactDateTime() != null){
            //  学生にグループ移動連絡済み
            ((TextView)convertView.findViewById(R.id.row_group_readjustmented_line1)).setGravity(Gravity.RIGHT);
            ((TextView)convertView.findViewById(R.id.row_group_readjustmented_line2)).setGravity(Gravity.RIGHT);
            ((TextView)convertView.findViewById(R.id.row_group_readjustmented_line3)).setGravity(Gravity.RIGHT);
            //((TextView)convertView.findViewById(R.id.row_group_readjustmented_line4)).setGravity(Gravity.RIGHT);
        }else{
            //  学生にグループ移動未伝達
            ((TextView)convertView.findViewById(R.id.row_group_readjustmented_line1)).setGravity(Gravity.LEFT);
            ((TextView)convertView.findViewById(R.id.row_group_readjustmented_line2)).setGravity(Gravity.LEFT);
            ((TextView)convertView.findViewById(R.id.row_group_readjustmented_line3)).setGravity(Gravity.LEFT);
            //((TextView)convertView.findViewById(R.id.row_group_readjustmented_line4)).setGravity(Gravity.LEFT);
        }

        //  バンディング
        ((TextView)convertView.findViewById(R.id.row_group_readjustmented_line1)).setPadding(10,0,10,0);
        ((TextView)convertView.findViewById(R.id.row_group_readjustmented_line2)).setPadding(10,0,10,0);
        ((TextView)convertView.findViewById(R.id.row_group_readjustmented_line3)).setPadding(10,0,10,0);
        //((TextView)convertView.findViewById(R.id.row_group_readjustmented_line4)).setPadding(10,0,10,0);

        return convertView;
    }
}