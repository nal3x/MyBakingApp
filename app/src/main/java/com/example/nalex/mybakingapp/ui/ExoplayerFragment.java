package com.example.nalex.mybakingapp.ui;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
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

public class ExoplayerFragment extends Fragment {

    private SimpleExoPlayerView mPlayerView;
    private SimpleExoPlayer mExoPlayer;
    public final static String URL_KEY = "videoUrl";

    public ExoplayerFragment() {

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.fragment_exoplayer, container, false);

        mPlayerView = rootView.findViewById(R.id.exoplayer_view);

        mExoPlayer = ExoPlayerFactory.newSimpleInstance(
                new DefaultRenderersFactory(getContext()),
                new DefaultTrackSelector(), new DefaultLoadControl());

        String mediaUrl = getArguments().getString(URL_KEY);

        Uri mediaUri = Uri.parse(mediaUrl);

        MediaSource mediaSource = new ExtractorMediaSource.Factory(
                new DefaultHttpDataSourceFactory("exoplayer")).createMediaSource(mediaUri);

        mExoPlayer.prepare(mediaSource);

        mPlayerView.setPlayer(mExoPlayer);

        mExoPlayer.setPlayWhenReady(true);

        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPlayerView = null;
        mExoPlayer = null;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

//    @Override
//    public void onPause() {
//        super.onPause();
//        mExoPlayer.setPlayWhenReady(false);
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//        mExoPlayer.setPlayWhenReady(true);
//    }
}
