package com.ecmaker.shiftapp.working;

import java.util.HashMap;

public class ItemsModel_Working {
    private HashMap<String,Object> hashMap;

    public ItemsModel_Working(HashMap<String,Object> hashMap) {
        this.hashMap = hashMap;
    }

    public String getKey() {
        String key = (String)hashMap.get("key");
        if(key == null){
            key = "";
        }
        return key;
    }

    public String getWorking() {
        String working = (String)hashMap.get("working");
        if(working == null){
            working = "";
        }
        return working;
    }
}
