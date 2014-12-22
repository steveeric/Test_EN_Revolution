package jp.pmw.test_en_revolution.drawer;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import jp.pmw.test_en_revolution.R;

/**
 * Created by scr on 2014/11/26.
 */
public class DrawerLayout extends LinearLayout {
    //
    //LinearLayout mDrawerLectureConfirmLayout;
    //章用のレイアウト
    LinearLayout mDrawerChapterLayout;
    //節用のレイアウト
    LinearLayout mDrawerSectionLayout;
    //
    TextView mChapterView;

    // アイコン
    ImageView mIconView;
    // タイトル
    TextView mTitleView;

    public DrawerLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        //mDrawerLectureConfirmLayout = (LinearLayout) findViewById(R.id.drawer_lecture_confirm_layout);
        mDrawerChapterLayout = (LinearLayout) findViewById(R.id.drawer_chapger_layout);
        mDrawerSectionLayout = (LinearLayout) findViewById(R.id.drawer_section_layout);
        mChapterView = (TextView) findViewById(R.id.chapter_textView);
        mIconView = (ImageView) findViewById(R.id.item_icon);
        mTitleView = (TextView) findViewById(R.id.item_title);
    }
    /**
     * NavigationDrawerに入れる
     * **/
    public void bindView(DrawerBindData item) {
        //チャプターフラグ
        int chapgerFlag = item.mChapter;
        //節フラグ
        int sectionFlag = item.mSection;
        int select = item.mSelectState;
        if(chapgerFlag == 1 && sectionFlag == 0){
            //章用
            //mDrawerLectureConfirmLayout.setVisibility(View.GONE);
            mDrawerChapterLayout.setVisibility(View.VISIBLE);
            mDrawerSectionLayout.setVisibility(View.GONE);
            mChapterView.setText(item.mTitle);
        }else if(chapgerFlag == 0 && sectionFlag == 1){
            //節用(選択可能)
            //mDrawerLectureConfirmLayout.setVisibility(View.GONE);
            mDrawerChapterLayout.setVisibility(View.GONE);
            mDrawerSectionLayout.setVisibility(View.VISIBLE);
            mIconView.setImageResource(item.mIconRes);
            mTitleView.setText(item.mTitle);
            /*選択されているか*/
            if(select == 1){
                mDrawerSectionLayout.setBackgroundColor(this.getContext().getResources().getColor(R.color.gray));
            }else{
                mDrawerSectionLayout.setBackgroundColor(this.getContext().getResources().getColor(R.color.whiteSmoke));
            }
        }else{
            //mDrawerLectureConfirmLayout.setVisibility(View.VISIBLE);
            mDrawerChapterLayout.setVisibility(View.GONE);
            mDrawerSectionLayout.setVisibility(View.GONE);
        }
    }
}