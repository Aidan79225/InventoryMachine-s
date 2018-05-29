package com.aidan.secondinventoryworkplatform.Entity.SelectableItem;

import org.json.JSONObject;

/**
 * Created by Aidan on 2018/4/15.
 */


public class DepositPlace extends SelectableItem{
    public DepositPlace(){
        super();
    }

    public DepositPlace(JSONObject jsonObject){
        super(jsonObject);
    }

    public DepositPlace(String name){
        super(name);
    }
}
