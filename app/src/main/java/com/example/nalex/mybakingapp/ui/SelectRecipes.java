package com.example.nalex.mybakingapp.ui;

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

    @BindInt(R.integer.recipes_list_columns) int numberOfColumns;
    @BindView(R.id.recipes_list_recycler_view) RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_recipes);
        ButterKnife.bind(this);

        mLayoutManager = new GridLayoutManager(this, numberOfColumns);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mRecipes = new ArrayList<>();

        mAdapter = new RecipesAdapter(this, mRecipes);
        mRecyclerView.setAdapter(mAdapter);


        Call<List<Recipe>> call = Utils.getBakeService().getRecipes();

        call.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                if (null != response.body()) {
                    mRecipes.addAll(response.body());
                    fixBrokenImages();
                }
            }
            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                Log.e(TAG, "Failed to get recipes");

            }
        });
    }

    private void fixBrokenImages() {
        /* This method is used to provide image urls to recipes that lack images. It runs through
         * the list of recipes and, if needed, makes a query to a custom google search engine.
         * See Utils class for the parameters used.
         */

        for (final Recipe recipe : mRecipes) {
            if (TextUtils.isEmpty(recipe.getImage())) { //TextUtils?
                Map<String, String> queryOptions = Utils.getQueryOptions(recipe.getName()); //use recipe name as search term
                Call<Search> call = getImageService().getImageUrl(queryOptions);
                call.enqueue(new Callback<Search>() {
                    @Override
                    public void onResponse(Call<Search> call, Response<Search> response) {
                        if (null != response.body()) {
                            String imageUrl = response.body().getItems().get(0).getLink();
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
}