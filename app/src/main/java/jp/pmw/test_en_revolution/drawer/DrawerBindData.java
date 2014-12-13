package jp.pmw.test_en_revolution.drawer;

/**
 * Created by scr on 2014/12/08.
 */
public class DrawerBindData {
    public boolean mTapFlag;
    public int mSelectState;
    public int mChapter;
    public int mSection;
    public String mChapterStr;
    public int mIconRes;
    public String mTitle;
    DrawerBindData(boolean tapflag, int mSelectState, int chapter, int section, int iconRes, String title){
        this.mTapFlag = tapflag;
        this.mSelectState = mSelectState;
        this.mChapter = chapter;
        this.mSection = section;
        this.mIconRes = iconRes;
        this.mTitle = title;
    }

    public void tap(){
        this.mSelectState = 1;
    }

    public void unFocus(){
        this.mSelectState = 0;
    }
}
