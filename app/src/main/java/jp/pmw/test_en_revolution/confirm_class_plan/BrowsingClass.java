package jp.pmw.test_en_revolution.confirm_class_plan;

import java.io.Serializable;

/**
 * Created by scr on 2014/12/23.
 * 出席状況閲覧状況を把握するためのクラスです.
 * 閲覧時間のログをDBに記録する際にbrowsingIdが必要になります.
 */
public class BrowsingClass implements Serializable {
    private String browsingClassId;

    public BrowsingClass(String browsingClassId){
        this.browsingClassId = browsingClassId;
    }
    public String getBrowsingClassId(){
        return this.browsingClassId;
    }
}
