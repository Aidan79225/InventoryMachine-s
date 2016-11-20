package com.aidan.inventoryworkplatform.Model;

import com.aidan.inventoryworkplatform.Entity.Item;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aidan on 2016/11/20.
 */

public class ItemSingleton {
    static ItemSingleton itemSingleton;
    private List<Item> itemList = new ArrayList<>();
    public static ItemSingleton getInstance(){
        if(itemSingleton == null){
            synchronized (ItemSingleton.class){
                if(itemSingleton == null){
                    itemSingleton = new ItemSingleton();
                }
            }
        }
        return itemSingleton;
    }

    public List<Item> getItemList() {
        return itemList;
    }
}
