package jp.pmw.test_en_revolution.confirm_class_plan;

import java.io.Serializable;

/**
 * Created by scr on 2014/12/17.
 */
public class ClassPlan implements Serializable {
    //授業ID
    private String classId;
    //日にちクラス
    private When when;
    //時間帯クラス
    private TimeZone timeZone;
    //場所クラス
    private Place place;
    //通算授業回数
    private int total;
    //アンケートを実施するか
    private int doQuestionnaire;
    //何時限目か
    //private String priodName;
    //授業開始時刻
    //private String classStartTime;
    //授業終了時刻
    //private String classEndTime;
    //現在の授業を閲覧している人数
    private int nowBrowsingCount;
    //授業クラス
    private Subject subject;
    //閲覧状況を管理するクラス
    private BrowsingClass browsingClass;

    public ClassPlan(String classId,When when,TimeZone timeZone,Place place
            ,int total,int doQuestionnaire
            ,int browsingCount,Room room,Subject subject,BrowsingClass browsingClass){
        this.classId = classId;
        this.when = when;
        this.place = place;
        this.total = total;
        this.doQuestionnaire = doQuestionnaire;
        this.timeZone = timeZone;
        this.nowBrowsingCount = browsingCount;
        this.subject = subject;
        this.browsingClass = browsingClass;
    }

    public String getClassId(){
        return this.classId;
    }
    public When getWhen(){return this.when;}
    public TimeZone getTimeZone(){return this.timeZone;}
    public Place getPlace(){return this.place;}
    public int getTotal(){
        return this.total;
    }
    public int getDoQuestionnaire(){
        return this.doQuestionnaire;
    }
    public int getNowBrowsingCount(){
        return this.nowBrowsingCount;
    }
    public Subject getSubject(){return this.subject;}
    public BrowsingClass getBrowsingClass(){return this.browsingClass;}
}
