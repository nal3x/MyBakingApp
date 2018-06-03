package com.example.nalex.mybakingapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nalex.mybakingapp.R;
import com.example.nalex.mybakingapp.model.Recipe;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipesAdapter extends RecyclerView.Adapter<RecipesAdapter.RecipeViewHolder> {

    private List<Recipe> mRecipesList; //data source
    private Context mContext;

    public RecipesAdapter(Context context, List<Recipe> recipesList) {
        mRecipesList = recipesList;
        mContext = context;
    }

    public static class RecipeViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.recipe_cardview) CardView mRecipeCard;
        @BindView(R.id.recipe_image) ImageView mRecipeImage;
        @BindView(R.id.recipe_title) TextView mRecipeTitle;
        @BindView(R.id.recipe_servings) TextView mRecipeServings;

        public RecipeViewHolder (View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @NonNull
    @Override
    public RecipesAdapter.RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //create new view for recipe by inflating recipe_list_item
        View recipeView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recipe_list_item, parent, false);

        //constructing the viewholder
        RecipeViewHolder viewHolder = new RecipeViewHolder(recipeView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {

        //binding data from RecipesList data source to the views of the viewholder
        Recipe recipe = mRecipesList.get(position);
        String imageUrl = recipe.getImage();
        if (!TextUtils.isEmpty(imageUrl)) {
            Picasso.get().load(imageUrl).into(holder.mRecipeImage);
        }

        holder.mRecipeTitle.setText(recipe.getName());
        holder.mRecipeServings.setText(mContext.getResources().getString(R.string.servings)
                + String.valueOf(recipe.getServings()));

    }


    @Override
    public int getItemCount() {
        if (null == mRecipesList) return 0;
        return mRecipesList.size();
    }
}
