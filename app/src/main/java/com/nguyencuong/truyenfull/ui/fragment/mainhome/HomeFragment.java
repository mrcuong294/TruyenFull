package com.nguyencuong.truyenfull.ui.fragment.mainhome;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.google.android.gms.ads.NativeExpressAdView;
import com.nguyencuong.truyenfull.BaseFragment;
import com.nguyencuong.truyenfull.R;
import com.nguyencuong.truyenfull.eventbus.ChangeDataSourceEventBus;
import com.nguyencuong.truyenfull.model.Block;
import com.nguyencuong.truyenfull.source.HomeLoader;
import com.nguyencuong.truyenfull.source.HomeRepository;
import com.nguyencuong.truyenfull.widget.bookblock.BooksBlockRecyclerAdapter;
import com.nguyencuong.truyenfull.widget.bookblock.BooksBlockView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * create an instance of this fragment.
 */
public class HomeFragment extends BaseFragment<HomePresenter> implements HomeContract.View {

    private static final String TAG = HomeFragment.class.getSimpleName();

    private static final String ARG_DATASOURCE = "ARG_DATASOURCE";

    public static HomeFragment newInstance(int dataSource) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_DATASOURCE, dataSource);
        fragment.setArguments(args);
        return fragment;
    }

    @BindView(R.id.home_scroll_layout)
    NestedScrollView mNestedScrollView;

    @BindView(R.id.home_content_layout)
    LinearLayout contentLayout;

    private int dataSource;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            dataSource = getArguments().getInt(ARG_DATASOURCE);
        }
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initPresenter() {
        HomeRepository repository = new HomeRepository(dataSource);
        mPresenter = new HomePresenter(
                this,
                getLoaderManager(),
                new HomeLoader(getActivity(), repository),
                repository
        );
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        addGroupButtonTop();

        mPresenter.onCreated();

        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroyView() {
        EventBus.getDefault().unregister(this);
        super.onDestroyView();
    }

    @Subscribe
    public void onEventBusChangeDataSource(ChangeDataSourceEventBus eventBus) {
        if (dataSource == eventBus.dataSource) return;

        dataSource = eventBus.dataSource;
        mPresenter.changeDataSource(dataSource);
    }

    @Override
    public void showLoading(boolean show) {
        showDialogLoading(show);
    }

    @Override
    public void showMsgError(boolean show, String msg) {
        if (show) showToastError(msg);
    }

    @Override
    public void showMsgError(boolean show, @StringRes int resId) {
        if (show) {
            showToastError(resId);
        }
    }

    @Override
    public void showMsgToast(String msg) {
        showToast(msg);
    }

    @Override
    public void showMsgToast(@StringRes int resId) {
        showToast(resId);
    }

    @Override
    public void addBlockView(Block block) {
        BooksBlockView blockView = new BooksBlockView(getActivity(), block.getStyle());

        blockView.setTextTitle(block.getTitle());
        blockView.setUrlViewMore(block.getUrl());
        blockView.setListBooks(block.getListBooks());

        blockView.setOnViewMoreListener(new BooksBlockView.OnViewMoreListener() {
            @Override
            public void onHomeBlockViewMoreClick(String urlMore) {
                showToastSuccess(urlMore);
            }
        });

        blockView.setOnItemClickListener(new BooksBlockRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onBookItemClick(String url) {
                showToastSuccess(url);
            }
        });

        contentLayout.addView(blockView);
    }

    @Override
    public void addAdsView() {

        NativeExpressAdView mAdView = new NativeExpressAdView(getActivity().getApplicationContext());
        mAdView.setAdUnitId(getString(R.string.admob_native_home_unit));
    }

    @Override
    public void clearViews() {
        contentLayout.removeAllViews();
        addGroupButtonTop();
    }

    public void addGroupButtonTop() {
        if (contentLayout.getChildCount() == 0) {
            LayoutInflater.from(getActivity())
                    .inflate(R.layout.inc_group_button_top, contentLayout, true);

            contentLayout.findViewById(R.id.vg_btn_top_day).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showToastSuccess("Top day clicked !");
                }
            });

            contentLayout.findViewById(R.id.vg_btn_top_month).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showToastSuccess("Top month clicked !");
                }
            });

            contentLayout.findViewById(R.id.vg_btn_top_alltime).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showToastSuccess("Top all time clicked !");
                }
            });
        }
    }
}
