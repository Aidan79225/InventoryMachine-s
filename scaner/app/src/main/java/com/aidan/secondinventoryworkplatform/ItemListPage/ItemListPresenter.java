package com.aidan.secondinventoryworkplatform.ItemListPage;

import com.aidan.secondinventoryworkplatform.Entity.Item;
import com.aidan.secondinventoryworkplatform.Model.ItemSingleton;
import com.aidan.secondinventoryworkplatform.SettingConstants;
import com.aidan.secondinventoryworkplatform.Singleton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Aidan on 2016/11/20.
 */

public class ItemListPresenter implements ItemListContract.presenter {
    ItemListContract.view view;
    ItemListModel model;
    List<Item> scaned = new ArrayList<>();

    public ItemListPresenter(ItemListContract.view view,List<Item> itemList){
        this.view = view;
        model = new ItemListModel(itemList);
        Collections.sort(itemList, new Comparator<Item>() {
            @Override
            public int compare(Item o1, Item o2) {
                return (o1.getNumber()+o1.getSerialNumber()).compareTo(o2.getNumber()+o2.getSerialNumber());
            }
        });
    }

    @Override
    public void start() {
        view.findView();
        view.setEditTextScan();
        setAdapter();
    }
    private void setAdapter(){
        view.setListView(model.getItemList());
    }

    @Override
    public void scan(String key) {
        if(key == null )return;
        key = key.replace("\n","").trim();
        if(key.isEmpty())return;
        Singleton.log(key);
        firstTypeScan(key);
    }

    private void firstTypeScan(String key){
        for (Item item : scaned) {
            if (key.equals(item.getBarcodeNumber())) {
                view.showToast("已重複盤點 : " + key);
                return;
            }
        }
        for (Item item : ItemSingleton.getInstance().getItemList()) {
            if (key.equals(item.getBarcodeNumber())) {
                item.setConfirm(true);
                if (Singleton.preferences.getBoolean(SettingConstants.PRINT_IN_SCANNER, false)) {
                    item.setPrint(true);
                }
                if (Singleton.preferences.getBoolean(SettingConstants.DELETE_IN_SCANNER, false)) {
                    item.setDelete(true);
                }
                scaned.add(0, item);
                ItemSingleton.getInstance().saveItem(item);
                view.refreshList();
                if (Singleton.preferences.getBoolean(SettingConstants.SHOW_AFTER_SCAN, false)) {
                    view.showItem(item);
                }
                return;
            }
        }
        view.showToast("找不到對應編號 : " + key);
    }
}
