package com.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/***
 * Class contains methods for getting current data and time
 */
public class CurrentDateTime {

    public static String currentDate(){
        String FORMAT_DATE = "dd-MM-yyyy HH:mm:ss";
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat(FORMAT_DATE);
        return formatter.format(date);
    }
}