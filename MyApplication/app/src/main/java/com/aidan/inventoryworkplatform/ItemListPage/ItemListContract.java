package com.aidan.inventoryworkplatform.ItemListPage;

import android.widget.BaseAdapter;

/**
 * Created by Aidan on 2016/11/20.
 */

public interface ItemListContract {
    interface view{
        void findView();
        void setListView(ItemListAdapter adapter);
    }
    interface presenter{
        void start();
    }
}
