package jp.pmw.test_en_revolution.attendee;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import jp.pmw.test_en_revolution.network.MyIOException;

/**
 * Created by si on 2016/12/27.
 * 日時を比較するシングルトンクラスです.
 */
public class CompareTime {
    SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static CompareTime instance = null;
    public static CompareTime newInstance() {
        instance = new CompareTime();
        return instance;
    }
    /**
     * Created by si on 2016/12/27.
     * futureメソッド
     * @parmaa String target1 比較元
     * @parmaa String target2 比較データ
     * @return true:target2が未来、false:target2とtarget1同じ又は、target2が過去
     * **/
    public boolean future(String target1, String target2){
        // 日付を作成します。
        try {
            Date dateTarget1 = mSimpleDateFormat.parse( getDateTime(target1) );
            Date dateTarget2 = mSimpleDateFormat.parse( getDateTime(target2) );
            long dateTimeTarget1 = dateTarget1.getTime();
            long dateTimeTarget2 = dateTarget2.getTime();
            long diff = dateTimeTarget2 - dateTimeTarget1;
            if( diff > 0 ){
                return true;
            }else{
                return false;
            }
        } catch (ParseException e) {
            MyIOException.absorbException(e);
            return false;
        }
    }
    String getDateTime(String strDateTime){
        String[] dateTimeAndMilisec = strDateTime.split("\\.", 0);
        String dateTime = "";
        if(dateTimeAndMilisec != null || dateTimeAndMilisec.length > 1){
            dateTime = dateTimeAndMilisec[0];
        }else{
            dateTime = strDateTime;
        }
        return dateTime;
    }
}
