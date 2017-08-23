package com.nguyencuong.truyenfull.model;

import java.util.ArrayList;

/**
 * The Class
 * Created by pc on 8/22/2017.
 */

public class Block {
    private int style;
    private String title;
    private String url;
    private ArrayList<Book> listBooks;

    public int getStyle() {
        return style;
    }

    public void setStyle(int style) {
        this.style = style;
    }

    public String getTitle() {
        if (title == null) return "";
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        if (url == null) return "";
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public ArrayList<Book> getListBooks() {
        if (listBooks == null) listBooks = new ArrayList<>();
        return listBooks;
    }

    public void setListBooks(ArrayList<Book> listBooks) {
        this.listBooks = listBooks;
    }
}
