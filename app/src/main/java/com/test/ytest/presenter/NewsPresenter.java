package com.test.ytest.presenter;

import com.test.ytest.view.NewsView;

/**
 * Created by igor on 29.10.16.
 */

public class NewsPresenter {

    private NewsView view = null;

    public void onViewCreated(NewsView view) {
        this.view = view;
    }

    public void onDestroyView() {

    }

}
