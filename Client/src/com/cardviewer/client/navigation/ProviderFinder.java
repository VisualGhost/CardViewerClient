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

    public List<LessonItem> getProviders() {
        List<ProviderInfo> allProviders = mPackageManager.queryContentProviders(null, 0, 0);
        List<LessonItem> ourProvider = null;
        for (ProviderInfo info : allProviders) {
            String authority = info.authority;
            if (Utils.isCorrectProvider(authority)) {
                if (ourProvider == null) {
                    ourProvider = new ArrayList<LessonItem>();
                }
                ourProvider.add(getLesson(authority));
            }
        }
        if(ourProvider!=null){
            Collections.sort(ourProvider);
        }
        return ourProvider == null ? Collections.<LessonItem>emptyList() : ourProvider;
    }

    private LessonItem getLesson(String authority) {
        String levelUnitLesson = authority != null ? authority.replace(Utils.COM_CARDVIEWER_PROVIDER, "") : "";
        String[] elements = levelUnitLesson.split("_");
        return new LessonItem(authority, elements[0], elements[1], elements[2]);
    }
}
