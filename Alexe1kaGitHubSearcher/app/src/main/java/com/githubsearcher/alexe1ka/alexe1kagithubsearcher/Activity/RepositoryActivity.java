package com.githubsearcher.alexe1ka.alexe1kagithubsearcher.Activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import android.widget.Toast;

import com.githubsearcher.alexe1ka.alexe1kagithubsearcher.Adapter.FoundRepoAdapter;
import com.githubsearcher.alexe1ka.alexe1kagithubsearcher.AppSearch;
import com.githubsearcher.alexe1ka.alexe1kagithubsearcher.R;
import com.githubsearcher.alexe1ka.alexe1kagithubsearcher.model.Item;
import com.githubsearcher.alexe1ka.alexe1kagithubsearcher.model.ReposResponse;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//TODO строки в strings убрать
public class RepositoryActivity extends AppCompatActivity {
    public final static String TAG = "RepoActivity";

    private String mSearchKeyword;
    private RecyclerView mRecyclerView;
    private ProgressDialog mProgressDialog;
    private LinearLayoutManager linearLayoutManager;


    private FoundRepoAdapter foundRepoAdapter;
    private static final int PAGE_START = 1;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int TOTAL_RESULT_ON_PAGES =30;
    private int currentPage = PAGE_START;
    ReposResponse result;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repository);
        mSearchKeyword = getIntent().getExtras().getString("mSearchKeyword");
        Log.d("RepoActivity", "mSearchKeyword = " + mSearchKeyword);


        mRecyclerView = (RecyclerView) findViewById(R.id.repo_rv);

        linearLayoutManager = new LinearLayoutManager(getApplicationContext());

        mRecyclerView.setLayoutManager(linearLayoutManager);

        foundRepoAdapter = new FoundRepoAdapter(this);
        mRecyclerView.setAdapter(foundRepoAdapter);
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("Please wait...");
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();


        mRecyclerView.addOnScrollListener(new PaginationScrollListener(linearLayoutManager) {
            @Override
            protected void loadMoreItems() {
                isLoading = true;
                currentPage += 1;

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        makeOtherRequestToApi();
                    }
                }, 1000);

            }

            @Override
            public int getTotalPageCount() {
                return TOTAL_RESULT_ON_PAGES;
            }

            @Override
            public boolean isLastPage() {
                return isLastPage;
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }
        });

        makeFirstRequestToApi(currentPage);

    }


    private void makeFirstRequestToApi(int page) {
        AppSearch.getSearchApi().foundRepository(mSearchKeyword, page).enqueue(new Callback<ReposResponse>() {
            @Override
            public void onResponse(Call<ReposResponse> call, Response<ReposResponse> response) {
                Log.d(TAG, "Status Code = " + response.code());
                if (response.body().getTotalCount() != 0) {
                    if (response.isSuccessful()) {
                        result = response.body();
                        Log.d(TAG, "response = " + new Gson().toJson(result));
                        List<Item> itemList = result.getItems();
                        foundRepoAdapter.addAll(itemList);
                        if (itemList.size() >= TOTAL_RESULT_ON_PAGES) {
                            foundRepoAdapter.addLoadingFooter();
                        } else {
                            isLastPage = true;
                        }


                        if (mProgressDialog != null) {
                            mProgressDialog.hide();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "response=null", Toast.LENGTH_LONG).show();
                    }
                } else {
                    if (mProgressDialog != null) {
                        mProgressDialog.hide();
                    }
                    AlertDialog.Builder builder = new AlertDialog.Builder(RepositoryActivity.this);
                    builder.setTitle("No result");
                    builder.setMessage("please enter other keywords");
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Intent intent = new Intent(RepositoryActivity.this, MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
            }

            @Override
            public void onFailure(Call<ReposResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
            }
        });
    }


    private void makeOtherRequestToApi() {
        AppSearch.getSearchApi().foundRepository(mSearchKeyword, currentPage).enqueue(new Callback<ReposResponse>() {
            @Override
            public void onResponse(Call<ReposResponse> call, Response<ReposResponse> response) {
                foundRepoAdapter.removeLoadingFooter();
                isLoading = false;
                ReposResponse nextResult = response.body();
                List<Item> itemList = nextResult.getItems();
                foundRepoAdapter.addAll(itemList);
                if (itemList.size() >= TOTAL_RESULT_ON_PAGES) {
                    foundRepoAdapter.addLoadingFooter();
                } else {
                    isLastPage = true;
                }
            }

            @Override
            public void onFailure(Call<ReposResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "response=null in other request", Toast.LENGTH_LONG).show();
            }
        });
    }
}