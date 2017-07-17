package com.githubsearcher.alexe1ka.alexe1kagithubsearcher;

import android.app.Application;

import com.githubsearcher.alexe1ka.alexe1kagithubsearcher.githubApi.GitHubSearchApi;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by alexe1ka on 13.07.2017.
 */


//Android-синглетон
// инициализация retrofit'а
public class AppSearch extends Application {
    private static GitHubSearchApi gitHubSearchApi;
    private Retrofit retrofit;

    @Override
    public void onCreate() {
        super.onCreate();
        Gson gson = new GsonBuilder().setLenient().create();

        retrofit = new Retrofit.Builder().
                baseUrl("https://api.github.com").
                addConverterFactory(GsonConverterFactory.create(gson)).//конвертер json
                build();
        gitHubSearchApi = retrofit.create(GitHubSearchApi.class);
    }

    public static GitHubSearchApi getSearchApi() {
        return gitHubSearchApi;
    }
}
