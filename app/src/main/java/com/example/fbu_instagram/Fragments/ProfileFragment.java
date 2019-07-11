package com.example.fbu_instagram.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.view.View;

import com.example.fbu_instagram.PostsAdapter;
import com.example.fbu_instagram.model.Post;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

public class ProfileFragment extends PostsFragment {


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    protected void setRecyclerView() {
        whichFragment = 1;
        adapter = new PostsAdapter(getContext(),mPosts,whichFragment);
        rvPosts.setAdapter(adapter);
        super.setRecyclerView();
        rvPosts.setLayoutManager(new GridLayoutManager(getContext(),3));
    }


    @Override
    protected void loadTopPosts(){
        ParseQuery<Post> postsQuery = new ParseQuery<Post>(Post.class);
        postsQuery.include(Post.KEY_USER);
        postsQuery.setLimit(20);
        postsQuery.whereEqualTo(Post.KEY_USER, ParseUser.getCurrentUser());
        postsQuery.addDescendingOrder(Post.KEY_CREATED_AT);
        postsQuery.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> posts, ParseException e) {
                if(e!=null){
                    Log.e("PostsFragment", "error with post");
                    e.printStackTrace();
                    return;
                }
                else{
                    mPosts.addAll(posts);
                    adapter.notifyDataSetChanged();
                    for(int i = 0; i < posts.size(); i++){
                        Post post = posts.get(i);
                        Log.d("PostsFragment", "Post[" + i + "] =" + posts.get(i).getDescription()
                                + "\n username = " + post.getUser().getUsername());
                    }
                }


            }
        });
    }
}
