package com.test.ytest.view;

import com.test.ytest.model.NewsItem;

import java.util.List;

/**
 * Created by igor on 29.10.16.
 */

public interface NewsView {

    void onNewsItemLoaded(List<NewsItem> newsItems);

}
