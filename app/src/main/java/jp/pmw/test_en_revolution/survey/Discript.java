package jp.pmw.test_en_revolution.survey;

import com.google.gson.annotations.SerializedName;

/**
 * Created by si on 2017/01/26.
 */

public class Discript {
    //  学籍番号
    @SerializedName("student_id_number")
    public String mStudentIdNumber;
    //  フリガナ
    @SerializedName("furigana")
    public String mFurigana;
    //  氏名
    @SerializedName("full_name")
    public String mFullName;
    //  記述内容
    @SerializedName("discript")
    public String mDiscript;
}
