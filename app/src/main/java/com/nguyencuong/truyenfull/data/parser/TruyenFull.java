package com.nguyencuong.truyenfull.data.parser;

import com.nguyencuong.truyenfull.constance.BlockStyle;
import com.nguyencuong.truyenfull.model.Block;
import com.nguyencuong.truyenfull.model.Book;
import com.nguyencuong.truyenfull.model.Category;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

/**
 * The Class
 * Created by pc on 8/23/2017.
 */

public class TruyenFull {

    private static final String TAG = TruyenFull.class.getSimpleName();

    private static final String URL_PAGE = "http://truyenfull.vn/";

    private static TruyenFull instance = new TruyenFull();

    private Document document;

    public TruyenFull() {}

    /**
     * Get instance;
     *
     * @return Instance {@link TruyenFull}
     */
    public static TruyenFull getInstance() {
        return instance;
    }

    /**
     * Get home data, call in {@link com.nguyencuong.truyenfull.ui.home.HomeActivity};
     *
     * @return list {@link Block} home data;
     */
    public ArrayList<Block> getHomeBlocks() {
        try {
            document = Jsoup.connect(URL_PAGE).get();
            if (document != null) {
                ArrayList<Block> listBlocks = new ArrayList<>();

                // Get Home > truyen hot
                Element introElement = document.select("#intro-index").first();
                if (introElement != null) {
                    Block blockHot = new Block();
                    blockHot.setStyle(BlockStyle.STYLE_DEFAULT);

                    Element subjectElement = introElement.getElementsByTag("a").first();
                    blockHot.setTitle(subjectElement.text());
                    blockHot.setUrl(subjectElement.attr("href"));

                    Elements contentElements = introElement.select(".index-intro > div > a");
                    ArrayList<Book> list = new ArrayList<>();
                    for (Element element : contentElements) {
                        Book book = new Book();
                        book.setName(element.text());
                        book.setUrl(element.attr("href"));
                        book.setFull(element.select("span").first() != null);
                        Element imgElement = element.select("img").first();
                        if (imgElement != null) {
                            book.setLinkPoster(imgElement.attr("src"));
                        }
                        list.add(book);
                    }
                    blockHot.setListBooks(list);
                    listBlocks.add(blockHot);
                }

                // Get Home > truyen moi cap nhat
                Element newElement = document.select(".list-new").first();
                if(newElement != null) {
                    Block blockNew = new Block();
                    blockNew.setStyle(BlockStyle.STYLE_1);

                    Element subjectElement = newElement.select(".title-list > h2 > a").first();
                    blockNew.setTitle(subjectElement.text());
                    blockNew.setUrl(subjectElement.attr("href"));

                    Elements contentElements = newElement.select("div.row");
                    ArrayList<Book> list = new ArrayList<>();
                    for (Element element : contentElements) {
                        Book book = new Book();

                        Element subjectBookElement = element.select("div.col-title a").first();
                        book.setName(subjectBookElement.text());
                        book.setUrl(subjectBookElement.attr("href"));

                        if (element.select("span.label-hot").first() != null) {
                            book.setHot(true);
                        }
                        book.setChapter(element.select(".col-chap").first().text());
                        book.setTimeUpdate(element.select(".col-time").first().text());
                        list.add(book);
                    }
                    blockNew.setListBooks(list);
                    listBlocks.add(blockNew);
                }

                // Get Home > truyen hoan thanh
                Element fullElement = document.select("div.list-thumbnail").first();
                if(fullElement != null) {
                    Block blockFull = new Block();
                    blockFull.setStyle(BlockStyle.STYLE_2);

                    Element subjectElement = fullElement.select(".title-list > h2 > a").first();
                    blockFull.setTitle(subjectElement.text());
                    blockFull.setUrl(subjectElement.attr("href"));

                    Elements contentElements = fullElement.select("div.row > div > a");
                    ArrayList<Book> list = new ArrayList<>();
                    for (Element element : contentElements) {
                        Book book = new Book();

                        book.setName(element.attr("title"));
                        book.setUrl(element.attr("href"));
                        Element imgElement = element.select("img").first();
                        if (imgElement != null) {
                            book.setLinkPoster(imgElement.attr("src"));
                        }
                        book.setChapter(element.select(".caption > small").first().text());
                        list.add(book);
                    }
                    blockFull.setListBooks(list);
                    listBlocks.add(blockFull);
                }

                return listBlocks;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }

    /**
     * Get category in homepage;
     *
     * @return list {@link Category}
     */
    public ArrayList<Category> getHomeCategory() {
        if (document == null) {
            try {
                document = Jsoup.connect(URL_PAGE).get();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (document != null) {
            ArrayList<Category> listCats = new ArrayList<>();

            Elements catsElement = document.select(".list-cat a");
            for (Element element : catsElement) {
                Category category = new Category();
                category.setName(element.text());
                category.setUrl(element.attr("href"));

                listCats.add(category);
            }

            return listCats;
        }

        return new ArrayList<>();
    }
}
