package com.aidan.inventoryworkplatform.SearchPage;

import android.content.DialogInterface;
import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
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
                agent = (Agent) item;
            }
        },"使用人列表",temp);

    }

    @Override
    public void searchTextViewClick(String number,String serialNumber) {
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
            if(number.length() > 1 && !item.getNumber().equals(number)){
                continue;
            }
            if(serialNumber.length() > 1 && !item.getSerialNumber().equals(number)){
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
