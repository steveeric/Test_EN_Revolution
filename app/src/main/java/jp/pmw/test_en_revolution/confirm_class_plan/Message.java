package jp.pmw.test_en_revolution.confirm_class_plan;

/**
 * Created by scr on 2014/12/25.
 */
public class Message {
    private String from;
    private String message;

    public Message(String from,String message){
        this.from = from;
        this.message = message;
    }

    public String getFrom(){return this.from;}
    public String getMessage(){return this.message;}
}
