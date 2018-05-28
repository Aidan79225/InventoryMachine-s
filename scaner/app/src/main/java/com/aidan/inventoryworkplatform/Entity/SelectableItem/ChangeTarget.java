package com.aidan.inventoryworkplatform.Entity.SelectableItem;
import com.aidan.inventoryworkplatform.Entity.ItemConstants;

import org.json.JSONObject;

/**
 * Created by Aidan on 2018/5/29.
 */

public class ChangeTarget extends SelectableItem {
    public final static int TYPE_SCRAPPED = 0;
    public final static int TYPE_MOVE = 1;
    private int type = TYPE_SCRAPPED;
    public ChangeTarget(JSONObject jsonObject){
        setData(jsonObject);
        try {
            type = jsonObject.getInt(ItemConstants.TYPE);
        }catch (Exception e){

        }
    }

    public JSONObject toJSON(){
        JSONObject jsonObject = super.toJSON();
        try {
            jsonObject.put(ItemConstants.TYPE,type);
        }catch (Exception e){}
        return jsonObject;
    }


    public int getType() {
        return type;
    }
}
