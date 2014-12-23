package jp.pmw.test_en_revolution.confirm_class_plan;

import java.io.Serializable;

/**
 * Created by scr on 2014/12/17.
 * 文科省が定める大学コードを保持するクラス.
 * @author Shota Ito
 * @version 1.0
 */
public class MextUniversity implements Serializable {
    private String mextUniversityId;
    private String universitySchoolName;

    public MextUniversity(String mextUniversityId,String universitySchoolName){
        this.mextUniversityId = mextUniversityId;
        this.universitySchoolName = universitySchoolName;
    }

    public String getMextUniversityId(){
        return this.mextUniversityId;
    }
    public String getMextUniversityName(){
        return this.universitySchoolName;
    }
}
