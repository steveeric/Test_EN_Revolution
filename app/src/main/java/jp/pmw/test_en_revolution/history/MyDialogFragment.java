package jp.pmw.test_en_revolution.history;

import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

/**
 * Created by scr on 2015/01/07.
 */
public class MyDialogFragment extends DialogFragment{
    /**
     * Created by scr on 2015/1/7.
     * resizeDialogメソッド
     * DialogFragmentのサイズを調整するときに使用します.
     * @param dialog ダイアログ
     * @param  widthScale 横の倍率
     * @param  heightScale 縦の倍率
     **/
    public void resizeDialog(Dialog dialog,double widthScale,double heightScale){
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();

        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int dialogWidth = (int) (metrics.widthPixels * widthScale);
        int dialogHeight = (int) (metrics.heightPixels * heightScale);

        lp.width = dialogWidth;
        lp.height = dialogHeight;
        dialog.getWindow().setAttributes(lp);
    }

    public LinearLayout getLinearLayout(Dialog d, int resorce) {
        return (LinearLayout) d.findViewById(resorce);
    }

    public TextView getTextView(Dialog d, int resorce) {
        return (TextView) d.findViewById(resorce);
    }

    public ListView getListView(Dialog d, int resorce){
        return (ListView) d.findViewById(resorce);
    }

    public Button getButton(Dialog d, int resorce) {
        return (Button) d.findViewById(resorce);
    }
    public SearchView getSerchView(Dialog d, int resorce) {
        return (SearchView) d.findViewById(resorce);
    }
    public GridView getGridView(Dialog d, int resorce) {
        return (GridView) d.findViewById(resorce);
    }
}
