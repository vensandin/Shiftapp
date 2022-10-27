package com.ecmaker.shiftapp.working;

import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.ecmaker.shiftapp.MainActivity;
import com.ecmaker.shiftapp.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class WorkingAdapter extends RecyclerView.Adapter<WorkingAdapter.ViewHolder> implements Filterable {
    private List<ItemsModel_Working> itemsModel_working;
    private List<ItemsModel_Working> itemsModel_working_Filtered;
    private MainActivity activity;
    private Context context;
    private int type;


    public WorkingAdapter(List<ItemsModel_Working> itemsModel_working, MainActivity activity, Context context, int type) {
        this.itemsModel_working = itemsModel_working;
        this.itemsModel_working_Filtered = itemsModel_working;
        this.activity = activity;
        this.context = context;
        this.type = type;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout layout_item;
        private TextView working;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            layout_item = itemView.findViewById(R.id.layout_item);
            working = itemView.findViewById(R.id.working);
        }
    }

    @NonNull
    @Override
    public WorkingAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.working_item_view, parent, false);
        return new WorkingAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WorkingAdapter.ViewHolder holder, int position) {
        int position_s = holder.getAdapterPosition();
        String key = itemsModel_working_Filtered.get(position_s).getKey();
        String Working = itemsModel_working_Filtered.get(position_s).getWorking();
        holder.working.setText(Working);
        Log.e("WorkingAdapter","run Holder");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WorkingFragment.fWorkingId = key;
                WorkingFragment.fWorkingName = Working;
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                PopupWindow popupWindow = new PopupWindow(activity);
                View popupView = LayoutInflater.from(activity).inflate(R.layout.working_popupwindow1,null);
                if(type == 2){//常用班別
                    popupView = LayoutInflater.from(activity).inflate(R.layout.working_popupwindow2,null);
                }

                //取得元件X軸Y軸
                int[] xy = new int[2];
                view.getLocationOnScreen(xy);
                //取得元件高度
                int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
                int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
                popupView.measure(w,h);
                int itemHeight = popupView.getMeasuredHeight();

                CardView cardView = popupView.findViewById(R.id.cardView);
                cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(type == 1) {//全部班別
                            boolean isPut = true;
                            List<ItemsModel_Working> list_working_often = WorkingFragment.list_working_often;
                            for(int i=0; i < list_working_often.size(); i++){
                                String iWorking = list_working_often.get(i).getWorking();
                                if(iWorking.equals(Working)){
                                    isPut = false;
                                }
                            }
                            if(isPut) {
                                HashMap<String, Object> hashMap = new HashMap<>();
                                hashMap.put("working", Working);
                                ItemsModel_Working itemsModel = new ItemsModel_Working(hashMap);
                                WorkingFragment.list_working_often.add(itemsModel);
                            }
                        }else if(type == 2){//常用班別
                            List<ItemsModel_Working> list_working_often = WorkingFragment.list_working_often;
                            for(int i=0; i < list_working_often.size(); i++){
                                String iWorking = list_working_often.get(i).getWorking();
                                if(iWorking.equals(Working)){
                                    list_working_often.remove(i);
                                    break;
                                }
                            }
                            WorkingFragment.list_working_often = list_working_often;
                            notifyDataSetChanged();
                        }
                        popupWindow.dismiss();
                    }
                });

                popupWindow.setContentView(popupView);
                popupWindow.setBackgroundDrawable(null);
                popupWindow.setFocusable(true);
                popupWindow.showAtLocation(popupView, Gravity.NO_GRAVITY,xy[0],xy[1]-itemHeight);

                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        //若無資料時使用關鍵字查詢會null 所以這邊要做判斷
        if(itemsModel_working_Filtered == null){
            return 0;
        }else {
            return itemsModel_working_Filtered.size();
        }
    }

    //關鍵字 動態搜尋
    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                FilterResults filterResults = new FilterResults();

                if(constraint == null || constraint.length() == 0){
                    Log.e("constraint","null");
                    filterResults.count = itemsModel_working.size();
                    filterResults.values = itemsModel_working;
                }else{
                    String searchStr = constraint.toString();
                    Log.e("searchStr",searchStr);
                    List<ItemsModel_Working> resultData = new ArrayList<>();

                    for(ItemsModel_Working itemsModel:itemsModel_working){
                        String working = itemsModel.getWorking();
                        Log.e("working",working);
                        boolean isAdd = false;//如有多項關鍵字查詢 可用這個判斷是不是有重複寫入
                        if(working.toLowerCase().contains(searchStr.toLowerCase())){
                            Log.e("contains","true");
                            if(!isAdd) {
                                Log.e("Add","Add");
                                resultData.add(itemsModel);
                                isAdd = true;
                            }
                        }

                        filterResults.count = resultData.size();
                        filterResults.values = resultData;
                    }
                }

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                itemsModel_working_Filtered = (List<ItemsModel_Working>) results.values;
                if(itemsModel_working_Filtered == null) {
                    Log.e("Filtered size", "null");
                }else{
                    Log.e("Filtered size", "" + itemsModel_working_Filtered.size());
                }

                notifyDataSetChanged();
            }
        };

        return filter;
    }
}
