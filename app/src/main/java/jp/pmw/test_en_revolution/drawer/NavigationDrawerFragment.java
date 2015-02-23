package jp.pmw.test_en_revolution.drawer;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import java.util.List;
import jp.pmw.test_en_revolution.R;
import jp.pmw.test_en_revolution.one_cushion.select_teacher.JapaneseAlphabeticalOrderFragmentActivity;

/**
 * Fragment used for managing interactions for and presentation of a navigation drawer.
 * See the <a href="https://developer.android.com/design/patterns/navigation-drawer.html#Interaction">
 * design guidelines</a> for a complete explanation of the behaviors implemented here.
 */
public class NavigationDrawerFragment extends Fragment {

    /**
     * Remember the position of the selected item.
     */
    private static final String STATE_SELECTED_POSITION = "selected_navigation_drawer_position";

    /**
     * Per the design guidelines, you should show the drawer on launch until the user manually
     * expands it. This shared preference tracks this.
     */
    private static final String PREF_USER_LEARNED_DRAWER = "navigation_drawer_learned";

    /**
     * A pointer to the current callbacks instance (the Activity).
     */
    private NavigationDrawerCallbacks mCallbacks;

    /**
     * Helper component that ties the action bar to the navigation drawer.
     */
    private ActionBarDrawerToggle mDrawerToggle;

    private DrawerLayout mDrawerLayout;
    private RelativeLayout mDrawerRelativeLayout;
    private ListView mDrawerListView;
    private View mFragmentContainerView;
    //トップのアクティビティーに戻る(50音順まで.)
    private Button returnTopActivityButton;

    private int mCurrentSelectedPosition = 0;
    private boolean mFromSavedInstanceState;
    private boolean mUserLearnedDrawer;

    /**mCurrentSelectedPosition
     * Created by scr on 2014/12/15.
     * getCurrentSelectedPositionメソッド
     * 選択されている個所を返す.
     */
    public int getCurrentSelectedPosition(){
        return this.mCurrentSelectedPosition;
    }

    public ListView getDrawerListView(){return this.mDrawerListView;}

    public NavigationDrawerFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Read in the flag indicating whether or not the user has demonstrated awareness of the
        // drawer. See PREF_USER_LEARNED_DRAWER for details.
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
        mUserLearnedDrawer = sp.getBoolean(PREF_USER_LEARNED_DRAWER, false);

        if (savedInstanceState != null) {
            mCurrentSelectedPosition = savedInstanceState.getInt(STATE_SELECTED_POSITION);
            mFromSavedInstanceState = true;
        }

        // Select either the default item (0) or the last selected item.
        selectItem(mCurrentSelectedPosition);

