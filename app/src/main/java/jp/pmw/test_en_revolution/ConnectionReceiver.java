package jp.pmw.test_en_revolution;

/**
 * Created by scr on 2015/03/09.
 */

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class ConnectionReceiver extends BroadcastReceiver {

    /*
     * AndroidManifest.xml
     *
     * <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
     *
     * <receiver android:name=".ConnectivityReceiver" >
     *     <intent-filter>
     *         <action android:name="android.net.conn.CONNECTIVITY_CHANGE" />
     *     </intent-filter>
     * </receiver>
     */
    private Observer mObserver;
    public ConnectionReceiver(Observer observer) {
        mObserver = observer;
    }
    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        if (info == null) {
            mObserver.onDisconnect();
        }else{
            mObserver.onConnect();
        }
    }

     interface Observer {
      void onConnect();
      void onDisconnect();
    }
}