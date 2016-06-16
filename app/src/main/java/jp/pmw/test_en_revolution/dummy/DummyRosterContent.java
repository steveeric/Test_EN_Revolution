package jp.pmw.test_en_revolution.dummy;

import java.util.ArrayList;
import java.util.List;

import jp.pmw.test_en_revolution.confirm_class_plan.Roster;
import jp.pmw.test_en_revolution.confirm_class_plan.Student;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p/>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class DummyRosterContent {
    /**
     * An array of sample (dummy) items.
     */
    public static List<Student> ITEMS = new ArrayList<Student>();

    /*static {
        ThisClassTime thisTime = null;
        thisTime = new ThisClassTime("3060580000000805",new AttendanceState(0,null,""));
        addItem(new Student("A03002","3060581500013", "かとう","いっぺい","加藤","一平","加藤一平",thisTime,new TotalAttendance(10,3),null));
        thisTime = new ThisClassTime("3060580000000612",new AttendanceState(0,null,""));
        addItem(new Student("A03003","3060581500005", "こみや","たつや","込野","達也","込野達也",thisTime,new TotalAttendance(10,3),null));
        thisTime = new ThisClassTime("3060580000000217",new AttendanceState(0,"",null));
        addItem(new Student("A03012","3060581500009", "せと","しゅうへい","瀬戸","修平","瀬戸修平",thisTime,new TotalAttendance(10,3),new Message("教務課より","履修申告状況について聞きたいので、教務課に来て下さい.","履修申告期間が過ぎていますがどうしますか?")));
        thisTime = new ThisClassTime("3060580000000415",new AttendanceState(0,null,""));
        addItem(new Student("A03020","3060581500013", "ひび","かずき","日比","一世","日比一世",thisTime,new TotalAttendance(8,5),null));
        thisTime = new ThisClassTime("3060580000000608",new AttendanceState(0,null,""));
        addItem(new Student("A03021","3060581500013", "ひびの","かずき","日比野","一樹","日比野一樹",thisTime,new TotalAttendance(8,5),null));
        thisTime = new ThisClassTime("3060580000000608",new AttendanceState(0,null,""));
        addItem(new Student("A03022","3060581500009", "ふじわら","ひでと","藤原","秀斗","藤原秀斗",thisTime,new TotalAttendance(9,4),null));
        thisTime = new ThisClassTime("3060580000000408",new AttendanceState(0,null,""));
        addItem(new Student("A03072","3060581500013", "むさしの","まさなり","武蔵野","正成","武蔵野正成",thisTime,new TotalAttendance(12,1),null));
        thisTime = new ThisClassTime("3060580000000615",new AttendanceState(0,null,""));
        addItem(new Student("A03073","3060581500009", "むとう","たつや","武藤","圭祐","武藤圭祐",thisTime,new TotalAttendance(13,0),new Message("学生課より","保護者より問い合わせがありました.学生課に来てください.","保護者よりと合わせがありましたので、本人に学校に来ている状況を聞き保護者に連絡して下さい.")));
        thisTime = new ThisClassTime("3060580000001405",new AttendanceState(0,null,""));
        addItem(new Student("B06001","3060581500013","あおやま","ゆうき","青山","祐城","青山祐城",thisTime,new TotalAttendance(7,6),null));
        thisTime = new ThisClassTime("3060580000001408",new AttendanceState(0,null,""));
        addItem(new Student("B06003","3060581500013","あだち","まさひろ","足立","将大","足立将大",thisTime,new TotalAttendance(10,3),null));
        thisTime = new ThisClassTime("3060580000001410",new AttendanceState(0,null,""));
        addItem(new Student("B06005","3060581500013","あまの","ゆうた","天野","雄太","天野雄太",thisTime,new TotalAttendance(9,4),null));
        thisTime = new ThisClassTime("3060580000001412",new AttendanceState(0,null,""));
        addItem(new Student("B06007","3060581500013","いいだ","ひでのぶ","飯田","英伸","飯田英伸",thisTime,new TotalAttendance(1,12),null));
        thisTime = new ThisClassTime("3060580000001415",new AttendanceState(0,null,""));
        addItem(new Student("B06008","3060581500013","いけの","ひろゆき","池野","浩之","池野浩之",thisTime,new TotalAttendance(13,0),null));
        thisTime = new ThisClassTime("3060580000001417",new AttendanceState(0,null,""));
        addItem(new Student("B06009","3060581500013","いそべ","あきら","磯部","輝","磯部輝",thisTime,new TotalAttendance(10,3),null));
        thisTime = new ThisClassTime("3060580000001603",new AttendanceState(0,null,""));
        addItem(new Student("B06011","3060581500013","いとう","としき","伊藤","寿紀","伊藤寿紀",thisTime,new TotalAttendance(13,0),null));
        thisTime = new ThisClassTime("3060580000001605",new AttendanceState(0,null,""));
        addItem(new Student("B06012","3060581500013","いとう","ともひろ","伊藤","知大","伊藤知大",thisTime,new TotalAttendance(8,5),null));
        thisTime = new ThisClassTime("3060580000001608",new AttendanceState(0,null,""));
        addItem(new Student("B06013","3060581500013","いとう","なおや","伊藤","直哉","伊藤直哉",thisTime,new TotalAttendance(11,2),null));
        thisTime = new ThisClassTime("3060580000001610",new AttendanceState(0,null,""));
        addItem(new Student("B06014","3060581500013","いなもと","かつき","稲本","勝己","稲本勝己",thisTime,new TotalAttendance(10,3),null));
        thisTime = new ThisClassTime("3060580000001612",new AttendanceState(0,null,null));
        addItem(new Student("B06015","3060581500013","いのしま","なお","猪島","尚","猪島尚",thisTime,new TotalAttendance(5,8),null));
        thisTime = new ThisClassTime("3060580000001615",new AttendanceState(0,null,""));
        addItem(new Student("B06016","3060581500013","いわさか","けんご","岩坂","謙吾","岩坂謙吾",thisTime,new TotalAttendance(10,3),null));
        thisTime = new ThisClassTime("3060580000001617",new AttendanceState(0,null,""));
        addItem(new Student("B06019","3060581500013","おおしお","しゅんや","大塩","峻矢","大塩峻矢",thisTime,new TotalAttendance(11,2),null));
        thisTime = new ThisClassTime("3060580000001803",new AttendanceState(0,null,""));
        addItem(new Student("B06020","3060581500013","おおしみず","まさと","大清水","真人","大清水真人",thisTime,new TotalAttendance(10,3),null));
        thisTime = new ThisClassTime("3060580000001805",new AttendanceState(0,null,""));
        addItem(new Student("B06021","3060581500013","おおにし","あきのり","大西","晃誠","大西晃誠",thisTime,new TotalAttendance(13,0),null));
        thisTime = new ThisClassTime("3060580000001808",new AttendanceState(0,null,""));
        addItem(new Student("B06023","3060581500013","おおむら","しゅうじろう","大村","宗士朗","大村宗士朗",thisTime,new TotalAttendance(6,7),null));
        thisTime = new ThisClassTime("3060580000001810",new AttendanceState(0,null,""));
        addItem(new Student("B06024","3060581500013","おおわ","こうへい","大輪","幸平","大輪幸平",thisTime,new TotalAttendance(10,3),null));
        thisTime = new ThisClassTime("3060580000001812",new AttendanceState(0,null,""));
        addItem(new Student("B06026","3060581500013","おがわ","まさし","小川","将司","小川将司",thisTime,new TotalAttendance(7,6),null));
        thisTime = new ThisClassTime("3060580000001815",new AttendanceState(0,null,""));
        addItem(new Student("B06027","3060581500013","おのでら","ひろと","小野寺","啓人","小野寺啓人",thisTime,new TotalAttendance(10,3),null));
        thisTime = new ThisClassTime("3060580000001817",new AttendanceState(0,null,""));
        addItem(new Student("B06029","3060581500013","かとう","あきひろ","加藤","章広","加藤章広",thisTime,new TotalAttendance(10,3),null));
        thisTime = new ThisClassTime("3060580000002001",new AttendanceState(0,null,""));
        addItem(new Student("B06030","3060581500013","かとう","さとみ","加藤","里美","加藤里美",thisTime,new TotalAttendance(5,8),null));
        thisTime = new ThisClassTime("3060580000002003",new AttendanceState(0,null,""));
        addItem(new Student("B06031","3060581500013","かとう","ともゆき","加藤","智之","加藤智之",thisTime,new TotalAttendance(10,3),null));
        thisTime = new ThisClassTime("3060580000002005",new AttendanceState(0,null,""));
        addItem(new Student("B06032","3060581500013","かとう","ひろあき","加藤","宏明","加藤宏明",thisTime,new TotalAttendance(4,9),null));
        thisTime = new ThisClassTime("3060580000002008",new AttendanceState(0,null,""));
        addItem(new Student("B06033","3060581500013","かとう","ひろのり","加藤","洋範","加藤洋範",thisTime,new TotalAttendance(11,2),null));
        thisTime = new ThisClassTime("3060580000002010",new AttendanceState(0,null,""));
        addItem(new Student("B06035","3060581500013","かねまつ","たかあき","兼松","貴明","兼松貴明",thisTime,new TotalAttendance(10,3),null));
        thisTime = new ThisClassTime("3060580000002012",new AttendanceState(0,null,""));
        addItem(new Student("B06036","3060581500013","かわい","まこと","川合","諒","川合諒",thisTime,new TotalAttendance(12,1),null));
        thisTime = new ThisClassTime("3060580000002015",new AttendanceState(0,null,""));
        addItem(new Student("B06037","3060581500013","きたがわ","ひろき","北川","裕貴","北川裕貴",thisTime,new TotalAttendance(13,0),null));
        thisTime = new ThisClassTime("3060580000002017",new AttendanceState(0,null,""));
        addItem(new Student("B06038","3060581500013","きたみ","あきら","喜多見","光","喜多見光",thisTime,new TotalAttendance(8,5),null));
        thisTime = new ThisClassTime("3060580000002019",new AttendanceState(0,null,""));
        addItem(new Student("B06039","3060581500013","きとう","かずや","鬼頭","和也","鬼頭和也",thisTime,new TotalAttendance(7,6),null));
        thisTime = new ThisClassTime("3060580000002201",new AttendanceState(0,null,""));
        addItem(new Student("B06040","3060581500013","きりがや","さとる","桐ケ谷","慧","桐ケ谷慧",thisTime,new TotalAttendance(7,6),null));
        thisTime = new ThisClassTime("3060580000002203",new AttendanceState(0,null,""));
        addItem(new Student("B06041","3060581500013","くろさわ","まさと","黒澤","真人","黒澤真人",thisTime,new TotalAttendance(3,10),null));
        thisTime = new ThisClassTime("3060580000001008",new AttendanceState(0,null,""));
        addItem(new Student("B07001","3060581500013","あおい","たいき","青井","大輝","青井大輝",thisTime,new TotalAttendance(6,7),null));
        thisTime = new ThisClassTime("3060580000001010",new AttendanceState(0,null,""));
        addItem(new Student("B07033","3060581500013","おかじま","ゆうだい","岡島","侑大","岡島侑大",thisTime,new TotalAttendance(10,3),null));
        thisTime = new ThisClassTime("3060580000001012",new AttendanceState(0,null,""));
        addItem(new Student("B07035","3060581500013","おおざき","まさや","尾崎","誠也","尾崎誠也",thisTime,new TotalAttendance(1,12),null));
        thisTime = new ThisClassTime("3060580000001015",new AttendanceState(0,null,""));
        addItem(new Student("B07041","3060581500013","かとう","よしたか","加藤","義隆","加藤義隆",thisTime,new TotalAttendance(10,3),null));
        thisTime = new ThisClassTime("3060580000001018",new AttendanceState(0,null,""));
        addItem(new Student("B07060","3060581500013","こじま","けんた","小嶋","健太","小嶋健太",thisTime,new TotalAttendance(13,0),null));
        thisTime = new ThisClassTime("3060580000001203",new AttendanceState(0,null,""));
        addItem(new Student("B07073","3060581500013","すずき","よしのり","鈴木","良則","鈴木良則",thisTime,new TotalAttendance(11,2),null));
        thisTime = new ThisClassTime("3060580000001205",new AttendanceState(0,null,""));
        addItem(new Student("B07087","3060581500013","つばい","たかのぶ","津梅","孝信","津梅孝信",thisTime,new TotalAttendance(10,3),null));
        thisTime = new ThisClassTime("3060580000001208",new AttendanceState(0,null,""));
        addItem(new Student("B07093","3060581500013","なかむら","たいち","中村","太一","中村太一",thisTime,new TotalAttendance(13,0),null));
        thisTime = new ThisClassTime("3060580000001210",new AttendanceState(0,null,""));
        addItem(new Student("B07094","3060581500013","なかむら","つよし","中村","幹","中村幹",thisTime,new TotalAttendance(1,12),null));
        thisTime = new ThisClassTime("3060580000001212",new AttendanceState(0,null,""));
        addItem(new Student("B07100","3060581500013","にしむら","たくや","西村","拓也","西村拓也",thisTime,new TotalAttendance(8,5),null));
        thisTime = new ThisClassTime("3060580000001215",new AttendanceState(0,null,""));
        addItem(new Student("B07110","3060581500013","はやし","としひろ","林","俊博","林俊博",thisTime,new TotalAttendance(10,3),null));
        thisTime = new ThisClassTime("3060580000001217",new AttendanceState(0,null,""));
        addItem(new Student("B07126","3060581500013","まつお","かつとし","松尾","勝利","松尾勝利",thisTime,new TotalAttendance(1,12),null));
        thisTime = new ThisClassTime("3060580000001403",new AttendanceState(0,null,""));
        addItem(new Student("B07127","3060581500013","まつばら","せいけん","松原","聖賢","松原聖賢",thisTime,new TotalAttendance(4,9),null));
        thisTime = new ThisClassTime("3060580000000808",new AttendanceState(0,null,""));
        addItem(new Student("B08070","3060581500013","なかしま","としき","中嶋","俊樹","中嶋俊樹",thisTime,new TotalAttendance(13,0),null));
        thisTime = new ThisClassTime("3060580000000810",new AttendanceState(0,null,""));
        addItem(new Student("B08112","3060581500013","よしむら","ともき","吉村","友貴","吉村友貴",thisTime,new TotalAttendance(1,12),null));
        thisTime = new ThisClassTime("3060580000000812",new AttendanceState(0,null,""));
        addItem(new Student("B09003","3060581500013","あらい","たかひろ","荒井","啓裕","荒井啓裕",thisTime,new TotalAttendance(10,3),null));
        thisTime = new ThisClassTime("3060580000000815",new AttendanceState(0,null,""));
        addItem(new Student("B09025","3060581500013","おおいし","りょう","大石","良","大石良",thisTime,new TotalAttendance(10,3),null));
        thisTime = new ThisClassTime("3060580000000818",new AttendanceState(0,null,""));
        addItem(new Student("B09085","3060581500013","すずき","とおる","鈴木","徹","鈴木徹",thisTime,new TotalAttendance(5,8),null));
        thisTime = new ThisClassTime("3060580000001002",new AttendanceState(0,null,""));
        addItem(new Student("B09110","3060581500013","はらだ","かずのり","原田","和典","原田和典",thisTime,new TotalAttendance(12,1),null));
        thisTime = new ThisClassTime("3060580000001005",new AttendanceState(0,null,""));
        addItem(new Student("B09114","3060581500013","ふりの","しょうへい","藤野","頌平","藤野頌平",thisTime,new TotalAttendance(5,8),null));
        thisTime = new ThisClassTime("3060580000000203",new AttendanceState(0,null,""));
        addItem(new Student("B12002","30605815000002","きとう","しょうへい","木藤","正平","木藤正平",thisTime,new TotalAttendance(10,3),new Message("学生課より","保護者から連絡があったので学生課へ来て下さい.","保護者の方から学校に来ているかの相談を受けました.きちんと学校に来ているかを本人に確認して下さい.")));
        thisTime = new ThisClassTime("3060580000000215",new AttendanceState(0,null,""));
        addItem(new Student("B12012","30605815000006","ささき","しげのぶ","佐々木","重信","佐々木重信",thisTime,new TotalAttendance(9,4),null));
        thisTime = new ThisClassTime("3060580000000405",new AttendanceState(0,null,""));
        addItem(new Student("B12022","3060581500010", "そとぼり","ゆうと","外堀","勇人","外堀勇人",thisTime,new TotalAttendance(9,4),null));
        thisTime = new ThisClassTime("3060580000000210",new AttendanceState(0,null,""));
        addItem(new Student("B12031","3060581500010", "めの","こうじ","目野","工事","目野工事",thisTime,new TotalAttendance(13,0),null));
        thisTime = new ThisClassTime("3060580000000418",new AttendanceState(0,null,""));
        addItem(new Student("B12049","3060581500010", "ほみ","けんと","保見","健斗","保見健斗",thisTime,new TotalAttendance(10,2),null));
        thisTime = new ThisClassTime("3060580000000602",new AttendanceState(0,null,""));
        addItem(new Student("E13005","3060581500008", "すずむら","のぶいち","鈴村","信一","鈴村信一",thisTime,new TotalAttendance(13,0),null));
        thisTime = new ThisClassTime("3060580000000412",new AttendanceState(0,null,""));
        addItem(new Student("E13014","3060581500012", "はやし","しょうへい","林","昇平","林昇平",thisTime,new TotalAttendance(12,1),null));
        thisTime = new ThisClassTime("3060580000000610",new AttendanceState(0,null,""));
        addItem(new Student("E13098","3060581500012", "みの","しょうきち","美濃","庄吉","美濃庄吉",thisTime,new TotalAttendance(13,0),null));
        thisTime = new ThisClassTime("3060580000000402",new AttendanceState(0,"",null));
        addItem(new Student("G09003","3060581500007", "あんどう","しげぞう","安藤","茂三","安藤茂三",thisTime,new TotalAttendance(13,0),null));
        thisTime = new ThisClassTime("3060580000000205",new AttendanceState(0,null,null));
        addItem(new Student("G09018","3060581500003", "きむら","ひろかず","木村","洋一","木村洋一",thisTime,new TotalAttendance(12,1),null));
        thisTime = new ThisClassTime("3060580000000212",new AttendanceState(0,"",null));
        addItem(new Student("G09034","3060581500007", "しみず","たつや","清水","龍也","清水達也",thisTime,new TotalAttendance(13,0),null));
        thisTime = new ThisClassTime("3060580000000410",new AttendanceState(0,null,""));
        addItem(new Student("G09076","3060581500011", "その","しげき","園","重喜","園重喜",thisTime,new TotalAttendance(11,2),null));
        thisTime = new ThisClassTime("3060580000000605",new AttendanceState(0,null,""));
        addItem(new Student("G09110","3060581500011", "まみや","まもる","間宮","守","間宮守",thisTime,new TotalAttendance(12,1),null));
        thisTime = new ThisClassTime("3060580000000618",new AttendanceState(0,null,""));
        addItem(new Student("G09129","3060581500011", "もの","さとし","物","聡","物聡",thisTime,new TotalAttendance(12,1),new Message("保健室より","健康診断の結果を保健室に取りに来てください.","健康診断の結果を保健室に取りに来てください.")));
        thisTime = new ThisClassTime("3060580000000802",new AttendanceState(0,null,""));
        addItem(new Student("J06012","3060581500001", "かとう","たつや","加藤","達也","加藤達也",thisTime,new TotalAttendance(11,2),null));
        thisTime = new ThisClassTime("3060580000000208",new AttendanceState(0,null,null));
        addItem(new Student("J07011","3060581500000", "いとう","しょうた","伊藤","翔太","伊藤翔太",thisTime,new TotalAttendance(12,1),new Message("会計課","授業料について聞きたいので、会計課に来てください.","授業料が未納入です.")));
    }*/

    private static void addItem(Student item) {
        ITEMS.add(item);
    }
    public static Roster getDummyRoster(){
        return new Roster(ITEMS);
    }

}
