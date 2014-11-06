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
        List<ProviderInfo> providers = mProviderFinder.getProviders();
        mAdapter = new ItemAdapter(this);
        if (providers.size() > 0) {
            for (final ProviderInfo info : providers) {
                mAdapter.add(new Item() {
                    @Override
                    public String getName() {
                        return transformAuthority(info.authority);
                    }

                    @Override
                    public String getValue() {
                        return info.authority;
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

    private String transformAuthority(String authority) {
        String levelUnitLesson = authority != null ? authority.replace(Utils.COM_CARDVIEWER_PROVIDER, "") : "";
        String[] elements = levelUnitLesson.split("_");
        LessonItem item = new LessonItem(elements[0], elements[1], elements[2]);
        return item.getLessonParameters();
    }

    class LessonItem {
        String level;
        String unit;
        String lesson;

        final static String L = "L";
        final static String U = "U";
        final static String DELIMITER = " > ";

        LessonItem(String level, String unit, String lesson) {
            this.level = level;
            this.unit = unit;
            this.lesson = lesson;
        }

        String getLevel() {
            return getString(R.string.level) +" "+ level.replace(L, "");
        }

        String getUnit() {
            return getString(R.string.unit) +" "+ unit.replace(U, "");
        }

        String getLesson() {
            return getString(R.string.lesson) +" "+ lesson.replace(L, "");
        }

        String getLessonParameters() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(getLevel());
            stringBuilder.append(DELIMITER);
            stringBuilder.append(getUnit());
            stringBuilder.append(DELIMITER);
            stringBuilder.append(getLesson());
            return stringBuilder.toString();
        }

    }

    private void startLesson(final String authority) {
        Intent intent = new Intent(this, ViewActivity.class);
        intent.putExtra(Utils.AUTHORITY, "content://" + authority);
        startActivity(intent);
    }


}
