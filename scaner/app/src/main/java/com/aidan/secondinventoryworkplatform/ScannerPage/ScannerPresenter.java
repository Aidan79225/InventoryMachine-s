package com.aidan.secondinventoryworkplatform.ScannerPage;

import com.aidan.secondinventoryworkplatform.Entity.Item;
import com.aidan.secondinventoryworkplatform.Model.ItemSingleton;
import com.aidan.secondinventoryworkplatform.Singleton;

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
        if(key == null )return;
        key = key.replace("\n","");
        if(key.length() == 0 )return;
        Singleton.log(key);
        startScan(key);
    }
    public void startScan(String key){
        for (Item item : itemList) {
            if ((item.getNumber() + item.getSerialNumber()).equals(key)) {
                view.showToast("已重複盤點 : " + key);
                return;
            }
        }
        for (Item item : ItemSingleton.getInstance().getItemList()) {
            if ((item.getNumber() + item.getSerialNumber()).equals(key)) {
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
