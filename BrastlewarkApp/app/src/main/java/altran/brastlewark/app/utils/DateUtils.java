package altran.brastlewark.app.utils;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DateUtils {

    public final static String TIMEZONE_UTC = "UTC";

    public final static String ISO_8601 = "yyyy-MM-dd'T'HH:mm:ss.SSSZ"; //or "yyyy-MM-dd'T'HH:mm:ss.SSSX" with timezone

    public final static String ISO_8601_AM_PM = "yyyy-MM-dd'T'HH:mm:ss.SSS aa"; //or "yyyy-MM-dd'T'HH:mm:ss.SSSX" with timezone

    public static long toMillis(long seconds) {
        return seconds * 1000;
    }


    public static String toSeconds(float seconds) {
        return String.valueOf((int) seconds);
    }

    public static long toSeconds(long millis) {
        return millis / 1000;
    }

    /**
     * Convert a Calendar object to UNIX timestamp (time in seconds since epoch)
     */
    public static int calendarToTimestamp(Calendar calendar) {
        return (int) (calendar.getTimeInMillis() / 1000L);
    }

    /**
     * Convert  UNIX timestamp (time in seconds since epoch) to Calendar object
     */
    public static Calendar timestampToCalendar(int timestamp) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis((long) timestamp * 1000L);
        return c;
    }

    /**
     * Get system time
     *
     * @return Calendar
     */
    public static Calendar getTimeNow() {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(System.currentTimeMillis());
        return c;
    }

    public static String getTimeNowString() {
        String dateFormatted = "";

        try {
            SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

            df1.setTimeZone(TimeZone.getTimeZone(TIMEZONE_UTC));
            dateFormatted = df1.format(new Date());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dateFormatted;

    }

    public static String getTimeNowString(String pattern) {
        String dateFormatted = "";

        try {
            SimpleDateFormat df1 = new SimpleDateFormat(pattern, Locale.US);

            df1.setTimeZone(TimeZone.getTimeZone(TIMEZONE_UTC));
            dateFormatted = df1.format(new Date());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dateFormatted;

    }

    public static Calendar fromIso8601toCalendar(String iso8601) {
        return fromIso8601toCalendar(ISO_8601, iso8601);
    }

    public static Calendar fromIso8601toCalendar(String format, String iso8601) {
        Calendar calendar = Calendar.getInstance();
        try {
            SimpleDateFormat df1 = new SimpleDateFormat(format, Locale.US);
            df1.setTimeZone(TimeZone.getTimeZone(TIMEZONE_UTC));
            Date date = df1.parse(iso8601);
            calendar.setTime(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return calendar;
    }

    public static Calendar fromSmallDatetoCalendar(String smallFormatDate) {
        Calendar calendar = Calendar.getInstance();
        try {
            SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
            df1.setTimeZone(TimeZone.getTimeZone(TIMEZONE_UTC));
            Date date = df1.parse(smallFormatDate);
            calendar.setTime(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return calendar;
    }


    public static String fromIso8601toString(String iso8601) {
        String dateFormatted = "";
        try {
            SimpleDateFormat df1 = new SimpleDateFormat(ISO_8601, Locale.US);

            df1.setTimeZone(TimeZone.getTimeZone(TIMEZONE_UTC));
            Date date = df1.parse(iso8601);
            dateFormatted = df1.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateFormatted;
    }


    public static String fromIso8601toNewDateFormat(String iso8601, String format) {
        String dateFormatted = "";
        try {
            SimpleDateFormat df1 = new SimpleDateFormat(ISO_8601, Locale.US);

            df1.setTimeZone(TimeZone.getDefault());
            Date date = df1.parse(iso8601);

            SimpleDateFormat df2 = new SimpleDateFormat(format, Locale.US);
            dateFormatted = df2.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateFormatted;
    }


    public static String fromMillisToIso8601(long millis) {
        SimpleDateFormat df1 = new SimpleDateFormat(ISO_8601, Locale.US);
        df1.setTimeZone(TimeZone.getDefault());

        return df1.format(millis);
    }

    public static String timeAgo(Calendar oldTime) {
        Calendar now = Calendar.getInstance();
        CharSequence format = android.text.format.DateUtils.getRelativeTimeSpanString(
                oldTime.getTimeInMillis(),
                now.getTimeInMillis(),
                android.text.format.DateUtils.FORMAT_ABBREV_RELATIVE);

        return format.toString();
    }

}
