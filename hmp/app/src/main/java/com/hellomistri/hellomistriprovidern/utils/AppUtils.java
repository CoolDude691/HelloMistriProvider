package com.hellomistri.hellomistriprovidern.utils;

import android.app.ActivityManager;
import android.content.Context;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class AppUtils {

    public static String addDateFormat(String newFormat, Date inputDate) {
        SimpleDateFormat formatter = new SimpleDateFormat(newFormat);
        String format = formatter.format(inputDate);
        return format;
    }

    public static String changeDateFormat(String oldFormat, String newFormat, String inputDate) {
        String format = "";
        try {
            SimpleDateFormat formatter = new SimpleDateFormat(oldFormat);
            Date dateObj = formatter.parse(inputDate);

            SimpleDateFormat formatter2 = new SimpleDateFormat(newFormat);
            format = formatter2.format(dateObj);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return format;
    }
    public static boolean isAppRunning(final Context context, final String packageName) {
        final ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        final List<ActivityManager.RunningAppProcessInfo> procInfos = activityManager.getRunningAppProcesses();
        if (procInfos != null)
        {
            for (final ActivityManager.RunningAppProcessInfo processInfo : procInfos) {
                if (processInfo.processName.equals(packageName)) {
                    return true;
                }
            }
        }
        return false;
    }

}
