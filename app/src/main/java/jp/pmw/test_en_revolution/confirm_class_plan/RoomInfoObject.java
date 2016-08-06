package jp.pmw.test_en_revolution.confirm_class_plan;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by scr on 2015/02/24.
 */
public class RoomInfoObject {
    @SerializedName("floor")
    public SeatingChart seatcingChart;
    @SerializedName("seat")
    public List<Seat> seats;
}
