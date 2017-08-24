package com.nguyencuong.truyenfull.data.parser;

import com.nguyencuong.truyenfull.model.Block;
import com.nguyencuong.truyenfull.model.Book;
import com.nguyencuong.truyenfull.model.Category;

import java.util.ArrayList;

/**
 * Content class.
 * <p>
 * Created by Mr Cuong on 8/23/2017.
 * Email: vancuong2941989@gmail.com
 */

public interface ParserInterface {

    /**
     * Load home document Dom;
     */
    void loadHomeDocument();

    /**
     * Load book info and list chapter;
     *
     * @param url link book;
     * @param page pager chapter;
     */
    void loadBookDocument(String url, int page);

    /**
     * Get category in homepage;
     *
     * @return list {@link Category}
     */
    ArrayList<Category> getHomeCategorys();

    /**
     * Get home data, call in {@link com.nguyencuong.truyenfull.ui.home.HomeActivity};
     *
     * @return list {@link Block} home data;
     */
    ArrayList<Block> getHomeBlocks();

    /**
     * Get list hot / full / category;
     *
     * @param url the main url of page
     * @param isFull filter book full chapter;
     * @param page the pager of page
     * @return The list {@link Book} book
     */
    ArrayList<Book> getListBook(String url, boolean isFull, int page);


    /**
     * Get book info;
     *
     * @param url link page book info;
     * @return The {@link Book}
     */
    Book getBookInfo(String url);
}
