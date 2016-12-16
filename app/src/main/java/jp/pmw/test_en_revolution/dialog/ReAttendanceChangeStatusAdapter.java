package jp.pmw.test_en_revolution.dialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import jp.pmw.test_en_revolution.MainActivity;
import jp.pmw.test_en_revolution.R;
import jp.pmw.test_en_revolution.TransmitStateObject;

/**
 * Created by si on 2016/12/16.
 * 在室確認赤外線に対してACKがなかった学生のアダプター
 */

public class ReAttendanceChangeStatusAdapter extends ArrayAdapter<ReAttendanceNackChangeStatus> {
    private LayoutInflater mLayoutInflater;

    public ReAttendanceChangeStatusAdapter(Context context, int resourceId, ReAttendanceNackChangeStatus[] items) {
        super(context, resourceId, items);
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        View view = convertView;
        if (view == null) {
            view = mLayoutInflater.inflate(R.layout.row_attendance_change_status, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        ReAttendanceNackChangeStatus acs = getItem(position);
        setItem(holder, acs);
        setTag(holder, acs);
        setTapColor(holder, acs);
        setBtnLayout(holder, acs);
        return view;
    }
    /**
     * changeAttendanceStatusメソッド
     * 選択された学生の出欠状態を変更する
     * @param   StudentObject   so      学生
     * @author Ito Shota
     * @since  2016/12/15
     **/
    public void changeAttendanceStatus(ReAttendanceNackChangeStatus so){
        for( int i = 0; i < getCount(); i++ ){
            ReAttendanceNackChangeStatus acs = getItem(i);
            if( acs.getAttendanceObject().getAttendanceId()
                    == so.getAttendanceObject().getAttendanceId() ){
                this.notifyDataSetChanged();
            }
        }
    }
    /**
     * setItemメソッド
     * 学生情報をセットする.
     *
     * @param ViewHolder    holder  ビューホルダー
     * @param StudentObject so      学生
     * @author Ito Shota
     * @since 2016/08/07
     **/
    void setItem(ViewHolder holder, ReAttendanceNackChangeStatus so) {
        holder.mStudentIdNumberTv.setText(so.getStudentIdNumber());
        holder.mFuriganaTv.setText(so.getFurigana());
        holder.mFullNameTv.setText(so.getFullName());
    }

    /**
     * setTagメソッド
     * ボタンにタグをセットする
     *
     * @param ViewHolder    holder  ビューホルダー
     * @param StudentObject so      学生
     * @author Ito Shota
     * @since 2016/08/07
     **/
    void setTag(ViewHolder holder, ReAttendanceNackChangeStatus acs) {
        holder.mNoResponseTv.setTag(acs);
        holder.mForgotTv.setTag(acs);
    }

    /**
     * setTapColorメソッド
     * タップされた箇所はオレンジにする
     *
     * @param ViewHolder    holder  ビューホルダー
     * @param StudentObject so      学生オブジェクト
     * @author Ito Shota
     * @since 2016/12/15
     **/
    void setTapColor(ViewHolder holder, ReAttendanceNackChangeStatus so) {
        MainActivity ma = (MainActivity) getContext();
        TransmitStateObject tso = ma.getClassObject().getTransmitStateObject();
        int color = so.getAttendanceObject().getAttendanceStateColor(ma, tso);
        holder.mStudentIdNumberTv.setTextColor(color);
        holder.mFuriganaTv.setTextColor(color);
        holder.mFullNameTv.setTextColor(color);
    }

    /**
     * setBtnLayoutメソッド
     * ボタンレイアウトをセットします.
     *
     * @param ViewHolder    holder  ビューホルダー
     * @param StudentObject so      学生オブジェクト
     * @author Ito Shota
     * @since 2016/12/15
     **/
    void setBtnLayout(ViewHolder holder, ReAttendanceNackChangeStatus acs) {
        //
        holder.mForgotTv.setText("在室");

        if( acs.mBeInRoom ){
            holder.mForgotIv.setVisibility(View.VISIBLE);
            holder.mForgotTv.setTextColor(this.getContext().getResources().getColor(R.color.forestGreen));
        }else{
            holder.mForgotIv.setVisibility(View.INVISIBLE);
            holder.mForgotTv.setTextColor(this.getContext().getResources().getColor(R.color.black));
        }

        //  不要のため非表示
        holder.mNoResponseIv.setVisibility(View.INVISIBLE);
        holder.mNoResponseTv.setVisibility(View.INVISIBLE);

        setRequestBtnListener(holder);
    }


    /**
     * setRequestBtnListenerメソッド
     * ボタンリスナーをセット.
     *
     * @param ViewHolder holder  ビューホルダー
     * @author Ito Shota
     * @since 2016/12/15
     **/
    void setRequestBtnListener(ViewHolder holder) {
        holder.mForgotTv.setOnClickListener(new BeInRoomBtnListener());
    }
    //  在室
    public class BeInRoomBtnListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            ReAttendanceNackChangeStatus acs = (ReAttendanceNackChangeStatus) v.getTag();
            acs.mBeInRoom = !acs.mBeInRoom;
            String attendanceId = acs.getAttendanceObject().getAttendanceId();
            int parameta = ReAttendanceChangeStatusDialogFragment.CANCEL;
            if( acs.mBeInRoom ){
                parameta = ReAttendanceChangeStatusDialogFragment.REQUEST;
            }
            changeAttendanceStatus( acs );
            new ReAttendanceNackChangeStatusAsyncTask( attendanceId, parameta ).execute();
        }
    }

    /**
     * ViewHolderクラス
     */
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
            this.mLinearLayout = (LinearLayout) view.findViewById(R.id.row_attendance_change_status_ll);
            this.mStudentIdNumberTv = (TextView) view.findViewById(R.id.row_attendance_change_status_student_id_number_tv);
            this.mFuriganaTv = (TextView) view.findViewById(R.id.row_attendance_change_status_furigana_tv);
            this.mFullNameTv = (TextView) view.findViewById(R.id.row_attendance_change_status_full_name_tv);
            this.mUnSelectedLl = (LinearLayout) view.findViewById(R.id.row_attendance_change_status_unsettled_ll);
            this.mNoResponseIv = (ImageView) view.findViewById(R.id.row_attendance_change_status_no_response_iv);
            this.mNoResponseTv = (TextView) view.findViewById(R.id.row_attendance_change_status_no_response_tv);
            this.mForgotIv = (ImageView) view.findViewById(R.id.row_attendance_change_status_forgot_iv);
            this.mForgotTv = (TextView) view.findViewById(R.id.row_attendance_change_status_forgot_btn);
        }
    }
}
