package com.example.nalex.mybakingapp;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.nalex.mybakingapp.adapter.RecipesAdapter;
import com.example.nalex.mybakingapp.ui.SelectRecipes;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class SelectRecipeStepContentsTest {

    public static final String INGREDIENTS_ITEM = "Ingredients";

    @Rule
    public ActivityTestRule<SelectRecipes> mActivityTestRule = new ActivityTestRule<>(SelectRecipes.class);

    @Test
    public void checkRecyclerViewFirstItem_MatchesIngredients() {

        try {
            Thread.sleep(4000); //TODO: Sync with Retrofit (Future work)
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //navigate to SelectRecipeTest
        onView(withId(R.id.recipes_list_recycler_view))
                .perform(RecyclerViewActions.<RecipesAdapter.RecipeViewHolder>actionOnItemAtPosition(0,click()));

        //check if master list fragment's view hierarchy contains view with Ingredients
        onView(withId(R.id.master_list_fragment)).check(matches(hasDescendant(withText(INGREDIENTS_ITEM))));
    }

}
