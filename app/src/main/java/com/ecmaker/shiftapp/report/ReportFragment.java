package com.ecmaker.shiftapp.report;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.ecmaker.shiftapp.CommonHR;
import com.ecmaker.shiftapp.MainActivity;
import com.ecmaker.shiftapp.MonthlyCalendar;
import com.ecmaker.shiftapp.R;

import org.jetbrains.annotations.NotNull;


public class ReportFragment extends Fragment {
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
        root = inflater.inflate(R.layout.fragment_report, container, false);

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

        getPeopleReportView(inflater, container, root, mainActivity);
        getWorkingReportView(inflater, container, root, mainActivity);

        return root;
    }

    //人數統計
    public void getPeopleReportView(LayoutInflater inflater, ViewGroup container, View workingView, MainActivity mainActivity){
        TableLayout tablelayout = (TableLayout) workingView.findViewById(R.id.tablelayout);
        for(int i=0; i < 5; i++) {
            TableRow tr = new TableRow(getActivity());
            tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
            LinearLayout linearLayout = new LinearLayout(context);
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);

            for (int j = 0; j < 31; j++) {
                View root2 = inflater.inflate(R.layout.attendance_item_view2, container, false);
                if(i == 0){
                    root2 = inflater.inflate(R.layout.attendance_item_view1, container, false);
                    TextView day = root2.findViewById(R.id.day);
                    String jDay = String.valueOf(j+1);
                    jDay = CommonHR.add0(jDay,2);
                    day.setText(jDay);
                }else{
                    TextView count = root2.findViewById(R.id.count);
                    LinearLayout layout = root2.findViewById(R.id.layout);
                    if(j < 7) {
                        count.setText(String.valueOf(j + 2));
                    }
                    if(i == 4) {
                        count.setTextColor(mainActivity.getColor(R.color.button_blue4));
                    }else {
                        count.setTextColor(mainActivity.getColor(R.color.black));
                    }
                    if(j % 2 == 0) {
                        layout.setBackgroundColor(mainActivity.getColor(R.color.att_table2));
                    }else{
                        layout.setBackgroundColor(mainActivity.getColor(R.color.att_table1));
                    }
                }

                linearLayout.addView(root2);

            }
            tr.addView(linearLayout);
            tablelayout.addView(tr, new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
        }
    }

    //工時統計
    public void getWorkingReportView(LayoutInflater inflater, ViewGroup container, View workingView, MainActivity mainActivity){
        TableLayout tablelayout = (TableLayout) workingView.findViewById(R.id.tablelayout2);
        for(int i=0; i < 5; i++) {
            TableRow tr = new TableRow(getActivity());
            tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
            LinearLayout linearLayout = new LinearLayout(context);
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);

            for (int j = 0; j < 31; j++) {
                View root2 = inflater.inflate(R.layout.attendance_item_view2, container, false);
                if(i == 0){
                    root2 = inflater.inflate(R.layout.attendance_item_view1, container, false);
                    TextView day = root2.findViewById(R.id.day);
                    String jDay = String.valueOf(j+1);
                    jDay = CommonHR.add0(jDay,2);
                    day.setText(jDay);
                }else{
                    TextView count = root2.findViewById(R.id.count);
                    LinearLayout layout = root2.findViewById(R.id.layout);
                    if(j < 7) {
                        count.setText(String.valueOf(j + 2));
                    }
                    if(i == 4) {
                        count.setTextColor(mainActivity.getColor(R.color.button_blue4));
                    }else {
                        count.setTextColor(mainActivity.getColor(R.color.black));
                    }
                    if(j % 2 == 0) {
                        layout.setBackgroundColor(mainActivity.getColor(R.color.att_table2));
                    }else{
                        layout.setBackgroundColor(mainActivity.getColor(R.color.att_table1));
                    }
                }

                linearLayout.addView(root2);

            }
            tr.addView(linearLayout);
            tablelayout.addView(tr, new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
        }
    }
}