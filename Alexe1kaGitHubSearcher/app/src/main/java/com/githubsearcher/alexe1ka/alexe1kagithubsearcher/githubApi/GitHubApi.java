package com.githubsearcher.alexe1ka.alexe1kagithubsearcher.githubApi;

import com.githubsearcher.alexe1ka.alexe1kagithubsearcher.model.SearchRepository;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by alexe1ka on 13.07.2017.
 */

public interface GitHubApi {
    @GET("/search/repositories")
    Call<ArrayList<SearchRepository>> foundRepository(@Query("name") String searchKeyword);
}
