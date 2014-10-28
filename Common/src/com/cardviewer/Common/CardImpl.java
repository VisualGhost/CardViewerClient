package com.cardviewer.Common;

import android.net.Uri;

/**
 * CardImpl
 */
public class CardImpl implements Card {

    private String mText;
    private Uri mImageUri;
    private Uri mAudioUri;
    private String mImageFileName;
    private String mAudioFileName;

    private static final String IMAGE_PATH = "images/";
    private static final String AUDIO_PATH = "audio/";
    private static final String IMAGE_FORMAT = ".png";
    private static final String AUDIO_FORMAT = ".mp3";

    public void setText(final String text) {
        mText = text;
    }

    public void setImageUri(final Uri imageUri) {
        mImageUri = imageUri;
    }

    public void setAudioUri(final Uri audioUri) {
        mAudioUri = audioUri;
    }

    public void setImageFileName(final String imageFileName) {
        mImageFileName = imageFileName;
    }

    public void setAudioFileName(final String audioFileName) {
        mAudioFileName = audioFileName;
    }

    @Override
    public String getText() {
        return mText != null ? mText : "";
    }

    @Override
    public Uri getImageUri() {
        return mImageUri;
    }

    @Override
    public Uri getAudioUri() {
        return mAudioUri;
    }

    @Override
    public String getImagePath() {
        return IMAGE_PATH + mImageFileName + IMAGE_FORMAT;
    }

    @Override
    public String getAudioPath() {
        return AUDIO_PATH + mAudioFileName + AUDIO_FORMAT;
    }
}
