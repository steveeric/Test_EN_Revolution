package jp.pmw.test_en_revolution.questionnaire;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import jp.pmw.test_en_revolution.R;

/**
 * Created by scr on 2015/01/05.
 */
public class AskLayout extends LinearLayout {
    private final int WC = ViewGroup.LayoutParams.WRAP_CONTENT;
    private TextView askNumber;
    private TextView askTheme;

    public AskLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        askNumber = (TextView) findViewById(R.id.row_ask_number_textView);
        askTheme = (TextView) findViewById(R.id.row_ask_theme_textView);
    }

    /***
     *
     ***/
    public void bindView(Ask ask) {
        String number = ask.getAskNumberWord();
        askNumber.setText(number);
        String theme = ask.getAskContent();
        askTheme.setText(theme);
    }
}
