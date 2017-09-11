package com.nguyencuong.truyenfull.source;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.nguyencuong.truyenfull.model.Block;
import com.nguyencuong.truyenfull.util.LogUtils;

import java.util.List;

/**
 * Content class.
 * <p>
 * Created by Mr Cuong on 9/11/2017.
 * Email: vancuong2941989@gmail.com
 */

public class HomeLoader extends AsyncTaskLoader<List<Block>> {

    private static final String TAG = "HomeLoader";

    private HomeRepository mHomeRepository;

    private boolean isLoaded = false;

    public HomeLoader(Context context, HomeRepository mHomeRepository) {
        super(context);
        this.mHomeRepository = mHomeRepository;
        isLoaded = false;
    }

    public void setLoaded(boolean loaded) {
        isLoaded = loaded;
    }

    @Override
    protected void onStartLoading() {
        LogUtils.d(TAG, "onStartLoading : isLoaded = " + isLoaded);
        if (!isLoaded) {
            forceLoad();
        }
    }

    @Override
    protected void onStopLoading() {
        LogUtils.d(TAG, "onStopLoading");
        cancelLoad();
    }

    @Override
    public List<Block> loadInBackground() {
        return mHomeRepository.loadHomeData();
    }
}
