package com.smartdroidesign.retrofitoauth2.api;

import com.smartdroidesign.retrofitoauth2.model.Basic;
import com.smartdroidesign.retrofitoauth2.model.Image;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface Imgur {

    /**
     * HANDLING AUTHENTICATION
     */
    String IMGUR_BASE_URL = "https://api.imgur.com";
    String IMGUR_CLIENT_ID = "fbb27c8f037abf1";
    String AUTHORIZATION_URL = "https://api.imgur.com/oauth2/authorize?client_id=" + IMGUR_CLIENT_ID + "&response_type=token";
    String REDIRECT_URL = "https://threehouseworkshop:88";

    /**
     * sub-interface to describe the endpoint you're connecting to
     */
    interface Auth {
        @GET("3/account/{username}/images/{page}")
        Call<Basic<ArrayList<Image>>> images(@Path("username") String username,
                                             @Path("page") int page);
    }
}
