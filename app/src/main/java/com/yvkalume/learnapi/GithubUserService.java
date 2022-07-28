package com.yvkalume.learnapi;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GithubUserService {

    @GET("user/{id}")
    Call<GithubUser> getUser(@Path("id") int id);
}
