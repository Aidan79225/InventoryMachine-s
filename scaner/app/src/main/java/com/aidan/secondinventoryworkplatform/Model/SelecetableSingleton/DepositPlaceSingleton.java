package com.aidan.secondinventoryworkplatform.Model.SelecetableSingleton;

import com.aidan.secondinventoryworkplatform.Constants;
import com.aidan.secondinventoryworkplatform.Entity.SelectableItem.DepositPlace;

/**
 * Created by Aidan on 2018/4/17.
 */

public class DepositPlaceSingleton extends SelectableItemSingleton<DepositPlace> {
    private static DepositPlaceSingleton singleton;
    public static DepositPlaceSingleton getInstance(){
        if(singleton == null){
            synchronized (DepositPlaceSingleton.class){
                if(singleton == null){
                    singleton = new DepositPlaceSingleton();
                }
            }
        }
        return singleton;
    }

    @Override
    public String getTableName() {
        return Constants.PREFERENCE_DEPOSIT_PLACE;
    }

    @Override
    public Class<? extends DepositPlace> getDataConstructor() {
        return DepositPlace.class;
    }
}
