package com.example.nalex.mybakingapp.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nalex.mybakingapp.R;
import com.example.nalex.mybakingapp.model.Ingredient;
import com.example.nalex.mybakingapp.model.Step;

import java.util.ArrayList;
import java.util.List;

public class MasterListFragment extends Fragment {

    List<Step> mSteps;
    List<Ingredient> mIngredients;

    public MasterListFragment (){
        //compulsory empty constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        //Should create and return a RecyclerView with the ingredients and the steps
        final View rootView = inflater.inflate(R.layout.fragment_master_list, container, false);

        RecyclerView recyclerView = rootView.findViewById(R.id.recipe_step_recycler_view);

        //TODO: finish it!


    }

    public void setRecipeSteps(List<Step> steps, List<Ingredient> ingredients) { //TODO: rename to short?
        /* MasterListFragment should be informed of the ingredients and steps of the recipe
         * by the Activity
         */
        mSteps = new ArrayList<>(steps); //TODO: check contents when filled by the activity
        mIngredients = new ArrayList<>(ingredients);
    }
}
