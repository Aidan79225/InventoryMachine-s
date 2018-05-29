package com.aidan.secondinventoryworkplatform.Entity.SelectableItem;

import org.json.JSONObject;

/**
 * Created by Aidan on 2018/4/15.
 */

public class SummonsTitle extends SelectableItem{
    public SummonsTitle(){
        super();
    }

    public SummonsTitle(JSONObject jsonObject){
        super(jsonObject);
    }

    public SummonsTitle(String name){
        super(name);
    }
}
