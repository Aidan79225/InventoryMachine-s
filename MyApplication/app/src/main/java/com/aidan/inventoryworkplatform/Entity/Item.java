package com.aidan.inventoryworkplatform.Entity;

import org.json.JSONObject;

/**
 * Created by Aidan on 2016/10/24.
 */

public class Item {
    private String PA341 = "PA341";
    private String PA342 = "PA342";
    private String PA343 = "PA343";
    private String PA3C1 = "PA3C1";
    private String PA3C2 = "PA3C2";
    private String PA3C3 = "PA3C3";
    private String PA3C4 = "PA3C4";
    private String PA3C5 = "PA3C5";
    private String PA3C6 = "PA3C6";
    private String PA3P3 = "PA3P3";
    private String PA3PS = "PA3PS";
    private String PA3MK = "PA3MK";
    private String PA3BD = "PA3BD";
    private String PA3PY = "PA3PY";
    private String PA3LOC = "PA3LOC";
    private String PA3LOCN = "PA3LOCN";
    private String PA3OUT = "PA3OUT";
    private String PA3OUTN = "PA3OUTN";
    private String PA3OU = "PA3OU";
    private String PA3OUN = "PA3OUN";
    private String PA3UUT = "PA3UUT";
    private String PA3UUTN = "PA3UUTN";
    private String PA3UR = "PA3UR";
    private String PA3URN = "PA3URN";
    private String PA308 = "PA308";
    private String PA3DEL = "PA3DEL";
    private String PA3PRN = "PA3PRN";

    public Item(JSONObject jsonObject) {
        try {
            PA341 = jsonObject.getString(ItemConstants.PA341);
            PA342 = jsonObject.getString(ItemConstants.PA342);
            PA343 = jsonObject.getString(ItemConstants.PA343);
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getLoaclNumber() {
        return PA341 + PA342 + PA343;
    }

    public String getNumber() {
        return PA3C1 + PA3C2 + PA3C3 + PA3C4 + PA3C5;
    }

    public String getSerialNumber() {
        return PA3C6;
    }
    public String getName(){
        return PA3P3;
    }
    public String getTypr(){
        return PA3PS;
    }
    public String getBrand(){
        return PA3MK;
    }
    public String getDate(){
        return PA3BD;
    }
    public String getYears(){
        return PA3PY;
    }
    public Agent getCustodian(){
        return new Agent(PA3OUN,PA3OU);
    }
    public Agent getUser(){
        return new Agent(PA3URN,PA3UR);
    }
    public Department getCustodyGroup(){
        return new Department(PA3OUTN,PA3OUT);
    }
    public Department getUseGroup(){
        return new Department(PA3UUTN,PA3UUT);
    }
    public Location getLocation(){
        return new Location(PA3LOCN,PA3LOC);
    }
    public boolean isConfirm(){
        return PA308.equals("Y");
    }
    public boolean isDelete(){
        return PA3DEL.equals("Y");
    }
    public boolean isPrint(){
        return PA3PRN.equals("Y");
    }

}
