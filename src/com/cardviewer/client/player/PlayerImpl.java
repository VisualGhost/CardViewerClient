package com.cardviewer.client.player;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.text.TextUtils;
import android.widget.Toast;

/**
 * PlayerImpl
 */
public class PlayerImpl implements Player {

    private MediaPlayer mMediaPlayer;
    private Context mContext;
    private OnPlayerListener mPlayerListener;
    private static final String AUDIO_MODE = "audio";

    public PlayerImpl(final Context context) {
        mContext = context;
        mMediaPlayer = new MediaPlayer();
    }

    @Override
    public void loadClip(final Uri uri) {
        try {
            mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mMediaPlayer.setOnCompletionListener(this);
            AssetFileDescriptor descriptor = mContext.getContentResolver().openAssetFileDescriptor(uri, AUDIO_MODE);
            if (descriptor == null) return;
            mMediaPlayer.setDataSource(descriptor.getFileDescriptor(), descriptor.getStartOffset(), descriptor.getLength());
            descriptor.close();
            mMediaPlayer.prepare();
            mMediaPlayer.setVolume(1f, 1f);
            mMediaPlayer.setLooping(false);
        } catch (Throwable t) {
            Toast.makeText(mContext, "loadClip = " + t.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void play() {
        if (mMediaPlayer != null && !mMediaPlayer.isPlaying()) {
            mMediaPlayer.start();
            if (mPlayerListener != null) {
                mPlayerListener.onStartPlaying();
            }
        }
    }

    @Override
    public void stop() {
        if (mMediaPlayer != null) {
            mMediaPlayer.stop();
        }
    }

    @Override
    public void release() {
        if (mMediaPlayer != null) {
            mMediaPlayer.release();
        }
    }

    @Override
    public void onCompletion(final MediaPlayer mp) {
        mp.stop();
        try {
            mp.prepare();
            mp.seekTo(0);
            if (mPlayerListener != null) {
                mPlayerListener.onCompletePlaying();
            }
        } catch (Throwable t) {
            Toast.makeText(mContext, "onCompletion = " + t.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public void setPlayerListener(final OnPlayerListener playerListener) {
        mPlayerListener = playerListener;
    }

    public interface OnPlayerListener {
        void onStartPlaying();

        void onCompletePlaying();
    }
}
