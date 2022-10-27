package com.ecmaker.shiftapp.working;

import java.util.List;
import java.util.Vector;

public class ItemsModel_PersonMonth {
    private List<Vector<Object>> list;

    public ItemsModel_PersonMonth(List<Vector<Object>> list){
        this.list = list;
    }

    public List<Vector<Object>> getList(){
        return list;
    }

    public void setList(List<Vector<Object>> list){
        this.list = list;
    }

}
