package com.githubsearcher.alexe1ka.alexe1kagithubsearcher.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
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

    private boolean isLoadingAdded = false;

    public FoundRepoAdapter(List<Item> mItemsFoundRepos, Context mContext) {
        this.mItemsFoundRepos = mItemsFoundRepos;
        this.mContext = mContext;
    }

    public List<Item> getItemsFoundRepos() {
        return mItemsFoundRepos;
    }

    public void setItemsFoundRepos(List<Item> mItemsFoundRepos) {
        this.mItemsFoundRepos = mItemsFoundRepos;
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
        Glide.with(mContext).load(owner.getAvatarUrl()).into(holder.mAvatarImageView);
    }

    @Override
    public int getItemCount() {
        return mItemsFoundRepos.size();
    }

    public void add(Item item) {
        this.mItemsFoundRepos.add(item);
        notifyItemInserted(mItemsFoundRepos.size() - 1);
        //notifyDataSetChanged();
    }

    public void addAll(List<Item> mItemsFoundRepos) {
        for (Item item : mItemsFoundRepos) {
            add(item);
        }
    }

    public void remove(Item item) {
        int position = mItemsFoundRepos.indexOf(item);
        if (position > -1) {
            mItemsFoundRepos.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void clear() {
        isLoadingAdded = false;
        while (getItemCount() > 0) {
            remove(getItem(0));
        }
    }

    public boolean isEmpty() {
        return getItemCount() == 0;
    }


    public void addLoadingFooter() {
        isLoadingAdded = true;
        add(new Item());
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;
        int position = mItemsFoundRepos.size() - 1;
        Item item = getItem(position);
        if (item != null) {
            mItemsFoundRepos.remove(position);
            notifyItemRemoved(position);
        }
    }

    public Item getItem(int position) {
        return mItemsFoundRepos.get(position);
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mUserTextView;
        private TextView mRepoNameTextView;
        private TextView mUrlTextView;
        private ImageView mAvatarImageView;
        public View mCardView;


        public ViewHolder(View itemView) {
            super(itemView);
            mUserTextView = (TextView) itemView.findViewById(R.id.userTextView);
            mRepoNameTextView = (TextView) itemView.findViewById(R.id.repoNameTextView);
            mUrlTextView = (TextView) itemView.findViewById(R.id.urlTextView);
            mAvatarImageView = (ImageView) itemView.findViewById(R.id.userAvatarImageView);
            mCardView = itemView;
            mCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(mUrlTextView.getText().toString())));
                }
            });
        }
    }
}
