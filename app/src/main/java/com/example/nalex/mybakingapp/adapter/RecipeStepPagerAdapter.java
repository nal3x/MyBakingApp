package com.example.nalex.mybakingapp.adapter;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.text.TextUtils;

import com.example.nalex.mybakingapp.model.Recipe;
import com.example.nalex.mybakingapp.model.Step;
import com.example.nalex.mybakingapp.ui.StepFragment;

public class RecipeStepPagerAdapter extends FragmentStatePagerAdapter {

    //This pager adapter needs the recipe to navigate both between steps and ingredients
    private Recipe mRecipe;

    public RecipeStepPagerAdapter(FragmentManager fm, Recipe recipe) {
        super(fm);
        mRecipe = recipe;

    }
    @Override
    public StepFragment getItem(int position) {
        //for position 0 return a fragment with ingredients, else return recipe step of position - 1
        if (position == 0) {
            return StepFragment.newInstance(mRecipe.getImage(), mRecipe.getRecipeIngredientsAsString());
        }
        else {
            Step step = mRecipe.getSteps().get(position - 1);
            if (TextUtils.isEmpty(step.getVideoURL())) {
                if (TextUtils.isEmpty(step.getThumbnailURL())) {
                    return StepFragment.newInstance(mRecipe.getImage(), step.getDescription());
                }
                return StepFragment.newInstance(step.getThumbnailURL(), step.getDescription());
            }
            return StepFragment.newInstance(step.getVideoURL(), step.getDescription());
        }
    }

    @Override
    public int getCount() {
        //recipe steps + ingredients...
        return mRecipe.getSteps().size() + 1;
    }
}
