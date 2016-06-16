package jp.pmw.test_en_revolution.grouping;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import jp.pmw.test_en_revolution.R;

/**
 * Created by scr on 2015/03/02.
 */
public class GroupingAdapter extends ArrayAdapter<Grouping> {

    private Context _context;
    private int _textViewResourceId;
    private LayoutInflater _inflater;

    private Grouping selectGrouping;

    public GroupingAdapter(Context context, int resourceId, List<Grouping> items) {
        super(context, resourceId, items);
        _context = context;
        _textViewResourceId = resourceId;
        _inflater = (LayoutInflater) _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    public void setSelectGrouping(Grouping g){
        this.selectGrouping = g;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        if (convertView != null) {
            view = convertView;
        } else {
            view = _inflater.inflate(R.layout.row_select_item, null);
        }

        Grouping g = this.getItem(position);
        TextView tv = (TextView) view.findViewById(R.id.row_select_item_textView);
        tv.setText(g.getGroupingName());
        ImageView iv = (ImageView) view.findViewById(R.id.row_select_item_imageView);
        if(this.selectGrouping != null) {
            if(this.selectGrouping.getGroupingCount() == g.getGroupingCount()){
                iv.setVisibility(View.VISIBLE);
            }else{
                iv.setVisibility(View.INVISIBLE);
            }
        }
        return view;
    }

}
