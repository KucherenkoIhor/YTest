package com.test.ytest.shared;

import com.test.ytest.presenter.NewsPresenter;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.schedulers.Schedulers;

import static com.test.ytest.shared.Constants.NEWS_ENDPOINT;

/**
 * Created by igor on 29.10.16.
 */
@Module
public class NewsModule {

    @Provides
    @Singleton
    NewsPresenter provideNewsPresenter() {
        return new NewsPresenter();
    }

    @Provides
    @Singleton
    NewsApiInterface provideNewApiInterface() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(NEWS_ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io()))
                .build();
        return retrofit.create(NewsApiInterface.class);
    }
}
