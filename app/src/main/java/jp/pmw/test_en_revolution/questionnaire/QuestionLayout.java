package jp.pmw.test_en_revolution.questionnaire;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import jp.pmw.test_en_revolution.R;

/**
 * Created by scr on 2014/12/25.
 */

public class QuestionLayout extends LinearLayout {
    private final int WC = ViewGroup.LayoutParams.WRAP_CONTENT;
    private TextView themeTextView;
    /*private TableLayout askTableLayout;
    private TextView questionTextView;
    private ListView questionsListView;*/

    public QuestionLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        themeTextView = (TextView) findViewById(R.id.row_question_topic);
        //askTableLayout = (TableLayout) findViewById(R.id.row_question_ask_tableLayout);
        //questionTextView = (TextView) findViewById(R.id.row_question_question_textView);
        //questionsListView = (ListView) findViewById(R.id.row_question_questions_listView);
    }

    /***
     *
     ***/
    public void bindView(Question question) {
        String str = "";
        str = question.getQuestionTitle();
        str = str + "\n";
        for(int i = 0; i < question.getAsks().size(); i++){
            //問
            //String s = question.getAsks().get(i).getAskNumber()+":"+question.getAsks().get(i).getAskContent();
            String s = this.getContext().getResources().getString(R.string.questionnaire_number)+question.getAsks().get(i).getAskNumber()+" "+question.getAsks().get(i).getAskContent();
            str = str + s + "\n";
            /*TextView tv = new TextView(this.getContext());
            tv.setTextColor(this.getContext().getResources().getColor(R.color.black));
            tv.setText(str);
            addView(tv);*/
        }

        //テーマ
        this.themeTextView.setText(str);
    }
}
