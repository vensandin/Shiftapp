package com.ecmaker.shiftapp.partner;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.ecmaker.shiftapp.CircularRingPercentageView;
import com.ecmaker.shiftapp.MainActivity;
import com.ecmaker.shiftapp.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class OvertimeFragment extends Fragment {
    private Context context;
    private View root;
    private MainActivity mainActivity;
    private CircularRingPercentageView progressCircle;
    private CircularRingPercentageView progressCircle2;

    @Override
    public void onAttach(@NonNull @NotNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_overtime, container, false);

        //取得當前Activity
        mainActivity = (MainActivity) getContext();

        double f1 = (3.0/14.0)*100;
        int num1 = (int)f1;
        ProgressBar progressBar = root.findViewById(R.id.progressBar);
        progressBar.setProgress(num1);

        double f2 = (1.0/10.0)*100;
        int num2 = (int)f2;
        ProgressBar progressBar2 = root.findViewById(R.id.progressBar2);
        progressBar2.setProgress(num2);



        BarChart barChart = root.findViewById(R.id.barChartRender);
        List<BarEntry> list=new ArrayList<>();
        list.add(new BarEntry(1,7));
        list.add(new BarEntry(2,10));
        list.add(new BarEntry(3,12));
        list.add(new BarEntry(4,6));
        list.add(new BarEntry(5,3));
        list.add(new BarEntry(6,9));
        list.add(new BarEntry(7,8));
        list.add(new BarEntry(8,11));
        list.add(new BarEntry(9,5));
        list.add(new BarEntry(10,0));
        list.add(new BarEntry(11,7));
        list.add(new BarEntry(12,3));
        list.add(new BarEntry(13,11));
        list.add(new BarEntry(14,6));
        list.add(new BarEntry(15,9));
        list.add(new BarEntry(16,0));
        list.add(new BarEntry(17,4));
        list.add(new BarEntry(18,5));
        list.add(new BarEntry(19,2));
        list.add(new BarEntry(20,9));
        list.add(new BarEntry(21,11));
        list.add(new BarEntry(22,3));
        list.add(new BarEntry(23,0));
        list.add(new BarEntry(24,9));
        list.add(new BarEntry(25,1));
        list.add(new BarEntry(26,7));
        list.add(new BarEntry(27,8));
        list.add(new BarEntry(28,2));
        list.add(new BarEntry(29,0));
        list.add(new BarEntry(30,10));
        list.add(new BarEntry(31,2));

        ArrayList<Integer> colors = new ArrayList<Integer>();
        colors.add(getResources().getColor(R.color.button_blue4));


        BarDataSet barDataSet=new BarDataSet(list,"單月加班累積時數");
        BarData barData=new BarData(barDataSet);
        barDataSet.setColors(colors);
        barData.setBarWidth(0.5f); //設定自定義條形寬度
        /*
        {
            //建立Legend物件
            Legend l = barChart.getLegend();
            //是否顯示圖例
            l.setEnabled(true);
            //設定垂直對齊of the Legend
            l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
            //設定水平of the Legend
            l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
            //設定方向
            l.setOrientation(Legend.LegendOrientation.VERTICAL);
            //其中哪一個將畫在圖表或外
            l.setDrawInside(false);
            //設定水平軸上圖例項之間的間距
            l.setXEntrySpace(10f);
            //設定在垂直軸上的圖例項之間的間距
            l.setYEntrySpace(0f);
            //設定此軸上標籤的所使用的y軸偏移量 更高的偏移意味著作為一個整體的Legend將被放置遠離頂部。
            l.setYOffset(0f);

            l.setTextColor(Color.BLUE);//設定文字颜色
            l.setTextSize(10f);//設定文字大小

            int[] cs = {getResources().getColor(R.color.red),getResources().getColor(R.color.orange)
                    ,getResources().getColor(R.color.black_overlay),getResources().getColor(R.color.teal_200)
                    ,getResources().getColor(R.color.button_blue4)};
            String[] labels = {"週一","週二","週三","週四","週五"};
            List<LegendEntry> entries2 = new ArrayList<>();
            for(int i=0;i < cs.length;i++){
                LegendEntry entry = new LegendEntry();
                entry.formColor = cs[i];
                entry.label = labels[i];
                entries2.add(entry);
            }
            l.setCustom(entries2);
        }
         */
        barChart.setData(barData);
        barChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);//X軸顯示位置 預設是TOP
        barChart.getAxisRight().setEnabled(false);//隱藏Y軸右側顯示位置 預設是左右都顯示
        barChart.getAxisLeft().setEnabled(false);//隱藏Y軸左側顯示位置 預設是左右都顯示


        CustomBarChartRender barChartRender = new CustomBarChartRender(barChart,barChart.getAnimator(),barChart.getViewPortHandler());
        barChartRender.setRadius(10);//圓角角度(但CustomBarChartRender 重繪圖片  這邊就沒用了)
        barChart.setRenderer(barChartRender);

        YAxis left = barChart.getAxisLeft();//Y軸
        left.setAxisMinimum(0);//Y軸最小值
        left.setDrawGridLines(false);//Y軸線是否顯示

        XAxis xAxis = barChart.getXAxis();//X軸
        xAxis.setDrawGridLines(false);//X軸線是否顯示

        barChart.setDescription(null);//右下文字






        /**
         <com.example.slidingpane.CircularRingPercentageView
         android:id="@+id/progress"
         android:layout_width="600dp"
         android:layout_height="600dp"
         app:circleRoundWidth="18dp"
         app:circleTextColor="#999999"
         app:circleTextSize="8sp"
         android:layout_marginTop="100dp"
         android:layout_marginLeft="50dp" />
         **/
        /*
        {
            progressCircle = root.findViewById(R.id.progress);
            //進度
            progressCircle.setProgress(80, new CircularRingPercentageView.OnProgressScore() {
                @Override
                public void setProgressScore(float score) {
                    Log.e("setProgressScore", score + "");
                }
            });
            //圓環大小(layout 得在後面線呈才能改)
            //LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) progressCircle.getLayoutParams();
            //layoutParams.width = 150;
            //layoutParams.height = 150;
            //progressCircle.setLayoutParams(layoutParams);
            progressCircle.setCircleWidth(500);
            //刻度數量
            progressCircle.setMaxColorNumber(0);
            //圓環寬度
            progressCircle.setRoundWidth(50);
            //文字大小
            progressCircle.setTextSize(30);
            //空白出顏色背景
            progressCircle.setRoundBackgroundColor(R.color.grey3);
            //漸變顏色
            int[] colors = {getResources().getColor(R.color.red), getResources().getColor(R.color.orange), getResources().getColor(R.color.colorPrimaryDark)};
            progressCircle.setColors(colors);


            progressCircle2 = root.findViewById(R.id.progress2);
            //進度
            progressCircle2.setProgress(80, new CircularRingPercentageView.OnProgressScore() {
                @Override
                public void setProgressScore(float score) {
                    Log.e("setProgressScore", score + "");
                }
            });
            //圓環大小(layout 得在後面線呈才能改)
            //LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) progressCircle.getLayoutParams();
            //layoutParams.width = 150;
            //layoutParams.height = 150;
            //progressCircle.setLayoutParams(layoutParams);
            progressCircle2.setCircleWidth(500);
            //刻度數量
            progressCircle2.setMaxColorNumber(0);
            //圓環寬度
            progressCircle2.setRoundWidth(90);
            //文字大小
            progressCircle2.setTextSize(50);
            //空白出顏色背景
            progressCircle2.setRoundBackgroundColor(R.color.white);
            //漸變顏色
            int[] colors2 = {getResources().getColor(R.color.button_blue4), getResources().getColor(R.color.purple_200), getResources().getColor(R.color.teal_200)};
            progressCircle2.setColors(colors2);
            //換中間文字
            progressCircle2.setTextData("15人");
        }
        */

        return root;
    }
}