package com.ecmaker.shiftapp.partner;

import java.util.HashMap;

public class ItemsModel_Vacation {
    private HashMap<String,Object> hashMap;

    public ItemsModel_Vacation(HashMap<String,Object> hashMap) {
        this.hashMap = hashMap;
    }

    public String getType() {
        String type = (String)hashMap.get("type");
        if(type == null){
            type = "";
        }
        return type;
    }

    public String getStartDate() {
        String startDate = (String)hashMap.get("startDate");
        if(startDate == null){
            startDate = "";
        }
        return startDate;
    }

    public String getEndDate() {
        String endDate = (String)hashMap.get("endDate");
        if(endDate == null){
            endDate = "";
        }
        return endDate;
    }

    public String getDays() {
        String days = (String)hashMap.get("days");
        if(days == null){
            days = "";
        }
        return days;
    }

    public String getReason() {
        String reason = (String)hashMap.get("reason");
        if(reason == null){
            reason = "";
        }
        return reason;
    }

    public String getAgent() {
        String agent = (String)hashMap.get("agent");
        if(agent == null){
            agent = "";
        }
        return agent;
    }

    public boolean getIsOpen() {

        boolean isOpen = false;
        if(hashMap.containsKey("isOpen")){
            isOpen = (boolean) hashMap.get("isOpen");
        }

        return isOpen;
    }

    public void setIsOpen(boolean isOpen) {
        hashMap.put("isOpen",isOpen);
    }
}
