package com.ecmaker.shiftapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.slidingpanelayout.widget.SlidingPaneLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.ecmaker.shiftapp.working.WorkingFragment;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private Context mContext;
    private ListView lol_hero_list;
    private SlidingPaneLayout slidingPaneLayout;
    private LinearLayout large_layout;
    private LinearLayout mini_layout;
    private FrameLayout frameLayout;
    static boolean isExpand = true;
    static Fragment currentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = MainActivity.this;
        lol_hero_list = (ListView) findViewById(R.id.lol_hero_list);
        slidingPaneLayout = findViewById(R.id.sliding_pane_layout);
        large_layout = findViewById(R.id.large_layout);
        mini_layout = findViewById(R.id.mini_layout);
        frameLayout = findViewById(R.id.framelayout);

        //樹狀圖
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        init();
        TreeViewAdapter treeViewAdapter = new TreeViewAdapter(
                elements, elementsData, inflater);
        lol_hero_list.setAdapter(treeViewAdapter);
        TreeViewItemClickListener treeViewItemClickListener = new TreeViewItemClickListener(treeViewAdapter,MainActivity.this);
        lol_hero_list.setOnItemClickListener(treeViewItemClickListener);

        //側邊攔縮放
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        float width = displayMetrics.widthPixels;
        int miniSize = (int) (width/19);
        int valueInPixels = (int) getResources().getDimension(R.dimen.my_value);

        frameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getTotalHeightofListView(lol_hero_list,treeViewAdapter);
                Log.e("isExpand","==>"+isExpand);
                if(isExpand) {
                    large_layout.animate().alpha(0).start();
                    mini_layout.animate().alpha(1).start();
                    frameLayout.getLayoutParams().width = miniSize;
                    frameLayout.requestLayout();
                    isExpand = false;
                }else{
                    mini_layout.animate().alpha(0).start();
                    large_layout.animate().alpha(1).start();
                    frameLayout.getLayoutParams().width = valueInPixels;
                    frameLayout.requestLayout();
                    isExpand = true;
                }

            }
        });


        //主畫面顯示
        FragmentManager fragmentManager = getSupportFragmentManager();
        WorkingFragment workingFragment = new WorkingFragment();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragmentView, workingFragment, workingFragment.getClass().getName());
        fragmentTransaction.commit();
        //currentFragment = workingFragment;
    }

    private void setListViewHeight(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }


    private void setListViewHeight(ExpandableListView listView,
                                   int group) {
        ExpandableListAdapter listAdapter = (ExpandableListAdapter) listView.getExpandableListAdapter();
        int totalHeight = 0;
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(),
                View.MeasureSpec.EXACTLY);
        for (int i = 0; i < listAdapter.getGroupCount(); i++) {
            View groupItem = listAdapter.getGroupView(i, false, null, listView);
            groupItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);

            totalHeight += groupItem.getMeasuredHeight();

            if (((listView.isGroupExpanded(i)) && (i != group))
                    || ((!listView.isGroupExpanded(i)) && (i == group))) {
                for (int j = 0; j < listAdapter.getChildrenCount(i); j++) {
                    View listItem = listAdapter.getChildView(i, j, false, null,
                            listView);
                    listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);

                    totalHeight += listItem.getMeasuredHeight();

                }
            }
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        int height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getGroupCount() - 1));
        if (height < 10)
            height = 200;
        params.height = height;
        listView.setLayoutParams(params);
        listView.requestLayout();

    }

    private int getTotalHeightofListView(ListView lv, Adapter mAdapter) {
        int listviewElementswidth = 0;
        for (int i = 0; i < mAdapter.getCount(); i++) {
            View mView = mAdapter.getView(i, null, lv);
            mView.measure(
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
            listviewElementswidth += mView.getMeasuredWidth();
        }
        return listviewElementswidth;
    }

    /** 樹中的元素集合 */
    private ArrayList<Element> elements;
    /** 資料來源元素集合 */
    private ArrayList<Element> elementsData;

    private void init() {
        elements = new ArrayList<Element>();
        elementsData = new ArrayList<Element>();
//新增節點 -- 節點名稱，節點level，節點id，父節點id，是否有子節點，是否展開
//新增最外層節點
        Element e1 = new Element(R.drawable.working, "排班表", Element.TOP_LEVEL, 0, Element.NO_PARENT, false, false);
//新增最外層節點
        Element e2 = new Element(R.drawable.partner, "夥伴管理", Element.TOP_LEVEL, 1, Element.NO_PARENT, false, false);
//新增最外層節點
        Element e3 = new Element(R.drawable.report,"統計報表", Element.TOP_LEVEL, 2, Element.NO_PARENT, false, false);
//新增最外層節點
        Element e4 = new Element(R.drawable.setup, "設定", Element.TOP_LEVEL, 3, Element.NO_PARENT, true, false);
//第一層節點
        Element e4_1 = new Element(0,"偏好設定", 1, 4, e4.getId(), true, false);
//第二層節點
        Element e4_1_1 = new Element(0,"快速登入", 2, 5, e4_1.getId(), false, false);
//第二層節點
        Element e4_1_2 = new Element(0,"推播通知", 2, 6, e4_1.getId(), false, false);
//第二層節點
        Element e4_1_3 = new Element(0,"風格設定", 2, 7, e4_1.getId(), false, false);
//第一層節點
        Element e4_2 = new Element(0,"其他", 1, 8, e4.getId(), true, false);
//第二層節點
        Element e4_2_1 = new Element(0,"關於點點班", 2, 9, e4_2.getId(), false, false);
//第二層節點
        Element e4_2_2 = new Element(0,"使用條款", 2, 10, e4_2.getId(), false, false);
        //第二層節點
        Element e4_2_3 = new Element(0,"使用條款", 2, 12, e4_2.getId(), false, false);
//新增最外層節點
        Element e5 = new Element(R.drawable.sign_out,"登出", Element.TOP_LEVEL, 11, Element.NO_PARENT, false, false);
//新增初始樹元素
        elements.add(e1);
        elements.add(e2);
        elements.add(e3);
        elements.add(e4);
        elements.add(e5);
//建立資料來源
        elementsData.add(e1);
        elementsData.add(e2);
        elementsData.add(e3);
        elementsData.add(e4);
        elementsData.add(e4_1);
        elementsData.add(e4_1_1);
        elementsData.add(e4_1_2);
        elementsData.add(e4_1_3);
        elementsData.add(e4_2);
        elementsData.add(e4_2_1);
        elementsData.add(e4_2_2);
        elementsData.add(e4_2_3);
        elementsData.add(e5);
    }
}