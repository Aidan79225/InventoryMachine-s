package com.aidan.secondinventoryworkplatform.Entity;

import com.aidan.secondinventoryworkplatform.dialog.SearchableItem;

/**
 * Created by Aidan on 2017/10/28.
 */

public enum TagContent implements SearchableItem {
    Agent("保管人"),
    AgentGroup("保管人/單位"),
    AgentLocation("保管人/地點"),
    AgentGroupLocation("保管人/單位/地點");
    String content;
    TagContent(String content){
        this.content = content;
    }

    @Override
    public String getName() {
        return content;
    }
}
