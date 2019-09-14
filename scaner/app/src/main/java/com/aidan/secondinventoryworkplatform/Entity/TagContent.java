package com.aidan.secondinventoryworkplatform.Entity;

import com.aidan.secondinventoryworkplatform.Constants;
import com.aidan.secondinventoryworkplatform.dialog.SearchableItem;

import java.util.concurrent.CompletionService;

/**
 * Created by Aidan on 2017/10/28.
 */

public enum TagContent implements SearchableItem {
    Agent(Constants.Custodian),
    AgentGroup(Constants.Custodian + "/" + Constants.CustodyGroup),
    AgentLocation(Constants.Custodian + "/" + Constants.Location),
    AgentGroupLocation(Constants.Custodian + "/" + Constants.CustodyGroup + "/" + Constants.Location);

    String content;
    TagContent(String content){
        this.content = content;
    }

    @Override
    public String getName() {
        return content;
    }
}
