package com.aidan.inventoryworkplatform.ChangeDetailPage;

import com.aidan.inventoryworkplatform.Entity.Item;

/**
 * Created by Aidan on 2018/4/18.
 */

public class ChangeDetailPresenter implements ChangeDetailContract.presenter{
    private ChangeDetailContract.view view;
    private ChangeDetailModel model;

    public ChangeDetailPresenter(ChangeDetailContract.view view, Item item){
        this.model = new ChangeDetailModel(item);
        this.view = view;
    }

    @Override
    public void start() {
        view.findView();
    }
}
