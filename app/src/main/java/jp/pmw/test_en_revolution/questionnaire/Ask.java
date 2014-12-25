package jp.pmw.test_en_revolution.questionnaire;

/**
 * Created by scr on 2014/12/25.
 * アンケートで尋ねる内容を管理するクラスです.
 */
public class Ask {
    //尋ねる問番号
    private String askNumber;
    //尋ねる内容
    private String askContent;
    public Ask(String askNumber, String askContent){
        this.askNumber = askNumber;
        this.askContent = askContent;
    }
    public String getAskNumber(){
        return this.askNumber;
    }
    public String getAskContent(){
        return this.askContent;
    }
}
