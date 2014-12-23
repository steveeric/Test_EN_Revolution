package jp.pmw.test_en_revolution.confirm_class_plan;

/**
 * Created by scr on 2014/12/22.
 * 本日又は今学期にどのような授業を持っているかを管理するクラスです.
 *
 */
public class Teaching {
    /**
     * 本日授業があるかを保持します.
     * 0 : 本日は授業なし
     * 1 : 今授業中
     * 2 : 本日授業はあるが、時間帯がこの後.
     * **/
    private int today;
    /**
     * 今又は次の授業が何なのかを表すクラスです.
     * 学期を超えて取得することはありません.
     * つまり、授業を持っている先生が
     * 9月の時点で全て16週終わってしまった場合は、
     * NULLを返します.
     * ClassPlan : NULL(今学期授業終わり)
     * ClassPlan : NULLでない(今学期まだ授業がある.)
     * **/
    private ClassPlan classPlan;

    /**
     * 学期を表します.
     * 今何学期に属しているかを表します.
     * 1 : 前期です.
     * 2 : 後期です.
     * ***/
    private int semester;
    public Teaching(int today,ClassPlan classPlan,int semester){
        this.today = today;
        this.classPlan = classPlan;
        this.semester = semester;
    }

    public int getToday(){return this.today;}
    public ClassPlan getClassPlan(){return this.classPlan;}
    public int getSemester(){return this.semester;}

}
