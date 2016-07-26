package com.nblagonravova.sovetstudentu.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nblagonravova.sovetstudentu.R;
import com.nblagonravova.sovetstudentu.parsers.ArticleParser;

import java.util.concurrent.ExecutionException;


public class ArticleFragment extends Fragment {
    private static String LOG_TAG = ArticleFragment.class.getSimpleName();
    private static final String ARG_CONTENT_PATH = "arg_content_path";

    private String mContent;

    private TextView mContentTextView;


    public static ArticleFragment newInstance(String articleContentPath){
        Bundle bundle = new Bundle();
        bundle.putString(ARG_CONTENT_PATH, articleContentPath);
        ArticleFragment fragment = new ArticleFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String contentPath = getArguments().getString(ARG_CONTENT_PATH);

        ArticleParser parser = new ArticleParser();
        parser.execute(contentPath);
        try {
            mContent = parser.get();
        } catch (InterruptedException ie) {
            Log.e(LOG_TAG, "Parser not returned result", ie);
        } catch (ExecutionException ee) {
            Log.e(LOG_TAG, "Parser not returned result", ee);
        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.article_fragment, container, false);
        mContentTextView = (TextView) view.findViewById(R.id.article_fragment_content);
        mContentTextView.setText(Html.fromHtml(mContent));

        return  view;

    }
}
