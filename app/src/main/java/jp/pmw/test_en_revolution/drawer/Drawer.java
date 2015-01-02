package jp.pmw.test_en_revolution.drawer;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import jp.pmw.test_en_revolution.R;

/**
 * Created by scr on 2014/12/08.
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
            items.add(new DrawerBindData(tapflags[i],select[i],chapter,section,res,title));
        }
        return items;
    }


    public List<DrawerBindData> getDrawerItem(Context context){
        List<DrawerBindData> items = new ArrayList<DrawerBindData>();
        boolean[] tapflags = {false,true,true,false,true,true};

        //int[] select = {0,1,0,0,0,0};
        int[] select = {0,0,0,0,0,0};
        int[] resoreses = {
                0,
                R.drawable.ic_chear,
                R.drawable.ic_atend_list,
                0,
                R.drawable.ic_grouping, //
                R.drawable.ic_questionnaire, //
                };
        int[] chapters = {1,0,0,1,0,0};
        int[] sections = {0,1,1,0,1,1};
        String[] titles = {"ハンズフリー出席管理","着座状況","受講者一覧","アクティブラーニング","グルーピィング","アンケート"};
        for(int i = 0; i < resoreses.length; i++){
            int chapter = chapters[i];
            int section = sections[i];
            int res = resoreses[i];
            String title = titles[i];
            items.add(new DrawerBindData(tapflags[i],select[i],chapter,section,res,title));
        }
        return items;
    }



}
