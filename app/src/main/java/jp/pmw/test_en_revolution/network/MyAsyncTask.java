package jp.pmw.test_en_revolution.network;

import android.os.AsyncTask;
import android.support.v4.app.Fragment;

import com.google.gson.Gson;

/**
 * Created by si on 2016/06/01.
 */
public class MyAsyncTask extends AsyncTask<Void, Void, String> {
    public Gson mGson = new Gson();
    // コンストラクタ
    public MyAsyncTask() {
        super();
    }

    @Override
    protected String doInBackground(Void... params) {
        return null;
    }
    /**
     *  chkAliveFragmentメソッド
     *  フラグメントが生存しているかを確認します.
     *  @param  Fragment    fragment    フラグメント
     *  @return {true}  生存している  {false} 生存していない
     * */
    public boolean chkAliveFragment( Fragment fragment ){
        if( fragment != null ){
            //  生存している
            return true;
        }
        //  生存していない
        return false;
    }
}
