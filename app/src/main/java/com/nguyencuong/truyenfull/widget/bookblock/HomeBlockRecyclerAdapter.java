package com.nguyencuong.truyenfull.widget.bookblock;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nguyencuong.truyenfull.R;
import com.nguyencuong.truyenfull.model.Book;

import java.util.ArrayList;
import java.util.List;

/**
 * The adater for {@link RecyclerView} in {@link HomeBlockView}
 * use with {@link HomeBlockView} #HOME_ITEM_STYLE_GRID #HOME_ITEM_STYLE_LIST_H #HOME_ITEM_STYLE_LIST_V
 * and type is {@link Book}
 * <p>
 * Created by Mr Cuong on 4/15/2017.
 * Email: vancuong2941989@gmail.com
 */

public class HomeBlockRecyclerAdapter extends RecyclerView.Adapter {

    public interface OnItemClickListener {
        void onBookItemClick(String url);
    }

    public static final int ITEM_TYPE_GRID = 0;
    public static final int ITEM_TYPE_LIST_V = 1;
    public static final int ITEM_TYPE_LIST_H = 2;

    private final int itemType;

    // Use on GridViewHolder
    private final int itemWidth;

    // Use on GridViewHolder
    private final int itemHeight;

    private ArrayList<Book> listBooks = new ArrayList<>();

    private OnItemClickListener onItemClickListener;

    public HomeBlockRecyclerAdapter(int itemType, int itemWidth) {
        this.itemType = itemType;
        this.itemWidth = itemWidth;

        itemHeight = itemWidth * 49/33;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = null;
        switch (itemType) {
            case ITEM_TYPE_GRID:
                itemView = inflater.inflate(R.layout.widget_books_block_0, parent, false);
                return new GridViewHolder(itemView);
            case ITEM_TYPE_LIST_V:
                itemView = inflater.inflate(R.layout.widget_books_block_1, parent, false);
                return new ListVerticalViewHolder(itemView);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof GridViewHolder) {
            ((GridViewHolder) holder).bindView(listBooks.get(position));
        } else if (holder instanceof ListVerticalViewHolder) {
            ((ListVerticalViewHolder) holder).bindView(listBooks.get(position));
        }
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return listBooks.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public Book getBook(int position) {
        if (position > -1 && position < listBooks.size()) {
            return listBooks.get(position);
        }
        return null;
    }

    public void setListBooks(List<Book> list) {
        if (list == null) return;
        listBooks = (ArrayList<Book>) list;
        notifyDataSetChanged();
    }

    public void addAll(List<Book> list) {
        listBooks.addAll(list);
        notifyDataSetChanged();
    }

    public void clear() {
        listBooks.clear();
        notifyDataSetChanged();
    }

    /**
     * View holder of recycler view grid and horizontal;
     */
    public class GridViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView poster;
        private TextView name;
        private TextView chapter;
        private View iconFull, iconNew, iconHot;

        public GridViewHolder(View itemView) {
            super(itemView);

            poster = (ImageView) itemView.findViewById(R.id.book_poster);
            name = (TextView) itemView.findViewById(R.id.book_name);
            chapter = (TextView) itemView.findViewById(R.id.book_chapter);
            iconFull = itemView.findViewById(R.id.book_label_full);
            iconNew = itemView.findViewById(R.id.book_label_new);
            iconHot = itemView.findViewById(R.id.book_label_hot);

            itemView.setOnClickListener(this);
            poster.setOnClickListener(this);
        }

        public void bindView(Book book) {

            if (itemType == ITEM_TYPE_GRID) {
                poster.getLayoutParams().width = itemWidth;
                poster.getLayoutParams().height = itemHeight;
            }

            Glide.with(itemView.getContext())
                    .load(book.getPoster())
                    .error(R.drawable.default_poster)
                    .into(poster);

            name.setText(book.getName());

            if (book.getChapter() == null || book.getChapter().length() == 0) {
                chapter.setVisibility(View.GONE);
            } else {
                chapter.setVisibility(View.VISIBLE);
                chapter.setText(String.format(
                        itemView.getContext().getResources().getString(R.string.widget_books_block_item_grid_text_chapter),
                        book.getChapter(),
                        book.getChapter()
                ));
            }
            iconFull.setVisibility(book.isFull() ? View.VISIBLE : View.GONE);
            iconNew.setVisibility(book.isNew() ? View.VISIBLE : View.GONE);
            iconHot.setVisibility(book.isHot() ? View.VISIBLE : View.GONE);
        }

        @Override
        public void onClick(View view) {
            if (onItemClickListener != null) {
                String bookUrl = listBooks.get(getLayoutPosition()).getUrl();
                onItemClickListener.onBookItemClick(bookUrl);
            }
        }
    }

    /**
     * View holder of recycler view horizontal;
     */
    public class ListVerticalViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView name;
        private TextView categorys;
        private TextView chapter;
        private View iconFull, iconNew, iconHot;

        public ListVerticalViewHolder(View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.book_name);
            categorys = (TextView) itemView.findViewById(R.id.book_ctg);
            chapter = (TextView) itemView.findViewById(R.id.book_chapter);
            iconFull = itemView.findViewById(R.id.book_label_full);
            iconNew = itemView.findViewById(R.id.book_label_new);
            iconHot = itemView.findViewById(R.id.book_label_hot);

            itemView.setOnClickListener(this);
        }

        public void bindView(Book book) {

            name.setText(book.getName());

            categorys.setText(book.getCategory());

            chapter.setText(book.getChapter() + " | " + book.getTimeUpdate());

            iconFull.setVisibility(book.isFull() ? View.VISIBLE : View.GONE);
            iconNew.setVisibility(book.isNew() ? View.VISIBLE : View.GONE);
            iconHot.setVisibility(book.isHot() ? View.VISIBLE : View.GONE);
        }

        @Override
        public void onClick(View view) {
            if (onItemClickListener != null) {
                String bookUrl = listBooks.get(getLayoutPosition()).getUrl();
                onItemClickListener.onBookItemClick(bookUrl);
            }
        }
    }
}
