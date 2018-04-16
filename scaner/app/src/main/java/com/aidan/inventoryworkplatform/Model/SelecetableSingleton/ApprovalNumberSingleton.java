package com.aidan.inventoryworkplatform.Model.SelecetableSingleton;

import com.aidan.inventoryworkplatform.Constants;
import com.aidan.inventoryworkplatform.Entity.SelectableItem.ApprovalNumber;
import com.aidan.inventoryworkplatform.Entity.SelectableItem.DepositPlace;

/**
 * Created by Aidan on 2018/4/17.
 */

public class ApprovalNumberSingleton extends SelectableItemSingleton<ApprovalNumber> {
    private static ApprovalNumberSingleton singleton;
    public static ApprovalNumberSingleton getInstance(){
        if(singleton == null){
            synchronized (ApprovalNumberSingleton.class){
                if(singleton == null){
                    singleton = new ApprovalNumberSingleton();
                }
            }
        }
        return singleton;
    }

    @Override
    public String getTableName() {
        return Constants.PREFERENCE_APPROVAL_NUMBER;
    }

    @Override
    public Class<? extends ApprovalNumber> getDataConstructor() {
        return ApprovalNumber.class;
    }
}
