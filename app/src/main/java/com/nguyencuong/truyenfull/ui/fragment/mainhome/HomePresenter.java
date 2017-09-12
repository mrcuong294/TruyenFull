package com.nguyencuong.truyenfull.ui.fragment.mainhome;

import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;

import com.nguyencuong.truyenfull.BasePresenter;
import com.nguyencuong.truyenfull.R;
import com.nguyencuong.truyenfull.model.Block;
import com.nguyencuong.truyenfull.source.HomeLoader;
import com.nguyencuong.truyenfull.source.HomeRepository;
import com.nguyencuong.truyenfull.util.LogUtils;

import java.util.List;

/**
 * Content class.
 * <p>
 * Created by Mr Cuong on 9/11/2017.
 * Email: vancuong2941989@gmail.com
 */

public class HomePresenter extends BasePresenter<HomeContract.View> implements HomeContract.Presenter {

    private static final int REQUEST_LOADER = 1;

    private final LoaderManager mLoaderManager;

    private final HomeLoader mHomeLoader;

    private final HomeRepository mHomeRepository;


    protected HomePresenter(HomeContract.View view, LoaderManager mLoaderManager, HomeLoader mHomeLoader, HomeRepository mHomeRepository) {
        super(view);
        this.mLoaderManager = mLoaderManager;
        this.mHomeLoader = mHomeLoader;
        this.mHomeRepository = mHomeRepository;
    }

    @Override
    protected void onCreated() {
        mLoaderManager.initLoader(REQUEST_LOADER, null, mLoaderCallbacks);
    }

    @Override
    protected void onDestroy() {

    }

    @Override
    public void changeDataSource(int dataSource) {
        LogUtils.d(TAG, "changeDataSource : dataSource = " + dataSource);
        mView.clearViews();
        mHomeRepository.setDataSource(dataSource);
        mHomeLoader.setLoaded(false);
        mHomeLoader.startLoading();
    }

    @Override
    public void reloadPage() {
        LogUtils.d(TAG, "reloadPage");
        mView.clearViews();
        mHomeLoader.setLoaded(false);
        mHomeLoader.startLoading();
    }

    private LoaderManager.LoaderCallbacks<List<Block>> mLoaderCallbacks =
            new LoaderManager.LoaderCallbacks<List<Block>>() {
        @Override
        public Loader<List<Block>> onCreateLoader(int id, Bundle args) {
            mView.showMsgError(false, null);
            mView.showLoading(true);
            return mHomeLoader;
        }

        @Override
        public void onLoadFinished(Loader<List<Block>> loader, List<Block> data) {
            mView.showLoading(false);

            if (data == null || data.size() == 0) {
                mView.showMsgError(true, R.string.error_msg_nodata);
                return;
            }

            for (Block block : data) {
                mView.addBlockView(block);
            }

            mHomeLoader.setLoaded(true);
        }

        @Override
        public void onLoaderReset(Loader<List<Block>> loader) {

        }
    };
}
