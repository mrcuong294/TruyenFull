package com.nguyencuong.truyenfull;

import android.support.annotation.StringRes;

/**
 * Content class.
 * <p>
 * Created by Mr Cuong on 4/13/2017.
 * Email: vancuong2941989@gmail.com
 */

public interface BaseView {

    void showLoading(boolean show);

    void showMsgError(boolean show, String msg);

    void showMsgError(boolean show, @StringRes int resId);

    void showMsgToast(String msg);

    void showMsgToast(@StringRes int resId);
}
