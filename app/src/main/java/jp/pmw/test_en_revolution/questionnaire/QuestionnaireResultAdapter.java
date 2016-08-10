package jp.pmw.test_en_revolution.questionnaire;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import jp.pmw.test_en_revolution.R;
import jp.pmw.test_en_revolution.StudentObject;

/**
 * Created by si on 2016/08/07.
 * QuestionnaireResultAdapterクラス
 * クリッカーの回答者アダプター
 */
public class QuestionnaireResultAdapter extends ArrayAdapter<StudentObject> {
    private LayoutInflater  mLayoutInflater;
    String  mColor;
    public QuestionnaireResultAdapter(Context context, int resourceId, String color, StudentObject[] items) {
        super(context, resourceId, items);
        mLayoutInflater =   (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mColor          =   color;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        View view = convertView;
        if(view == null){
            view = mLayoutInflater.inflate(R.layout.row_roster_item_4, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }else{
            holder = (ViewHolder)view.getTag();
        }
        StudentObject so = getItem( position );
        setItem( holder, so );
        setItemColor( holder );
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
    void setItem(ViewHolder holder,StudentObject so){
        holder.orijinalStudentIdTextView.setText(so.getStudentIdNumber());
        holder.furiganaTextView.setText(so.getFurigana());
        holder.fullNameTextView.setText(so.getFullName());
    }
    /**
     * setItemColorメソッド
     * 学生情報の文字列に色をセットする.(「はい」は青色,「いいえ」は赤色)
     * クリッカー回答結果の円グラフに合わせております.
     * @param   ViewHolder      holder  ビューホルダー
     * @author Ito Shota
     * @since  2016/08/10
     **/
    void setItemColor(ViewHolder holder){
        holder.orijinalStudentIdTextView.setTextColor(Color.parseColor(mColor));
        holder.furiganaTextView.setTextColor(Color.parseColor(mColor));
        holder.fullNameTextView.setTextColor(Color.parseColor(mColor));
    }

    public class ViewHolder {
        //
        LinearLayout mLeftLl;
        //      1番目信号
        TextView firstSignalTextView;
        //      2番目信号
        TextView secondSignalTextView;
        //      3番目信号
        TextView thirdSignalTextView;
        //      4番目信号
        TextView fourthSignalTextView;

        //      学籍番号
        TextView orijinalStudentIdTextView;
        //      フリガナ
        TextView furiganaTextView;
        //      氏名
        TextView fullNameTextView;
        TextView attendanceStateColorTextView;
        LinearLayout subContentLinearLayout;
        ImageView forgotESLTextView;
        ImageView messageTextView;

        public ViewHolder(View view) {
            this.mLeftLl = (LinearLayout)view.findViewById(R.id.roster_left_ll);
            this.mLeftLl.setVisibility(View.GONE);
            //      1番目信号
            this.firstSignalTextView = (TextView) view.findViewById(R.id.roster_first_signal_textView);
            this.firstSignalTextView.setVisibility(View.GONE);
            //      2番目信号
            this.secondSignalTextView = (TextView) view.findViewById(R.id.roster_second_signal_textView);
            this.secondSignalTextView.setVisibility(View.GONE);
            //      3番目信号
            this.thirdSignalTextView = (TextView) view.findViewById(R.id.roster_third_signal_textView);
            this.thirdSignalTextView.setVisibility(View.GONE);
            //      4番目信号
            this.fourthSignalTextView = (TextView) view.findViewById(R.id.roster_fourth_signal_textView);
            this.fourthSignalTextView.setVisibility(View.GONE);

            this.orijinalStudentIdTextView = (TextView) view.findViewById(R.id.roster_orijinal_student_id_textView);
            this.furiganaTextView = (TextView) view.findViewById(R.id.roster_furigana_textView);
            this.fullNameTextView = (TextView) view.findViewById(R.id.roster_full_name_textView);
            //this.attendanceStateColorTextView = (TextView) view.findViewById(R.id.roster_attendance_state_color_textView);
            //this.subContentLinearLayout = (LinearLayout) view.findViewById(R.id.roster_attendance_sub_content_linearLayout);
            this.forgotESLTextView = (ImageView) view.findViewById(R.id.roster_forgot_esl_textView);
            this.forgotESLTextView.setVisibility(View.GONE);
            this.messageTextView = (ImageView) view.findViewById(R.id.roster_have_messge_textView);
            this.messageTextView.setVisibility(View.GONE);
        }
    }
}