package com.example.darko.topnews;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.darko.topnews.model.Articles.Articles;
import com.example.darko.topnews.model.News;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static String baseUrl = "https://newsapi.org/v2/";
    private static String TAG = "MainActivity";
    private ArrayList<Articles> articles = new ArrayList<Articles>();
    private ArrayList<String> newsTitles = new ArrayList<String>();
    
    @BindView(R.id.lv_newsTitles)
    ListView lvNewsTitles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initializeRetrofit();

    }

    public void initializeRetrofit(){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create()).build();
        NewsAPI newsAPI = retrofit.create(NewsAPI.class);
        Call<News> call = newsAPI.getNews(NewsAPI.country, NewsAPI.apiKey);
        call.enqueue(new Callback<News>() {
            @Override
            public void onResponse(Call<News> call, Response<News> response) {
                Log.d(TAG, "Response information: " + response.body().toString());
                articles = response.body().getArticles();
                setData(articles);
            }

            @Override
            public void onFailure(Call<News> call, Throwable t) {
                Log.e(TAG, "Failure: " + t.getMessage() );
            }
        });
    }

    private void setData(ArrayList<Articles> items) {
        articles = items;
        for (int i = 0; i < articles.size(); i++){
            newsTitles.add(articles.get(i).getTitle());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplication(), android.R.layout.simple_list_item_1, newsTitles);
        lvNewsTitles.setAdapter(adapter);
        lvNewsTitles.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                startNewsActivity(i);
            }
        });
    }

    private void startNewsActivity(int i) {
        Intent intent = new Intent(getApplication(), NewsActivity.class);
        intent.putExtra("TITLE", articles.get(i).getTitle());
        intent.putExtra("DESCRIPTION", articles.get(i).getDescription());
        intent.putExtra("IMAGEURL", articles.get(i).getUrlToImage());
        startActivity(intent);
    }

}
