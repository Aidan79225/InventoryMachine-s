package com.aidan.inventoryworkplatform.Entity.SelectableItem;

import org.json.JSONObject;

/**
 * Created by Aidan on 2016/10/25.
 */

public class Department extends SelectableItem{
    public Department(){
        super();
    }

    public Department(JSONObject jsonObject){
        super(jsonObject);
    }

    public Department(String name){
        super(name);
    }
}
