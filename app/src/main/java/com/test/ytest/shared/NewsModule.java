package com.test.ytest.shared;

import dagger.Module;

/**
 * Created by igor on 29.10.16.
 */
@Module
public class NewsModule {

    private NewsApp app = null;

    public NewsModule(NewsApp app) {
        this.app = app;
    }
}
