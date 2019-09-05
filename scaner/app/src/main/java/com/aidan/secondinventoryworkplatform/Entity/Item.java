package com.aidan.secondinventoryworkplatform.Entity;

import android.text.format.DateUtils;

import com.aidan.secondinventoryworkplatform.Entity.SelectableItem.Agent;
import com.aidan.secondinventoryworkplatform.Entity.SelectableItem.Department;
import com.aidan.secondinventoryworkplatform.Entity.SelectableItem.Location;
import com.aidan.secondinventoryworkplatform.KeyConstants;
import com.aidan.secondinventoryworkplatform.Model.ItemSingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Aidan on 2016/10/24.
 */

public class Item {
    public enum Type{
        property,
        item;
    }
    private long id = 0;
    private Type type = Type.property;
    private String PA3C1 = "";
    private String PA3C2 = "";
    private String PA3C3 = "";
    private String PA3C4 = "";
    private String PA3C5 = "";
    private String PA3C6 = "";
    private String PA3P3 = "";
    private String PA3PS = "";
    private String PA3MK = "";
    private String PA3BD = "";
    private String PA3PY = "";
    private String PA3LOC = "";
    private String PA3LOCN = "";
    private String PA3OUT = "";
    private String PA3OUTN = "";
    private String PA3OU = "";
    private String PA3OUN = "";
    private String PA3UUT = "";
    private String PA3UUTN = "";
    private String PA3UR = "";
    private String PA3URN = "";
    private String PA308 = "";
    private String PA3DEL = "";
    private String PA3PRN = "";
    private String NAME = "";
    private TagContent tagContent = TagContent.AgentGroup;

    private String PA3INX = "";
    private String PA3C0 = "";
    private String PA3C7 = "";
    private String PA3QY = "";
    private String PA3UNP = "";
    private String PA3TOP = "";
    private String PA3MEMO = "";
    private String PA3MOC8 = "";
    private String PA3MOB = "";
    private String PA3MOD = "";
    private String PA3MONO = "";
    private String PA3DED = "";
    private String PA3DENO = "";
    private String PA3TI = "";
    private String PA3MB ="";
    private String PA3VWW = "";
    private String PA3VN = "";
    private String PA3DR = "";
    private String PA3PD = "";
    private String PA3AN = "";
    private String PA3MOL = "";
    private String PA8PD = "";
    private String PA8A = "";

    public Item() {

    }

