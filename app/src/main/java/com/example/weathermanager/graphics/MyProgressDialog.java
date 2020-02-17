package com.example.weathermanager.graphics;

import android.app.ActionBar;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.ProgressBar;

import com.example.weathermanager.R;


public class MyProgressDialog extends Dialog {
    @Override
    public boolean isShowing() {
        return super.isShowing();
    }

    public static MyProgressDialog show(Context context, CharSequence title,
                                        CharSequence message, boolean indeterminate) {
        return show(context, title, message, indeterminate, false, null);
    }

    public static MyProgressDialog show(Context context, CharSequence title,
                                        CharSequence message, boolean indeterminate,
                                        boolean cancelable, DialogInterface.OnCancelListener cancelListener) {
        MyProgressDialog dialog = new MyProgressDialog(context);
        dialog.setTitle(title);
        dialog.setCancelable(cancelable);
        dialog.setOnCancelListener(cancelListener);
        /* The next line will add the ProgressBar to the dialog. */
        dialog.addContentView(new ProgressBar(context), new ActionBar.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT));
        dialog.show();

        return dialog;
    }

    public MyProgressDialog(Context context) {
        super(context, R.style.NewDialog);
    }
}