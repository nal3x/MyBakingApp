package com.example.nalex.mybakingapp.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.RemoteViews;

import com.example.nalex.mybakingapp.R;
import com.example.nalex.mybakingapp.ui.SelectRecipes;
import com.example.nalex.mybakingapp.utils.Utils;

/**
 * Implementation of App Widget functionality.
 */
public class IngredientsWidgetProvider extends AppWidgetProvider {

    public static void updateIngredientsWidgets(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        //getting recipe name from shared prefs
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String selectedRecipeName = sharedPreferences.getString(Utils.PREFERRED_RECIPE, "");

        //pending intent to launch entry activity when RemoteViews are clicked
        Intent intent = new Intent(context, SelectRecipes.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        //intent for setting the service connecting remote adapter w/ remote views
        Intent serviceIntent = new Intent(context, ListWidgetService.class);

        //updating our widgets
        for (int appWidgetId : appWidgetIds) {
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.ingredients_widget);
            views.setTextViewText(R.id.widget_recipe_text, selectedRecipeName);
            views.setOnClickPendingIntent(R.id.widget, pendingIntent);
            views.setRemoteAdapter(R.id.widget_listview, serviceIntent);
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
    }


    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }


}

