package jp.pmw.test_en_revolution.questionnaire;

import android.app.Activity;
import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.List;

import jp.pmw.test_en_revolution.MainActivity;
import jp.pmw.test_en_revolution.R;
import jp.pmw.test_en_revolution.questionnaire.dummy.DummyQuestionContent;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link QuestionnaireFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link QuestionnaireFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QuestionnaireFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static QuestionnaireFragment newInstance(int sectionNumber) {
        QuestionnaireFragment fragment = new QuestionnaireFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    //アンケートが登録されていない場合のレイアウト
    private LinearLayout noQuestionLayout;
    //アンケートが登録されている場合のレイアウト
    private LinearLayout doQuestionlayout;

    public QuestionnaireFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_clicker, container, false);
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.noQuestionLayout = (LinearLayout)this.getActivity().findViewById(R.id.clicker_survey_no_question_linearLayout);
        this.doQuestionlayout = (LinearLayout)this.getActivity().findViewById(R.id.clicker_survey_do_question_linearLayout);
    }

    @Override
    public void onResume(){
        super.onResume();

    }

    /**
     * Created by scr on 2014/12/25.
     * showNoQuestionLayoutメソッド
     * アンケートが登録されていない場合に表示するレイアウト.
     */
    private void showNoQuestionLayout(){
        this.doQuestionlayout.setVisibility(View.GONE);
        this.noQuestionLayout.setVisibility(View.VISIBLE);
    }
    /**
     * Created by scr on 2014/12/25.
     * showNoQuestionLayoutメソッド
     * アンケートが登録されている場合に表示するレイアウト.
     */
    private void showQuestionLayout(){
        this.doQuestionlayout.setVisibility(View.VISIBLE);
        this.noQuestionLayout.setVisibility(View.GONE);
    }

    /**
     * Created by scr on 2014/12/25.
     * dummyTestStartメソッド
     * ダミーデータでアンケート調査画面を開始する.
     */
    private void dummyTestStart() {
        //質問アイテムを取得
        List<Question> questions = DummyQuestionContent.ITEMS;
        if(questions.size() == 0){
            //アンケートがない.
            showNoQuestionLayout();
        }else{
            //アンケートがある.
            showQuestionLayout();
        }
    }





    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(getArguments().getInt(ARG_SECTION_NUMBER));
    }
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }
}
