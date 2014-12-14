package jp.pmw.test_en_revolution.common;

import android.app.Dialog;

/**
 * Created by scr on 2014/12/14.
 */
public interface CommonDialogInterface {
    public interface onClickListener {
        void onDialogButtonClick(String tag, Dialog dialog, int which);
    }

    public interface onShowListener {
        void onDialogShow(String tag, Dialog dialog);
    }

    public interface onItemClickListener {
        void onDialogItemClick(String tag, Dialog dialog, String title, int which);
    }
}
