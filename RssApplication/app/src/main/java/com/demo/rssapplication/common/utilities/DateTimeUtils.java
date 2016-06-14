package com.demo.rssapplication.common.utilities;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;


/**
 * DateTimeUtils
 */
public class DateTimeUtils {

    public static final String TZ_ID_UTC = "UTC";
    /**
     * Giving date time format to "MMM dd". eg: Mar 24
     */
    public static final String MONTH_DAY_FORMAT = "MMM dd";
    private static final int SECOND_MILLIS = 1000;
    private static final int MINUTE_MILLIS = 60 * SECOND_MILLIS;
    private static final int HOUR_MILLIS = 60 * MINUTE_MILLIS;
    private static final int DAY_MILLIS = 24 * HOUR_MILLIS;
    public static String DATE_FORMAT = "MMMM dd, yyyy";
    public static String SHORT_TIME_FORMAT = "hh:mm a";
    public static String TIME_FORMAT = "MMMM dd, yyyy hh:mm a";


    /**
     * Get the calendar of specific timezone.
     *
     * @param timezone {@link String} The string timezone.
     * @return {@link Calendar} The calendar instance.
     */
    public static Calendar getCalendar(String timezone) {
        return Calendar.getInstance(TimeZone.getTimeZone(timezone));
    }

    /**
     * Get current UTC millisecond
     *
     * @return The milliseconds
     */
    public static long getCurrentUTCInMillis() {
        return getCalendar(TZ_ID_UTC).getTimeInMillis();
    }

    /**
     * Get date string by the input format
     *
     * @param milliSeconds date in milliseconds
     * @param dateFormat   The output format
     * @return A date string by the input format
     */
    public static String getDate(long milliSeconds, String dateFormat) {

        // Create a DateFormatter object for displaying date in specified format.
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat, Locale.getDefault());

        // Create a calendar object that will convert the date and time value in milliseconds to date.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());
    }

    /**
     * Get date string by the input format
     *
     * @param milliSeconds date in milliseconds
     * @return A datetime
     */
    public static Date getDate(long milliSeconds) {

        // Create a calendar object that will convert the date and time value in milliseconds to date.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return calendar.getTime();
    }

    /**
     * Get date string by the input format
     *
     * @param date       date in date time
     * @param dateFormat The output format
     * @return A date string by the input format
     */
    public static String getDate(Date date, String dateFormat) {

        // Create a DateFormatter object for displaying date in specified format.
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat, Locale.getDefault());
        return formatter.format(date);
    }

    /**
     * Convert time second to string format
     *
     * @param m_record_time time input in second
     * @return time format
     */
    public static String getTimeClock(long m_record_time) {
        int ss = (int) m_record_time;
        int mm = ss / 60;
        int hh = mm / 60;
        ss %= 60;
        mm %= 60;
        return String.format("%d:%02d:%02d", hh, mm, ss);
    }

    /**
     * Date Time AGO
     *
     * @param time time
     * @return time ago
     */
    public static String getTimeAgo(long time) {
        if (time < 1000000000000L) {
            // if timestamp given in seconds, convert to millis
            time *= 1000;
        }

        long now = getCurrentUTCInMillis();
        if (time > now || time <= 0) {
            return null;
        }

        final long diff = now - time;
        if (diff < MINUTE_MILLIS) {
            return "just now";
        } else if (diff < 2 * MINUTE_MILLIS) {
            return "a minute ago";
        } else if (diff < 50 * MINUTE_MILLIS) {
            return diff / MINUTE_MILLIS + " minutes ago";
        } else if (diff < 90 * MINUTE_MILLIS) {
            return "an hour ago";
        } else if (diff < 24 * HOUR_MILLIS) {
            return diff / HOUR_MILLIS + " hours ago";
        } else if (diff < 48 * HOUR_MILLIS) {
            return "yesterday";
        } else {
            return diff / DAY_MILLIS + " days ago";
        }
    }
}
