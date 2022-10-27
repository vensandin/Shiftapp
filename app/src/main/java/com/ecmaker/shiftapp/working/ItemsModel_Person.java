package com.ecmaker.shiftapp.working;

import java.util.HashMap;

public class ItemsModel_Person {
    private HashMap<String,Object> hashMap;

    public ItemsModel_Person(HashMap<String,Object> hashMap) {
        this.hashMap = hashMap;
    }

    public HashMap<String,Object> getList() {
        return hashMap;
    }

    public void setList(HashMap<String,Object> hashMap) {
        this.hashMap = hashMap;
    }

    public void setCheck(boolean check) {
        hashMap.put("check",check);
    }

    public boolean getCheck() {
        boolean check = (boolean)hashMap.get("check");

        return check;
    }

    public String getName() {
        String name = (String)hashMap.get("name");
        if(name == null){
            name = "";
        }
        return name;
    }

    public String getPositiont() {
        String position = (String)hashMap.get("position");
        if(position == null){
            position = "";
        }
        return position;
    }

    public String getHours() {
        String hours = (String)hashMap.get("hours");
        if(hours == null){
            hours = "";
        }
        return hours;
    }

    public String getVIEW_TYPE() {
        String VIEW_TYPE = (String)hashMap.get("VIEW_TYPE");
        if(VIEW_TYPE == null){
            VIEW_TYPE = "";
        }
        return VIEW_TYPE;
    }
}
