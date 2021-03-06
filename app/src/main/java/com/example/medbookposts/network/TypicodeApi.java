package com.example.medbookposts.network;

import com.example.medbookposts.models.PostsApiResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface TypicodeApi {
    @GET("posts")
    Call<List<PostsApiResponse>> getPosts();

    @PUT("posts/{id}/")
    Call<PostsApiResponse> updatePost(
            @Path("id") int id
    );

    @POST("posts")
    Call<PostsApiResponse> addPost(
            @Body PostsApiResponse post
    );

    @DELETE("posts/{id}/")
    Call<PostsApiResponse> deletePost(
            @Path("id") int id
    );

}
