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
        /*View view;
        if (convertView != null) {
            view = convertView;
        } else {
            view = mFactory.inflate(R.layout.row_question_item, null);
        }*/
        /*
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getContext(),
                android.R.layout.simple_list_item_1);
        for(int i=0;i<question.getAsks().size();i++){
            str = question.getAsks().get(i).getAskNumber() + ":" + question.getAsks().get(i).getAskContent();
            adapter.add(str);
        }
        ((ListView) view.findViewById(R.id.row_question_questions_listView)).setAdapter(adapter);
        */



        //str = question.getQuestionTitle();
        Question question = getItem(position);
        view.bindView(question);

        return view;
    }
}
