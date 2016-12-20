package jp.pmw.test_en_revolution;

import com.google.gson.annotations.SerializedName;

/**
 * Created by si on 2016/12/19.
 * SonoRIs MTから出席・遅刻などの状態ステータスを変更する際に、
 * 使用するステータスオブジェクト
 */

public class ManulReason {

    public static final int ACCEPTED_ATTENDANCE_ACK_AVAILABLE = 1;
    public static final int ACCEPTED_ATTENDANCE_AACK_AVAILABLE = 2;
    @SerializedName("reason_id")
    public String mReasonId;
    @SerializedName("state")
    public int mState;
    @SerializedName("reason_number")
    public String mReasonNumber;
    @SerializedName("reason")
    public String mReason;
    @SerializedName("forgot")
    public int mForgot;
    //  出席認定ACKに応じて表示するかを決める(0:無関係,1:出席認定ACKのみ表示)
    @SerializedName("show_according_to_ack")
    public int mShowAccordingToACK;
    @SerializedName("do_not_show_ack_response")
    public int mDoNotShowACKResponse;
}
