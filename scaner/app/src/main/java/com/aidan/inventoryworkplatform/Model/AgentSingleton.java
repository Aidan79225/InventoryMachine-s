package com.aidan.inventoryworkplatform.Model;

import com.aidan.inventoryworkplatform.Constants;
import com.aidan.inventoryworkplatform.Entity.SelectableItem.Agent;
import com.aidan.inventoryworkplatform.Utils.LocalCacheHelper;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aidan on 2016/11/20.
 */

public class AgentSingleton extends SelectableItemSingleton<Agent> {
    private static AgentSingleton agentSingleton;
    public static AgentSingleton getInstance(){
        if(agentSingleton == null){
            synchronized (AgentSingleton.class){
                if(agentSingleton == null){
                    agentSingleton = new AgentSingleton();
                }
            }
        }
        return agentSingleton;
    }


    @Override
    public String getTableName() {
        return Constants.PREFERENCE_AGENT;
    }

    @Override
    public Class<? extends Agent> getDataConstructor() {
        return Agent.class;
    }
}
