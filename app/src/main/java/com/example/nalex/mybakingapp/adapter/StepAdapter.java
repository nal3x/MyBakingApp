package com.example.nalex.mybakingapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.nalex.mybakingapp.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepAdapter extends RecyclerView.Adapter<StepAdapter.StepViewHolder>{

    private List<String> mStepsList;
    private Context mContext;
    private StepClickListener mStepListener;

    //Interface implemented by the MasterListFragment
    public interface StepClickListener {
        public void onStepClick(int clickedStepIndex);
    }


    public StepAdapter(Context context, List<String> recipeSteps, StepClickListener stepClickListener) {
        mStepsList = recipeSteps;
        mContext = context;
        mStepListener = stepClickListener;
    }

    public class StepViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.step_short_description_textview) TextView mRecipeStepDescription;
        @BindView(R.id.recipe_step_metadata) TextView mStepMetaData;

        public StepViewHolder (View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mStepListener.onStepClick(getAdapterPosition());
        }
    }

    @NonNull
    @Override
    public StepViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View recipeView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recipe_step_list_item, parent, false);

        StepViewHolder stepViewHolder = new StepViewHolder(recipeView);

        return stepViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull StepViewHolder holder, int position) {
        String recipeStep = mStepsList.get(position);
        holder.mRecipeStepDescription.setText(recipeStep);
        if (position > 1)
            holder.mStepMetaData.setText(String.valueOf(position -1));
        else
            holder.mStepMetaData.setText("");
    }

    @Override
    public int getItemCount() {
        if (null == mStepsList) return 0;
        return mStepsList.size();
    }

}
