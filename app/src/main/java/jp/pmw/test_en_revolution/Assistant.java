package jp.pmw.test_en_revolution;

import com.google.gson.annotations.SerializedName;

/**
 * Created by si on 2016/04/24.
 * 補助者クラスです.
 * 授業に参加している学生の左右に、
 * 授業補佐を行う学生が、
 * andoidタブレット上の着座表示の際に、
 * どこに着席しているかをわかるようにするための
 * クラスです.
 */
public class Assistant {
    //  出席カラー(StudentObjectのAttendanceObjetの出席カラー)
    private int attendanceColor = 0;

    Assistant(){

    }

    @SerializedName("seat_id")
    private String seatId;
    public String getSeatId(){return this.seatId;}

    //  出席カラー
    public void setAttendanceColor(int attendanceColor){
        this.attendanceColor = attendanceColor;
    }
    //  出席カラー
    public int getAttendanceColor(){
        return attendanceColor;
    }

}
