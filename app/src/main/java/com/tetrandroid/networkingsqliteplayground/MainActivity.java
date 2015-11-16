package com.tetrandroid.networkingsqliteplayground;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    private static final String BASE_URL = "http://www.tetrandroid.com";
    private static final String ARTICLES_ENDPOINT = "/articles.php";
    private static final String VOLLEY_TAG = "volley";
    private static final String LOG_TAG = "MainActivity";

    private ArrayList<Article> articleData;
    private ArticlesAdapter listAdapter;
    private ArticleReaderDbHelper dbHelper;

    @Bind(R.id.list) ListView listView;
    @Bind(R.id.empty_list_view) TextView emptyView;

    private Response.Listener<GetArticlesResponse> responseListener =
            new Response.Listener<GetArticlesResponse>() {
        @Override
        public void onResponse(GetArticlesResponse response) {
            if (response.getSuccess() == 1) {
                ArrayList<Article> articles = response.getArticles();

                updateUi(articles);

                // Write articles to database
                dbHelper.insertArticles(articles);
            }
        }};

    private Response.ErrorListener errorListener= new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Toast.makeText(MainActivity.this, "An error occurred. Try again later.",
                    Toast.LENGTH_SHORT).show();
        }};

    @OnClick(R.id.download_data) void downloadArticles(){
        String url = BASE_URL + ARTICLES_ENDPOINT;
        GsonVolleyRequest<GetArticlesResponse> articlesRequest = new GsonVolleyRequest<>(url,
                GetArticlesResponse.class, null,responseListener, errorListener);
        articlesRequest.setTag(VOLLEY_TAG);
        MySingletonRequestQueue.getInstance(this).getRequestQueue().add(articlesRequest);
    }

    @OnClick(R.id.load_data) void readArticlesDromDb(){
        ArrayList<Article> articles = dbHelper.readArticles();
        updateUi(articles);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        dbHelper = new ArticleReaderDbHelper(MainActivity.this);

        articleData = new ArrayList<>();
        listAdapter = new ArticlesAdapter(MainActivity.this, articleData);
        listView.setEmptyView(emptyView);
        listView.setAdapter(listAdapter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        dbHelper.close();
        MySingletonRequestQueue.getInstance(this).getRequestQueue().cancelAll(VOLLEY_TAG);
    }

    private void updateUi(ArrayList<Article> articles) {
        articleData.clear();
        articleData.addAll(articles);
        listAdapter.notifyDataSetChanged();
    }
}