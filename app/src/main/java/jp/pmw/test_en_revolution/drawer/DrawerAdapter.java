package jp.pmw.test_en_revolution.drawer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import java.util.List;

/**
 * Created by scr on 2014/11/26.
 */
public class DrawerAdapter extends ArrayAdapter<DrawerBindData> {
    private LayoutInflater mFactory;
    private int mItemLayoutResource;
    public DrawerAdapter(Context context, int resource, List<DrawerBindData> objects) {
        super(context, resource, objects);
        mFactory = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mItemLayoutResource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       final DrawerLayout view;
        if (convertView == null) {
            // Viewがなかったら生成
            view = (DrawerLayout) mFactory.inflate(mItemLayoutResource, null);
        } else {
            view = (DrawerLayout) convertView;
        }
        DrawerBindData item = getItem(position);
        view.bindView(item);
        /*if(item.mTapFlag == false){
            view.setMinimumHeight(10);
        }else{

        }*/
        return view;
    }

    @Override
    public boolean isEnabled(int position) {
        DrawerBindData item = this.getItem(position);
        return item.mTapFlag;
    }
}