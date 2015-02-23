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

    //ハンズフリー出席管理
    public static final int HANDS_FREE_ATTENDANCE = 1;
    //アクティブラーニング
    public static final int ACTIVE_LEARNING = 2;
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
    // 選択アイコン
    ImageView mSelectIconView;

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
        mSelectIconView = (ImageView) findViewById(R.id.item_select_icon);
    }
    /**
     * NavigationDrawerに入れる
     * **/
    public void bindView(DrawerBindData item) {
        //カテゴリー
        int contentCategory = item.mContentCategory;
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
            setBarckGround(contentCategory,chapgerFlag,sectionFlag,mDrawerChapterLayout);
        }else if(chapgerFlag == 0 && sectionFlag == 1){
            //節用(選択可能)
            //mDrawerLectureConfirmLayout.setVisibility(View.GONE);
            mDrawerChapterLayout.setVisibility(View.GONE);
            mDrawerSectionLayout.setVisibility(View.VISIBLE);
            mIconView.setImageResource(item.mIconRes);
            mTitleView.setText(item.mTitle);
            //mTitleView.setBackgroundColor(this.getResources().getColor(R.color.handsFreeAttendanceContent));
            setBarckGround(contentCategory,chapgerFlag,sectionFlag,mDrawerSectionLayout);
            /*選択されているか*/
            if(select == 1){
                mSelectIconView.setVisibility(View.VISIBLE);
                //mDrawerSectionLayout.setBackgroundColor(this.getContext().getResources().getColor(R.color.semitransparenttranslucent_gray));
                //mDrawerSectionLayout.setBackgroundColor(this.getContext().getResources().getColor(R.color.dronaviContentSelect));
            }else {
                mSelectIconView.setVisibility(View.INVISIBLE);
            }/*else{
                mDrawerSectionLayout.setBackgroundColor(this.getContext().getResources().getColor(R.color.whiteSmoke));
            }*/

        }else{
            //mDrawerLectureConfirmLayout.setVisibility(View.VISIBLE);
            mDrawerChapterLayout.setVisibility(View.GONE);
            mDrawerSectionLayout.setVisibility(View.GONE);
        }
    }

    /**
     * Created by scr on 2015/1/6.
     * moveToTopActivityメソッド
     * トップのアクティビティーまで戻る(今回は50音順まで)
     */
     private void setBarckGround(int contentCategory,int chapter,int section,LinearLayout layout){
         int corlor = this.getResources().getColor(R.color.semitransparenttranslucent_gray);
        if(this.HANDS_FREE_ATTENDANCE == contentCategory){
            if(chapter == 1 && section == 0){
                corlor = this.getResources().getColor(R.color.handsFreeAttendanceTitleBackGround);
            }else{
                corlor = this.getResources().getColor(R.color.handsFreeAttendanceContentBackGround);
            }
        }else if(this.ACTIVE_LEARNING == contentCategory){
            if(chapter == 1 && section == 0){
                corlor = this.getResources().getColor(R.color.activeLearningTitleBackGround);
            }else{
                corlor = this.getResources().getColor(R.color.activeLearningContentBackGround);
            }
        }
         layout.setBackgroundColor(corlor);
     }

}