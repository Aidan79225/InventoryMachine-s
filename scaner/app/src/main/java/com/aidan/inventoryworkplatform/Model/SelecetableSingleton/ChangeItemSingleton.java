package com.aidan.inventoryworkplatform.Model.SelecetableSingleton;

import com.aidan.inventoryworkplatform.Constants;
import com.aidan.inventoryworkplatform.Entity.SelectableItem.ChangeItem;

/**
 * Created by Aidan on 2018/4/17.
 */

public class ChangeItemSingleton extends SelectableItemSingleton<ChangeItem> {
    private static ChangeItemSingleton singleton;
    public static ChangeItemSingleton getInstance(){
        if(singleton == null){
            synchronized (ChangeItemSingleton.class){
                if(singleton == null){
                    singleton = new ChangeItemSingleton();
                }
            }
        }
        return singleton;
    }

    @Override
    public String getTableName() {
        return Constants.PREFERENCE_CHANGE_ITEM;
    }

    @Override
    public Class<? extends ChangeItem> getDataConstructor() {
        return ChangeItem.class;
    }
}
