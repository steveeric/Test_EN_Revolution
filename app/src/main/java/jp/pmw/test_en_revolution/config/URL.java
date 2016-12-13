package jp.pmw.test_en_revolution.config;

/**
 * Created by scr on 2014/12/07.
 */
public class URL {
    //  Enumクラス
    public static final UrlEnum S_URL_ENUM = UrlEnum.AIT;
    //  IPアドレス
    public static final String S_IP_ADDRESS = S_URL_ENUM.getIpAddress();
    //  WEBアプリ名称
    public static final String S_WEB_APP_NAME = S_URL_ENUM.getWebAppName();
    //  ベースとなるURL
    private static final String TEMP_BASE_URL = "http://"+S_IP_ADDRESS+"/"+S_WEB_APP_NAME+"/";

    //選択した頭文字から教員名リストを取得するサイト
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
    //  出・遅・欠席回数を取得用
    public static final String getPastTotalAttendanceCount(String attendanceId){
        return TEMP_BASE_URL + "AttendanceJson_Controller/getPastTotalAttendanceCount?a="+attendanceId;
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
    /**
     *  getUrlRegardedAsAttendanceメソッド
     *  出席とみなすURL
     *  @param String   attendanceId    出席ID
     *  @param int      reason          理由
     *  @param int      forgot          SonoRIs忘れ(0:持っている,1:SonoRIs忘れ)
     * **/
    public static final String getUrlRegardedAsAttendance(String attendanceId, int reason, int forgot){
        return TEMP_BASE_URL + "AckLeakSupport_Controller/attendance?a="+attendanceId+"&r="+reason+"&f="+forgot;
    }
    /**
     *  getUrlRegardedAsLateメソッド
     *  遅刻とみなすURL
     *  @param String   attendanceId    出席ID
     *  @param int      reason          理由
     *  @param int      forgot          SonoRIs忘れ(0:持っている,1:SonoRIs忘れ)
     * **/
    public static final String getUrlRegardedAsLate(String attendanceId, int reason, int forgot){
        return TEMP_BASE_URL + "AckLeakSupport_Controller/late?a="+attendanceId+"&r="+reason+"&f="+forgot;
    }
    /**
     *  getUrlRegardedAsAbsentメソッド
     *  欠席とみなすURL
     *  @param String   attendanceId    出席ID
     * **/
    public static final String getUrlRegardedAsAbsent(String attendanceId){
        return TEMP_BASE_URL + "AckLeakSupport_Controller/absent?a="+attendanceId;
    }
    /**
     *  getUrlchkExistenceFaceImageメソッド
     *  ネットワーク上のサーバーの特定ディレクトリに顔画像が存在するかを確認します.
     *  @param String   studentId    学生ID
     * **/
    public static final String getUrlchkExistenceFaceImage(String studentId){
        return TEMP_BASE_URL + "AttendanceJson_Controller/chkExistenceFaceImage?st="+studentId;
    }
    /**
     *  getUrlgetFaceImageメソッド
     *  顔画像を取得します.
     *  @param String   studentId    学生ID
     * **/
    public static final String getUrlgetFaceImage(String studentId){
        return TEMP_BASE_URL + "AttendanceJson_Controller/getFaceImage?st="+studentId;
    }
    /**
     * getUrlgetQuestionAnswersメソッド
     * 質問回答群取得URL
     * @param   String  questionId      質問ID(クリッカー用)
     * @param   int     answerType      回答タイプ(0:はい、1:いいえ)
     * @author Ito Shota
     * @since  2016/08/08
     **/
    public static final String getUrlgetQuestionAnswers(String questionId, int answerType){
        return TEMP_BASE_URL + "QuestionJson_Controller/getQuestionAnswers?qid="+questionId+"&at="+answerType;
    }
    /**
     * getUrlSurveyStateメソッド
     * アンケート状態を取得します.
     * @param   String  sameClassNumber 授業識別番号
     * @author Ito Shota
     * @since  2016/08/17
     **/
    public static final String getUrlSurveyState(String sameClassNumber){
        return TEMP_BASE_URL + "SurveyDevaice_Controller/getSituation?scn="+sameClassNumber;
    }
    /**
     * getUrlStartSurveyメソッド
     * アンケートを開始するURLを取得します.
     * @param   String  sameClassNumber 授業識別番号
     * @author Ito Shota
     * @since  2016/08/17
     **/
    public static final String getUrlStartSurvey(String sameClassNumber){
        return TEMP_BASE_URL + "SurveyDevaice_Controller/startSurvey?scn="+sameClassNumber;
    }
    /**
     * getUrlGetSurveyAnswersメソッド
     * アンケートを開始するURLを取得します.
     * @param   String  surveyId        アンケートID(スマホ クリッカー)
     * @param   String  selectNumber    選択されている問題番号(問は除く、数字のみ)
     * @param   String  selectedId      選択ID(二択のみしか受けておりません.)[例 はい:○○○○_01, いいえ:○○○○_02]
     * @author Ito Shota
     * @since  2016/08/18
     **/
    public static final String getUrlGetSurveyAnswers(String surveyId, String selectNumber, String selectedId){
        return TEMP_BASE_URL + "SurveyDevaice_Controller/getSurveyAnswers?sc="+surveyId+"&sn="+selectNumber+"&sl="+selectedId;
    }


}
