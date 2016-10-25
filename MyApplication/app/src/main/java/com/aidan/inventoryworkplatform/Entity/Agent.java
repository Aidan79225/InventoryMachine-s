package com.aidan.inventoryworkplatform.Entity;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Aidan on 2016/10/25.
 */

public class Agent {
    public String number ="";
    public String name = "";
    public Agent(JSONObject jsonObject){
        try{
            number = jsonObject.getString(AgentConstants.D1KY);
            name = jsonObject.getString(AgentConstants.D1NM);
        }catch (JSONException e){
            e.printStackTrace();
        }
    }
    public Agent(String name , String number){
        this.name = name;
        this.number = number;
    }
}
