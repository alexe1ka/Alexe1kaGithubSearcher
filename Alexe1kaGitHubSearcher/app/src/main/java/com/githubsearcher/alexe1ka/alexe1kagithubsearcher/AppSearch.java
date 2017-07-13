package com.githubsearcher.alexe1ka.alexe1kagithubsearcher;

import android.app.Application;

import com.githubsearcher.alexe1ka.alexe1kagithubsearcher.githubApi.GitHubSearchApi;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by alexe1ka on 13.07.2017.
 */


//Android-синглетон
// инициализация retrofit'а
public class AppSearch extends Application {
    private static GitHubSearchApi gitHubSearchApiApi;
    private Retrofit retrofit;

    @Override
    public void onCreate() {
        super.onCreate();

        retrofit = new Retrofit.Builder().
                baseUrl("https://api.github.com/search/repositories").
                addConverterFactory(GsonConverterFactory.create()).
                build(); //конвертер json
        gitHubSearchApiApi = retrofit.create(GitHubSearchApi.class);
    }

    public static GitHubSearchApi getSearchApi() {
        return gitHubSearchApiApi;
    }
}
