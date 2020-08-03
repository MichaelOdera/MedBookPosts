package com.example.medbookposts.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.medbookposts.models.PostsApiResponse;
import com.example.medbookposts.ui.PostDetailFragment;

import java.util.List;

public class PostDetailFragmentPagerAdapter extends FragmentPagerAdapter {

    private List<PostsApiResponse> mPosts;
    public PostDetailFragmentPagerAdapter(FragmentManager supportFragmentManager, int behaviorResumeOnlyCurrentFragment, List<PostsApiResponse> posts) {
        super(supportFragmentManager, behaviorResumeOnlyCurrentFragment);
        mPosts = posts;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return PostDetailFragment.newInstance(mPosts, position);
    }

    @Override
    public int getCount() {
        return mPosts.size();
    }
}
