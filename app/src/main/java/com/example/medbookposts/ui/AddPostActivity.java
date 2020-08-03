package com.example.medbookposts.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

    private List<PostsApiResponse> mPosts = new ArrayList<>();
    private PostsApiResponse mPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);
        ButterKnife.bind(this);

        mAddNewPostButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view == mAddNewPostButton) {
            String title = mAddedPostTitleEditText.getText().toString();
            String body = mAddedPostBodyEditText.getText().toString();

            int sizeOfList = mPosts.size();

            mPost = new PostsApiResponse();

            mPost.setTitle(title);
            mPost.setBody(body);
            mPost.setId(sizeOfList + 1);

            addNewPostToList(mPost);


        }

    }

    private void addNewPostToList(PostsApiResponse post) {
        TypicodeApi client = TypiCodeClient.getClient();

        Call<List<PostsApiResponse>> call = client.addPost();

        call.enqueue(new Callback<List<PostsApiResponse>>() {
            @Override
            public void onResponse(@NotNull Call<List<PostsApiResponse>> call, @NotNull Response<List<PostsApiResponse>> response) {
                if (response.isSuccessful()){
                    mPosts = response.body();
                    assert mPosts != null;
                    mPosts.add(post);
                    Toast.makeText(AddPostActivity.this, "Added post with id"+mPosts.size(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(@NotNull Call<List<PostsApiResponse>> call, @NotNull Throwable t) {
                Toast.makeText(AddPostActivity.this, "Something Went wrong", Toast.LENGTH_LONG).show();
            }
        });
    }
}
