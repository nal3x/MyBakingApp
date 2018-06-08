package com.example.nalex.mybakingapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.example.nalex.mybakingapp.R;
import com.example.nalex.mybakingapp.model.Recipe;

public class SelectRecipeStep extends AppCompatActivity implements MasterListFragment.onMasterListClickListener {

    /* Activity for selecting (and viewing) the recipe steps. This activity has different layouts
     * for mobile and tablet. In the first case the activity layout contains only the Master List
     * Fragment and its listener initiaties the ViewRecipeStepActivity, a separate activity used
     * only for mobiles.
     */

    Recipe mRecipe;
    private boolean mTwoPane;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_recipe_step);

        //get the Recipe from the activity that started this activity
        Intent intent = getIntent();
        mRecipe = intent.getExtras().getParcelable(SelectRecipes.SELECT_RECIPES_ACTIVITY_INTENT_KEY);

        //setting the content for master list
        MasterListFragment fragment = (MasterListFragment) getSupportFragmentManager().findFragmentById(R.id.master_list_fragment );
        fragment.setSteps(mRecipe);

        //checking for two-pane
        if(findViewById(R.id.two_pane_step_linear_layout) != null) {
            mTwoPane = true;

            //TODO: savedinstancestate here! For fragments too!!!

            //If in two pane mode we need to make fragment transactions to fill the layout
            FragmentManager fragmentManager = getSupportFragmentManager();

            StepDescriptionFragment descriptionFragment = new StepDescriptionFragment();
            descriptionFragment.setStepDescription(mRecipe.getSteps().get(0).getDescription());
            fragmentManager.beginTransaction()
                    .add(R.id.step_description_container, descriptionFragment)
                    .commit();
        } else {
            mTwoPane = false;
        }
    }



    @Override
    public void onStepClicked(int position) {
        /* determine what happens when user clicks on a Step. Reminder: position 0 of the steps holds
         * the ingredients, position 1 is Recipe's step 0.
         */
        if (mTwoPane) {
            //In two-pane mode, make new Fragments and replace the existing ones
            FragmentManager fragmentManager = getSupportFragmentManager();
            StepDescriptionFragment descriptionFragment = new StepDescriptionFragment();
            if (position == 0) {
                //user selected the Ingredients. We use our helper method to get them as a single String
                descriptionFragment.setStepDescription(mRecipe.getRecipeIngredientsAsString());
            }
            else {
                int stepNumberSelected = position - 1;
                descriptionFragment.setStepDescription(mRecipe.getSteps().get(stepNumberSelected).getDescription());
            }
            fragmentManager.beginTransaction()
                    .replace(R.id.step_description_container, descriptionFragment)
                    .commit();
        }
        else {
            //in single pane, launch a different activity that uses the above fragments
        }


    }
}
