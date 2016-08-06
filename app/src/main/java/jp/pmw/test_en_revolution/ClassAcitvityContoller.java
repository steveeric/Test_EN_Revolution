package jp.pmw.test_en_revolution;

/**
 * Created by si on 2016/01/29.
 * 授業画面(MainAcitivty)が、
 * 表示すべきフラグメントをコントロールするクラス.
 */
public class ClassAcitvityContoller {
    private MainActivity activity;
    /**
     * コンストラクタ
     * **/
    ClassAcitvityContoller(MainActivity activity){
        this.activity = activity;
    }
    /**
     * Created by si on 2016/01/29.
     * startUpメソッド
     * MainActivityのonResume時に
     * どのような処理を行うべきかを選択します.
     */
    public void startUp(){
        activity.getClassHttpRequest().getClassReamingTimeFromNetWrokDB();
    }

    //  送信状態オブジェクトが取得でき次第,getClassDataへ行く
    /*private void waitNextProcessGetClassData(){
        boolean getTransmitStateOjbectEndFlag = false;
        while(!getTransmitStateOjbectEndFlag){
            TransmitStateObject tso =
            if(tso != null && sos != null){
                getTransmitStateOjbectEndFlag = true;
            }
        }
    }*/

    /**
     * Created by si on 2016/01/31.
     * getTranmitStateメソッド
     * 送信状態を取得します.
     */


    /**
     * Created by si on 2016/01/29.
     * getClassDataメソッド
     * 授業データを取得します.
     * ①    送信状態
     * ②    学生(授業出席状態)
     * ③    アンケート情報
     */
    public void getClassData(){
        //  ①
        //activity.chkTrxTranmitState();
        //  ②
        activity.getClassHttpRequest().getChkAttendance();
        //new ChkAttendanceHttpRequest(activity).getChkAttendance();
        //activity.getChkAttendanceHttpRequest().getChkAttendance();

        //  ③

        //waitNextProcess();
    }

    //  次の処理待ち...
    public void waitNextProcess(){
        //  getClassDataの終了フラグ
        boolean getClassDataEndFlag = false;
        while(!getClassDataEndFlag){
            TransmitStateObject tso = this.activity.getClassObject().getTransmitStateObject();
            StudentObject[]     sos = this.activity.getClassObject().getStudentObject();
            if(tso != null && sos != null){
                getClassDataEndFlag = true;
            }
            sleep();
        }
        //  教室情報取得.
        getRoomData();
        //  アンケート情報取得する.
        getQuestion();
    }
    private void sleep(){
        long l = 3000;
        try {
            Thread.sleep(l);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    /**
     * Created by si on 2016/01/29.
     * getRoomDataメソッド
     * 教室データを保持していない場合は、
     * 教室データを取得します.
     */
    public void getRoomData(){
        //TODO:教室情報取得すること！

    }
    /**
     * Created by si on 2016/01/29.
     * getQuestionメソッド
     * アンケート情報があれば取得する.
     */
    public void getQuestion(){
        //TODO:アンケート情報取得すること！
    }

}
