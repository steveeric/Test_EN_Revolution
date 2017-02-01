package jp.pmw.test_en_revolution.survey;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import jp.pmw.test_en_revolution.R;

/**
 * Created by si on 2016/08/17.
 */
public class ChoiceLayout extends LinearLayout {
    private final int WC = ViewGroup.LayoutParams.WRAP_CONTENT;
    private TextView indexColorTextView;
    private TextView choiceTextView;
    private TextView answerCountTextView;
    private TextView answerPercentageTextView;

    public ChoiceLayout(Context context, AttributeSet attrs) {
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
        this.indexColorTextView.setBackgroundColor(Color.parseColor(answer.mChoiceIndexColor));
        this.choiceTextView.setText(answer.mChoice);
        this.answerCountTextView.setText(answer.mStrAnswer);
        this.answerCountTextView.setTextColor(Color.parseColor(answer.mChoiceIndexColor));
        this.answerPercentageTextView.setText(answer.mPercentage);
        this.answerPercentageTextView.setTextColor((Color.parseColor(answer.mChoiceIndexColor)));
    }
}
