package com.nguyencuong.truyenfull;

/**
 * Content class.
 * <p>
 * Created by Mr Cuong on 4/13/2017.
 * Email: vancuong2941989@gmail.com
 */

public abstract class BasePresenter<T> {

    protected T mView;

    protected BasePresenter(T view) {
        this.mView = view;
    }
}
