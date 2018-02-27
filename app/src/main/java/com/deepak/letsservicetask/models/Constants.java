package com.deepak.letsservicetask.models;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by Deepak on 27-Feb-18.
 */

public class Constants {

    public static String GetToday(){
        Date presentTime = Calendar.getInstance().getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
        return dateFormat.format(presentTime);
    }
}
