package com.test.ytest.shared;

import android.app.Application;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by igor on 29.10.16.
 */

public class NewsApp extends Application {

    //public static ApplicationComponent graph = null;

    @Singleton
    @Component(modules = { NewsModule.class })
    public interface News {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        News news = DaggerNewsApp_News.builder().build();
     //   graph = DaggerApplicationComponent.builder().injectionModule(new InjectionModule(this)).build();
      //  graph.inject(this)
    }
}
