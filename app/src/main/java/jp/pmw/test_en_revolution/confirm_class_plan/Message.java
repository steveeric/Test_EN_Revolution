package jp.pmw.test_en_revolution.confirm_class_plan;

import java.io.Serializable;

/**
 * Created by scr on 2014/12/25.
 */
public class Message implements Serializable {
    //どこから
    private String from;
    //簡易メッセージ(ESLに表示されるであろう内容.)
    private String simpleMessage;
    //具体的な内容
    private String message;

    public Message(String from,String simpleMessage,String message){
        this.from = from;
        this.simpleMessage = simpleMessage;
        this.message = message;
    }

    public String getFrom(){return this.from;}
    public String getSimpleMessage(){return this.simpleMessage;}
    public String getMessage(){return this.message;}
}
