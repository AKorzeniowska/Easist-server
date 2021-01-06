package com.edu.agh.easist.easistserver.resource.utils;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Locale;

public class DateTimeParser {
    private static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    private static final SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm:ss a", Locale.US);


    public static String parseDateToString(LocalDate date){
        return dateFormat.format(date);
    }

    public static LocalDate parseStringToDate(String string){
        return LocalDate.parse(string);
    }

    public static Time parseStringToTime(String string){
        try {
            return new Time(timeFormat.parse(string).getTime());
        } catch (ParseException e) {
            return null;
        }
    }
}
