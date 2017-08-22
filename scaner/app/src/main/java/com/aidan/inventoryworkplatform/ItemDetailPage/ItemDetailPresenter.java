package com.aidan.inventoryworkplatform.ItemDetailPage;

import android.content.DialogInterface;
import android.widget.TextView;

import com.aidan.inventoryworkplatform.Database.ItemDAO;
import com.aidan.inventoryworkplatform.Dialog.SearchItemAdapter;
import com.aidan.inventoryworkplatform.Dialog.SearchableItem;
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
        try {
            ItemDAO.getInstance().update(item);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void locationTextViewClick(final TextView locationTextView){
        List<SearchableItem> temp = new ArrayList<>();
        temp.addAll( LocationSingleton.getInstance().getLocationList());
        view.showSetDialog(new SearchItemAdapter.OnClickListener() {
            @Override
            public void onClick(SearchableItem item) {
                locationTextView.setText(item.getName());
                model.getItem().setLocation((Location)item);
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
                model.getItem().setCustodyGroup((Department) item);
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
                model.getItem().setCustodian((Agent) item);
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
                model.getItem().setUseGroup((Department) item);
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
                model.getItem().setUser((Agent) item);
            }
        },"使用人列表",temp);
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
