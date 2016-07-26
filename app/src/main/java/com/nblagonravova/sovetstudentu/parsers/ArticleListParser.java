package com.nblagonravova.sovetstudentu.parsers;

import android.os.AsyncTask;
import android.util.Log;

import com.nblagonravova.sovetstudentu.model.Article;
import com.nblagonravova.sovetstudentu.utils.StringUtils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ArticleListParser extends AsyncTask<Integer, Void, List<Article>> {
    private static String LOG_TAG = ArticleListParser.class.getSimpleName();
    private static final String URL = "http://xn--b1aecb4bbudibdie.xn--p1ai/";

    private String mCategoty;

    public ArticleListParser(String category) {
        mCategoty = category;
    }

    @Override
    protected List<Article> doInBackground(Integer... pageNumber) {
        List<Article> articles = new ArrayList<>();

        String url = URL + "/" + mCategoty +  "/page/" + pageNumber[0];

        try {
            Document document = Jsoup.connect(url).get();
            Elements headers = document.select(".posthead");
            Elements contents = document.select(".postcontent");

            for (int i = 0; i < headers.size(); ++i){
                String title = headers.get(i).select("h2").text();
                String author = headers.get(i).select("ul li").get(1).text();
                String publicationDate =  headers.get(i).select("ul li").first().text();
                String imagePath = StringUtils.convertToPunycode(contents.get(i).select("p img").attr("src"));
                String contentPath = contents.get(i).select("a").attr("href");

                Log.d(LOG_TAG, "Title: " + title);
                Log.d(LOG_TAG, "Author: " + author);
                Log.d(LOG_TAG, "Publication date: " + publicationDate);
                Log.d(LOG_TAG, "Image path: " + imagePath);
                Log.d(LOG_TAG, "Content path: " + contentPath);

                Article article = new Article();
                article.setTitle(title);
                article.setAuthor(author);
                article.setPublicationDate(publicationDate);
                article.setImagePath(imagePath);
                article.setContentPath(contentPath);

                articles.add(article);
            }

        } catch (IOException ioe) {
            Log.e(LOG_TAG, "Parsing + " + url + " error");
        }


        return articles;
    }
}