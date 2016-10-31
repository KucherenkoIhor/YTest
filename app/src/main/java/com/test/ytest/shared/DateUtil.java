package com.test.ytest.shared;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by polyakov on 31.10.16.
 */

public class DateUtil {

    public static final String DATE_PATTERN = "MMM dd, yyyy, hh.mma Z";

    public static String formatDate(String originalDate) {
        try {
            DateFormat sdf = new SimpleDateFormat(DATE_PATTERN, Locale.US);
            Date date = sdf.parse(originalDate);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);

            String month = calendar.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.getDefault());
            int dayOFMonth = calendar.get(Calendar.DAY_OF_MONTH);
            int hours = calendar.get(Calendar.HOUR_OF_DAY);
            int minutes = calendar.get(Calendar.MINUTE);

            return String.valueOf(dayOFMonth) +
                    " " +
                    month +
                    " " +
                    hours +
                    ":" +
                    minutes;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return originalDate;
    }

}
