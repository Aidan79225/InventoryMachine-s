package com.aidan.secondinventoryworkplatform.Model;

import com.aidan.secondinventoryworkplatform.Database.DBHelper;
import com.aidan.secondinventoryworkplatform.Database.ItemDAO;
import com.aidan.secondinventoryworkplatform.Entity.Item;

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
    public void saveToDB() {
        try {
            DBHelper.getDatabase().beginTransaction();
            for (Item item : itemList) {
                saveItem(item);
            }
            DBHelper.getDatabase().setTransactionSuccessful();
            DBHelper.getDatabase().endTransaction();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void saveItem(Item item){
        if (!ItemDAO.getInstance().update(item))
            ItemDAO.getInstance().insert(item);
    }
    public void loadFromDB(){
        itemList = ItemDAO.getInstance().getAll();
    }
}
