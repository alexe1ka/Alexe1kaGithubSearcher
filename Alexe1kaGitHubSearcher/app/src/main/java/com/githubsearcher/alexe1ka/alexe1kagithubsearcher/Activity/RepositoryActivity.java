package com.githubsearcher.alexe1ka.alexe1kagithubsearcher.Activity;

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
import com.githubsearcher.alexe1ka.alexe1kagithubsearcher.AppSearch;
import com.githubsearcher.alexe1ka.alexe1kagithubsearcher.R;
import com.githubsearcher.alexe1ka.alexe1kagithubsearcher.model.Item;
import com.githubsearcher.alexe1ka.alexe1kagithubsearcher.model.ReposResponse;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RepositoryActivity extends AppCompatActivity {
    public final static String TAG = "RepoActivity";

    private String mSearchKeyword;
    private RecyclerView mRecyclerView;
    private ProgressDialog mProgressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repository);
        mSearchKeyword = getIntent().getExtras().getString("mSearchKeyword");
        Log.d("RepoActivity", "mSearchKeyword = " + mSearchKeyword);


        mRecyclerView = (RecyclerView) findViewById(R.id.repo_rv);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
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
        AppSearch.getSearchApi().foundRepository(mSearchKeyword).enqueue(new Callback<ReposResponse>() {
            @Override
            public void onResponse(Call<ReposResponse> call, Response<ReposResponse> response) {
                Log.d(TAG, "Status Code = " + response.code());
                if (response.isSuccessful()) {
                    ReposResponse result = response.body();
                    Log.d(TAG, "response = " + new Gson().toJson(result));
                    mRecyclerView.setAdapter(new FoundRepoAdapter(result.getItems(), getApplicationContext()));
                    if (mProgressDialog != null) {
                        mProgressDialog.hide();
                    }
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
