package com.aidan.secondinventoryworkplatform.ItemListPage;

import com.aidan.secondinventoryworkplatform.Entity.Item;

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
