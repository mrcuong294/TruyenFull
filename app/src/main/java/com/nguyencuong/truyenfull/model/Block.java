package com.nguyencuong.truyenfull.model;

import java.util.ArrayList;

/**
 * The Class
 * Created by pc on 8/22/2017.
 */

public class Block {
    String title;
    String url;
    ArrayList<Book> listBooks;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public ArrayList<Book> getListBooks() {
        return listBooks;
    }

    public void setListBooks(ArrayList<Book> listBooks) {
        this.listBooks = listBooks;
    }
}
