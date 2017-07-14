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

import com.githubsearcher.alexe1ka.alexe1kagithubsearcher.Adapter.FoundRepoAdapter;
import com.githubsearcher.alexe1ka.alexe1kagithubsearcher.model.Item;
import com.githubsearcher.alexe1ka.alexe1kagithubsearcher.model.ReposResponse;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RepositoryActivity extends AppCompatActivity {

    private String searchKeyword;
    RecyclerView recyclerView;
    List<Item> itemRepositories;
    ProgressDialog mProgressDialog;


    TextView mRepoView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repository);
        searchKeyword = getIntent().getExtras().getString("searchKeyword");
        Log.d("RepoActivity", "searchKeyword = " + searchKeyword);
        Toast.makeText(getApplicationContext(), searchKeyword, Toast.LENGTH_LONG).show();


        itemRepositories = new ArrayList<>();

        recyclerView = (RecyclerView) findViewById(R.id.repo_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        FoundRepoAdapter foundRepoAdapter = new FoundRepoAdapter(itemRepositories);
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
        AppSearch.getSearchApi().foundRepository(searchKeyword).enqueue(new Callback<ReposResponse>() {
            @Override
            public void onResponse(Call<ReposResponse> call, Response<ReposResponse> response) {
                Log.d("RepoActivity", "Status Code = " + response.code());
                //Log.v("RESPONSE_BODY", response.body().toString());
                if (response.isSuccessful()) {
                    ReposResponse result = response.body();
                    Log.d("MainActivity", "response = " + new Gson().toJson(result));
                    //Log.i(getApplicationContext().toString(), response.toString());
                    Toast.makeText(getApplicationContext(), response.body().getTotalCount().toString(), Toast.LENGTH_LONG).show();
                    itemRepositories.addAll(response.body().getItems());
                    //recyclerView.getAdapter().notifyDataSetChanged();
                } else {
                    Toast.makeText(getApplicationContext(), "response=null", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<ReposResponse> call, Throwable t) {
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


}
