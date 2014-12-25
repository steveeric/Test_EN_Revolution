package jp.pmw.test_en_revolution.confirm_class_plan;

/**
 * Created by scr on 2014/12/24.
 * 座席を管理するクラスです.
 */
public class Seat {
    //座席ID
    private String seatId;
    //座席行
    private int row;
    //座席列
    private int column;

    public Seat(String seatId,int row,int column){
        this.seatId = seatId;
        this.row = row;
        this.column = column;
    }

    public String getSeatId(){
        return this.seatId;
    }
    public int getRow(){
        return this.row;
    }
    public int getColumn(){
        return this.column;
    }
}
