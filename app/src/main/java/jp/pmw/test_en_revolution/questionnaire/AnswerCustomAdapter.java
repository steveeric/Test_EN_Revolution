package jp.pmw.test_en_revolution.questionnaire;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

/**
 * Created by scr on 2014/12/31.
 */
public class AnswerCustomAdapter extends ArrayAdapter<Answer> {

    private LayoutInflater mFactory;
    private int mItemLayoutResource;

    public AnswerCustomAdapter(Context context, int resource, List<Answer> objects) {
        super(context, resource, objects);
        mFactory = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mItemLayoutResource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final AnswerLayout view;

        if (convertView == null) {
            // Viewがなかったら生成
            view = (AnswerLayout) mFactory.inflate(mItemLayoutResource, null);
        } else {
            view = (AnswerLayout) convertView;
        }

        Answer answer = getItem(position);
        view.bindView(answer);

        return view;
    }
}
