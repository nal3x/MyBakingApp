package com.example.nalex.mybakingapp.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.nalex.mybakingapp.R;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ThumbnailFragment extends Fragment {

    //Fragment holding a view of the thumbnail, used when no video is present in a recipe step

    public final static String THUMBNAIL_URL_KEY = "thumbnailUrl";

    @BindView(R.id.thumbnail_view) ImageView thumbnailImageView;

    public ThumbnailFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_thumbnail, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        String thumbnailUrl = getArguments().getString(THUMBNAIL_URL_KEY);
        Picasso.get().load(thumbnailUrl).into(thumbnailImageView);
    }
}
