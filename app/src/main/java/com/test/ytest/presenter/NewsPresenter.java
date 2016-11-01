package com.test.ytest.presenter;

import android.support.annotation.NonNull;
import android.util.Log;

import com.test.ytest.R;
import com.test.ytest.model.NewsItem;
import com.test.ytest.model.NewsResponse;
import com.test.ytest.shared.Constants;
import com.test.ytest.shared.NewsApiInterface;
import com.test.ytest.view.interfaces.NewsView;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.IllegalFormatException;
import java.util.concurrent.TimeUnit;

import io.realm.Realm;
import io.realm.RealmResults;
import ru.arturvasilov.rxloader.LifecycleHandler;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;

import static com.test.ytest.shared.Constants.REQUEST_TIMEOUT;

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
//https://github.com/ArturVasilov/AndroidSchool/tree/master/GithubMVP/app/src/main/java/ru/gdgkazan/githubmvp
    public void loadNews() {
        mNewsApiInterface
                .getNews()
                .map(NewsResponse::getNewsItem)
                .doOnTerminate(mNewsView::hideLoading)
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
                .subscribe(mNewsView::onNewsItemLoaded, mNewsView::onError);
    }

}
