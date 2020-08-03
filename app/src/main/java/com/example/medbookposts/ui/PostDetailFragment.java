package com.example.medbookposts.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Parcel;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.medbookposts.R;
import com.example.medbookposts.models.PostsApiResponse;
import com.example.medbookposts.network.TypiCodeClient;
import com.example.medbookposts.network.TypicodeApi;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class PostDetailFragment extends Fragment implements View.OnClickListener {

    private List<PostsApiResponse> mPosts = new ArrayList<>();
    private PostsApiResponse mPost;

    @BindView(R.id.postFragmentId)
    TextView mPostFragmentId;
    @BindView(R.id.postFragmentTitle) TextView mPostFragmentTitle;
    @BindView(R.id.postFragmentBody) TextView mPostFragmentBody;
    @BindView(R.id.userId) TextView mUserId;

    @BindView(R.id.updatePostsButton) Button mUpdatePostButton;
    @BindView(R.id.deletePostsButton) Button mDeleteButton;

    public PostDetailFragment() {
        // Required empty public constructor
    }

    public static Fragment newInstance(List<PostsApiResponse> posts, int position) {
        PostDetailFragment pdFragment = new PostDetailFragment();
        Bundle arguments = new Bundle();

        arguments.putParcelable("posts", Parcels.wrap(posts));
        arguments.putInt("position", position);

        pdFragment.setArguments(arguments);

        return pdFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        assert getArguments() != null;
        mPosts = Parcels.unwrap(getArguments().getParcelable("posts"));
        int position = getArguments().getInt("position");
        mPost = mPosts.get(position);

    }


    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_post_detail, container, false);
        ButterKnife.bind(this, view);

        mUpdatePostButton.setOnClickListener(this);
        mDeleteButton.setOnClickListener(this);

        mPostFragmentId.setText(mPost.getId().toString());
        mPostFragmentTitle.setText(mPost.getTitle());
        mPostFragmentBody.setText(mPost.getBody());
        return view;
    }

    @Override
    public void onClick(View view) {
        if(view == mUpdatePostButton) {
            Intent updatePostIntent = new Intent(getActivity(), UpdatePostActivity.class);
            updatePostIntent.putExtra("post", Parcels.wrap(mPost));
            System.out.println(mPost);
            updatePostIntent.putExtra("position", mPost.getId());
            startActivity(updatePostIntent);
        }

        if(view == mDeleteButton){
            TypicodeApi client = TypiCodeClient.getClient();
            Call<PostsApiResponse> call = client.deletePost(mPost.getId());
            call.enqueue(new Callback<PostsApiResponse>() {
                @Override
                public void onResponse(Call<PostsApiResponse> call, Response<PostsApiResponse> response) {
                    if(response.isSuccessful()){
                        mPosts.remove(mPost);
                        Toast.makeText(getActivity(), "Deleted Successfully", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getActivity(), MainActivity.class);
                        startActivity(intent);
                    }
                }

                @Override
                public void onFailure(Call<PostsApiResponse> call, Throwable t) {

                }
            });
        }

    }
}
