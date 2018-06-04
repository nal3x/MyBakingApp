package com.example.nalex.mybakingapp.ui;

import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;

import com.example.nalex.mybakingapp.R;
import com.example.nalex.mybakingapp.adapter.RecipesAdapter;
import com.example.nalex.mybakingapp.model.Recipe;
import com.example.nalex.mybakingapp.model.Search;
import com.example.nalex.mybakingapp.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindInt;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.nalex.mybakingapp.utils.Utils.getImageService;

public class SelectRecipes extends AppCompatActivity {

    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<Recipe> mRecipes;
    private final static String TAG = SelectRecipes.class.getSimpleName();
    private final static String RECIPE_LIST_SAVEDINSTANCESTATE_KEY = "RECIPE_LIST";
    private final static String FALLBACK_IMAGE_URL = "https://www.gretchensbakery.com/wp-content/uploads/2013/01/ingrdients-2015.jpg";

    @BindInt(R.integer.recipes_list_columns) int numberOfColumns;
    @BindView(R.id.recipes_list_recycler_view) RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_recipes);
        ButterKnife.bind(this);

        mLayoutManager = new GridLayoutManager(this, numberOfColumns);
        mRecyclerView.setLayoutManager(mLayoutManager);



        if (savedInstanceState == null) {
            mRecipes = new ArrayList<>();
            Call<List<Recipe>> call = Utils.getBakeService().getRecipes();
            call.enqueue(new Callback<List<Recipe>>() {
                @Override
                public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                    if (null != response.body()) {
                        mRecipes.addAll(response.body());
                        Log.d(TAG, "Fetched recipes. Recipe 1 name: " + mRecipes.get(1).getName());
                        fixBrokenImages();
                    }
                }
                @Override
                public void onFailure(Call<List<Recipe>> call, Throwable t) {
                    Log.d(TAG, "Failed to get recipes");
                }
            });

        } else { //load previously downloaded recipes
            mRecipes = savedInstanceState.getParcelableArrayList(RECIPE_LIST_SAVEDINSTANCESTATE_KEY);
            Log.d(TAG, "Loaded recipes list from Bundle, recipe 0 name: " + mRecipes.get(0).getName());
        }
        mAdapter = new RecipesAdapter(this, mRecipes);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void fixBrokenImages() {
        /* This method is used to provide image urls to recipes that lack images. It runs through
         * the list of recipes and, if needed, makes a query to a custom google search engine.
         * See Utils class for the parameters used.
         */

        for (final Recipe recipe : mRecipes) {
            if (TextUtils.isEmpty(recipe.getImage())) {
                Map<String, String> queryOptions = Utils.getQueryOptions(recipe.getName()); //use recipe name as search term
                Call<Search> call = getImageService().getImageUrl(queryOptions);
                call.enqueue(new Callback<Search>() {
                    @Override
                    public void onResponse(Call<Search> call, Response<Search> response) {
                        Log.d(TAG, "Response code from image search: " + String.valueOf(response.code()));
                        if (response.body() == null) {
                            //fallback image for exceeding search limit
                            Log.d(TAG, "Empty response body, setting fallback image");
                            String fallbackImage = FALLBACK_IMAGE_URL;
                            recipe.setImage(fallbackImage);
                            mAdapter.notifyDataSetChanged();
                        } else {
                            String imageUrl = response.body().getItems().get(0).getLink();
                            Log.d("TAG", "Image URL from search" + imageUrl);
                            recipe.setImage(imageUrl);
                            mAdapter.notifyDataSetChanged();
                        }
                    }
                    @Override
                    public void onFailure(Call<Search> call, Throwable t) {
                        Log.d("TAG", "Failed to search for an image URL");
                    }
                });
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(RECIPE_LIST_SAVEDINSTANCESTATE_KEY, (ArrayList<? extends Parcelable>) mRecipes);
        super.onSaveInstanceState(outState);
    }
}