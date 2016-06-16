package jp.pmw.test_en_revolution.grouping;

/**
 * Created by scr on 2015/03/02.
 */
public class Grouping {
    private int groupingCount;
    private String groupingName;
    private int active;
    Grouping(int groupingCount,String groupingName){
        this.groupingCount = groupingCount;
        this.groupingName = groupingName;
    }

    public void setActive(int active){
        this.active = active;
    }

    public int getActive(){
        return this.active;
    }
    public int getGroupingCount(){
        return this.groupingCount;
    }
    public String getGroupingName(){
        return this.groupingName;
    }

}
