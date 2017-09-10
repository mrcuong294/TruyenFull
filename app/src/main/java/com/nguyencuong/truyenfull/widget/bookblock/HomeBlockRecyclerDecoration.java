package com.nguyencuong.truyenfull.widget.bookblock;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.nguyencuong.truyenfull.R;


/**
 *
 * Created by pc on 3/3/2017.
 */

public class HomeBlockRecyclerDecoration extends RecyclerView.ItemDecoration {

    private final int MARGIN;

    private final int spanCount;

    public HomeBlockRecyclerDecoration(Context context, int spanCount) {
        MARGIN = context.getResources().getDimensionPixelSize(R.dimen.space_16);
        this.spanCount = spanCount;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int pos = parent.getChildAdapterPosition(view);
        if(pos == 0 || pos % spanCount == 0){
            outRect.left = MARGIN;
            outRect.right = MARGIN;
        } else {
            outRect.left = 0;
            outRect.right = MARGIN;
        }
    }
}
