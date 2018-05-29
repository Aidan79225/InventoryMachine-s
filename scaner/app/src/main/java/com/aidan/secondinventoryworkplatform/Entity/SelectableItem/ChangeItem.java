package com.aidan.secondinventoryworkplatform.Entity.SelectableItem;

import org.json.JSONObject;

/**
 * Created by Aidan on 2018/4/15.
 */

public class ChangeItem extends SelectableItem {
    public ChangeItem(){
        super();
    }

    public ChangeItem(JSONObject jsonObject){
        super(jsonObject);
    }

    public ChangeItem(String name){
        super(name);
    }

}
