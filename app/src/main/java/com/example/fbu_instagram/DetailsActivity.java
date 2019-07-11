package com.example.fbu_instagram;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fbu_instagram.model.Post;

import org.parceler.Parcels;

public class DetailsActivity extends AppCompatActivity {

    Post postCurr;
    ImageView imageDeets;
    TextView captionDeets;
    TextView userDeets;
    private static final String TAG = "DetailsActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        postCurr = (Post) Parcels.unwrap(getIntent().getParcelableExtra("clicked_post"));

        captionDeets = findViewById(R.id.tvCaptionDeets);
        captionDeets.setText(postCurr.getDescription());
//        imageDeets.setImageResource(postCurr.getMedia());



    }
}
