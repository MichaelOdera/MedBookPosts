package com.example.medbookposts.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.medbookposts.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @BindView(R.id.checkPostsButton) Button mCheckPostsButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mCheckPostsButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view == mCheckPostsButton){
            Intent listActivityIntent = new Intent(MainActivity.this, PostsListActivity.class);
            startActivity(listActivityIntent);
        }

    }
}
