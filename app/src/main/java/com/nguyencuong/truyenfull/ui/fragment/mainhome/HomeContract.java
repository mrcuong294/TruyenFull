package com.nguyencuong.truyenfull.ui.fragment.mainhome;

import com.nguyencuong.truyenfull.BaseView;
import com.nguyencuong.truyenfull.model.Block;

/**
 * Content class.
 * <p>
 * Created by Mr Cuong on 9/11/2017.
 * Email: vancuong2941989@gmail.com
 */

public interface HomeContract {

    interface View extends BaseView {

        void addBlockView(Block block);

        void addAdsView();

        void clearViews();
    }

    interface Presenter {

        void changeDataSource(int dataSource);

        void reloadPage();
    }
}
