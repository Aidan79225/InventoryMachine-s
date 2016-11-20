package com.aidan.inventoryworkplatform.ItemListPage;

/**
 * Created by Aidan on 2016/11/20.
 */

public class ItemListPresenter implements ItemListContract.presenter {
    ItemListContract.view view;
    ItemListAdapter adapter;
    ItemListModel model;
    public ItemListPresenter(ItemListContract.view view){
        this.view = view;
        model = new ItemListModel();
    }

    @Override
    public void start() {
        view.findView();
        setAdapter();
    }
    private void setAdapter(){
        adapter = new ItemListAdapter(model.getItemList());
        view.setListView(adapter);
    }
}
