package jp.pmw.test_en_revolution.config;

/**
 * Created by scr on 2015/02/24.
 */
public class Config {
    //まだ赤外線送信を行っていない
    public static final int DO_NOT_SEND_INFRARED = 0;
    //出席確認済み
    public static final int ALREADY_ATTENDANCE = 1;
    //ESL忘れによる出席
    public static final int FOGOT_ESL_APPLY = 2;
    //欠席(ACK得られなかった)
    public static final int ABSENT = 3;


    //今期授業終了
    public static final int END_NOW_SEMESTER_MODE = 0;
    //今授業中です...
    public static final int TODAY_NOW_CLASS_MODE = 1;
    //本日授業だけど、次の時限以降です
    public static final int TODAY_NEXT_CLASS_MODE = 2;
    //明日以降に授業があります.
    public static final int TOMMOROW_NEXT_CLASS_MODE = 3;

    //
    public static final String SEPARATOR = ":";
}
