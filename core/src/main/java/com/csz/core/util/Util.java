package com.csz.core.util;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.SharedPreferences;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


/**
 * @author caishuzhan
 */
@SuppressWarnings("WeakerAccess")
public final class Util {

    public static final String SP = "panel_db";
    public static final String KEYBOARD = "keyboard_panel";

    public static void showKeyboard(final View view) {
        view.requestFocus();
        InputMethodManager inputManager =
                (InputMethodManager) view.getContext().getSystemService(
                        Context.INPUT_METHOD_SERVICE);
        inputManager.showSoftInput(view, 0);
    }

    public static void hideKeyboard(final View view) {
        InputMethodManager imm =
                (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static void saveKeyboardHeight(Context context, int panelHeight) {
        SharedPreferences preferences = context.getApplicationContext().getSharedPreferences(SP, Context.MODE_PRIVATE);
        preferences.edit().putInt(KEYBOARD, panelHeight).commit();
    }

    public static int getKeyboardHeight(Context context) {
        SharedPreferences preferences = context.getApplicationContext().getSharedPreferences(SP, Context.MODE_PRIVATE);
        return preferences.getInt(KEYBOARD, 0);
    }

    public static View getActivityRoot(View view) {
        return getContentRoot(findActivity(view.getContext())).getRootView();
    }

    public static ViewGroup getContentRoot(Activity activity) {
        return activity.findViewById(android.R.id.content);
    }
    /**
     * @param context
     * @return maybe null , The view might be somewhere else, like a service.
     */
    @Nullable
    public static Activity findActivity(@NonNull Context context) {
        if (context instanceof Activity) {
            return (Activity) context;
        } else if (context instanceof ContextWrapper) {
            return findActivity(((ContextWrapper) context).getBaseContext());
        } else {
            return null;
        }
    }
}
