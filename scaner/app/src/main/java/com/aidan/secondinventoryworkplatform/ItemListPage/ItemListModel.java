package com.aidan.secondinventoryworkplatform.ItemListPage;

import com.aidan.secondinventoryworkplatform.Entity.Item;

import java.util.List;

/**
 * Created by Aidan on 2016/11/20.
 */

public class ItemListModel {
    List<Item> itemList;
    public ItemListModel(List<Item> itemList){
        this.itemList = itemList;
    }
    public List<Item> getItemList(){
        return itemList;
    }
}
