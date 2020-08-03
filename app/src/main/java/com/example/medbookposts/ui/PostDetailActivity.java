package com.example.medbookposts.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.medbookposts.R;
import com.example.medbookposts.adapters.PostDetailFragmentPagerAdapter;
import com.example.medbookposts.models.PostsApiResponse;

import org.parceler.Parcels;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PostDetailActivity extends AppCompatActivity implements View.OnClickListener{

    @BindView(R.id.viewPager)
    ViewPager mViewPager;

    private List<PostsApiResponse> mPosts;

    private PostDetailFragmentPagerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);

        ButterKnife.bind(this);


        mPosts = Parcels.unwrap(getIntent().getParcelableExtra("posts"));
        System.out.println("Found Posts");

        int position = getIntent().getIntExtra("position", 0);

        mAdapter = new PostDetailFragmentPagerAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, mPosts);

        mViewPager.setAdapter(mAdapter);
        mViewPager.setCurrentItem(position);

    }

    @Override
    public void onClick(View view) {

    }
}
