package jp.pmw.test_en_revolution.confirm_class_plan;

import com.google.gson.annotations.SerializedName;

/**
 * Created by scr on 2014/12/24.
 * 座席を管理するクラスです.
 */
public class Seat {
    //座席ID
    @SerializedName("seat_id")
    private String seatId;
    //座席名
    @SerializedName("seat_name")
    private String seatName;
    //座席ブロック名
    @SerializedName("seat_block_name")
    private String seatBlokName;
    //座席行
    @SerializedName("seat_row")
    private int row;
    //座席列
    @SerializedName("seat_column")
    private int column;
    //
    @SerializedName("cell_seat_row")
    private int cellSeatRow;
    //
    @SerializedName("cell_seat_column")
    private int cellSeatColumn;



    public Seat(String seatId,int row,int column){
        this.seatId = seatId;
        this.row = row;
        this.column = column;
    }

    public String getSeatId(){
        return this.seatId;
    }
    public String getSeatName(){return this.seatName;}
    public String getSeatBlokName(){return this.seatBlokName;}
    public int getRow(){
        return this.row;
    }
    public int getColumn(){
        return this.column;
    }
    public int getCellSeatRow(){return this.cellSeatRow;}
    public int getCellSeatColumn(){return this.cellSeatColumn;}
}
