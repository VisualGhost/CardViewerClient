package com.cardviewer.Common;

import android.net.Uri;

/**
 * Card
 */
public interface Card {
    String getText();
    Uri getImageUri();
    Uri getAudioUri();
    String getImagePath();
    String getAudioPath();
}
