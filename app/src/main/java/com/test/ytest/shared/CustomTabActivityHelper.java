package com.test.ytest.shared;

import android.content.Context;
import android.net.Uri;
import android.support.customtabs.CustomTabsIntent;

/**
 * This is a helper class to manage the connection to the Custom Tabs Service.
 */
public class CustomTabActivityHelper {

    public static void openCustomTab(Context context,
                                     CustomTabsIntent customTabsIntent,
                                     String url,
                                     CustomTabFallback fallback) {
        String packageName = CustomTabsHelper.getPackageNameToUse(context);

        if (packageName == null) {
            if (fallback != null) {
                fallback.openUri(context, url);
            }
        } else {
            customTabsIntent.intent.setPackage(packageName);
            customTabsIntent.launchUrl(context, Uri.parse(url));
        }
    }

    public interface CustomTabFallback {
        void openUri(Context context, String url);
    }

}