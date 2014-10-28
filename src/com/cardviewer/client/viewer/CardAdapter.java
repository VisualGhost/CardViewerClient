package com.cardviewer.client.viewer;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.cardviewer.Common.Card;

import java.util.List;

/**
 * CardAdapter
 */
public class CardAdapter extends FragmentPagerAdapter {

    private List<Card> mCards;

    public CardAdapter(final FragmentManager fm, List<Card> cards) {
        super(fm);
        mCards = cards;
    }

    @Override
    public Fragment getItem(final int i) {
        Card card = mCards.get(i);
        return CardFragment.newInstance(card);
    }

    @Override
    public int getCount() {
        return mCards.size();
    }
}
