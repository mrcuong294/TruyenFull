package com.nguyencuong.truyenfull.data.parser;

import com.nguyencuong.truyenfull.constance.BlockStyle;
import com.nguyencuong.truyenfull.model.Block;
import com.nguyencuong.truyenfull.model.Book;
import com.nguyencuong.truyenfull.model.Category;
import com.nguyencuong.truyenfull.model.Chapter;
import com.nguyencuong.truyenfull.ui.activity.main.MainActivity;
import com.nguyencuong.truyenfull.util.IntUtils;

import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * The Class
 * Created by pc on 8/23/2017.
 */

public class TruyenFullParser implements ParserInterface {

    private static final String TAG = TruyenFullParser.class.getSimpleName();

    private static final String URL_PAGE = "http://truyenfull.vn/";

    private static final long EXPIRES_HASH = 30 * 60 * 1000; // Milisecond <=> 30 phut

    private static TruyenFullParser instance = new TruyenFullParser();

    private static String hashKey;

    private static long timeGetHash; // Milisecond

    private Document document;

    public TruyenFullParser() {}

    /**
     * Get instance;
     *
     * @return Instance {@link TruyenFullParser}
     */
    public static TruyenFullParser getInstance() {
        return instance;
    }

    /**
     * Load home document Dom;
     */
    @Override
    public void loadHomeDocument() {
        if (document == null || document.select("body#body_home").first() == null) {
            try {
                document = Jsoup.connect(URL_PAGE).get();
            } catch (IOException e) {
                document = null;
                //LogUtils.d(TAG, "Document is Null, Msg: " + e.getMessage());
            }
        }
    }

    /**
     * Load book info and list chapter;
     *
     * @param url link book;
     */
    @Override
    public void loadBookDocument(String url) {
        if (document == null || document.select("body#body_truyen").first() == null) {
            try {
                document = Jsoup.connect(url).get();
            } catch (IOException e) {
                document = null;
                //LogUtils.d(TAG, "Document is Null, Msg: " + e.getMessage());
            }
        }
    }

    /**
     * Get category in homepage;
     *
     * @return list {@link Category}
     */
    @Override
    public ArrayList<Category> getHomeCategorys() {
        loadHomeDocument();

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

    /**
     * Get home data, call in {@link MainActivity};
     *
     * @return list {@link Block} home data;
     */
    @Override
    public ArrayList<Block> getHomeBlocks() {
        loadHomeDocument();

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

                    book.setNew(element.select("span.label-new").first() != null);
                    book.setHot(element.select("span.label-hot").first() != null);
                    book.setFull(element.select("span.full-label").first() != null);

                    Element imgElement = element.select("img").first();
                    if (imgElement != null) {
                        book.setPoster(imgElement.attr("src"));
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

                    book.setNew(element.select("span.label-new").first() != null);
                    book.setHot(element.select("span.label-hot").first() != null);
                    book.setFull(element.select("span.label-full").first() != null);

                    book.setCategory(element.select(".col-cat").first().text());

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
                blockFull.setStyle(BlockStyle.STYLE_DEFAULT);

                Element subjectElement = fullElement.select(".title-list > h2 > a").first();
                blockFull.setTitle(subjectElement.text());
                blockFull.setUrl(subjectElement.attr("href"));

                Elements contentElements = fullElement.select("div.row > div > a");
                ArrayList<Book> list = new ArrayList<>();
                for (Element element : contentElements) {
                    Book book = new Book();

                    book.setName(element.attr("title"));
                    book.setUrl(element.attr("href"));

                    book.setNew(element.select("span.label-new").first() != null);
                    book.setHot(element.select("span.label-hot").first() != null);
                    book.setFull(true);

                    Element imgElement = element.select("div.lazyimg").first();
                    if (imgElement != null) {
                        book.setPoster(imgElement.attr("data-image"));
                    }

                    book.setChapter(element.select(".caption > small").first().text());
                    book.setChapter(String.valueOf(IntUtils.convertStringToInt(book.getChapter())));
                    list.add(book);
                }
                blockFull.setListBooks(list);
                listBlocks.add(blockFull);
            }

            return listBlocks;
        }

        return new ArrayList<>();
    }

