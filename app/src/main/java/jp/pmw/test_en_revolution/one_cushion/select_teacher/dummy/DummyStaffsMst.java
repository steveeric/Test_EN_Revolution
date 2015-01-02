package jp.pmw.test_en_revolution.one_cushion.select_teacher.dummy;

/**
 * Created by scr on 2015/01/01.
 * 教職員のダミーテストデータを格納するクラス.
 */
public class DummyStaffsMst {
    public static final String CSV_FILE_NAME_STAFFS_MST = "STAFFS_MST.csv";

    public static String getTapKanaItem(int tapIndex){
        String[] items  = {"わ","ら","や","ま","は","な","た","さ","か","あ",
                "","り","","み","ひ","に","ち","し","き","い",
                "","る","ゆ","む","ふ","ぬ","つ","す","く","う",
                "","れ","","め","へ","ね","て","せ","け","え",
                "を","ろ","よ","も","ほ","の","と","そ","こ","お"};

        return items[tapIndex];
    }

}
