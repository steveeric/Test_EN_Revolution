package jp.pmw.test_en_revolution.one_cushion.select_teacher.dummy;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by scr on 2015/01/01.
 */
// テーブル名の定義
@Table(name = "STAFFS_MST")
public class StaffsMst extends Model {
    // フィールドの定義
    @Column(name = "STAFF_ID")
    public String staffId;
    @Column(name = "REGISTRATION_STATUS")
    public String registrationStatus;
    @Column(name = "STAFF_FAMILY_FURIGANA_NAME")
    public String staffFamilyFriganaName;
    @Column(name = "STAFF_GIVEN_FURIGANA_NAME")
    public String staffGivenFriganaName;
    @Column(name = "STAFF_NAME")
    public String staffName;
    @Column(name = "TEACHER_OR_STAFF")
    public String teacherOrStaff;
    @Column(name = "CATEGORY")
    public String category;
    @Column(name = "CAMPUS_ID")
    public String campusId;
    @Column(name = "REGISTER_DATE_TIME")
    public String registerDateTime;
    @Column(name = "LAST_UPDATE_TIME")
    public String lastUpdateTime;


    // コンストラクタの定義
    public StaffsMst(){
        super();
    }

    public StaffsMst(String staffId,String registrationStatus,
                     String staffFamilyFriganaName,String staffGivenFriganaName,
                     String staffName,String teacherOrStaff,
                     String category,String campusId,
                     String registerDateTime,String lastUpdateTime) {
        super();
        this.staffId = staffId;
        this.registrationStatus = registerDateTime;
        this. staffFamilyFriganaName = staffFamilyFriganaName;
        this.staffGivenFriganaName = staffGivenFriganaName;
        this.staffName = staffName;
        this.teacherOrStaff = teacherOrStaff;
        this.category = category;
        this.campusId = campusId;
        this.registerDateTime = registerDateTime;
        this.lastUpdateTime = lastUpdateTime;
    }
}