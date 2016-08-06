package jp.pmw.test_en_revolution;

/**
 * Created by si on 2016/02/02.
 * 強制的にandroid端末内のメモリは、
 * 何かを送信中にしてしまうクラス.
 */
public class ForciblyTransmit {
    public ForciblyTransmit(){

    }
    /**
     * Created by scr on 2016/2/2.
     * forciblyTranmitBmpTransmitIdメソッド
     * 強制的に送信中にする.
     * (送信状態を管理するオブジェクトが、
     * 10秒に一回しか起動しないので、
     * 一時強制的に送信状態にし、
     * 次の送信をすぐに行えないようにする.)
     */
    public void forciblyTranmitBmpTransmitId(MainActivity activity){
        activity.getClassObject().getTransmitStateObject().setBmpTransmitId("");
    }

}
