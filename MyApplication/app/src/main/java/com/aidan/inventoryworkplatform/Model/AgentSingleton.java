package com.aidan.inventoryworkplatform.Model;

import com.aidan.inventoryworkplatform.Entity.Agent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aidan on 2016/11/20.
 */

public class AgentSingleton {
    static AgentSingleton agentSingleton;
    private List<Agent> agentList = new ArrayList<>();
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

    public List<Agent> getAgentList() {
        return agentList;
    }
}
