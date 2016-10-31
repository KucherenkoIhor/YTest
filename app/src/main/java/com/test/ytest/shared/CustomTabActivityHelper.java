package com.test.ytest.shared;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.customtabs.CustomTabsClient;
import android.support.customtabs.CustomTabsIntent;
import android.support.customtabs.CustomTabsServiceConnection;
import android.support.customtabs.CustomTabsSession;

import java.util.List;

/**
 * This is a helper class to manage the connection to the Custom Tabs Service.
 */
public class CustomTabActivityHelper {

    public static void openCustomTab(Activity activity,
                                     CustomTabsIntent customTabsIntent,
                                     String url,
                                     CustomTabFallback fallback) {
        String packageName = CustomTabsHelper.getPackageNameToUse(activity);

        if (packageName == null) {
            if (fallback != null) {
                fallback.openUri(activity, url);
            }
        } else {
            customTabsIntent.intent.setPackage(packageName);
            customTabsIntent.launchUrl(activity, Uri.parse(url));
        }
    }

    public interface CustomTabFallback {
        void openUri(Activity activity, String url);
    }

}