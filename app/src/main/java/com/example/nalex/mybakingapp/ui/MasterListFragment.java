package com.example.nalex.mybakingapp.ui;

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

public class MasterListFragment extends Fragment {

    /* MasterListFragment returns a recyclerview of the steps of a recipe. As steps we define both
     * the "Ingredients" title and the short description of steps. In order to obtain them we use
     * a setter method as a non-empty constructor must be avoided and the setArguments-getArguments
     * pair would require a bundle and looked like a more complicated choice.
     */

    private List<String> mSteps;
    private RecyclerView.LayoutManager mLayoutManager;
    private StepAdapter mStepAdapter;

    //@BindView(R.id.recipe_step_recycler_view) RecyclerView mRecyclerView; //TODO: check if I can do it

    public MasterListFragment (){
        //compulsory empty constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        //Should create and return the rootView containing the rv with the steps

        final View rootView = inflater.inflate(R.layout.fragment_master_list, container, false);

        RecyclerView recyclerView = rootView.findViewById(R.id.recipe_step_recycler_view);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false);

        recyclerView.setLayoutManager(layoutManager);

        mSteps = new ArrayList<>();
        mStepAdapter= new StepAdapter(getContext(), mSteps); //TODO: check Context suitability
        recyclerView.setAdapter(mStepAdapter);

        return rootView;
    }

    /* Setter method for the steps presented by this fragment (ingredients title + short description)
     * Used be the activity that creates the fragment. Takes a Recipe as an argument.
     */

    public void setSteps(Recipe recipe) {

        mSteps.add(getResources().getString(R.string.ingredients_title)); //First step is just an "Ingredients" TextView.
        for (Step step : recipe.getSteps()) {
            mSteps.add(step.getShortDescription());
        }
        mStepAdapter.notifyDataSetChanged();
    }








}
