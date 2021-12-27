package com.csz.core.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.view.inputmethod.InputMethodManager;


/**
 * @author caishuzhan
 */
@SuppressWarnings("WeakerAccess")
public final class Util {

    public static final String SP = "panel_db";
    public static final String KEYBOARD = "keyboard_panel";

    public static void showKeyboard(final View view) {
        view.requestFocus();
        InputMethodManager inputManager = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.showSoftInput(view, 0);
    }

    public static void hideKeyboard(final View view) {
        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
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
}
