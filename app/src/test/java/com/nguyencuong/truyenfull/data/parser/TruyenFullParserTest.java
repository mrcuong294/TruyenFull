package com.nguyencuong.truyenfull.data.parser;

import com.google.gson.Gson;
import com.nguyencuong.truyenfull.model.Block;
import com.nguyencuong.truyenfull.model.Book;
import com.nguyencuong.truyenfull.model.Category;

import org.junit.Test;

import java.util.ArrayList;

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

}