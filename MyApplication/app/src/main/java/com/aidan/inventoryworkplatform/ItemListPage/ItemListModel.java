package com.aidan.inventoryworkplatform.ItemListPage;

import com.aidan.inventoryworkplatform.Entity.Item;
import com.aidan.inventoryworkplatform.Model.ItemSingleton;

import java.util.List;

/**
 * Created by Aidan on 2016/11/20.
 */

public class ItemListModel {
    List<Item> itemList;
    public ItemListModel(){
        itemList = ItemSingleton.getInstance().getItemList();
    }
    public List<Item> getItemList(){
        return itemList;
    }
}
