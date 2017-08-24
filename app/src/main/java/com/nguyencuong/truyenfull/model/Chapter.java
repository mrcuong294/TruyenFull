package com.nguyencuong.truyenfull.model;

/**
 * The Class
 * Created by pc on 8/24/2017.
 */

public class Chapter {

    private long id;

    private String name;

    private String url;

    private String content;

    public Chapter() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
