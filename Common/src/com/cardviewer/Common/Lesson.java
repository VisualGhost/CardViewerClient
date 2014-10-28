package com.cardviewer.Common;

import com.cardviewer.Common.Card;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Android on 26.10.14.
 */
public class Lesson {

    private List<Card> mCards;
    private CardImpl mCard;
    private String mLessonNumber;
    private String mLevelNumber;
    private String mUnitNumber;
    private String mUnitName;

    public Lesson() {
        mCards = new ArrayList<Card>();
    }

    public void createCard() {
        mCard = new CardImpl();
    }

    public void setTextCard(String s) {
        if (mCard != null) {
            mCard.setText(s);
        }
    }

    public void setImageCard(String s) {
        if (mCard != null) {
            mCard.setImageFileName(s);
        }
    }

    public void setAudioCard(String s) {
        if (mCard != null) {
            mCard.setAudioFileName(s);
        }
    }

    public void addCardToCollection() {
        mCards.add(mCard);
    }

    public void setLessonNumber(String mLessonNumber) {
        this.mLessonNumber = mLessonNumber;
    }

    public void setLevelNumber(String mLevelNumber) {
        this.mLevelNumber = mLevelNumber;
    }

    public void setUnitNumber(String mUnitNumber) {
        this.mUnitNumber = mUnitNumber;
    }

    public void setUnitName(String mUnitName) {
        this.mUnitName = mUnitName;
    }

    public List<Card> getCards() {
        return mCards != null ? mCards : Collections.<Card>emptyList();
    }

    public String getLessonNumber() {
        return mLessonNumber != null ? mLessonNumber : "";
    }

    public String getLevelNumber() {
        return mLevelNumber != null ? mLevelNumber : "";
    }

    public String getUnitNumber() {
        return mUnitNumber != null ? mUnitNumber : "";
    }

    public String getUnitName() {
        return mUnitName != null ? mUnitName : "";
    }
}
