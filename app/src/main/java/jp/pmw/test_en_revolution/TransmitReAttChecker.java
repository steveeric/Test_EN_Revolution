package jp.pmw.test_en_revolution;

import android.app.AlertDialog;

/**
 * Created by si on 2016/02/04.
 */
public class TransmitReAttChecker {

    private MainActivity activity;
    public TransmitReAttChecker(MainActivity activity){
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

        if(tso.getReAttendanceEndTime() != null){
            //在室確認はすでに行いました.
            endReAttendance();
            return false;
        }

        //  送信受付終了
        if(tso.getUndoTranmitStartReamingTime() < TransmitChecker.reamingUndoTime || tso.getUndoTransmitEndTime() != null){
            transmitEnd();
            return false;
        }

        return true;
    }

    private void pleaseWait(){
        showAlertDialogSendState(TransmitChecker.STR_JUST_A_MINITS);
    }

    private void notEndAttendance(){
        showAlertDialogSendState(TransmitChecker.STR_DO_NOT_END_ATTEND);
    }

    private void pleaseWaitTransmit(){
        showAlertDialogSendState(TransmitChecker.STR_TRANSMITTING);
    }

    private void endReAttendance(){
        showAlertDialogSendState(TransmitChecker.STR_END_RE_ATTEND);}


    private void transmitEnd(){
        showAlertDialogSendState(TransmitChecker.STR_END_TRANMIT);
    }

    public void showAlertDialogSendState(String msg) {
        new AlertDialog.Builder(activity)
                .setTitle(" ")
                .setMessage(msg)
                .setPositiveButton(TransmitChecker.STR_CLOSE, null)
                .show();
    }

}
