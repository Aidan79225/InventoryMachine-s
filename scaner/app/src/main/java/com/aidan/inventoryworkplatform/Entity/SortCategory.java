package com.aidan.inventoryworkplatform.Entity;

import com.aidan.inventoryworkplatform.Dialog.SearchableItem;

/**
 * Created by Aidan on 2017/10/28.
 */

public enum SortCategory implements SearchableItem {
    Agent("保管人"),
    Group("保管單位"),
    Location("存置地點");
    String content;
    SortCategory(String content){
        this.content = content;
    }

    @Override
    public String getName() {
        return content;
    }
}
