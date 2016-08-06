package jp.pmw.test_en_revolution;

/**
 * Created by si on 2016/01/29.
 * 授業設定オブジェクトクラス
 * ①    座席指定を行う
 * ②    クリッカーを行う
 *
 */
public class ClassConfigObject {
    //  座席指定モード
    private int reservedSeatMode    =   0;
    public int getReservedSeatMode(){return this.reservedSeatMode;}

    //  クリッカー実施モード
    private int doQuestionMode      =   0;
    public int getDoQuestionMode(){return this.doQuestionMode;}

}
