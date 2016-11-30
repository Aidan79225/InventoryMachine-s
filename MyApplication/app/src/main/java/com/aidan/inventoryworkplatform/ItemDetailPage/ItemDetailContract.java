package com.aidan.inventoryworkplatform.ItemDetailPage;

import com.aidan.inventoryworkplatform.Entity.Item;

/**
 * Created by s352431 on 2016/11/22.
 */
public interface ItemDetailContract {
    interface view{
        void findView();
        void setViewValue(Item item);
        void setViewClick();
    }
    interface presenter{
        void start();
        void saveItemToChecked(boolean flag);
    }
}
