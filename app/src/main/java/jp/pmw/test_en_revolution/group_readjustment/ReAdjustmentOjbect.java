package jp.pmw.test_en_revolution.group_readjustment;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by si on 2016/05/11.
 */
public class ReAdjustmentOjbect {
    /**
     *  グルーピング授業かどうかを表しております.
     *  ■not_groupingが「null」の場合
     *  グルーピング授業になります.
     *
     *  ■not_groupingが「null」でない場合
     *  通常授業になります.
     * */
    @SerializedName("not_grouping")
    public String notGrouping;
    public String getNotGrouping(){return this.notGrouping;}


    /**
     * グループ調整不可メッセージ
     * ■unnableMessageが「null」の場合
     *  グループ調整実行可能です.
     *  (全グループがグループ最小人数を満たしている場合もこちらに属します)
     * ■unnableMessageが「null」でない場合
     *  グループ調整実行不可能です.
     *  現在の出席者数では、グループ調整を行うことが不可能なためです.
     * */
    @SerializedName("unable_message")
    public String unnableMessage;
    public String getUnnableMessage(){return this.unnableMessage;}
    /**
     * グループ調整実行不可能か調べる.
     * @return {@code false} グループ調整可能 {@code true} グループ調整不可能
     * **/
    public boolean chkCanNotAdjustment(){
        if( unnableMessage != null ){
            return true;
        }
        return false;
    }


    /**
     * メッセージ (グループ調整が必要な場合に使用します)
     * 例 : １人グループ : 3人
     *      ２人グループ : 1人
     *      etc...
     * */
    @SerializedName("messages")
    public List<AllGroupState> messages;
    public List<AllGroupState> getMessages(){return messages;}

    //  座席移動オブジェクト
    @SerializedName("moveds")
    public List<Moved> moveds;
    public List<Moved>getMoveds(){return moveds;}

    //  全グループ数の状態
    @SerializedName("all_group_states")
    public List<AllGroupState> allGroupStates;
    public List<AllGroupState> getAllGroupStates(){return allGroupStates;}

    //  一グループ最大数のグループ数
    @SerializedName("maximum_group_count")
    public String mMaximumGroupCount;
    public String getMaximumGroupCount(){return this.mMaximumGroupCount;}
    //  一グループ最小数のグループ数
    @SerializedName("minimum_group_count")
    public String mMinimumGroupCount;
    public String getMinimumGroupCount(){return this.mMinimumGroupCount;}
    //  それ以外のグループ数
    @SerializedName("do_not_condition_count_str")
    public String mStrDoNotConditionCount;
    public String getStrDoNotConditionCount(){return this.mStrDoNotConditionCount;}
    //  それ以外のグループ数
    @SerializedName("do_not_condition_count")
    public int mDoNotConditionCount;
    public int getDoNotConditionCount(){return this.mDoNotConditionCount;}

    /**
     * グループ調整実行済みかを調べる.
     * @return {@code false} グループ調整実行前 {@code true} グループ調整実行後.
     * **/
    public boolean chkProcessdGroupAdjust(){
        if( moveds != null ){
            return true;
        }
        return false;
    }

    /**
     * グループ調整が必要かどうかを返します.
     * @return {@code false} グループ調整を行う必要はありません,{@code true} グループ調整を行う必要があります.
     * */
    public boolean getDoingReAdjustmet(){
        boolean doing =  false;
        /*if(msObject.getMessages() != null){
            doing = true;
        }*/
        if( messages != null ){
            doing = true;
        }
        return doing;
    }

    /**
     * グループ調整のため座席移動が必要な学生全員に
     * 移動先座席を伝え終えたかどうかを返します.
     * @return {@code false} 全員に伝え終えてません {@code true} 全員に伝え終えました.
     * */
    public boolean toldToMoveSeatsToEveryone(){

        if( moveds == null ){
            return false;
        }
        //  伝達済み数
        int toldCount = 0;
        for( int i = 0; i < moveds.size(); i++ ){
            if(moveds.get(i).getContactDateTime() != null){
                //  伝達済み
                ++toldCount;
            }
        }
        if( toldCount == moveds.size() ){
            //  全員に伝達済みです!
            return true;
        }

        return false;
    }



}
