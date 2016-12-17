package jp.pmw.test_en_revolution.attendee;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import jp.pmw.test_en_revolution.R;
import static jp.pmw.test_en_revolution.attendee.Indicator.ACK;
import static jp.pmw.test_en_revolution.attendee.Indicator.DEF;
import static jp.pmw.test_en_revolution.attendee.Indicator.NAC;
import static jp.pmw.test_en_revolution.attendee.Indicator.NMF;
import static jp.pmw.test_en_revolution.attendee.Indicator.TMF;

/**
 * Created by si on 2016/12/17.
 * ACKインジケーター
 */

public enum IndicatorEnum {
    DEFAULT_SIGNAL(DEF, R.drawable.signal_gray),
    NACK_SIGNAL(NAC, R.drawable.signal_red),
    ACK_SIGNAL(ACK, R.drawable.signal_green),
    TARGET_MANUAL_FOLLOW_SIGNAL(TMF, R.drawable.signal_target_manual_follow),
    NOT_TARGET_MANUAL_FOLLOW_SIGNAL(NMF, R.drawable.signal_not_target_manual_follow);

    private final String signal;
    private final int signalResorce;
    IndicatorEnum(final String signal, int signalResorce) {
        this.signal = signal;
        this.signalResorce = signalResorce;
    }
    public static Drawable getSignalDrawable(Context context, String signal){
        IndicatorEnum[] enums = IndicatorEnum.values();
        for (IndicatorEnum type : enums) {
            if ( type.signal.equals(signal) ) {
                return ContextCompat.getDrawable(context, type.signalResorce);
            }
        }
        return ContextCompat.getDrawable(context, R.color.black);
    }
}
