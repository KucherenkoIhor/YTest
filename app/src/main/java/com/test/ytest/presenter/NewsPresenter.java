package com.test.ytest.presenter;

import android.support.annotation.NonNull;

import com.test.ytest.R;
import com.test.ytest.model.NewsItem;
import com.test.ytest.model.NewsResponse;
import com.test.ytest.shared.NewsApiInterface;
import com.test.ytest.view.interfaces.NewsView;

import io.realm.Realm;
import io.realm.RealmResults;
import ru.arturvasilov.rxloader.LifecycleHandler;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;


/**
 * Created by igor on 29.10.16.
 */

public class NewsPresenter {

    private NewsApiInterface mNewsApiInterface = null;
    private NewsView mNewsView = null;
    private LifecycleHandler mLifecycleHandler = null;

    public void onViewCreated(@NonNull LifecycleHandler lifecycleHandler,
                              @NonNull NewsView view) {
        mLifecycleHandler = lifecycleHandler;
        mNewsView = view;
    }

    public void setNewsApiInterface(NewsApiInterface newsApiInterface) {
        this.mNewsApiInterface = newsApiInterface;
    }

    public void loadNews() {
        mNewsApiInterface
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
                .compose(mLifecycleHandler.load(R.id.news_request))
                .doOnTerminate(mNewsView::hideLoading)
                .subscribe(mNewsView::onNewsItemLoaded, mNewsView::onError);
    }

}
