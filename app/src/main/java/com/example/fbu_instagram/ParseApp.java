package com.example.fbu_instagram;

import android.app.Application;

import com.example.fbu_instagram.model.Post;
import com.parse.Parse;
import com.parse.ParseObject;

public class ParseApp extends Application {
    @Override
    public void onCreate(){
        super.onCreate();

        //register subclass, tells parse we made this
        ParseObject.registerSubclass(Post.class);
        final Parse.Configuration configuration = new Parse.Configuration.Builder(this)
                .applicationId("banana-pancakes").clientKey("Dreamer787898")
                .server("http://elenagray-fbu-instagram-real.herokuapp.com/parse")
                .build();
        Parse.initialize(configuration);
    }
}
