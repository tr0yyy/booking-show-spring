package com.fmi.bookingshow.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

public class DateUtils {
    private static final String dateRegex = "\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}";
    private static final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static Date parseDate(String date) throws ParseException {
        Pattern pattern = Pattern.compile(dateRegex);
        if (!pattern.matcher(date).matches()) {
            throw new IllegalArgumentException("Invalid date format");
        }
        return formatter.parse(date);
    }

    public static String formatDate(Date date) {
        return formatter.format(date);
    }
}
