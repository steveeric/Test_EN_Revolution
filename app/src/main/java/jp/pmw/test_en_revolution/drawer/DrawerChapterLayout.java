package jp.pmw.test_en_revolution.drawer;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import jp.pmw.test_en_revolution.R;

/**
 * Created by scr on 2014/12/08.
 */
public class DrawerChapterLayout  extends LinearLayout {
    // アイコン
    ImageView mIconView;
    // タイトル
    TextView mTitleView;

    public DrawerChapterLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mIconView = (ImageView) findViewById(R.id.item_icon);
        mTitleView = (TextView) findViewById(R.id.item_title);

    }
    /**
     * NavigationDrawerに入れる
     * **/
    public void bindView(DrawerBindData item) {
        mIconView.setImageResource(item.mIconRes);
        mTitleView.setText(item.mTitle);
        /*if (data.mCounter > 0) {
            counter.setText(String.valueOf(data.mCounter));
            counter.setVisibility(View.VISIBLE);
        } else {
            counter.setVisibility(View.INVISIBLE);
        }*/
    }
}