package com.aidan.inventoryworkplatform.ItemListPage;

import com.aidan.inventoryworkplatform.Entity.Item;
import com.aidan.inventoryworkplatform.Model.ItemSingleton;

import java.util.List;

/**
 * Created by Aidan on 2016/11/20.
 */

public class ItemListPresenter implements ItemListContract.presenter {
    ItemListContract.view view;
    ItemListModel model;
    public ItemListPresenter(ItemListContract.view view,List<Item> itemList){
        this.view = view;
        model = new ItemListModel(itemList);
    }

    @Override
    public void start() {
        view.findView();
        setAdapter();
    }
    private void setAdapter(){
        view.setListView(model.getItemList());
    }
}
