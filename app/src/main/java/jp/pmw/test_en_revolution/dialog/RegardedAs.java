package jp.pmw.test_en_revolution.dialog;

import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import jp.pmw.test_en_revolution.AttendanceObject;
import jp.pmw.test_en_revolution.R;
import jp.pmw.test_en_revolution.confirm_class_plan.Student;

/**
 * Created by si on 2016/06/16.
 */
public class RegardedAs {
    //  エンティティー
    RegardedAsEntity mEntity;
    //
    StudentInfoDialogFragnemt mDialogFragment;

    RegardedAs(RegardedAsEntity entity,StudentInfoDialogFragnemt dialogFragnemt){
        this.mEntity = entity;
        this.mDialogFragment = dialogFragnemt;
        initLayout();
    }
    LinearLayout getRefardedAsAttendanceLl(){
        return this.mEntity.mRefardedAsAttendanceLl;
    }
    Button getRregardedAsAttendanceBtn(){
        return this.mEntity.mRregardedAsAttendanceBtn;
    }
    Button getRregardedAsNotAttendanceBtn(){
        return this.mEntity.mRregardedAsNotAttendanceBtn;
    }
    LinearLayout getRefardedAsLateLl(){
        return this.mEntity.mRefardedAsLateLl;
    }
    Button getRregardedAsLateBtn(){
        return this.mEntity.mRregardedAsLateBtn;
    }
    Button getRregardedAsNotLateBtn(){
        return this.mEntity.mRregardedAsNotLateBtn;
    }

    /**
     *  setRegardedAsAttendanceListenerメソッド
     *  初期レイアウト設定
     * */
    void initLayout(){
        //
        //setRegardedAsAttendanceListener();
        //
        //showRegardedAsLateListener();
        //  ACKによる出席かどうか
        boolean byACK = mDialogFragment.tapStudent.getAttendanceObject().getAttendanceByACK();
        if( byACK ){
            //  ACKによる出席の場合は、レイアウトを隠す.
            getRefardedAsAttendanceLl().setVisibility(View.GONE);
            getRefardedAsLateLl().setVisibility(View.GONE);
        }else{
            boolean lateState = mDialogFragment.tapStudent.getAttendanceObject().chkLateState();
            if( lateState ){
                //  遅刻状態
                showLateState();
                return;
            }

            boolean manualAttendanceState = mDialogFragment.tapStudent.getAttendanceObject().chkManualAttendanceState();
            if( manualAttendanceState ){
                //  手動出席状態
                showManualAttendanceState();
                return;
            }
        }
    }
    /**
     * Created by si on 2016/06/16.
     * showRegardedAsAttendanceLayoutメソッド
     * 出席とみなす＆出席とみなさないレイアウトを表示する.
     **/
    /*void setRegardedAsAttendanceListener(){
        //  出席とみなすレイアウト
        //  出席とみなすボタン
        getRregardedAsAttendanceBtn().setOnClickListener(mRregardedAsAttBtnListener);
        //  出席とみなさないボタン
        getRregardedAsNotAttendanceBtn().setOnClickListener(mRregardedAsAttBtnListener);
    }*/
    /**
     *  togleRegardedAsAttendanceBtnメソッド
     *  出席とみなす＆出席とみなさないボタンをトグル機能にします.
     * **/
    /*void togleRegardedAsAttendanceBtn(int resorceId){
        switch (resorceId) {
            case R.id.dialog_custom_regarded_as_attendance_btn:
                //  出席とみなすボタン
                getRregardedAsAttendanceBtn().setVisibility(View.INVISIBLE);
                //  出席とみなさないボタン
                getRregardedAsNotAttendanceBtn().setVisibility(View.VISIBLE);
                //  遅刻とみなす＆遅刻とみなさないレイアウト非表示
                getRefardedAsLateLl().setVisibility(View.INVISIBLE);
                //
                setDummyAttendanceTime();
                break;
            case R.id.dialog_custom_regarded_as_not_attendance_btn:
                //  出席とみなすボタン
                getRregardedAsAttendanceBtn().setVisibility(View.VISIBLE);
                //  出席とみなさないボタン
                getRregardedAsNotAttendanceBtn().setVisibility(View.INVISIBLE);
                //  遅刻とみなす＆遅刻とみなさないレイアウト表示
                getRefardedAsLateLl().setVisibility(View.VISIBLE);
                //
                setDummyAbsentTime();;
                break;
        }
    }*/
    /**
     * 出席とみなす＆出席とみなさないボタンのリスナー
     * */
    /*private View.OnClickListener mRregardedAsAttBtnListener = new View.OnClickListener() {
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.dialog_custom_regarded_as_attendance_btn:
                    //  出席とみなす
                    togleRegardedAsAttendanceBtn(R.id.dialog_custom_regarded_as_attendance_btn);
                    break;

                case R.id.dialog_custom_regarded_as_not_attendance_btn:
                    //  出席とみなさない
                    togleRegardedAsAttendanceBtn(R.id.dialog_custom_regarded_as_not_attendance_btn);
                    break;
            }
        }
    };*/
    /**
     * Created by si on 2016/06/16.
     * showRegardedAsLateLayoutメソッド
     * 遅刻とみなす＆遅刻とみなさないレイアウトを表示する.
     **/
    /*void showRegardedAsLateListener(){
        //  遅刻とみなすレイアウト
        //  遅刻とみなすボタン
        getRregardedAsLateBtn().setOnClickListener(mRregardedAsLateBtnListener);
        //  遅刻とみなさないボタン
        getRregardedAsNotLateBtn().setOnClickListener(mRregardedAsLateBtnListener);
    }*/
    /**
     *  togleRegardedAsAttendanceBtnメソッド
     *  遅刻とみなす＆遅刻とみなさないボタンをトグル機能にします.
     * **/
    /*void togleRegardedAsLateBtn(int resorceId){
        switch (resorceId) {
            //  遅刻とみなすボタンタップ
            case R.id.dialog_custom_regarded_as_late_btn:
                //  遅刻とみなすボタン
                getRregardedAsLateBtn().setVisibility(View.INVISIBLE);
                //  遅刻とみなさないボタン
                getRregardedAsNotLateBtn().setVisibility(View.VISIBLE);
                //  出席とみなすレイアウト非表示
                getRefardedAsAttendanceLl().setVisibility(View.INVISIBLE);
                //  遅刻
                setDummyLateTime();
                break;
            //  遅刻とみなさないボタンタップ
            case R.id.dialog_custom_regarded_as_not_late_btn:
                //  遅刻とみなすボタン
                getRregardedAsLateBtn().setVisibility(View.VISIBLE);
                //  遅刻とみなさないボタン
                getRregardedAsNotLateBtn().setVisibility(View.INVISIBLE);
                //  出席とみなすレイアウト非表示
                getRefardedAsAttendanceLl().setVisibility(View.VISIBLE);
                //  もどす
                setDummyAbsentTime();
                break;
        }
    }*/
    /**
     * 遅刻とみなす＆遅刻とみなさないボタンのリスナー
     * */
    /*private View.OnClickListener mRregardedAsLateBtnListener = new View.OnClickListener() {
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.dialog_custom_regarded_as_late_btn:
                    //  遅刻とみなす
                    togleRegardedAsLateBtn(R.id.dialog_custom_regarded_as_late_btn);
                    break;

                case R.id.dialog_custom_regarded_as_not_late_btn:
                    //  遅刻とみなさない
                    togleRegardedAsLateBtn(R.id.dialog_custom_regarded_as_not_late_btn);
                    break;
            }
        }
    };*/

