package jp.pmw.test_en_revolution.questionnaire;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import jp.pmw.test_en_revolution.R;

/**
 * Created by scr on 2014/12/25.
 */

public class QuestionLayout extends LinearLayout {
    private final int WC = ViewGroup.LayoutParams.WRAP_CONTENT;
    private final int MP = ViewGroup.LayoutParams.MATCH_PARENT;
    private TextView questionNumberTextView;
    //private TextView themeTextView;
    private ListView askListView;
    /*private TableLayout askTableLayout;
    private TextView questionTextView;
    private ListView questionsListView;*/

    LayoutParams mLayoutParams;

    public QuestionLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        questionNumberTextView = (TextView) findViewById(R.id.row_question_question_index_textView);
        //themeTextView = (TextView) findViewById(R.id.row_question_topic);
        //askListView = (ListView) findViewById(R.id.row_question_asks_listView);
        //askTableLayout = (TableLayout) findViewById(R.id.row_question_ask_tableLayout);
        //questionTextView = (TextView) findViewById(R.id.row_question_question_textView);
        //questionsListView = (ListView) findViewById(R.id.row_question_questions_listView);
    }

    /***
     *
     ***/
    public void bindView(int index,Question question) {
        //アンケート番号
        String str = this.getResources().getString(R.string.questionnaire_topic) + index + " " +question.getQuestionTitle();
        this.questionNumberTextView.setText(str);
        //尋ねる内容
        List<Ask> asks = question.getAsks();
        for(int i = 0; i < asks.size();i++){
            //Q.〇×■◀のレイアウト
            LinearLayout layout = new LinearLayout(this.getContext());
            layout.setOrientation(LinearLayout.HORIZONTAL);
            layout.setBackgroundColor(this.getResources().getColor(R.color.lightCyan));

            float fontSize = this.getContext().getResources().getDimension(R.dimen.textsize_xlarge);
            //Q.のテキストビュー
            TextView questionIndexTv = new TextView(this.getContext());
            questionIndexTv.setTextSize(fontSize);
            setBackGroundColor(questionIndexTv);
            String quecon = "　";
            questionIndexTv.setText(quecon);
            questionIndexTv.setTextSize(fontSize);
            layout.addView(questionIndexTv);

            //〇×■◀のテキストビュー
            TextView tv = new TextView(this.getContext());
            setBackGroundColor(tv);
            String content = asks.get(i).getAskContent();
            tv.setText(content);
            tv.setTextSize(fontSize);
            layout.addView(tv);
            mLayoutParams = new LinearLayout.LayoutParams(MP, MP);
            this.addView(layout, mLayoutParams);
        }
    }
    private void setBackGroundColor(TextView tv){
        tv.setBackgroundColor(this.getResources().getColor(R.color.lightCyan));
    }
}
