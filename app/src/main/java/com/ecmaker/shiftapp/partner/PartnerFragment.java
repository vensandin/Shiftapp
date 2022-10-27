package com.ecmaker.shiftapp.partner;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ecmaker.shiftapp.CommonHR;
import com.ecmaker.shiftapp.MainActivity;
import com.ecmaker.shiftapp.OkHttpSingleton;
import com.ecmaker.shiftapp.R;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class PartnerFragment extends Fragment {
    private Context context;
    private View root;
    private View itemView;
    private MainActivity mainActivity;
    private int st = 1;
    private int center = 1;
    private int ed = 10;
    private CardView cardView;

    @Override
    public void onAttach(@NonNull @NotNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_partner, container, false);

        //取得當前Activity
        mainActivity = (MainActivity) getContext();










        //連線
        LinearLayout listLayout = root.findViewById(R.id.listLayout);
        itemView = inflater.inflate(R.layout.fragment_partner_item, container, false);
        listLayout.addView(itemView);
        listLayout.setVisibility(View.INVISIBLE);
        //Client(mainActivity, root, inflater ,container);

        /** 測試用 **/
        {
            listLayout.removeAllViews();
            listLayout.setVisibility(View.VISIBLE);
            itemView = inflater.inflate(R.layout.fragment_partner_item, container, false);
            listLayout.addView(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    PartnerFragment partnerFragment = new PartnerFragment();
                    PartnerDetialFragment partnerDetialFragment = new PartnerDetialFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("empid", "123456");
                    partnerDetialFragment.setArguments(bundle);
                    FragmentTransaction ft = mainActivity.getSupportFragmentManager().beginTransaction();
                    ft.add(R.id.fragmentView, partnerDetialFragment, partnerDetialFragment.getClass().getName());
                    ft.addToBackStack(partnerFragment.getClass().getName());
                    ft.hide(mainActivity.getSupportFragmentManager().findFragmentByTag(partnerFragment.getClass().getName()));
                    ft.commit();
                }
            });
        }
        /** 測試用 **/





        //頁數
        CardView c1 = root.findViewById(R.id.carv1);
        CardView c2 = root.findViewById(R.id.carv2);
        CardView c3 = root.findViewById(R.id.carv3);
        CardView c4 = root.findViewById(R.id.carv4);
        CardView c5 = root.findViewById(R.id.carv5);
        CardView c6 = root.findViewById(R.id.carv6);
        CardView c7 = root.findViewById(R.id.carv7);
        TextView t1 = root.findViewById(R.id.text1);
        TextView t2 = root.findViewById(R.id.text2);
        TextView t3 = root.findViewById(R.id.text3);
        TextView t4 = root.findViewById(R.id.text4);
        TextView t5 = root.findViewById(R.id.text5);
        TextView t6 = root.findViewById(R.id.text6);
        TextView t7 = root.findViewById(R.id.text7);

        //頁數初始
        t1.setText(String.valueOf(st));
        t2.setText(String.valueOf((st+1)));
        t3.setText(String.valueOf((st+2)));
        t4.setText("...");
        t5.setText("");
        t6.setText("");
        t7.setText(String.valueOf(ed));
        cardView = c1;
        center = st;
        c5.setVisibility(View.GONE);
        c6.setVisibility(View.GONE);

        //頁數OnClick
        c1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getCardViewOnClick(c1,t1);
            }
        });
        c2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getCardViewOnClick(c2,t2);
            }
        });
        c3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getCardViewOnClick(c3,t3);
            }
        });
        c4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getCardViewOnClick(c4,t4);
            }
        });
        c5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getCardViewOnClick(c5,t5);
            }
        });
        c6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getCardViewOnClick(c6,t6);
            }
        });
        c7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getCardViewOnClick(c7,t7);
            }
        });

        //頁數左右按鈕
        LinearLayout left = root.findViewById(R.id.left);
        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getPage((center-1));
            }
        });
        LinearLayout right = root.findViewById(R.id.right);
        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getPage((center+1));
            }
        });


        //搜尋頁數
        EditText page = root.findViewById(R.id.page);
        page.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                switch (i){
                    case EditorInfo.IME_ACTION_SEARCH:
                        if(page.getText().toString().length() > 0){
                            try{
                                int iPage = Integer.parseInt(page.getText().toString());
                                getPage(iPage);
                            }catch (Exception e){
                                break;
                            }
                        }
                        break;
                    default:
                        break;
                }
                return false;
            }
        });

        return root;
    }

    //左右按鈕點擊切換頁數
    public void getPage(int num){
        if(num < st || num > ed){
            return;
        }
        CardView c1 = root.findViewById(R.id.carv1);
        CardView c2 = root.findViewById(R.id.carv2);
        CardView c3 = root.findViewById(R.id.carv3);
        CardView c4 = root.findViewById(R.id.carv4);
        CardView c5 = root.findViewById(R.id.carv5);
        CardView c6 = root.findViewById(R.id.carv6);
        CardView c7 = root.findViewById(R.id.carv7);
        TextView t1 = root.findViewById(R.id.text1);
        TextView t2 = root.findViewById(R.id.text2);
        TextView t3 = root.findViewById(R.id.text3);
        TextView t4 = root.findViewById(R.id.text4);
        TextView t5 = root.findViewById(R.id.text5);
        TextView t6 = root.findViewById(R.id.text6);
        TextView t7 = root.findViewById(R.id.text7);
        t1.setTextColor(getResources().getColor(R.color.black));
        t2.setTextColor(getResources().getColor(R.color.black));
        t3.setTextColor(getResources().getColor(R.color.black));
        t4.setTextColor(getResources().getColor(R.color.black));
        t5.setTextColor(getResources().getColor(R.color.black));
        t6.setTextColor(getResources().getColor(R.color.black));
        t7.setTextColor(getResources().getColor(R.color.black));
        if(num <= (st+2)) {
            cardView.setCardBackgroundColor(getResources().getColor(R.color.white));
            if(num == st) {
                t1.setTextColor(getResources().getColor(R.color.white));
                c1.setCardBackgroundColor(getResources().getColor(R.color.button_blue4));
                cardView = c1;
            }else if(num == (st+1)) {
                t2.setTextColor(getResources().getColor(R.color.white));
                c2.setCardBackgroundColor(getResources().getColor(R.color.button_blue4));
                cardView = c2;
            }else if(num == (st+2)) {
                t3.setTextColor(getResources().getColor(R.color.white));
                c3.setCardBackgroundColor(getResources().getColor(R.color.button_blue4));
                cardView = c3;
            }
            c2.setVisibility(View.VISIBLE);
            c3.setVisibility(View.VISIBLE);
            c5.setVisibility(View.GONE);
            c6.setVisibility(View.GONE);
            t1.setText(String.valueOf(st));
            t2.setText(String.valueOf((st+1)));
            t3.setText(String.valueOf((st+2)));
            t4.setText("...");
            t5.setText("");
            t6.setText("");
            t7.setText(String.valueOf(ed));
        }else if(num > (st+2) && num < (ed-2)) {
            cardView.setCardBackgroundColor(getResources().getColor(R.color.white));
            c4.setCardBackgroundColor(getResources().getColor(R.color.button_blue4));
            t4.setTextColor(getResources().getColor(R.color.white));
            cardView = c4;
            c2.setVisibility(View.VISIBLE);
            c3.setVisibility(View.VISIBLE);
            c5.setVisibility(View.VISIBLE);
            c6.setVisibility(View.VISIBLE);
            t1.setText(String.valueOf(st));
            t2.setText("...");
            t3.setText(String.valueOf((num-1)));
            t4.setText(String.valueOf(num));
            t5.setText(String.valueOf((num+1)));
            t6.setText("...");
            t7.setText(String.valueOf(ed));
        }else if(num >= (ed-2)) {
            cardView.setCardBackgroundColor(getResources().getColor(R.color.white));
            if(num == (ed-2)) {
                t5.setTextColor(getResources().getColor(R.color.white));
                c5.setCardBackgroundColor(getResources().getColor(R.color.button_blue4));
                cardView = c5;
            }else if(num == (ed-1)) {
                t6.setTextColor(getResources().getColor(R.color.white));
                c6.setCardBackgroundColor(getResources().getColor(R.color.button_blue4));
                cardView = c6;
            }else if(num == ed) {
                t7.setTextColor(getResources().getColor(R.color.white));
                c7.setCardBackgroundColor(getResources().getColor(R.color.button_blue4));
                cardView = c7;
            }
            c2.setVisibility(View.GONE);
            c3.setVisibility(View.GONE);
            c5.setVisibility(View.VISIBLE);
            c6.setVisibility(View.VISIBLE);
            t1.setText(String.valueOf(st));
            t2.setText("");
            t3.setText("");
            t4.setText("...");
            t5.setText(String.valueOf((ed-2)));
            t6.setText(String.valueOf((ed-1)));
            t7.setText(String.valueOf(ed));
        }
        center = num;
    }

    //頁數點擊切換
    public void getCardViewOnClick(CardView cv, TextView tv) {
        CardView c2 = root.findViewById(R.id.carv2);
        CardView c3 = root.findViewById(R.id.carv3);
        CardView c4 = root.findViewById(R.id.carv4);
        CardView c5 = root.findViewById(R.id.carv5);
        CardView c6 = root.findViewById(R.id.carv6);
        TextView t1 = root.findViewById(R.id.text1);
        TextView t2 = root.findViewById(R.id.text2);
        TextView t3 = root.findViewById(R.id.text3);
        TextView t4 = root.findViewById(R.id.text4);
        TextView t5 = root.findViewById(R.id.text5);
        TextView t6 = root.findViewById(R.id.text6);
        TextView t7 = root.findViewById(R.id.text7);
        t1.setTextColor(getResources().getColor(R.color.black));
        t2.setTextColor(getResources().getColor(R.color.black));
        t3.setTextColor(getResources().getColor(R.color.black));
        t4.setTextColor(getResources().getColor(R.color.black));
        t5.setTextColor(getResources().getColor(R.color.black));
        t6.setTextColor(getResources().getColor(R.color.black));
        t7.setTextColor(getResources().getColor(R.color.black));
        try {
            String str = tv.getText().toString();
            int i1 = Integer.parseInt(str);
            if(center == i1){
                return;
            }else {
                center = i1;
            }

            if(i1 <= (st+2)) {
                tv.setTextColor(getResources().getColor(R.color.white));
                cardView.setCardBackgroundColor(getResources().getColor(R.color.white));
                cv.setCardBackgroundColor(getResources().getColor(R.color.button_blue4));
                c2.setVisibility(View.VISIBLE);
                c3.setVisibility(View.VISIBLE);
                c5.setVisibility(View.GONE);
                c6.setVisibility(View.GONE);
                t1.setText(String.valueOf(st));
                t2.setText(String.valueOf((st+1)));
                t3.setText(String.valueOf((st+2)));
                t4.setText("...");
                t5.setText("");
                t6.setText("");
                t7.setText(String.valueOf(ed));
                cardView = cv;
            }else if(i1 > (st+2) && i1 < (ed-2)){
                t4.setTextColor(getResources().getColor(R.color.white));
                cardView.setCardBackgroundColor(getResources().getColor(R.color.white));
                c4.setCardBackgroundColor(getResources().getColor(R.color.button_blue4));
                c2.setVisibility(View.VISIBLE);
                c3.setVisibility(View.VISIBLE);
                c5.setVisibility(View.VISIBLE);
                c6.setVisibility(View.VISIBLE);
                t1.setText(String.valueOf(st));
                t2.setText("...");
                t3.setText(String.valueOf((i1-1)));
                t4.setText(String.valueOf(i1));
                t5.setText(String.valueOf((i1+1)));
                t6.setText("...");
                t7.setText(String.valueOf(ed));
                cardView = c4;
            }else if(i1 >= (ed-2)) {
                tv.setTextColor(getResources().getColor(R.color.white));
                cardView.setCardBackgroundColor(getResources().getColor(R.color.white));
                cv.setCardBackgroundColor(getResources().getColor(R.color.button_blue4));
                c2.setVisibility(View.GONE);
                c3.setVisibility(View.GONE);
                c5.setVisibility(View.VISIBLE);
                c6.setVisibility(View.VISIBLE);
                t1.setText(String.valueOf(st));
                t2.setText("");
                t3.setText("");
                t4.setText("...");
                t5.setText(String.valueOf((ed-2)));
                t6.setText(String.valueOf((ed-1)));
                t7.setText(String.valueOf(ed));
                cardView = cv;
            }

        }catch (Exception e){
            return;
        }
    }


    @Override
    public void onResume() {
        super.onResume();
    }


    //連線(測試用 暫時寫在這)
    String url = "http://59.124.100.151:8090/servlet/HRNative/appPinCode";
    private String locale = "";
    public void Client(MainActivity activity, View v, LayoutInflater inflater, ViewGroup container) {

        // 建立 OkHttpClient
        OkHttpSingleton localSingleton = OkHttpSingleton.getInstance();
        OkHttpClient client = localSingleton.getClient();

        locale = CommonHR.getLocale();

        //設置傳送所需夾帶的內容
        FormBody formBody = new FormBody.Builder()
                .add("request", "{'pinCode':'970909'}")
                .add("locale",locale)
                .build();

        // 建立 Request，設定連線資訊
        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();

        // 建立 Call
        Call call = client.newCall(request);

        // 執行 Call 連線
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {

                // 連線成功
                String result = response.body().string();

                try {
                    JSONObject jsonObject1 = new JSONObject(result);
                    String status = jsonObject1.getString("status");
                    String message = (String)jsonObject1.getJSONArray("message").get(0);


                    if("success".equals(status)) {
                        LinearLayout listLayout = root.findViewById(R.id.listLayout);

                        int listHeight = listLayout.getHeight();
                        int itemHeight = itemView.getHeight();

                        Log.e("listLayout","height == "+listHeight);
                        Log.e("itemView","height == "+itemHeight);
                        int x = listHeight / itemHeight;
                        Log.e("x","height == "+x);


                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                listLayout.removeAllViews();
                                listLayout.setVisibility(View.VISIBLE);
                                for(int i=0; i < x; i++) {
                                    itemView = inflater.inflate(R.layout.fragment_partner_item, container, false);
                                    listLayout.addView(itemView);

                                    itemView.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            PartnerFragment partnerFragment = new PartnerFragment();
                                            PartnerDetialFragment partnerDetialFragment = new PartnerDetialFragment();
                                            Bundle bundle = new Bundle();
                                            bundle.putString("empid","123456");
                                            partnerDetialFragment.setArguments(bundle);
                                            FragmentTransaction ft = activity.getSupportFragmentManager().beginTransaction();
                                            ft.add(R.id.fragmentView,partnerDetialFragment,partnerDetialFragment.getClass().getName());
                                            ft.addToBackStack(partnerFragment.getClass().getName());
                                            ft.hide(activity.getSupportFragmentManager().findFragmentByTag(partnerFragment.getClass().getName()));
                                            ft.commit();
                                        }
                                    });
                                }
                            }
                        });

                    }else{
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                            }
                        });
                    }

                } catch (Exception e) {
                    e.printStackTrace();

                    return;
                }finally {

                }

            }

            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }
        });
    }
}