package jp.pmw.test_en_revolution.survey;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by si on 2016/08/17.
 * Questionクラス
 * アンケート問題データクラス
 */
public class Question implements Serializable {
    //  選択式
    public static final int TYPE_SELECTION = 0;
    //  記述式
    public static final int TYPE_DESCRIPT = 1;

    //  アンケートID
    @SerializedName("question_id")
    public String mQuestionId;
    //  タイプ
    @SerializedName("type")
    public int mType;
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
    // 記述群
    @SerializedName("discripts")
    public Discript[] mDiscript;
}
