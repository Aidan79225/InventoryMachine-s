package com.aidan.inventoryworkplatform.Entity;

import com.aidan.inventoryworkplatform.Dialog.SearchableItem;

/**
 * Created by Aidan on 2017/11/19.
 */

public abstract class SelectableItem implements Comparable<SelectableItem>,SearchableItem {
    public long id = 0;
    public String number ="";
    public String name = "";


    @Override
    public int compareTo(SelectableItem o) {
        return name.compareTo(o.name) ;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    @Override
    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }
}
