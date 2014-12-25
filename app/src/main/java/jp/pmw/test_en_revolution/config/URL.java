package jp.pmw.test_en_revolution.config;

/**
 * Created by scr on 2014/12/07.
 */
public class URL {
    private static final String BASE_URL = "http://192.168.53.138";
    //private static final String BASE_URL = "http://192.168.51.181";
    public static final String ALL_TEACHER = BASE_URL+"/test/catalunya/public/api/teacher";

    //選択した頭文字から教員名リストを取得するサイト
    public static final String TEACHER_FAMILY_NAME_INITIAL = BASE_URL+"/test/catalunya/public/api/teacher_family_name_initial";
    //教室情報を取得サイト
    public static final String ROOM_MAP = BASE_URL+"/test/catalunya/public/api/room_map";
    //出欠席の情報を取得サイト
    public static final String ATTENDANCE_INFO = BASE_URL+"/test/catalunya/public/api/attendance";
    //出席者の情報を取得サイト
    public static final String ATTENDEE_LIST = BASE_URL+"/test/catalunya/public/api/attendee_list";

}
