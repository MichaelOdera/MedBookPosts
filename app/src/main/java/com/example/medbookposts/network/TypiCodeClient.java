package com.example.medbookposts.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TypiCodeClient {

    public static Retrofit retrofit = null;

    public static TypicodeApi getClient(){
        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.TYPICODE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit.create(TypicodeApi.class);
    }


}
