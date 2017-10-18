package com.aidan.inventoryworkplatform.ScannerPage;

import com.aidan.inventoryworkplatform.Entity.Item;
import com.aidan.inventoryworkplatform.Model.ItemSingleton;
import com.aidan.inventoryworkplatform.Singleton;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aidan on 2017/1/22.
 */

public class ScannerPresenter implements ScannerContract.presenter {
    private ScannerContract.view view;
    private List<Item> itemList;

    public ScannerPresenter(ScannerContract.view view) {
        this.view = view;
        this.itemList = ScannerItemManager.getInstance().getItemList();
    }

    @Override
    public void start() {
        view.findView();
        view.setEditTextScan();
        view.setListView(itemList);
        view.setViewClick();
    }

    @Override
    public void scan(String key) {
        Singleton.log(key);
        String[] temps = key.split("-");
        Singleton.log(temps.length + "");
        if (temps.length < 3) {
            view.showToast("找不到對應編號 : " + key);
            return;
        }
        String temp = temps[1] + "-" + temps[2].substring(2);
        Singleton.log(temp);
        for (Item item : itemList) {
            if (item.getIdNumber().equals(temp)) {
                view.showToast("已重複盤點 : " + key);
                return;
            }
        }
        for (Item item : ItemSingleton.getInstance().getItemList()) {
            if (item.getIdNumber().equals(temp)) {
                item.setConfirm(true);
                itemList.add(0, item);
                ItemSingleton.getInstance().saveItem(item);
                view.refreshList();
                return;
            }
        }
        view.showToast("找不到對應編號 : " + key);

    }
}
