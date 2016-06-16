package jp.pmw.test_en_revolution.questionnaire;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import jp.pmw.test_en_revolution.R;

/**
 * Created by scr on 2014/12/31.
 */
public class AnswerLayout extends LinearLayout {
    private final int WC = ViewGroup.LayoutParams.WRAP_CONTENT;
    private TextView indexColorTextView;
    private TextView choiceTextView;
    private TextView answerCountTextView;
    private TextView answerPercentageTextView;

    public AnswerLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        indexColorTextView = (TextView) findViewById(R.id.row_questionnaire_answer_result_index_color_textView);
        choiceTextView = (TextView) findViewById(R.id.row_questionnaire_answer_result_choice_textView);
        answerCountTextView = (TextView) findViewById(R.id.row_questionnaire_answer_result_answer_count_textView);
        answerPercentageTextView = (TextView) findViewById(R.id.row_questionnaire_answer_result_answer_percentage_textView);
    }

    /***
     *
     ***/
    public void bindView(Choice answer) {
        this.indexColorTextView.setBackgroundColor(Color.parseColor(answer.getChoiceIndexColor()));
        this.choiceTextView.setText(answer.getChoice());
        this.answerCountTextView.setText(answer.getQuestionResult().getAnswerCount()+"人");
        this.answerPercentageTextView.setText(""+answer.getQuestionResult().getPercentage()+"%");
    }
}
