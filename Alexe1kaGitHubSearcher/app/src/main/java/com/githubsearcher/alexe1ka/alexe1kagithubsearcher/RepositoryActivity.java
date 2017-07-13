package com.githubsearcher.alexe1ka.alexe1kagithubsearcher;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.githubsearcher.alexe1ka.alexe1kagithubsearcher.model.SearchRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RepositoryActivity extends AppCompatActivity implements DownloadComplete {

    private String searchKeyword;
    RecyclerView recyclerView;
    List<SearchRepository> repositories;




    TextView mRepoView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repository);
        searchKeyword = getIntent().getExtras().getString("searchKeyword");

        











        AppSearch.getSearchApi().foundRepository(searchKeyword).enqueue(new Callback<ArrayList<SearchRepository>>() {
            @Override
            public void onResponse(Call<ArrayList<SearchRepository>> call, Response<ArrayList<SearchRepository>> response) {
                if (response.body() != null) {
                    repositories.addAll(response.body());
                    Toast.makeText(getApplicationContext(), repositories.get(0).toString(), Toast.LENGTH_LONG).show();

                } else {
                    Toast.makeText(getApplicationContext(), response.body().toString(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<SearchRepository>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
            }
        });


        //mRepoView = (TextView) findViewById(R.id.testTextViewRepo);
        //mRepoView.setText(response.toString());

    }


    private boolean isNetworkConnected() {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    private boolean isWifiConnected() {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return networkInfo != null && (ConnectivityManager.TYPE_WIFI == networkInfo.getType()) && networkInfo.isConnected();
    }

    @Override
    public void downloadComplete(ArrayList<SearchRepository> searchRepositories) {

    }
}
