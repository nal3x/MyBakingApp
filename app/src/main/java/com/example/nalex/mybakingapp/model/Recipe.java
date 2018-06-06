package com.example.nalex.mybakingapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

public class Recipe implements Parcelable { //TODO: Keep Parcelable only for Recipe(?)

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("ingredients")
    @Expose
    private List<Ingredient> ingredients = null;
    @SerializedName("steps")
    @Expose
    private List<Step> steps = null;
    @SerializedName("servings")
    @Expose
    private int servings;
    @SerializedName("image")
    @Expose
    private String image;

    public final static Parcelable.Creator<Recipe> CREATOR = new Creator<Recipe>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        public Recipe[] newArray(int size) {
            return (new Recipe[size]);
        }

    }
            ;

    private Recipe(Parcel in) {
        this.id = ((int) in.readValue((int.class.getClassLoader())));
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        this.ingredients = new ArrayList<Ingredient>();
        in.readTypedList(this.ingredients, Ingredient.CREATOR);
        this.steps = new ArrayList<Step>();
        in.readTypedList(this.steps, Step.CREATOR);
        this.servings = ((int) in.readValue((int.class.getClassLoader())));
        this.image = ((String) in.readValue((String.class.getClassLoader())));
    }

    public Recipe() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public List<Step> getSteps() {
        return steps;
    }

    public void setSteps(List<Step> steps) {
        this.steps = steps;
    }

    public int getServings() {
        return servings;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(name);
        dest.writeTypedList(ingredients);
        dest.writeTypedList(steps);
        dest.writeValue(servings);
        dest.writeValue(image);
    }

    /* Helper method that returns the ingredients of the recipe as a single String. Ingredients are
     * separated by a newline character.
     */
    public String getRecipeIngredientsAsString () {
        StringBuilder recipeIngredients = new StringBuilder();

        for (Ingredient ingredient : this.getIngredients()) {
            double quantity = ingredient.getQuantity();
            recipeIngredients.append(String.valueOf(quantity));

            if (ingredient.getMeasure() == "UNIT") { //3 large whole eggs
                recipeIngredients.append(" ").append(ingredient.getIngredient());
            } else { //if we have a unit of measure we append it directly to quantity lowercased
                recipeIngredients.append(ingredient.getMeasure().toLowerCase()); //eg 250g
                recipeIngredients.append(" of "); //250g of
                recipeIngredients.append(ingredient.getIngredient()); //250g of sugar
            }
            recipeIngredients.append("\n");
        }
        recipeIngredients.deleteCharAt(recipeIngredients.length() - 1); //deleting the last newline
        return recipeIngredients.toString();
    }

}
