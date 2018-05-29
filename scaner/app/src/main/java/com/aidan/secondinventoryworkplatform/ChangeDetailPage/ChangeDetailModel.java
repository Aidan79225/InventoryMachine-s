package com.aidan.secondinventoryworkplatform.ChangeDetailPage;

import com.aidan.secondinventoryworkplatform.Entity.Item;
import com.aidan.secondinventoryworkplatform.Entity.SelectableItem.ChangeTarget;

/**
 * Created by Aidan on 2018/4/18.
 */

public class ChangeDetailModel {
    private Item item;
    private ChangeTarget changeTarget;
    public ChangeDetailModel(Item item){
        this.item = item;
    }

    public ChangeTarget getChangeTarget() {
        return changeTarget;
    }

    public void setChangeTarget(ChangeTarget changeTarget) {
        this.changeTarget = changeTarget;
    }

    public Item getItem() {
        return item;
    }
}
