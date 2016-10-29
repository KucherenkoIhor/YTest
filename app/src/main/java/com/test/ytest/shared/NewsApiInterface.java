package com.test.ytest.shared;

import com.test.ytest.model.NewsResponse;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by igor on 29.10.16.
 */

public interface NewsApiInterface {

    @GET("/feeds/newsdefaultfeeds.cms?feedtype=sjson")
    Observable<NewsResponse> getNews();

}
