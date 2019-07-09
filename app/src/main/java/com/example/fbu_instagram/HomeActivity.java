package com.example.fbu_instagram;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.fbu_instagram.model.Post;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.File;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private static final String imagePath = Environment.getExternalStorageDirectory().getPath();
    private EditText descriptionImage;
    private Button createButton;
    private Button refreshButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        descriptionImage = findViewById(R.id.description_et);
        createButton = findViewById(R.id.createBtn);
        refreshButton = findViewById(R.id.refreshBtn);

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String description = descriptionImage.getText().toString();
                final ParseUser user = ParseUser.getCurrentUser();
                final File file = new File(imagePath);
                final ParseFile parseFile = new ParseFile(file);
                createPost(description, parseFile,user);

            }
        });

        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadTopPosts();
            }
        });
    }

    private void createPost(String description, ParseFile imageFile, ParseUser user){
        final Post newPost = new Post();
        newPost.setDescription(description);
        newPost.setImage(imageFile);
        newPost.setUser(user);

        newPost.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if(e==null){
                    Log.d("HomeActivity", "create post success");
                }else{
                    Log.d("HomeActivity", "post failure");
                    e.printStackTrace();
                }
            }
        });
    }


    private void loadTopPosts(){
        final Post.Query postsQuery = new Post.Query();
        postsQuery.getTop().withUser();
        postsQuery.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> objects, ParseException e) {
                if(e==null){
                    for(int i = 0; i < objects.size(); i++){
                        Log.d("HomeActivity", "Post[" + i + "] =" + objects.get(i).getDescription()
                                + "\n username = " + objects.get(i).getUser().getUsername());
                    }
                }
                else{
                    e.printStackTrace();
                }

            }
        });
    }
}
