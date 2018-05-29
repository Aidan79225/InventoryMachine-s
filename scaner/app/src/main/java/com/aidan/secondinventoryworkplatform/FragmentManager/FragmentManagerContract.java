package com.aidan.secondinventoryworkplatform.FragmentManager;

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
        void showProgress(String msg);
        void hideProgress();
        void updateProgress(int value);
    }
    interface presenter{
        void start();
    }
}
