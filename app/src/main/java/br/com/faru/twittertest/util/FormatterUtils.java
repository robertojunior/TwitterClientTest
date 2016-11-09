package br.com.faru.twittertest.util;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class FormatterUtils {

    private static String[] suffix = new String[]{"", "K", "M", "B", "T"};
    private static int MAX_LENGTH = 4;

    public static Date toDate(String createdAt) {
        Date date = null;
        try {
            SimpleDateFormat sf = new SimpleDateFormat("EEE MMM dd HH:mm:ss ZZZZZ yyyy", Locale.US);
            sf.setLenient(true);
            date = sf.parse(createdAt);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }

    public static String getTimeAgo(String createdAt) {
        return getTimeAgo(toDate(createdAt));
    }

    public static String getTimeAgo(Date date) {
        Date now = new Date();
        if (date.before(now)) {
            int daysPassed = (int) TimeUnit.MILLISECONDS.toDays(now.getTime() - date.getTime());
            if (daysPassed >= 1) return String.format("%s days ago", daysPassed);
            else {
                int hoursPassed = (int) TimeUnit.MILLISECONDS.toHours(now.getTime() - date.getTime());
                if (hoursPassed >= 1) return String.format("%s hours ago", hoursPassed);
                else {
                    int minutesPassed = (int) TimeUnit.MILLISECONDS.toMinutes(now.getTime() - date.getTime());
                    if (minutesPassed > 0 && minutesPassed < 59) return String.format("%s minutes ago", minutesPassed);
                    else {
                        int secondsPassed = (int) TimeUnit.MILLISECONDS.toSeconds(now.getTime() - date.getTime());
                        return String.format("%s seconds ago", secondsPassed);
                    }
                }
            }
        } else {
            return new SimpleDateFormat("HH:mm:ss MM/dd/yyyy", Locale.US).format(date);
        }
    }

    public static String prettyDouble(int count) {
        return pretty(new Double(count));
    }

    private static String pretty(double number) {
        String r = new DecimalFormat("##0E0").format(number);
        r = r.replaceAll("E[0-9]", suffix[Character.getNumericValue(r.charAt(r.length() - 1)) / 3]);
        while (r.length() > MAX_LENGTH || r.matches("[0-9]+\\.[a-z]")) {
            r = r.substring(0, r.length() - 2) + r.substring(r.length() - 1);
        }
        return r;
    }
}
