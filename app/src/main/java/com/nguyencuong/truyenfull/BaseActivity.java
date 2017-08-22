package com.nguyencuong.truyenfull;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import butterknife.ButterKnife;

/**
 * The Class
 * Created by pc on 8/22/2017.
 */

public class BaseActivity extends AppCompatActivity {
    public String TAG = this.getClass().getSimpleName();

    protected boolean isEnableButterKnight = true;

    protected boolean isScreenLand;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getResources().getBoolean(R.bool.isTablet)) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            isScreenLand = true;
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            isScreenLand = false;
        }
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        if (isEnableButterKnight) {
            ButterKnife.bind(this);
        }
    }

    protected void showTost(String msg) {
        Toast.makeText(getApplicationContext(), msg + "", Toast.LENGTH_SHORT).show();
    }

    protected void showTost(@StringRes int msgId) {
        showTost(getString(msgId));
    }
}
