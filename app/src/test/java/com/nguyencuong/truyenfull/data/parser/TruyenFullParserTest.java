package com.nguyencuong.truyenfull.data.parser;

import com.google.gson.Gson;
import com.nguyencuong.truyenfull.model.Block;
import com.nguyencuong.truyenfull.model.Book;
import com.nguyencuong.truyenfull.model.Category;
import com.nguyencuong.truyenfull.model.Chapter;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Map;

/**
 * Content class.
 * <p>
 * Created by Mr Cuong on 8/23/2017.
 * Email: vancuong2941989@gmail.com
 */
public class TruyenFullParserTest {

    @Test
    public void getHomeCategorys() throws Exception {
        ArrayList<Category> list = TruyenFullParser.getInstance().getHomeCategorys();
        System.out.println("HomeCategorys : " + new Gson().toJson(list));
    }

    @Test
    public void getHomeBlocks() throws Exception {
        ArrayList<Block> list = TruyenFullParser.getInstance().getHomeBlocks();
        System.out.println("HomeBlocks : " + new Gson().toJson(list));
    }

    @Test
    public void getListBook() throws Exception {
        String url = "http://truyenfull.vn/the-loai/ngon-tinh/";

        ArrayList<Book> list = TruyenFullParser.getInstance().getListBook(url, true, 0);
        System.out.println("HomeBlocks : " + new Gson().toJson(list));
    }

    @Test
    public void getAjaxHashKey() {
        String hash = TruyenFullParser.getInstance().getAjaxHashKey();
        System.out.println("Hash key : " + hash);

        String result = TruyenFullParser.getInstance().quickSearch(hash);
        System.out.println("Result : " + result);
    }

    @Test
    public void getBookInfo() throws Exception {
        String url = "http://truyenfull.vn/noi-yeu-em-99-lan/";

        Book book = TruyenFullParser.getInstance().getBookInfo(url);
        if (book == null) {
            System.out.println("Book is null");
        } else {
            System.out.println("HomeBlocks : " + new Gson().toJson(book));
        }
    }

    @Test
    public void getListChapter() throws Exception {
        String url = "http://truyenfull.vn/noi-yeu-em-99-lan/";

        ArrayList<Chapter> list = TruyenFullParser.getInstance().getListChapter(url, 2);
        System.out.println("List : \n" + new Gson().toJson(list));
    }

    @Test
    public void getChapterMapInfo() throws Exception {
        String url = "http://truyenfull.vn/linh-vu-thien-ha/chuong-1/";

        Map map = TruyenFullParser.getInstance().getChapterMapInfo(url);
        if (map == null) {
            System.out.println("Map chapter is null");
        } else {
            System.out.println("Prev : " + map.get("prev_chap"));
            System.out.println("Next : " + map.get("next_chap"));
            System.out.println("Content : " + map.get("content"));
        }
    }
}