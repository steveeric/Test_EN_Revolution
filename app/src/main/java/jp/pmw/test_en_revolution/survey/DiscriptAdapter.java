package jp.pmw.test_en_revolution.survey;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import jp.pmw.test_en_revolution.R;

/**
 * Created by si on 2017/01/26.
 */

public class DiscriptAdapter extends ArrayAdapter<Discript> {
    private LayoutInflater mFactory;
    private int mItemLayoutResource;
    public DiscriptAdapter(Context context, int resource, Discript[] objects) {
        super(context, resource, objects);
        mFactory = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mItemLayoutResource = resource;
    }
    public void replaceDiscriptAnswer( Discript[] newDiscripts ){
        int total = this.getCount();
        for( int i = 0; i < total; i++ ){
            Discript d = getItem(i);
            for( int j = 0; j < newDiscripts.length; j++ ){
                Discript newd = newDiscripts[j];
                if( d.mStudentIdNumber.equals( newd.mStudentIdNumber ) ){
                    getItem(i).mDiscript = newd.mDiscript;
                    break;
                }
            }
        }
        this.notifyDataSetChanged();
    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder holder;
        if (view != null) {
            holder = (ViewHolder) view.getTag();
        } else {
            view = mFactory.inflate(mItemLayoutResource, parent, false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }
        Discript d = getItem(position);
        holder.mStudentInfoTv.setText( d.mStudentIdNumber + " " + d.mFullName + "(" +d.mFurigana+ ")" );
        holder.mAnswerTv.setText(d.mDiscript);
        return view;
    }
    static class ViewHolder {
        @BindView(R.id.row_survey_discript_student_info_tv) TextView mStudentInfoTv;
        @BindView(R.id.row_survey_discript_answertv) TextView mAnswerTv;
        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}