package jp.pmw.test_en_revolution.questionnaire;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import jp.pmw.test_en_revolution.R;

/**
 * Created by scr on 2014/12/25.
 * QuestionnaireCustomAdapterクラス
 * クリッカー問題のアダプターです.
 */
public class QuestionnaireCustomAdapter extends ArrayAdapter<Question> {
    private LayoutInflater mFactory;
    private int mItemLayoutResource;
    public QuestionnaireCustomAdapter(Context context, int resource, List<Question> objects) {
        super(context, resource, objects);
        mFactory = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mItemLayoutResource = resource;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        View view = convertView;
        //  クリッカーオブジェクト
        Question question = getItem(position);
        //  VIew生成
        if (view == null) {
            view = mFactory.inflate(R.layout.row_question_item, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder)view.getTag();
        }
        //  タイトルをセットする.
        setTitle( position, question, holder );

        //
        setStatus( holder, question );

        //  既に処理済みにする.
        setAlreayProcessed( question, holder );

        //  クリッカー問題
        List<Ask> asks = question.getAsks();
        String strClikcerQuestion = asks.get(0).getAskContent();
        holder.mClikcerQuestionTv.setText( strClikcerQuestion );

        return view;
    }
    /**
     * setTitle
     * タイトルをセットする.
     * @param   int             positoion 選択インデックス
     * @param   Question        question  質問
     * @param   ViewHolder      holder    ビューホルダー
     * @author Ito Shota
     * @since  2016/08/10
     **/
    void setTitle(int position, Question question,ViewHolder holder){
        int index = position;
        String strTitleNumber = getContext().getResources().getString(R.string.questionnaire_topic) + index + " " +question.getQuestionTitle();
        holder.mTitleNumberTv.setText( strTitleNumber );
    }
    /**
     * setStatusメソッド
     * 進行状況テキストビューの色をセットする.
     * @param   ViewHolder  holder      ホルダー
     * @param   String      time        時間文字列
     * @author Ito Shota
     * @since  2016/08/10
     **/
    void setStatus(ViewHolder holder, Question question){
        String firstEndTime = question.getQuestionEndDateTime();
        setChangeStataus( holder.mFirstStatusTv,  firstEndTime);
        String secondEndTime = question.getQuestionCheckEndDateTime();
        setChangeStataus( holder.mSecondStatusTv,  secondEndTime);
        String thirdEndTime = question.getQuestionResultEndDateTime();
        setChangeStataus( holder.mThirdStatusTv,  thirdEndTime);
    }
    /**
     * setChangeStatausメソッド
     * 進行状況テキストビューの色をセットする.
     * @param   TextView    tv          テキストビュー
     * @param   String      time        時間文字列
     * @author Ito Shota
     * @since  2016/08/10
     **/
    void setChangeStataus(TextView tv, String time){
        if( time != null ){
            tv.setBackgroundResource(R.color.gold);
        }else{
            tv.setBackgroundResource(R.color.white);
        }
    }
    /**
     * setAlreayProcessed
     * 既に処理済みにする.
     * @param   Question        question  質問
     * @param   ViewHolder      holder    ビューホルダー
     * @author Ito Shota
     * @since  2016/08/10
     **/
    void setAlreayProcessed(Question question,ViewHolder holder){
        //  クリッカー問題送信開始日時
        String startTime = question.getQuesiontStartDateTime();
        //  クリッカー回答送信日時
        String endTime = question.getQuestionResultEndDateTime();
        if( startTime != null && endTime != null ){
            //  クリッカー済み
            holder.mAlreadyTv.setVisibility(View.VISIBLE);
            holder.mProgressStatusLl.setVisibility(View.GONE);
            holder.mTitleNumberTv.setBackgroundResource(R.color.limeGreen);
        }else if(startTime == null && endTime == null){
            //  クリッカー未送信
            holder.mAlreadyTv.setVisibility(View.GONE);
            holder.mProgressStatusLl.setVisibility(View.VISIBLE);
            holder.mProgressStatusLl.setBackgroundResource(R.color.limeGreen);
            holder.mTitleNumberTv.setBackgroundResource(R.color.limeGreen);
        }else{
            //  クリッカー実施中
            holder.mAlreadyTv.setVisibility(View.GONE);
            holder.mProgressStatusLl.setVisibility(View.VISIBLE);
            holder.mProgressStatusLl.setBackgroundResource(R.color.gold);
            holder.mTitleNumberTv.setBackgroundResource(R.color.gold);
        }
    }
    //以下2つをfalseで返すと選択が行えなくなる
    public boolean areAllItemsEnabled() {
        return true;
    }
    public boolean isEnabled(int position) {
        return true;
    }
    /**
     *  ViewHolderクラス
     * */
    public class ViewHolder {
        public LinearLayout mProgressStatusLl;
        //  一つ目の状況
        public TextView mFirstStatusTv;
        //  二つ目の状況
        public TextView mSecondStatusTv;
        //  三つ目の状況
        public TextView mThirdStatusTv;
        //      クリッカー問題番号
        public TextView mTitleNumberTv;
        //      済（クリッカー済)
        public TextView mAlreadyTv;
        //      クリッカー問
        public TextView mClikcerQuestionTv;
        public ViewHolder(View view) {
            this.mProgressStatusLl = (LinearLayout) view.findViewById(R.id.row_question_question_progress_status_ll);
            this.mFirstStatusTv     =   (TextView) view.findViewById(R.id.row_question_question_first_status_tv);
            this.mSecondStatusTv    =   (TextView) view.findViewById(R.id.row_question_question_second_status_tv);
            this.mThirdStatusTv     =   (TextView) view.findViewById(R.id.row_question_question_third_status_tv);
            this.mTitleNumberTv = (TextView) view.findViewById(R.id.row_question_question_index_textView);
            this.mAlreadyTv = (TextView) view.findViewById(R.id.row_question_question_already_tv);
            this.mClikcerQuestionTv = (TextView) view.findViewById(R.id.row_clikcer_question_textView);
        }
    }
}
