package jp.pmw.test_en_revolution.history;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import java.util.List;

/**
 * Created by scr on 2015/01/07.
 */
public class HistoryCustomAdapter extends ArrayAdapter<History> {

    private LayoutInflater mFactory;
    private int mItemLayoutResource;

    public HistoryCustomAdapter(Context context, int resourceId, List<History> items) {
        super(context, resourceId, items);
        mFactory = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mItemLayoutResource = resourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final HistoryLayout view;
        if (convertView == null) {
            // Viewがなかったら生成
            view = (HistoryLayout) mFactory.inflate(mItemLayoutResource, null);
        } else {
            view = (HistoryLayout) convertView;
        }

        History history = getItem(position);
        view.bindView(history);

        return view;
    }
}