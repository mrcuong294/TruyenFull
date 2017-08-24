package com.nguyencuong.truyenfull;

import com.google.gson.Gson;
import com.nguyencuong.truyenfull.model.Book;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

/**
 * The Class
 * Created by pc on 8/22/2017.
 */

public class DomParserHtmlTest {

    private static final String URL_PAGE = "http://truyenfull.vn/";

    private Document doc;

    @Test
    public void getPageIntroTest() throws Exception {
        doc = Jsoup.connect(URL_PAGE).get();
        if (doc != null) {
            Element introElement = doc.select("#intro-index").first();

            if (introElement != null) {

                Element subjectElement = introElement.getElementsByTag("a").first();
                System.out.println("\n" + subjectElement.text() + " | link = " + subjectElement.attr("href"));

                Elements contentElements = introElement.select(".index-intro > div > a");
                for (Element element : contentElements) {
                    Book book = new Book();
                    book.setName(element.text());
                    book.setUrl(element.attr("href"));
                    book.setFull(element.select("span").first() != null);
                    Element imgElement = element.select("img").first();
                    if (imgElement != null) {
                        book.setPoster(imgElement.attr("src"));
                    }

                    System.out.println("-----\n" + new Gson().toJson(book));
                }

            } else {
                System.out.println("introElement IS NULL");
            }

        } else {
            System.out.println("DOC is NULL");
        }
    }

}
