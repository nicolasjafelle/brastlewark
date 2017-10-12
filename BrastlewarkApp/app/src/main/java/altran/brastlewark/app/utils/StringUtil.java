package altran.brastlewark.app.utils;


import android.os.Build;
import android.text.Html;
import android.text.Spanned;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.regex.Pattern;


public class StringUtil {

    /**
     * Convert the BufferedReader an return as a String.
     *
     * @param br
     * @return
     */
    public static String convertToString(BufferedReader br) {
        StringBuilder sb = new StringBuilder();
        String line;

        try {
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }


    /**
     * Convert the InputStream an return as a String.
     *
     * @param is
     * @return
     */
    public static String convertToString(InputStream is) {
        return convertToString(new BufferedReader(new InputStreamReader(is)));
    }

    public static boolean isNullOrEmpty(String text) {
        return text == null || text.trim().isEmpty();
    }

    public static boolean isValidEmail(String email) {
        Pattern pattern = Pattern.compile("[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}");
        return pattern.matcher(email).matches();
    }

    public static Spanned formatHtml(String text) {
        Spanned result;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            result = Html.fromHtml(text, Html.FROM_HTML_MODE_LEGACY);
        } else {
            result = Html.fromHtml(text);
        }

        return result;
    }


}
