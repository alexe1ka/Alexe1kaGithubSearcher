package com.githubsearcher.alexe1ka.alexe1kagithubsearcher.model;

import android.content.ClipData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by alexe1ka on 13.07.2017.
 */

//generate by jsonschema2pojo.org

public class SearchRepository implements Serializable
{

    @SerializedName("total_count")
    @Expose
    private Integer totalCount;
    @SerializedName("incomplete_results")
    @Expose
    private Boolean incompleteResults;
    @SerializedName("items")
    @Expose
    private List<Item> items = null;
    private final static long serialVersionUID = 5572140021374994439L;

    /**
     * No args constructor for use in serialization
     *
     */
    public SearchRepository() {
    }

    /**
     *
     * @param items
     * @param totalCount
     * @param incompleteResults
     */
    public SearchRepository(Integer totalCount, Boolean incompleteResults, List<Item> items) {
        super();
        this.totalCount = totalCount;
        this.incompleteResults = incompleteResults;
        this.items = items;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public SearchRepository withTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
        return this;
    }

    public Boolean getIncompleteResults() {
        return incompleteResults;
    }

    public void setIncompleteResults(Boolean incompleteResults) {
        this.incompleteResults = incompleteResults;
    }

    public SearchRepository withIncompleteResults(Boolean incompleteResults) {
        this.incompleteResults = incompleteResults;
        return this;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public SearchRepository withItems(List<Item> items) {
        this.items = items;
        return this;
    }

}
