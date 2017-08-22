package com.aidan.inventoryworkplatform.Entity;

import com.aidan.inventoryworkplatform.Dialog.SearchableItem;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Aidan on 2016/10/25.
 */

public class Department implements Comparable<Department>,SearchableItem {
    private long id = 0;
    public String number ="";
    public String name = "";
    public Department(){

    }
    public void setData(String data){
        try{
            JSONObject jsonObject = new JSONObject(data);
            number = jsonObject.getString(DepartmentConstants.D2KY);
            name = jsonObject.getString(DepartmentConstants.D2NM);
        }catch (JSONException e){
            e.printStackTrace();
        }
    }
    public Department(JSONObject jsonObject){
        try{
            number = jsonObject.getString(DepartmentConstants.D2KY);
            name = jsonObject.getString(DepartmentConstants.D2NM);
        }catch (JSONException e){
            e.printStackTrace();
        }
    }
    public Department(String name , String number){
        this.name = name;
        this.number = number;
    }
    public JSONObject toJSON(){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(DepartmentConstants.D2KY,number);
            jsonObject.put(DepartmentConstants.D2NM,name);
        }catch (JSONException e){
            e.printStackTrace();
        }
        return jsonObject;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
    @Override
    public int compareTo(Department o) {
        return name.compareTo(o.name);
    }
    @Override
    public String getName() {
        return name;
    }
}
