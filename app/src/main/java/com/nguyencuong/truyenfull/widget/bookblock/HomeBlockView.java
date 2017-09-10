package com.nguyencuong.truyenfull.widget.bookblock;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.nguyencuong.truyenfull.R;
import com.nguyencuong.truyenfull.constance.BlockStyle;
import com.nguyencuong.truyenfull.model.Book;
import com.nguyencuong.truyenfull.util.DensityUtils;

import java.util.List;

import static com.nguyencuong.truyenfull.widget.bookblock.HomeBlockRecyclerAdapter.ITEM_TYPE_GRID;
import static com.nguyencuong.truyenfull.widget.bookblock.HomeBlockRecyclerAdapter.ITEM_TYPE_LIST_V;


/**
 * Apply for style HOME_ITEM_STYLE_LIST_V and HOME_ITEM_STYLE_GRID
 * <p>
 * Created by Mr Cuong on 4/15/2017.
 * Email: vancuong2941989@gmail.com
 */
public class HomeBlockView extends FrameLayout implements View.OnClickListener {

    public interface OnViewMoreListener {
        void onHomeBlockViewMoreClick(String urlMore);
    }

    private static final String TAG = HomeBlockView.class.getSimpleName();

    private TextView title;
    private View btnViewMoreTop;
    private View btnViewMoreBottom;

    private HomeBlockRecyclerAdapter adapter;

    private OnViewMoreListener onViewMoreListener;

    private int style;

    private String urlViewMore;


    public HomeBlockView(@NonNull Context context, int style) {
        super(context);
        this.style = style;
        init();
    }

    public HomeBlockView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        style = BlockStyle.STYLE_DEFAULT;
        init();
    }

    private void init() {
        boolean isTablet = getContext().getResources().getBoolean(R.bool.isTablet);

        LayoutInflater.from(getContext()).inflate(R.layout.widget_book_block, this);

        title = (TextView) findViewById(R.id.widget_block_tv_title);
        btnViewMoreTop = findViewById(R.id.widget_block_tv_more);
        btnViewMoreTop.setOnClickListener(this);
        btnViewMoreBottom = findViewById(R.id.widget_block_tv_more2);
        btnViewMoreBottom.setOnClickListener(this);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.widget_block_recyclerview);

        RecyclerView.LayoutManager layoutManager;
        int itemWidth;
        int itemType;

        switch (style) {
            default:
                int spanCount = 3; // mobile.
                if (isTablet) {
                    spanCount = 6;
                }
                float screenWidth = DensityUtils.getWidthInPx(getContext());
                int margin = getContext().getResources().getDimensionPixelSize(R.dimen.space_8);
                int pading = getContext().getResources().getDimensionPixelSize(R.dimen.space_4);

                itemType = ITEM_TYPE_GRID;
                itemWidth = (int) ((screenWidth - margin*(spanCount - 1)) / spanCount);
                layoutManager = new GridLayoutManager(getContext(), spanCount);

                recyclerView.setPadding(pading, 0, pading, 0);
                break;

            case BlockStyle.STYLE_1:
                itemType = ITEM_TYPE_LIST_V;
                itemWidth = 0;
                layoutManager = new LinearLayoutManager(getContext());
                break;
        }

        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new HomeBlockRecyclerAdapter(itemType, itemWidth);
        recyclerView.setAdapter(adapter);
    }

    public void setTextTitle(String text) {
        title.setText(text);
    }

    public void setUrlViewMore(String urlViewMore) {
        this.urlViewMore = urlViewMore;
    }

    public void setListBooks(List<Book> list) {
        adapter.setListBooks(list);
    }

    public void setOnItemClickListener(HomeBlockRecyclerAdapter.OnItemClickListener onItemClickListener) {
        adapter.setOnItemClickListener(onItemClickListener);
    }

    public void setOnViewMoreListener(OnViewMoreListener onViewMoreListener) {
        this.onViewMoreListener = onViewMoreListener;
    }

    @Override
    public void onClick(View view) {
        if (onViewMoreListener != null && urlViewMore != null) {
            if (view == btnViewMoreTop || view == btnViewMoreBottom) {
                onViewMoreListener.onHomeBlockViewMoreClick(urlViewMore);
            }
        }
    }
}
