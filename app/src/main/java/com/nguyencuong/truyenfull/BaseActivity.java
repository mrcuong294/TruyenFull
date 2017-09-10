package com.nguyencuong.truyenfull;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.nguyencuong.truyenfull.ui.dialog.DialogLoading;

import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;

/**
 * The Class
 * Created by pc on 8/22/2017.
 */

public class BaseActivity extends AppCompatActivity {
    protected DialogLoading dialogLoading;

    protected boolean enableButterKnight = true;

    protected boolean isScreenLand;

    protected boolean isLoadingBackPressExit = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getResources().getBoolean(R.bool.isTablet)) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            isScreenLand = true;
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            isScreenLand = false;
        }

        dialogLoading = new DialogLoading(this);
        dialogLoading.setOnPopupLoadingListener(new DialogLoading.OnPopupLoadingListener() {
            @Override
            public void onBackPressed() {
                if (isLoadingBackPressExit) {
                    finish();
                }
            }
        });
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        if (enableButterKnight) {
            ButterKnife.bind(this);
        }
    }

    @Override
    protected void onDestroy() {
        dialogLoading.dismiss();
        dialogLoading = null;
        super.onDestroy();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        isScreenLand = newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE;
    }

    protected void showToast(String mes) {
        Toast.makeText(getApplicationContext(), mes + "", Toast.LENGTH_SHORT).show();
    }

    protected void showToast(@StringRes int messageId) {
        showToast(getString(messageId));
    }

    protected void showToastError(@StringRes int messageId) {
        showToastError(getString(messageId));
    }

    protected void showToastError(String mes) {
        Toasty.error(getApplicationContext(), mes + "", Toast.LENGTH_LONG).show();
    }

    protected void showToastSuccess(@StringRes int messageId) {
        showToastSuccess(getString(messageId));
    }

    protected void showToastSuccess(String mes) {
        Toasty.success(getApplicationContext(), mes + "", Toast.LENGTH_SHORT).show();
    }
}
