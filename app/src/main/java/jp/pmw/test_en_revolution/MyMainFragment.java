package jp.pmw.test_en_revolution;

import android.support.v4.app.Fragment;

/**
 * Created by scr on 2014/12/23.
 */
public class MyMainFragment extends Fragment {
    @Override
    public void onResume(){
        super.onResume();
        //ドロワーの必要個所をオープンにする.
        MainActivity activity = (MainActivity)this.getActivity();
        activity.openNavigationDrawer();
    }
}
