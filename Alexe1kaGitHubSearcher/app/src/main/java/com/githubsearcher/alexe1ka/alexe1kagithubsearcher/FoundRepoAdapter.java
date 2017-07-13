package com.githubsearcher.alexe1ka.alexe1kagithubsearcher;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.githubsearcher.alexe1ka.alexe1kagithubsearcher.model.Item;
import com.githubsearcher.alexe1ka.alexe1kagithubsearcher.model.Owner;
import com.githubsearcher.alexe1ka.alexe1kagithubsearcher.model.SearchRepository;

import java.util.List;

/**
 * Created by alexe1ka on 14.07.2017.
 */

public class FoundRepoAdapter extends RecyclerView.Adapter<FoundRepoAdapter.ViewHolder> {
    private List<SearchRepository> foundRepos;

    public FoundRepoAdapter(List<SearchRepository> foundRepo) {
        this.foundRepos = foundRepo;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.found_repo_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        SearchRepository searchRepository = foundRepos.get(position);
        Item item = (Item) searchRepository.getItems();
        Owner owner = item.getOwner();

        holder.mUserTextView.setText(owner.getLogin());
        holder.mRepoNameTextView.setText(item.getName());
        holder.mUrlTextView.setText(owner.getHtmlUrl());
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
