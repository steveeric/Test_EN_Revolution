package jp.pmw.test_en_revolution.questionnaire;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

/**
 * Created by scr on 2014/12/25.
 */
public class QuestionnaireCustomAdapter extends ArrayAdapter<Question> {

    private LayoutInflater mFactory;
    private int mItemLayoutResource;

    public QuestionnaireCustomAdapter(Context context, int resource, List<Question> objects) {
        super(context, resource, objects);
        mFactory = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mItemLayoutResource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final QuestionLayout view;

        if (convertView == null) {
            // Viewがなかったら生成
            view = (QuestionLayout) mFactory.inflate(mItemLayoutResource, null);
        } else {
            view = (QuestionLayout) convertView;
        }

        //str = question.getQuestionTitle();
        Question question = getItem(position);
        int index = position + 1;
        view.bindView(index,question);

        return view;
    }
    //以下2つをfalseで返すと選択が行えなくなる
    public boolean areAllItemsEnabled() {
        return true;
    }
    public boolean isEnabled(int position) {
        return true;
    }
}
