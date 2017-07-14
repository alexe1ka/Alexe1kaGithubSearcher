package com.githubsearcher.alexe1ka.alexe1kagithubsearcher.githubApi;

import com.githubsearcher.alexe1ka.alexe1kagithubsearcher.model.ReposResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by alexe1ka on 13.07.2017.
 */
// q - параметр поиска
public interface GitHubSearchApi {
    @GET("/search/repositories")
    Call<ReposResponse> foundRepository(@Query("q") String searchKeyword);
}
