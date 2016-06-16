package jp.pmw.test_en_revolution.one_cushion.select_teacher;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import jp.pmw.test_en_revolution.R;

/**
 * Created by scr on 2014/12/04.
 */
public class CustomAdapter extends ArrayAdapter<Faculty> {

    private Context _context;
    private int _textViewResourceId;
    private List<Faculty> _items;
    private LayoutInflater _inflater;

    public CustomAdapter(Context context, int resourceId, List<Faculty> items) {
        super(context, resourceId, items);
        _context = context;
        _textViewResourceId = resourceId;
        _items = items;
        _inflater = (LayoutInflater) _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        if (convertView != null) {
            view = convertView;
        } else {
            view = _inflater.inflate(R.layout.row_onw_item, null);
        }

        Faculty item = _items.get(position);

        ((TextView) view.findViewById(R.id.row_text)).setText(item.getFullName());

        return view;
    }
}