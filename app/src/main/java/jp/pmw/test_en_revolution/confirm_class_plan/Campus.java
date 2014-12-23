package jp.pmw.test_en_revolution.confirm_class_plan;

import java.io.Serializable;

/**
 * Created by scr on 2014/12/17.
 * キャンパスに関する情報を保持するクラス
 * @author Shota Ito
 * @version 1.0
 */
public class Campus implements Serializable {
    private String campusId;
    private String campusName;

    public Campus(String campusId,String campusName){
        this.campusId = campusId;
        this.campusName = campusName;
    }

    public String getCampusId(){
        return this.campusId;
    }
    public String getCampusName(){
        return this.campusName;
    }
}
