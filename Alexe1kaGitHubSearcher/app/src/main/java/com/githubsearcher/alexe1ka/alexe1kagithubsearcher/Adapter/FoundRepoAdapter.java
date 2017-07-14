package com.githubsearcher.alexe1ka.alexe1kagithubsearcher.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.githubsearcher.alexe1ka.alexe1kagithubsearcher.R;
import com.githubsearcher.alexe1ka.alexe1kagithubsearcher.model.Item;
import com.githubsearcher.alexe1ka.alexe1kagithubsearcher.model.ReposResponse;

import java.util.List;

/**
 * Created by alexe1ka on 14.07.2017.
 */

public class FoundRepoAdapter extends RecyclerView.Adapter<FoundRepoAdapter.ViewHolder> {
    private List<Item> itemsFoundRepos;

    public FoundRepoAdapter(List<Item> itemsFoundRepos) {
        this.itemsFoundRepos = itemsFoundRepos;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.found_repo_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
/*
        ReposResponse model = foundRepos.get(position);
        ReposResponse.SearchRepository searchRepository = model.getSearchRepository();
        ReposResponse.SearchRepository.Item item = (ReposResponse.SearchRepository.Item)searchRepository.getItems();
        ReposResponse.SearchRepository.Item.Owner owner = item.getOwner();

        holder.mUserTextView.setText(owner.getLogin());
        holder.mRepoNameTextView.setText(item.getName());
        holder.mUrlTextView.setText(owner.getHtmlUrl());
        */
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView mUserTextView;
        TextView mRepoNameTextView;
        TextView mUrlTextView;


        public ViewHolder(View itemView) {
            super(itemView);
            mUserTextView = (TextView) itemView.findViewById(R.id.userTextView);
            mRepoNameTextView = (TextView) itemView.findViewById(R.id.repoNameTextView);
            mUrlTextView = (TextView) itemView.findViewById(R.id.urlTextView);
        }
    }
}
