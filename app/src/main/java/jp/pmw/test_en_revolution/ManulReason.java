package jp.pmw.test_en_revolution;

import com.google.gson.annotations.SerializedName;

/**
 * Created by si on 2016/12/19.
 * SonoRIs MTから出席・遅刻などの状態ステータスを変更する際に、
 * 使用するステータスオブジェクト
 */

public class ManulReason {
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
    @SerializedName("do_not_show_ack_response")
    public int mDoNotShowACKResponse;
}
