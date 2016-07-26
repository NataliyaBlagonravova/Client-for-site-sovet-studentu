package com.nblagonravova.sovetstudentu.parsers;


import android.os.AsyncTask;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

public class ArticleParser extends AsyncTask<String, Void, String>{
    private static String LOG_TAG = ArticleParser.class.getSimpleName();

    @Override
    protected String doInBackground(String... path) {

        StringBuilder stringBuilder = null;

        Elements elements  =null;
        Document document = null;

        try {
            document  = Jsoup.connect(path[0]).get();
            elements  = document.select("p");


//            stringBuilder = new StringBuilder();
//
//            for (Element element: elements){
//                stringBuilder.append(element.text());
//            }

        } catch (IOException e) {
            Log.e(LOG_TAG, "Parse error");
        }

        String string = elements.html();

        Log.d(LOG_TAG, string);

        //return stringBuilder.toString();
        return  string;
    }
}
