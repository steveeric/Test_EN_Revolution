package jp.pmw.test_en_revolution.questionnaire;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.util.TimerTask;

import jp.pmw.test_en_revolution.MyAsyncTask;
import jp.pmw.test_en_revolution.config.URL;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by si on 2016/02/06.
 * アンケートに関する送信で、
 * 最後に行っている又は行った
 * ACk情報とNPD_IDを
 * 保持するクラスです.
 */
public class LastQuestionBmpTransmitLogTimerTask extends TimerTask{

    private OkHttpClient client = new OkHttpClient();
    private QuestionnaireFragment qf;
    private String sameClassNumber;

    LastQuestionBmpTransmitLogTimerTask(QuestionnaireFragment qf){
        this.qf = qf;
        this.sameClassNumber = qf.getMainActivity().getClassObject().getSameClassNumber();
    }

    public void run(){
        doGetLastQuestionBmpTransmitLog();
    }


    /**
     * Created by si on 2016/02/06.
     * doGetLastQuestionBmpTransmitLogメソッド
     * ①ネットワークアクセスを行います.
     * ②doTypeによりVolley実行後の処理が変わります.
     * **/
    public void doGetLastQuestionBmpTransmitLog(){
        final String url = URL.getLastQuestionTransmitState(sameClassNumber);
        final LastQuestionBmpTransmitLogObject object = null;
        new MyAsyncTask() {
            @Override
            protected String doInBackground(Void... params) {
                String res = null;
                try {
                    String result = run(url);
                    JSONObject jsonObject = new JSONObject(result);
                    //  授業最終送信情報オブジェクト取得
                    Data data =  new Gson().fromJson(jsonObject.toString(), Data.class);
                    LastQuestionBmpTransmitLogObject lqbto = data.lqbtlo;
                    if(qf != null){
                        qf.setReDrowLastQuestionTransmitResultState(lqbto);
                    }
                } catch(IOException e) {
                    //qf.getMainActivity().showAnotherErrorToast(e.toString());
                } catch(JSONException e) {
                    //qf.getMainActivity().showAnotherErrorToast(e.toString());
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
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    //  最終アンケート送信情報を取得するためのクラスです.
    class Data {
        @SerializedName("last_question_bmp_transmit_log_object")
        public LastQuestionBmpTransmitLogObject lqbtlo;
    }



}
