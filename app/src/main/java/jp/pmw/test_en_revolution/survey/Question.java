package jp.pmw.test_en_revolution.survey;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by si on 2016/08/17.
 * Questionクラス
 * アンケート問題データクラス
 */
public class Question implements Serializable {
    //  アンケートID
    @SerializedName("question_id")
    public String mQuestionId;
    //  選択肢番号
    @SerializedName("number_only")
    public String mNumberOnly;
    //  選択肢番号
    @SerializedName("number")
    public String mNumber;
    //  選択肢タイトル
    @SerializedName("title")
    public String mTitle;
    //  選択肢群
    @SerializedName("choices")
    public Choice[] mChoices;
}
