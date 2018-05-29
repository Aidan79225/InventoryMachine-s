package com.aidan.secondinventoryworkplatform;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;


/**
 * Created by s352431 on 2016/10/3.
 */
public class Singleton {
    public static final boolean logSend = BuildConfig.IS_SANDBOX;
    public static SharedPreferences preferences = null;
    public static SharedPreferences.Editor preferenceEditor = null;

    public static void log(String log) {
        if (logSend) {
            StackTraceElement ste = getCallerCallerClassName();
            if (ste != null)
                Log.e(ste.getClassName() + " ", "at line:" + ste.getLineNumber() + " " + log);
            else
                Log.e("", "at line:" + ste.getLineNumber() + " " + log);
        }
    }

    public static StackTraceElement getCallerCallerClassName() {
        StackTraceElement[] stElements = Thread.currentThread().getStackTrace();
        for (int i = 1; i < stElements.length; i++) {
            StackTraceElement ste = stElements[i];
            if (!ste.getClassName().equals(Singleton.class.getName())
                    && ste.getClassName().indexOf("java.lang.Thread") != 0) {
                return ste;// ste.getClassName()+ste.getMethodName()+ste.getLineNumber();
            }
        }
        return null;
    }
    public static void  setPreference(Context applicationContext){
        preferences = PreferenceManager.getDefaultSharedPreferences(applicationContext);
        preferenceEditor = preferences.edit();
    }
}
