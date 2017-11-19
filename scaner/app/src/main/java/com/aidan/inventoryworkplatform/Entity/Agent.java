package com.aidan.inventoryworkplatform.Entity;

import com.aidan.inventoryworkplatform.Dialog.SearchableItem;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Aidan on 2016/10/25.
 */

public class Agent extends SelectableItem {
    public Agent(){

    }

    public Agent(JSONObject jsonObject){
        setData(jsonObject);
    }

    public Agent(String name , String number){
        this.name = name;
        this.number = number;
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
            number = jsonObject.getString(AgentConstants.D1KY);
            name = jsonObject.getString(AgentConstants.D1NM);
        }catch (JSONException e){
            e.printStackTrace();
        }
    }
    public JSONObject toJSON(){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(AgentConstants.D1KY,number);
            jsonObject.put(AgentConstants.D1NM,name);
        }catch (JSONException e){
            e.printStackTrace();
        }
        return jsonObject;
    }
}
