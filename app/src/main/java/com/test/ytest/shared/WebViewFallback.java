package com.test.ytest.shared;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import com.test.ytest.view.activities.WebViewActivity;

public class WebViewFallback implements CustomTabActivityHelper.CustomTabFallback {

    @Override
    public void openUri(Activity activity, String url) {
        Intent intent = new Intent(activity, WebViewActivity.class);
        intent.putExtra(WebViewActivity.EXTRA_URL, url);
        activity.startActivity(intent);
    }
}