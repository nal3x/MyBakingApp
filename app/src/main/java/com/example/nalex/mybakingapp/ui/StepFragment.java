package com.example.nalex.mybakingapp.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nalex.mybakingapp.R;

public class StepFragment extends Fragment {

    /* StepFragment is just a container fragment which hosts a media and a step description fragment.
     * It is used by ViewRecipeSteps activity as the fragment returned from the RecipeStepPagerAdapter.
     */

    private final static String ARG_PARAM1 = "mediaUrl";
    private final static String ARG_PARAM2 = "stepDescription";

    private String mMediaUrl;
    private String mStepDescription;

    public StepFragment() {
        // Required empty public constructor
    }

    public static StepFragment newInstance(String mediaUrl, String stepDescription) {
        StepFragment fragment = new StepFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, mediaUrl);
        args.putString(ARG_PARAM2, stepDescription);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mMediaUrl = getArguments().getString(ARG_PARAM1);
            mStepDescription = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_step, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (mMediaUrl.endsWith(".mp4")) {
            Bundle bundle = new Bundle();
            bundle.putString(ExoplayerFragment.URL_KEY, mMediaUrl);
            ExoplayerFragment exoplayerFragment = new ExoplayerFragment();
            exoplayerFragment.setArguments(bundle);
            getChildFragmentManager().beginTransaction()
                    .add(R.id.fragment_step_exoplayer_container, exoplayerFragment)
                    .commit();
        }
        else { //use a thumbnail fragment to show image instead
            Bundle bundle = new Bundle();
            bundle.putString(ThumbnailFragment.THUMBNAIL_URL_KEY, mMediaUrl);
            ThumbnailFragment thumbnailFragment= new ThumbnailFragment();
            thumbnailFragment.setArguments(bundle);
            getChildFragmentManager().beginTransaction()
                    .add(R.id.fragment_step_exoplayer_container, thumbnailFragment)
                    .commit();
        }
        StepDescriptionFragment stepDescriptionFragment = new StepDescriptionFragment();
        stepDescriptionFragment.setStepDescription(mStepDescription);
        getChildFragmentManager().beginTransaction()
                .add(R.id.fragment_step_step_description_container, stepDescriptionFragment)
                .commit();

    }
}
