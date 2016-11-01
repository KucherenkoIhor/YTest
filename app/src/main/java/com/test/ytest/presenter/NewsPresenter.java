package com.test.ytest.presenter;

import com.test.ytest.model.NewsItem;
import com.test.ytest.model.NewsResponse;
import com.test.ytest.shared.NewsApiInterface;
import com.test.ytest.view.NewsView;

import io.realm.Realm;
import io.realm.RealmResults;
import rx.Observable;
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
                .flatMap(items -> {
                    Realm.getDefaultInstance().executeTransaction(realm -> {
                        realm.delete(NewsItem.class);
                        realm.insert(items);
                    });
                    return Observable.just(items);
                })
                .onErrorResumeNext(throwable -> {
                    Realm realm = Realm.getDefaultInstance();
                    RealmResults<NewsItem> items = realm.where(NewsItem.class).findAll();
                    return Observable.just(realm.copyFromRealm(items));
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(view::onNewsItemLoaded, Throwable::printStackTrace);
    }

    public void onDestroyView() {
        if(newsSubscription != null) newsSubscription.unsubscribe();
    }

}
