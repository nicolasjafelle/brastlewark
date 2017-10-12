package altran.brastlewark.app.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import altran.brastlewark.app.R;

/**
 * Created by nicolas on 7/27/15.
 */
public class UIUtils {

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(
                Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            View currentFocus = activity.getCurrentFocus();
            if (currentFocus != null) {
                imm.hideSoftInputFromWindow(currentFocus.getWindowToken(), 0);
            }
        }
    }

    public static void showSoftKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(
                Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            View currentFocus = activity.getCurrentFocus();
            if (currentFocus != null) {
                imm.showSoftInput(currentFocus, 0);
            }
        }
    }

    public static void hideSoftKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static void showSoftKeyboard(View view) {
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.GINGERBREAD_MR1 && view.hasFocus()) {
            view.clearFocus();
        }
        view.requestFocus();
        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(view, 0);
    }

    public static void showToast(Context context, String text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }

    public static void showToast(Context context, int stringResId) {
        Toast.makeText(context, stringResId, Toast.LENGTH_SHORT).show();
    }

    public static void showDismissibleSnackBar(View parentView, String text) {
        final Snackbar snackBar = Snackbar.make(parentView, text, Snackbar.LENGTH_LONG);
        snackBar.setActionTextColor(ContextCompat.getColor(parentView.getContext(), R.color.colorAccent));
        snackBar.setAction(R.string.dismiss, view -> snackBar.dismiss());

        View snackbarView = snackBar.getView();

        int snackbarTextId = android.support.design.R.id.snackbar_text;
        TextView textView = (TextView) snackbarView.findViewById(snackbarTextId);
        int whiteColor = ContextCompat.getColor(parentView.getContext(), R.color.white);
        textView.setTextColor(whiteColor);
        snackBar.setActionTextColor(whiteColor);
//        snackbarView.setBackgroundColor(ContextCompat.getColor(parentView.getContext(), R.color.bright_red));

        snackBar.show();
    }

    public static void showDismissibleSnackBar(View parentView, int textResId) {
        String text = parentView.getResources().getString(textResId);
        showDismissibleSnackBar(parentView, text);
    }

    public static int getToolbarHeight(Context context) {

        final TypedArray styledAttributes = context.getTheme().obtainStyledAttributes(
                new int[]{android.R.attr.actionBarSize});

        int height;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            height = styledAttributes.getDimensionPixelSize(0, 0);
        } else {
            height = styledAttributes.getDimensionPixelSize(0, 0) + context.getResources().getDimensionPixelSize(R.dimen.base_separation);
        }

        return height;
    }

}
