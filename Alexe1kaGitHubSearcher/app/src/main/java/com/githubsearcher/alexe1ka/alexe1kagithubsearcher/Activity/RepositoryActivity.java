package com.githubsearcher.alexe1ka.alexe1kagithubsearcher.Activity;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.githubsearcher.alexe1ka.alexe1kagithubsearcher.Adapter.FoundRepoAdapter;
import com.githubsearcher.alexe1ka.alexe1kagithubsearcher.AppSearch;
import com.githubsearcher.alexe1ka.alexe1kagithubsearcher.R;
import com.githubsearcher.alexe1ka.alexe1kagithubsearcher.model.ReposResponse;
import com.google.gson.Gson;

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

        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("Please wait...");
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
        makeRequestToApi();
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

}
