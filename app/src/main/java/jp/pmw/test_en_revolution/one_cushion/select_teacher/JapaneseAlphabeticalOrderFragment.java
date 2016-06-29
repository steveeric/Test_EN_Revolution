package jp.pmw.test_en_revolution.one_cushion.select_teacher;

import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.BaseAdapter;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import jp.pmw.test_en_revolution.R;
import jp.pmw.test_en_revolution.config.URL;
/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link JapaneseAlphabeticalOrderFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link JapaneseAlphabeticalOrderFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
/**
 *
 * Created by scr on 2014/12/13.
 * JapaneseAlphabeticalOrderFragmentクラス
 * ５０音順を選択する画面です.
 * 教員の苗字を選択します.
 * @author Shota Ito
 * @version 1.0
 */
public class JapaneseAlphabeticalOrderFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    /**
     * 50音順にボタンを並べるグリッド
     * **/
    public GridView mGridLayout;

    //ボタンが全て敷き詰められているかを表すフラグ
    private boolean mSetButtonFlag = false;

    private int screenHeight = 0;
    private int screenWidth = 0;

    private static final int FONT_SIZE = 30;
    /*50音順のボタンの高さ*/
    //private static final int INITIAL_BUTTON_HEIGHT = 155;
    private static final int INITIAL_BUTTON_HEIGHT = 140;
    /*50音順のボタンの横幅*/
    private static final int INITIAL_BUTTON_WIDTH = 80;

    public JapaneseAlphabeticalOrderFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static JapaneseAlphabeticalOrderFragment newInstance(int sectionNumber) {
        JapaneseAlphabeticalOrderFragment fragment = new JapaneseAlphabeticalOrderFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_japanese_alphabetical_order, container, false);
        setAppVersionName(view);
        return view;
    }
    /**
     *  setAppVersionNameメソッド
     * */
    void setAppVersionName(View view){
        TextView tv = (TextView)view.findViewById(R.id.japanese_alphabetical_version_tv);
        String versionName = this.getString(R.string.app_version)+" ("+ URL.S_IP_ADDRESS+"/"+URL.S_WEB_APP_NAME+")";
        tv.setText(versionName);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.mGridLayout = (GridView)this.getActivity().findViewById(R.id.japanese_alphabetical_grid);
    }

    private static final int ROW = 5;
    private static final int COLUMN = 10;
    @Override
    public void onResume(){
        super.onResume();
        JapaneseAlphabeticalOrderFragmentActivity activity = (JapaneseAlphabeticalOrderFragmentActivity) this.getActivity();

        WindowManager wm = activity.getWindowManager();
        Display disp = wm.getDefaultDisplay();
        int width  = disp.getWidth();
        int height = disp.getHeight();
        screenHeight        = height;
        screenWidth         = width;
        int heightBtn    = screenWidth / (ROW);
        int widthBtn   = screenHeight / COLUMN;

        ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < JapaneseAlphabeticalOrder.ALPHABET_BLOCK.length; i++) {
            for (int j = 0; j < JapaneseAlphabeticalOrder.ALPHABET_BLOCK[i].length; j++) {
                //ボタン一つ一つに文字を入れる.
                String str = JapaneseAlphabeticalOrder.ALPHABET_BLOCK[i][j];
                list.add(String.valueOf(str));
            }
        }
        this.mGridLayout.setAdapter(new HueAdapter(this.getActivity(), 0, list, heightBtn, widthBtn));
        this.mGridLayout.setNumColumns(COLUMN);
    }

    /**
     * Created by scr on 2014/12/12.
     * moveToActivityメソッド
     * SelectActivityに遷移する.
     * @param v 選択されたボタンの情報
     */
    public void moveToActivity(View v){
        //タップされたボタンのIDを取得
        int id = v.getId();
        /*
        //タップされたボタンのかなインデックスを取得
        String tabInitialId = null;
        //カウンター
        int counter = 0;
        for(int i = 0; i < JapaneseAlphabeticalOrder.ALPHABET_BLOCK.length; i++){
            for(int j = 0; j< JapaneseAlphabeticalOrder.ALPHABET_BLOCK[i].length; j++){
                ++counter;
                //もしカウンターとタップしたボタンのＩＤがなじなら
                //選択された文字を取得する.
                if(id == counter){
                    tabInitialId = JapaneseAlphabeticalOrder.ALPHABET_BLOCK[i][j];
                    break;
                }
            }
        }*/

        //画面遷移をする
        JapaneseAlphabeticalOrderFragmentActivity activity = (JapaneseAlphabeticalOrderFragmentActivity)this.getActivity();
        activity.doChangeKanaIndexSelectAcitivty(id);

    }


    /**
     * Created by scr on 2014/12/12.
     * confirmTapButtonIdメソッド
     * 画面上に配置された50音順のボタンのIDを取得する.
     * @param v ボタンの情報
     */
    public void confirmTapButtonId(View v){
        JapaneseAlphabeticalOrderFragmentActivity activity = (JapaneseAlphabeticalOrderFragmentActivity)this.getActivity();
        int id = v.getId();

        //タップされた頭文字を保持する
        String tabInitialId=null;
        int counter = 0;
        for(int i = 0; i < JapaneseAlphabeticalOrder.ALPHABET_BLOCK.length; i++){
            for(int j = 0; j< JapaneseAlphabeticalOrder.ALPHABET_BLOCK[i].length; j++){
                ++counter;
                //もしカウンターとタップしたボタンのＩＤがなじなら
                //選択された文字を取得する.
                if(id == counter){
                    tabInitialId = JapaneseAlphabeticalOrder.ALPHABET_BLOCK[i][j];
                }
            }
        }
        Toast.makeText(activity, ""+tabInitialId, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onStart(){
        super.onStart();
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

    public class HueAdapter extends ArrayAdapter<String> {
        List<String> list;
        private Context mContext;
        private LayoutInflater mLayoutInflater;
        private int heightBtn;
        private int widthBtn;
        private  class ViewHolder {
            public Button button;
        }

        public HueAdapter(Context context, int layoutId, List<String> objects,int heightBtn,int widthBtn) {
            super(context, layoutId, objects);
            mContext = context;
            mLayoutInflater = LayoutInflater.from(context);
            list            = objects;
            this.heightBtn  = heightBtn;
            this.widthBtn   = widthBtn;
        }

        public String getItem(int position) {
            //return mHueArray[position];
            return this.list.get(position);
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                convertView = mLayoutInflater.inflate(R.layout.grid_item_japanese_alpabetical, null);
                holder = new ViewHolder();
                holder.button = (Button)convertView.findViewById(R.id.japanese_alphabetical_grid_button);
                String str = this.getItem(position);
                holder.button.setId(++position);
                holder.button.setBackgroundResource(R.drawable.circle_oval_shape_gray_button_frame_border);
                holder.button.setPadding(10, 0, 10, 0);
                holder.button.setTextSize(FONT_SIZE);
                holder.button.setMinimumHeight(heightBtn);

                if(str.equals("")){
                    holder.button.setVisibility(View.INVISIBLE);
                }else{
                    holder.button.setText(str);
                    holder.button.setOnClickListener(new OnClickListener() {
                        public void onClick(View v) {
                            moveToActivity(v);
                        }
                     });
                }
                //holder.button.setHeight(heightBtn);
                //holder.button.setWidth(widthBtn);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder)convertView.getTag();
            }

            return convertView;
        }
    }

}
