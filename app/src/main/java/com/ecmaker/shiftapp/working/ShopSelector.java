package com.ecmaker.shiftapp.working;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.ecmaker.shiftapp.MainActivity;
import com.ecmaker.shiftapp.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ShopSelector {
    public ShopSelector(int x, int y, MainActivity mainActivity, View root){
        PopupWindow popupWindow = new PopupWindow(mainActivity);
        View popupView = LayoutInflater.from(mainActivity).inflate(R.layout.shop_selector_popupwindow,null);
        RecyclerView recyclerView = popupView.findViewById(R.id.recyclerview);
        List list = new ArrayList();


        HashMap hm = new HashMap();
        hm.put("isCheck",false);
        hm.put("content","台北");
        hm.put("code","");
        hm.put("group","1");
        hm.put("isParent",true);
        hm.put("isEnd",false);
        ItemsModel_ShopSelector model = new ItemsModel_ShopSelector(hm);
        list.add(model);
        HashMap hm1 = new HashMap();
        hm1.put("isCheck",false);
        hm1.put("content","忠孝店");
        hm1.put("code","c01");
        hm1.put("group","1");
        hm1.put("isParent",false);
        hm1.put("isEnd",true);
        ItemsModel_ShopSelector model1 = new ItemsModel_ShopSelector(hm1);
        list.add(model1);
        HashMap hm2 = new HashMap();
        hm2.put("isCheck",false);
        hm2.put("content","新北");
        hm2.put("code","");
        hm2.put("group","2");
        hm2.put("isParent",true);
        hm2.put("isEnd",false);
        ItemsModel_ShopSelector model2 = new ItemsModel_ShopSelector(hm2);
        list.add(model2);
        HashMap hm3 = new HashMap();
        hm3.put("isCheck",false);
        hm3.put("content","中和店");
        hm3.put("code","d01");
        hm3.put("group","2");
        hm3.put("isParent",false);
        hm3.put("isEnd",false);
        ItemsModel_ShopSelector model3 = new ItemsModel_ShopSelector(hm3);
        list.add(model3);
        HashMap hm4 = new HashMap();
        hm4.put("isCheck",false);
        hm4.put("content","永和店");
        hm4.put("code","d02");
        hm4.put("group","2");
        hm4.put("isParent",false);
        hm4.put("isEnd",true);
        ItemsModel_ShopSelector model4 = new ItemsModel_ShopSelector(hm4);
        list.add(model4);
        ShopSelectorAdapter shopSelectorAdapter = new ShopSelectorAdapter(list,mainActivity,root,popupWindow);

        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setAdapter(shopSelectorAdapter);

        popupWindow.setContentView(popupView);
        popupWindow.setBackgroundDrawable(null);
        popupWindow.setFocusable(true);
        popupWindow.showAtLocation(popupView, Gravity.NO_GRAVITY,x,y);
    }

    public interface OnLabelClickListener {
        /**
         * 点击label
         */
        void onClick(String year,String month);
    }
    private ShopSelector.OnLabelClickListener onLabelClickListener;
    public void setOnLabelClickListener(ShopSelector.OnLabelClickListener onLabelClickListener) {
        this.onLabelClickListener = onLabelClickListener;
    }
}
