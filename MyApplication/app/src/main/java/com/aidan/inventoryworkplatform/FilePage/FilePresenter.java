package com.aidan.inventoryworkplatform.FilePage;

import android.os.Environment;
import android.provider.MediaStore;

import com.aidan.inventoryworkplatform.Entity.Agent;
import com.aidan.inventoryworkplatform.Entity.Department;
import com.aidan.inventoryworkplatform.Entity.Item;
import com.aidan.inventoryworkplatform.Entity.Location;
import com.aidan.inventoryworkplatform.Model.AgentSingleton;
import com.aidan.inventoryworkplatform.Model.DepartmentSingleton;
import com.aidan.inventoryworkplatform.Model.ItemSingleton;
import com.aidan.inventoryworkplatform.Model.LocationSingleton;
import com.aidan.inventoryworkplatform.Singleton;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.List;

/**
 * Created by Aidan on 2016/11/20.
 */

public class FilePresenter implements FileContract.presenter {
    FileContract.view view;
    public FilePresenter(FileContract.view view){
        this.view = view;
    }

    @Override
    public void start() {
        view.findView();
        view.setViewClick();
    }

    @Override
    public void readTxtButtonClick(String path) {
        List<Item> itemList = ItemSingleton.getInstance().getItemList();
        List<Location> locationList = LocationSingleton.getInstance().getLocationList();
        List<Agent> agentList = AgentSingleton.getInstance().getAgentList();
        List<Department> departmentList = DepartmentSingleton.getInstance().getDepartmentList();
        itemList.clear();
        locationList.clear();
        agentList.clear();
        departmentList.clear();
        try {
            File yourFile = new File(path);
            FileInputStream stream = new FileInputStream(yourFile);
            String jsonStr = null;
            try {
                FileChannel fc = stream.getChannel();
                MappedByteBuffer bb = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size());

                jsonStr = Charset.defaultCharset().decode(bb).toString();
            }
            catch(Exception e){
                e.printStackTrace();
            }
            finally {
                stream.close();
            }

            JSONObject jsonObj = new JSONObject(jsonStr);
            JSONObject ASSETs = jsonObj.getJSONObject("ASSETs");

            // Getting data JSON Array nodes
            JSONArray data  = ASSETs.getJSONArray("PA3");
            Singleton.log("itemList size : "+data.length());
            // looping through All nodes
            for (int i = 0; i < data.length(); i++) {
                JSONObject c = data.getJSONObject(i);
                Item item = new Item(c);
                itemList.add(item);
            }
            Singleton.log("itemList size : "+itemList.size());

            data  = ASSETs.getJSONArray("D1");
            Singleton.log("agentList size : "+data.length());
            for (int i = 0; i < data.length(); i++) {
                JSONObject c = data.getJSONObject(i);
                Agent agent = new Agent(c);
                agentList.add(agent);
            }
            Singleton.log("agentList size : "+agentList.size());
            data  = ASSETs.getJSONArray("D2");
            Singleton.log("departmentList size : "+data.length());
            for (int i = 0; i < data.length(); i++) {
                JSONObject c = data.getJSONObject(i);
                Department department = new Department(c);
                departmentList.add(department);
            }
            Singleton.log("departmentList size : "+departmentList.size());
            data  = ASSETs.getJSONArray("D3");
            Singleton.log("locationList size : "+data.length());
            for (int i = 0; i < data.length(); i++) {
                JSONObject c = data.getJSONObject(i);
                Location location = new Location(c);
                locationList.add(location);
            }
            Singleton.log("locationList size : "+locationList.size());


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void saveFile(String fileName){
        JSONObject jsonObject  = getAllDataJSON();
        String dir = Environment.getExternalStorageDirectory().getAbsolutePath()+ "/Download";
        File dirFile = new File(dir);
        if (!dirFile.exists()) {
            dirFile.mkdirs();
        }
        File file = new File(dir, fileName+".txt");
        if(file.exists()){
            file.delete();
            file = new File(dir, fileName+".txt");
        }

        try  {
            FileWriter fw = new FileWriter(file);
            fw.write(jsonObject.toString());
            fw.close();
            Singleton.log(file.getAbsolutePath());
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }catch (IOException ioe){
            ioe.printStackTrace();
        }


    }
    public JSONObject getAllDataJSON(){
        List<Item> itemList = ItemSingleton.getInstance().getItemList();
        List<Location> locationList = LocationSingleton.getInstance().getLocationList();
        List<Agent> agentList = AgentSingleton.getInstance().getAgentList();
        List<Department> departmentList = DepartmentSingleton.getInstance().getDepartmentList();
        JSONObject jsonObject = new JSONObject();
        try{
            JSONObject ASSETs = new JSONObject();
            JSONArray PA3 = new JSONArray();
            for(Item item : itemList){
                PA3.put(item.toJSON());
            }
            JSONArray D1 = new JSONArray();
            for(Agent agent : agentList){
                D1.put(agent.toJSON());
            }
            JSONArray D2 = new JSONArray();
            for(Department department : departmentList){
                D2.put(department.toJSON());
            }
            JSONArray D3 = new JSONArray();
            for(Location location : locationList){
                D3.put(location.toJSON());
            }
            ASSETs.put("D1",D1);
            ASSETs.put("D2",D2);
            ASSETs.put("D3",D3);
            ASSETs.put("PA3",PA3);
            jsonObject.put("ASSETs",ASSETs);
        }catch (JSONException e){
            e.printStackTrace();
        }
        return jsonObject;
    }
}
