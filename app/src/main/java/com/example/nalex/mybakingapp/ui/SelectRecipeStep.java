package com.example.nalex.mybakingapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.nalex.mybakingapp.R;
import com.example.nalex.mybakingapp.model.Recipe;

public class SelectRecipeStep extends AppCompatActivity {

    /* Activity for selecting (and viewing) the recipe steps. This activity has different layouts
     * for mobile and tablet. In the first case the activity layout contains only the Master List
     * Fragment and its listener initiaties the ViewRecipeStepActivity, a separate activity used
     * only for mobiles.
     */

    Recipe mRecipe;

    //TODO: Started without layout for tablet, no TwoPane mode => no Fragment transactions


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_recipe_step);

        //get the Recipe from the activity that started this activity
        Intent intent = getIntent();
        mRecipe = intent.getExtras().getParcelable(SelectRecipes.SELECT_RECIPES_ACTIVITY_INTENT_KEY);

        MasterListFragment fragment = (MasterListFragment) getSupportFragmentManager().findFragmentById(R.id.master_list_fragment );
        fragment.setSteps(mRecipe);

    }
}
