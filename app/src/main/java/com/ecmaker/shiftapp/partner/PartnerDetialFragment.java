package com.ecmaker.shiftapp.partner;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ecmaker.shiftapp.MainActivity;
import com.ecmaker.shiftapp.R;

import org.jetbrains.annotations.NotNull;


public class PartnerDetialFragment extends Fragment {
    private Context context;
    private View root;
    private MainActivity mainActivity;
    private AttendanceFragment attendanceFragment;
    private OvertimeFragment overtimeFragment;
    private VacationFragment vacationFragment;
    private TravelFragment travelFragment;

    @Override
    public void onAttach(@NonNull @NotNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_partner_detial, container, false);

        //取得當前Activity
        mainActivity = (MainActivity) getContext();

        //上一頁
        LinearLayout back = root.findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity.getSupportFragmentManager().popBackStackImmediate();
            }
        });

        attendanceFragment = new AttendanceFragment();
        FragmentManager fragmentManager = mainActivity.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.DetialView, attendanceFragment, attendanceFragment.getClass().getName());
        fragmentTransaction.commit();


        LinearLayout fg1 = root.findViewById(R.id.fg1);
        LinearLayout fg2 = root.findViewById(R.id.fg2);
        LinearLayout fg3 = root.findViewById(R.id.fg3);
        LinearLayout fg4 = root.findViewById(R.id.fg4);
        TextView textfg1 = root.findViewById(R.id.textfg1);
        TextView textfg2 = root.findViewById(R.id.textfg2);
        TextView textfg3 = root.findViewById(R.id.textfg3);
        TextView textfg4 = root.findViewById(R.id.textfg4);
        View viewfg1 = root.findViewById(R.id.viewfg1);
        View viewfg2 = root.findViewById(R.id.viewfg2);
        View viewfg3 = root.findViewById(R.id.viewfg3);
        View viewfg4 = root.findViewById(R.id.viewfg4);
        fg1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textfg1.setTextColor(getResources().getColor(R.color.black));
                textfg2.setTextColor(getResources().getColor(R.color.grey2));
                textfg3.setTextColor(getResources().getColor(R.color.grey2));
                textfg4.setTextColor(getResources().getColor(R.color.grey2));
                viewfg1.setBackgroundColor(getResources().getColor(R.color.black));
                viewfg2.setBackgroundColor(getResources().getColor(R.color.grey2));
                viewfg3.setBackgroundColor(getResources().getColor(R.color.grey2));
                viewfg4.setBackgroundColor(getResources().getColor(R.color.grey2));

                attendanceFragment = new AttendanceFragment();
                FragmentManager fragmentManager = mainActivity.getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.DetialView, attendanceFragment, attendanceFragment.getClass().getName());
                fragmentTransaction.commit();
            }
        });
        fg2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textfg1.setTextColor(getResources().getColor(R.color.grey2));
                textfg2.setTextColor(getResources().getColor(R.color.black));
                textfg3.setTextColor(getResources().getColor(R.color.grey2));
                textfg4.setTextColor(getResources().getColor(R.color.grey2));
                viewfg1.setBackgroundColor(getResources().getColor(R.color.grey2));
                viewfg2.setBackgroundColor(getResources().getColor(R.color.black));
                viewfg3.setBackgroundColor(getResources().getColor(R.color.grey2));
                viewfg4.setBackgroundColor(getResources().getColor(R.color.grey2));

                overtimeFragment = new OvertimeFragment();
                FragmentManager fragmentManager = mainActivity.getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.DetialView, overtimeFragment, overtimeFragment.getClass().getName());
                fragmentTransaction.commit();
            }
        });
        fg3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textfg1.setTextColor(getResources().getColor(R.color.grey2));
                textfg2.setTextColor(getResources().getColor(R.color.grey2));
                textfg3.setTextColor(getResources().getColor(R.color.black));
                textfg4.setTextColor(getResources().getColor(R.color.grey2));
                viewfg1.setBackgroundColor(getResources().getColor(R.color.grey2));
                viewfg2.setBackgroundColor(getResources().getColor(R.color.grey2));
                viewfg3.setBackgroundColor(getResources().getColor(R.color.black));
                viewfg4.setBackgroundColor(getResources().getColor(R.color.grey2));

                vacationFragment = new VacationFragment();
                FragmentManager fragmentManager = mainActivity.getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.DetialView, vacationFragment, vacationFragment.getClass().getName());
                fragmentTransaction.commit();
            }
        });
        fg4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textfg1.setTextColor(getResources().getColor(R.color.grey2));
                textfg2.setTextColor(getResources().getColor(R.color.grey2));
                textfg3.setTextColor(getResources().getColor(R.color.grey2));
                textfg4.setTextColor(getResources().getColor(R.color.black));
                viewfg1.setBackgroundColor(getResources().getColor(R.color.grey2));
                viewfg2.setBackgroundColor(getResources().getColor(R.color.grey2));
                viewfg3.setBackgroundColor(getResources().getColor(R.color.grey2));
                viewfg4.setBackgroundColor(getResources().getColor(R.color.black));

                travelFragment = new TravelFragment();
                FragmentManager fragmentManager = mainActivity.getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.DetialView, travelFragment, travelFragment.getClass().getName());
                fragmentTransaction.commit();
            }
        });




        return root;
    }
}