package com.aidan.secondinventoryworkplatform.Entity.SelectableItem;

import org.json.JSONObject;

/**
 * Created by Aidan on 2016/10/25.
 */

public class Location extends SelectableItem {
    public Location(){
        super();
    }

    public Location(JSONObject jsonObject){
        super(jsonObject);
    }

    public Location(String name){
        super(name);
    }
}
