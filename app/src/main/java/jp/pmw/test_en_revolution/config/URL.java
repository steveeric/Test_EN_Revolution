package jp.pmw.test_en_revolution.config;

/**
 * Created by scr on 2014/12/07.
 */
public class URL {
    //  園田学園女子大学様 (add 2016-03-07 09:09)
    //private static final String TEMP_BASE_URL = "http://172.17.101.201/cms/";
    //private static final String TEMP_BASE_URL = "http://202.209.33.134/cms/";
    private static final String TEMP_BASE_URL = "http://192.168.53.216/cms/";
    //private static final String TEMP_BASE_URL = "http://192.168.53.195/cms/";
    //private static final String BASE_URL = "http://aitech.ac.jp/scr/scr";

    //public static final String ALL_TEACHER = BASE_URL+"/test/catalunya/public/api/teacher";
    //public static final String JSON_DIR = "/sg/app/api/json";

    //選択した頭文字から教員名リストを取得するサイト
    //public static final String TEACHER_FAMILY_NAME_INITIAL = BASE_URL+"/test/catalunya/public/api/teacher_family_name_initial";
    //public static final String TEACHER_FAMILY_NAME_INITIAL = BASE_URL+JSON_DIR;
    public static String getTeacherFamilyNameInitial(int index){
        //return  BASE_URL+JSON_DIR + "/search_faculty_family_name.php?index="+index;
        return TEMP_BASE_URL+"FacultyJson_Controller/searchFacultyFamilyName?index="+index;
    }

    public static final String getClassAbstructInfo(String facultyId){
        //return BASE_URL + JSON_DIR + "/confirm_class.php?f="+facultyId;
        return TEMP_BASE_URL + "ClassJson_Controller/confirmClass?f="+facultyId;
    }
    public static final String getClassReaminingTime(String sameClassNumber) {
        //return BASE_URL + JSON_DIR + "/class_remaining_time.php?scn=" + sameClassNumber;
        return TEMP_BASE_URL + "ClassJson_Controller/classReamingTime?scn="+sameClassNumber;
    }
    //  出欠確認用URL(授業参加学生 & 出席時刻状況)確認用
    public static final String getUrlChkAttendance(String sameClassNumber){
        return TEMP_BASE_URL + "AttendanceJson_Controller/getChkAttendance?scn="+sameClassNumber;
    }
    //  出席関係の情報のみを取得用
    public static final String getAttendanceRelationShipInto(String sameClassNumber){
        return TEMP_BASE_URL + "AttendanceJson_Controller/getAttendanceRelationshipInfo?scn="+sameClassNumber;
    }
    //  在室確認(再出席調査)URL
    public static final String getReAttendanceStart(String sameClassNumber){
        return TEMP_BASE_URL + "AttendanceJson_Controller/reAttendanceStart?scn="+sameClassNumber;
    }
    //  教室のマップ情報URL
    public static final String getRoomFloaMapInfo(String roomId){
        return TEMP_BASE_URL + "RoomJson_Controller/floaMapInfo?ro="+roomId;
    }
    //  グループ調整状態URL
    public static final String getGroupAdjustmentStatus(String sameClassNumber){
        return TEMP_BASE_URL + "Group_Controller/chkStatus?scn="+sameClassNumber;
    }
    //  グループ調整開始URL
    public static final String getStartGrupAdjustment(String sameClassNumber){
        return TEMP_BASE_URL + "Group_Controller/startAdjust?scn="+sameClassNumber;
    }
    //  グループ調整後の移動座席伝達URL
    public static final String getManualContacted(String seatAfterMoving,String sameClassNumber){
        return TEMP_BASE_URL + "Group_Controller/manualContacted?sam="+seatAfterMoving+"&scn="+sameClassNumber;
    }



    public static final String getClassInfo(String sameClassNumber){
        //return BASE_URL + JSON_DIR + "/class.php?class_id="+classId;
        return TEMP_BASE_URL + "ClassJson_Controller/classJson?scn="+sameClassNumber;
    }

