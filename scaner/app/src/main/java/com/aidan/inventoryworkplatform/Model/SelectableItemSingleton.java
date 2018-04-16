package com.aidan.inventoryworkplatform.Model;

import com.aidan.inventoryworkplatform.Constants;
import com.aidan.inventoryworkplatform.Entity.SelectableItem.Agent;
import com.aidan.inventoryworkplatform.Entity.SelectableItem.SelectableItem;
import com.aidan.inventoryworkplatform.Utils.LocalCacheHelper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aidan on 2018/4/16.
 */

public abstract class SelectableItemSingleton<T extends SelectableItem> {
    private List<T> dataList = new ArrayList<>();

    public abstract String getTableName();
    public abstract Class<? extends T> getDataConstructor();

    public List<T> getDataList() {
        return dataList;
    }

    public void loadFromDB(){
        dataList.clear();
        JSONArray array = LocalCacheHelper.getInstance().getJSONArray(getTableName());
        for(int i = 0 ; i < array.length() ; i++){
            try {
                dataList.add(getDataConstructor().getConstructor(JSONObject.class)
                        .newInstance(array.getJSONObject(i)));
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    public void saveToDB() {
        JSONArray array = new JSONArray();
        for(T data : dataList){
            try {
                array.put(data.toJSON());
            }catch (Exception e){

            }
        }
        LocalCacheHelper.getInstance().setJSONArray(getTableName(), array);
    }

}
