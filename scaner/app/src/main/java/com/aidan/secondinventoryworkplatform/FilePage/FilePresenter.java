package com.aidan.secondinventoryworkplatform.FilePage;

import android.os.Environment;

import com.aidan.secondinventoryworkplatform.Constants;
import com.aidan.secondinventoryworkplatform.Database.ItemDAO;
import com.aidan.secondinventoryworkplatform.Entity.SelectableItem.Agent;
import com.aidan.secondinventoryworkplatform.Entity.SelectableItem.ApprovalNumber;
import com.aidan.secondinventoryworkplatform.Entity.SelectableItem.ChangeTarget;
import com.aidan.secondinventoryworkplatform.Entity.SelectableItem.Department;
import com.aidan.secondinventoryworkplatform.Entity.Item;
import com.aidan.secondinventoryworkplatform.Entity.SelectableItem.DepositPlace;
import com.aidan.secondinventoryworkplatform.Entity.SelectableItem.ImpairmentReason;
import com.aidan.secondinventoryworkplatform.Entity.SelectableItem.Location;
import com.aidan.secondinventoryworkplatform.Entity.SelectableItem.SummonsNumber;
import com.aidan.secondinventoryworkplatform.Entity.SelectableItem.SummonsTitle;
import com.aidan.secondinventoryworkplatform.Model.SelecetableSingleton.AgentSingleton;
import com.aidan.secondinventoryworkplatform.Model.SelecetableSingleton.ApprovalNumberSingleton;
import com.aidan.secondinventoryworkplatform.Model.SelecetableSingleton.ChangeTargetSingleton;
import com.aidan.secondinventoryworkplatform.Model.SelecetableSingleton.DepartmentSingleton;
import com.aidan.secondinventoryworkplatform.Model.ItemSingleton;
import com.aidan.secondinventoryworkplatform.Model.SelecetableSingleton.DepositPlaceSingleton;
import com.aidan.secondinventoryworkplatform.Model.SelecetableSingleton.ImpairmentReasonSingleton;
import com.aidan.secondinventoryworkplatform.Model.SelecetableSingleton.LocationSingleton;
import com.aidan.secondinventoryworkplatform.Model.SelecetableSingleton.SummonsNumberSingleton;
import com.aidan.secondinventoryworkplatform.Model.SelecetableSingleton.SummonsTitleSingleton;
import com.aidan.secondinventoryworkplatform.ScannerPage.ScannerItemManager;
import com.aidan.secondinventoryworkplatform.Singleton;
import com.aidan.secondinventoryworkplatform.Utils.ReadExcel;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Aidan on 2016/11/20.
 */

public class FilePresenter implements FileContract.presenter {
    FileContract.view view;
    Map<String,String> nameMap;

    public FilePresenter(FileContract.view view) {
        this.view = view;
    }

    @Override
    public void start() {
        view.findView();
        view.setViewClick();
    }

