package jp.pmw.test_en_revolution;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Timer;

import jp.pmw.test_en_revolution.config.URL;
import jp.pmw.test_en_revolution.confirm_class_plan.RoomInfoObject;
import jp.pmw.test_en_revolution.group_readjustment.ReAdjustmentOjbect;
import jp.pmw.test_en_revolution.questionnaire.Questionnaire;
import jp.pmw.test_en_revolution.room.Room;
import jp.pmw.test_en_revolution.survey.Survey;
import jp.pmw.test_en_revolution.survey.SurveyState;
import okhttp3.Request;

/**
 * Created by si on 2016/01/29.
 * 授業オブジェクトに関するネットワーク取得クラス
 */
public class ClassHttpRequest {
    //  授業残り時間[msec]を取得タイプ
    private static final int DO_TYPE_CLASS_REAMING_TIME = 1;
    //  送信状態を取得タイプ
    private static final int DO_TYPE_TRANMIT_STATE = 2;
    //  授業参加予定学生と出欠状態を取得タイプ
    private static final int DO_TYPE_CHK_ATTENDANCE = 3;
    //  クリッカー(アンケート)取得タイプ
    private static final int DO_TYPE_QUESTIONNAIRE = 4;
    //  出席関係の情報のみ取得タイプ
    private static final int DO_TYPE_ONLY_ATTENDANCE_RERATIONSHIP = 5;
    //  在室確認(再出席調査)取得タイプ
    private static final int DO_TYPE_RE_ATTENDANCE = 6;
    //  教室のフロアマップ取得タイプ
    private static final int DO_TYPE_ROOM_FLOA_MAP = 7;
    //  グループ調整に関するデータ取得タイプ
    private static final int DO_TYPE_GROUP_ADJUSTMENT = 8;
    //  アンケートに関するデータ取得タイプ
    private static final int DO_TYPE_STATE_SURVEY = 9;


    public MainActivity activity;
    public String sameClassNumber;
    public Gson gson;
    ClassHttpRequest(MainActivity activity){
        this.activity           = activity;
        this.sameClassNumber    = this.activity.getClassObject().getSameClassNumber();
        this.gson               = new Gson();
    }


    //abstract public void jsonObjectParser(JSONObject response);

    public void getClassReamingTimeFromNetWrokDB(){
        String url = URL.getClassReaminingTime(sameClassNumber);
        doOkHttp(DO_TYPE_CLASS_REAMING_TIME, url);
    }

    public void getChkAttendance(){
        String url = URL.getUrlChkAttendance(sameClassNumber);
        doOkHttp(DO_TYPE_CHK_ATTENDANCE, url);
    }

    public void getTranmitState(){
        String url = URL.getTransmitState(sameClassNumber);
        doOkHttp(DO_TYPE_TRANMIT_STATE, url);
    }

    public void getQuestionnaire(){
        String url = URL.getQuestionnaireInfo(sameClassNumber);
        doOkHttp(DO_TYPE_QUESTIONNAIRE, url);
    }
    //  アンケートの状態を取得します.
    public void getSurvey(){
        String url = URL.getUrlSurveyState( sameClassNumber );
        doOkHttp(DO_TYPE_STATE_SURVEY, url);
    }

    public void getAttendanceRelationShipInto(){
        String url = URL.getAttendanceRelationShipInto(sameClassNumber);
        doOkHttp(DO_TYPE_ONLY_ATTENDANCE_RERATIONSHIP, url);
    }

    public void getReAttendanceStart(){
        String url = URL.getReAttendanceStart(sameClassNumber);
        doOkHttp(DO_TYPE_RE_ATTENDANCE, url);
    }

    public void getRoomFloaMapInfo(String roomId){
        String url = URL.getRoomFloaMapInfo(roomId);
        doOkHttp(DO_TYPE_ROOM_FLOA_MAP, url);
    }
    //  グループ調整状況
    public void getGroupAdjustmentStatus(){
        String url = URL.getGroupAdjustmentStatus(sameClassNumber);
        doOkHttp(DO_TYPE_GROUP_ADJUSTMENT, url);
    }
    //  グループ調整開始
    public void startGroupAdjustment(){
        String url = URL.getStartGrupAdjustment(sameClassNumber);
        doOkHttp(DO_TYPE_GROUP_ADJUSTMENT, url);
    }
    //  グループ調整実施後に、androidタブレットの表示を見て伝達する
    public void manulaContacted(String seatAfterMovingId){
        String url = URL.getManualContacted(seatAfterMovingId, sameClassNumber);
        doOkHttp(DO_TYPE_GROUP_ADJUSTMENT, url);
    }

