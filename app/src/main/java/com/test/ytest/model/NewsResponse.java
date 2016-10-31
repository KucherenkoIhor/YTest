package com.test.ytest.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class NewsResponse {

    @SerializedName("Pagination")
    @Expose
    private Pagination pagination;
    @SerializedName("NewsItem")
    @Expose
    private List<NewsItem> newsItem = new ArrayList<NewsItem>();

    /**
     * 
     * @return
     *     The pagination
     */
    public Pagination getPagination() {
        return pagination;
    }

    /**
     * 
     * @param pagination
     *     The Pagination
     */
    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }

    /**
     * 
     * @return
     *     The newsItem
     */
    public List<NewsItem> getNewsItem() {
        return newsItem;
    }

    /**
     * 
     * @param newsItem
     *     The NewsItem
     */
    public void setNewsItem(List<NewsItem> newsItem) {
        this.newsItem = newsItem;
    }

}
