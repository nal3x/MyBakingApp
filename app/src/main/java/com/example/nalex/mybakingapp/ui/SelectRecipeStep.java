package com.example.nalex.mybakingapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.example.nalex.mybakingapp.R;
import com.example.nalex.mybakingapp.model.Recipe;

public class SelectRecipeStep extends AppCompatActivity implements MasterListFragment.onMasterListClickListener {

    /* Activity for selecting (and viewing) the recipe steps. This activity has different layouts
     * for mobile and tablet. In the first case the activity layout contains only the Master List
     * Fragment and its listener initiaties the ViewRecipeStepActivity, a separate activity used
     * only for mobiles.
     */

    private Recipe mRecipe;
    private boolean mTwoPane;
    private StepDescriptionFragment mStepDescriptionFragment;
    private ExoplayerFragment mExoplayerFragment;
    private final FragmentManager mFragmentManager = getSupportFragmentManager();
    private final String EXOPLAYER_FRAGMENT_TAG = "exoplayerFragmentTag";

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

            if (savedInstanceState == null) {
                //If in two pane mode we need to make fragment transactions to fill the layout

                mStepDescriptionFragment = new StepDescriptionFragment();
                mStepDescriptionFragment.setStepDescription(mRecipe.getSteps().get(0).getDescription());
                mFragmentManager.beginTransaction()
                        .add(R.id.step_description_container, mStepDescriptionFragment)
                        .commit();

                String videoUrl = mRecipe.getSteps().get(0).getVideoURL();
                //a fix is applied in getVideoURL ensuring that if video Url is empty then thumbnail
                //does not accidentally point to a video
                if (!TextUtils.isEmpty(videoUrl)) {
                    Log.d("VideoURL", videoUrl);
                    Bundle bundle = new Bundle();
                    bundle.putString(ExoplayerFragment.URL_KEY, videoUrl);
                    mExoplayerFragment = new ExoplayerFragment();
                    mExoplayerFragment.setArguments(bundle);
                    mFragmentManager.beginTransaction()
                            .add(R.id.exoplayer_container, mExoplayerFragment)
                            .commit();
                }
                else {
                    //we have an empty videoURL -> thumbnailUrl does not point to a video. See above.
                    String thumbnailUrl = mRecipe.getSteps().get(0).getThumbnailURL();
                    if (TextUtils.isEmpty(thumbnailUrl)) {
                        //if thumbnailUrl is empty we load the image of the Recipe, set by our custom search.
                        thumbnailUrl = mRecipe.getImage();
                    }
                    Bundle bundle = new Bundle();
                    bundle.putString(ThumbnailFragment.THUMBNAIL_URL_KEY, thumbnailUrl);
                    ThumbnailFragment thumbnailFragment= new ThumbnailFragment();
                    thumbnailFragment.setArguments(bundle);
                    mFragmentManager.beginTransaction()
                            .add(R.id.exoplayer_container, thumbnailFragment)
                            .commit();
                }
            }

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
            if (position == 0) {
                /* If the user selected the Ingredients, we use our recipe's helper method
                 * getRecipeIngredientsAsString() to get ingredients as a single String
                 * and then replace the StepDescriptionFragment
                 */
                mStepDescriptionFragment = new StepDescriptionFragment();
                mStepDescriptionFragment.setStepDescription(mRecipe.getRecipeIngredientsAsString());
                mFragmentManager.beginTransaction()
                        .replace(R.id.step_description_container, mStepDescriptionFragment)
                        .commit();

                String thumbnailUrl = mRecipe.getSteps().get(0).getThumbnailURL();
                if (TextUtils.isEmpty(thumbnailUrl)) {
                    thumbnailUrl = mRecipe.getImage();
                }
                Bundle bundle = new Bundle();
                bundle.putString(ThumbnailFragment.THUMBNAIL_URL_KEY, thumbnailUrl);
                ThumbnailFragment thumbnailFragment= new ThumbnailFragment();
                thumbnailFragment.setArguments(bundle);
                mFragmentManager.beginTransaction()
                        .replace(R.id.exoplayer_container, thumbnailFragment)
                        .commit();

            }
            else {
                //User selects a true recipe step, not ingredients
                int stepNumberSelected = position - 1;
                mStepDescriptionFragment = new StepDescriptionFragment();
                mStepDescriptionFragment.setStepDescription(mRecipe.getSteps().get(stepNumberSelected).getDescription());
                mFragmentManager.beginTransaction()
                        .replace(R.id.step_description_container, mStepDescriptionFragment)
                        .commit();

                String videoUrl = mRecipe.getSteps().get(stepNumberSelected).getVideoURL();

                if (!TextUtils.isEmpty(videoUrl)) {
                    mExoplayerFragment = new ExoplayerFragment();
                    Log.d("VideoURL", videoUrl);
                    Bundle bundle = new Bundle();
                    bundle.putString(ExoplayerFragment.URL_KEY, videoUrl);

                    mExoplayerFragment.setArguments(bundle);
                    mFragmentManager.beginTransaction()
                            .replace(R.id.exoplayer_container, mExoplayerFragment, EXOPLAYER_FRAGMENT_TAG)
                            .commit();
                }
                else {

                    String thumbnailUrl = mRecipe.getSteps().get(0).getThumbnailURL();
                    if (TextUtils.isEmpty(thumbnailUrl)) {
                        thumbnailUrl = mRecipe.getImage();
                    }
                    Bundle bundle = new Bundle();
                    bundle.putString(ThumbnailFragment.THUMBNAIL_URL_KEY, thumbnailUrl);
                    ThumbnailFragment thumbnailFragment= new ThumbnailFragment();
                    thumbnailFragment.setArguments(bundle);
                    mFragmentManager.beginTransaction()
                            .replace(R.id.exoplayer_container, thumbnailFragment)
                            .commit();
                }
            }

        }
        else {
            //TODO: in single pane, launch a different activity that uses the above fragments
        }


    }
}
