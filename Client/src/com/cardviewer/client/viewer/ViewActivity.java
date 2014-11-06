package com.cardviewer.client.viewer;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;
import android.widget.TextView;
import com.cardviewer.Common.Card;
import com.cardviewer.Common.CardImpl;
import com.cardviewer.Common.Utils;
import com.cardviewer.client.R;

import java.util.ArrayList;
import java.util.List;

/**
 * ViewActivity
 */
public class ViewActivity extends FragmentActivity
        implements LoaderManager.LoaderCallbacks<Cursor>, ViewPager.OnPageChangeListener {

    private static final int URL_LOADER = 0;
    private Uri contentUri;
    private ViewPager mViewPager;
    private TextView mCardNumber;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUpActionBar();
        setContentView(R.layout.main);
        contentUri = Uri.parse(getIntent().getStringExtra(Utils.AUTHORITY));
        getLoaderManager().initLoader(URL_LOADER, null, this);
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setOnPageChangeListener(this);
        mCardNumber = (TextView) findViewById(R.id.card_number);
    }

    @Override
    public Loader<Cursor> onCreateLoader(final int id, final Bundle args) {
        switch (id) {
            case URL_LOADER:
                // Returns a new CursorLoader
                return new CursorLoader(this,   // Parent activity context
                                               contentUri,        // Table to query
                                               getProjection(),     // Projection to return
                                               null,            // No selection clause
                                               null,            // No selection arguments
                                               null             // Default sort order
                );
            default:
                // An invalid id was passed in
                return null;
        }
    }


    private String[] getProjection() {
        return new String[]{"_id",
                                   Utils.COLUMN_NAME,
                                   Utils.COLUMN_IMAGE,
                                   Utils.COLUMN_AUDIO,
                                   Utils.COLUMN_LEVEL_NUMBER,
                                   Utils.COLUMN_UNIT_NUMBER,
                                   Utils.COLUMN_UNIT_NAME,
                                   Utils.COLUMN_LESSON_NUMBER
        };
    }

    @Override
    public void onLoadFinished(final Loader<Cursor> loader, final Cursor data) {
        List<Card> cards = new ArrayList<Card>();
        while (data.moveToNext()) {
            CardImpl card = new CardImpl();
            card.setText(data.getString(1));
            card.setImageUri(Uri.parse(data.getString(2)));
            card.setAudioUri(Uri.parse(data.getString(3)));
            cards.add(card);
        }
        CardAdapter adapter = new CardAdapter(getSupportFragmentManager(), cards);
        mViewPager.setAdapter(adapter);
        managePageNumber(mViewPager.getCurrentItem() + 1);
    }


    private void managePageNumber(int number) {
        int total = mViewPager.getAdapter() != null ? mViewPager.getAdapter().getCount() : 0;
        if (mCardNumber != null) {
            if (number == total) {
                mCardNumber.setText(getString(R.string.done));
            } else {
                mCardNumber.setText(String.valueOf(number) + "/" + String.valueOf(total));

            }
        }
    }


    @Override
    public void onLoaderReset(final Loader<Cursor> loader) {

    }

    private void setUpActionBar() {
        if (getActionBar() != null) {
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPageScrolled(int i, float v, int i2) {

    }

    @Override
    public void onPageSelected(int i) {
        managePageNumber(i + 1);
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }
}
