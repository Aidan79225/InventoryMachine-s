package com.aidan.secondinventoryworkplatform.Model.SelecetableSingleton;

import com.aidan.secondinventoryworkplatform.Constants;
import com.aidan.secondinventoryworkplatform.Entity.SelectableItem.SummonsTitle;

/**
 * Created by Aidan on 2018/4/17.
 */

public class SummonsTitleSingleton extends SelectableItemSingleton<SummonsTitle> {
    static SummonsTitleSingleton singleton;
    public static SummonsTitleSingleton getInstance(){
        if(singleton == null){
            synchronized (SelectableItemSingleton.class){
                if(singleton == null){
                    singleton = new SummonsTitleSingleton();
                }
            }
        }
        return singleton;
    }
    @Override
    public String getTableName() {
        return Constants.PREFERENCE_SUMMONS_TITLE;
    }

    @Override
    public Class<SummonsTitle> getDataConstructor() {
        return SummonsTitle.class;
    }
}
