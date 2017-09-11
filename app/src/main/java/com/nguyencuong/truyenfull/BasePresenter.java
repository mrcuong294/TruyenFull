package com.nguyencuong.truyenfull;

/**
 * Content class.
 * <p>
 * Created by Mr Cuong on 4/13/2017.
 * Email: vancuong2941989@gmail.com
 */

public abstract class BasePresenter<V extends BaseView> {
    protected final String TAG = this.getClass().getSimpleName();

    protected final V mView;

    protected BasePresenter(V view) {
        this.mView = view;
    }

    protected abstract void onCreated();

    void onStart(){};

    void onResume(){};

    void onPause(){};

    void onStop(){};

    protected abstract void  onDestroy();
}
