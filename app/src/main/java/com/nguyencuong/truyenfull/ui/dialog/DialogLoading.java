package com.nguyencuong.truyenfull.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.KeyEvent;
import android.view.Window;

import com.nguyencuong.truyenfull.R;


public class DialogLoading {
    private Dialog dialog;
    private Listener listener;

    public DialogLoading(Context context) {
        build(context);
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public boolean isShowing() {
        return !(dialog == null || !dialog.isShowing());
    }

    public void show() {
        if (dialog != null && !dialog.isShowing()) {
            dialog.show();
        }
    }

    public void show(boolean show) {
        if (dialog != null) {
            if (show) dialog.show();
            else dialog.dismiss();
        }
    }

    public void dismiss() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    private void build(Context context) {
        dialog = new Dialog(context, R.style.AppTheme_Dialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_loading);
        dialog.setCanceledOnTouchOutside(false);

        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialogInterface, int keyCode, KeyEvent keyEvent) {

                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    if (listener != null)
                        listener.onBackPressed();
                    return true;
                }
                return false;
            }
        });
    }

    public interface Listener {
        void onBackPressed();
    }
}
