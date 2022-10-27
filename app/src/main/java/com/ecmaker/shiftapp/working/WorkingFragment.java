package com.ecmaker.shiftapp.working;

import android.app.Dialog;
import androidx.fragment.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.SearchView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.ecmaker.shiftapp.CommonHR;
import com.ecmaker.shiftapp.MonthlyCalendar;
import com.ecmaker.shiftapp.dialog.DialogDeleteWorking;
import com.ecmaker.shiftapp.dialog.DialogUtil;
import com.ecmaker.shiftapp.MainActivity;
import com.ecmaker.shiftapp.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

public class WorkingFragment extends Fragment {
    private Context context;
    private DialogUtil dialogUtil;
    private float mScale = 1f;
    private ScaleGestureDetector mScaleGestureDetector;
    private GestureDetector gestureDetector;
    private int orgSize = 0;
    private int changeSize = 0;
    private boolean bScroll = false;
    private float mx, my;
    private View todayView;
    private View afterView;
    private int days = 31;
    private ItemsModel_Date todaymodelDate;
    private ItemsModel_Date modelDate;
    private List<ItemsModel_Person> list_people;
    private HashMap<String,ItemsModel_PersonMonth> hashMap_month;
    private boolean isCheck = false;
    private String Year;
    private String Month;
    private String Date;
    private RecyclerView recyclerView;
    private WorkingAdapter workingAdapter;
    static List<ItemsModel_Working> list_working_often = new ArrayList<>();
    private CustomizeAdapter customizeAdapter;
    static List<ItemsModel_Customize> list_working_customize = new ArrayList<>();
    static String fWorkingId = "";
    static String fWorkingName = "";
    private int count = 1;
    private MainActivity mainActivity;

    @Override
    public void onAttach(@NonNull @NotNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_working, container, false);

        //取得當前Activity
        mainActivity = (MainActivity) getContext();

        //通知(鈴鐺)
        LinearLayout notification = root.findViewById(R.id.notification);
        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<ItemsModel_Notice> list_notice = new ArrayList<>();
                for(int i=0; i < 10; i++){
                    HashMap<String,Object> hNotice = new HashMap<>();
                    hNotice.put("circle","");
                    hNotice.put("content","");
                    hNotice.put("date","");
                    ItemsModel_Notice itemsModel_notice = new ItemsModel_Notice(hNotice);
                    list_notice.add(itemsModel_notice);
                }

                //取得元件X軸Y軸
                int[] xy = new int[2];
                view.getLocationOnScreen(xy);
                //取得View高度
                int viewHeight = view.getHeight();
                int x = xy[0]-420;
                int y = xy[1]+viewHeight;

