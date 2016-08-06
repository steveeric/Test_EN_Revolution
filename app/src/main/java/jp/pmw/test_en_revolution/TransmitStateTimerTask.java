package jp.pmw.test_en_revolution;


import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.util.List;
import java.util.TimerTask;
import jp.pmw.test_en_revolution.config.URL;
import jp.pmw.test_en_revolution.questionnaire.Question;
import jp.pmw.test_en_revolution.questionnaire.QuestionTransmitState;
import jp.pmw.test_en_revolution.questionnaire.Questionnaire;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by si on 2016/01/28.
 */
public class TransmitStateTimerTask extends TimerTask{
    private MainActivity activity;
    //  授業識別番号
    private String sameClassNumber = null;
    //  送信状況を管理するオブジェクト
    private TransmitStateObject tso = null;

    /*コンストラクタ*/
    TransmitStateTimerTask(MainActivity activity){
        this.activity = activity;
        //this.sameClassNumber    = activity.mTeacher.getClassPlan().getSameClassNumber();
        //this.tso                = activity.mTeacher.getClassPlan().getTransmitStateObject();
        this.sameClassNumber      = activity.getClassObject().getSameClassNumber();
        //this.tso                  = activity.getClassObject().getTransmitStateObject();
    }


    public void run(){
        chkTransmitState();
    }

    /**
     * chkTransmitStateメソッド
     * 現在の赤外線送信状態を、DBに確認するメソッドです.
     */
    /*private void chkTransmitState(){
        String url = URL.getTransmitState(sameClassNumber);
        JsonObjectRequest request = new JsonObjectRequest(url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        applyTransmitStateChange(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(activity, "Unable to fetch data: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        AppController.getInstance(activity).getRequestQueue().add(request);
    }*/

    private void chkTransmitState(){
        new MyAsyncTask() {
            @Override
            protected String doInBackground(Void... params) {
                String res = null;
                try {
                    String url = URL.getTransmitState(sameClassNumber);
                    String result = run(url);
                    JSONObject response = new JSONObject(result);
                    applyTransmitStateChange(response);
                } catch(IOException e) {
                    //activity.showErrorToast(e.toString());
                } catch(JSONException e) {
                    //activity.showErrorToast(e.toString());
                }
                return res;
            }
        }.execute();
    }


    /*public void chkTransmitState(){
        try {
            okTransmitState();
        } catch (IOException e) {
            activity.showErrorToast(e.toString());
        }
        //doOkHttp();
    }*/

    /*public void doOkHttp(){
        new MyAsyncTask() {
            @Override
            protected String doInBackground(Void... params) {
                String res = null;
                String url = URL.getTransmitState(sameClassNumber);
                try {
                    String result = run(url);
                    JSONObject response = new JSONObject(result);
                    //jsonObjectParser(type, response);
                    applyTransmitStateChange(response);
                } catch(IOException e) {
                    activity.showErrorToast(e.toString());
                } catch(JSONException e) {
                    activity.showErrorToast(e.toString());
                }
                return res;
            }
        }.execute();
    }*/

    // OKHttpを使った通信処理
    public String run(String url) throws IOException {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        okhttp3.Response response = okHttpClient.newCall(request).execute();
        return response.body().string();
    }


    /*public void okTransmitState() throws IOException {
        String url = URL.getTransmitState(sameClassNumber);
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        OkHttpClient client = new OkHttpClient();

        Response response = client.newCall(request).execute();
        String json = response.body().string();
        //JSONObject response = new JSONObject(json);
        applyTransmitStateChange(json);
    }*/
    /**
     * applyTransmitStateChangeメソッド
     * 送信状態に変更があればandroid端末の
     * 送信状態オブジェクト(TranmitStateObject)に
     * 反映させる.
     */
    private void applyTransmitStateChange(JSONObject response/*String json*/){
        Gson gson = new Gson();
        TransmitStateObject newTransmitStateObject = gson.fromJson(response.toString(), TransmitStateObject.class);
        if (newTransmitStateObject != null
                && activity.getClassObject().getTransmitStateObject() != null){
            //  画面を再描画する必要があるかを調べる.
            chkScreenReflash(newTransmitStateObject);
        }else{
            //  送信状態オブジェクトは、生成されていないので
            //  初回取得したデータを授業オブジェクト(ClassObject)にセットする.
            activity.getClassObject().setTransmitStateObject(newTransmitStateObject);
        }

        if(newTransmitStateObject.getQuestionTransmitState() != null){
            setQuestionTransmitState(newTransmitStateObject.getQuestionTransmitState());
        }

    }

    private void setQuestionTransmitState(QuestionTransmitState[] newQTS){
        //  アンケート実施時刻が取得できた場合
        if(newQTS.length > 0){
            if(this.activity.getClassObject().getQuestionnaire()!=null){
                Questionnaire  q = this.activity.getClassObject().getQuestionnaire();
                //  問題が登録されていれば
                List<Question> questions = q.getQuestions();
                //QuestionTransmitState[] newQTS =  newTransmitStateObject.getQuestionTransmitState();
                for(int i = 0; i < questions.size(); i++){
                    String questionid = questions.get(i).getQuestionId();
                    for(int j = 0; j < newQTS.length; j++){
                        String newQuestionId = newQTS[j].getQuestionId();
                        if(questionid.equals(newQuestionId)){
                            //  送信状態をセットする.
                            questions.get(i).setQuestionTransmitState(newQTS[j]);
                        }
                    }
                }
            }
        }
    }
    /**
     * chkScreenReflashメソッド
     * 受講者一覧画面の再描画行うために、
     * MainAcitivytすべて再描画する必要があるかを調べる.
     */
    private void chkScreenReflash(TransmitStateObject newTransmitStateObject){
        //TransmitStateObject beforeTransmitStateObject = activity.mTeacher.getClassPlan().getTransmitStateObject();
        TransmitStateObject beforeTransmitStateObject = activity.getClassObject().getTransmitStateObject();
        activity.getClassObject().setTransmitStateObject(newTransmitStateObject);
        if(beforeTransmitStateObject == null){
            return;
        }

        if( beforeTransmitStateObject.getAttendanceTranmitEndTime() ==  null && newTransmitStateObject.getAttendanceTranmitEndTime() != null ) {
            //  出席調査終わり(そのだ教授)
            screenReflash();
        }else if( beforeTransmitStateObject.getReAttendanceEndTime() == null && newTransmitStateObject.getReAttendanceEndTime() != null ){
            //  再調査終わり
            screenReflash();
        }else if( beforeTransmitStateObject.getUndoTransmitEndTime() == null && newTransmitStateObject.getUndoTransmitEndTime() != null){
            //  かなえちゃん終わり
            screenReflash();
        }
    }
    /**
     * screenReflashメソッド
     * 画面再描画
     * */
    private void screenReflash(){
        //this.activity.getRoomInfoAndRosterFromNetWrokDB();

        //  受講者一覧再描画が必要
        if(this.activity.mNavigationDrawerItemSelected == MainFragmentConfig.PARTICIPANTS_FRAGNEMT){
            this.activity.reDrawAttendFlag = true;
        }
        //  出席者再取得
        //activity.getClassHttpRequest().getChkAttendance();
        //activity.showAnotherErrorToast("再描画でっせ！");
    }
}
