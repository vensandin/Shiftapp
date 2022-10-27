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

import com.ecmaker.shiftapp.MainActivity;
import com.ecmaker.shiftapp.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TravelFragment extends Fragment {
    private Context context;
    private View root;
    private MainActivity mainActivity;

    @Override
    public void onAttach(@NonNull @NotNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_travel, container, false);

        //取得當前Activity
        mainActivity = (MainActivity) getContext();

        RecyclerView recycleview = root.findViewById(R.id.recycleview);
        recycleview.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));//行數、左右滑動
        List list = new ArrayList();

        for(int i=0;i<8;i++) {
            HashMap hm = new HashMap();
            hm.put("number", "1");
            hm.put("startDate", "2022/10/19(一)");
            hm.put("endDate", "2022/10/20(二)");
            hm.put("time", "09:00-18:00");
            hm.put("place", "高雄分店");
            hm.put("event", "教育訓練");
            hm.put("isShow", false);
            hm.put("note", "備註、韻剛，議不快琵中在用分轉敗較部？請綠考瘦訊、棵跳較難晨證氓關的是反瘤廣成很取連先！…。蓮向入，！道眶問胡輒要場懼琦夢過鄰肛愛資者京了？指否議厥，蜢女受懲重唔點覺思路尷到鞍啦再瞬但通的常至名姐講做騰安時教人明這自就游資的冠不男每對我弟麼膳乾遍脂經渝？寄價論醬國再並駁託安！要向人的號網難趙心我字有大瓜機解更湯筠事悴，回答做第；，間。認他心面資期蠻於成站感彈疾襟的程蒂挂張煥的我州、，歐耽赫工價社，，於！路家句凱？峰邦原我淚建何弗生的發意報縫資第台");
            ItemsModel_Travel itemsModel_travel = new ItemsModel_Travel(hm);
            list.add(itemsModel_travel);
        }
        TravelAdapter travelAdapter = new TravelAdapter(list,mainActivity);
        recycleview.setAdapter(travelAdapter);


        return root;
    }
}