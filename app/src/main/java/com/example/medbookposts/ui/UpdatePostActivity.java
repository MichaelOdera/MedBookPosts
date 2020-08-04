package com.example.medbookposts.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.medbookposts.R;
import com.example.medbookposts.models.PostsApiResponse;
import com.example.medbookposts.network.TypiCodeClient;
import com.example.medbookposts.network.TypicodeApi;

import org.jetbrains.annotations.NotNull;
import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdatePostActivity extends AppCompatActivity implements View.OnClickListener{

    @BindView(R.id.noticeTextView)
    TextView mNoticeTextView;

    @BindView(R.id.titleEditText)
    EditText mTitleEditText;
    @BindView(R.id.bodyEditText)
    EditText mBodyEditText;

    @BindView(R.id.submitPost)
    Button mSubmitPost;


    int mPosition;


    private PostsApiResponse mPost;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_post);
        ButterKnife.bind(this);

        mSubmitPost.setOnClickListener(this);

        Intent intent = getIntent();
        mPost = Parcels.unwrap(intent.getParcelableExtra("post")) ;

        mPosition = intent.getIntExtra("position", 0);

        mNoticeTextView.setText("You are about to Edit Post Number: "+ mPosition);




    }

    @Override
    public void onClick(View view) {
        if(view == mSubmitPost){

            Toast.makeText(UpdatePostActivity.this, "Button Has Been Clicked", Toast.LENGTH_SHORT).show();
            String title = mTitleEditText.getText().toString().trim();
            String body = mBodyEditText.getText().toString().trim();

            System.out.println("Title: "+title+"Body: "+body);

            TypicodeApi client = TypiCodeClient.getClient();

            Call<PostsApiResponse> call = client.updatePost(mPosition);

            call.enqueue(new Callback<PostsApiResponse>() {
                @Override
                public void onResponse(@NotNull Call<PostsApiResponse> call, @NotNull Response<PostsApiResponse> response) {
                    if(response.isSuccessful()){
                        assert response.body() != null;
                        mPost = response.body();
                        mPost.setBody(body);
                        mPost.setTitle(title);
                        System.out.println(mPost);
                        Toast.makeText(UpdatePostActivity.this, "Successful", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(UpdatePostActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                }

                @Override
                public void onFailure(@NotNull Call<PostsApiResponse> call, @NotNull Throwable t) {
                    Toast.makeText(UpdatePostActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();

                }
            });

        }

    }

}