    /**
     * Get list hot / full / category;
     *
     * @param url the main url of page
     * @param isFull filter book full chapter;
     * @param page   the pager of page
     * @return The {@link Block} book
     */
    @Override
    public ArrayList<Book> getListBook(String url, boolean isFull, int page) {
        ArrayList<Book> listBooks = new ArrayList<>();
        if (url == null || url.length() < 5) return listBooks;

        // Build url connect;
        if (!url.substring(url.length() - 1).equalsIgnoreCase("/")) {
            url = url + "/";
        }

        if (isFull) {
            url += "hoan/";
        }

        if (page > 1) {
            url += "trang-" + page + "/";
        }

        try {
            document = Jsoup.connect(url).get();
            if (document != null) {
                Elements listElement = document.select(".col-truyen-main .list-truyen > div.row");
                for (Element element : listElement) {
                    Book book = new Book();

                    Element coverElement = element.select("div.lazyimg").first();
                    if(coverElement != null) {
                        book.setPoster(coverElement.attr("data-image"));
                    }

                    Element subjectElement = element.select("h3.truyen-title a").first();
                    book.setName(subjectElement.text());
                    book.setUrl(subjectElement.attr("href"));

                    book.setNew(element.select("span.label-new").first() != null);
                    book.setHot(element.select("span.label-hot").first() != null);
                    book.setFull(element.select("span.label-full").first() != null);

                    book.setAuthor(element.select("span.author").first().text());
                    book.setChapter(element.select("span.chapter-text").first().text());

                    listBooks.add(book);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return listBooks;
    }

    /**
     * Get book info;
     *
     * @param url link page book info;
     * @return The {@link Book}
     */
    @Override
    public Book getBookInfo(String url) {
        loadBookDocument(url);

        if (document != null) {
            Book book = new Book();

            book.setId(document.getElementById("truyen-id").attr("value"));

            Element infoDescElement = document.select("div.col-info-desc").first();

            Element imgElement = infoDescElement.select("img").first();
            if (imgElement != null) {
                book.setPoster(imgElement.attr("src"));
            }

            Element titleElement = infoDescElement.select("h3.title").first();
            if (titleElement != null) {
                book.setName(titleElement.text());
            }

            Element infoElement = infoDescElement.select("div.info").first();

            for (Element element : infoElement.select("div")) {
                if (element.select("a").first() != null) {
                    // Author or category
                    if (element.select("a").first().attr("itemprop").equalsIgnoreCase("author")) {
                        book.setAuthor(element.select("a").first().text());
                    } else {
                        book.setCategory(element.text().replace("Thể loại:", ""));
                    }
                } else if (element.select(".source").first() != null){
                    book.setSource(element.select(".source").first().text());
                } else if (element.select(".text-primary").first() != null){
                    book.setFull(false);
                } else if (element.select(".text-success").first() != null){
                    book.setFull(true);
                }
            }

            // rating
            for (Element element : infoDescElement.select(".rate span")) {
                if (element.attr("itemprop").equalsIgnoreCase("ratingValue")) {
                    book.setRating(Double.parseDouble(element.text()));
                } else if (element.attr("itemprop").equalsIgnoreCase("ratingCount")) {
                    book.setRatingCount(Long.parseLong(element.text()));
                }
            }

            Element descElement = infoDescElement.select(".desc-text").first();
            if (descElement != null) {
                book.setDescription(descElement.html());
            }

            Element firstchapElement = document.select("#list-chapter .list-chapter li a").first();
            if (firstchapElement != null) {
                book.setFirstChapterUrl(firstchapElement.attr("href"));
            }

            return book;
        }

        return null;
    }

    /**
     * Get list new chapter of book;
     *
     * @param url link book
     * @return list chapter;
     */
    @Override
    public ArrayList<Chapter> getBookNewChapters(String url) {
        loadBookDocument(url);
        ArrayList<Chapter> listChapter = new ArrayList<>();

        if (document != null) {
            for (Element element : document.select("ul.l-chapters li a")) {
                Chapter chapter = new Chapter();
                chapter.setName(element.text());
                chapter.setUrl(element.attr("href"));

                listChapter.add(chapter);
            }
        }
        return listChapter;
    }

    /**
     * Get list chapter of book without page;
     *
     * @param url link book;
     * @param page page number;
     * @return list {@link Chapter}
     */
    @Override
    public ArrayList<Chapter> getListChapter(String url, int page) {

        // Load book id & total page chapter;
        int bookId = getBookId(url);
        int totalPage = getBookTotalPageChapter(url);

        if (bookId == 0 || totalPage == 0 || page > totalPage) {
            return new ArrayList<>();
        }

        // Build url load;
        String urlAjax = URL_PAGE + "/ajax.php?type=list_chapter";
        urlAjax += "&tid=" + bookId;
        urlAjax += "&page=" + page + "&totalp=" + totalPage;
        urlAjax += "&hash=" + getAjaxHashKey();

        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(urlAjax)
                    .build();

            Response response = client.newCall(request).execute();

            String json = response.body().string();
            JSONObject jsonObject = new JSONObject(json);

            if (jsonObject.has("chap_list")) {
                String html = jsonObject.getString("chap_list");
                Document doc = Jsoup.parseBodyFragment(html);

                ArrayList<Chapter> list = new ArrayList<>();

                for (Element element : doc.select(".list-chapter li a")) {
                    Chapter chapter = new Chapter();
                    chapter.setName(element.text());
                    chapter.setUrl(element.attr("href"));
                    list.add(chapter);
                }
                return list;
            }

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }

    /**
     * Get Book id;
     *
     * @param url link book;
     * @return book id;
     */
    public int getBookId(String url) {
        loadBookDocument(url);

        if (document != null) {
            return Integer.parseInt(document.getElementById("truyen-id").attr("value"));
        }
        return 0;
    }

    /**
     * Get total page chapter
     * @param url link book;
     * @return total page;
     */
    public int getBookTotalPageChapter(String url) {
        loadBookDocument(url);

        if (document != null) {
            return Integer.parseInt(document.getElementById("total-page").attr("value"));
        }
        return 0;
    }

    /**
     * Get hash key use load ajax;
     *
     * @return The hash key;
     */
    public String getAjaxHashKey() {
        if (hashKey != null && System.currentTimeMillis() - timeGetHash < EXPIRES_HASH) {
            return hashKey;
        }

        try {
            String url = URL_PAGE + "ajax.php?type=hash";
            Document doc = Jsoup.connect(url).get();
            if (doc != null) {
                hashKey = doc.select("body").text();
                timeGetHash = System.currentTimeMillis();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return hashKey;
    }

    /**
     * Quick search;
     *
     * @param hash key
     * @return List {@link Book};
     */
    public String quickSearch(String hash) {
        String url = URL_PAGE + "ajax.php?type=quick_search&str=tien+&hash=" + hash;
        try {
            Document doc = Jsoup.connect(url).get();
            if (doc != null) return doc.select("body").text();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "No result!";
    }

    /**
     * Get list top book;
     *
     * @param typeTime  = day / month / all
     * @param catId the id of category;
     * @return List {@link Book}
     */
    public ArrayList<Book> getTopBook(String typeTime, int catId) {
        String url = URL_PAGE + "ajax.php?type=top_switch";

        if (typeTime == null || typeTime.length() == 0) {
            typeTime = "all";
        }
        url += "&data_type=" + typeTime;

        if (catId > 0) url += "&data_cat=" + catId;
        url +="&hash=" + hashKey;

        try {
            Document doc = Jsoup.connect(url).get();
            if (doc != null) {

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }

    /**
     * Get chapter content and url next chapter & prev chapter;
     *
     * @param url link chapter;
     * @return The HashMap chapter info : content => content chapter; next_chap => url next chapter;
     *          prev_chap => url previous chapter;
     */
    public Map<String, String> getChapterMapInfo(String url) {
        if (url == null || url.length() < 5) {
            return null;
        }

        try {
            Document doc = Jsoup.connect(url).get();
            if (doc != null) {
                HashMap<String, String> map = new HashMap<>();

                map.put("content", doc.getElementsByClass("chapter-c").first().html());

                Element nextChapElm = doc.getElementById("next_chap");
                if (!nextChapElm.hasClass("disabled")) {
                    map.put("next_chap", nextChapElm.attr("href"));
                }

                Element prevChapElm = doc.getElementById("prev_chap");
                if (!prevChapElm.hasClass("disabled")) {
                    map.put("prev_chap", prevChapElm.attr("href"));
                }

                return map;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

}
