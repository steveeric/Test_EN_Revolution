package jp.pmw.test_en_revolution.common;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by scr on 2014/12/08.
 */
public class CommonDialogFragment extends DialogFragment {

    /*public interface CommonDialogInterface {
        public interface onClickListener {
            void onDialogButtonClick(String tag, Dialog dialog, int which);
        }

        public interface onShowListener {
            void onDialogShow(String tag, Dialog dialog);
        }

        public interface onItemClickListener {
            void onDialogItemClick(String tag, Dialog dialog, String title, int which);
        }
    }*/

    public static final String FIELD_LAYOUT = "layout";
    public static final String FIELD_TITLE = "title";
    public static final String FIELD_MESSAGE = "message";
    public static final String FIELD_LIST_ITEMS = "list_items";
    public static final String FIELD_LIST_ITEMS_STRING = "list_items_string";
    public static final String FIELD_LABEL_POSITIVE = "label_positive";
    public static final String FIELD_LABEL_NEGATIVE = "label_negative";
    public static final String FIELD_LABEL_NEUTRAL = "label_neutral";

    private CommonDialogInterface.onShowListener mListenerShow;
    private CommonDialogInterface.onClickListener mListenerOnClick;
    private CommonDialogInterface.onItemClickListener mListenerItemClick;
    private AlertDialog mAlertDialog;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Bundle args = getArguments();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        // listener check
        if (getTargetFragment() != null) {
            setListener(getTargetFragment());
        } else if (getActivity() != null) {
            setListener(getActivity());
        }

        // dialog title
        if (args.containsKey(FIELD_TITLE)) {
            builder.setTitle(args.getInt(FIELD_TITLE));
        }

        // dialog message
        if (args.containsKey(FIELD_MESSAGE)) {
            builder.setMessage(args.getInt(FIELD_MESSAGE));
        }

        // dialog customize content view
        if (args.containsKey(FIELD_LAYOUT)) {
            LayoutInflater inflater = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View content = inflater.inflate(args.getInt(FIELD_LAYOUT), null);
            builder.setView(content);
        }

        // dialog string list
        final List<String> items = new ArrayList<String>();
        if (args.containsKey(FIELD_LIST_ITEMS)) {
            final int[] listItems = args.getIntArray(FIELD_LIST_ITEMS);
            for (int i = 0; i < listItems.length; i++) {
                items.add(getString(listItems[i]));
            }
        }
        if (args.containsKey(FIELD_LIST_ITEMS_STRING)) {
            final String[] listItems = args.getStringArray(FIELD_LIST_ITEMS_STRING);
            for (int i = 0; i < listItems.length; i++) {
                items.add(listItems[i]);
            }
        }
        if (items.size() > 0) {
            builder.setItems(items.toArray(new String[items.size()]), new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if ( mListenerItemClick != null) {
                        mListenerItemClick.onDialogItemClick(getTag(), mAlertDialog, items.get(which), which);
                    }
                }

            });
        }

        // positive button title and click listener
        if (args.containsKey(FIELD_LABEL_POSITIVE)) {
            builder.setPositiveButton(args.getInt(FIELD_LABEL_POSITIVE), new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (mListenerOnClick != null) {
                        mListenerOnClick.onDialogButtonClick(getTag(), mAlertDialog, which);
                    }

                }
            });
        }

        // negative button title and click listener
        if (args.containsKey(FIELD_LABEL_NEGATIVE)) {
            builder.setNegativeButton(args.getInt(FIELD_LABEL_NEGATIVE), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    if (mListenerOnClick != null) {
                        mListenerOnClick.onDialogButtonClick(getTag(), mAlertDialog, which);
                    }
                }
            });
        }

        // neutral button title and click listener
        if (args.containsKey(FIELD_LABEL_NEUTRAL)) {
            builder.setNeutralButton(args.getInt(FIELD_LABEL_NEUTRAL), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (mListenerOnClick != null) {
                        mListenerOnClick.onDialogButtonClick(getTag(), mAlertDialog, which);
                    }
                }
            });
        }

        // make dialog
        mAlertDialog = builder.create();

        // show listener
        if (mListenerShow != null) {
            mAlertDialog.setOnShowListener(new DialogInterface.OnShowListener() {

                @Override
                public void onShow(DialogInterface dialog) {
                    mListenerShow.onDialogShow(getTag(), mAlertDialog);
                }
            });
        }

        return mAlertDialog;
    }

    private void setListener(Object target) {

        // on click listener
        if (target instanceof CommonDialogInterface.onClickListener) {
            mListenerOnClick = (CommonDialogInterface.onClickListener) target;
        }

        // on item click listener
        if (target instanceof CommonDialogInterface.onItemClickListener) {
            mListenerItemClick = (CommonDialogInterface.onItemClickListener) target;
        }

        // on show listener
        if (target instanceof CommonDialogInterface.onShowListener) {
            mListenerShow = (CommonDialogInterface.onShowListener) target;
        }

    }

}