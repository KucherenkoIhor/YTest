package com.test.ytest.shared;

import android.content.Context;
import android.content.Intent;

import com.test.ytest.view.activities.WebViewActivity;

public class WebViewFallback implements CustomTabActivityHelper.CustomTabFallback {

    @Override
    public void openUri(Context context, String url) {
        Intent intent = new Intent(context, WebViewActivity.class);
        intent.putExtra(WebViewActivity.EXTRA_URL, url);
        context.startActivity(intent);
    }
}