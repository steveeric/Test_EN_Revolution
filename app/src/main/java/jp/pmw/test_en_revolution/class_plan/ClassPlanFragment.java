package jp.pmw.test_en_revolution.class_plan;

import android.app.Activity;
import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import jp.pmw.test_en_revolution.MainActivity;
import jp.pmw.test_en_revolution.R;
import jp.pmw.test_en_revolution.one_cushion.select_teacher.Teacher;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ClassPlanFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ClassPlanFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ClassPlanFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static ClassPlanFragment newInstance(int sectionNumber) {
        ClassPlanFragment fragment = new ClassPlanFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    private ListView mClassPlanListView;

    public ClassPlanFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_class_plan, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mClassPlanListView = (ListView)this.getActivity().findViewById(R.id.class_plan_listView);
    }

    @Override
    public void onResume(){
        super.onResume();
        MainActivity activity = (MainActivity)this.getActivity();
        Teacher teacher = activity.mTeacher;
        String date = teacher.getClassPlan().getWhen().getYear();
        String timetableName = teacher.getClassPlan().getTimeZone().getTimeZoneName();
        String roomName = teacher.getClassPlan().getPlace().getRoom().getRoomName();
        String teacherName = teacher.getName();
        String subjectName = teacher.getClassPlan().getSubject().getSubjectName();
        String[] members = { "日付 : " +date, "時限 : "+timetableName, "講義室名 : "+roomName, "教員名 : "+teacherName,"科目名 : "+subjectName};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(),
                android.R.layout.simple_expandable_list_item_1, members);
        this.mClassPlanListView.setAdapter(adapter);
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
