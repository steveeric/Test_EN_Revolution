package jp.pmw.test_en_revolution.room;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Shota Ito on 2014/12/08.
 */
public class Seat {
    /*座席ID*/
    @SerializedName("seat_id")
    private String seatId;
    @SerializedName("room_map_row")
    private int row;
    @SerializedName("room_map_column")
    private int column;
    /*テスト用出席状態にする*/
    private int preattendee=0;
    /*Seat(String seatId,int row,int column){
        this.seatId=seatId;
        this.row = row;
        this.column = column;
    }*/

    /**
     * Created by Shota Ito on 2014/12/11～2014/12/11.
     * getSeatIdメソッド
     * 座席IDのゲッター
     * @return 座席IDを返す.
     */
    public String getSeatId(){
        return this.seatId;
    }

    public int getSeatRowNumber(){
        return this.row;
    }
    public int getSeatColumnNumber(){
        return this.column;
    }

    /**
     * Created by Shota Ito on 2014/12/11～2014/12/11.
     * setPreAttendeeStateメソッド
     * テスト用の出席状態にする
     * 0:なにもなし,1:出席状態,2:欠席状態
     */
    public void setPreAttendeeState(int state){
        this.preattendee=state;
    }
    public int getPreAttendeeState(){
        return this.preattendee;
    }

}
