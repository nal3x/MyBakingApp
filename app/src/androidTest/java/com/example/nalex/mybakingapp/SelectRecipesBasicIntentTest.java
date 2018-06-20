package com.example.nalex.mybakingapp;


import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.nalex.mybakingapp.adapter.RecipesAdapter;
import com.example.nalex.mybakingapp.ui.SelectRecipeStep;
import com.example.nalex.mybakingapp.ui.SelectRecipes;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtraWithKey;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.allOf;

@RunWith(AndroidJUnit4.class)
public class SelectRecipesBasicIntentTest {
    @Rule public IntentsTestRule<SelectRecipes> mIntentsRule =
            new IntentsTestRule<>(SelectRecipes.class);

    @Test
    public void clickRecipeLaunchesSelectRecipeStep() {

        try {
            Thread.sleep(3000); //TODO: Sync with Retrofit (Future work)
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withId(R.id.recipes_list_recycler_view))
                .perform(RecyclerViewActions.<RecipesAdapter.RecipeViewHolder>scrollToPosition(3))
                .perform(RecyclerViewActions.<RecipesAdapter.RecipeViewHolder>actionOnItemAtPosition(3,click()));

        intended(allOf(
                hasComponent(SelectRecipeStep.class.getName()),
                hasExtraWithKey(SelectRecipes.SELECT_RECIPES_ACTIVITY_INTENT_KEY)
                ));

    }
}