    /**
     * Created by si on 2016/01/29.
     * doVolleyメソッド
     * ①ネットワークアクセスを行います.
     * ②doTypeによりVolley実行後の処理が変わります.
     * @param doType    処理タイプ
     * @param url       URL
     * **/
    /*public void doVolley(String url){
        JsonObjectRequest request = new JsonObjectRequest(url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        jsonObjectParser(response);
                    }
                }, new Response.ErrorListener() {
            @Override public void onErrorResponse(VolleyError error) {
               activity.showErrorToast(error.getMessage());
            }
        });
        AppController.getInstance(activity).getRequestQueue().add(request);
    }*/

    /*public void doOkHttp(final String url){
        new MyAsyncTask() {
            @Override
            protected String doInBackground(Void... params) {
                String res = null;
                try {
                    String result = run(url);
                    JSONObject response = new JSONObject(result);
                    jsonObjectParser(response);
                } catch(IOException e) {
                    activity.showErrorToast(e.toString());
                } catch(JSONException e) {
                    activity.showErrorToast(e.toString());
                }
                return res;
            }
        }.execute();
    }*/
    public void doOkHttp(final int type, final String url){
        new MyAsyncTask() {
            @Override
            protected String doInBackground(Void... params) {
                String res = null;
                try {
                    String result = run(url);
                    JSONObject response = new JSONObject(result);
                    jsonObjectParser(type, response);
                } catch(IOException e) {
                    activity.showAnotherErrorToast(e.toString());
                } catch(JSONException e) {
                    activity.showAnotherErrorToast(e.toString());
                }
                return res;
            }
        }.execute();
    }

    // OKHttpを使った通信処理
    public String run(String url) throws IOException {
        Request request = new Request.Builder()
                    .url(url)
                    .build();
        okhttp3.Response response = activity.getOkHttpClient().newCall(request).execute();
        return response.body().string();
    }

    /**
     * Created by si on 2016/01/29.
     * jsonObjectParserメソッド
     * 処理タイプに応じてJSON解析を行います.
     *  @param doType       処理タイプ
     *  @param jsonObject   JSONオブジェクト
     * **/
    public void jsonObjectParser(int doType, JSONObject jsonObject){
        switch (doType){
            case DO_TYPE_CLASS_REAMING_TIME:
                ClassReamingTime crTime =  gson.fromJson(jsonObject.toString(), ClassReamingTime.class);
                setClassReamingTime(crTime);
                break;
            case DO_TYPE_TRANMIT_STATE:
                TransmitStateObject tso =  gson.fromJson(jsonObject.toString(), TransmitStateObject.class);
                setTransmitState(tso);
                break;
            case DO_TYPE_CHK_ATTENDANCE:
                RegistrationObject ro =  gson.fromJson(jsonObject.toString(), RegistrationObject.class);
                doChkAttendance(ro);
                break;
            case DO_TYPE_QUESTIONNAIRE:
                Questionnaire questionnaire = gson.fromJson(jsonObject.toString(), Questionnaire.class);
                setQuestionnaire(questionnaire);
                break;
            case DO_TYPE_ONLY_ATTENDANCE_RERATIONSHIP:
                AttendanceReationObject aros = gson.fromJson(jsonObject.toString(), AttendanceReationObject.class);
                setAttendanceReationObject(aros);
                break;
            case DO_TYPE_RE_ATTENDANCE:
                TransmitStateObject afterReAttendance =  gson.fromJson(jsonObject.toString(), TransmitStateObject.class);
                setTransmitState(afterReAttendance);
                break;
            case DO_TYPE_ROOM_FLOA_MAP:
                RoomInfoObject room = gson.fromJson(jsonObject.toString(), RoomInfoObject.class);
                setRoomFloaMap(room);
                break;
            case DO_TYPE_GROUP_ADJUSTMENT:
                GroupReAdjustment groupReAdjustmentOjbect = gson.fromJson(jsonObject.toString(), GroupReAdjustment.class);
                ReAdjustmentOjbect reAdjustmentOjbect = groupReAdjustmentOjbect.rao;
                setGroupReAdjustment(reAdjustmentOjbect);
                break;
            case DO_TYPE_STATE_SURVEY:
                SurveyState surveyState = gson.fromJson(jsonObject.toString(), SurveyState.class);
                Survey survey = surveyState.mSurvey;
                setSurvyState( survey );
                break;
        }
    }