        super.onCreate(savedInstanceState);
    }
    /**
     * Created by scr on 2014/12/15.
     * moveToTopActivityメソッド
     * トップのアクティビティーまで戻る(今回は50音順まで)
     */
    public void moveToTopActivity(){
        Intent intent = new Intent(this.getActivity(),JapaneseAlphabeticalOrderFragmentActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        this.getActivity().finish();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // Indicate that this fragment would like to influence the set of actions in the action bar.
        setHasOptionsMenu(true);
        mDrawerListView = (ListView) this.getActivity().findViewById(R.id.navigation_drawer_list);
        mDrawerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ListView listView = (ListView) parent;
                changeTap(listView,position);
                selectItem(position);
            }
        });

        /*List<DrawerItem> items = new ArrayList<DrawerItem>();
        for(int i=0;i<resores.length;i++){
            items.add(new DrawerItem(resores[i],titles[i]));
        }*/

        Drawer drawer = new Drawer();
        List<DrawerBindData> items = drawer.getDrawerItem(this.getActivity());
        mDrawerListView.setAdapter(new DrawerAdapter(getActionBar().getThemedContext(),R.layout.fragment_drawer_sector,items));

        //mDrawerListView.setItemChecked(mCurrentSelectedPosition, true);

        //50音順に戻るボタン
        this.returnTopActivityButton = (Button)this.getActivity().findViewById(R.id.fragment_navigation_drawer_return_top_button);
        this.returnTopActivityButton.setOnClickListener(new View.OnClickListener() {
            // ボタンがクリックされた時のハンドラ
            @Override
            public void onClick(View v) {
                //TODO Auto-generated method stub
                moveToTopActivity();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mDrawerRelativeLayout = (RelativeLayout)inflater.inflate(R.layout.fragment_navigation_drawer, container, false);
        return mDrawerRelativeLayout;
    }


    /**
     * ListViewの違う項目をタップした.
     * ***/
    public void changeTap(ListView listView,int position){
        //前のをクリアにする
        DrawerBindData data = (DrawerBindData)listView.getItemAtPosition(this.mCurrentSelectedPosition);
        data.unFocus();
        //フラグメントのListViewを1行更新
        updateRowLineListView(listView);
        data=null;
        data = (DrawerBindData)listView.getItemAtPosition(position);
        data.tap();
        //リストビュー再描画
        //タップされた個所が変わったので.
        //this.mDrawerListView.invalidate();
        //this.mDrawerListView.notifyAll();
    }

    /**
     * Created by scr on 2014/12/16.
     * updateRowLineListViewメソッド
     * NavigationDrawerFragment所のListViewの必要な1行の箇所を
     * 更新するメソッドです.
     */
    public void updateRowLineListView(ListView listView){
        // 更新対象のViewを取得
        View targetView = listView.getChildAt(mCurrentSelectedPosition);
        // getViewで対象のViewを更新
        listView.getAdapter().getView(mCurrentSelectedPosition, targetView, listView);
    }



   /* @Override
    public void onWindowFocusChanged(final boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        ListView list = (ListView) this.getActivity().findViewById(R.id.navigation_drawer_list);
        LinearLayout listItem = (LinearLayout) list.getChildAt(0);
        if (listItem != null) {
            int y = list.getHeight()/2 - listItem.getHeight() / 2;
            list.setSelectionFromTop(SELECT_LIST_INDEX, y);
        }
    }*/

    public boolean isDrawerOpen() {
        return mDrawerLayout != null && mDrawerLayout.isDrawerOpen(mFragmentContainerView);
    }

    /**
     * Users of this fragment must call this method to set up the navigation drawer interactions.
     *
     * @param fragmentId   The android:id of this fragment in its activity's layout.
     * @param drawerLayout The DrawerLayout containing this fragment's UI.
     */
    public void setUp(int fragmentId, DrawerLayout drawerLayout) {
        mFragmentContainerView = getActivity().findViewById(fragmentId);
        mDrawerLayout = drawerLayout;

        // set a custom shadow that overlays the main content when the drawer opens
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        // set up the drawer's list view with items and click listener

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        // ActionBarDrawerToggle ties together the the proper interactions
        // between the navigation drawer and the action bar app icon.
        mDrawerToggle = new ActionBarDrawerToggle(
                getActivity(),                    /* host Activity */
                mDrawerLayout,                    /* DrawerLayout object */
                R.drawable.ic_drawer,             /* nav drawer image to replace 'Up' caret */
                R.string.navigation_drawer_open,  /* "open drawer" description for accessibility */
                R.string.navigation_drawer_close  /* "close drawer" description for accessibility */
        ) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                if (!isAdded()) {
                    return;
                }

                getActivity().invalidateOptionsMenu(); // calls onPrepareOptionsMenu()
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                if (!isAdded()) {
                    return;
                }

                if (!mUserLearnedDrawer) {
                    // The user manually opened the drawer; store this flag to prevent auto-showing
                    // the navigation drawer automatically in the future.
                    mUserLearnedDrawer = true;
                    SharedPreferences sp = PreferenceManager
                            .getDefaultSharedPreferences(getActivity());
                    sp.edit().putBoolean(PREF_USER_LEARNED_DRAWER, true).apply();
                }

                getActivity().invalidateOptionsMenu(); // calls onPrepareOptionsMenu()
            }
        };

        // If the user hasn't 'learned' about the drawer, open it to introduce them to the drawer,
        // per the navigation drawer design guidelines.
        if (!mUserLearnedDrawer && !mFromSavedInstanceState) {
            mDrawerLayout.openDrawer(mFragmentContainerView);
        }

        // Defer code dependent on restoration of previous instance state.
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });

        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    public void selectItem(int position) {
        //選択したListViewのPositionをフィールド変数に持たせる.
        mCurrentSelectedPosition = position;
        if (mDrawerListView != null) {
            mDrawerListView.setItemChecked(position, true);
            //フラグメントのListViewを1行更新
            updateRowLineListView(mDrawerListView);
        }
        if (mDrawerLayout != null) {
            mDrawerLayout.closeDrawer(mFragmentContainerView);
        }
        if (mCallbacks != null) {
            mCallbacks.onNavigationDrawerItemSelected(position);
        }
    }
    /*@Override
    public void onDataAssigned(DrawerItem data, View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            return;
        }
        if (TextUtils.equals(getString(R.string.title_section1), data.mTitle)) {
            view.setBackgroundColor(0xfff7aecb);
        } else {
            view.setBackgroundColor(0x00000000);
        }
    }*/


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mCallbacks = (NavigationDrawerCallbacks) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement NavigationDrawerCallbacks.");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(STATE_SELECTED_POSITION, mCurrentSelectedPosition);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Forward the new configuration the drawer toggle component.
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // If the drawer is open, show the global app actions in the action bar. See also
        // showGlobalContextActionBar, which controls the top-left area of the action bar.
        if (mDrawerLayout != null && isDrawerOpen()) {
            inflater.inflate(R.menu.global, menu);
            showGlobalContextActionBar();
        }
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        /*if(item.getItemId() == R.id.action_attendance_list){
            this.getActivity().getFragmentManager().beginTransaction()
                    .replace(R.id.container, AttendeeFragment.newInstance("a", "b"))
                    .commit();
            return true;
        }else if(item.getItemId() == R.id.action_sit_situation){
            this.getActivity().getFragmentManager().beginTransaction()
                    .replace(R.id.container, SeatSituationFragment.newInstance(1))
                    .commit();
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

    /**
     * Per the navigation drawer design guidelines, updates the action bar to show the global app
     * 'context', rather than just what's in the current screen.
     */
    private void showGlobalContextActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setTitle(R.string.app_name);
    }

    private ActionBar getActionBar() {
        return getActivity().getActionBar();
    }

    /**
     * Callbacks interface that all activities using this fragment must implement.
     */
    public static interface NavigationDrawerCallbacks {
        /**
         * Called when an item in the navigation drawer is selected.
         */
        void onNavigationDrawerItemSelected(int position);
    }
}
