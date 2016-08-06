package jp.pmw.test_en_revolution.questionnaire;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import jp.pmw.test_en_revolution.R;

/**
 * Created by scr on 2014/12/25.
 * QuestionnaireCustomAdapterクラス
 * クリッカー問題のアダプターです.
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
        ViewHolder holder = null;
        View view = convertView;
        //  クリッカーオブジェクト
        Question question = getItem(position);
        //  VIew生成
        if (view == null) {
            view = mFactory.inflate(R.layout.row_question_item, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder)view.getTag();
        }
        //  クリッカー問題番号
        int index = position + 1;
        String strTitleNumber = getContext().getResources().getString(R.string.questionnaire_topic) + index + " " +question.getQuestionTitle();
        holder.mTitleNumberTv.setText( strTitleNumber );
        //  クリッカー問題
        List<Ask> asks = question.getAsks();
        String strClikcerQuestion = asks.get(0).getAskContent();
        holder.mClikcerQuestionTv.setText( strClikcerQuestion );

        return view;
    }
    //以下2つをfalseで返すと選択が行えなくなる
    public boolean areAllItemsEnabled() {
        return true;
    }
    public boolean isEnabled(int position) {
        return true;
    }
    /**
     *  ViewHolderクラス
     * */
    public class ViewHolder {
        //      クリッカー問題番号
        public TextView mTitleNumberTv;
        //      クリッカー問
        public TextView mClikcerQuestionTv;
        public ViewHolder(View view) {
            this.mTitleNumberTv = (TextView) view.findViewById(R.id.row_question_question_index_textView);
            this.mClikcerQuestionTv = (TextView) view.findViewById(R.id.row_clikcer_question_textView);
        }
    }
}
