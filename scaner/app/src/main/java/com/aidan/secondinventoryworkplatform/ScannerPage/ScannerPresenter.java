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
        String[] temps = key.split("-");
        if (temps.length < 3) return;
        if(temps[0].length()<7){
            firstTypeScan(key,temps);
        } else{
            secondTypeScan(key,temps);
        }
    }
    public void firstTypeScan(String key, String[] temps){
        temps[2] = temps[2].substring(2);
        int serialNumber = Integer.valueOf(temps[2]);
        for (Item item : itemList) {
            if (item.getNumber().equals(temps[1]) && serialNumber == Integer.valueOf(item.getSerialNumber().substring(2))) {
                view.showToast("已重複盤點 : " + key);
                return;
            }
        }
        for (Item item : ItemSingleton.getInstance().getItemList()) {
            if (item.getNumber().equals(temps[1]) && serialNumber == Integer.valueOf(item.getSerialNumber().substring(2))) {
                item.setConfirm(true);
                itemList.add(0, item);
                ItemSingleton.getInstance().saveItem(item);
                view.refreshList();
                return;
            }
        }
        view.showToast("找不到對應編號 : " + key);
    }

    public void secondTypeScan(String key, String[] temps){
        temps[1] = temps[0] + temps[1];
        int serialNumber = Integer.valueOf(temps[2]);
        for (Item item : itemList) {
            if (item.getNumber().equals(temps[1]) && serialNumber == Integer.valueOf(item.getSerialNumber().substring(2))) {
                view.showToast("已重複盤點 : " + key);
                return;
            }
        }
        for (Item item : ItemSingleton.getInstance().getItemList()) {
            if (item.getNumber().equals(temps[1]) && serialNumber == Integer.valueOf(item.getSerialNumber().substring(2))) {
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
