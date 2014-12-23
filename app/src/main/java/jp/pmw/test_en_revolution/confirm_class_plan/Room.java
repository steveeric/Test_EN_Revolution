package jp.pmw.test_en_revolution.confirm_class_plan;

import java.io.Serializable;

/**
 * Created by scr on 2014/12/17.
 * 部屋に関する情報を保持するクラス
 * @author Shota Ito
 * @version 1.0
 */
public class Room implements Serializable {
    //教室ID
    private String roomId;
    //教室名
    private String roomName;
    //教室の座席数のキャパシティ
    private int maxSeatCapacity;
    public Room(String roomId,String roomName,int capacity){
        this.roomId = roomId;
        this.roomName = roomName;
        this.maxSeatCapacity = capacity;
    }
    //教室ID
    public String getRoomId(){return this.roomId;}
    //教室名
    public String getRoomName(){return this.roomName;}
    //
    public int getMaxSeatCapacity(){return this.maxSeatCapacity;}
}
