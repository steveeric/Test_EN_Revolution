package jp.pmw.test_en_revolution;

import com.google.gson.annotations.SerializedName;

/**
 * Created by scr on 2014/12/08.
 */
public class Student{
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

    /*private String id;
    Student(String id,String fullName){
        super(fullName);
        this.id=id;
    }
    public String getStudentId(){
        return this.id;
    }*/
}
