package jp.pmw.test_en_revolution.attendee;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.List;

import jp.pmw.test_en_revolution.AttendanceObject;
import jp.pmw.test_en_revolution.MainActivity;
import jp.pmw.test_en_revolution.ManulReason;
import jp.pmw.test_en_revolution.R;
import jp.pmw.test_en_revolution.StudentObject;
import jp.pmw.test_en_revolution.TransmitStateObject;
import jp.pmw.test_en_revolution.dialog.AttendanceChangeStatus;
import jp.pmw.test_en_revolution.dialog.ManualRequestAttendanceAsyncTask;

/**
 * Created by si on 2016/12/15.
 */

public class AttendanceChangeStatusAdapter extends ArrayAdapter<AttendanceChangeStatus> {
    private LayoutInflater mLayoutInflater;
    public AttendanceChangeStatusAdapter(Context context, int resourceId, AttendanceChangeStatus[] items) {
        super(context, resourceId, items);
        mLayoutInflater =   (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    /**
     * changeAttendanceStatusメソッド
     * 選択された学生の出欠状態を変更する
     * @param   StudentObject   so      学生
     * @author Ito Shota
     * @since  2016/12/15
     **/
    public void changeAttendanceStatus(AttendanceChangeStatus so){
        for( int i = 0; i < getCount(); i++ ){
            AttendanceChangeStatus acs = getItem(i);
            if( acs.getAttendanceObject().getAttendanceId()
                    == so.getAttendanceObject().getAttendanceId() ){
                if( acs.mTapNoResponseStatus == true && acs.mTapForgotStatus == false ){
                    new ManualRequestAttendanceAsyncTask( so, ManualRequestAttendanceAsyncTask.NO_RESPONSE ).execute();
                    so.getAttendanceObject().toStateManualAttendance();
                }else if ( acs.mTapNoResponseStatus == false && acs.mTapForgotStatus == true ){
                    new ManualRequestAttendanceAsyncTask( so, ManualRequestAttendanceAsyncTask.FORGOT ).execute();
                    so.getAttendanceObject().toStateForgotManualAttendance();
                }else{
                    new ManualRequestAttendanceAsyncTask( so, ManualRequestAttendanceAsyncTask.CANCEL ).execute();
                    so.getAttendanceObject().toStateManualAbsent();
                }
                this.notifyDataSetChanged();
            }
        }
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        View view = convertView;
        if(view == null){
            view = mLayoutInflater.inflate(R.layout.row_attendance_change_status, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }else{
            holder = (ViewHolder)view.getTag();
        }
        AttendanceChangeStatus acs = getItem( position );
        setItem( holder, acs );
        setTag( holder, acs );
        setTapColor( holder, acs );
        setBtnLayout( holder, acs );
        setDoNotShowForgotLayout( holder, acs );
        return view;
    }
    /**
     * setItemメソッド
     * 学生情報をセットする.
     * @param   ViewHolder      holder  ビューホルダー
     * @param   StudentObject   so      学生
     * @author Ito Shota
     * @since  2016/08/07
     **/
    void setItem(ViewHolder holder, AttendanceChangeStatus so){
        holder.mStudentIdNumberTv.setText(so.getStudentIdNumber());
        holder.mFuriganaTv.setText(so.getFurigana());
        holder.mFullNameTv.setText(so.getFullName());
    }
    /**
     * setTagメソッド
     * ボタンにタグをセットする
     * @param   ViewHolder      holder  ビューホルダー
     * @param   StudentObject   so      学生
     * @author Ito Shota
     * @since  2016/08/07
     **/
    void setTag(ViewHolder holder, AttendanceChangeStatus acs) {
        //  無反応
        holder.mNoResponseTv.setTag( acs );
        //  ソ忘れ
        holder.mForgotTv.setTag( acs );
    }
    /**
     * setTapColorメソッド
     * タップされた箇所はオレンジにする
     * @param   ViewHolder      holder  ビューホルダー
     * @param   StudentObject   so      学生オブジェクト
     * @author Ito Shota
     * @since  2016/12/15
     **/
    void setTapColor(ViewHolder holder, AttendanceChangeStatus so){
        MainActivity ma = (MainActivity)getContext();
        TransmitStateObject tso = ma.getClassObject().getTransmitStateObject();
        int color = so.getAttendanceObject().getAttendanceStateColor(ma, tso);
        holder.mStudentIdNumberTv.setTextColor( color );
        holder.mFuriganaTv.setTextColor( color );
        holder.mFullNameTv.setTextColor( color );
    }
    /**
     * setBtnLayoutメソッド
     * ボタンレイアウトをセットします.
     * @param   ViewHolder      holder  ビューホルダー
     * @param   StudentObject   so      学生オブジェクト
     * @author Ito Shota
     * @since  2016/12/15
     **/
    void setBtnLayout(ViewHolder holder, AttendanceChangeStatus acs){
            if( acs.mTapNoResponseStatus == true && acs.mTapForgotStatus == false ){
                holder.mNoResponseIv.setVisibility(View.VISIBLE);
                holder.mNoResponseTv.setTextColor(this.getContext().getResources().getColor(R.color.forestGreen));
                holder.mForgotIv.setVisibility(View.INVISIBLE);
                holder.mForgotTv.setTextColor(this.getContext().getResources().getColor(R.color.black));
            }else if ( acs.mTapNoResponseStatus == false && acs.mTapForgotStatus == true ){
                holder.mNoResponseIv.setVisibility(View.INVISIBLE);
                holder.mNoResponseTv.setTextColor(this.getContext().getResources().getColor(R.color.black));
                holder.mForgotIv.setVisibility(View.VISIBLE);
                holder.mForgotTv.setTextColor(this.getContext().getResources().getColor(R.color.forestGreen));
            }else {
                holder.mNoResponseIv.setVisibility(View.INVISIBLE);
                holder.mNoResponseTv.setTextColor(this.getContext().getResources().getColor(R.color.black));
                holder.mForgotIv.setVisibility(View.INVISIBLE);
                holder.mForgotTv.setTextColor(this.getContext().getResources().getColor(R.color.black));
            }
        setRequestBtnListener( holder );
    }
    /**
     * setRequestBtnListenerメソッド
     * ボタンリスナーをセット.
     * @param   ViewHolder      holder  ビューホルダー
     * @author Ito Shota
     * @since  2016/12/15
     **/
    void setRequestBtnListener(ViewHolder holder){
        holder.mNoResponseTv.setOnClickListener(new NoResponseBtnListener());
        holder.mForgotTv.setOnClickListener(new ForgotBtnListener());
    }
    //  SonoRIs無反応
    public class NoResponseBtnListener implements View.OnClickListener {
        @Override
        public void onClick(View v){
            AttendanceChangeStatus acs = (AttendanceChangeStatus)v.getTag();
            acs.mTapForgotStatus = false;
            acs.mTapNoResponseStatus = !acs.mTapNoResponseStatus;
            changeAttendanceStatus( acs );
        }
    }
    //  SonoRIs忘れ
    public class ForgotBtnListener implements View.OnClickListener {
        @Override
        public void onClick(View v){
            AttendanceChangeStatus acs = (AttendanceChangeStatus)v.getTag();
            acs.mTapForgotStatus = !acs.mTapForgotStatus;
            acs.mTapNoResponseStatus = false;
            changeAttendanceStatus( acs );
        }
    }
    /**
     * setDoNotShowForgotLayoutメソッド
     * ACK応答が一度でもあった場合、
     * 「ソ忘れ」を表示しない
     * @param   ViewHolder      holder  ビューホルダー
     * @param   AttendanceChangeStatus      acs  一括変更対象学生
     * @author Ito Shota
     * @since  2016/12/21
     **/
    void setDoNotShowForgotLayout(ViewHolder holder, AttendanceChangeStatus acs ){
        boolean ackResponse = acs.getAttendanceObject().getReturndedResponse();
        if( ackResponse ){
            holder.mForgotTv.setVisibility(View.INVISIBLE);
        }else{
            holder.mForgotTv.setVisibility(View.VISIBLE);
        }
    }

    /**
     *  ViewHolderクラス
     * */
    public class ViewHolder {
        LinearLayout mLinearLayout;
        TextView mStudentIdNumberTv;
        TextView mFuriganaTv;
        TextView mFullNameTv;
        LinearLayout mUnSelectedLl;
        ImageView mNoResponseIv;
        TextView mNoResponseTv;
        ImageView mForgotIv;
        TextView mForgotTv;
        public ViewHolder(View view) {
            this.mLinearLayout      =   (LinearLayout) view.findViewById(R.id.row_attendance_change_status_ll);
            this.mStudentIdNumberTv =   (TextView) view.findViewById(R.id.row_attendance_change_status_student_id_number_tv);
            this.mFuriganaTv        =   (TextView) view.findViewById(R.id.row_attendance_change_status_furigana_tv);
            this.mFullNameTv        =   (TextView) view.findViewById(R.id.row_attendance_change_status_full_name_tv);
            this.mUnSelectedLl      =   (LinearLayout) view.findViewById(R.id.row_attendance_change_status_unsettled_ll);
            this.mNoResponseIv      =   (ImageView) view.findViewById(R.id.row_attendance_change_status_no_response_iv);
            this.mNoResponseTv      =   (TextView) view.findViewById(R.id.row_attendance_change_status_no_response_tv);
            this.mForgotIv          =   (ImageView) view.findViewById(R.id.row_attendance_change_status_forgot_iv);
            this.mForgotTv          =   (TextView) view.findViewById(R.id.row_attendance_change_status_forgot_btn);
        }
    }
}