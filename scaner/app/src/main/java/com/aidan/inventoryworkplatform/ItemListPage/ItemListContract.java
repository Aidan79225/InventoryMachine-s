package com.aidan.inventoryworkplatform.ItemListPage;

import android.widget.BaseAdapter;

import com.aidan.inventoryworkplatform.Entity.Item;

import java.util.List;

/**
 * Created by Aidan on 2016/11/20.
 */

public interface ItemListContract {
    interface view{
        void findView();
        void setListView(List<Item> itemList);
    }
    interface presenter{
        void start();
    }
}
