package jp.pmw.test_en_revolution.grouping;

/**
 * Created by scr on 2015/01/08.
 */
public class GroupinConfig {
    //一つのグループの最少
    public static final int DEFAULT_ONE_GROUPING_MIN_COUN = 2;
    //一つのグループ最大
    public static final int DEFAULT_ONE_GROUPING_MAX_COUN = 4;

    //入力可能なグループ最小数
    public int mInputPossibleMinGroupCount = 0;

    //入力可能なグループ最大数
    public int mInputPossibleMaxGroupCount = 0;

    private int attendeeCount;

    GroupinConfig(int attendeeCount){
        this.attendeeCount = attendeeCount;
        mInputPossibleMinGroupCount = attendeeCount / DEFAULT_ONE_GROUPING_MAX_COUN;
        mInputPossibleMaxGroupCount = attendeeCount / DEFAULT_ONE_GROUPING_MIN_COUN;
    }

    //グループのメンバー数
   /* public int[] numberOfGroupUnit(int inputValue){

        int[] groupUnit = new int[inputValue];



    }*/


}
