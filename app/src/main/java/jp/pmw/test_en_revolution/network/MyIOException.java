package jp.pmw.test_en_revolution.network;

import org.json.JSONException;

import java.io.IOException;

import timber.log.Timber;

/**
 * Created by si on 2016/05/31.
 */
public class MyIOException extends IOException {
    private static MyIOException instance = null;
    private static MyIOException getInstance() {
        if (instance == null) {
            instance = new MyIOException();
        }
        return instance;
    }

    private static void generateInstance(){
        if(instance == null){
            getInstance();
        }
    }

    public static void absorbException(Exception e){
        generateInstance();
        Timber.e(e.getMessage());
    }

    public static void absorbIOException(IOException e){
        generateInstance();
        Timber.e(e.getMessage());
    }

    public static void absorbJsonException(JSONException e){
        generateInstance();
        Timber.e(e.getMessage());
    }



}
