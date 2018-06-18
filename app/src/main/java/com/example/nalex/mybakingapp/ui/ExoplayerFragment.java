package com.example.nalex.mybakingapp.ui;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nalex.mybakingapp.R;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

public class ExoplayerFragment extends Fragment implements StepFragment.onSetUserVisibleParentCall {

    private SimpleExoPlayerView mPlayerView;
    private SimpleExoPlayer mExoPlayer;
    public final static String URL_KEY = "videoUrl";
    private final static String VIDEO_POSITION_KEY = "positionKey";
    private final static String VIDEO_STATE_KEY = "stateKey";
    private long mPlaybackPosition;
    private boolean mPlayWhenReady = true;
    private Uri mediaUri;
    private final static String TAG = ExoplayerFragment.class.getSimpleName();

    public ExoplayerFragment() {
    }

    @Override
    public void onVisibilityChange(boolean isVisible) {
        if (isVisible) {
            initializePlayer();
        } else {
            releasePlayer();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_exoplayer, container, false);
        mPlayerView = rootView.findViewById(R.id.exoplayer_view);
        //onCreateView called before onStart and onStop, where we initialize the player
        if (savedInstanceState != null) {
            mPlaybackPosition = savedInstanceState.getLong(VIDEO_POSITION_KEY);
            mPlayWhenReady = savedInstanceState.getBoolean(VIDEO_STATE_KEY);
        }
        return rootView;
    }

    private void initializePlayer() {

        String mediaUrl = getArguments().getString(URL_KEY);
        mediaUri = Uri.parse(mediaUrl);

        mExoPlayer = ExoPlayerFactory.newSimpleInstance(
                new DefaultRenderersFactory(getContext()),
                new DefaultTrackSelector(), new DefaultLoadControl());
        mPlayerView.setPlayer(mExoPlayer);
        mPlayerView.setUseController(false); //can hide exoplayer controller
        MediaSource mediaSource = new ExtractorMediaSource.Factory(
                new DefaultHttpDataSourceFactory("myBakingApp")).createMediaSource(mediaUri);

        mExoPlayer.prepare(mediaSource, true, false);
        mExoPlayer.setPlayWhenReady(mPlayWhenReady);
        mExoPlayer.seekTo(mPlaybackPosition);
    }


    private void releasePlayer() {
        if (mExoPlayer != null) {
            mPlaybackPosition = mExoPlayer.getCurrentPosition();
            mPlayWhenReady = mExoPlayer.getPlayWhenReady();
            mExoPlayer.release();
            mExoPlayer = null;
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mExoPlayer != null) {
            outState.putLong(VIDEO_POSITION_KEY, mExoPlayer.getCurrentPosition());
            outState.putBoolean(VIDEO_STATE_KEY, mExoPlayer.getPlayWhenReady());
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23) {
            if (getParentFragment() == null) { //if not nested, i.e. on tablets
                initializePlayer();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if ((Util.SDK_INT <= 23 || mExoPlayer == null) && getParentFragment() == null) {
            initializePlayer();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23) {
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            releasePlayer();
        }
    }

   @Override
    public void onDestroyView() {
        //cleaning references, listeners etc to avoid memory leaks
        super.onDestroyView();
        mPlayerView = null;
        mExoPlayer = null;
    }

//    @Override
//    public void onDetach() {
//        super.onDetach();
//        releasePlayer();
//        Log.d(TAG, "Exoplayer Detached");
//    }
}
