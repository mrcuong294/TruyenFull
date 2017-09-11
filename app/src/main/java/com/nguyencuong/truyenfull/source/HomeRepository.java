package com.nguyencuong.truyenfull.source;

import com.nguyencuong.truyenfull.constance.DataSources;
import com.nguyencuong.truyenfull.data.parser.TruyenFullParser;
import com.nguyencuong.truyenfull.model.Block;

import java.util.List;

/**
 * Content class.
 * <p>
 * Created by Mr Cuong on 9/11/2017.
 * Email: vancuong2941989@gmail.com
 */

public class HomeRepository  {

    private static final String TAG = HomeRepository.class.getSimpleName();

    private static HomeRepository INSTANCE = null;

    private TruyenFullParser mTruyenFullParser;

    private int dataSource;

    public HomeRepository(int dataSource) {
        this.dataSource = dataSource;

        mTruyenFullParser = TruyenFullParser.getInstance();
    }

    public static HomeRepository getInstance(int sourceSite) {
        if (INSTANCE == null) {
            INSTANCE = new HomeRepository(sourceSite);
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    public void setDataSource(int dataSource) {
        this.dataSource = dataSource;
    }

    public List<Block> loadHomeData() {
        switch (dataSource) {
            case DataSources.TRUYENFULL:

                return mTruyenFullParser.getHomeBlocks();
        }
        return null;
    }
}
