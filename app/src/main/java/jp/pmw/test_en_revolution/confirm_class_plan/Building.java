package jp.pmw.test_en_revolution.confirm_class_plan;

import java.io.Serializable;

/**
 * Created by scr on 2014/12/17.
 */
public class Building implements Serializable {
    private String buildingId;
    private String buildingName;

    public Building(String buildingId,String buildingName){
        this.buildingId = buildingId;
        this.buildingName = buildingName;
    }

    public String getBuildingId(){
        return this.buildingId;
    }
    public String getBuildingName(){
        return this.buildingName;
    }
}
