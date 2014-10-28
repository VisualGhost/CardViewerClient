package com.cardviewer.client.navigation;

import android.app.ListActivity;
import android.content.Intent;
import android.content.pm.ProviderInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import com.cardviewer.Common.Utils;
import com.cardviewer.client.viewer.ViewActivity;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class MainActivity extends ListActivity {

    private LessonAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ProviderFinder mProviderFinder = new ProviderFinder(this);
        List<ProviderInfo> providers = mProviderFinder.getProviders();
        mAdapter = new LessonAdapter(this);
        if (providers.size() > 0) {
            for (ProviderInfo info : providers) {
                mAdapter.add(info);
            }
        }
        setListAdapter(mAdapter);
        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(final AdapterView<?> parent, final View view, final int position, final long id) {
                startLesson(mAdapter.getItem(position).authority);
            }
        });

    }

    private void startLesson(final String authority) {
        Intent intent = new Intent(this, ViewActivity.class);
        intent.putExtra(Utils.AUTHORITY, "content://" + authority);
        startActivity(intent);
    }


}