    public Item(JSONObject jsonObject) {
        try {
            PA3C1 = jsonObject.getString(ItemConstants.PA3C1);
            PA3C2 = jsonObject.getString(ItemConstants.PA3C2);
            PA3C3 = jsonObject.getString(ItemConstants.PA3C3);
            PA3C4 = jsonObject.getString(ItemConstants.PA3C4);
            PA3C5 = jsonObject.getString(ItemConstants.PA3C5);
            PA3C6 = jsonObject.getString(ItemConstants.PA3C6);
            PA3P3 = jsonObject.getString(ItemConstants.PA3P3);
            PA3PS = jsonObject.getString(ItemConstants.PA3PS);
            PA3MK = jsonObject.getString(ItemConstants.PA3MK);
            PA3BD = jsonObject.getString(ItemConstants.PA3BD);
            PA3PY = jsonObject.getString(ItemConstants.PA3PY);
            PA3LOC = jsonObject.getString(ItemConstants.PA3LOC);
            PA3LOCN = jsonObject.getString(ItemConstants.PA3LOCN);
            PA3OUT = jsonObject.getString(ItemConstants.PA3OUT);
            PA3OUTN = jsonObject.getString(ItemConstants.PA3OUTN);
            PA3OU = jsonObject.getString(ItemConstants.PA3OU);
            PA3OUN = jsonObject.getString(ItemConstants.PA3OUN);
            PA3UUT = jsonObject.getString(ItemConstants.PA3UUT);
            PA3UUTN = jsonObject.getString(ItemConstants.PA3UUTN);
            PA3UR = jsonObject.getString(ItemConstants.PA3UR);
            PA3URN = jsonObject.getString(ItemConstants.PA3URN);
            PA308 = jsonObject.getString(ItemConstants.PA308);
            PA3DEL = jsonObject.getString(ItemConstants.PA3DEL);
            PA3PRN = jsonObject.getString(ItemConstants.PA3PRN);

            PA3INX = jsonObject.getString(ItemConstants.PA3INX);
            PA3C0 = jsonObject.getString(ItemConstants.PA3C0);
            PA3C7 = jsonObject.getString(ItemConstants.PA3C7);
            PA3QY = jsonObject.getString(ItemConstants.PA3QY);
            PA3UNP = jsonObject.getString(ItemConstants.PA3UNP);
            PA3TOP = jsonObject.getString(ItemConstants.PA3TOP);

            if (jsonObject.has(ItemConstants.PA3MEMO)){
                PA3MEMO = jsonObject.getString(ItemConstants.PA3MEMO);
            }
            if (jsonObject.has(ItemConstants.PA3MOC8)){
                PA3MOC8 = jsonObject.getString(ItemConstants.PA3MOC8);
            }
            if (jsonObject.has(ItemConstants.PA3MOB)){
                PA3MOB = jsonObject.getString(ItemConstants.PA3MOB);
            }
            if (jsonObject.has(ItemConstants.PA3MOD)){
                PA3MOD = jsonObject.getString(ItemConstants.PA3MOD).replace("\\","");;
            }
            if (jsonObject.has(ItemConstants.PA3MONO)){
                PA3MONO = jsonObject.getString(ItemConstants.PA3MONO);
            }

            if (jsonObject.has(ItemConstants.PA3DED)){
                PA3DED = jsonObject.getString(ItemConstants.PA3DED).replace("\\","");
            }
            if (jsonObject.has(ItemConstants.PA3DENO)){
                PA3DENO = jsonObject.getString(ItemConstants.PA3DENO);
            }
            if (jsonObject.has(ItemConstants.PA3TI)){
                PA3TI = jsonObject.getString(ItemConstants.PA3TI);
            }
            if (jsonObject.has(ItemConstants.PA3VWW)){
                PA3VWW = jsonObject.getString(ItemConstants.PA3VWW);
            }
            if (jsonObject.has(ItemConstants.PA3VN)){
                PA3VN = jsonObject.getString(ItemConstants.PA3VN);
            }
            if (jsonObject.has(ItemConstants.PA3DR)){
                PA3DR = jsonObject.getString(ItemConstants.PA3DR);
            }
            if (jsonObject.has(ItemConstants.PA3PD)){
                PA3PD = jsonObject.getString(ItemConstants.PA3PD);
            }
            if (jsonObject.has(ItemConstants.PA3AN)){
                PA3AN = jsonObject.getString(ItemConstants.PA3AN);
            }
            if (jsonObject.has(ItemConstants.PA3MOL)){
                PA3MOL = jsonObject.getString(ItemConstants.PA3MOL);
            }

            if (jsonObject.has(ItemConstants.PA8PD)){
                PA8PD = jsonObject.getString(ItemConstants.PA8PD);
            }
            if (jsonObject.has(ItemConstants.PA8A)){
                PA8A = jsonObject.getString(ItemConstants.PA8A);
            }
            if(jsonObject.has(ItemConstants.TYPE)){
                type = Type.valueOf(jsonObject.getString(ItemConstants.TYPE));
            }
            if(jsonObject.has(ItemConstants.PA3MB)){
                PA3MB = jsonObject.getString(ItemConstants.PA3MB);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setData(String data) {
        try {
            JSONObject jsonObject = new JSONObject(data);
            PA3C1 = jsonObject.getString(ItemConstants.PA3C1);
            PA3C2 = jsonObject.getString(ItemConstants.PA3C2);
            PA3C3 = jsonObject.getString(ItemConstants.PA3C3);
            PA3C4 = jsonObject.getString(ItemConstants.PA3C4);
            PA3C5 = jsonObject.getString(ItemConstants.PA3C5);
            PA3C6 = jsonObject.getString(ItemConstants.PA3C6);
            PA3P3 = jsonObject.getString(ItemConstants.PA3P3);
            PA3PS = jsonObject.getString(ItemConstants.PA3PS);
            PA3MK = jsonObject.getString(ItemConstants.PA3MK);
            PA3BD = jsonObject.getString(ItemConstants.PA3BD);
            PA3PY = jsonObject.getString(ItemConstants.PA3PY);
            PA3LOC = jsonObject.getString(ItemConstants.PA3LOC);
            PA3LOCN = jsonObject.getString(ItemConstants.PA3LOCN);
            PA3OUT = jsonObject.getString(ItemConstants.PA3OUT);
            PA3OUTN = jsonObject.getString(ItemConstants.PA3OUTN);
            PA3OU = jsonObject.getString(ItemConstants.PA3OU);
            PA3OUN = jsonObject.getString(ItemConstants.PA3OUN);
            PA3UUT = jsonObject.getString(ItemConstants.PA3UUT);
            PA3UUTN = jsonObject.getString(ItemConstants.PA3UUTN);
            PA3UR = jsonObject.getString(ItemConstants.PA3UR);
            PA3URN = jsonObject.getString(ItemConstants.PA3URN);
            PA308 = jsonObject.getString(ItemConstants.PA308);
            PA3DEL = jsonObject.getString(ItemConstants.PA3DEL);
            PA3PRN = jsonObject.getString(ItemConstants.PA3PRN);
            NAME = jsonObject.getString(ItemConstants.NAME);

            PA3INX = jsonObject.getString(ItemConstants.PA3INX);
            PA3C0 = jsonObject.getString(ItemConstants.PA3C0);
            PA3C7 = jsonObject.getString(ItemConstants.PA3C7);
            PA3QY = jsonObject.getString(ItemConstants.PA3QY);
            PA3UNP = jsonObject.getString(ItemConstants.PA3UNP);
            PA3TOP = jsonObject.getString(ItemConstants.PA3TOP);

            if (jsonObject.has(ItemConstants.PA3MEMO)){
                PA3MEMO = jsonObject.getString(ItemConstants.PA3MEMO);
            }
            if (jsonObject.has(ItemConstants.PA3MOC8)){
                PA3MOC8 = jsonObject.getString(ItemConstants.PA3MOC8);
            }
            if (jsonObject.has(ItemConstants.PA3MOB)){
                PA3MOB = jsonObject.getString(ItemConstants.PA3MOB);
            }
            if (jsonObject.has(ItemConstants.PA3MOD)){
                PA3MOD = jsonObject.getString(ItemConstants.PA3MOD).replace("\\","");;
            }
            if (jsonObject.has(ItemConstants.PA3MONO)){
                PA3MONO = jsonObject.getString(ItemConstants.PA3MONO);
            }

            if (jsonObject.has(ItemConstants.PA3DED)){
                PA3DED = jsonObject.getString(ItemConstants.PA3DED).replace("\\","");
            }
            if (jsonObject.has(ItemConstants.PA3DENO)){
                PA3DENO = jsonObject.getString(ItemConstants.PA3DENO);
            }
            if (jsonObject.has(ItemConstants.PA3TI)){
                PA3TI = jsonObject.getString(ItemConstants.PA3TI);
            }
            if (jsonObject.has(ItemConstants.PA3VWW)){
                PA3VWW = jsonObject.getString(ItemConstants.PA3VWW);
            }
            if (jsonObject.has(ItemConstants.PA3VN)){
                PA3VN = jsonObject.getString(ItemConstants.PA3VN);
            }
            if (jsonObject.has(ItemConstants.PA3DR)){
                PA3DR = jsonObject.getString(ItemConstants.PA3DR);
            }
            if (jsonObject.has(ItemConstants.PA3PD)){
                PA3PD = jsonObject.getString(ItemConstants.PA3PD);
            }
            if (jsonObject.has(ItemConstants.PA3AN)){
                PA3AN = jsonObject.getString(ItemConstants.PA3AN);
            }
            if (jsonObject.has(ItemConstants.PA3MOL)){
                PA3MOL = jsonObject.getString(ItemConstants.PA3MOL);
            }

            if (jsonObject.has(ItemConstants.PA8PD)){
                PA8PD = jsonObject.getString(ItemConstants.PA8PD);
            }
            if (jsonObject.has(ItemConstants.PA8A)){
                PA8A = jsonObject.getString(ItemConstants.PA8A);
            }
            if(jsonObject.has(ItemConstants.TYPE)){
                type = Type.valueOf(jsonObject.getString(ItemConstants.TYPE));
            }
            if(jsonObject.has(ItemConstants.PA3MB)){
                PA3MB = jsonObject.getString(ItemConstants.PA3MB);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public JSONObject toJSON() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(ItemConstants.PA3C1, PA3C1);
            jsonObject.put(ItemConstants.PA3C2, PA3C2);
            jsonObject.put(ItemConstants.PA3C3, PA3C3);
            jsonObject.put(ItemConstants.PA3C4, PA3C4);
            jsonObject.put(ItemConstants.PA3C5, PA3C5);
            jsonObject.put(ItemConstants.PA3C6, PA3C6);
            jsonObject.put(ItemConstants.PA3P3, PA3P3);
            jsonObject.put(ItemConstants.PA3PS, PA3PS);
            jsonObject.put(ItemConstants.PA3MK, PA3MK);
            jsonObject.put(ItemConstants.PA3BD, PA3BD);
            jsonObject.put(ItemConstants.PA3PY, PA3PY);
            jsonObject.put(ItemConstants.PA3LOC, PA3LOC);
            jsonObject.put(ItemConstants.PA3LOCN, PA3LOCN);
            jsonObject.put(ItemConstants.PA3OUT, PA3OUT);
            jsonObject.put(ItemConstants.PA3OUTN, PA3OUTN);
            jsonObject.put(ItemConstants.PA3OU, PA3OU);
            jsonObject.put(ItemConstants.PA3OUN, PA3OUN);
            jsonObject.put(ItemConstants.PA3UUT, PA3UUT);
            jsonObject.put(ItemConstants.PA3UUTN, PA3UUTN);
            jsonObject.put(ItemConstants.PA3UR, PA3UR);
            jsonObject.put(ItemConstants.PA3URN, PA3URN);
            jsonObject.put(ItemConstants.PA308, PA308);
            jsonObject.put(ItemConstants.PA3DEL, PA3DEL);
            jsonObject.put(ItemConstants.PA3PRN, PA3PRN);

            jsonObject.put(ItemConstants.PA3INX, PA3INX);
            if(type == Type.item){
                jsonObject.put(ItemConstants.PA3C0, PA3C0);
            }
            jsonObject.put(ItemConstants.PA3C7, PA3C7);
            jsonObject.put(ItemConstants.PA3QY, PA3QY);
            jsonObject.put(ItemConstants.PA3UNP, PA3UNP);
            jsonObject.put(ItemConstants.PA3TOP, PA3TOP);
            jsonObject.put(ItemConstants.PA3MEMO, PA3MEMO);
            jsonObject.put(ItemConstants.PA3MOC8, PA3MOC8);
            jsonObject.put(ItemConstants.PA3MOB, PA3MOB);
            jsonObject.put(ItemConstants.PA3MOD, PA3MOD.replace("\\",""));
            jsonObject.put(ItemConstants.PA3MONO, PA3MONO);
            jsonObject.put(ItemConstants.PA3DED, PA3DED.replace("\\",""));
            jsonObject.put(ItemConstants.PA3DENO, PA3DENO);
            jsonObject.put(ItemConstants.PA3TI, PA3TI);
            jsonObject.put(ItemConstants.PA3VWW, PA3VWW);
            jsonObject.put(ItemConstants.PA3VN, PA3VN);
            jsonObject.put(ItemConstants.PA3DR, PA3DR);
            jsonObject.put(ItemConstants.PA3PD, PA3PD);
            jsonObject.put(ItemConstants.PA3AN, PA3AN);
            jsonObject.put(ItemConstants.PA3MOL, PA3MOL);
            jsonObject.put(ItemConstants.PA8PD, PA8PD);
            jsonObject.put(ItemConstants.PA8A, PA8A);
            jsonObject.put(ItemConstants.PA3MB, PA3MB);


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public JSONObject toDbJSON() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(ItemConstants.PA3C1, PA3C1);
            jsonObject.put(ItemConstants.PA3C2, PA3C2);
            jsonObject.put(ItemConstants.PA3C3, PA3C3);
            jsonObject.put(ItemConstants.PA3C4, PA3C4);
            jsonObject.put(ItemConstants.PA3C5, PA3C5);
            jsonObject.put(ItemConstants.PA3C6, PA3C6);
            jsonObject.put(ItemConstants.PA3P3, PA3P3);
            jsonObject.put(ItemConstants.PA3PS, PA3PS);
            jsonObject.put(ItemConstants.PA3MK, PA3MK);
            jsonObject.put(ItemConstants.PA3BD, PA3BD);
            jsonObject.put(ItemConstants.PA3PY, PA3PY);
            jsonObject.put(ItemConstants.PA3LOC, PA3LOC);
            jsonObject.put(ItemConstants.PA3LOCN, PA3LOCN);
            jsonObject.put(ItemConstants.PA3OUT, PA3OUT);
            jsonObject.put(ItemConstants.PA3OUTN, PA3OUTN);
            jsonObject.put(ItemConstants.PA3OU, PA3OU);
            jsonObject.put(ItemConstants.PA3OUN, PA3OUN);
            jsonObject.put(ItemConstants.PA3UUT, PA3UUT);
            jsonObject.put(ItemConstants.PA3UUTN, PA3UUTN);
            jsonObject.put(ItemConstants.PA3UR, PA3UR);
            jsonObject.put(ItemConstants.PA3URN, PA3URN);
            jsonObject.put(ItemConstants.PA308, PA308);
            jsonObject.put(ItemConstants.PA3DEL, PA3DEL);
            jsonObject.put(ItemConstants.PA3PRN, PA3PRN);
            jsonObject.put(ItemConstants.NAME, NAME);

            jsonObject.put(ItemConstants.PA3INX, PA3INX);
            jsonObject.put(ItemConstants.PA3C0, PA3C0);
            jsonObject.put(ItemConstants.PA3C7, PA3C7);
            jsonObject.put(ItemConstants.PA3QY, PA3QY);
            jsonObject.put(ItemConstants.PA3UNP, PA3UNP);
            jsonObject.put(ItemConstants.PA3TOP, PA3TOP);
            jsonObject.put(ItemConstants.PA3MEMO, PA3MEMO);
            jsonObject.put(ItemConstants.PA3MOC8, PA3MOC8);
            jsonObject.put(ItemConstants.PA3MOB, PA3MOB);
            jsonObject.put(ItemConstants.PA3MOD, PA3MOD.replace("\\",""));
            jsonObject.put(ItemConstants.PA3MONO, PA3MONO);
            jsonObject.put(ItemConstants.PA3DED, PA3DED.replace("\\",""));
            jsonObject.put(ItemConstants.PA3DENO, PA3DENO);
            jsonObject.put(ItemConstants.PA3TI, PA3TI);
            jsonObject.put(ItemConstants.PA3VWW, PA3VWW);
            jsonObject.put(ItemConstants.PA3VN, PA3VN);
            jsonObject.put(ItemConstants.PA3DR, PA3DR);
            jsonObject.put(ItemConstants.PA3PD, PA3PD);
            jsonObject.put(ItemConstants.PA3AN, PA3AN);
            jsonObject.put(ItemConstants.PA3MOL, PA3MOL);
            jsonObject.put(ItemConstants.PA8PD, PA8PD);
            jsonObject.put(ItemConstants.PA8A, PA8A);
            jsonObject.put(ItemConstants.TYPE,type.toString());
            jsonObject.put(ItemConstants.PA3MB, PA3MB);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public String getPA3C1() {
        return PA3C1;
    }

    public String getNumber() {
        return PA3C0 + PA3C1 + PA3C2 + PA3C3 + PA3C4 + PA3C5;
    }

    public String getSerialNumber() {
        return PA3C6;
    }

    public String getIdNumber() {
        return getNumber() + "-" + getSerialNumber();
    }

    public String getTagIdNumber() {
        return (type == Type.item ? PA3C0 : "" ) + PA3C1 + PA3C2 + PA3C3 + PA3C4  + PA3C5 + " -" + getSerialNumber();
    }

    public String getBarcodeNumber() {
        return  getNumber()  + getSerialNumber();
    }

    public String getName() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public String getNickName() {
        return PA3P3;
    }

    public String getType() {
        return PA3PS;
    }

    public String getBrand() {
        return PA3MK;
    }

    public Date getDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        try {
            return sdf.parse(PA3BD);
        }catch (Exception e){
            return new Date();
        }
    }

    public String getPA3BD() {
        return PA3BD;
    }

    public String getYears() {
        return PA3PY;
    }


    public Agent getCustodian() {
        return new Agent(PA3OUN);
    }

    public void setCustodian(Agent agent) {
        PA3OUN = agent.name;
        setConfirm(true);
    }

    public Agent getUser() {
        return new Agent(PA3URN);
    }

    public void setUser(Agent agent) {
        PA3URN = agent.name;
        setConfirm(true);
    }

    public Department getCustodyGroup() {
        return new Department(PA3OUTN);
    }

    public void setCustodyGroup(Department department) {
        PA3OUTN = department.name;
        setConfirm(true);
    }

    public Department getUseGroup() {
        return new Department(PA3UUTN);
    }

    public void setUseGroup(Department department) {
        PA3UUTN = department.name;
        setConfirm(true);
    }

    public Location getLocation() {
        return new Location(PA3LOCN);
    }

    public void setLocation(Location location) {
        PA3LOCN = location.name;
        setConfirm(true);
    }

    public boolean isConfirm() {
        return PA308.equals("Y");
    }

    public void setConfirm(boolean flag) {
        if (flag) {
            PA308 = "Y";
            ItemSingleton.getInstance().saveItem(this);
        } else {
            PA308 = "N";
        }
    }

    public boolean isDelete() {
        return PA3DEL.equals("Y");
    }

    public void setDelete(boolean flag) {
        if (flag) {
            PA3DEL = "Y";
        } else {
            PA3DEL = "N";
        }
    }

    public void setDelete(String key) {
        PA3DEL = key;
        setConfirm(true);
    }

    public boolean isPrint() {
        return PA3PRN.equals("Y");
    }

    public void setPrint(boolean flag) {
        if (flag) {
            PA3PRN = "Y";
        } else {
            PA3PRN = "N";
        }
    }

    public void setPrint(String key) {
        PA3PRN = key;
        setConfirm(true);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
    public String getLittleTagContentString() {
        String ans = "  ";
        ans += KeyConstants.LittleAuthorityName + (type == Type.item ? KeyConstants.LittleItemName : "") + "\n";
        ans += "  " + getTagIdNumber() + "\n";
        ans += "  " + getName() + "\n";
        ans += "  " + ADtoCal() + "  年限:" + getYears() +"\n";
        ans += "  "+ getCustodian().getName() + "/" + getLocation().getName() ;
        return ans;
    }
    public String getTagContentString() {
        String ans = "  ";
        ans += KeyConstants.AuthorityName + (type == Type.item ? KeyConstants.ItemName : "") + "\n";
        ans += "  編號：" + getTagIdNumber() + "\n";
        ans += "  名稱：" + getName() + "\n";
        ans += "  日期：" + ADtoCal() + "  年限：" + getYears() + "  金額："+getPA3TOP()+"\n";
        if (tagContent != null) {
            ans += "  " + tagContent.getName() + "：";
            switch (tagContent) {
                case Agent:
                    ans += getCustodian().getName() + "\n";
                    break;
                case AgentGroup:
                    ans += getCustodian().getName() + "/" + getCustodyGroup().getName() + "\n";
                    break;
                case AgentLocation:
                    ans += getCustodian().getName() + "/" + getLocation().getName() + "\n";
                    break;
                case AgentGroupLocation:
                    ans += getCustodian().getName() + "/" + getCustodyGroup().getName() + "/" + getLocation().getName() + "\n";
                    break;
            }
        }

        ans += "  廠牌/型式：" + getBrand() + "/" + getType() + "\n";
        ans += "  經費：" + getPA3P3() ;
        return ans;
    }

    public String ADtoCal() {
        Calendar c = Calendar.getInstance();
        c.setTime(getDate());
        int y = c.get(Calendar.YEAR) - 1911;
        int m = c.get(Calendar.MONTH) + 1;
        int d = c.get(Calendar.DAY_OF_MONTH);
        return String.format("%d/%02d/%02d", y, m, d);
    }

    public String getScrappedADtoCal() {
        Calendar c = Calendar.getInstance();
        c.setTime(getDate());
        int y = c.get(Calendar.YEAR) - 1911 + Integer.valueOf(PA3PY);
        int m = c.get(Calendar.MONTH) + 1;
        int d = c.get(Calendar.DAY_OF_MONTH);
        return String.format("%d/%02d/%02d", y, m, d);
    }

    public String getPA3C2() {
        return PA3C2;
    }

    public String getPA3C3() {
        return PA3C3;
    }

    public String getPA3C4() {
        return PA3C4;
    }

    public String getPA3C5() {
        return PA3C5;
    }

    public String getPA3C6() {
        return PA3C6;
    }

    public String getPA3P3() {
        return PA3P3;
    }

    public String getPA3PS() {
        return PA3PS;
    }

    public String getPA3MK() {
        return PA3MK;
    }

    public String getPA3PY() {
        return PA3PY;
    }

    public String getPA3LOC() {
        return PA3LOC;
    }

    public String getPA3LOCN() {
        return PA3LOCN;
    }

    public String getPA3OUT() {
        return PA3OUT;
    }

    public String getPA3OUTN() {
        return PA3OUTN;
    }

    public String getPA3OU() {
        return PA3OU;
    }

    public String getPA3OUN() {
        return PA3OUN;
    }

    public String getPA3UUT() {
        return PA3UUT;
    }

    public String getPA3UUTN() {
        return PA3UUTN;
    }

    public String getPA3UR() {
        return PA3UR;
    }

    public String getPA3URN() {
        return PA3URN;
    }

    public String getPA308() {
        return PA308;
    }

    public String getPA3DEL() {
        return PA3DEL;
    }

    public String getPA3PRN() {
        return PA3PRN;
    }

    public String getNAME() {
        return NAME;
    }

    public String getPA3INX() {
        return PA3INX;
    }

    public String getPA3C0() {
        return PA3C0;
    }

    public String getPA3C7() {
        return PA3C7;
    }

    public String getPA3QY() {
        return PA3QY;
    }

    public String getPA3UNP() {
        return PA3UNP;
    }

    public String getPA3TOP() {
        return PA3TOP;
    }

    public String getPA3MEMO() {
        return PA3MEMO;
    }

    public String getPA3MOC8() {
        return PA3MOC8;
    }

    public String getPA3MOB() {
        return PA3MOB;
    }

    public String getPA3MOD() {
        return PA3MOD;
    }

    public String getPA3MONO() {
        return PA3MONO;
    }

    public String getPA3DED() {
        return PA3DED;
    }

    public String getPA3DENO() {
        return PA3DENO;
    }

    public String getPA3TI() {
        return PA3TI;
    }

    public String getPA3VWW() {
        return PA3VWW;
    }

    public String getPA3VN() {
        return PA3VN;
    }

    public String getPA3DR() {
        return PA3DR;
    }

    public String getPA3PD() {
        return PA3PD;
    }

    public String getPA3AN() {
        return PA3AN;
    }

    public String getPA3MOL() {
        return PA3MOL;
    }

    public String getPA3MB() {
        return PA3MB;
    }

    public void setPA3MB(String PA3MB) {
        this.PA3MB = PA3MB;
    }

    public void setPA3C1(String PA3C1) {
        this.PA3C1 = PA3C1;
    }

    public void setPA3C2(String PA3C2) {
        this.PA3C2 = PA3C2;
    }

    public void setPA3C3(String PA3C3) {
        this.PA3C3 = PA3C3;
    }

    public void setPA3C4(String PA3C4) {
        this.PA3C4 = PA3C4;
    }

    public void setPA3C5(String PA3C5) {
        this.PA3C5 = PA3C5;
    }

    public void setPA3C6(String PA3C6) {
        this.PA3C6 = PA3C6;
    }

    public void setPA3P3(String PA3P3) {
        this.PA3P3 = PA3P3;
    }

    public void setPA3PS(String PA3PS) {
        this.PA3PS = PA3PS;
    }

    public void setPA3MK(String PA3MK) {
        this.PA3MK = PA3MK;
    }

    public void setPA3BD(String PA3BD) {
        this.PA3BD = PA3BD;
    }

    public void setPA3PY(String PA3PY) {
        this.PA3PY = PA3PY;
    }

    public void setPA3LOC(String PA3LOC) {
        this.PA3LOC = PA3LOC;
    }

    public void setPA3LOCN(String PA3LOCN) {
        this.PA3LOCN = PA3LOCN;
    }

    public void setPA3OUT(String PA3OUT) {
        this.PA3OUT = PA3OUT;
    }

    public void setPA3OUTN(String PA3OUTN) {
        this.PA3OUTN = PA3OUTN;
    }

    public void setPA3OU(String PA3OU) {
        this.PA3OU = PA3OU;
    }

    public void setPA3OUN(String PA3OUN) {
        this.PA3OUN = PA3OUN;
    }

    public void setPA3UUT(String PA3UUT) {
        this.PA3UUT = PA3UUT;
    }

    public void setPA3UUTN(String PA3UUTN) {
        this.PA3UUTN = PA3UUTN;
    }

    public void setPA3UR(String PA3UR) {
        this.PA3UR = PA3UR;
    }

    public void setPA3URN(String PA3URN) {
        this.PA3URN = PA3URN;
    }

    public void setPA308(String PA308) {
        this.PA308 = PA308;
    }

    public void setPA3DEL(String PA3DEL) {
        this.PA3DEL = PA3DEL;
    }

    public void setPA3PRN(String PA3PRN) {
        this.PA3PRN = PA3PRN;
    }

    public void setPA3INX(String PA3INX) {
        this.PA3INX = PA3INX;
    }

    public void setPA3C0(String PA3C0) {
        this.PA3C0 = PA3C0;
    }

    public void setPA3C7(String PA3C7) {
        this.PA3C7 = PA3C7;
    }

    public void setPA3QY(String PA3QY) {
        this.PA3QY = PA3QY;
    }

    public void setPA3UNP(String PA3UNP) {
        this.PA3UNP = PA3UNP;
    }

    public void setPA3TOP(String PA3TOP) {
        this.PA3TOP = PA3TOP;
    }

    public void setPA3MEMO(String PA3MEMO) {
        this.PA3MEMO = PA3MEMO;
    }

    public void setPA3MOC8(String PA3MOC8) {
        this.PA3MOC8 = PA3MOC8;
    }

    public void setPA3MOB(String PA3MOB) {
        this.PA3MOB = PA3MOB;
    }

    public void setPA3MOD(String PA3MOD) {
        this.PA3MOD = PA3MOD.replace("\\","");
    }

    public void setPA3MONO(String PA3MONO) {
        this.PA3MONO = PA3MONO;
    }

    public void setPA3DED(String PA3DED) {
        this.PA3DED = PA3DED.replace("\\","");;
    }

    public void setPA3DENO(String PA3DENO) {
        this.PA3DENO = PA3DENO;
    }

    public void setPA3TI(String PA3TI) {
        this.PA3TI = PA3TI;
    }

    public void setPA3VWW(String PA3VWW) {
        this.PA3VWW = PA3VWW;
    }

    public void setPA3VN(String PA3VN) {
        this.PA3VN = PA3VN;
    }

    public void setPA3DR(String PA3DR) {
        this.PA3DR = PA3DR;
    }

    public void setPA3PD(String PA3PD) {
        this.PA3PD = PA3PD;
    }

    public String getPA8PD() {
        return PA8PD;
    }

    public void setPA8PD(String PA8PD) {
        this.PA8PD = PA8PD;
    }

    public String getPA8A() {
        return PA8A;
    }

    public void setPA8A(String PA8A) {
        this.PA8A = PA8A;
    }

    public void setPA3AN(String PA3AN) {
        this.PA3AN = PA3AN;
    }

    public void setPA3MOL(String PA3MOL) {
        this.PA3MOL = PA3MOL;
    }

    public void setTagContent(TagContent tagContent) {
        this.tagContent = tagContent;
    }

    public TagContent getTagContent() {
        return tagContent;
    }

    public void setType(Type t){
        this.type = t;
    }
}
