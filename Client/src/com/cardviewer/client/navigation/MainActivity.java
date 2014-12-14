package com.cardviewer.client.navigation;

import android.app.ListActivity;
import android.content.Intent;
import android.content.pm.ProviderInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import com.cardviewer.Common.Utils;
import com.cardviewer.client.R;
import com.cardviewer.client.viewer.ViewActivity;

import java.util.List;

public class MainActivity extends ListActivity {

    private ItemAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ProviderFinder mProviderFinder = new ProviderFinder(this);
        List<com.cardviewer.client.navigation.LessonItem> lessonItems = mProviderFinder.getProviders();
        mAdapter = new ItemAdapter(this);
        if (lessonItems.size() > 0) {
            for (final com.cardviewer.client.navigation.LessonItem info : lessonItems) {
                mAdapter.add(new Item() {
                    @Override
                    public String getName() {
                        return getLessonParameters(info.getLevel(), info.getUnit(), info.getLesson());
                    }

                    @Override
                    public String getValue() {
                        return info.getAuthority();
                    }
                });
            }
        }
        setListAdapter(mAdapter);
        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(final AdapterView<?> parent, final View view, final int position, final long id) {
                startLesson(((Item) mAdapter.getItem(position)).getValue());
            }
        });
    }

    String getLessonParameters(String level, String unit, String lesson) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(getString(R.string.level));
        stringBuilder.append(" ");
        stringBuilder.append(level);
        stringBuilder.append(" - ");
        stringBuilder.append(getString(R.string.unit));
        stringBuilder.append(" ");
        stringBuilder.append(unit);
        stringBuilder.append(" - ");
        stringBuilder.append(getString(R.string.lesson));
        stringBuilder.append(" ");
        stringBuilder.append(lesson);
        return stringBuilder.toString();
    }

    private void startLesson(final String authority) {
        Intent intent = new Intent(this, ViewActivity.class);
        intent.putExtra(Utils.AUTHORITY, "content://" + authority);
        startActivity(intent);
    }


}
