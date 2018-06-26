package com.example.nalex.mybakingapp.widget;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.AdapterView;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.nalex.mybakingapp.R;
import com.example.nalex.mybakingapp.utils.Utils;

public class ListWidgetService extends RemoteViewsService {


    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new ListRemoteViewsFactory(this.getApplicationContext());
    }

    public class ListRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

        private Context mContext;
        private String[] mRecipeIngredients; //our datasource


        public ListRemoteViewsFactory(Context context) {
            this.mContext = context;
        }

        @Override
        public void onCreate() {

        }

        //called onStart and when notifyAppWidgetViewDataChanged
        @Override
        public void onDataSetChanged() {

            //get the ingredients string from shared preferences and split lines to a String array
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
            String ingredients = sharedPreferences.getString(Utils.PREFERRED_RECIPE_INGREDIENTS, "");
            mRecipeIngredients = ingredients.split("\\n");

            }


        @Override
        public void onDestroy() {

        }

        @Override
        public int getCount() {
            return mRecipeIngredients == null ? 0 : mRecipeIngredients.length;
        }

        @Override
        public RemoteViews getViewAt(int position) {

            if (position == AdapterView.INVALID_POSITION || mRecipeIngredients == null) {
                return null;
            }

            RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.widget_list_item);
            views.setTextViewText(R.id.widget_list_item_text, mRecipeIngredients[position]);
            return views;
        }


        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }
    }

}
