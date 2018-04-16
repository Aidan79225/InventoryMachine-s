package com.aidan.inventoryworkplatform.Entity.SelectableItem;

import org.json.JSONObject;

/**
 * Created by Aidan on 2018/4/15.
 */

public class ApprovalNumber extends SelectableItem{
    public ApprovalNumber(){
        super();
    }

    public ApprovalNumber(JSONObject jsonObject){
        super(jsonObject);
    }

    public ApprovalNumber(String name){
        super(name);
    }
}
