package com.ecmaker.shiftapp.working;

import java.util.HashMap;

public class ItemsModel_ShopSelector {
    private HashMap<String,Object> hashMap;

    public ItemsModel_ShopSelector(HashMap<String,Object> hashMap) {
        this.hashMap = hashMap;
    }

    public void setCheck(boolean check) {
        hashMap.put("isCheck",check);
    }

    public boolean getCheck() {
        boolean check = false;
        if(hashMap.containsKey("isCheck")){
            check = (boolean)hashMap.get("isCheck");
        }
        return check;
    }

    public String getCode() {
        String code = (String)hashMap.get("code");
        if(code == null){
            code = "";
        }
        return code;
    }

    public String getGroup() {
        String group = (String)hashMap.get("group");
        if(group == null){
            group = "";
        }
        return group;
    }

    public String getContent() {
        String content = (String)hashMap.get("content");
        if(content == null){
            content = "";
        }
        return content;
    }

    public boolean getEnd() {
        boolean check = false;
        if(hashMap.containsKey("isEnd")){
            check = (boolean)hashMap.get("isEnd");
        }
        return check;
    }
}
