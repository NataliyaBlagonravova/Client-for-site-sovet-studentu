package com.nblagonravova.sovetstudentu.fragments;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nblagonravova.sovetstudentu.R;
import com.nblagonravova.sovetstudentu.activities.ArticleActivity;
import com.nblagonravova.sovetstudentu.listeners.EndlessRecyclerOnScrollListener;
import com.nblagonravova.sovetstudentu.model.Article;
import com.nblagonravova.sovetstudentu.parsers.ArticleListParser;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import it.sephiroth.android.library.picasso.Picasso;

public class ArticleListFragment extends Fragment{
    private static  String LOG_TAG = ArticleListFragment.class.getSimpleName();
    private static final String ARG_CATEGORY = "arg_category";

    private List<Article> mArticles = new ArrayList<>();

    private String mCategory;


    public static ArticleListFragment newInstance(String category){
        Bundle args = new Bundle();
        args.putString(ARG_CATEGORY, category);
        ArticleListFragment fragment = new ArticleListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCategory  = getArguments().getString(ARG_CATEGORY);
        updateData(1);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_article_list, container, false);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(
                R.id.news_fragment_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(new ArticlesRecyclerViewAdapter(mArticles));



        recyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int currentPage) {
                updateData(currentPage);
            }
        });

        return view;
    }

    private class ArticleHolder extends RecyclerView.ViewHolder{

        private Article mArticle;

        private TextView mTitleTextView;
        private ImageView mImage;
        private TextView mAuthorTextView;
        private TextView mPublicationDateTextView;

        public ArticleHolder(View itemView) {
            super(itemView);

            mTitleTextView = (TextView) itemView.findViewById(R.id.article_list_item_title);
            mImage = (ImageView) itemView.findViewById(R.id.article_list_item_image);
            mAuthorTextView = (TextView) itemView.findViewById(
                    R.id.article_list_item_author);
            mPublicationDateTextView = (TextView) itemView.findViewById(
                    R.id.article_list_item_publication_date);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = ArticleActivity.newIntent(
                            getActivity(), mArticle.getContentPath());
                    startActivity(intent);
                }
            });
        }

        public void bindArticle(final Article article){
            mArticle = article;
            mAuthorTextView.setText(article.getAuthor());
            mPublicationDateTextView.setText(article.getPublicationDate());

            Picasso.Builder builder = new Picasso.Builder(getActivity());
            builder.listener(new Picasso.Listener() {
                @Override
                public void onImageLoadFailed(Picasso picasso, Uri uri, Exception e) {
                    mImage.setVisibility(View.GONE);
                    mTitleTextView.setText(article.getTitle());
                    mTitleTextView.setVisibility(View.VISIBLE);
                }
            }).build()
                    .load(article.getImagePath())
                    .placeholder(R.drawable.placeholder)
                    .into(mImage);
        }
    }

    private class ArticlesRecyclerViewAdapter extends RecyclerView.Adapter<ArticleHolder>{
        List<Article> mArticles;

        public ArticlesRecyclerViewAdapter(List<Article> articles) {
            mArticles = articles;
        }

        @Override
        public ArticleHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View view = inflater.inflate(com.nblagonravova.sovetstudentu.R.layout.articles_list_item, parent, false);
            return new ArticleHolder(view);
        }

        @Override
        public void onBindViewHolder(ArticleHolder holder, int position) {
            Article article = mArticles.get(position);
            holder.bindArticle(article);
        }

        @Override
        public int getItemCount() {
            return mArticles.size();
        }
    }



    private void updateData(int pageNumber){
        ArticleListParser parser = new ArticleListParser(mCategory);
        Log.d(LOG_TAG, "Trying to parse page " + pageNumber + "category " + mCategory);
        parser.execute(pageNumber);
        try {
            mArticles.addAll(parser.get());
            Log.d(LOG_TAG, "Parser returned result");
        } catch (InterruptedException ie) {
            Log.e(LOG_TAG, "Parser not returned result", ie);
        } catch (ExecutionException ee) {
            Log.e(LOG_TAG, "Parser not returned result", ee);
        }
    }
}
