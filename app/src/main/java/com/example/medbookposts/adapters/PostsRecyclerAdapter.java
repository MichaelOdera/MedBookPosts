package com.example.medbookposts.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medbookposts.R;
import com.example.medbookposts.models.PostsApiResponse;
import com.example.medbookposts.ui.PostDetailActivity;

import org.parceler.Parcels;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PostsRecyclerAdapter extends RecyclerView.Adapter<PostsRecyclerAdapter.PostsViewHolder> {

    Context mContext;
    public List<PostsApiResponse> mPosts;

    public PostsRecyclerAdapter(Context context, List<PostsApiResponse> posts){
        mContext = context;
        mPosts = posts;

    }

    @NonNull
    @Override
    public PostsRecyclerAdapter.PostsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_list_item, parent, false);
        PostsViewHolder postsViewHolder = new PostsViewHolder(view);
        return postsViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PostsRecyclerAdapter.PostsViewHolder holder, int position) {
        holder.bindPost(mPosts.get(position));

    }

    @Override
    public int getItemCount() {
        return mPosts.size();
    }

    public class PostsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        Context mContext;

        @BindView(R.id.postId) TextView mPostId;
        @BindView(R.id.postTitle) TextView mPostTitle;
        @BindView(R.id.postBody) TextView mPostBody;

        public PostsViewHolder(@NonNull View itemView) {
            super(itemView);
            mContext = itemView.getContext();
            ButterKnife.bind(this,itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getLayoutPosition();
            Intent postDetailIntent = new Intent(mContext, PostDetailActivity.class);
            postDetailIntent.putExtra("position", position);
            postDetailIntent.putExtra("posts", Parcels.wrap(mPosts));
            mContext.startActivity(postDetailIntent);

        }

        @SuppressLint("SetTextI18n")
        public void bindPost(PostsApiResponse mPost) {
            mPostId.setText("Post ID: "+mPost.getId().toString());
            mPostTitle.setText(mPost.getTitle());
            mPostBody.setText(mPost.getBody());
        }
    }




}