    /**
     *  showLateStateメソッド
     *  遅刻状態レイアウトを表示する.
     * */
    void showLateState(){
        //  出席とみなすレイアウト消す
        getRefardedAsAttendanceLl().setVisibility(View.INVISIBLE);
        //  遅刻とみなすレイアウト表示
        getRefardedAsLateLl().setVisibility(View.VISIBLE);
        //  遅刻とみなすボタン非表示
        getRregardedAsLateBtn().setVisibility(View.INVISIBLE);
        //  遅刻だが、欠席とみなすボタン表示
        getRregardedAsNotLateBtn().setVisibility(View.VISIBLE);
    }

    /**
     * showManualAttendanceStateメソッド
     * 手動出席状態レイアウトを表示する.
     * */
    void showManualAttendanceState(){
        //  出席とみなすレイアウト消す
        getRefardedAsAttendanceLl().setVisibility(View.VISIBLE);
        //  遅刻とみなすレイアウト表示
        getRefardedAsLateLl().setVisibility(View.INVISIBLE);
        //  出席とみなすボタン非表示
        getRregardedAsAttendanceBtn().setVisibility(View.INVISIBLE);
        //  出席だが、欠席とみなすボタン表示
        getRregardedAsNotAttendanceBtn().setVisibility(View.VISIBLE);
    }



    void setDummyAttendanceTime(){
        //
        mDialogFragment.tapStudent.getAttendanceObject().setManualRequestAttendance(1);
        //
        mDialogFragment.tapStudent.getAttendanceObject().setAttendanceTime("");
        //
        mDialogFragment.redrawFrame();
    }

    void setDummyLateTime(){
        //
        mDialogFragment.tapStudent.getAttendanceObject().setManualRequestAttendance(2);
        //
        mDialogFragment.tapStudent.getAttendanceObject().setAttendanceTime("");
        //
        mDialogFragment.redrawFrame();
    }


    void setDummyAbsentTime(){
        mDialogFragment.tapStudent.getAttendanceObject().setManualRequestAttendance(0);
        mDialogFragment.tapStudent.getAttendanceObject().setAttendanceTime(null);
        //
        mDialogFragment.redrawFrame();
    }


}
