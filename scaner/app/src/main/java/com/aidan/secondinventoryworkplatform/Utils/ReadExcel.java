package com.aidan.secondinventoryworkplatform.Utils;

import com.aidan.secondinventoryworkplatform.Entity.Item;
import com.aidan.secondinventoryworkplatform.Model.ItemSingleton;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

/**
 * Created by Aidan on 2017/10/29.
 */

public class ReadExcel {
    private ProgressAction progressAction;

    public void read(final String inputFile) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if(progressAction != null){
                    progressAction.showProgress("讀取名稱中");
                }
                File inputWorkbook = new File(inputFile);
                Workbook w;
                try {
                    w = Workbook.getWorkbook(inputWorkbook);
                    loadAndSetName(w);
                } catch (BiffException e) {
                    progressAction.showToast("檔案格式錯誤");
                    e.printStackTrace();
                } catch (IOException iOException) {
                    progressAction.showToast("檔案格式錯誤");
                    iOException.printStackTrace();
                }finally {
                    if(progressAction != null){
                        progressAction.hideProgress();
                    }
                }
            }
        }).start();
    }

    public void loadAndSetName(Workbook w) {
        Sheet sheet = w.getSheet(0);
        List<Item> itemList = ItemSingleton.getInstance().getItemList();
        Map<String,List<Item>> itemMap = new HashMap<>();
        for(Item item : itemList){
            List<Item> list = itemMap.get(item.getNumber());
            if(list == null){
                list = new ArrayList<>();
            }
            list.add(item);
            itemMap.put(item.getNumber(),list);
        }
        for (int i = 0; i < sheet.getRows(); i++) {
            if (i == 0) continue;
            String id = "";
            id += sheet.getCell(0, i).getContents();
            id += sheet.getCell(1, i).getContents();
            id += sheet.getCell(2, i).getContents();
            id += sheet.getCell(3, i).getContents();
            id += sheet.getCell(4, i).getContents();
            String name = sheet.getCell(5, i).getContents();

            List<Item> list = itemMap.get(id);
            if(list != null){
                for(Item item : list){
                    item.setNAME(name);
                }
            }
            if(progressAction != null){
                progressAction.updateProgress((i+1)*100/sheet.getRows());
            }
        }
        ItemSingleton.getInstance().saveToDB();
    }

    public void setProgressAction(ProgressAction progressAction){
        this.progressAction = progressAction;
    }
    public interface ProgressAction{
        void showProgress(String msg);
        void hideProgress();
        void updateProgress(int value);
        void showToast(String msg);
    }
}
