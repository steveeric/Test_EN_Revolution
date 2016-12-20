package jp.pmw.test_en_revolution;

import com.google.gson.annotations.SerializedName;

/**
 * Created by si on 2016/12/20.
 */

public class AttendanceTotal {
    @SerializedName("attended_count")
    public int mAttendedCount;
    @SerializedName("absented_count")
    public int mAbsentedCount;
    @SerializedName("lated_count")
    public int mLatedCount;
    @SerializedName("leave_count")
    public int mLeaveCount;
    @SerializedName("total")
    public String mTotal;
}
