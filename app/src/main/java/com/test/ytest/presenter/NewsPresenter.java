package com.test.ytest.presenter;

import com.test.ytest.model.NewsResponse;
import com.test.ytest.shared.NewsApiInterface;
import com.test.ytest.view.NewsView;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by igor on 29.10.16.
 */

public class NewsPresenter {

    private NewsApiInterface newsApiInterface = null;

    private NewsView view = null;

    private Subscription newsSubscription = null;

    public void onViewCreated(NewsView view) {
        this.view = view;
    }

    public void setNewsApiInterface(NewsApiInterface newsApiInterface) {
        this.newsApiInterface = newsApiInterface;
    }
//https://github.com/ArturVasilov/AndroidSchool/tree/master/GithubMVP/app/src/main/java/ru/gdgkazan/githubmvp
    public void loadNews() {
        newsSubscription = newsApiInterface
                .getNews()
                .map(NewsResponse::getNewsItem)
                .doOnError(Throwable::printStackTrace)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(view::onNewsItemLoaded, Throwable::printStackTrace);
    }

    public void onDestroyView() {
        if(newsSubscription != null) newsSubscription.unsubscribe();
    }

}
