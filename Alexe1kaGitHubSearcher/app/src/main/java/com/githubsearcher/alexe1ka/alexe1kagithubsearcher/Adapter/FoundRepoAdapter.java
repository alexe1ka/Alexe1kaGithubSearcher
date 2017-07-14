package com.githubsearcher.alexe1ka.alexe1kagithubsearcher.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.githubsearcher.alexe1ka.alexe1kagithubsearcher.R;
import com.githubsearcher.alexe1ka.alexe1kagithubsearcher.model.Item;
import com.githubsearcher.alexe1ka.alexe1kagithubsearcher.model.Owner;

import java.util.List;

/**
 * Created by alexe1ka on 14.07.2017.
 */

public class FoundRepoAdapter extends RecyclerView.Adapter<FoundRepoAdapter.ViewHolder> {
    private List<Item> mItemsFoundRepos;
    private Context mContext;

    public FoundRepoAdapter(List<Item> mItemsFoundRepos, Context mContext) {
        this.mItemsFoundRepos = mItemsFoundRepos;
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.found_repo_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Item itemRepo = mItemsFoundRepos.get(position);
        final Owner owner = itemRepo.getOwner();
        holder.mUserTextView.setText(owner.getLogin());
        holder.mRepoNameTextView.setText(itemRepo.getName());
        holder.mUrlTextView.setText(owner.getHtmlUrl());
        // TODO open browser by intent URl
        holder.mUrlTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse(owner.getHtmlUrl())));
            }
        });

    }

    @Override
    public int getItemCount() {
        return mItemsFoundRepos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mUserTextView;
        private TextView mRepoNameTextView;
        private TextView mUrlTextView;


        public ViewHolder(View itemView) {
            super(itemView);
            mUserTextView = (TextView) itemView.findViewById(R.id.userTextView);
            mRepoNameTextView = (TextView) itemView.findViewById(R.id.repoNameTextView);
            mUrlTextView = (TextView) itemView.findViewById(R.id.urlTextView);
        }
    }
}
