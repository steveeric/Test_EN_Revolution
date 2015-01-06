package jp.pmw.test_en_revolution.questionnaire;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

/**
 * Created by scr on 2015/01/05.
 */
public class QuestionAskAdapter extends ArrayAdapter<Ask> {

    private LayoutInflater mFactory;
    private int mItemLayoutResource;

    public QuestionAskAdapter(Context context, int resource, List<Ask> objects) {
        super(context, resource, objects);
        mFactory = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mItemLayoutResource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final AskLayout view;
        if (convertView == null) {
            // Viewがなかったら生成
            view = (AskLayout) mFactory.inflate(mItemLayoutResource, null);
        } else {
            view = (AskLayout) convertView;
        }

        Ask ask = this.getItem(position);
        view.bindView(ask);

        return view;
    }

    //以下2つをfalseで返すと選択が行えなくなる
    public boolean areAllItemsEnabled() {
        return false;
    }
    public boolean isEnabled(int position) {
        return false;
    }
}
