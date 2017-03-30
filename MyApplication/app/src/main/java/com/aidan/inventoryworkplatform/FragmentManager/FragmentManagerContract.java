package com.aidan.inventoryworkplatform.FragmentManager;

/**
 * Created by Aidan on 2016/10/25.
 */

public interface FragmentManagerContract {
    interface view{
        void findView();
        void setViewClick();
        void setScanner();
        void loadScannerFragment();
        void loadItemListFragment();
        void loadFileFragment();
        void loadSearchFragment();
    }
    interface presenter{
        void start();
    }
}
