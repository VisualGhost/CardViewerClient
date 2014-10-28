package com.cardviewer.client.navigation;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import com.cardviewer.Common.Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Android on 26.10.14.
 */
public class ProviderFinder {

    private final PackageManager mPackageManager;

    public ProviderFinder(Context context) {
        mPackageManager = context.getPackageManager();
    }

    public List<ProviderInfo> getProviders() {
        List<ProviderInfo> allProviders = mPackageManager.queryContentProviders(null, 0, 0);
        List<ProviderInfo> ourProvider = null;
        for (ProviderInfo info : allProviders) {
            if (Utils.isCorrectProvider(info.authority)) {
                if (ourProvider == null) {
                    ourProvider = new ArrayList<ProviderInfo>();
                }
                ourProvider.add(info);
            }
        }
        return ourProvider == null ? Collections.<ProviderInfo>emptyList() : ourProvider;
    }
}
