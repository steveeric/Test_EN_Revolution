package jp.pmw.test_en_revolution;

import com.google.gson.annotations.SerializedName;

/**
 * Created by scr on 2014/12/08.
 */
public class PreStudent {
    //大学独自の学籍番ID
    @SerializedName("orijinal_student_id")
    public String mOrijinalStudentId;
    //学籍番ID
    @SerializedName("student_id")
    public String mStudentId;
    //苗字のふりがな
    @SerializedName("furigana_family_name")
    public String mFuriganaFamilyName;
    //なまえのふりがな
    @SerializedName("furigana_givne_name")
    public String mFuriganaGivenName;
    //苗字
    @SerializedName("family_name")
    public String mFamilyName;
    //名前
    @SerializedName("given_name")
    public String mGivenName;
    //フルネーム
    @SerializedName("full_name")
    public String mFullName;

}
