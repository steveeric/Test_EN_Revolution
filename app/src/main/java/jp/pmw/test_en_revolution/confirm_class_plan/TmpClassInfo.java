package jp.pmw.test_en_revolution.confirm_class_plan;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by scr on 2015/02/24.
 */
public class TmpClassInfo {
    @SerializedName("room")
    public RoomInfoObject tmpRoomInfo;
    @SerializedName("registration")
    public List<Student> rosterList;
    @SerializedName("last_grouping_time")
    public String lastGroupingTime;
}
