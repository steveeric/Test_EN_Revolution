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

    public List<DrawerBindData> getDrawerItem(Context context){
        List<DrawerBindData> items = new ArrayList<DrawerBindData>();
        boolean[] tapflags = {false,true,true,true,true,false,true,true,false,true,true,false,true,true};
        int[] select = {0,1,0,0,0,0,0,0,0,0,0,0,0,0};
        int[] resoreses = {
                0,
                R.drawable.ic_chear,
                R.drawable.ic_atend_list,
                R.drawable.ic_retry,
                R.drawable.ic_forgot_esl,
                0,
                R.drawable.ic_launcher,
                R.drawable.ic_launcher,
                0,
                R.drawable.ic_launcher,
                R.drawable.ic_launcher,
                0,
                R.drawable.ic_launcher,
                R.drawable.ic_launcher};
        int[] chapters = {1,0,0,0,0,1,0,0,1,0,0,1,0,0};
        int[] sections = {0,1,1,1,1,0,1,1,0,1,1,0,1,1};
        String[] titles = {"出席管理","着座状況","出席者一覧","再調査","ESL忘れ","アクティブラーニング","グルーピング","クリッカー","確認","個人履歴","講義内容","その他","ヘルプ","アプリケーション情報"};


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
