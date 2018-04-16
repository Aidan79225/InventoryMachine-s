package com.aidan.inventoryworkplatform.Entity.SelectableItem;

import org.json.JSONObject;

/**
 * Created by Aidan on 2018/4/15.
 */

public class SummonsNumber extends SelectableItem{
    public SummonsNumber(){
        super();
    }

    public SummonsNumber(JSONObject jsonObject){
        super(jsonObject);
    }

    public SummonsNumber(String name){
        super(name);
    }
}
