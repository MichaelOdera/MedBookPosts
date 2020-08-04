package com.example.medbookposts.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medbookposts.R;
import com.example.medbookposts.adapters.PostsRecyclerAdapter;
import com.example.medbookposts.models.PostsApiResponse;
import com.example.medbookposts.network.TypiCodeClient;
import com.example.medbookposts.network.TypicodeApi;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostsListActivity extends AppCompatActivity implements View.OnClickListener{

    @BindView(R.id.postsRecyclerView) RecyclerView mRecyclerView;
    @BindView(R.id.addPostButton)
    Button mAddPostButton;

    private ProgressDialog mProgressDialog;

    private PostsRecyclerAdapter mRecyclerAdapter;

    private List<PostsApiResponse> mPosts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_post);
        ButterKnife.bind(this);

        mProgressDialog = new ProgressDialog(PostsListActivity.this);

        createProgressDialogMessage();

        mAddPostButton.setOnClickListener(this);


        TypicodeApi client = TypiCodeClient.getClient();

        Call<List<PostsApiResponse>> call = client.getPosts();

        call.enqueue(new Callback<List<PostsApiResponse>>() {
            @Override
            public void onResponse(@NotNull Call<List<PostsApiResponse>> call, @NotNull Response<List<PostsApiResponse>> response) {
                if(response.isSuccessful()){
                    mProgressDialog.dismiss();
                    mPosts = response.body();
                    mRecyclerAdapter = new PostsRecyclerAdapter(PostsListActivity.this, mPosts);
                    RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(PostsListActivity.this);
                    mRecyclerView.setAdapter(mRecyclerAdapter);
                    mRecyclerView.setLayoutManager(linearLayoutManager);
                    mRecyclerView.setHasFixedSize(true);
                }
            }

            @Override
            public void onFailure(@NotNull Call<List<PostsApiResponse>> call, @NotNull Throwable t) {
                Toast.makeText(PostsListActivity.this, "Could Not Fetch Data", Toast.LENGTH_LONG).show();
            }
        });




    }

    private void createProgressDialogMessage() {
        mProgressDialog.setTitle("Loading:");
        mProgressDialog.setMessage("Fetching Posts");
        mProgressDialog.show();
    }

    @Override
    public void onClick(View view) {
        if (view == mAddPostButton) {
            Intent addPostIntent = new Intent(PostsListActivity.this, AddPostActivity.class);
            startActivity(addPostIntent);
        }
    }
}
