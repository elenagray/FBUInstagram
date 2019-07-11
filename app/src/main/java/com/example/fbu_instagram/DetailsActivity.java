package com.example.fbu_instagram;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.fbu_instagram.model.Post;
import com.parse.ParseFile;

import org.parceler.Parcels;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DetailsActivity extends AppCompatActivity {

    Post postCurr;
    ImageView imageDeets;
    TextView captionDeets;
    TextView userDeets;
    TextView createdAt;
    private static final String TAG = "DetailsActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        postCurr = (Post) Parcels.unwrap(getIntent().getParcelableExtra("clicked_post"));

        captionDeets = findViewById(R.id.tvCaptionDeets);
        imageDeets = findViewById(R.id.ivPicDeets);
        userDeets = findViewById(R.id.tvUserDeets);
        createdAt = findViewById(R.id.tvCreatedAtDeets);

        captionDeets.setText(postCurr.getDescription());
        userDeets.setText(postCurr.getUser().getUsername());
        Date timestamp = postCurr.getCreatedAt();
        DateFormat dateFormat = new SimpleDateFormat("MMMM dd hh:mm");
        String strDate = dateFormat.format(timestamp);
        createdAt.setText(strDate);

        ParseFile image = postCurr.getImage();
        if(image!= null){
            Glide.with(this).load(image.getUrl()).into(imageDeets);
        }

    }
}
