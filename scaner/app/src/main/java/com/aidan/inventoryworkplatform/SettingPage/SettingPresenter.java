package com.aidan.inventoryworkplatform.SettingPage;

import android.widget.TextView;

import com.aidan.inventoryworkplatform.Dialog.SearchItemAdapter;
import com.aidan.inventoryworkplatform.Dialog.SearchableItem;
import com.aidan.inventoryworkplatform.Entity.Agent;
import com.aidan.inventoryworkplatform.Entity.Department;
import com.aidan.inventoryworkplatform.Entity.Item;
import com.aidan.inventoryworkplatform.Entity.Location;
import com.aidan.inventoryworkplatform.Model.AgentSingleton;
import com.aidan.inventoryworkplatform.Model.DepartmentSingleton;
import com.aidan.inventoryworkplatform.Model.ItemSingleton;
import com.aidan.inventoryworkplatform.Model.LocationSingleton;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aidan on 2017/1/8.
 */

public class SettingPresenter implements SettingContract.presenter{
    SettingContract.view view;
    String[] locationStrings ={};
    String[] agentStrings ={};
    String[] departmentStrings ={};
    Location location;
    Agent agent;
    Department department;
    Agent user;
    Department useGroup;
    List<Item> itemList;
    SettingPresenter(SettingContract.view view,List<Item> itemList){
        this.view = view;
        this.itemList = itemList;
    }

    @Override
    public void start() {
        init();
        view.findView();
        view.setViewClick();
    }
    private void init(){
        List<Location> locationList = LocationSingleton.getInstance().getLocationList();
        List<String> locationStringList = new ArrayList<>();
        for(Location location : locationList ){
            locationStringList.add(location.name);
        }
        locationStrings = locationStringList.toArray(locationStrings);

        List<Agent> agentList = AgentSingleton.getInstance().getAgentList();
        List<String> agentStringList = new ArrayList<>();
        for(Agent agent : agentList ){
            agentStringList.add(agent.name);
        }
        agentStrings = agentStringList.toArray(agentStrings);


        List<Department> departmentList = DepartmentSingleton.getInstance().getDepartmentList();
        List<String> departmentStringList = new ArrayList<>();
        for(Department department : departmentList ){
            departmentStringList.add(department.name);
        }
        departmentStrings = departmentStringList.toArray(departmentStrings);
    }
    @Override
    public void locationTextViewClick(final TextView locationTextView){
        List<SearchableItem> temp = new ArrayList<>();
        temp.addAll( LocationSingleton.getInstance().getLocationList());
        view.showSetDialog(new SearchItemAdapter.OnClickListener() {
            @Override
            public void onClick(SearchableItem item) {
                locationTextView.setText(item.getName());
                location = (Location) item;
            }
        },"地點列表",temp);
    }
    @Override
    public void departmentTextViewClick(final TextView departmentTextView){
        List<SearchableItem> temp = new ArrayList<>();
        temp.addAll( DepartmentSingleton.getInstance().getDepartmentList());
        view.showSetDialog(new SearchItemAdapter.OnClickListener() {
            @Override
            public void onClick(SearchableItem item) {
                departmentTextView.setText(item.getName());
                department = (Department) item;
            }
        },"保管部門列表",temp);
    }
    @Override
    public void agentTextViewClick(final TextView agentTextView){
        List<SearchableItem> temp = new ArrayList<>();
        temp.addAll( AgentSingleton.getInstance().getAgentList());
        view.showSetDialog(new SearchItemAdapter.OnClickListener() {
            @Override
            public void onClick(SearchableItem item) {
                agentTextView.setText(item.getName());
                agent = (Agent) item;
            }
        },"保管人列表",temp);
    }

    @Override
    public void useGroupTextViewClick(final TextView useGroupTextVie) {
        List<SearchableItem> temp = new ArrayList<>();
        temp.addAll( DepartmentSingleton.getInstance().getDepartmentList());
        view.showSetDialog(new SearchItemAdapter.OnClickListener() {
            @Override
            public void onClick(SearchableItem item) {
                useGroupTextVie.setText(item.getName());
                useGroup = (Department) item;
            }
        },"使用部門列表",temp);
    }

    @Override
    public void userTextViewClick(final TextView userTextView) {
        List<SearchableItem> temp = new ArrayList<>();
        temp.addAll( AgentSingleton.getInstance().getAgentList());
        view.showSetDialog(new SearchItemAdapter.OnClickListener() {
            @Override
            public void onClick(SearchableItem item) {
                userTextView.setText(item.getName());
                user = (Agent) item;
            }
        },"使用人列表",temp);

    }

    @Override
    public void searchTextViewClick() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                view.showProgress("設定中");
                int count = 0;
                for(Item item : itemList){
                    if(location != null ){
                        item.setLocation(location);
                    }
                    if(agent != null ){
                        item.setCustodian(agent);
                    }
                    if(department != null){
                        item.setCustodyGroup(department);
                    }
                    if(user != null){
                        item.setUser(user);
                    }
                    if(useGroup != null){
                        item.setUseGroup(useGroup);
                    }
                    count++;
                    view.updateProgress(count * 100 / itemList.size());
                }
                ItemSingleton.getInstance().saveToDB();
                view.hideProgress();
                view.dismissAllowingStateLoss();
            }
        }).start();
    }

}
