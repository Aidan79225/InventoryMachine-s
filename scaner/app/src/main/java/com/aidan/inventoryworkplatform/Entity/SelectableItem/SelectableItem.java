package com.aidan.inventoryworkplatform.Entity.SelectableItem;

import com.aidan.inventoryworkplatform.Dialog.SearchableItem;
import com.aidan.inventoryworkplatform.Entity.ItemConstants;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Aidan on 2017/11/19.
 */

public class SelectableItem implements Comparable<SelectableItem>,SearchableItem {
    public long id = 0;
    public String name = "";

    public SelectableItem(){

    }

    public SelectableItem(JSONObject jsonObject){
        setData(jsonObject);
    }

    public SelectableItem(String name){
        this.name = name;
    }
    @Override
    public int compareTo(SelectableItem o) {
        return name.compareTo(o.name) ;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setData(String data){
        try{
            JSONObject jsonObject = new JSONObject(data);
            setData(jsonObject);
        }catch (JSONException e){
            e.printStackTrace();
        }
    }
    public void setData(JSONObject jsonObject){
        try{
            name = jsonObject.getString(ItemConstants.ITEM);
        }catch (JSONException e){
            e.printStackTrace();
        }
    }

    public JSONObject toJSON(){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(ItemConstants.ITEM,name);
        }catch (JSONException e){
            e.printStackTrace();
        }
        return jsonObject;
    }
}
