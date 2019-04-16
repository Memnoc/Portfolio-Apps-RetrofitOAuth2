package com.smartdroidesign.retrofitoauth2.api;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Service class to create instances of the API
 *
 * */
public class Service {

    /**
     *
     * @return an instance of the Imgur class (interface)
     */
    public static Imgur.Auth getAuthedApi() {

        // Intercepts every API request, append a header with a token to it, and lets it proceed.
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request authed = chain.request()
                                .newBuilder() // build upon the existing request
                                .addHeader("Authorization", "Bearer " + OAuthUtil.get(OAuthUtil.ACCESS_TOKEN))
                                .build(); // return back a new request with an authorization header

                        return chain.proceed(authed); // proceed into adding a new request onto the next level in the network request
                    }
                }).build(); // returns back the actual client

        return new Retrofit.Builder() // Retrofit builder class
                .baseUrl(Imgur.IMGUR_BASE_URL)
                .client(client) // provide the client to Retrofit used every time we mak a call here
                .addConverterFactory(GsonConverterFactory.create()) // Convert the image data in Model into an object type
                .build()
                .create(Imgur.Auth.class);
    }
}
