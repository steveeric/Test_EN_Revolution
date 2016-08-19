package jp.pmw.test_en_revolution.survey;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

/**
 * Created by si on 2016/08/17.
 * ChoiceAdapterクラス
 * 選択肢のアダプタークラスです.
 */
public class ChoiceAdapter extends ArrayAdapter<Choice> {
    private LayoutInflater mFactory;
    private int mItemLayoutResource;
    public ChoiceAdapter(Context context, int resource, Choice[] objects) {
        super(context, resource, objects);
        mFactory = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mItemLayoutResource = resource;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ChoiceLayout view;
        if (convertView == null) {
            // Viewがなかったら生成
            view = (ChoiceLayout) mFactory.inflate(mItemLayoutResource, null);
        } else {
            view = (ChoiceLayout) convertView;
        }
        Choice choice = getItem(position);
        view.bindView(choice);
        return view;
    }
}