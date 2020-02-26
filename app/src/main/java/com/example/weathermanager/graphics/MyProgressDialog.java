package com.example.weathermanager.graphics;

import android.app.ActionBar;
import android.app.Dialog;
import android.content.Context;
import android.widget.ProgressBar;

import com.example.weathermanager.R;


public class MyProgressDialog extends Dialog {
    public static MyProgressDialog show(Context context, CharSequence title) {
        MyProgressDialog dialog = new MyProgressDialog(context);
        dialog.setTitle(title);
        dialog.setCancelable(false);
        dialog.setOnCancelListener(null);
        /* The next line will add the ProgressBar to the dialog. */
        dialog.addContentView(new ProgressBar(context), new ActionBar.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT));
        dialog.show();

        return dialog;
    }

    private MyProgressDialog(Context context) {
        super(context, R.style.NewDialog);
    }
}