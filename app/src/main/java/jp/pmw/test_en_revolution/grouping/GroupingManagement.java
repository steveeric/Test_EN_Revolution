package jp.pmw.test_en_revolution.grouping;

import java.util.List;

import jp.pmw.test_en_revolution.confirm_class_plan.Student;

/**
 * Created by scr on 2015/01/06.
 * グルーピング情報を一括で管理するクラスです.
 * ・グルーピングを行っているか
 * ・グループの内訳
 * 等をクラスが管理します.
 *
 * 注意 : グルーピングは授業内に一度しか行えない仕様になっています.
 */
public class GroupingManagement {
    public static final String[] GROUP_NAME = {"A","B","C","D","E","F"};
    //グルーピングを行ったかどうか
    /**
     * ・ false まだおこなっていない
     * ・ true すでに行った
     * **/
    private boolean doGroupingFlag = false;
    //どのモードでグループ編成を行ったのかを保持する
    /**
     * 1 グループ数
     * 2 メンバー数
     * **/
    private int groupingMode = -1;
    //
    private int requestCount = -1;
    //グループ編成時の一つのグループ最大人数
    private int maxMemberCount;
    //グループ編成時の一つのグループ最少人数
    private int miniMemberCount;
    //グループ
    private List<Student> groupings;

    public GroupingManagement(){

    }

    public int[] setRequestGroupingMode(int mode,int requestcount,int attendeeCount){
        this.groupingMode = mode;
        this.requestCount = requestcount;

        int[] membersCount = new int[requestCount];

        //int evenFlag = 1;
        //①本日の出席者/グループ数
        int ress = attendeeCount%requestcount;
        int amari = attendeeCount / requestCount;
        if(ress != 0){
            ++amari;
        }

        for(int i = 0; i < requestCount; i++) {
            if (ress != 0 && i == (requestCount - 1)) {
                membersCount[i] = --amari;
            } else {
                membersCount[i] = amari;
            }
        }
        return membersCount;
    }
    public void setGroupingInfomagion(int maxMemberCount,int miniMemberCount){
        this.maxMemberCount = maxMemberCount;
        this.miniMemberCount = miniMemberCount;
    }

    public void setDoGroupingFlag(){
        this.doGroupingFlag = true;
    }
    public void setGroupings(List<Student> groups){
        this.groupings = groups;
    }

    public int getMaxMemberCount(){return this.maxMemberCount;}
    public int getMiniMemberCount(){return this.miniMemberCount;}
    public boolean getDoGroupingFlag(){return this.doGroupingFlag;}
    public List<Student> getGroupings(){return this.groupings;}
    public int getTotalGroupCount(){return getGroupings().size();}

}
