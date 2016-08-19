package jp.pmw.test_en_revolution.survey;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by si on 2016/08/17.
 * Choiceクラス
 * 選択肢を扱います.
 */
public class Choice implements Serializable {
    // 選択内容「はい」「いいえ」
    @SerializedName("choice")
    public String mChoice;
    //  選択肢の色
    @SerializedName("choice_color")
    public String mChoiceIndexColor;
    //  回答者数
    @SerializedName("answer")
    public int mAnswer;
    //  回答者数
    @SerializedName("str_answer")
    public String mStrAnswer;
    //  割合
    @SerializedName("percentage")
    public String mPercentage;
}
