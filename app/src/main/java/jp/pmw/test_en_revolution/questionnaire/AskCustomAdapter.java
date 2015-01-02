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
public class AskCustomAdapter extends ArrayAdapter<Ask> {

    private LayoutInflater mFactory;
    private int mItemLayoutResource;

    public AskCustomAdapter(Context context, int resource, List<Ask> objects) {
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
        String str = "";
        Ask question = getItem(position);

        //view.bindView(question);
        //
        /*str = question.getQuestionTitle();
        view.bindView(0,str);
        for(int i = 0; i < question.getAsks().size(); i++){
            //問
            str = question.getAsks().get(i).getAskNumber()+":"+question.getAsks().get(i).getAskContent();
            view.bindView(1,str);
        }*/
        return view;
    }
}
