package com.aidan.inventoryworkplatform.FilePage;

import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;

import com.aidan.inventoryworkplatform.Constants;
import com.aidan.inventoryworkplatform.Database.ItemDAO;
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


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static android.R.attr.data;
import static android.R.attr.value;

/**
 * Created by Aidan on 2016/11/20.
 */

public class FilePresenter implements FileContract.presenter {
    FileContract.view view;

    public FilePresenter(FileContract.view view) {
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
        ItemDAO.getInstance().dropTable();
        try {
            File yourFile = new File(path);
            FileInputStream stream = new FileInputStream(yourFile);
            String jsonStr = "";

            try {
                FileChannel fc = stream.getChannel();
                MappedByteBuffer bb = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size());
                jsonStr = Charset.forName("Big5").decode(bb).toString();
            } catch (Exception e) {
                e.printStackTrace();
            }
            stream.close();

            JSONObject jsonObj = new JSONObject(jsonStr);
            JSONObject ASSETs = jsonObj.getJSONObject("ASSETs");
            JSONObject MS  = jsonObj.getJSONObject(Constants.MS);
            Singleton.preferenceEditor.putString(Constants.MS,MS.toString()).commit();
            getItems(ASSETs, itemList);
            getAgents(ASSETs, agentList);
            getDepartments(ASSETs, departmentList);
            getLocations(ASSETs, locationList);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getItems(JSONObject ASSETs, List<Item> itemList) {
        try {
            JSONArray data = ASSETs.getJSONArray("PA3");
            Singleton.log("itemList size : " + data.length());
            int size = data.length();
            for (int i = 0; i < size; i++) {
                JSONObject c = data.getJSONObject(i);
                Item item = new Item(c);
                itemList.add(item);
            }
            Singleton.log("itemList size : " + itemList.size());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void getAgents(JSONObject ASSETs, List<Agent> agentList) {
        try {
            JSONArray data = ASSETs.getJSONArray("D1");
            Singleton.log("agentList size : " + data.length());
            for (int i = 0; i < data.length(); i++) {
                JSONObject c = data.getJSONObject(i);
                Agent agent = new Agent(c);
                agentList.add(agent);
            }
            Singleton.log("agentList size : " + agentList.size());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Collections.sort(agentList);
    }

    private void getDepartments(JSONObject ASSETs, List<Department> departmentList) {
        try {
            JSONArray data = ASSETs.getJSONArray("D2");
            Singleton.log("departmentList size : " + data.length());
            for (int i = 0; i < data.length(); i++) {
                JSONObject c = data.getJSONObject(i);
                Department department = new Department(c);
                departmentList.add(department);
            }
            Singleton.log("departmentList size : " + departmentList.size());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Collections.sort(departmentList);
    }

    private void getLocations(JSONObject ASSETs, List<Location> locationList) {
        try {
            JSONArray data = ASSETs.getJSONArray("D3");
            Singleton.log("locationList size : " + data.length());
            for (int i = 0; i < data.length(); i++) {
                JSONObject c = data.getJSONObject(i);
                Location location = new Location(c);
                locationList.add(location);
            }
            Singleton.log("locationList size : " + locationList.size());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Collections.sort(locationList);
    }


    public void saveFile(String fileName) {
        JSONObject jsonObject = getAllDataJSON();
        String dir = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Download";
        File dirFile = new File(dir);
        if (!dirFile.exists()) {
            dirFile.mkdirs();
        }
        File file = new File(dir, fileName + ".txt");
        if (file.exists()) {
            file.delete();
            file = new File(dir, fileName + ".txt");
        }

        try {
            BufferedWriter bufWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, false), "big5"));
            bufWriter.write(jsonObject.toString());
            bufWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
            Singleton.log(file.getAbsoluteFile() + "寫檔發生錯誤");
        }
    }

    public JSONObject getAllDataJSON() {
        List<Item> itemList = ItemSingleton.getInstance().getItemList();
        List<Location> locationList = LocationSingleton.getInstance().getLocationList();
        List<Agent> agentList = AgentSingleton.getInstance().getAgentList();
        List<Department> departmentList = DepartmentSingleton.getInstance().getDepartmentList();
        JSONObject jsonObject = new JSONObject();
        try {
            JSONObject MS = new JSONObject(Singleton.preferences.getString(Constants.MS,""));
            jsonObject.put(Constants.MS,MS);
        }catch (JSONException e){
            e.printStackTrace();
        }
        try {
            JSONObject ASSETs = new JSONObject();
            JSONArray PA3 = new JSONArray();
            for (Item item : itemList) {
                PA3.put(item.toJSON());
            }
            JSONArray D1 = new JSONArray();
//            for (Agent agent : agentList) {
//                D1.put(agent.toJSON());
//            }
            JSONArray D2 = new JSONArray();
//            for (Department department : departmentList) {
//                D2.put(department.toJSON());
//            }
            JSONArray D3 = new JSONArray();
//            for (Location location : locationList) {
//                D3.put(location.toJSON());
//            }

            ASSETs.put("D1", D1);
            ASSETs.put("D2", D2);
            ASSETs.put("D3", D3);
            ASSETs.put("PA3", PA3);
            jsonObject.put("ASSETs", ASSETs);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}
