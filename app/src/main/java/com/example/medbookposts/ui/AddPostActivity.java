package com.example.medbookposts.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.medbookposts.R;
import com.example.medbookposts.models.PostsApiResponse;
import com.example.medbookposts.network.TypiCodeClient;
import com.example.medbookposts.network.TypicodeApi;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddPostActivity extends AppCompatActivity implements View.OnClickListener{
    @BindView(R.id.addNewPost) Button mAddNewPostButton;
    @BindView(R.id.addedPostBodyEditText) EditText mAddedPostBodyEditText;
    @BindView(R.id.addedPostTitleEditText) EditText mAddedPostTitleEditText;
    @BindView(R.id.addedPostTitleTextView) TextView mAddedPostTitleTextView;
    @BindView(R.id.addedPostIdTextView) TextView mAddedPostIdTextView;
    @BindView(R.id.addedPostBodyTextView) TextView mAddedPostBodyTextView;
    @BindView(R.id.addedPostUserIdTextView) TextView mAddedPostUserIdTextView;

    @BindView(R.id.backToHomeButton) Button mBackToHomeButton;

    private List<PostsApiResponse> mPosts = new ArrayList<>();
    private PostsApiResponse mPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);
        ButterKnife.bind(this);

        mAddNewPostButton.setOnClickListener(this);
        mBackToHomeButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view == mAddNewPostButton) {
            String title = mAddedPostTitleEditText.getText().toString();
            String body = mAddedPostBodyEditText.getText().toString();


            mPost = new PostsApiResponse();

            mPost.setTitle(title);
            mPost.setBody(body);
            mPost.setUserId(1);


            addNewPostToList(mPost);


        }

        if(view == mBackToHomeButton) {
            Intent mainActivityIntent = new Intent(AddPostActivity.this, MainActivity.class);
            startActivity(mainActivityIntent);
        }

    }

    private void addNewPostToList(PostsApiResponse post) {
        TypicodeApi client = TypiCodeClient.getClient();

        Call<PostsApiResponse> call = client.addPost(post);

        call.enqueue(new Callback<PostsApiResponse>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NotNull Call<PostsApiResponse> call, @NotNull Response<PostsApiResponse> response) {
                if (response.isSuccessful()){
                    Toast.makeText(AddPostActivity.this, "Added post Successfully", Toast.LENGTH_LONG).show();
                    assert response.body() != null;
                    mAddedPostTitleTextView.setText("Post Title: "+response.body().getTitle());
                    mAddedPostBodyTextView.setText("Post Body: "+response.body().getBody());
                    mAddedPostUserIdTextView.setText("UserID: "+response.body().getUserId().toString());
                    mAddedPostIdTextView.setText("POST ID: " + response.body().getId().toString());
                    mBackToHomeButton.setVisibility(View.VISIBLE);

                }
            }

            @Override
            public void onFailure(@NotNull Call<PostsApiResponse> call, @NotNull Throwable t) {
                Toast.makeText(AddPostActivity.this, "Something Went wrong", Toast.LENGTH_LONG).show();
            }
        });
    }
}
