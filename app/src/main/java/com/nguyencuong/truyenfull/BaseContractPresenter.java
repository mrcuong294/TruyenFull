package com.nguyencuong.truyenfull;

/**
 * Content class.
 * <p>
 * Created by Mr Cuong on 4/13/2017.
 * Email: vancuong2941989@gmail.com
 */

public interface BaseContractPresenter {

    void onCreated();

    void onStart();

    void onResume();

    void onPause();

    void onDestroy();
}
