package jp.pmw.test_en_revolution.confirm_class_plan;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by scr on 2014/12/23.
 * 場所に関することを管理するクラスです.
 */
public class Place implements Serializable {
    @SerializedName("room_id")
    String roomId;
    @SerializedName("building_name")
    String buildingName;
    @SerializedName("room_name")
    String roomName;

    Room room;
    Building building;
    Campus campus;
    MextUniversity university;

    public Place(MextUniversity university,Campus campus,Building building,Room room){
        this.university = university;
        this.campus = campus;
        this.building = building;
        this.room = room;
    }
    public String getRoomId(){
        return this.roomId;
    }
    public String getBuildingName(){
        return this.buildingName;
    }
    public String getRoomName(){
        return this.roomName;
    }



    public Room getRoom(){return this.room;}
    public Building getBuilding(){return this.building;}
    public Campus getCampus(){return this.campus;}
    public MextUniversity getMextUniversity(){return this.university;}
}
