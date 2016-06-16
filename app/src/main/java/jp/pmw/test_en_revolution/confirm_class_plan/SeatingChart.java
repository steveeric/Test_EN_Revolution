package jp.pmw.test_en_revolution.confirm_class_plan;

import com.google.gson.annotations.SerializedName;

/**
 * Created by scr on 2015/02/24.
 */
public class SeatingChart {
    @SerializedName("max_map_row")
    private int maxMapRow;
    @SerializedName("max_map_column")
    private int maxMapColumn;
    //有効行数
    @SerializedName("enable_row_count")
    private int enableRowLineCount;
    //無効行数
    @SerializedName("disable_row_count")
    private int disableRowLineCount;
    @SerializedName("seating_chart")
    private String seatingChart;

    public int getMaxMapRow(){
        return this.maxMapRow;
    }
    public int getMaxMapColumn(){
        return this.maxMapColumn;
    }
    public int getEnabeRowLineCount(){
        return this.enableRowLineCount;
    }
    public int getDisableRowLineCount(){
        return this.disableRowLineCount;
    }
    public String getSeatingChart(){
        return this.seatingChart;
    }
}
