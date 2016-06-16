package jp.pmw.test_en_revolution.room;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by scr on 2014/12/11.
 * 教室情報を管理するクラス.
 *  @author Shota Ito
 * @version 1.0
 */
public class Room {
    @SerializedName("map")
    private String map;
    @SerializedName("enable_rows")
    private int enableRows;
    @SerializedName("disabled_rows")
    private int  disabledRows;
    @SerializedName("cell_rows")
    private int cellRows;
    @SerializedName("cell_columns")
    private int  cellColumns;
    @SerializedName("cell")
    private RoomMap roomMap;
    @SerializedName("seat")
    private List<Seat_1> seats;


    public String getRoomMaping(){
        return this.map;
    }
    public int getEnableRows(){return this.enableRows;}
    public int getDisabledRows(){return this.disabledRows;}
    public int getCellRows(){
        return this.cellRows;
    }
    public int getCellColumns(){
        return this.cellColumns;
    }

    /*public RoomMap getRoomMap(){
        return this.roomMap;
    }*/

    public List<Seat_1> getSeats(){
        return this.seats;
    }
}
