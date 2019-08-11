package com.aidan.secondinventoryworkplatform.Model.SelecetableSingleton;

import com.aidan.secondinventoryworkplatform.Constants;
import com.aidan.secondinventoryworkplatform.Entity.SelectableItem.ChangeTarget;

/**
 * Created by Aidan on 2018/5/29.
 */

public class ChangeTargetSingleton extends SelectableItemSingleton<ChangeTarget> {
    private static ChangeTargetSingleton singleton;
    public static ChangeTargetSingleton getInstance(){
        if(singleton == null){
            synchronized (ChangeTargetSingleton.class){
                if(singleton == null){
                    singleton = new ChangeTargetSingleton();
                }
            }
        }
        return singleton;
    }
    @Override
    public String getTableName() {
        return Constants.PREFERENCE_CHANGE_TARGET;
    }

    @Override
    public Class<? extends ChangeTarget> getDataConstructor() {
        return ChangeTarget.class;
    }

    public void loadFromDB(){
        dataList.clear();
        dataList.addAll(ChangeTarget.getBasicList());
    }
}
