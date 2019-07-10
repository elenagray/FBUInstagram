package com.example.fbu_instagram;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.fbu_instagram.model.Post;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

public class NewsFeed extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_feed);

        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
        // Specify the object id
        query.getInBackground("aFuEsvjoHt", new GetCallback<Post>() {
            public void done(Post item, ParseException e) {
                if (e == null) {


                } else {
                    // something went wrong
                }
            }
        });
    }
}
