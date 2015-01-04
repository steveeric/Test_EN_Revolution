package jp.pmw.test_en_revolution.dummy;

import java.util.ArrayList;
import java.util.List;

import jp.pmw.test_en_revolution.confirm_class_plan.AttendanceState;
import jp.pmw.test_en_revolution.confirm_class_plan.Message;
import jp.pmw.test_en_revolution.confirm_class_plan.Roster;
import jp.pmw.test_en_revolution.confirm_class_plan.Student;
import jp.pmw.test_en_revolution.confirm_class_plan.ThisClassTime;
import jp.pmw.test_en_revolution.confirm_class_plan.TotalAttendance;

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

    static {
        // Add 3 sample items.
        ThisClassTime thisTime = null;
        thisTime = new ThisClassTime("3060580000000203",new AttendanceState(0,null,"　"));
        addItem(new Student("J07011","3060581500000", "いとう","しょうた","伊藤","翔太","伊藤翔太",thisTime,new TotalAttendance(12,1),new Message("会計課","授業料について聞きたいので、会計課に来てください.","授業料が未納入です.")));
        thisTime = new ThisClassTime("3060580000000205",new AttendanceState(0,null,null));
        addItem(new Student("J06012","3060581500001", "かとう","たつや","加藤","龍也","加藤達也",thisTime,new TotalAttendance(11,2),null));
        thisTime = new ThisClassTime("3060580000000208",new AttendanceState(0,null,null));
        addItem(new Student("B12042","3060581500002", "きとう","たつや","木藤","龍也","木藤達也",thisTime,new TotalAttendance(10,3),new Message("学生課より","保護者から連絡があったので学生課へ来て下さい.","保護者の方から学校に来ているかの相談を受けました.きちんと学校に来ているかを本人に確認して下さい.")));
        thisTime = new ThisClassTime("3060580000000210",new AttendanceState(0,null,"　"));
        addItem(new Student("G09052","3060581500003", "きむら","たつや","木村","龍也","木村達也",thisTime,new TotalAttendance(12,1),null));
        thisTime = new ThisClassTime("3060580000000212",new AttendanceState(0,"","　"));
        addItem(new Student("E13062","3060581500004", "くずや","たつや","葛谷","龍也","葛谷達也",thisTime,new TotalAttendance(11,2),new Message("広報課より","広報課へ取材を受けに来てください.","取材をしたいです.")));
        thisTime = new ThisClassTime("3060580000000215",new AttendanceState(0,null,"　"));
        addItem(new Student("A03072","3060581500005", "こみや","たつや","込野","龍也","込野達也",thisTime,new TotalAttendance(10,3),null));
        thisTime = new ThisClassTime("3060580000000217",new AttendanceState(0,"","　"));
        addItem(new Student("B12042","3060581500006", "ささき","たつや","佐々木","龍也","佐々木達也",thisTime,new TotalAttendance(9,4),null));
        thisTime = new ThisClassTime("3060580000000402",new AttendanceState(0,"","　"));
        addItem(new Student("G09052","3060581500007", "しみず","たつや","清水","龍也","清水達也",thisTime,new TotalAttendance(13,0),null));
        thisTime = new ThisClassTime("3060580000000405",new AttendanceState(0,null,"　"));
        addItem(new Student("A03072","3060581500009", "せと","たつや","瀬戸","龍也","瀬戸達也",thisTime,new TotalAttendance(10,3),new Message("教務課より","履修申告状況について聞きたいので、教務課に来て下さい.","履修申告期間が過ぎていますがどうしますか?")));
        thisTime = new ThisClassTime("3060580000000408",new AttendanceState(0,null,"　"));
        addItem(new Student("B12042","3060581500010", "そとぼり","たつや","外堀","龍也","外堀達也",thisTime,new TotalAttendance(9,4),null));
        thisTime = new ThisClassTime("3060580000000410",new AttendanceState(0,null,"　"));
        addItem(new Student("G09052","3060581500011", "そのだ","たつや","園田","龍也","園田達也",thisTime,new TotalAttendance(11,2),null));
        thisTime = new ThisClassTime("3060580000000412",new AttendanceState(0,null,"　"));
        addItem(new Student("E13062","3060581500012", "はやし","たつや","林","龍也","林達也",thisTime,new TotalAttendance(12,1),null));
        thisTime = new ThisClassTime("3060580000000415",new AttendanceState(1,null,null));
        addItem(new Student("A03072","3060581500009", "ふじわら","たつや","藤原","龍也","藤原達也",thisTime,new TotalAttendance(9,4),null));
        thisTime = new ThisClassTime("3060580000000418",new AttendanceState(1,null,null));
        addItem(new Student("B12042","3060581500010", "ほみ","たつや","保見","龍也","保見達也",thisTime,new TotalAttendance(10,2),null));

        thisTime = new ThisClassTime("3060580000000602",new AttendanceState(0,"","　"));
        addItem(new Student("E13062","3060581500008", "すずむら","たつや","鈴村","龍也","鈴村達也",thisTime,new TotalAttendance(13,0),null));
        thisTime = new ThisClassTime("3060580000000605",new AttendanceState(1,null,null));
        addItem(new Student("A03072","3060581500013", "ひびの","たつや","日比野","龍也","日比野達也",thisTime,new TotalAttendance(8,5),null));
        thisTime = new ThisClassTime("3060580000000608",new AttendanceState(1,null,null));
        addItem(new Student("G09052","3060581500011", "まみや","たつや","間宮","龍也","間宮達也",thisTime,new TotalAttendance(12,1),null));
        thisTime = new ThisClassTime("3060580000000610",new AttendanceState(1,null,null));
        addItem(new Student("E13062","3060581500012", "みの","たつや","美濃","龍也","美濃達也",thisTime,new TotalAttendance(13,0),null));
        thisTime = new ThisClassTime("3060580000000612",new AttendanceState(1,null,null));
        addItem(new Student("A03072","3060581500013", "むさしの","たつや","武蔵野","龍也","武蔵野達也",thisTime,new TotalAttendance(12,1),null));
        thisTime = new ThisClassTime("3060580000000615",new AttendanceState(1,null,null));
        addItem(new Student("A03072","3060581500009", "むとう","たつや","武藤","龍也","武藤達也",thisTime,new TotalAttendance(13,0),new Message("学生課より","保護者より問い合わせがありました.学生課に来てください.","保護者よりと合わせがありましたので、本人に学校に来ている状況を聞き保護者に連絡して下さい.")));
        thisTime = new ThisClassTime("3060580000000618",new AttendanceState(1,null,null));
        addItem(new Student("B12042","3060581500010", "めの","りゅうや","目野","龍也","目野龍也",thisTime,new TotalAttendance(13,0),null));
        thisTime = new ThisClassTime("3060580000000702",new AttendanceState(1,null,null));
        addItem(new Student("G09052","3060581500011", "もの","たつや","物","龍也","物達也",thisTime,new TotalAttendance(12,1),new Message("保健室より","健康診断の結果を保健室に取りに来てください.","健康診断の結果を保健室に取りに来てください.")));
        thisTime = new ThisClassTime("30605800000000705",new AttendanceState(1,null,null));
        addItem(new Student("A03072","3060581500013", "かとう","たつや","加藤","龍也","加藤達也",thisTime,new TotalAttendance(10,3),null));
    }

    private static void addItem(Student item) {
        ITEMS.add(item);
    }


    public static Roster getDummyRoster(){
        return new Roster(ITEMS);
    }

}
