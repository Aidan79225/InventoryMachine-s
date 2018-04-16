package com.aidan.inventoryworkplatform.Entity.SelectableItem;

import org.json.JSONObject;

/**
 * Created by Aidan on 2016/10/25.
 */

public class Agent extends SelectableItem {
    public Agent(){
        super();
    }

    public Agent(JSONObject jsonObject){
        super(jsonObject);
    }

    public Agent(String name){
        super(name);
    }

}
