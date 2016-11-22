package com.aidan.inventoryworkplatform.ItemDetailPage;

import com.aidan.inventoryworkplatform.Entity.Item;

/**
 * Created by s352431 on 2016/11/22.
 */
public class ItemDetailPresenter implements ItemDetailContract.presenter {
    ItemDetailContract.view view;
    ItemDetailModel model;
    public ItemDetailPresenter(ItemDetailContract.view view,Item item){
        this.view = view;
        model = new ItemDetailModel(item);
    }

    @Override
    public void start() {
        view.findView();
        view.setViewValue(model.getItem());
    }
}
