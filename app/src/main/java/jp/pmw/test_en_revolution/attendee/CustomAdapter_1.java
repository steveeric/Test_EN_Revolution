package jp.pmw.test_en_revolution.attendee;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import jp.pmw.test_en_revolution.R;
import jp.pmw.test_en_revolution.confirm_class_plan.Student;

/**
 * Created by scr on 2015/02/24.
 */
public class CustomAdapter_1  extends ArrayAdapter<Student> {

    private Context _context;
    private int _textViewResourceId;
    private LayoutInflater _inflater;

    public CustomAdapter_1(Context context, int resourceId, List<Student> items) {
        super(context, resourceId, items);
        _context = context;
        _textViewResourceId = resourceId;
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
        //Student item = _items.get(position);
        Student st = this.getItem(position);
        ((TextView) view.findViewById(R.id.row_text)).setText(st.getFullName());

        //仮出席状態でなければ
        //if(item.mTempAttedance == 0) {
        /*
        if(st.mForgotEslOfferTime != null) {
            //ESL忘れ申請時間がNULLでない.
            //色を変える必要があるか？
            ((TextView) view.findViewById(R.id.row_text)).setText(item.mFullName);
        }else if(item.mAttendanceConfirmationReceivetime != null){
            //出席状態
            ((TextView) view.findViewById(R.id.row_text)).setText(item.mFullName);
        }*/
        /*}else{
            ((TextView) view.findViewById(R.id.row_text)).setText("");
        }*/
        return view;
    }
}