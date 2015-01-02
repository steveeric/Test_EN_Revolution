package jp.pmw.test_en_revolution.questionnaire;

import java.util.List;

/**
 * Created by scr on 2014/12/25.
 * アンケートで尋ねる内容を管理するクラスです.
 */
public class Ask {
    //尋ねる問番号
    private String askNumber;
    //尋ねる内容
    private String askContent;
    //回答情報
    private List<Answer> answer;
    public Ask(String askNumber, String askContent,List<Answer> answer){
        this.askNumber = askNumber;
        this.askContent = askContent;
        this.answer = answer;
    }
    public String getAskNumber(){
        return this.askNumber;
    }
    public String getAskContent(){
        return this.askContent;
    }
    public List<Answer> getAnswer(){return this.answer;}
}
