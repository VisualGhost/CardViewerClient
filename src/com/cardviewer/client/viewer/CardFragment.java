package com.cardviewer.client.viewer;

import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.cardviewer.Common.Card;
import com.cardviewer.client.R;
import com.cardviewer.client.player.PlayerImpl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * CardFragment
 */
public class CardFragment extends Fragment implements PlayerImpl.OnPlayerListener {

    private String mText;
    private Uri mImageUri;
    private Uri mAudioUri;

    private ImageView mPlayButton;
    private PlayerImpl mPlayer;

    private CardFragment(final Card card) {
        mText = card.getText();
        mImageUri = card.getImageUri();
        mAudioUri = card.getAudioUri();
    }

    public static CardFragment newInstance(final Card card) {
        return new CardFragment(card);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        return inflater.inflate(R.layout.card_layout, container, false);
    }

    @Override
    public void onActivityCreated(final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        TextView text = (TextView) getView().findViewById(R.id.text);
        if (TextUtils.isEmpty(mText)) {
            RelativeLayout relativeLayout = (RelativeLayout)getView().findViewById(R.id.text_button_holder);
            relativeLayout.setVisibility(View.GONE);
        }
        ImageView image = (ImageView) getView().findViewById(R.id.image);
        text.setText(mText);
        try {
            image.setImageBitmap(getBitmap(mImageUri));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        mPlayButton = (ImageView) getView().findViewById(R.id.play_button);
        mPlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if (mPlayer == null) {
                    mPlayer = new PlayerImpl(getActivity());
                    mPlayer.setPlayerListener(CardFragment.this);
                    mPlayer.loadClip(mAudioUri);
                }
                mPlayer.play();
            }
        });
    }

    private Bitmap getBitmap(final Uri uri) throws FileNotFoundException {
        InputStream is = getActivity().getContentResolver().openInputStream(uri);
        return BitmapFactory.decodeStream(is);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void setMenuVisibility(final boolean visible) {
        super.setMenuVisibility(visible);
        if (!visible && mPlayer != null) {
            mPlayButton.setImageResource(R.drawable.play_btn_selector);
            mPlayButton.setEnabled(true);
            mPlayer.stop();
            mPlayer.release();
            mPlayer.setPlayerListener(null);
            mPlayer = null;
        }
    }

    @Override
    public void onStartPlaying() {
        mPlayButton.setImageResource(R.drawable.play_icon_white);
        mPlayButton.setEnabled(false);
    }

    @Override
    public void onCompletePlaying() {
        mPlayButton.setImageResource(R.drawable.play_btn_selector);
        mPlayButton.setEnabled(true);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPlayer != null) {
            mPlayer.setPlayerListener(null);
            mPlayer.release();
        }
    }
}
