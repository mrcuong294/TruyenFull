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
}
