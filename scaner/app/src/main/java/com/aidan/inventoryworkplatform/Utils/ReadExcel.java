package com.aidan.inventoryworkplatform.Utils;

import com.aidan.inventoryworkplatform.Entity.Item;
import com.aidan.inventoryworkplatform.Model.ItemSingleton;

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
    public static void read(String inputFile) {
        File inputWorkbook = new File(inputFile);
        Workbook w;
        try {
            w = Workbook.getWorkbook(inputWorkbook);
            loadAndSetName(w);
        } catch (BiffException e) {
            e.printStackTrace();
        } catch (IOException iOException) {
            iOException.printStackTrace();
        }
    }

    public static void loadAndSetName(Workbook w) {
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
        }
        ItemSingleton.getInstance().saveToDB();
    }
}
