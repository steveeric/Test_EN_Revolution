package jp.pmw.test_en_revolution;

import com.google.gson.annotations.SerializedName;

/**
 * Created by si on 2016/02/05.
 */
public class SeatObject {
    //  自由席
    public static final int FREE_SEAT      =   0;
    //  指定席
    public static final int RESERVED_SEAT  =   1;
    //  グルーピング
    public static final int GROUPING       =   2;

    //  座席タイプ
    @SerializedName("seat_type")
    private int seatType;
    public int getSeatType(){return this.seatType;}

    //
    @SerializedName("seat_id")
    private String seatId;
    public String getSeatId(){return this.seatId;}

    //  座席名
    @SerializedName("seat_name")
    private String seatName;
    public String getSeatName(){return this.seatName;}

    //  グループ名
    @SerializedName("group_name")
    private String groupName;
    public String getGroupName(){return this.groupName;}

    SeatObject(String seatName,String groupName){
        this.seatName = seatName;
        this.groupName = groupName;
    }


}
