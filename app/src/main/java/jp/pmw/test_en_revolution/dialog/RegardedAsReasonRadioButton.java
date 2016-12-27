package jp.pmw.test_en_revolution.dialog;

import android.widget.RadioButton;

import jp.pmw.test_en_revolution.ManulReason;

/**
 * Created by si on 2016/12/27.
 * 理由ラジオボタンとDBから取得したデーターを対応させる.
 */

public class RegardedAsReasonRadioButton {
    public static final String AT00 = "AT00";
    public static final String AT01 = "AT01";
    public static final String AT02 = "AT02";
    public static final String AT03 = "AT03";
    public static final String LA01 = "LA01";
    public static final String LA02 = "LA02";
    public static final String LA03 = "LA03";
    public static final String AB01 = "AB01";
    public static final String AB02 = "AB02";
    public static final String AB03 = "AB03";
    public static final String LE01 = "LE01";
    public static final String LE02 = "LE02";
    public static final String LE03 = "LE03";
    public RadioButton mRadioButton;
    public String mReasonId;
    public ManulReason mManulReason;
    RegardedAsReasonRadioButton(
            String reasonId,
            RadioButton radioButton
    ){
        this.mRadioButton = radioButton;
        this.mReasonId = reasonId;
    }

}
