package com.cardviewer.provider;

import android.net.Uri;
import com.cardviewer.Common.DataProvider;

/**
 * Created by Android on 26.10.14.
 */
public class DataProviderImpl extends DataProvider {
    @Override
    public Uri getAuthority() {
        return Uri.parse("content://com.cardviewer.provider.L4_U1_L1");
    }
}
