package jp.pmw.test_en_revolution.confirm_class_plan.dummy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.pmw.test_en_revolution.confirm_class_plan.BrowsingClass;
import jp.pmw.test_en_revolution.confirm_class_plan.Building;
import jp.pmw.test_en_revolution.confirm_class_plan.Campus;
import jp.pmw.test_en_revolution.confirm_class_plan.ClassPlan;
import jp.pmw.test_en_revolution.confirm_class_plan.MextUniversity;
import jp.pmw.test_en_revolution.confirm_class_plan.Place;
import jp.pmw.test_en_revolution.confirm_class_plan.Room;
import jp.pmw.test_en_revolution.confirm_class_plan.Subject;
import jp.pmw.test_en_revolution.confirm_class_plan.Teaching;
import jp.pmw.test_en_revolution.confirm_class_plan.Timetable;
import jp.pmw.test_en_revolution.confirm_class_plan.When;

/**
 * Created by scr on 2014/12/22.
 * ダミー授業内容を保持するクラスです.
 */
public class DummyContentClass {

    public static final String ROOM_ID_241 = "306058000000";
    public static final String ROOM_ID_135 = "306058000001";
    public static final String ROOM_ID_1317 = "306058000002";


    /**
     * An array of sample (dummy) items.
     */
    public static List<Teaching> ITEMS = new ArrayList<Teaching>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static Map<String, Teaching> ITEM_MAP = new HashMap<String, Teaching>();

    static {
        // Add 3 sample items.
        /*テストインスタンス*/
        MextUniversity university = null;
        Campus campus = null;
        Building building = null;
        Room room = null;
        When when = null;
        Timetable timeZone = null;
        Place place = null;
        Subject subject = null;
        BrowsingClass browsingClass = null;
        ClassPlan classPlan = null;
        Teaching teaching = null;
        /*テストインスタンス*/

        //現在授業中の場合.
         /**/
        when = new When("2014","12","26");
        timeZone = new Timetable("4時限目","14:30:00","16:10:00");
        university = new MextUniversity("306058","園田学園女子大学");
        campus = new Campus("30605800","そのだキャンパス");
        building = new Building("30605800","1号館");
        room = new Room(ROOM_ID_241,"241教室",244);
        place = new Place(university,campus,building,room);
        subject = new Subject("306058001400116","情報数学I");
        browsingClass = new BrowsingClass("");
        classPlan = new ClassPlan("306058001400116141219306058001405",when,timeZone,place,11,0,0,room,subject,browsingClass);
        /**/
        teaching = new Teaching(1,classPlan,0);
        addItem(teaching);

        /*衣井先生*/
        when = new When("2014","12","26");
        timeZone = new Timetable("4時限目","14:30:00","16:10:00");
        university = new MextUniversity("306058","園田学園女子大学");
        campus = new Campus("30605800","そのだキャンパス");
        building = new Building("30605800","1号館");
        room = new Room(ROOM_ID_135,"135教室",144);
        place = new Place(university,campus,building,room);
        subject = new Subject("306058001400280","スポーツ栄養学");
        browsingClass = new BrowsingClass("");
        classPlan = new ClassPlan("306058001400280141219306058001405",when,timeZone,place,11,0,0,room,subject,browsingClass);
        teaching = new Teaching(1,classPlan,0);
        addItem(teaching);

        /*稲葉先生*/
        when = new When("2014","12","26");
        timeZone = new Timetable("4時限目","14:30:00","16:10:00");
        university = new MextUniversity("306058","園田学園女子大学");
        campus = new Campus("30605800","そのだキャンパス");
        building = new Building("30605800","1号館");
        room = new Room(ROOM_ID_1317, "1317教室" ,180);
        place = new Place(university,campus,building,room);
        subject = new Subject("306058001400220","スポーツ社会学");
        browsingClass = new BrowsingClass("");
        classPlan = new ClassPlan("306058001400220141219306058001405",when,timeZone,place,11,0,1,room,subject,browsingClass);
        /**/
        teaching = new Teaching(1,classPlan,0);
        addItem(teaching);

        //この後じゅぎょうですよ!.
        /**/
        when = new When("2014","12","26");
        timeZone = new Timetable("5時限目","16:20:00","18:00:00");
        university = new MextUniversity("306058","園田学園女子大学");
        campus = new Campus("30605800","そのだキャンパス");
        building = new Building("30605800","1号館");
        room = new Room("30605800000","135教室",244);
        place = new Place(university,campus,building,room);
        subject = new Subject("306058001400116","人工知能");
        browsingClass = new BrowsingClass("");
        classPlan = new ClassPlan("306058001400116141219306058001405",when,timeZone,place,11,0,0,room,subject,browsingClass);
        /**/
        teaching = new Teaching(2,classPlan,0);
        addItem(teaching);

        //今日は授業がないけど、今期(後期)(ex明日)まだ授業あるよ
        /**/
        when = new When("2014","12","29");
        timeZone = new Timetable("4時限目","14:30:00","14:00:00");
        university = new MextUniversity("306058","園田学園女子大学");
        campus = new Campus("30605800","そのだキャンパス");
        building = new Building("30605800","1号館");
        room = new Room("30605800000","501教室",244);
        place = new Place(university,campus,building,room);
        subject = new Subject("306058001400116","人工知能");
        browsingClass = new BrowsingClass("");
        classPlan = new ClassPlan("306058001400116141219306058001405",when,timeZone,place,11,0,0,room,subject,browsingClass);
        /**/
        teaching = new Teaching(0,classPlan,2);
        addItem(teaching);

        //今学期(後期)は終了しましたよ.
        teaching = new Teaching(0,null,2);
        addItem(teaching);

    }

    private static void addItem(Teaching classPlan) {
        ITEMS.add(classPlan);
        //ITEM_MAP.put(classPlan.getClassPlan().getClassId(), classPlan);
    }

    /***
     *STAFF_IDから渡す内容を変える.
     * 園田女学園様のテストデータを
     * 元に振り分けるスタイル位なっております.
     * * */
    public static Teaching getDummyTeaching(String staffId){
        Teaching teaching = null;
        if(staffId.equals("30605800140007")){
            //赤羽教職
            teaching = ITEMS.get(0);
        }else if(staffId.equals("30605800140019")){
            //石垣短
            teaching = ITEMS.get(3);
        }else if(staffId.equals("30605800140016")){
            //磯だ総研
            teaching = ITEMS.get(4);
        }else if(staffId.equals("30605800140020")){
            //稲葉総研
            teaching = ITEMS.get(2);
        }else if(staffId.equals("30605800140056")){
            //衣井健生
            teaching = ITEMS.get(1);
        }else{
            teaching = ITEMS.get(5);
        }
        return teaching;
    }

}

