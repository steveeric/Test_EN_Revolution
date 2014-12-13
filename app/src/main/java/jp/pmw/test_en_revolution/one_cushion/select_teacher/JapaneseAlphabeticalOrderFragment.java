package jp.pmw.test_en_revolution.one_cushion.select_teacher;

import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.Toast;

import jp.pmw.test_en_revolution.R;
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
    private GridLayout mGridLayout;

    private static final int FONT_SIZE = 30;

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

        return view;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.mGridLayout = (GridLayout)this.getActivity().findViewById(R.id.japanese_alphabetical_grid);
    }
    @Override
    public void onResume(){
        super.onResume();
        //50音それぞれのボタンインデックス
        int count = 0;

         //グリッドビューの横幅
        int gridWidthSize = this.mGridLayout.getWidth();
        int widthCount = 0;
        for(int i = 0; i < JapaneseAlphabeticalOrder.ALPHABET_BLOCK.length; i++){
            widthCount = JapaneseAlphabeticalOrder.ALPHABET_BLOCK[i].length;
            for(int j = 0; j < JapaneseAlphabeticalOrder.ALPHABET_BLOCK[i].length; j++) {
                //５０音ボタン生成
                Button b = new Button(this.getActivity());
                b.setId(++count);
                b.setOnClickListener(new OnClickListener() {
                    public void onClick(View v) {
                        //タップしたボタンのIDを確認する.
                        //confirmTapButtonId(v);

                        //選択されたボタンIDを元にSelectActivityに画面遷移をする
                        moveToActivity(v);
                    }
                });
                //ボタン一つ一つに文字を入れる.
                String str = JapaneseAlphabeticalOrder.ALPHABET_BLOCK[i][j];
                if(str.equals("")){
                    b.setVisibility(View.INVISIBLE);
                }else{
                    b.setText(str);
                    b.setTextSize(FONT_SIZE);
                    //b.setText("::");
                }
                b.setHeight(150);
                b.setWidth(80);
                this.mGridLayout.addView(b);

            }
        }
    }

    /**
     * Created by scr on 2014/12/12.
     * moveToActivityメソッド
     * SelectActivityに遷移する.
     * @param v 選択されたボタンの情報
     */
    public void moveToActivity(View v){
        JapaneseAlphabeticalOrderFragmentActivity activity = (JapaneseAlphabeticalOrderFragmentActivity)this.getActivity();
        activity.doChangeKanaIndexSelectAcitivty();
    }


    /**
     * Created by scr on 2014/12/12.
     * confirmTapButtonIdメソッド
     * 画面上に配置された50音順のボタンのIDを取得する.
     * @param v ボタンの情報
     */
    public void confirmTapButtonId(View v){
        AllSelectActivity activity = (AllSelectActivity)this.getActivity();
        int id = v.getId();
        Toast.makeText(activity, ""+id, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onStart(){
        super.onStart();
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
