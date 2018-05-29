package com.aidan.secondinventoryworkplatform.Entity.SelectableItem;

import org.json.JSONObject;

/**
 * Created by Aidan on 2018/4/15.
 */

public class ImpairmentReason extends SelectableItem{
    public ImpairmentReason(){
        super();
    }

    public ImpairmentReason(JSONObject jsonObject){
        super(jsonObject);
    }

    public ImpairmentReason(String name){
        super(name);
    }
}

