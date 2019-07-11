package com.example.fbu_instagram;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.fbu_instagram.model.Post;
import com.parse.ParseFile;

import org.parceler.Parcels;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.ViewHolder> {

    private Context context;
    private List<Post> posts;
    public int whichFragment;
    RecyclerView rvPosts;


    public PostsAdapter(Context context, List<Post> posts, int whichFragment) {
        this.context = context;
        this.posts = posts;
        this.whichFragment = whichFragment;
    }

    public void clear() {
        posts.clear();
        notifyDataSetChanged();
    }

    // Add a list of items -- change to type used
    public void addAll(List<Post> list) {
        posts.addAll(list);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_post, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
            Post post = posts.get(i);
            if(whichFragment == 0){ //for timeline fragment

            }
            if(whichFragment == 1){//for profile fragment

            }


        viewHolder.bind(post);
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView tvHandle;
        ImageView ivImage;
        TextView tvDescription;
        TextView tvHandleDown;
        TextView tvCreatedStamp;
        ImageButton dmButton;
        ImageButton likeButton;
        ImageButton commentButton;
        ImageButton saveButton;





        public ViewHolder(View view) {
            super(view);
            tvHandle = itemView.findViewById(R.id.tvHandle);
            ivImage = itemView.findViewById(R.id.ivImage);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            rvPosts = itemView.findViewById(R.id.rvPosts);
            tvHandleDown = itemView.findViewById(R.id.tvUserDown);
            tvCreatedStamp = itemView.findViewById(R.id.tvTimeStamp);
            dmButton = itemView.findViewById(R.id.dmBtn);
            likeButton = itemView.findViewById(R.id.likeBtn);
            commentButton = itemView.findViewById(R.id.commentBtn);
            saveButton = itemView.findViewById(R.id.savepostBtn);
            view.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if(position!=RecyclerView.NO_POSITION){
                Post post = posts.get(position);
                Intent intent = new Intent(context, DetailsActivity.class);
                intent.putExtra("clicked_post", Parcels.wrap(post));
                context.startActivity(intent);
            }
        }

        public void bind(Post post){

            if(whichFragment==0){
            tvHandle.setText(post.getUser().getUsername());
            tvHandleDown.setText(post.getUser().getUsername());
            Date timestamp = post.getCreatedAt();
            DateFormat dateFormat = new SimpleDateFormat("MMMM dd hh:mm");
            String strDate = dateFormat.format(timestamp);
            tvCreatedStamp.setText(strDate);
            ParseFile image = post.getImage();
            if(image!= null){
                Glide.with(context).load(image.getUrl()).into(ivImage);
            }
            tvDescription.setText(post.getDescription());
            }
            else if(whichFragment==1){
                tvHandle.setVisibility(View.GONE);
                tvDescription.setVisibility(View.GONE);
                tvCreatedStamp.setVisibility(View.GONE);
                tvHandleDown.setVisibility(View.GONE);
                commentButton.setVisibility(View.GONE);
                likeButton.setVisibility(View.GONE);
                dmButton.setVisibility(View.GONE);
                saveButton.setVisibility(View.GONE);

                ParseFile image = post.getImage();
                DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
                int pxWidth = displayMetrics.widthPixels;
                ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams(pxWidth/3,pxWidth/3);
                ivImage.setLayoutParams(layoutParams);
                if(image!= null){
                    Glide.with(context).load(image.getUrl()).into(ivImage);
                }
            }
        }
    }
}
