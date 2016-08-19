package jp.pmw.test_en_revolution.drawer;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import jp.pmw.test_en_revolution.R;

/**
 * Created by scr on 2014/12/08.
 * ドロワーナビゲーションにセットするアイテムオブジェクト
 */
public class Drawer {
    Drawer(){
    }
    public List<DrawerBindData>getKanaDrawerItem(Context context){
        List<DrawerBindData> items = new ArrayList<DrawerBindData>();
        boolean[] tapflags = {false};
        int[] select = {0};
        int[] resoreses = {0};
        int[] chapters = {1};
        int[] sections = {0};
        String[] titles = {""};
        for(int i = 0; i < resoreses.length; i++){
            int chapter = chapters[i];
            int section = sections[i];
            int res = resoreses[i];
            String title = titles[i];
            //items.add(new DrawerBindData(tapflags[i],select[i],chapter,section,res,title));
        }
        return items;
    }
    public List<DrawerBindData> getDrawerItem(Context context){
        List<DrawerBindData> items = new ArrayList<DrawerBindData>();
        boolean[] tapflags = {false,true,true,false,true,true,true};

        int[] select = {0,0,0,0,0,0,0};
        int[] resoreses = {
                0,
                R.drawable.ic_chear,
                R.drawable.ic_atend_list,
                0,
                R.drawable.ic_questionnaire,
                R.drawable.ic_grouping,
                R.drawable.ic_questionnaire
        };
        int[] chapters = {1,0,0,1,0,0,0};
        int[] sections = {0,1,1,0,1,1,1};
        int[] category = {DrawerLayout.HANDS_FREE_ATTENDANCE, DrawerLayout.HANDS_FREE_ATTENDANCE, DrawerLayout.HANDS_FREE_ATTENDANCE
                ,DrawerLayout.ACTIVE_LEARNING, DrawerLayout.ACTIVE_LEARNING, DrawerLayout.ACTIVE_LEARNING, DrawerLayout.ACTIVE_LEARNING};
        String menu1 = context.getString(R.string.title_section1);
        String menu2 = context.getString(R.string.title_section2);
        String menu6 = context.getString(R.string.title_section6);
        String menu12 = context.getString(R.string.title_section12);
        String menu13 = context.getString(R.string.title_section13);
        String[] titles = {"ハンズフリー出席管理", menu1, menu2, "アクティブラーニング", menu6, menu12, menu13};

        for(int i = 0; i < resoreses.length; i++){
            int chapter = chapters[i];
            int section = sections[i];
            int res = resoreses[i];
            String title = titles[i];
            items.add(new DrawerBindData(category[i],tapflags[i],select[i],chapter,section,res,title));
        }
        return items;
    }
}
