package com.aidan.inventoryworkplatform.Entity;

import org.json.JSONObject;

/**
 * Created by Aidan on 2016/10/24.
 */

public class Item {
    String PA341 = "PA341";
    String PA342 = "PA342";
    String PA343 = "PA343";
    String PA3C1 = "PA3C1";
    String PA3C2 = "PA3C2";
    String PA3C3 = "PA3C3";
    String PA3C4 = "PA3C4";
    String PA3C5 = "PA3C5";
    String PA3C6 = "PA3C6";
    String PA3P3 = "PA3P3";
    String PA3PS = "PA3PS";
    String PA3MK = "PA3MK";
    String PA3BD = "PA3BD";
    String PA3PY = "PA3PY";
    String PA3LOC = "PA3LOC";
    String PA3LOCN = "PA3LOCN";
    String PA3OUT = "PA3OUT";
    String PA3OUTN = "PA3OUTN";
    String PA3OU = "PA3OU";
    String PA3OUN = "PA3OUN";
    String PA3UUT = "PA3UUT";
    String PA3UUTN = "PA3UUTN";
    String PA3UR = "PA3UR";
    String PA3URN = "PA3URN";
    String PA308 = "PA308";
    String PA3DEL = "PA3DEL";
    String PA3PRN = "PA3PRN";
    public Item(JSONObject jsonObject){
        try {
            PA341 = jsonObject.getString(ItemConstans.PA341);
            PA342 = jsonObject.getString(ItemConstans.PA342);
            PA343 = jsonObject.getString(ItemConstans.PA343);
            PA3C1 = jsonObject.getString(ItemConstans.PA3C1);
            PA3C2 = jsonObject.getString(ItemConstans.PA3C2);
            PA3C3 = jsonObject.getString(ItemConstans.PA3C3);
            PA3C4 = jsonObject.getString(ItemConstans.PA3C4);
            PA3C5 = jsonObject.getString(ItemConstans.PA3C5);
            PA3C6 = jsonObject.getString(ItemConstans.PA3C6);
            PA3P3 = jsonObject.getString(ItemConstans.PA3P3);
            PA3PS = jsonObject.getString(ItemConstans.PA3PS);
            PA3MK = jsonObject.getString(ItemConstans.PA3MK);
            PA3BD = jsonObject.getString(ItemConstans.PA3BD);
            PA3PY = jsonObject.getString(ItemConstans.PA3PY);
            PA3LOC = jsonObject.getString(ItemConstans.PA3LOC);
            PA3LOCN = jsonObject.getString(ItemConstans.PA3LOCN);
            PA3OUT = jsonObject.getString(ItemConstans.PA3OUT);
            PA3OUTN = jsonObject.getString(ItemConstans.PA3OUTN);
            PA3OU = jsonObject.getString(ItemConstans.PA3OU);
            PA3OUN = jsonObject.getString(ItemConstans.PA3OUN);
            PA3UUT = jsonObject.getString(ItemConstans.PA3UUT);
            PA3UUTN = jsonObject.getString(ItemConstans.PA3UUTN);
            PA3UR = jsonObject.getString(ItemConstans.PA3UR);
            PA3URN = jsonObject.getString(ItemConstans.PA3URN);
            PA308 = jsonObject.getString(ItemConstans.PA308);
            PA3DEL = jsonObject.getString(ItemConstans.PA3DEL);
            PA3PRN = jsonObject.getString(ItemConstans.PA3PRN);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public String getLoaclNumber(){
        return PA341+PA342+PA343;
    }
    public String getNumber(){
        return PA3C1+PA3C2+PA3C3+PA3C4+PA3C5;
    }
    public String getSerialNumber(){
        return PA3C6;
    }
}
