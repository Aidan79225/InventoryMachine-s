package com.aidan.inventoryworkplatform.ItemDetailPage;

import com.aidan.inventoryworkplatform.Entity.Item;

/**
 * Created by s352431 on 2016/11/22.
 */
public interface ItemDetailContract {
    interface view{
        void findView();
        void setViewValue(Item item);
    }
    interface presenter{
        void start();
    }
}
