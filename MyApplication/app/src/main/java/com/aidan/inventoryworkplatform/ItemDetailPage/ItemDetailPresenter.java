package com.aidan.inventoryworkplatform.ItemDetailPage;

import android.content.DialogInterface;
import android.widget.TextView;

import com.aidan.inventoryworkplatform.Entity.Agent;
import com.aidan.inventoryworkplatform.Entity.Department;
import com.aidan.inventoryworkplatform.Entity.Item;
import com.aidan.inventoryworkplatform.Entity.Location;
import com.aidan.inventoryworkplatform.Model.AgentSingleton;
import com.aidan.inventoryworkplatform.Model.DepartmentSingleton;
import com.aidan.inventoryworkplatform.Model.LocationSingleton;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by s352431 on 2016/11/22.
 */
public class ItemDetailPresenter implements ItemDetailContract.presenter {
    private ItemDetailContract.view view;
    private ItemDetailModel model;
    private String[] locationStrings ={};
    private String[] agentStrings ={};
    private String[] departmentStrings ={};
    private String[] deleteStrings ={"Y","N"};
    private String[] printStrings ={"Y","N"};

    public ItemDetailPresenter(ItemDetailContract.view view,Item item){
        this.view = view;
        model = new ItemDetailModel(item);
        init();
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
    public void start() {
        view.findView();
        view.setViewValue(model.getItem());
        view.setViewClick();
    }

    @Override
    public void saveItemToChecked(boolean flag) {
        Item item = model.getItem();
        item.setConfirm(flag);
    }

    @Override
    public void locationTextViewClick(final TextView locationTextView){
        view.showSetDialog(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int position) {
                locationTextView.setText(locationStrings[position]);
                model.getItem().setLocation(LocationSingleton.getInstance().getLocationList().get(position));
            }
        },"地點列表",locationStrings);

    }
    @Override
    public void departmentTextViewClick(final TextView departmentTextView){
        view.showSetDialog(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int position) {
                departmentTextView.setText(departmentStrings[position]);
                model.getItem().setCustodyGroup(DepartmentSingleton.getInstance().getDepartmentList().get(position));
            }
        },"保管部門列表",departmentStrings);
    }
    @Override
    public void agentTextViewClick(final TextView agentTextView){
        view.showSetDialog(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int position) {
                agentTextView.setText(agentStrings[position]);
                model.getItem().setCustodian(AgentSingleton.getInstance().getAgentList().get(position));
            }
        },"保管人列表",agentStrings);
    }

    @Override
    public void useGroupTextViewClick(final TextView useGroupTextVie) {
        view.showSetDialog(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int position) {
                useGroupTextVie.setText(departmentStrings[position]);
                model.getItem().setUseGroup(DepartmentSingleton.getInstance().getDepartmentList().get(position));
            }
        },"使用部門列表",departmentStrings);
    }

    @Override
    public void userTextViewClick(final TextView userTextView) {
        view.showSetDialog(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int position) {
                userTextView.setText(agentStrings[position]);
                model.getItem().setUser(AgentSingleton.getInstance().getAgentList().get(position));
            }
        },"使用人列表",agentStrings);
    }

    @Override
    public void deleteTextViewClick(final TextView deleteTextView) {
        view.showSetDialog(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int position) {
                deleteTextView.setText(deleteStrings[position]);
                model.getItem().setDelete(deleteStrings[position]);
            }
        },"報廢",deleteStrings);
    }

    @Override
    public void printTextViewClick(final TextView printTextView) {
        view.showSetDialog(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int position) {
                printTextView.setText(printStrings[position]);
                model.getItem().setPrint(printStrings[position]);
            }
        },"補印",printStrings);
    }


}
