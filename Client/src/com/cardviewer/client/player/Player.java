package com.cardviewer.client.player;

import android.media.MediaPlayer;
import android.net.Uri;

/**
 * Player
 */
public interface Player extends MediaPlayer.OnCompletionListener {

    void loadClip(Uri uri);

    void play();

    void stop();

    void release();
}