                NoticePopupWindow noticePopupWindow = new NoticePopupWindow(x,y,list_notice,mainActivity);
                noticePopupWindow.setOnLabelClickListener(new NoticePopupWindow.OnLabelClickListener() {
                    @Override
                    public void onClick() {

                    }
                });
            }
        });


        //下方班別
        List<ItemsModel_Working> list_working = new ArrayList<>();
        for(int i=1; i <= 30; i++) {
            HashMap<String,Object> hashMap = new HashMap<>();
            hashMap.put("working","班別"+i);
            ItemsModel_Working itemsModel_working = new ItemsModel_Working(hashMap);
            list_working.add(itemsModel_working);
        }
        recyclerView = root.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL));//行數、左右滑動
        workingAdapter = new WorkingAdapter(list_working, mainActivity, context, 1);
        recyclerView.setAdapter(workingAdapter);

        //搜尋
        SearchView searchView = root.findViewById(R.id.working_search);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(count == 3){
                    customizeAdapter.getFilter().filter(newText);
                }else {
                    workingAdapter.getFilter().filter(newText);
                }

                return true;
            }
        });

        {//假資料
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("VIEW_TYPE", "1");
            hashMap.put("working", "");
            ItemsModel_Customize itemsModel_customize = new ItemsModel_Customize(hashMap);
            list_working_customize.add(itemsModel_customize);

            HashMap<String, Object> hashMap2 = new HashMap<>();
            hashMap2.put("VIEW_TYPE", "2");
            hashMap2.put("working", "自訂事件");
            ItemsModel_Customize itemsModel_customize2 = new ItemsModel_Customize(hashMap2);
            list_working_customize.add(itemsModel_customize2);
        }


        CardView all = root.findViewById(R.id.all);
        CardView often = root.findViewById(R.id.often);
        CardView customize = root.findViewById(R.id.customize);
        //全部班別
        all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                all.setCardBackgroundColor(getResources().getColor(R.color.grey1));
                often.setCardBackgroundColor(getResources().getColor(R.color.white));
                customize.setCardBackgroundColor(getResources().getColor(R.color.white));
                count = 1;

                recyclerView.removeAllViews();
                recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL));//行數、左右滑動
                workingAdapter = new WorkingAdapter(list_working, mainActivity, context, 1);
                recyclerView.setAdapter(workingAdapter);
            }
        });

        //常用班別
        often.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                all.setCardBackgroundColor(getResources().getColor(R.color.white));
                often.setCardBackgroundColor(getResources().getColor(R.color.grey1));
                customize.setCardBackgroundColor(getResources().getColor(R.color.white));
                count = 2;

                recyclerView.removeAllViews();
                recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL));//行數、左右滑動
                workingAdapter = new WorkingAdapter(list_working_often, mainActivity, context, 2);
                recyclerView.setAdapter(workingAdapter);
            }
        });

        //自訂備註
        customize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                all.setCardBackgroundColor(getResources().getColor(R.color.white));
                often.setCardBackgroundColor(getResources().getColor(R.color.white));
                customize.setCardBackgroundColor(getResources().getColor(R.color.grey1));
                count = 3;

                recyclerView.removeAllViews();
                recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL));//行數、左右滑動
                customizeAdapter = new CustomizeAdapter(list_working_customize, mainActivity, context);
                recyclerView.setAdapter(customizeAdapter);
            }
        });


        //檢核
        CardView check = root.findViewById(R.id.check);
        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


        //年月日
        Year = CommonHR.getYear();
        Month = CommonHR.getMonth();
        Month = CommonHR.add0(Month,2);
        Date = CommonHR.getDate();
        Date = CommonHR.add0(Date,2);

        //年月
        TextView textYear = root.findViewById(R.id.year);
        TextView textMonth = root.findViewById(R.id.month);
        textYear.setText(Year);
        textMonth.setText(Month);
        LinearLayout calendar = root.findViewById(R.id.calendar);
        calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //取得元件X軸Y軸
                int[] xy = new int[2];
                view.getLocationOnScreen(xy);
                //取得View高度
                int viewHeight = view.getHeight();
                int x = xy[0];
                int y = xy[1]+viewHeight;

                int year = 2022;
                int month = 1;

                try {
                    year = Integer.parseInt(textYear.getText().toString());
                    month = Integer.parseInt(textMonth.getText().toString());
                }catch (Exception e){
                    return;
                }

                MonthlyCalendar monthlyCalendar = new MonthlyCalendar(x,y,year,month,mainActivity);
                monthlyCalendar.setOnLabelClickListener(new MonthlyCalendar.OnLabelClickListener() {
                    @Override
                    public void onClick(String year, String month) {
                        textYear.setText(year);
                        textMonth.setText(month);
                    }
                });

            }
        });

        //某人員該月分排班資訊
        Vector<String> vName = new Vector<>();
        vName.add("陳采昕");
        vName.add("劉慈恭");
        vName.add("許可月");
        vName.add("王俊坤");
        vName.add("涂昀軒");
        vName.add("劉紀平");
        vName.add("王曉華");
        hashMap_month = new HashMap<>();
        for(int i=0; i < vName.size(); i++) {
            String name = vName.get(i);
            List<Vector<Object>> list_month = new ArrayList<>();
            for (int x = 1; x <= days; x++) {
                if(i == 1 && x == 2) {
                    Vector<Object> vMonth = new Vector<>();
                    HashMap<String, Object> hashMap = new HashMap<>();
                    hashMap.put("workingId", "11");
                    hashMap.put("workingName", "test1");
                    vMonth.add(hashMap);
                    list_month.add(vMonth);
                }else if(i == 2  && x == 3){
                    Vector<Object> vMonth = new Vector<>();
                    HashMap<String, Object> hashMap = new HashMap<>();
                    hashMap.put("workingId", "22");
                    hashMap.put("workingName", "test2");
                    vMonth.add(hashMap);
                    HashMap<String, Object> hashMap2 = new HashMap<>();
                    hashMap2.put("workingId", "33");
                    hashMap2.put("workingName", "test3");
                    vMonth.add(hashMap2);
                    list_month.add(vMonth);
                }else{
                    Vector<Object> vMonth = new Vector<>();
                    list_month.add(vMonth);
                }

            }
            ItemsModel_PersonMonth itemsModel_personMonth = new ItemsModel_PersonMonth(list_month);
            hashMap_month.put(name,itemsModel_personMonth);
        }

        //人員
        list_people = getPerson("c01");
        getPersonView(inflater,container,root,list_people);

        //全選
        CheckBox allCheck = root.findViewById(R.id.allCheck);
        allCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                isCheck = b;
                for(int i=0; i < list_people.size(); i++) {
                    list_people.get(i).setCheck(b);
                }
                TableLayout personlayout = (TableLayout) root.findViewById(R.id.personlayout);
                personlayout.removeAllViews();
                getPersonView(inflater,container,root,list_people);
            }
        });

        //日期
        List<ItemsModel_Date> list_date = new ArrayList<>();
        int x = 1;
        String week = "";
        for(int i=1; i <= days; i++) {
            if(x == 8){
                x = 1;
            }
            if(x == 1){week = "一";}
            else if(x == 2){week = "二";}
            else if(x == 3){week = "三";}
            else if(x == 4){week = "四";}
            else if(x == 5){week = "五";}
            else if(x == 6){week = "六";}
            else if(x == 7){week = "日";}
            HashMap<String, Object> hDate = new HashMap<>();
            String day = String.valueOf(i);
            if(day.length() == 1){
                day = "0"+day;
            }
            hDate.put("week", week);
            hDate.put("day", day);
            ItemsModel_Date itemsModel_date = new ItemsModel_Date(hDate);
            list_date.add(itemsModel_date);
            x++;
        }
        TableLayout datelayout = (TableLayout) root.findViewById(R.id.datelayout);
        TableRow tr2 = new TableRow(getActivity());
        tr2.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
        LinearLayout Layout2 = new LinearLayout(context);
        Layout2.setOrientation(LinearLayout.HORIZONTAL);
        for(int i=0; i < list_date.size(); i++) {
            ItemsModel_Date itemsModel_date = list_date.get(i);
            View root2 = inflater.inflate(R.layout.date_item_view, container, false);
            CardView cardView = root2.findViewById(R.id.cardview);
            TextView week_tv = root2.findViewById(R.id.week);
            TextView day_tv = root2.findViewById(R.id.day);
            week_tv.setText(itemsModel_date.getWeek());
            day_tv.setText(itemsModel_date.getDay());
            if(Date.equals(itemsModel_date.getDay())){
                todayView = root2;
                todaymodelDate = itemsModel_date;
            }

            if(i == Integer.parseInt(Date)-1){
                afterView = root2;
                modelDate = itemsModel_date;
                cardView.setCardBackgroundColor(mainActivity.getColor(R.color.button_blue2));
                week_tv.setTextColor(mainActivity.getColor(R.color.white));
                day_tv.setTextColor(mainActivity.getColor(R.color.white));
            }else {
                cardView.setCardBackgroundColor(mainActivity.getColor(R.color.white));
                week_tv.setTextColor(mainActivity.getColor(R.color.black));
                day_tv.setTextColor(mainActivity.getColor(R.color.black));
            }
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    cardView.setCardBackgroundColor(mainActivity.getColor(R.color.button_blue2));
                    week_tv.setTextColor(mainActivity.getColor(R.color.white));
                    day_tv.setTextColor(mainActivity.getColor(R.color.white));

                    CardView cardView_after = afterView.findViewById(R.id.cardview);
                    TextView week_tv_after = afterView.findViewById(R.id.week);
                    TextView day_tv_after = afterView.findViewById(R.id.day);
                    cardView_after.setCardBackgroundColor(mainActivity.getColor(R.color.white));
                    week_tv_after.setTextColor(mainActivity.getColor(R.color.black));
                    day_tv_after.setTextColor(mainActivity.getColor(R.color.black));

                    afterView = root2;
                    modelDate = itemsModel_date;
                }
            });
            Layout2.addView(root2);
        }
        tr2.addView(Layout2);
        datelayout.addView(tr2, new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));

        //今日
        CardView today = root.findViewById(R.id.today);
        today.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!Date.equals(modelDate.getDay())){
                    CardView cardView = todayView.findViewById(R.id.cardview);
                    TextView week_tv = todayView.findViewById(R.id.week);
                    TextView day_tv = todayView.findViewById(R.id.day);
                    week_tv.setText(todaymodelDate.getWeek());
                    day_tv.setText(todaymodelDate.getDay());
                    cardView.setCardBackgroundColor(mainActivity.getColor(R.color.button_blue2));
                    week_tv.setTextColor(mainActivity.getColor(R.color.white));
                    day_tv.setTextColor(mainActivity.getColor(R.color.white));

                    CardView cardView_after = afterView.findViewById(R.id.cardview);
                    TextView week_tv_after = afterView.findViewById(R.id.week);
                    TextView day_tv_after = afterView.findViewById(R.id.day);
                    cardView_after.setCardBackgroundColor(mainActivity.getColor(R.color.white));
                    week_tv_after.setTextColor(mainActivity.getColor(R.color.black));
                    day_tv_after.setTextColor(mainActivity.getColor(R.color.black));
                    afterView = todayView;
                    modelDate = todaymodelDate;
                }
            }
        });

        //排班表
        /* Find Tablelayout defined in main.xml */
        TableLayout tablelayout = (TableLayout) root.findViewById(R.id.tablelayout);
        /* Create a new row to be added. */
        getWorkingView(inflater,container,root);

        //下拉選單
        LinearLayout filterLayout = root.findViewById(R.id.filterLayout);
        filterLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //取得元件X軸Y軸
                int[] xy = new int[2];
                view.getLocationOnScreen(xy);
                //取得View高度
                int viewHeight = view.getHeight();
                int x = xy[0];
                int y = xy[1]+viewHeight;
                FilterSelector filterSelector = new FilterSelector(x,y,mainActivity);
            }
        });

        //分店點選
        LinearLayout shopLayout = root.findViewById(R.id.shopLayout);
        shopLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //取得元件X軸Y軸
                int[] xy = new int[2];
                view.getLocationOnScreen(xy);
                //取得View高度
                int viewHeight = view.getHeight();
                int x = xy[0];
                int y = xy[1]+viewHeight;
                ShopSelector shopSelector = new ShopSelector(x,y,mainActivity,root);
            }
        });

        //連動滑動
        HorizontalScrollView scrollView_table = root.findViewById(R.id.scrollView_table);
        HorizontalScrollView scrollView_date = root.findViewById(R.id.scrollView_date);
        scrollView_table.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View view, int i, int i1, int i2, int i3) {
                scrollView_date.scrollTo(i,i1);
            }
        });
        scrollView_date.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View view, int i, int i1, int i2, int i3) {
                scrollView_table.scrollTo(i,i1);
            }
        });

        //table x、y移動
        ScrollView scrollView = root.findViewById(R.id.scrollView);
        scrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                float curX, curY;
                switch (event.getAction()) {

                    case MotionEvent.ACTION_DOWN:
                        mx = event.getX();
                        my = event.getY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        curX = event.getX();
                        curY = event.getY();
                        if((int) (mx - curX)<-100 || (int) (mx - curX)>100){

                        }else {
                            scrollView_table.scrollBy((int) (mx - curX), (int) (my - curY));
                        }
                        mx = curX;
                        my = curY;
                        break;
                    case MotionEvent.ACTION_UP:
                        curX = event.getX();
                        curY = event.getY();
                        break;
                }
                mScaleGestureDetector.onTouchEvent(event);
                gestureDetector.onTouchEvent(event);
                return bScroll;
            }
        });

        scrollView_table.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                mScaleGestureDetector.onTouchEvent(event);
                gestureDetector.onTouchEvent(event);
                return bScroll;
            }
        });

        //取得預設寬度
        View view = ((LinearLayout)((TableRow)tablelayout.getChildAt(0)).getChildAt(0)).getChildAt(0);
        LinearLayout linearLayout = view.findViewById(R.id.layout_date);
        CardView cardView = (CardView) linearLayout.getChildAt(0);
        changeSize = cardView.getLayoutParams().width;
        orgSize = changeSize;

        //放大縮小
        gestureDetector = new GestureDetector(context, new GestureListener());
        mScaleGestureDetector = new ScaleGestureDetector(context, new ScaleGestureDetector.SimpleOnScaleGestureListener() {
            @Override
            public boolean onScale(ScaleGestureDetector detector) {
                bScroll = true;
                // firstly we will get the scale factor
                float scale = 1 - detector.getScaleFactor();
                float prevScale = mScale;
                mScale += scale;

                // we can maximise our focus to 10f only
                if (mScale > 10f)
                    mScale = 10f;

                if(prevScale>mScale){
                    changeSize = changeSize+9;
                }else if(prevScale<mScale){
                    changeSize = changeSize-9;
                }

                if(changeSize<orgSize){
                    changeSize = orgSize;
                }

                for(int i=0;i<tablelayout.getChildCount();i++){
                    TableRow iRow = (TableRow)tablelayout.getChildAt(i);
                    LinearLayout iLinearLayout = (LinearLayout) iRow.getChildAt(0);
                    for(int j=0;j<iLinearLayout.getChildCount();j++){
                        View jView = iLinearLayout.getChildAt(j);
                        LinearLayout jLinearLayout = jView.findViewById(R.id.layout_date);
                        for(int x=0;x<jLinearLayout.getChildCount();x++){
                            CardView xCardView = (CardView) jLinearLayout.getChildAt(x);
                            xCardView.getLayoutParams().width = changeSize;
                            xCardView.requestLayout();
                        }
                    }
                }

                for(int i=0;i<datelayout.getChildCount();i++){
                    TableRow row = (TableRow)datelayout.getChildAt(i);
                    LinearLayout iLinearLayout = (LinearLayout) row.getChildAt(0);
                    for(int j=0;j<iLinearLayout.getChildCount();j++){
                        View jView = iLinearLayout.getChildAt(j);
                        LinearLayout jLinearLayout = jView.findViewById(R.id.layout_date);
                        jLinearLayout.getLayoutParams().width = changeSize;
                        jLinearLayout.requestLayout();

                    }
                }
                return true;
            }

            @Override
            public boolean onScaleBegin(ScaleGestureDetector detector) {
                bScroll = true;
                return super.onScaleBegin(detector);

            }

            @Override
            public void onScaleEnd(ScaleGestureDetector detector) {
                bScroll = false;
                super.onScaleEnd(detector);

            }
        });

        return root;
    }

    private class GestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            return true;
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    public static float dipToPixels(Context context, float dipValue){
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,  dipValue, metrics);
    }

    public static float spToPixels(Context context, float spValue){
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,  spValue, metrics);
    }

    //畫班表
    public void getWorkingView(LayoutInflater inflater, ViewGroup container, View workingView){
        TableLayout tablelayout = (TableLayout) workingView.findViewById(R.id.tablelayout);
        for(int i=0; i < list_people.size(); i++) {
            ItemsModel_Person itemsModel_person = list_people.get(i);
            String iName = itemsModel_person.getName();
            ItemsModel_PersonMonth personMonth = hashMap_month.get(iName);
            List<Vector<Object>> list_month = personMonth.getList();

            TableRow tr = new TableRow(getActivity());
            tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
            LinearLayout linearLayout = new LinearLayout(context);
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);

            for (int j = 0; j < list_month.size(); j++) {
                Vector<Object> vMonth = list_month.get(j);
                if(vMonth != null && vMonth.size() > 0){
                    if(vMonth.size() == 1){
                        HashMap<String, Object> hMonth = (HashMap<String, Object>) vMonth.get(0);
                        String workingName = (String)hMonth.get("workingName");

                        View root2 = inflater.inflate(R.layout.table_item_view2, container, false);
                        CardView cardView = root2.findViewById(R.id.cardview);
                        TextView text1 = root2.findViewById(R.id.text1);
                        text1.setText(workingName);
                        linearLayout.addView(root2);
                        cardView.setOnLongClickListener(new View.OnLongClickListener() {
                            @Override
                            public boolean onLongClick(View view) {
                                Log.e("LongClick", "text1:" + text1.getText().toString());

                                DialogDeleteWorking dialogDeleteWorking = new DialogDeleteWorking(context,mainActivity);
                                dialogDeleteWorking.show();
                                dialogDeleteWorking.setOnLabelClickListener(new DialogDeleteWorking.OnLabelClickListener() {
                                    @Override
                                    public void onClick() {
                                        Log.e("DialogDeleteWorking", "OnLabelClick");
                                    }
                                });

                                return true;//true為結束Click ,如果後面還有短按也要執行才會回傳false
                            }
                        });

                        cardView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Log.e("OnClick", "OnClick2");
                                //更換Item
                                getTableItem(inflater, container, tr, linearLayout, tablelayout, root2, vMonth);
                            }
                        });

                    }else{
                        HashMap<String, Object> hMonth = (HashMap<String, Object>) vMonth.get(0);
                        String workingName = (String)hMonth.get("workingName");
                        HashMap<String, Object> hMonth2 = (HashMap<String, Object>) vMonth.get(1);
                        String workingName2 = (String)hMonth2.get("workingName");

                        View root2 = inflater.inflate(R.layout.table_item_view3, container, false);
                        CardView cardView = root2.findViewById(R.id.cardview);
                        TextView text1 = root2.findViewById(R.id.text1);
                        text1.setText(workingName);

                        CardView cardView2 = root2.findViewById(R.id.cardview2);
                        TextView text2 = root2.findViewById(R.id.text2);
                        text2.setText(workingName2);
                        linearLayout.addView(root2);

                        root2.setOnLongClickListener(new View.OnLongClickListener() {
                            @Override
                            public boolean onLongClick(View view) {
                                Log.e("LongClick", "text1:" + text1.getText().toString() + "  text2:" + text2.getText().toString());
                                return true;
                            }
                        });
                    }

                }else{
                    View root2 = inflater.inflate(R.layout.table_item_view, container, false);
                    CardView cardView = root2.findViewById(R.id.cardview);
                    TextView text1 = root2.findViewById(R.id.text1);
                    text1.setText("");
                    linearLayout.addView(root2);
                    cardView.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View view) {
                            Log.e("LongClick", "text1:" + text1.getText().toString());
                            return true;
                        }
                    });

                    cardView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Log.e("OnClick", "OnClick1");
                            //更換Item
                            getTableItem(inflater, container, tr, linearLayout, tablelayout, root2, vMonth);
                        }
                    });
                }

            }
            tr.addView(linearLayout);
            tablelayout.addView(tr, new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
        }
    }

    //畫出某天的班(區塊)
    public void getTableItem(LayoutInflater inflater, ViewGroup container, TableRow tr,
                             LinearLayout linearLayout, TableLayout tablelayout, View view,
                             Vector<Object> vMonth ){
        if(fWorkingName.length() > 0) {
            if(vMonth != null && vMonth.size() == 1) {
                HashMap<String, Object> hMonth = (HashMap<String, Object>) vMonth.get(0);
                String workingName = (String)hMonth.get("workingName");
                if(fWorkingName.equals(workingName)){
                    return;
                }

                int col = linearLayout.indexOfChild(view);
                int row = tablelayout.indexOfChild(tr);
                Log.e("ttt", "col:" + col + "  row:" + row);

                ViewGroup vp = (ViewGroup) view.getParent();
                vp.removeViewAt(col);
                View root3 = inflater.inflate(R.layout.table_item_view3, container, false);
                TextView text1 = root3.findViewById(R.id.text1);
                text1.setText(workingName);
                TextView text2 = root3.findViewById(R.id.text2);
                text2.setText(fWorkingName);
                root3.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        Log.e("LongClick", "text1:" + text1.getText().toString() + "  text2:" + text2.getText().toString());
                        return true;
                    }
                });
                vp.addView(root3, col);

                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("workingId", "55");
                hashMap.put("workingName", fWorkingName);
                vMonth.add(hashMap);

            }else if(vMonth == null || vMonth.size() == 0) {
                int col = linearLayout.indexOfChild(view);
                int row = tablelayout.indexOfChild(tr);
                Log.e("ttt", "col:" + col + "  row:" + row);

                ViewGroup vp = (ViewGroup) view.getParent();
                vp.removeViewAt(col);
                View root3 = inflater.inflate(R.layout.table_item_view2, container, false);
                CardView cardView = root3.findViewById(R.id.cardview);
                TextView text1 = root3.findViewById(R.id.text1);
                text1.setText(fWorkingName);
                vp.addView(root3, col);
                cardView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        Log.e("LongClick", "text1:" + text1.getText().toString());
                        return true;
                    }
                });

                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("workingId", "44");
                hashMap.put("workingName", fWorkingName);
                vMonth.add(hashMap);

                cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        getTableItem(inflater, container, tr, linearLayout, tablelayout, root3, vMonth);
                    }
                });
            }
        }

    }

    //畫人員列表
    public void getPersonView(LayoutInflater inflater, ViewGroup container, View view, List<ItemsModel_Person> list_people){
        TableLayout personlayout = (TableLayout) view.findViewById(R.id.personlayout);
        TableRow tr3 = new TableRow(getActivity());
        tr3.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
        LinearLayout Layout3 = new LinearLayout(context);
        Layout3.setOrientation(LinearLayout.VERTICAL);
        if(list_people == null || list_people.size() == 0) {
            //沒東西不知道要顯示什麼
        }else{
            for (int i = 0; i < list_people.size(); i++) {
                ItemsModel_Person itemsModel_person = list_people.get(i);
                View personRoot2 = inflater.inflate(R.layout.person_item_view, container, false);
                CheckBox checkBox = personRoot2.findViewById(R.id.checkBox);
                ImageView person_header = personRoot2.findViewById(R.id.person_header);
                TextView name = personRoot2.findViewById(R.id.name);
                TextView position = personRoot2.findViewById(R.id.position);
                ImageView icon = personRoot2.findViewById(R.id.icon);
                TextView hours = personRoot2.findViewById(R.id.hours);

                checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        //list_people.get(i).setCheck(b);
                    }
                });

                checkBox.setChecked(itemsModel_person.getCheck());
                name.setText(itemsModel_person.getName());
                position.setText(itemsModel_person.getPositiont());
                hours.setText(itemsModel_person.getHours());

                personRoot2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.e("personRoot2",itemsModel_person.getName());
                        if (dialogUtil == null) {
                            dialogUtil = new DialogUtil(context, R.layout.dialog_right);
                            Dialog dialog = dialogUtil.showRightDialog();
                            TextView t1 = dialog.findViewById(R.id.textView);
                            TextView t2 = dialog.findViewById(R.id.textView2);
                            TextView t3 = dialog.findViewById(R.id.textView3);
                            TextView t4 = dialog.findViewById(R.id.textView4);
                            ImageView cls = dialog.findViewById(R.id.cls);

                            t1.setText(modelDate.getDay()+modelDate.getWeek());
                            t2.setText(itemsModel_person.getName());

                            ItemsModel_PersonMonth personMonth = hashMap_month.get(itemsModel_person.getName());
                            List<Vector<Object>> list_month = personMonth.getList();
                            Vector<Object> vMonth = list_month.get(Integer.parseInt(modelDate.getDay())-1);
                            if(vMonth != null && vMonth.size() > 0) {
                                HashMap<String, Object> hMonth = (HashMap<String, Object>) vMonth.get(0);
                                String workingName = (String) hMonth.get("workingName");
                                t3.setText(workingName);
                                if(vMonth.size() > 1){
                                    HashMap<String, Object> hMonth2 = (HashMap<String, Object>) vMonth.get(1);
                                    String workingName2 = (String) hMonth2.get("workingName");
                                    t4.setText(workingName2);
                                }
                            }

                            cls.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    dialogUtil.close();
                                    dialogUtil = null;
                                }
                            });
                        } else {
                            if (dialogUtil != null) {
                                dialogUtil.close();
                                dialogUtil = null;
                            }
                        }
                    }
                });

                Layout3.addView(personRoot2);
            }
        }
        tr3.addView(Layout3);
        personlayout.addView(tr3, new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
    }

    //人員資料 (下拉選單分類)
    public List<ItemsModel_Person> getPerson(String code){
        List<ItemsModel_Person> list_people = new ArrayList<>();
        if(code.equals("c01")) {
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("check",false);
            hashMap.put("VIEW_TYPE", "1");
            hashMap.put("name", "陳采昕");
            hashMap.put("position", "正職 | 外場");
            hashMap.put("hours", "220/220");
            hashMap.put("name", "陳采昕");
            ItemsModel_Person itemsModelPerson = new ItemsModel_Person(hashMap);
            list_people.add(itemsModelPerson);
        }


        if(code.equals("c01") || code.equals("c02") || code.equals("c05")) {
            HashMap<String, Object> hashMap1 = new HashMap<>();
            hashMap1.put("check",false);
            hashMap1.put("VIEW_TYPE", "1");
            hashMap1.put("name", "劉慈恭");
            hashMap1.put("position", "正職 | 外場");
            hashMap1.put("hours", "220/220");
            ItemsModel_Person itemsModelPerson1 = new ItemsModel_Person(hashMap1);
            list_people.add(itemsModelPerson1);
        }

        if(code.equals("c01") || code.equals("c03") || code.equals("c06")) {
            HashMap<String,Object> hashMap2 = new HashMap<>();
            hashMap2.put("check",false);
            hashMap2.put("VIEW_TYPE","1");
            hashMap2.put("name","許可月");
            hashMap2.put("position","兼職 | 外場");
            hashMap2.put("hours","220/220");
            ItemsModel_Person itemsModelPerson2 = new ItemsModel_Person(hashMap2);
            list_people.add(itemsModelPerson2);
        }

        if(code.equals("c01") || code.equals("c04")) {
            HashMap<String,Object> hashMap3 = new HashMap<>();
            hashMap3.put("check",false);
            hashMap3.put("VIEW_TYPE","1");
            hashMap3.put("name","王俊坤");
            hashMap3.put("position","正職 | 外場");
            hashMap3.put("hours","220/220");
            ItemsModel_Person itemsModelPerson3 = new ItemsModel_Person(hashMap3);
            list_people.add(itemsModelPerson3);
        }

        if(code.equals("c01") || code.equals("c05")) {
            HashMap<String,Object> hashMap4 = new HashMap<>();
            hashMap4.put("check",false);
            hashMap4.put("VIEW_TYPE","1");
            hashMap4.put("name","涂昀軒");
            hashMap4.put("position","正職 | 內場");
            hashMap4.put("hours","220/220");
            ItemsModel_Person itemsModelPerson4 = new ItemsModel_Person(hashMap4);
            list_people.add(itemsModelPerson4);
        }

        if(code.equals("c01") || code.equals("c06")) {
            HashMap<String,Object> hashMap5 = new HashMap<>();
            hashMap5.put("check",false);
            hashMap5.put("VIEW_TYPE","1");
            hashMap5.put("name","劉紀平");
            hashMap5.put("position","兼職 | 內場");
            hashMap5.put("hours","220/220");
            ItemsModel_Person itemsModelPerson5 = new ItemsModel_Person(hashMap5);
            list_people.add(itemsModelPerson5);
        }

        if(code.equals("c01") || code.equals("c03") || code.equals("c04") || code.equals("c05")) {
            HashMap<String, Object> hashMap6 = new HashMap<>();
            hashMap6.put("check",false);
            hashMap6.put("VIEW_TYPE", "1");
            hashMap6.put("name", "王曉華");
            hashMap6.put("position", "兼職 | 外場");
            hashMap6.put("hours", "220/220");
            ItemsModel_Person itemsModelPerson6 = new ItemsModel_Person(hashMap6);
            list_people.add(itemsModelPerson6);
        }

        return list_people;
    }
}
