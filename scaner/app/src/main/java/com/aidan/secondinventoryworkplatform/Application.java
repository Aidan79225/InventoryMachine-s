package com.aidan.secondinventoryworkplatform;

import android.content.Context;

import com.aidan.secondinventoryworkplatform.Utils.LocalCacheHelper;

/**
 * Created by Aidan on 2018/4/16.
 */

public class Application extends android.app.Application {
    private static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        LocalCacheHelper.init(this);
    }
    public static Context getContext(){
        return context;
    }
}
