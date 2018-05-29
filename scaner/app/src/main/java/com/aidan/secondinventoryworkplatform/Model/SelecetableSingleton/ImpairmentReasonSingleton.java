package com.aidan.secondinventoryworkplatform.Model.SelecetableSingleton;

import com.aidan.secondinventoryworkplatform.Constants;
import com.aidan.secondinventoryworkplatform.Entity.SelectableItem.ImpairmentReason;

/**
 * Created by Aidan on 2018/4/17.
 */

public class ImpairmentReasonSingleton extends SelectableItemSingleton<ImpairmentReason>{
    static ImpairmentReasonSingleton singleton;
    public static ImpairmentReasonSingleton getInstance(){
        if(singleton == null){
            synchronized (ImpairmentReasonSingleton.class){
                if(singleton == null){
                    singleton = new ImpairmentReasonSingleton();
                }
            }
        }
        return singleton;
    }
    @Override
    public String getTableName() {
        return Constants.PREFERENCE_IMPAIRMENT_REASON;
    }

    @Override
    public Class<? extends ImpairmentReason> getDataConstructor() {
        return ImpairmentReason.class;
    }
}
