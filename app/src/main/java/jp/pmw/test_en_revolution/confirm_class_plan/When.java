package jp.pmw.test_en_revolution.confirm_class_plan;

import java.io.Serializable;

/**
 * Created by scr on 2014/12/23.
 */
public class When  implements Serializable {
    //実施年
    private String year;
    //実施月
    private String month;
    //実施日
    private String day;

    public When(String year,String month,String day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public String getYear(){
        return this.year;
    }
    public String getMonth(){
        return this.month;
    }
    public String getDay(){
        return this.day;
    }

}
