package com.nguyencuong.truyenfull.model;

/**
 * The Class
 * Created by pc on 8/22/2017.
 */

public class Book {

    private String name;

    private String url;

    private boolean isFull;

    private String linkPoster;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLinkPoster() {
        return linkPoster;
    }

    public void setLinkPoster(String linkPoster) {
        this.linkPoster = linkPoster;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isFull() {
        return isFull;
    }

    public void setFull(boolean full) {
        isFull = full;
    }
}
