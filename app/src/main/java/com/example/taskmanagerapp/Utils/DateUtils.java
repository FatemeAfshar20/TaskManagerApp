package com.example.taskmanagerapp.Utils;

import java.text.DateFormat;
import java.util.Date;

public class DateUtils {
    private static DateFormat sDATEFORMAT =
            DateFormat.getDateInstance(DateFormat.SHORT);
    private static DateFormat sTIMEFORMAT=
            DateFormat.getTimeInstance(DateFormat.SHORT);

    public static String getShortDateFormat(Date date){
        return sDATEFORMAT.format(date);
    }

    public static String getShortTimeFormat(Date date){
        return sTIMEFORMAT.format(date);
    }
}