    @Override
    public void readTxtButtonClick(final String path) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                loadData(path, "讀取財產中", Constants.PREFERENCE_PROPERTY_KEY, Item.Type.property);
            }
        }).start();
    }

    private void loadData(String path, String msg,String key, Item.Type type) {
        view.showProgress(msg);
        List<Item> itemList = ItemSingleton.getInstance().getItemList();
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
            JSONObject MS = jsonObj.getJSONObject(Constants.MS);
            Singleton.preferenceEditor.putString(key, MS.toString()).commit();
            getNameMap(ASSETs);
            getItems(ASSETs, itemList, type);
            getAgents(ASSETs);
            getDepartments(ASSETs);
            getLocations(ASSETs);
            getApprovalNumber(ASSETs);
            getChangeItem(ASSETs);
            getDepositPlace(ASSETs);
            getImpairmentReason(ASSETs);
            getSummonsNumber(ASSETs);
            getSummonsTitle(ASSETs);


            ItemSingleton.getInstance().saveToDB();
            DepartmentSingleton.getInstance().saveToDB();
            AgentSingleton.getInstance().saveToDB();
            LocationSingleton.getInstance().saveToDB();
            ApprovalNumberSingleton.getInstance().saveToDB();
            DepartmentSingleton.getInstance().saveToDB();
            ImpairmentReasonSingleton.getInstance().saveToDB();
            SummonsNumberSingleton.getInstance().saveToDB();
            SummonsTitleSingleton.getInstance().saveToDB();
            DepositPlaceSingleton.getInstance().saveToDB();
        } catch (Exception e) {
            view.hideProgress();
            view.showToast("檔案格式錯誤");
            e.printStackTrace();
        }
    }

    private void dropTable() {
        try {
            ItemDAO.getInstance().dropTable();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void getNameMap(JSONObject ASSETs){
        try {
            nameMap = new HashMap<>();
            JSONArray data = ASSETs.getJSONArray("PM");
            for(int i = 0 ; i < data.length() ; i++){
                JSONObject t = data.getJSONObject(i);
                nameMap.put(t.getString("編號"),t.getString("品名"));
            }
        }catch (Exception e){

        }
    }

    private void getItems(JSONObject ASSETs, List<Item> itemList, Item.Type type) {
        try {
            JSONArray data = ASSETs.getJSONArray("PA3");
            Singleton.log("itemList size : " + data.length());
            int size = data.length();
            for (int i = 0; i < size; i++) {
                JSONObject c = data.getJSONObject(i);
                Item item = new Item(c);
                boolean notSet = item.getNumber().startsWith("6");

                String key = notSet ? item.getNumber().substring(1) : item.getNumber();
                while(key.length() > 0){
                    if(nameMap.containsKey(key)){
                        item.setNAME(nameMap.get(key));
                        notSet = false;
                        break;
                    }else{
                        key = key.substring(0,key.length()-1);
                    }
                }

                if(notSet){
                    key = item.getNumber();
                    while(key.length() > 0){
                        if(nameMap.containsKey(key)){
                            item.setNAME(nameMap.get(key));
                            break;
                        }else{
                            key = key.substring(0,key.length()-1);
                        }
                    }
                }


                item.setType(type);
                itemList.add(item);
                view.updateProgress((i + 1) * 100 / size);
            }
            view.hideProgress();
            Singleton.log("itemList size : " + itemList.size());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void getLocations(JSONObject ASSETs) {
        List<Location> locationList = LocationSingleton.getInstance().getDataList();
        try {
            HashSet<String> mSet = new HashSet<>();
            JSONArray data = ASSETs.getJSONArray("PA81");
            Singleton.log("locationList size : " + data.length());
            view.showProgress("讀取地點中");
            for (int i = 0; i < data.length(); i++) {
                JSONObject c = data.getJSONObject(i);
                Location location = new Location(c);
                if(mSet.contains(location.getName())){
                    continue;
                }
                locationList.add(location);
                mSet.add(location.getName());
                view.updateProgress((i + 1) * 100 / data.length());
            }
            Singleton.log("locationList size : " + locationList.size());
            view.hideProgress();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Collections.sort(locationList);
    }

    private void getDepartments(JSONObject ASSETs) {
        List<Department> departmentList = DepartmentSingleton.getInstance().getDataList();
        try {
            HashSet<String> mSet = new HashSet<>();
            JSONArray data = ASSETs.getJSONArray("PA82");
            Singleton.log("departmentList size : " + data.length());
            view.showProgress("讀取部門中");
            for (int i = 0; i < data.length(); i++) {
                view.updateProgress((i + 1) * 100 / data.length());
                JSONObject c = data.getJSONObject(i);
                Department department = new Department(c);
                if(mSet.contains(department.getName())){
                    continue;
                }
                departmentList.add(department);
                mSet.add(department.getName());
            }
            Singleton.log("departmentList size : " + departmentList.size());
            view.hideProgress();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Collections.sort(departmentList);
    }

    private void getAgents(JSONObject ASSETs) {
        List<Agent> agentList = AgentSingleton.getInstance().getDataList();
        try {
            HashSet<String> mSet = new HashSet<>();
            JSONArray data = ASSETs.getJSONArray("PA83");
            Singleton.log("agentList size : " + data.length());
            view.showProgress("讀取人名中");
            for (int i = 0; i < data.length(); i++) {
                JSONObject c = data.getJSONObject(i);
                Agent agent = new Agent(c);
                if(mSet.contains(agent.getName())){
                    continue;
                }
                agentList.add(agent);
                mSet.add(agent.getName());
                view.updateProgress((i + 1) * 100 / data.length());
            }
            Singleton.log("agentList size : " + agentList.size());
            view.hideProgress();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Collections.sort(agentList);
    }

    private void getChangeItem(JSONObject ASSETs) {
        List<ChangeTarget> dataList = ChangeTargetSingleton.getInstance().getDataList();
        try {
            HashSet<String> mSet = new HashSet<>();
            JSONArray dataArray = ASSETs.getJSONArray("PA85");

            view.showProgress("讀取異動項目中");
            for (int i = 0; i < dataArray.length(); i++) {
                JSONObject c = dataArray.getJSONObject(i);
                ChangeTarget data = new ChangeTarget(c);
                if(mSet.contains(data.getName())){
                    continue;
                }
                dataList.add(data);
                mSet.add(data.getName());
                view.updateProgress((i + 1) * 100 / dataArray.length());
            }
            Singleton.log("locationList size : " + dataList.size());
            view.hideProgress();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Collections.sort(dataList);
    }

    private void getSummonsTitle(JSONObject ASSETs) {
        List<SummonsTitle> dataList = SummonsTitleSingleton.getInstance().getDataList();
        try {
            HashSet<String> mSet = new HashSet<>();
            JSONArray dataArray = ASSETs.getJSONArray("PA85");

            view.showProgress("讀取傳票用字中");
            for (int i = 0; i < dataArray.length(); i++) {
                JSONObject c = dataArray.getJSONObject(i);
                SummonsTitle data = new SummonsTitle(c);
                if(mSet.contains(data.getName())){
                    continue;
                }
                dataList.add(data);
                mSet.add(data.getName());
                view.updateProgress((i + 1) * 100 / dataArray.length());
            }
            Singleton.log("locationList size : " + dataList.size());
            view.hideProgress();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Collections.sort(dataList);
    }

    private void getSummonsNumber(JSONObject ASSETs) {
        List<SummonsNumber> dataList = SummonsNumberSingleton.getInstance().getDataList();
        try {
            HashSet<String> mSet = new HashSet<>();
            JSONArray dataArray = ASSETs.getJSONArray("PA86");

            view.showProgress("讀取傳票號碼中");
            for (int i = 0; i < dataArray.length(); i++) {
                JSONObject c = dataArray.getJSONObject(i);
                SummonsNumber data = new SummonsNumber(c);
                if(mSet.contains(data.getName())){
                    continue;
                }
                dataList.add(data);
                mSet.add(data.getName());
                view.updateProgress((i + 1) * 100 / dataArray.length());
            }
            Singleton.log("locationList size : " + dataList.size());
            view.hideProgress();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Collections.sort(dataList);
    }

    private void getImpairmentReason(JSONObject ASSETs) {
        List<ImpairmentReason> dataList = ImpairmentReasonSingleton.getInstance().getDataList();
        try {
            HashSet<String> mSet = new HashSet<>();
            JSONArray dataArray = ASSETs.getJSONArray("PA87");

            view.showProgress("讀取減損原因中");
            for (int i = 0; i < dataArray.length(); i++) {
                JSONObject c = dataArray.getJSONObject(i);
                ImpairmentReason data = new ImpairmentReason(c);
                if(mSet.contains(data.getName())){
                    continue;
                }
                dataList.add(data);
                mSet.add(data.getName());
                view.updateProgress((i + 1) * 100 / dataArray.length());
            }
            Singleton.log("locationList size : " + dataList.size());
            view.hideProgress();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Collections.sort(dataList);
    }

    private void getDepositPlace(JSONObject ASSETs) {
        List<DepositPlace> dataList = DepositPlaceSingleton.getInstance().getDataList();
        try {
            HashSet<String> mSet = new HashSet<>();
            JSONArray dataArray = ASSETs.getJSONArray("PA88");

            view.showProgress("讀取繳存地點中");
            for (int i = 0; i < dataArray.length(); i++) {
                JSONObject c = dataArray.getJSONObject(i);
                DepositPlace data = new DepositPlace(c);
                if(mSet.contains(data.getName())){
                    continue;
                }
                dataList.add(data);
                mSet.add(data.getName());
                view.updateProgress((i + 1) * 100 / dataArray.length());
            }
            Singleton.log("locationList size : " + dataList.size());
            view.hideProgress();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Collections.sort(dataList);
    }

    private void getApprovalNumber(JSONObject ASSETs) {
        List<ApprovalNumber> dataList = ApprovalNumberSingleton.getInstance().getDataList();
        try {
            HashSet<String> mSet = new HashSet<>();
            JSONArray dataArray = ASSETs.getJSONArray("PA89");

            view.showProgress("讀取核准文號中");
            for (int i = 0; i < dataArray.length(); i++) {
                JSONObject c = dataArray.getJSONObject(i);
                ApprovalNumber data = new ApprovalNumber(c);
                if(mSet.contains(data.getName())){
                    continue;
                }
                dataList.add(data);
                mSet.add(data.getName());
                view.updateProgress((i + 1) * 100 / dataArray.length());
            }
            Singleton.log("locationList size : " + dataList.size());
            view.hideProgress();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Collections.sort(dataList);
    }



    @Override
    public void saveFile(String fileName, String preferencesKey, Set<String> allowType) {
        JSONObject jsonObject = getAllDataJSON(preferencesKey, allowType);
        String dir = Environment.getExternalStorageDirectory().getAbsolutePath() + "/欣華盤點系統/行動裝置轉至電腦";
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
            bufWriter.write(jsonObject.toString().replace("\\/","/"));
            bufWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
            Singleton.log(file.getAbsoluteFile() + "寫檔發生錯誤");
        }
    }

    @Override
    public void clearData() {
        List<Item> itemList = ItemSingleton.getInstance().getItemList();
        List<Location> locationList = LocationSingleton.getInstance().getDataList();
        List<Agent> agentList = AgentSingleton.getInstance().getDataList();
        List<Department> departmentList = DepartmentSingleton.getInstance().getDataList();
        itemList.clear();
        locationList.clear();
        agentList.clear();
        departmentList.clear();
        ScannerItemManager.getInstance().getItemList().clear();
        dropTable();
    }

    @Override
    public void inputItemTextViewClick(final String path) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                loadData(path, "讀取物品中", Constants.PREFERENCE_ITEM_KEY, Item.Type.item);
            }
        }).start();
    }

    public JSONObject getAllDataJSON(String key, Set<String> allowType) {
        List<Item> itemList = ItemSingleton.getInstance().getItemList();
        JSONObject jsonObject = new JSONObject();
        try {
            JSONObject MS = new JSONObject(Singleton.preferences.getString(key, ""));
            jsonObject.put(Constants.MS, MS);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            JSONObject ASSETs = new JSONObject();
            JSONArray PA3 = new JSONArray();
            for (Item item : itemList) {
                if (allowType.contains(item.getPA3C0().length() == 0 ? item.getPA3C1() : item.getPA3C0())) {
                    PA3.put(item.toJSON());
                }
            }
            JSONArray D1 = new JSONArray();
            JSONArray D2 = new JSONArray();
            JSONArray D3 = new JSONArray();
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
