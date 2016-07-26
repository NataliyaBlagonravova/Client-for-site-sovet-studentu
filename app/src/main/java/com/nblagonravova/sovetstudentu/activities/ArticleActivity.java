package com.nblagonravova.sovetstudentu.activities;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.nblagonravova.sovetstudentu.R;
import com.nblagonravova.sovetstudentu.fragments.ArticleFragment;

public class ArticleActivity extends AppCompatActivity{
    private static final String EXTRA_CONTENT_PATH = "extra_content_path";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);

        String articleContentPath = getIntent().getExtras().getString(EXTRA_CONTENT_PATH);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);

        if (fragment == null){
            fragment = ArticleFragment.newInstance(articleContentPath);
            fm.beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();
        }
    }

    public static Intent newIntent(Context context, String articleContentPath){
        Intent intent = new Intent(context, ArticleActivity.class);
        intent.putExtra(EXTRA_CONTENT_PATH, articleContentPath);
        return intent;
    }


}
