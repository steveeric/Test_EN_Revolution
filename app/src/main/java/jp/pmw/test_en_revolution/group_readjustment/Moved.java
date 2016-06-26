package jp.pmw.test_en_revolution.group_readjustment;

import com.google.gson.annotations.SerializedName;

/**
 * Created by si on 2016/05/16.
 */
public class Moved {
        @SerializedName("seat_after_moving")
        private String seatAfterMoving;
        @SerializedName("attendance_id")
        private String attendanceId;
        @SerializedName("moved_seat_id")
        private String movedSeatId;
        @SerializedName("line1")
        private String line1;
        @SerializedName("line2")
        private String line2;
        @SerializedName("line3")
        private String line3;
        @SerializedName("line4")
        private String line4;
        @SerializedName("position")
        private String mPosition;
        @SerializedName("contact_date_time")
        private String contactDateTime;

        public String getSeatAfterMoving(){return this.seatAfterMoving;}
        public String getAttendanceId(){return this.attendanceId;}
        public String getMovedSeatId(){return this.movedSeatId;}
        public String getLine1(){return this.line1;}
        public String getLine2(){return this.line2;}
        public String getLine3(){return this.line3;}
        public String getLine4(){return this.line4;}
        public String getPosition(){return this.mPosition;}
        public void setContactDateTime(String contactDateTime){
                this.contactDateTime = contactDateTime;
        }
        public String getContactDateTime(){return this.contactDateTime;}
}
