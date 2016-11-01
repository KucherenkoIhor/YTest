package com.test.ytest.shared;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import static com.test.ytest.shared.Constants.DATE_PATTERN;
import static com.test.ytest.shared.Constants.IST_TIME_ZONE;
import static com.test.ytest.shared.Constants.LEADING_ZERO_TEMPLATE;

/**
 * Created by polyakov on 31.10.16.
 */

public class DateUtil {

    public static String formatDate(String originalDate) {
        try {
            DateFormat sdf = new SimpleDateFormat(DATE_PATTERN, Locale.ENGLISH);
            TimeZone timeZone = TimeZone.getTimeZone(IST_TIME_ZONE);
            sdf.setTimeZone(timeZone);
            Date date = sdf.parse(originalDate);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            String month = calendar.getDisplayName(
                    Calendar.MONTH,
                    Calendar.SHORT,
                    Locale.getDefault());
            int dayOFMonth = calendar.get(Calendar.DAY_OF_MONTH);
            int hours = calendar.get(Calendar.HOUR_OF_DAY);
            int minutes = calendar.get(Calendar.MINUTE);

            return new StringBuilder()
                    .append(String.valueOf(dayOFMonth))
                    .append(" ")
                    .append(month)
                    .append(" ")
                    .append(String.format(Locale.getDefault(), LEADING_ZERO_TEMPLATE, hours))
                    .append(":")
                    .append(String.format(Locale.getDefault(), LEADING_ZERO_TEMPLATE, minutes))
                    .toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return originalDate;
    }

}
