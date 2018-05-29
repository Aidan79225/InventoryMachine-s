package com.aidan.secondinventoryworkplatform.Model.SelecetableSingleton;

import com.aidan.secondinventoryworkplatform.Constants;
import com.aidan.secondinventoryworkplatform.Entity.SelectableItem.SummonsNumber;

/**
 * Created by Aidan on 2018/4/17.
 */

public class SummonsNumberSingleton extends SelectableItemSingleton<SummonsNumber> {
    static SummonsNumberSingleton singleton;
    public static SummonsNumberSingleton getInstance(){
        if(singleton == null){
            synchronized (SummonsNumberSingleton.class){
                if(singleton == null){
                    singleton = new SummonsNumberSingleton();
                }
            }
        }
        return singleton;
    }
    @Override
    public String getTableName() {
        return Constants.PREFERENCE_SUMMONS_NUMBER;
    }

    @Override
    public Class<? extends SummonsNumber> getDataConstructor() {
        return SummonsNumber.class;
    }
}