    private void setClassReamingTime(ClassReamingTime crTime){
        activity.getClassObject().setClassTimeReaming(crTime.reamingTime);
        //  授業残り時間[msec]
        long classReamingTime = activity.getClassObject().getClassTimeReaming();

        if(activity.endClassRoomTimer == null){
            //授業終了タイマー
            activity.endClassRoomTimer = new Timer();
        }else{
            activity.endClassRoomTimer.cancel();
            activity.endClassRoomTimer = null;
            activity.endClassRoomTimer = new Timer();
        }
        //授業終了タイマー
        activity.endClassRoomTimer.schedule(new FinishTimer(activity), classReamingTime);
        if(classReamingTime > 0){
            //  まだ授業中であれば
            //送信状態を取得する...
            getTranmitState();
        }else{
            //  授業終わりましたよ!
        }
    }

    private void setTransmitState(TransmitStateObject tso){
        TransmitStateObject beforeTso = this.activity.getClassObject().getTransmitStateObject();
        if(beforeTso == null){
            this.activity.getClassObject().setTransmitStateObject(tso);
        }else{
            beforeTso.setObject(beforeTso);
        }
    }

    /*
* 学生情報と出欠データ取得で使用クラス
* */
    class RegistrationObject{
        //
        @SerializedName("students")
        public StudentObject[] sos;
        @SerializedName("number_of_attendance")
        public NumOfAttendanceEntity numOfAttendanceEntity;
    }

    /**
     * Created by si on 2016/01/29.
     * doChkAttendanceメソッド
     * 授業参加学生群を授業オブジェクトにセットします.
     *  @param students       学生群
     * **/
    public void doChkAttendance(RegistrationObject ro){
        activity.getClassObject().setNumOfAttendanceEnttity(ro.numOfAttendanceEntity);
        activity.getClassObject().setStudentObject(ro.sos);
    }

    class AttendanceReationObject{
        //
        @SerializedName("attendance_relation")
        public AttendanceObject[] aos;
        @SerializedName("number_of_attendance")
        public NumOfAttendanceEntity numOfAttendanceEntity;
    }
    /**
     * Created by si on 2016/02/02.
     * setAttendanceReationObjectメソッド
     * 出席関係情報群を学生群オブジェクトにセットします.
     *  @param students       学生群
     * **/
    public void setAttendanceReationObject(AttendanceReationObject aro){
        StudentObject[] sos = this.activity.getClassObject().getStudentObject();
        //  授業参加者の時刻が入っております.
        AttendanceObject[] newAos = aro.aos;
        //  出席・出席(忘れ)・遅刻・遅刻(忘れ)・欠席者数をセットする.
        activity.getClassObject().setNumOfAttendanceEnttity(aro.numOfAttendanceEntity);
        if(sos == null){
            //  学生情報がない場合は何もしない
            return;
        }

        for(int i = 0; i < sos.length; i++){
            //  学生情報
            StudentObject so          = sos[i];
            //  以前の出席情報
            AttendanceObject beforeAo = so.getAttendanceObject();
            String beforeAttendanceId = beforeAo.getAttendanceId();
            for(int j = 0; j < newAos.length; j++){
                AttendanceObject newAo = newAos[j];
                String newAttendanceId = newAo.getAttendanceId();
                if(beforeAttendanceId.equals(newAttendanceId)) {
                    //同一出席IDの場合は,
                    //新しい出席情報をセットする.
                    so.getAttendanceObject().setAttendanceObject(newAo);
                }
            }
        }
        //  出席関係取得中フラグ(false:取得中でない, true:取得中)
        this.activity.chkAttendanceRelationshipInfoRetrieving = false;
    }
    /*public void chkTrxTranmitState(){
        activity.chkTrxTranmitState();
    }*/


    /**
     * Created by scr on 2015/3/4.
     * setQuestionnaireメソッド
     * アンケート情報をセットする.
     */
    private void setQuestionnaire(Questionnaire questionnaire){
        this.activity.getClassObject().setQuestionnaire(questionnaire);
    }

    /**
     * Created by scr on 2015/3/4.
     * setRoomFloaMapメソッド
     * 教室情報をセットする.
     */
    private void setRoomFloaMap(RoomInfoObject roomInfoObject){
        this.activity.getClassObject().setRoomInfoObject(roomInfoObject);
    }

    /**
     * Created by scr on 2015/5/11.
     * setGroupReAdjustmentメソッド
     * グループ調整情報をセットする.
     */
    private void setGroupReAdjustment(ReAdjustmentOjbect rao){
        this.activity.getClassObject().setReAdjustment(rao);
    }
    class GroupReAdjustment{
        @SerializedName("re_adjustment")
        public ReAdjustmentOjbect rao;
    }
    /**
     * setSurvyStateメソッド
     * 現在のアンケート状態を取得します.
     * @author Ito Shota
     * @since  2016/08/17
     **/
    private void setSurvyState(jp.pmw.test_en_revolution.survey.Survey survey){
        this.activity.getClassObject().setSurvey( survey );
    }
}
