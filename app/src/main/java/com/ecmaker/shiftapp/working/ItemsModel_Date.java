package com.ecmaker.shiftapp.working;

import java.util.HashMap;

public class ItemsModel_Date {
    private HashMap<String,Object> hashMap;

    public ItemsModel_Date(HashMap<String,Object> hashMap) {
        this.hashMap = hashMap;
    }

    public String getWeek() {
        String week = (String)hashMap.get("week");
        if(week == null){
            week = "";
        }
        return week;
    }

    public String getDay() {
        String day = (String)hashMap.get("day");
        if(day == null){
            day = "";
        }
        return day;
    }
}
