package com.demo.rssapplication.common.utilities;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Point;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager.LayoutParams;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.File;

public class ViewUtils {

    /**
     * Change dp to pixel
     *
     * @param dp       dimension
     * @param mContext view context
     * @return value convert
     */
    public static int dp2px(int dp, Context mContext) {
        Resources r = mContext.getResources();
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics());
        return (int) px;
    }

    /**
     * Distance between a point and a line
     *
     * @param A point start
     * @param B point end
     * @param P point detect
     * @return distance
     */
    public static double pointToLineDistance(Point A, Point B, Point P) {
        double normalLength = Math.sqrt((B.x - A.x) * (B.x - A.x) + (B.y - A.y) * (B.y - A.y));
        return Math.abs((P.x - A.x) * (B.y - A.y) - (P.y - A.y) * (B.x - A.x)) / normalLength;
    }

    /**
     * Show soft keyboard input.
     *
     * @param context The context
     * @param view    The input view
     */
    public static void showSoftInput(Context context, View view) {
        if (context == null || view == null)
            return;

        view.requestFocus();
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
    }

    /**
     * Hide soft input if focused.
     *
     * @param activity The current activity
     */
    public static void hideSoftInput(Activity activity) {
        if (activity != null)
            activity.getWindow().setSoftInputMode(LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    /**
     * Hide soft input if focused on view.
     *
     * @param activity The current activity
     */
    public static void hideSoftInput(Activity activity, View view) {
        if (activity == null || view == null)
            return;

        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /**
     * Show prompt alert.
     *
     * @param context  The context
     * @param titleId  The alert title
     * @param msgId    The alert message
     * @param negId    The negative label
     * @param posId    The positive label
     * @param listener The positive click listener
     */
    public static AlertDialog showPrompt(Context context, int titleId, int msgId, int negId,
                                         int posId, DialogInterface.OnClickListener listener) {
        if (context == null)
            return null;

        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        // Title
        if (titleId > 0)
            builder.setTitle(titleId);

        // Message
        if (msgId > 0)
            builder.setMessage(msgId);

        // Negative button
        if (negId > 0)
            builder.setNegativeButton(negId, null);

        // Positive button
        if (posId > 0)
            builder.setPositiveButton(posId, listener);

        final AlertDialog dialog = builder.show();
        dialog.setCancelable(false);
        centerDialogMessage(dialog);

        return dialog;
    }

    /**
     * Show prompt alert.
     *
     * @param context          The context
     * @param titleId          The alert title
     * @param msgId            The alert message
     * @param negId            The negative label
     * @param posId            The positive label
     * @param negativeListener The negative click listener
     * @param positiListener   The positive click listener
     */
    public static AlertDialog showPrompt(Context context, int titleId, int msgId, int negId,
                                         int posId, DialogInterface.OnClickListener negativeListener, DialogInterface.OnClickListener positiListener) {
        if (context == null)
            return null;

        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        // Title
        if (titleId > 0)
            builder.setTitle(titleId);

        // Message
        if (msgId > 0)
            builder.setMessage(msgId);

        // Negative button
        if (negId > 0)
            builder.setNegativeButton(negId, negativeListener);

        // Positive button
        if (posId > 0)
            builder.setPositiveButton(posId, positiListener);

        final AlertDialog dialog = builder.show();
        dialog.setCancelable(false);
        centerDialogMessage(dialog);

        return dialog;
    }

    /**
     * Show prompt alert.
     *
     * @param context The context
     * @param titleId The alert title
     * @param msgId   The alert message
     * @param posId   The positive label
     * @return alertdialog
     */
    public static AlertDialog showPrompt(Context context, int titleId, int msgId, int posId) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        // Title
        if (titleId > 0)
            builder.setTitle(titleId);

        // Message
        if (msgId > 0)
            builder.setMessage(msgId);

        // Position button
        if (posId > 0)
            builder.setPositiveButton(posId, new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                }
            });

        final AlertDialog dialog = builder.show();
        dialog.setCancelable(false);
        centerDialogMessage(dialog);

        return dialog;
    }

    /**
     * Center dialog message.
     *
     * @param dialog The dialog
     */
    public static void centerDialogMessage(AlertDialog dialog) {
        ((TextView) dialog.findViewById(android.R.id.message)).setGravity(Gravity.CENTER);
    }

    /**
     * Load image from file to image view
     *
     * @param context   {@link Context} The context
     * @param filePath  {@link String} The file path
     * @param imageView {@link ImageView} The image view
     */
    public static void loadImage(Context context, String filePath, ImageView imageView) {
        if (TextUtils.isEmpty(filePath))
            return;

        File f = new File(filePath);
        if (!f.exists())
            return;

        Glide.with(context).load(f).fitCenter().into(imageView);
    }
}