package com.ecmaker.shiftapp.working;

import java.util.HashMap;

public class ItemsModel_Customize {
    private HashMap<String,Object> hashMap;

    public ItemsModel_Customize(HashMap<String,Object> hashMap) {
        this.hashMap = hashMap;
    }

    public HashMap<String,Object> getList() {
        return hashMap;
    }

    public void setList(HashMap<String,Object> hashMap) {
        this.hashMap = hashMap;
    }

    public void setWorking(String working) {
        hashMap.put("working",working);
    }

    public String getWorking() {
        String working = (String)hashMap.get("working");
        if(working == null){
            working = "";
        }
        return working;
    }

    public String getVIEW_TYPE() {
        String VIEW_TYPE = (String)hashMap.get("VIEW_TYPE");
        if(VIEW_TYPE == null){
            VIEW_TYPE = "";
        }
        return VIEW_TYPE;
    }
}
