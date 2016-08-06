package jp.pmw.test_en_revolution;

import com.google.gson.annotations.SerializedName;

/**
 * Created by si on 2016/02/05.
 * メッセージオブジェクトクラス
 */
public class MessageObject {
    //  メッセージID
    @SerializedName("message_content")
    private String messageContent;
    public String getMessageContent(){return this.messageContent;}
}
