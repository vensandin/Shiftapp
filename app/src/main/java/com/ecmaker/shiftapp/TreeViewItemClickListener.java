package com.ecmaker.shiftapp;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;

import androidx.fragment.app.FragmentTransaction;

import com.ecmaker.shiftapp.partner.PartnerFragment;
import com.ecmaker.shiftapp.report.ReportFragment;
import com.ecmaker.shiftapp.working.WorkingFragment;

import java.util.ArrayList;

public class TreeViewItemClickListener implements AdapterView.OnItemClickListener {
    /** adapter */
    private TreeViewAdapter treeViewAdapter;
    private MainActivity mainActivity;
    private WorkingFragment workingFragment = new WorkingFragment();

    public TreeViewItemClickListener(TreeViewAdapter treeViewAdapter, MainActivity mainActivity) {
        this.treeViewAdapter = treeViewAdapter;
        this.mainActivity = mainActivity;
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
//點選的item代表的元素
        Element element = (Element) treeViewAdapter.getItem(position);
//樹中的元素
        ArrayList<Element> elements = treeViewAdapter.getElements();
//元素的資料來源
        ArrayList<Element> elementsData = treeViewAdapter.getElementsData();

        Log.e("element.getId","==>"+element.getId());
        if(element.getId() == 0) {
            Log.e("排班表","排班表");
            WorkingFragment workingFragment = new WorkingFragment();
            FragmentTransaction ft = mainActivity.getSupportFragmentManager().beginTransaction();
            //ft.hide(MainActivity.currentFragment);
            ft.replace(R.id.fragmentView, workingFragment, workingFragment.getClass().getName());
            ft.commit();
            //MainActivity.currentFragment = workingFragment;
        }else if(element.getId() == 1){
            Log.e("夥伴管理","夥伴管理");
            PartnerFragment partnerFragment = new PartnerFragment();
            FragmentTransaction ft = mainActivity.getSupportFragmentManager().beginTransaction();
            //ft.hide(MainActivity.currentFragment);
            ft.replace(R.id.fragmentView,partnerFragment,partnerFragment.getClass().getName());
            ft.commit();
            //MainActivity.currentFragment = partnerFragment;
        }else if(element.getId() == 2){
            Log.e("統計表表","統計表表");
            ReportFragment reportFragment = new ReportFragment();
            FragmentTransaction ft = mainActivity.getSupportFragmentManager().beginTransaction();
            //ft.hide(MainActivity.currentFragment);
            ft.replace(R.id.fragmentView,reportFragment,reportFragment.getClass().getName());
            ft.commit();
        }else if(element.getId() == 3){
            Log.e("設定","設定");
        }else if(element.getId() == 11){
            Log.e("登出","登出");
        }

        if(!MainActivity.isExpand){
            return;
        }
//點選沒有子項的item直接返回
        if (!element.isHasChildren()) {
            return;
        }
        if (element.isExpanded()) {
            element.setExpanded(false);
//刪除節點內部對應子節點資料，包括子節點的子節點...
            ArrayList<Element> elementsToDel = new ArrayList<Element>();
            for (int i = position+1; i < elements.size(); i++) {
                if (element.getLevel() >= elements.get(i).getLevel()) {
                    break;
                }
                elementsToDel.add(elements.get(i));
            }
            elements.removeAll(elementsToDel);
            //縮減Layout寬度
            FrameLayout frameLayout = mainActivity.findViewById(R.id.framelayout);
            int valueInPixels = (int) mainActivity.getResources().getDimension(R.dimen.my_value);
            frameLayout.getLayoutParams().width = valueInPixels;
            frameLayout.requestLayout();
            treeViewAdapter.notifyDataSetChanged();
        } else {
            element.setExpanded(true);
//從資料來源中提取子節點資料新增進樹，注意這裡只是新增了下一級子節點，為了簡化邏輯
            int i = 1;//注意這裡的計數器放在for外面才能保證計數有效
            for (Element e : elementsData) {
                if (e.getParendId() == element.getId()) {
                    //增加Layout寬度
                    if(e.getParendId() == 4 || e.getParendId() == 8) {
                        FrameLayout frameLayout = mainActivity.findViewById(R.id.framelayout);
                        int valueInPixels = (int) mainActivity.getResources().getDimension(R.dimen.my_value2);
                        frameLayout.getLayoutParams().width = valueInPixels;
                        frameLayout.requestLayout();
                    }
                    e.setExpanded(false);
                    elements.add(position+i, e);
                    i++;
                }
            }
            treeViewAdapter.notifyDataSetChanged();
        }
    }
}