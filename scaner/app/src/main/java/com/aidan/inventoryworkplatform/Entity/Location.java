package com.aidan.inventoryworkplatform.Entity;

import com.aidan.inventoryworkplatform.Dialog.SearchableItem;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Aidan on 2016/10/25.
 */

public class Location implements Comparable<Location>, SearchableItem {
    private long id = 0;
    public String number = "";
    public String name = "";

    public Location() {

    }

    public Location(JSONObject jsonObject) {
        try {
            number = jsonObject.getString(LocationConstants.D3KY);
            name = jsonObject.getString(LocationConstants.D3NM);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void setData(String data) {
        try {
            JSONObject jsonObject = new JSONObject(data);
            setData(jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void setData(JSONObject jsonObject) {
        try {
            number = jsonObject.getString(LocationConstants.D3KY);
            name = jsonObject.getString(LocationConstants.D3NM);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public JSONObject toJSON() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(LocationConstants.D3KY, number);
            jsonObject.put(LocationConstants.D3NM, name);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public Location(String name, String number) {
        this.name = name;
        this.number = number;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public int compareTo(Location o) {
        return name.compareTo(o.name);
    }

    @Override
    public String getName() {
        return name;
    }
}