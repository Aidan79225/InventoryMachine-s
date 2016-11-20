package com.aidan.inventoryworkplatform.Model;

import com.aidan.inventoryworkplatform.Entity.Location;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aidan on 2016/11/20.
 */

public class LocationSingleton {
    static LocationSingleton locationSingleton;
    private List<Location> locationList = new ArrayList<>();
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

    public List<Location> getLocationList() {
        return locationList;
    }
}
