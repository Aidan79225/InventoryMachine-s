package com.aidan.inventoryworkplatform.ChangeDetailPage;

import com.aidan.inventoryworkplatform.Entity.Item;

/**
 * Created by Aidan on 2018/4/18.
 */

public class ChangeDetailModel {
    Item item;
    public ChangeDetailModel(Item item){
        this.item = item;
    }

    public Item getItem() {
        return item;
    }
}
