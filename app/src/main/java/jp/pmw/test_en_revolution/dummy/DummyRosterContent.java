package jp.pmw.test_en_revolution.dummy;

import java.util.ArrayList;
import java.util.List;

import jp.pmw.test_en_revolution.confirm_class_plan.Attendance;
import jp.pmw.test_en_revolution.confirm_class_plan.Message;
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


    static {
        // Add 3 sample items.
        addItem(new Student("J07011","3060581500000", "いとう","しょうた","伊藤","翔太","伊藤翔太","3060580000000203",new Attendance(0,null,""),new Message("","")));
        addItem(new Student("J06012","3060581500001", "かとう","たつや","加藤","龍也","加藤達也","3060580000000204",new Attendance(0,null,""),null));
        addItem(new Student("B12042","3060581500002", "きとう","たつや","木藤","龍也","木藤達也","3060580000000205",new Attendance(0,null,""),null));
        addItem(new Student("G09052","3060581500003", "きむら","たつや","木村","龍也","木村達也","3060580000000207",new Attendance(0,null,""),null));
        addItem(new Student("E13062","3060581500004", "くずや","たつや","葛谷","龍也","葛谷達也","3060580000000208",new Attendance(0,"",null),new Message("","")));
        addItem(new Student("A03072","3060581500005", "こみや","たつや","込野","龍也","込野達也","3060580000000209",new Attendance(0,null,""),null));
        addItem(new Student("B12042","3060581500006", "ささき","たつや","佐々木","龍也","佐々木達也","3060580000000210",new Attendance(0,"",null),null));
        addItem(new Student("G09052","3060581500007", "しみず","たつや","清水","龍也","清水達也","3060580000000212",new Attendance(0,"",null),null));
        addItem(new Student("E13062","3060581500008", "すずむら","たつや","鈴村","龍也","鈴村達也","3060580000000213",new Attendance(0,"",null),null));
        addItem(new Student("A03072","3060581500009", "せと","たつや","瀬戸","龍也","瀬戸達也","3060580000000214",new Attendance(0,null,""),new Message("","")));
        addItem(new Student("B12042","3060581500010", "そとぼり","たつや","外堀","龍也","外堀達也","3060580000000204",new Attendance(0,null,""),null));
        addItem(new Student("G09052","3060581500011", "そのだ","たつや","園田","龍也","園田達也","3060580000000204",new Attendance(0,null,""),null));
        addItem(new Student("E13062","3060581500012", "はやし","たつや","林","龍也","林達也","3060580000000204",new Attendance(0,null,""),null));
        addItem(new Student("A03072","3060581500013", "ひびの","たつや","日比野","龍也","日比野達也","3060580000000204",new Attendance(1,null,null),null));
        addItem(new Student("A03072","3060581500009", "ふじわら","たつや","藤原","龍也","藤原達也","3060580000000214",new Attendance(1,null,null),null));
        addItem(new Student("B12042","3060581500010", "ほみ","たつや","保見","龍也","保見達也","3060580000000204",new Attendance(1,null,null),null));
        addItem(new Student("G09052","3060581500011", "まみや","たつや","間宮","龍也","間宮達也","3060580000000204",new Attendance(1,null,null),null));
        addItem(new Student("E13062","3060581500012", "みの","たつや","美濃","龍也","美濃達也","3060580000000204",new Attendance(1,null,null),null));
        addItem(new Student("A03072","3060581500013", "むさしの","たつや","武蔵野","龍也","武蔵野達也","3060580000000204",new Attendance(1,null,null),null));
        addItem(new Student("A03072","3060581500009", "むとう","たつや","武藤","龍也","武藤達也","3060580000000214",new Attendance(1,null,null),new Message("","")));
        addItem(new Student("B12042","3060581500010", "めの","たつや","目野","龍也","目野達也","3060580000000204",new Attendance(1,null,null),null));
        addItem(new Student("G09052","3060581500011", "もの","たつや","物","龍也","物達也","3060580000000204",new Attendance(1,null,null),null));
        addItem(new Student("A03072","3060581500013", "かとう","たつや","加藤","龍也","加藤達也","3060580000000204",new Attendance(1,null,null),null));
        addItem(new Student("A03072","3060581500009", "かとう","たつや","加藤","龍也","加藤達也","3060580000000214",new Attendance(1,null,null),null));
        addItem(new Student("B12042","3060581500010", "かとう","たつや","加藤","龍也","加藤達也","3060580000000204",new Attendance(1,null,null),null));
        addItem(new Student("G09052","3060581500011", "かとう","たつや","加藤","龍也","加藤達也","3060580000000204",new Attendance(1,null,null),null));
        addItem(new Student("E13062","3060581500012", "かとう","たつや","加藤","龍也","加藤達也","3060580000000204",new Attendance(1,null,null),null));
        addItem(new Student("A03072","3060581500013", "かとう","たつや","加藤","龍也","加藤達也","3060580000000204",new Attendance(1,null,null),null));
        addItem(new Student("A03072","3060581500009", "かとう","たつや","加藤","龍也","加藤達也","3060580000000214",new Attendance(1,null,null),null));
        addItem(new Student("B12042","3060581500010", "かとう","たつや","加藤","龍也","加藤達也","3060580000000204",new Attendance(1,null,null),null));
        addItem(new Student("G09052","3060581500011", "かとう","たつや","加藤","龍也","加藤達也","3060580000000204",new Attendance(1,null,null),null));
        addItem(new Student("E13062","3060581500012", "かとう","たつや","加藤","龍也","加藤達也","3060580000000204",new Attendance(1,null,null),null));
        addItem(new Student("A03072","3060581500013", "かとう","たつや","加藤","龍也","加藤達也","3060580000000204",new Attendance(1,null,null),null));
        addItem(new Student("A03072","3060581500009", "かとう","たつや","加藤","龍也","加藤達也","3060580000000214",new Attendance(0,"",null),null));
        addItem(new Student("B12042","3060581500010", "かとう","たつや","加藤","龍也","加藤達也","3060580000000204",new Attendance(0,"",null),null));
        addItem(new Student("G09052","3060581500011", "かとう","たつや","加藤","龍也","加藤達也","3060580000000204",new Attendance(0,"",null),null));
        addItem(new Student("E13062","3060581500012", "かとう","たつや","加藤","龍也","加藤達也","3060580000000204",new Attendance(0,null,""),null));
        addItem(new Student("A03072","3060581500013", "かとう","たつや","加藤","龍也","加藤達也","3060580000000204",new Attendance(0,null,""),null));
        addItem(new Student("A03072","3060581500009", "かとう","たつや","加藤","龍也","加藤達也","3060580000000214",new Attendance(0,null,""),null));
        addItem(new Student("B12042","3060581500010", "かとう","たつや","加藤","龍也","加藤達也","3060580000000204",new Attendance(0,null,""),null));
        addItem(new Student("G09052","3060581500011", "かとう","たつや","加藤","龍也","加藤達也","3060580000000204",new Attendance(0,null,""),null));
        addItem(new Student("E13062","3060581500012", "かとう","たつや","加藤","龍也","加藤達也","3060580000000204",new Attendance(0,null,null),null));
        addItem(new Student("A03072","3060581500013", "かとう","たつや","加藤","龍也","加藤達也","3060580000000204",new Attendance(0,null,null),null));

    }

    private static void addItem(Student item) {
        ITEMS.add(item);
    }


    public static Roster getDummyRoster(){
        return new Roster(ITEMS);
    }

}
