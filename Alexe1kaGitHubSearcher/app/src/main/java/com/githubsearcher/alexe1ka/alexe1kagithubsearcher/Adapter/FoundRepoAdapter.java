package com.githubsearcher.alexe1ka.alexe1kagithubsearcher.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.githubsearcher.alexe1ka.alexe1kagithubsearcher.R;
import com.githubsearcher.alexe1ka.alexe1kagithubsearcher.model.Item;
import com.githubsearcher.alexe1ka.alexe1kagithubsearcher.model.Owner;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alexe1ka on 14.07.2017.
 */

public class FoundRepoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int STATE_ITEM = 0;
    private static final int STATE_LOADING = 1;

    private List<Item> mItemsFoundRepos;
    private Context mContext;

    private boolean isLoadingAdded = false;

    public FoundRepoAdapter(Context mContext) {
        mItemsFoundRepos = new ArrayList<>();
        this.mContext = mContext;
    }

    public List<Item> getItemsFoundRepos() {
        return mItemsFoundRepos;
    }

    public void setItemsFoundRepos(List<Item> mItemsFoundRepos) {
        this.mItemsFoundRepos = mItemsFoundRepos;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case STATE_ITEM:
                viewHolder = getViewHolder(parent, inflater);
                break;
            case STATE_LOADING:
                View progressView = inflater.inflate(R.layout.found_repo_progressbar, parent, false);
                viewHolder = new LoadedViewHolder(progressView);
                break;
        }
        return viewHolder;
    }

    @NonNull
    private RecyclerView.ViewHolder getViewHolder(ViewGroup parent, LayoutInflater inflater) {
        RecyclerView.ViewHolder viewHolder;
        View view = inflater.inflate(R.layout.found_repo_item, parent, false);
        viewHolder = new ItemViewHolder(view);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Item itemRepo = mItemsFoundRepos.get(position);
        switch (getItemViewType(position)) {
            case STATE_ITEM:
                final ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
                final Owner owner = itemRepo.getOwner();
                itemViewHolder.mUserTextView.setText(owner.getLogin());
                itemViewHolder.mRepoNameTextView.setText(itemRepo.getName());
                itemViewHolder.mUrlTextView.setText(owner.getHtmlUrl());
                Glide.with(mContext).load(owner.getAvatarUrl()).into(itemViewHolder.mAvatarImageView);
                break;
            case STATE_LOADING:
                ((LoadedViewHolder) holder).progressBar.setIndeterminate(true);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mItemsFoundRepos == null ? 0 : mItemsFoundRepos.size();
    }

    @Override
    public int getItemViewType(int position) {
        return (position == mItemsFoundRepos.size() - 1 && isLoadingAdded) ? STATE_LOADING : STATE_ITEM;
    }

    public void add(Item item) {
        mItemsFoundRepos.add(item);
        notifyItemInserted(mItemsFoundRepos.size() - 1);
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


    public class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView mUserTextView;
        private TextView mRepoNameTextView;
        private TextView mUrlTextView;
        private ImageView mAvatarImageView;
        public View mCardView;


        public ItemViewHolder(View itemView) {
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

    public class LoadedViewHolder extends RecyclerView.ViewHolder {
        private ProgressBar progressBar;

        public LoadedViewHolder(View itemView) {
            super(itemView);
            progressBar = (ProgressBar) itemView.findViewById(R.id.found_repo_progressBar);
        }
    }


}
