package com.ecmaker.shiftapp.partner;

import java.util.HashMap;

public class ItemsModel_Travel {
    private HashMap<String,Object> hashMap;

    public ItemsModel_Travel(HashMap<String,Object> hashMap) {
        this.hashMap = hashMap;
    }

    public String getNumber() {
        String number = (String)hashMap.get("number");
        if(number == null){
            number = "";
        }
        return number;
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

    public String getTime() {
        String time = (String)hashMap.get("time");
        if(time == null){
            time = "";
        }
        return time;
    }

    public String getPlace() {
        String place = (String)hashMap.get("place");
        if(place == null){
            place = "";
        }
        return place;
    }

    public String getEvent() {
        String event = (String)hashMap.get("event");
        if(event == null){
            event = "";
        }
        return event;
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

    public String getNote() {
        String note = (String)hashMap.get("note");
        if(note == null){
            note = "";
        }
        return note;
    }
}
