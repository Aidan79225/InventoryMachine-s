package com.aidan.inventoryworkplatform.Model.SelecetableSingleton;

import com.aidan.inventoryworkplatform.Constants;
import com.aidan.inventoryworkplatform.Entity.SelectableItem.Location;

/**
 * Created by Aidan on 2016/11/20.
 */

public class LocationSingleton extends SelectableItemSingleton<Location> {
    static LocationSingleton locationSingleton;
    public static LocationSingleton getInstance(){
        if(locationSingleton == null){
            synchronized (LocationSingleton.class){
                if(locationSingleton == null){
                    locationSingleton = new LocationSingleton();
                }
            }
        }
        return locationSingleton;
    }

    @Override
    public String getTableName() {
        return Constants.PREFERENCE_LOCATION;
    }

    @Override
    public Class<? extends Location> getDataConstructor() {
        return Location.class;
    }
}
