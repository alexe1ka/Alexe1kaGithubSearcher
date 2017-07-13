package com.githubsearcher.alexe1ka.alexe1kagithubsearcher;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.githubsearcher.alexe1ka.alexe1kagithubsearcher.model.SearchRepository;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RepositoryActivity extends AppCompatActivity implements DownloadComplete {

    private String searchKeyword;
    RecyclerView recyclerView;
    List<SearchRepository> repositories;
    ProgressDialog mProgressDialog;


    TextView mRepoView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repository);
        searchKeyword = getIntent().getExtras().getString("searchKeyword");

        repositories = new ArrayList<>();

        recyclerView = (RecyclerView) findViewById(R.id.repo_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        FoundRepoAdapter foundRepoAdapter = new FoundRepoAdapter(repositories);
        recyclerView.setAdapter(foundRepoAdapter);

        if (isNetworkConnected() || isWifiConnected()) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage("Please wait...");
            mProgressDialog.setCancelable(false);
            mProgressDialog.show();

            makeRequestToApi();
        } else {
            new AlertDialog.Builder(this)
                    .setTitle("No Internet Connection")
                    .setMessage("It looks like your internet connection is off. Please turn it " +
                            "on and try again")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    }).setIcon(android.R.drawable.ic_dialog_alert).show();
        }

    }

    private void makeRequestToApi() {
        AppSearch.getSearchApi().foundRepository(searchKeyword).enqueue(new Callback<ArrayList<SearchRepository>>() {
            @Override
            public void onResponse(Call<ArrayList<SearchRepository>> call, Response<ArrayList<SearchRepository>> response) {
                Log.v("RESPONSE_BODY", response.body().toString());
                if (response.body() != null) {
                    Log.i(getApplicationContext().toString(), response.toString());
                    repositories.addAll(response.body());
                    recyclerView.getAdapter().notifyDataSetChanged();
                } else {
                    Toast.makeText(getApplicationContext(), "response body=0", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<ArrayList<SearchRepository>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
            }
        });
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
