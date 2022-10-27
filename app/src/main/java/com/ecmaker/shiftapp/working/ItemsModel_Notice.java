package com.ecmaker.shiftapp.working;

import java.util.HashMap;

public class ItemsModel_Notice {
    private HashMap<String,Object> hashMap;

    public ItemsModel_Notice(HashMap<String,Object> hashMap) {
        this.hashMap = hashMap;
    }

    public String getCircle() {
        String circle = (String)hashMap.get("circle");
        if(circle == null){
            circle = "";
        }
        return circle;
    }

    public String getContent() {
        String content = (String)hashMap.get("content");
        if(content == null){
            content = "";
        }
        return content;
    }

    public String getDate() {
        String date = (String)hashMap.get("date");
        if(date == null){
            date = "";
        }
        return date;
    }
}
