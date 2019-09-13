package com.aidan.secondinventoryworkplatform.ItemListPage;

import com.aidan.secondinventoryworkplatform.Entity.Item;

import java.util.List;

/**
 * Created by Aidan on 2016/11/20.
 */

public interface ItemListContract {
    interface view{
        void findView();
        void setListView(List<Item> itemList);
        void setEditTextScan();
        void showToast(String msg);
        void refreshList();
        void showItem(Item item);
    }
    interface presenter{
        void start();
        void scan(String scan);
    }
}
