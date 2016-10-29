package com.test.ytest.view.fragments;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.test.ytest.R;
import com.test.ytest.view.NewsView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewsFragment extends Fragment
        implements SwipeRefreshLayout.OnRefreshListener,
        NewsView {

    @BindView(R.id.swipe_layout)
    protected SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.recycler_view)
    protected RecyclerView recyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        prepareSwipeRefreshLayout();
        prepareRecyclerView();
    }

    private void prepareSwipeRefreshLayout() {
        swipeRefreshLayout.setColorSchemeColors(Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW);
        swipeRefreshLayout.setOnRefreshListener(this);
    }

    private void prepareRecyclerView() {
        final LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    public void onRefresh() {

    }
}
