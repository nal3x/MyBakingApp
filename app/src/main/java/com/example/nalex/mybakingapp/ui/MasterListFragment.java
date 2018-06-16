package com.example.nalex.mybakingapp.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nalex.mybakingapp.R;
import com.example.nalex.mybakingapp.adapter.StepAdapter;
import com.example.nalex.mybakingapp.model.Recipe;
import com.example.nalex.mybakingapp.model.Step;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MasterListFragment extends Fragment implements StepAdapter.StepClickListener {

    /* MasterListFragment returns a recyclerview of the steps of a recipe. As steps we define both
     * the "Ingredients" title and the short description of steps. In order to obtain them we use
     * a setter method as a non-empty constructor must be avoided and the setArguments-getArguments
     * pair would require a bundle and looked like a more complicated choice.
     */

    private List<String> mSteps;
    private StepAdapter mStepAdapter;
    private onMasterListClickListener mListener;
    private RecyclerView.LayoutManager mLayoutManager;

    @BindView(R.id.recipe_step_recycler_view) RecyclerView mRecyclerView; //TODO: check if I can do it

    public MasterListFragment (){
        //compulsory empty constructor
    }

    //Interface to communicate the step clicked to SelectRecipeStep activity which implements it
    public interface onMasterListClickListener {
        void onStepClicked(int position);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //if context cannot be cast to onStepClickListener then the mCallbacks does not implement
        //onStepClickListener
        try {
            mListener = (onMasterListClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement onMasterListStepClick");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mLayoutManager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false);
        mSteps = new ArrayList<>();
        mStepAdapter = new StepAdapter(getContext(), mSteps, this); //TODO: check Context suitability
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        //Creates and returns a rootView which contains a recyclerView of the recipe steps
        final View rootView = inflater.inflate(R.layout.fragment_master_list, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        /*  Only called if the view returned from onCreateView() is non-null. Any view setup should
         *  occur here.  E.g., view lookups and attaching view listeners.
         */
        ButterKnife.bind(this, view); //Two argument version separates instance being bound (this)
        // from the source of the views
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mStepAdapter);
    }

    /* Setter method for the steps presented by this fragment (ingredients title + short description)
     * Used be the activity that creates the fragment. Takes a Recipe as an argument.
     */
    public void setSteps(Recipe recipe) {

        mSteps.add(getResources().getString(R.string.ingredients_title)); //First "step" is just an "Ingredients" TextView.
        for (Step step : recipe.getSteps()) {
            mSteps.add(step.getShortDescription());
        }
        mStepAdapter.notifyDataSetChanged();
    }


    @Override
    public void onStepClick (int clickedStepIndex) {
        //clickedStepIndex carries the StepAdapter position
        mListener.onStepClicked(clickedStepIndex);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.mListener = null;
    }
}
