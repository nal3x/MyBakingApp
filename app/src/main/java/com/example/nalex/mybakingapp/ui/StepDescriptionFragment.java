package com.example.nalex.mybakingapp.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.nalex.mybakingapp.R;

public class StepDescriptionFragment extends Fragment {

    //Fragment returning a view with text describing the recipe step

    private String mStepDescription;
    private final static String SAVE_STEP_DESCRIPTION_KEY = "saveDescription";

    public StepDescriptionFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        //load saved state if there is one
        if (savedInstanceState != null) {
            mStepDescription = savedInstanceState.getString(SAVE_STEP_DESCRIPTION_KEY);
        }

        View rootView = inflater.inflate(R.layout.fragment_step_description, container, false);
        final TextView stepDescriptionTextView = rootView.findViewById(R.id.step_long_description_textview);
        stepDescriptionTextView.setText(mStepDescription);
        return rootView;
    }

    public void setStepDescription (String description) {
        this.mStepDescription = description;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mStepDescription = null;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        //save step description
        outState.putString(SAVE_STEP_DESCRIPTION_KEY, mStepDescription);
    }
}