    public static final String getQuestionnaireInfo(String sameClassNumber){
        //return BASE_URL + JSON_DIR + "/question.php?scn="+sameClassNumber;
        return TEMP_BASE_URL + "QuestionJson_Controller/question?scn="+sameClassNumber;
    }
    public static final String getQuestionnaireTextStart(String questionId){
        //return BASE_URL + JSON_DIR + "/question_start.php?question_id="+questionId;
        return TEMP_BASE_URL + "QuestionJson_Controller/questionTextTransmitStartTime?question_id="+questionId;
    }

    public static final String getQuestionnaireCheckStart(String questionId){
        return TEMP_BASE_URL + "QuestionJson_Controller/questionCheckTransmitStartTime?question_id="+questionId;
    }
    public static final String getQuestionResult(String sameClassNumber, String questionId){
        //return BASE_URL + JSON_DIR + "/question_result.php?scn="+sameClassNumber;
        return TEMP_BASE_URL + "QuestionJson_Controller/questionResultTransmitStartTime?scn="+sameClassNumber+"&question_id="+questionId;
    }
    //  授業最終に行ったアンケートの送信状態とNACK者を取得するURL
    public static final String getLastQuestionTransmitState(String sameClassNumber){
        return TEMP_BASE_URL + "TransmitStateJson_Controller/lastQuestionTransmitState?scn="+sameClassNumber;
    }


    public static final String getOnlyQuestionResult(String sameClassNumber, String questionId){
        //return BASE_URL + JSON_DIR + "/question_result.php?scn="+sameClassNumber;
        return TEMP_BASE_URL + "QuestionJson_Controller/getOnlyQuestionResult?scn="+sameClassNumber+"&question_id="+questionId;
    }


    /*public static final String getDoGrouping(String classId,int member){
        return BASE_URL + JSON_DIR + "/do_grouping.php?class_id="+classId+"&member="+member;
    }*/

    public static final String getForgotAppy(int p,/*String sameClassNumber,*/String attendanceId){
       // return BASE_URL + JSON_DIR + "/forgot_apply.php?p="+p+"&scn="+sameClassNumber+"&att_id="+attendanceId;
        return TEMP_BASE_URL + "Forgot_Controller/apply?p="+p+"&att_id="+attendanceId;
    }

    //  送信状態を授業識別番号を頼りに探します.
    public static final String getTransmitState(String sameClassNumber){
        // http://192.168.53.163/cms/TransmitStateJson_Controller/transmitState?scn=160328143000161000ROOM3060580002
        return TEMP_BASE_URL + "TransmitStateJson_Controller/transmitState?scn="+sameClassNumber;
    }
    //  出席ACK漏れ(そのだ教授)
    public static final String getAttendLeak(String attendanceId,int parameta){
        return TEMP_BASE_URL + "AckLeakSupport_Controller/attendLeak?a="+attendanceId+"&p="+parameta;
    }
    //  出席再調査ACK漏れ(そのだ教授+1)
    public static final String getReAttendLeak(String attendanceId,int parameta){
        return TEMP_BASE_URL + "AckLeakSupport_Controller/reAttendLeak?a="+attendanceId+"&p="+parameta;
    }

    //  外部名札ACK漏れ(かなえちゃん)
    public static final String getUndoLeak(String attendanceId,int parameta){
        return TEMP_BASE_URL + "AckLeakSupport_Controller/undoLeak?a="+attendanceId+"&p="+parameta;
    }


    //教室情報を取得サイト
    //public static final String ROOM_MAP = BASE_URL+"/test/catalunya/public/api/room_map";
    //出欠席の情報を取得サイト
    //public static final String ATTENDANCE_INFO = BASE_URL+"/test/catalunya/public/api/attendance";
    //出席者の情報を取得サイト
    //public static final String ATTENDEE_LIST = BASE_URL+"/test/catalunya/public/api/attendee_list";

}
