package com.aidan.secondinventoryworkplatform.Entity.SelectableItem;
import com.aidan.secondinventoryworkplatform.Entity.ItemConstants;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aidan on 2018/5/29.
 */

public class ChangeTarget extends SelectableItem {
    public final static int TYPE_SCRAPPED = 0;
    public final static int TYPE_MOVE = 1;
    private int type = TYPE_SCRAPPED;
    public static ChangeTarget getScrappedChangeTarget(){
        ChangeTarget scrapped = new ChangeTarget();
        scrapped.type = TYPE_SCRAPPED;
        scrapped.name = "報廢";
        return scrapped;
    }
    public static List<ChangeTarget> getBasicList(){
        List<ChangeTarget> ans = new ArrayList<>();
        ChangeTarget scrapped = new ChangeTarget();
        scrapped.type = TYPE_SCRAPPED;
        scrapped.name = "報廢";
        ans.add(scrapped);
        ChangeTarget move = new ChangeTarget();
        move.type = TYPE_MOVE;
        move.name = "移動";
        ans.add(move);
        return ans;
    }
    public ChangeTarget(){

    }
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
    public void setName(String name) {
        this.name = name;
    }
    public void setType(int type) {
        this.type = type;
    }
}
