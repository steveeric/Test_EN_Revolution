package jp.pmw.test_en_revolution.attendee;

import com.google.gson.annotations.SerializedName;

/**
 * Created by si on 2016/12/17.
 * ACKインジケーターの状態を管理します.
 */

public class Indicator {
    //  デフォルトのシグナル(グレー)
    public static final String DEF = "def";
    //  ACK無しシグナル(赤)
    public static final String NAC = "nac";
    //  ACK有りシグナル(緑)
    public static final String ACK = "ack";
    //  送信対象でACK無しを手動フォロー(中:赤、外:緑)
    public static final String TMF = "tmf";
    //  送信未対象を手動フォロー(中:グレー、外:緑)
    public static final String NMF = "nmf";

    @SerializedName("seat")
    public String mSeat;
    @SerializedName("attendance")
    public String mAttendance;
    @SerializedName("in_room")
    public String mInRoom;
    @SerializedName("privacy")
    public String mPrivacy;

        /*
    //  デフォルトのシグナル(グレー)
    public static final String DEFAULT_SIGNAL = "def";
    //  ACK無しシグナル(赤)
    public static final String NACK_SIGNAL = "nac";
    //  ACK有りシグナル(緑)
    public static final String ACK_SIGNAL = "ack";
    //  送信対象でACK無しを手動フォロー(中:赤、外:緑)
    public static final String TARGET_MANUAL_FOLLOW_SIGNAL = "tmf";
    //  送信未対象を手動フォロー(中:グレー、外:緑)
    public static final String NOT_TARGET_MANUAL_FOLLOW_SIGNAL = "nmf";
    */
}
