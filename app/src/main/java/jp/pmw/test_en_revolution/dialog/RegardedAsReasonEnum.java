package jp.pmw.test_en_revolution.dialog;

import jp.pmw.test_en_revolution.R;

/**
 * Created by si on 2016/06/21.
 * 学生情報確認ダイアログで、
 * 手動にて「出席・遅刻・欠席」を選択時のEnumクラス
 */
public enum RegardedAsReasonEnum {
    //  並び順(StringのリソースID, 理由ID, 忘れ)
    NOT_SELECTED(-1, 0, 0),
    REASON_DID_NOT_SHOW(R.id.dialog_custom_regarded_as_reason_did_not_show_rb, 1, 0),
    //REASON_ACK_NO_RESPONSE(R.id.dialog_custom_regarded_as_reason_ack_no_response_rb, 2, 0),
    REASON_FORGOT(R.id.dialog_custom_regarded_as_reason_forgot_rb, 2, 1),
    REASON_OTHER1(R.id.dialog_custom_regarded_as_reason_other1_rb, 3, 0);

    //  リソースID
    private final int mResorceId;
    //  理由
    private final int mReasonId;
    //  忘れ
    private final int mForgot;

    private RegardedAsReasonEnum(final int id,final int reasonId,final int forgot) {
        this.mResorceId = id;
        this.mReasonId = reasonId;
        this.mForgot = forgot;
    }
    /**
     *  getResorceIdメソッド
     * */
    public int getResorceId() {
        return mResorceId;
    }
    /**
     * getReasonIdメソッド
     * */
    public int getReasonId(){
        return mReasonId;
    }
    /**
     *  getForgotメソッド
     * */
    public int getForgot(){
        return mForgot;
    }
    /**
     *  valueOfメソッド
     * */
    public static RegardedAsReasonEnum valueOf(int reasonId) {
        // values() で、列挙したオブジェクトをすべて持つ配列が得られる
        for (RegardedAsReasonEnum num : values()) {
            if (num.getResorceId() == reasonId) { // id が一致するものを探す
                return num;
            }
        }
        throw new IllegalArgumentException("no such enum object for the id: " + reasonId);
    }
}