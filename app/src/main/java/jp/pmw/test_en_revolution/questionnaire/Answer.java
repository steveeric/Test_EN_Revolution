package jp.pmw.test_en_revolution.questionnaire;

import java.io.Serializable;

/**
 * Created by scr on 2014/12/30.
 * 回答者を保持するクラス.
 */
public class Answer implements Serializable {
    private String answerIndexColor;
    private String answerContent;
    //回答者数
    private int answerCount;
    private int percentage;

    public Answer(String answerIndexColor,String answerContent,int answerCount,int percentage){
        this.answerIndexColor = answerIndexColor;
        this.answerContent = answerContent;
        this.answerCount = answerCount;
        this.percentage = percentage;
    }
    public String getanswerIndexColor(){return this.answerIndexColor;}
    public String getAnswerContent(){return this.answerContent;}
    public int getAnswerCount(){
        return this.answerCount;
    }
    public int getPercentage(){return this.percentage;}

}
