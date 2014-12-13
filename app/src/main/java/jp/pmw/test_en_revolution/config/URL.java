package jp.pmw.test_en_revolution.config;

/**
 * Created by scr on 2014/12/07.
 */
public class URL {
    private static final String BASE_URL = "http://192.168.11.3";
    public static final String ALL_TEACHER = BASE_URL+"/test/catalunya/public/api/teacher";

    //教室情報を取得サイト
    public static final String ROOM_MAP = BASE_URL+"/test/catalunya/public/api/room_map";
    //出欠席の情報を取得サイト
    public static final String ATTENDANCE_INFO = BASE_URL+"/test/catalunya/public/api/attendance";
}
