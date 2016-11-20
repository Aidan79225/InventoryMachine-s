package com.aidan.inventoryworkplatform.FragmentManager;

/**
 * Created by Aidan on 2016/10/25.
 */

public interface FragmentManagerContract {
    interface view{
        void findView();
        void setViewClick();
    }
    interface presenter{
        void start();
    }
}
