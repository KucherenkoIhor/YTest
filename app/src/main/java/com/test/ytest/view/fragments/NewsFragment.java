package com.test.ytest.view.fragments;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.test.customtabhelper.CustomTabActivityHelper;
import com.test.customtabhelper.WebViewFallback;
import com.test.ytest.R;
import com.test.ytest.model.NewsItem;
import com.test.ytest.presenter.NewsPresenter;
import com.test.ytest.shared.NewsApiInterface;
import com.test.ytest.shared.NewsApp;
import com.test.ytest.view.adapters.NewsAdapter;
import com.test.ytest.view.adapters.NewsAdapter.NewsViewHolder;
import com.test.ytest.view.interfaces.NewsView;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.arturvasilov.rxloader.LifecycleHandler;
import ru.arturvasilov.rxloader.LoaderLifecycleHandler;

public class NewsFragment extends Fragment
        implements SwipeRefreshLayout.OnRefreshListener,
        NewsView {

    @Inject
    protected NewsPresenter mNewsPresenter;
    @Inject
    protected NewsApiInterface mNewsAPI;

    @BindView(R.id.progress_bar)
    protected ProgressBar progressBar;
    @BindView(R.id.status_text_view)
    protected TextView statusTextView;
    @BindView(R.id.recycler_view)
    protected RecyclerView mRecyclerView;
    @BindView(R.id.swipe_layout)
    protected SwipeRefreshLayout mSwipeRefreshLayout;

    private NewsAdapter mNewsAdapter = new NewsAdapter();

    private LifecycleHandler mLifecycleHandler = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inject();
        mNewsPresenter.setNewsApiInterface(mNewsAPI);
    }

    private void inject() {
        ((NewsApp) getActivity().getApplication()).getNews().inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_news, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mLifecycleHandler = LoaderLifecycleHandler.create(
                getActivity(),
                getActivity().getSupportLoaderManager());
        mNewsPresenter.onViewCreated(mLifecycleHandler, this);
        mNewsPresenter.loadNews();
        prepareSwipeRefreshLayout();
        prepareRecyclerView();
        mRecyclerView.setAdapter(mNewsAdapter);
    }

    private void prepareSwipeRefreshLayout() {
        mSwipeRefreshLayout.setColorSchemeColors(Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW);
        mSwipeRefreshLayout.setOnRefreshListener(this);
    }

    private void prepareRecyclerView() {
        final LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
    }

    @Override
    public void onNewsItemLoaded(List<NewsItem> newsItems) {
        mLifecycleHandler.clear(R.id.news_request);
        mSwipeRefreshLayout.setRefreshing(false);
        progressBar.setVisibility(View.GONE);
        if(newsItems.isEmpty()) {
            statusTextView.setText(R.string.list_is_empty);
            return;
        }
        statusTextView.setText(null);
        mNewsAdapter.setDataSource(newsItems);
        mNewsAdapter.setOnItemClickListener(mOnItemClickListener);
    }

    private View.OnClickListener mOnItemClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            NewsViewHolder holder = (NewsViewHolder) mRecyclerView.getChildViewHolder(view);
            int primaryColor = ContextCompat.getColor(
                    getActivity(),
                    R.color.colorPrimary);
            CustomTabsIntent customTabsIntent = new CustomTabsIntent.Builder()
                    .setToolbarColor(primaryColor)
                    .setStartAnimations(getActivity(), R.anim.slide_in_right, R.anim.slide_out_left)
                    .setExitAnimations(getActivity(), R.anim.slide_in_left, R.anim.slide_out_right)
                    .build();
            CustomTabActivityHelper.openCustomTab(
                    getActivity(),
                    customTabsIntent,
                    holder.getNewsItem().getWebURL(),
                    new WebViewFallback());

        }
    };

    @Override
    public void onRefresh() {
        mNewsPresenter.loadNews();
    }

    @Override
    public void onError(Throwable throwable) {
        mLifecycleHandler.clear(R.id.news_request);
        mSwipeRefreshLayout.setRefreshing(false);
        progressBar.setVisibility(View.GONE);
        if (throwable instanceof IOException) {
            statusTextView.setText(R.string.connection_error);
        } else {
            statusTextView.setText(R.string.list_is_empty);
        }
    }

    @Override
    public void hideLoading() {
        mLifecycleHandler.clear(R.id.news_request);
        mSwipeRefreshLayout.setRefreshing(false);
        progressBar.setVisibility(View.GONE);
    }
}
