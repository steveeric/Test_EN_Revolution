package jp.pmw.test_en_revolution.attendee;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import jp.pmw.test_en_revolution.R;

/**
 * Created by scr on 2014/12/14.
 */
public class CustomAdapter extends ArrayAdapter<Attendee> {

    private Context _context;
    private int _textViewResourceId;
    private List<Attendee> _items;
    private LayoutInflater _inflater;

    public CustomAdapter(Context context, int resourceId, List<Attendee> items) {
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
        //出欠席者の情報
        Attendee item = _items.get(position);
        //仮出席状態でなければ
        //if(item.mTempAttedance == 0) {
            if(item.mForgotEslOfferTime != null) {
                //ESL忘れ申請時間がNULLでない.
                //色を変える必要があるか？
                ((TextView) view.findViewById(R.id.row_text)).setText(item.mFullName);
            }else if(item.mAttendanceConfirmationReceivetime != null){
            //出席状態
            ((TextView) view.findViewById(R.id.row_text)).setText(item.mFullName);
        }
        return view;
    }
}