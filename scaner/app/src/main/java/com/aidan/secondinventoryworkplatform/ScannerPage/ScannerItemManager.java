package com.aidan.secondinventoryworkplatform.ScannerPage;

import com.aidan.secondinventoryworkplatform.Entity.Item;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aidan on 2017/9/5.
 */

public class ScannerItemManager {
    private static ScannerItemManager instance;
    private List<Item> itemList;
    private ScannerItemManager(){
        itemList = new ArrayList<>();
    }
    public static ScannerItemManager getInstance(){
        if(instance == null){
            synchronized (ScannerItemManager.class){
                if(instance == null){
                    instance = new ScannerItemManager();
                }
            }
        }
        return instance;
    }
    public List<Item> getItemList(){
        return itemList;
    }
}
