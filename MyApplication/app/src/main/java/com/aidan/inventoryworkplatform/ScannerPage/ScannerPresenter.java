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
    private List<Item> itemList = new ArrayList<>();

    public ScannerPresenter(ScannerContract.view view) {
        this.view = view;
    }

    @Override
    public void start() {
        view.findView();
        view.setEditTextScan();
        view.setListView(itemList);
    }

    @Override
    public void scan(String key) {
        Singleton.log(key);
        String[] temps = key.split("-");
        Singleton.log(temps.length+"");
        if (temps.length < 3) return;
        String temp = temps[1] + "-" + temps[2].substring(2);
        Singleton.log(temp);
        for (Item item : itemList) {
            if (item.getIdNumber().equals(temp)) {
                return;
            }
        }
        for (Item item : ItemSingleton.getInstance().getItemList()) {
            if (item.getIdNumber().equals(temp)) {
                item.setConfirm(true);
                itemList.add(item);
                view.refreshList();
                break;
            }
        }
    }
}
