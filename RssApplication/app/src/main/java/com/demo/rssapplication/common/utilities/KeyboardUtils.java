package com.demo.rssapplication.common.utilities;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class KeyboardUtils {

    /**
     * Hides the soft keyboard from screen
     *
     * @param view Usually the EditText, but in dynamically  layouts you should pass the layout
     * instead of the EditText
     * @return true, if keyboard has been hidden, otherwise false (i.e. the keyboard was not displayed
     * on the screen or no Softkeyboard because device has hardware keyboard)
     */
    public static boolean hideKeyboard(View view) {

        if (view == null) {
            throw new NullPointerException("View is null!");
        }

        try {
            InputMethodManager imm =
                    (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);

            if (imm == null) {
                return false;
            }

            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    /**
     * Check soft keyboard from screen is show or hide
     *
     * @param view the EditText
     * @return show or hide
     */
    public static boolean keyboardIsShowed(View view) {
        InputMethodManager imm = (InputMethodManager) view.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);

        if (imm.isAcceptingText()) {
            // writeToLog("Software Keyboard was shown");
            return true;
        } else {
            // writeToLog("Software Keyboard was not shown");
            return false;
        }
    }
}