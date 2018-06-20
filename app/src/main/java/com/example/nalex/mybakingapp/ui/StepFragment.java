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

    /* A container fragment which hosts a media and a step description fragment.
     * Used by ViewRecipeSteps activity as the fragment returned from the RecipeStepPagerAdapter.
     */

    private final static String ARG_PARAM1 = "mediaUrl";
    private final static String ARG_PARAM2 = "stepDescription";
    private final static String TAG = StepFragment.class.getSimpleName();

    private String mMediaUrl;
    private String mStepDescription;
    private ExoplayerFragment mCallback;

    public StepFragment() {
        // Required empty public constructor
    }

    public interface onSetUserVisibleParentCall {
        //interface used to notify nested exoplayer fragment to play video
        void onVisibilityChange(boolean isVisible);
    }

    /* Method called by the Pager to notify if this fragment is visible to the user.
     * When this method is called we notify the exoplayer fragment (if any) that visibility
     * has changed, so we can control the playback of the video.
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (mCallback != null) { //if there exists a nested exoplayer fragment
            mCallback.onVisibilityChange(getUserVisibleHint());
        }
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
        if (savedInstanceState == null) { //don't create new fragments if there are existing
            if (mMediaUrl.endsWith(".mp4")) {
                Bundle bundle = new Bundle();
                bundle.putString(ExoplayerFragment.URL_KEY, mMediaUrl);
                mCallback = new ExoplayerFragment();
                mCallback.setArguments(bundle);
                getChildFragmentManager().beginTransaction()
                        .add(R.id.fragment_step_exoplayer_container, mCallback)
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



    @Override
    public void onDetach() {
        super.onDetach();
        mCallback = null;
    }
}
