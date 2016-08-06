package jp.pmw.test_en_revolution;

import android.app.AlertDialog;
import android.os.Bundle;
import android.widget.Toast;

import jp.pmw.test_en_revolution.common.CommonDialogFragment;

/**
 * Created by si on 2016/02/02.
 */
public class TransmitChecker {

    //  [秒]前まで送信受付可能
    public final static long reamingUndoTime = 12;

    public final static String STR_JUST_A_MINITS = "しばらくお待ちください";

    public final static String STR_DO_NOT_END_ATTEND ="出席認定が終わるまで、しばらくお待ちください";

    public final static String STR_TRANSMITTING = "送信中のためしばらくお待ちください.";

    public final static String STR_END_RE_ATTEND = "在室確認は終了しています.";

    public final static String STR_END_TRANMIT = "赤外線送信受付を終了しました.";

    public final static String STR_CLOSE        = "とじる";

    private MainActivity activity;
    public TransmitChecker(MainActivity activity){
        this.activity = activity;
    }

    /**
     * 送信可能かを調べる
     * @return false    :   送信中のため、送信NG
     *          true    :   送信していないので、送信OK
     *
     * */
    public boolean chkTransmitPossible(){
        TransmitStateObject tso = activity.getClassObject().getTransmitStateObject();
        if(tso == null){
            pleaseWait();
            return false;
        }

        if(tso.getAttendanceTranmitEndTime() == null){
            notEndAttendance();
            return false;
        }

        if(tso.getBmpTransmitId() != null){
            pleaseWaitTransmit();
            return false;
        }
        //  送信受付終了
        if(tso.getUndoTranmitStartReamingTime() < reamingUndoTime || tso.getUndoTransmitEndTime() != null){
            transmitEnd();
            return false;
        }

        return true;
    }

    private void pleaseWait(){
        //Toast.makeText(activity,"しばらくお待ちください.", Toast.LENGTH_SHORT).show();

        showAlertDialogSendState(STR_JUST_A_MINITS);
    }

    private void notEndAttendance(){
        //String msg = "出欠確認が終わるまで、しばらくお待ちください";
        showAlertDialogSendState(STR_DO_NOT_END_ATTEND);
    }

    private void pleaseWaitTransmit(){
        //String msg = "送信中のためしばらくお待ちください.";
        //Toast.makeText(activity,"送信中のためしばらくお待ちください.", Toast.LENGTH_SHORT).show();
        showAlertDialogSendState(STR_TRANSMITTING);
    }

    private void transmitEnd(){
        //String msg = "赤外線送信受付を終了しました.";
        //Toast.makeText(activity,"送信中のためしばらくお待ちください.", Toast.LENGTH_SHORT).show();
        showAlertDialogSendState(STR_END_TRANMIT);
    }

    public void showAlertDialogSendState(String msg) {
        new AlertDialog.Builder(activity)
                .setTitle(" ")
                .setMessage(msg)
                .setPositiveButton(STR_CLOSE, null)
                .show();
    }

}
