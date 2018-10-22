package com.example.darko.topnews;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewsActivity extends AppCompatActivity {

    private String title;
    private String description;
    private String imageUrl;

    @BindView(R.id.iv_image)
    ImageView ivImage;

    @BindView(R.id.tv_title)
    TextView tvTitle;

    @BindView(R.id.tv_description)
    TextView tvDescription;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        title = intent.getStringExtra("TITLE");
        description = intent.getStringExtra("DESCRIPTION");
        imageUrl = intent.getStringExtra("IMAGEURL");

        tvTitle.setText(title);
        tvDescription.setText(description);

        Picasso.get().load(imageUrl).into(ivImage);
    }
}
