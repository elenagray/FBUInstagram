package com.example.fbu_instagram.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.fbu_instagram.EndlessScrollListener;
import com.example.fbu_instagram.LoginActivity;
import com.example.fbu_instagram.PostsAdapter;
import com.example.fbu_instagram.R;
import com.example.fbu_instagram.model.Post;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class PostsFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    public static final int FIRST_PAGE = 0;
    protected RecyclerView rvPosts;
    protected PostsAdapter adapter;
    protected List<Post> mPosts;
    protected Button btnLogout;
    private SwipeRefreshLayout swipeLayout;
    protected int whichFragment;
    private EndlessScrollListener scrollListener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view;
        view = inflater.inflate(R.layout.fragment_posts, container, false);
        swipeLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeContainer);
        swipeLayout.setOnRefreshListener(this);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        rvPosts = view.findViewById(R.id.rvPosts);
        mPosts = new ArrayList<>();
        setRecyclerView();
        btnLogout = view.findViewById(R.id.logoutBtn);

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseUser.logOut();
                ParseUser currentUser = ParseUser.getCurrentUser();
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });
        loadTopPosts(FIRST_PAGE);
    }

    protected void setRecyclerView(){
        whichFragment = 0;
        adapter = new PostsAdapter(getContext(), mPosts, whichFragment);
        rvPosts.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        rvPosts.setLayoutManager(layoutManager);
    }

    protected void loadTopPosts(int page){
        ParseQuery<Post> postsQuery = new ParseQuery<Post>(Post.class);
        postsQuery.include(Post.KEY_USER);
        postsQuery.setLimit(20);
        postsQuery.setSkip(page*20);
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

    @Override
    public void onRefresh() {
        swipeLayout.setRefreshing(false);
    }
}

