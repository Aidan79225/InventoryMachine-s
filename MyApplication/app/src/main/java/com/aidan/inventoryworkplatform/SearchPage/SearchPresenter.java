package com.aidan.inventoryworkplatform.SearchPage;

import android.content.DialogInterface;
import android.widget.TextView;

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

public class SearchPresenter implements SearchContract.presenter{
    SearchContract.view view;
    String[] locationStrings ={};
    String[] agentStrings ={};
    String[] departmentStrings ={};
    Location location;
    Agent agent;
    Department department;
    Agent user;
    Department useGroup;
     SearchPresenter( SearchContract.view view){
        this.view = view;

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
        view.showSetDialog(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int position) {
                locationTextView.setText(locationStrings[position]);
                location = LocationSingleton.getInstance().getLocationList().get(position);
            }
        },"地點列表",locationStrings);

    }
    @Override
    public void departmentTextViewClick(final TextView departmentTextView){
        view.showSetDialog(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int position) {
                departmentTextView.setText(departmentStrings[position]);
                department = DepartmentSingleton.getInstance().getDepartmentList().get(position);
            }
        },"保管部門列表",departmentStrings);
    }
    @Override
    public void agentTextViewClick(final TextView agentTextView){
        view.showSetDialog(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int position) {
                agentTextView.setText(agentStrings[position]);
                agent = AgentSingleton.getInstance().getAgentList().get(position);
            }
        },"保管人列表",agentStrings);
    }

    @Override
    public void useGroupTextViewClick(final TextView useGroupTextVie) {
        view.showSetDialog(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int position) {
                useGroupTextVie.setText(departmentStrings[position]);
                useGroup = DepartmentSingleton.getInstance().getDepartmentList().get(position);
            }
        },"使用部門列表",departmentStrings);
    }

    @Override
    public void userTextViewClick(final TextView userTextView) {
        view.showSetDialog(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int position) {
                userTextView.setText(agentStrings[position]);
                user = AgentSingleton.getInstance().getAgentList().get(position);
            }
        },"使用人列表",agentStrings);

    }

    @Override
    public void searchTextViewClick(String key) {
        List<Item> itemList = new ArrayList<>();
        for(Item item : ItemSingleton.getInstance().getItemList()){
            if(location != null && !item.getLocation().number.equals(location.number)){
                continue;
            }
            if(agent != null && !item.getCustodian().number.equals(agent.number)){
                continue;
            }
            if(department != null && !item.getCustodyGroup().number.equals(department.number)){
                continue;
            }
            if(user != null && !item.getCustodian().number.equals(user.number)){
                continue;
            }
            if(useGroup != null && !item.getCustodyGroup().number.equals(useGroup.number)){
                continue;
            }
            if( key.length() > 1 && !item.getIdNumber().equals(key)){
                continue;
            }
            itemList.add(item);
        }
        view.showFragmentWithResult(itemList);
    }
    @Override
    public void clearAll(){
        location = null;
        agent = null;
        department = null;
        user = null;
        useGroup = null;
        view.clearViews();
    }
}
