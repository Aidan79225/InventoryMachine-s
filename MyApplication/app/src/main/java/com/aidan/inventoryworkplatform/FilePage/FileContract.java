package com.aidan.inventoryworkplatform.FilePage;

/**
 * Created by Aidan on 2016/11/20.
 */

public interface FileContract {
    interface view{
        void findView();
        void setViewClick();
    }
    interface presenter{
        void start();
        void readTxtButtonClick(String path);
    }
}
