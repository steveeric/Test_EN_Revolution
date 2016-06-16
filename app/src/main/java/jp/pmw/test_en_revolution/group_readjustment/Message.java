package jp.pmw.test_en_revolution.group_readjustment;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by si on 2016/05/11.
 */
public class Message {
    @SerializedName("messages")
    private List<String> messages;
    public List<String> getMessages(){return messages; }
}
