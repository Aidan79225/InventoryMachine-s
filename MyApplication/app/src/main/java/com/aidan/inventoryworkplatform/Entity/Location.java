package com.aidan.inventoryworkplatform.Entity;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Aidan on 2016/10/25.
 */

public class Location {
    public String number ="";
    public String name = "";
    public Location(JSONObject jsonObject){
        try{
            number = jsonObject.getString(DepartmentConstants.D2KY);
            name = jsonObject.getString(DepartmentConstants.D2NM);
        }catch (JSONException e){
            e.printStackTrace();
        }
    }
    public Location(String name , String number){
        this.name = name;
        this.number = number;
    }
}
