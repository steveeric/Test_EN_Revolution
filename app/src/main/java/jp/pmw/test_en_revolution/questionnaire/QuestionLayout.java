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
    private TextView questionNumberTextView;
    //private TextView themeTextView;
    private ListView askListView;
    /*private TableLayout askTableLayout;
    private TextView questionTextView;
    private ListView questionsListView;*/

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
        //this.questionNumberTextView.setText(this.getContext().getResources().getString(R.string.question_index_number) + index);
        String str = question.getQuestionTitle();
        this.questionNumberTextView.setText(str);
        //テーマ
        //this.themeTextView.setText(str);
        //尋ねる内容
        List<Ask> asks = question.getAsks();
        /*MainActivity activity = (MainActivity)this.getContext();
        QuestionAskAdapter adapter = new QuestionAskAdapter(activity,R.layout.row_ask_item,asks);
        this.askListView.setAdapter(adapter);*/
        for(int i = 0; i < asks.size();i++){
            TextView tv = new TextView(this.getContext());
            //Q.(問題内容)
            String content = "　"
                    +this.getContext().getResources().getString(R.string.ask_number)
                    +asks.get(i).getAskNumber()
                    +this.getContext().getResources().getString(R.string.ask_dot)
                    +asks.get(i).getAskContent();
            tv.setText(content);
            //問題の門とサイズ
            float fontSize = this.getContext().getResources().getDimension(R.dimen.textsize_large);
            tv.setTextSize(fontSize);
            this.addView(tv);
            for(int j = 0; j < asks.get(i).getAnswer().size(); j++){
                TextView anstv = new TextView(this.getContext());
                String asnContent = "　　"
                        + asks.get(i).getAnswer().get(j).getAnswerIndexNumber()
                        + "　"
                        + asks.get(i).getAnswer().get(j).getAnswerContent();
                anstv.setText(asnContent);
                float ansSize = this.getContext().getResources().getDimension(R.dimen.textsize_medium);
                anstv.setTextSize(ansSize);
                this.addView(anstv);
            }
        }
    }
}
