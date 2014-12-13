package jp.pmw.test_en_revolution.one_cushion.select_teacher;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;

import jp.pmw.test_en_revolution.AppController;
import jp.pmw.test_en_revolution.R;
import jp.pmw.test_en_revolution.config.Packcage;
import jp.pmw.test_en_revolution.config.URL;

public class AllSelectActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);

        /**
         *教員全文をListで表示する.
         * **/
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }

        /**
         * 50音順で表示
         * **/
        /*if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new JapaneseAlphabeticalOrderFragment().newInstance(1))
                    .commit();
        }*/

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        private LinearLayout loadingLayout,sucessLayuot;
        private ListView listview;
        private Button button;
        private Teacher selectTeacher=null;
        private final PlaceholderFragment self = this;
        private boolean loading;
        public PlaceholderFragment() {
        }

        public void setSelectTeacher(Teacher teacher){
            this.selectTeacher=teacher;
        }
        public Teacher getSelectTeacher(){
            return this.selectTeacher;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_fragment_select_main, container, false);

            return view;
        }
        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
            self.loading = true;

            loadingLayout = (LinearLayout)getView().findViewById(R.id.load_layout);
            sucessLayuot = (LinearLayout)getView().findViewById(R.id.success_layout);

            listview = (ListView)getView().findViewById(R.id.listView);
            listview.setChoiceMode(ListView.CHOICE_MODE_NONE);
            listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent,
                                        View view, int pos, long id) {
                    ListView listView = (ListView) parent;
                    Teacher item = (Teacher) listView.getItemAtPosition(pos);
                    setSelectTeacher(item);
                }
            });
            button = (Button)getView().findViewById(R.id.button);
            button.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    // クリック時の処理
                    String selectItem = "";

                    //マップの情報を取得する
                    SparseBooleanArray checked = listview.getCheckedItemPositions();
                    Teacher teacher = getSelectTeacher();
                    doCheckAlertDialog(teacher);
                }
            });
        }

        public void onResume(){
            super.onResume();
            selectTeacher = null;
        }

        public void doCheckAlertDialog(Teacher teacher){
            String str = "教員名:";
            if(teacher!=null){
                str = str + teacher.getName();
            }else{
                str = "選択されていません.";
            }

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this.getActivity());
            alertDialogBuilder.setTitle("確認");
            alertDialogBuilder.setMessage(str);
            alertDialogBuilder.setPositiveButton("肯定",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //nextFragment();
                            //showTestNextFragment();
                            nextActivity();
                        }
                    });
            alertDialogBuilder.setCancelable(true);
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }

        /**
         *
         * MainActivityに行く
         * **/
        public void nextActivity(){
            Intent intent = new Intent();
            intent.setClassName(Packcage.MAIN, Packcage.MAIN_ACTIVITY);
            startActivity(intent);
        }


        /*public void nextFragment(){
            FragmentManager manager = this.getFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            NextFragment fragment = new NextFragment();
            transaction.replace(R.id.container, fragment);
            transaction.addToBackStack(null);//前のfragmentへもどるのに必要
            transaction.commit();
        }*/

        /*private void showTestNextFragment() {
            FragmentManager manager = getFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.container, NextFragment.newInstance(0));
            transaction.addToBackStack(null);//前のfragmentへもどるのに必要
            transaction.commit();
        }*/




        @Override
        public void onStart(){
            super.onStart();
            fetch();
        }


        private void fetch() {
            JsonArrayRequest request = new JsonArrayRequest(
                    URL.ALL_TEACHER,
                    new Response.Listener<JSONArray>() {
                        @Override public void onResponse(JSONArray response) {
                            // レスポンス受け取り時の処理...
                            JsonParser(response);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override public void onErrorResponse(VolleyError error) {
                            // エラー時の処理...
                            Toast.makeText(getActivity(), "Unable to fetch data: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
            AppController.getInstance(this.getActivity()).getRequestQueue().add(request);
        }
        private void JsonParser(JSONArray jsonarray){
            Gson mygson = new Gson();
            //List<Teacher> teachers = new ArrayList<Teacher>();
            Type collectionType = new TypeToken<Collection<Teacher>>(){}.getType();
            List<Teacher> teachers = mygson.fromJson(jsonarray.toString(),collectionType);
            CustomAdapter adapter = new CustomAdapter(this.getActivity(),0,teachers);
            listview.setAdapter(adapter);
            showTeacherSelect();
        }
        private void showTeacherSelect(){
            loadingLayout.setVisibility(View.INVISIBLE);
            sucessLayuot.setVisibility(View.VISIBLE);
        }

    }
}
