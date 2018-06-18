package com.example.nalex.mybakingapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.nalex.mybakingapp.R;
import com.example.nalex.mybakingapp.adapter.RecipeStepPagerAdapter;
import com.example.nalex.mybakingapp.model.Recipe;

public class ViewRecipeSteps extends AppCompatActivity {

    /* This activity is only used on mobile (widths < 600dp) and is launched by SelectRecipeStep
     * activity. It has a complete recipe as a member in order to navigate between steps. The
     * ViewRecipeSteps activity uses a ViewPager to navigate between host fragments (StepFragment)
     * which include nested fragments for recipe step details (video or thumbnail + stepdescription).
     */
    private Recipe mRecipe;
    private int stepShown; //position of the adapter in SelectRecipeStep activity
    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_recipe_steps);

        Intent intent = getIntent();
        mRecipe = intent.getExtras().getParcelable(SelectRecipeStep.SELECT_RECIPE_STEP_RECIPE_INTENT_KEY);
        stepShown = intent.getExtras().getInt(SelectRecipeStep.SELECT_RECIPE_STEP_STEP_SELECT_INTENT_KEY);

        mPager = findViewById(R.id.pager);
        mPagerAdapter = new RecipeStepPagerAdapter(getSupportFragmentManager(), mRecipe);
        mPager.setAdapter(mPagerAdapter);
        mPager.setCurrentItem(stepShown);
    }
}