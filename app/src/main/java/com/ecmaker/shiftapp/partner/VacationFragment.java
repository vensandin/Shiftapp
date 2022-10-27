package com.ecmaker.shiftapp.partner;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ecmaker.shiftapp.CommonHR;
import com.ecmaker.shiftapp.MainActivity;
import com.ecmaker.shiftapp.MonthlyCalendar;
import com.ecmaker.shiftapp.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class VacationFragment extends Fragment {
    private Context context;
    private View root;
    private MainActivity mainActivity;
    private String Year = "";
    private String Month = "";


    @Override
    public void onAttach(@NonNull @NotNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_vacation, container, false);

        //取得當前Activity
        mainActivity = (MainActivity) getContext();

        //年月
        Year = CommonHR.getYear();
        Month = CommonHR.getMonth();
        Month = CommonHR.add0(Month,2);
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


        RecyclerView recycleview = root.findViewById(R.id.recycleview);
        recycleview.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));//行數、左右滑動
        List list = new ArrayList();

        for(int i=0;i<8;i++) {
            HashMap hm = new HashMap();
            hm.put("type", "病假");
            hm.put("startDate", "2022/10/19(一)");
            hm.put("endDate", "2022/10/20(二)");
            hm.put("days", "3天");
            hm.put("isShow", false);
            hm.put("reason", "確診確診(太多太多放不下)確診確診(太多太多放不下)確診確診(太多太多放不下)確診確診(太多太多放不下)");
            hm.put("agent", "Vensan");
            ItemsModel_Vacation itemsModel_vacation = new ItemsModel_Vacation(hm);
            list.add(itemsModel_vacation);
        }
        VacationAdapter vacationAdapter = new VacationAdapter(list,mainActivity);
        recycleview.setAdapter(vacationAdapter);










        return root;
    }
    public void getVacationView(LayoutInflater inflater, ViewGroup container, View workingView, MainActivity mainActivity){

    }
}