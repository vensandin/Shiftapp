package com.ecmaker.shiftapp.working;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.util.TypedValue;
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

import com.ecmaker.shiftapp.EditTextConfirmDialog;
import com.ecmaker.shiftapp.MainActivity;
import com.ecmaker.shiftapp.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CustomizeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements Filterable {
    private List<ItemsModel_Customize> itemsModel_customize;
    private List<ItemsModel_Customize> itemsModel_customize_Filtered;
    public CustomizeAdapter.OnTransClick onItemClick;
    private MainActivity activity;
    private Context context;
    private EditTextConfirmDialog editTextConfirmDialog;

    public CustomizeAdapter(List<ItemsModel_Customize> itemsModel_customize, MainActivity activity, Context context) {
        this.itemsModel_customize = itemsModel_customize;
        this.itemsModel_customize_Filtered = itemsModel_customize;
        this.activity = activity;
        this.context = context;
    }

    /**設置將資料傳回Activity的接口*/
    public void setOnTransButtonClick(CustomizeAdapter.OnTransClick onItemClick){
        this.onItemClick = onItemClick;
    }

    /**取得每個item內的"VIEW_TYPE"*/
    @Override
    public int getItemViewType(int position) {
        int getType = Integer.parseInt(itemsModel_customize.get(position).getVIEW_TYPE());
        return getType;
    }


    public class CustomizeAddView extends CustomizeViewHolders{
        private LinearLayout layout_item;
        public CustomizeAddView(@NonNull @NotNull View itemView) {
            super(itemView);
            layout_item = itemView.findViewById(R.id.layout_item);
        }

        @Override
        public void bindViewHolder(HashMap<String, Object> hashMap) {
        }
    }

    public class CustomizeDataView extends CustomizeViewHolders{
        private LinearLayout layout_item;
        private TextView working;
        public CustomizeDataView(@NonNull @NotNull View itemView) {
            super(itemView);
            layout_item = itemView.findViewById(R.id.layout_item);
            working = itemView.findViewById(R.id.working);
        }

        @Override
        public void bindViewHolder(HashMap<String, Object> hashMap) {
            working.setText((String)hashMap.get("working"));
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        /**在上面的"getItemViewType"中取得的
         * @see viewType
         * 為基準，判斷每個item需使用哪個介面*/
        if (viewType == 1) {
            return new CustomizeAddView(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.customize_add_item_view, parent, false));
        } else {
            return new CustomizeDataView(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.customize_data_item_view, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        int position_s = holder.getAdapterPosition();
        /**將holder強制轉型為"MyViewHolders"類別，使介面Ａ/Ｂ都可以獲得onBindViewHolder內容*/
        ((CustomizeViewHolders) holder).bindViewHolder(itemsModel_customize.get(position).getList());
        /**判斷該item的介面是處於哪一個介面*/
        if (holder instanceof CustomizeAddView){
            //你可以試著為他加入點擊事件～
            CustomizeAddView customizeAddView = (CustomizeAddView)holder;
            customizeAddView.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    /**
                    if (onLabelClickListener != null) {
                        onLabelClickListener.onClick(position,itemsModel_customize_Filtered);
                    }
                    **/
                    //if(editTextConfirmDialog == null) {
                        Resources r = activity.getResources();
                        int dp400 = Math.round(TypedValue.applyDimension(
                                TypedValue.COMPLEX_UNIT_DIP, 400, r.getDisplayMetrics()));
                        int dp250 = Math.round(TypedValue.applyDimension(
                                TypedValue.COMPLEX_UNIT_DIP, 250, r.getDisplayMetrics()));
                        editTextConfirmDialog = new EditTextConfirmDialog(context, "新增備註", "確認");
                        editTextConfirmDialog.getWindow().setLayout(dp400, dp250);
                        editTextConfirmDialog.show();

                        editTextConfirmDialog.setOnLabelClickListener(new EditTextConfirmDialog.OnLabelClickListener() {
                            @Override
                            public void onSuccess() {
                                String memo = editTextConfirmDialog.getMemo();
                                HashMap<String, Object> hashMap = new HashMap<>();
                                hashMap.put("VIEW_TYPE", "2");
                                hashMap.put("working", memo);
                                ItemsModel_Customize itemsModel = new ItemsModel_Customize(hashMap);
                                WorkingFragment.list_working_customize.add(itemsModel);
                                notifyDataSetChanged();

                                editTextConfirmDialog.dismiss();
                            }
                        });
                    //}
                }
            });

        }else if(holder instanceof CustomizeDataView){
            CustomizeDataView customizeDataView = (CustomizeDataView)holder;

            customizeDataView.working.setText(itemsModel_customize.get(position).getWorking());

            customizeDataView.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    PopupWindow popupWindow = new PopupWindow(activity);
                    View popupView = LayoutInflater.from(activity).inflate(R.layout.working_popupwindow3,null);

                    //取得元件X軸Y軸
                    int[] xy = new int[2];
                    view.getLocationOnScreen(xy);
                    //取得元件高度
                    int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
                    int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
                    popupView.measure(w,h);
                    int itemHeight = popupView.getMeasuredHeight();

                    //修改
                    CardView edit = popupView.findViewById(R.id.edit);
                    edit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            popupWindow.dismiss();
                        }
                    });
                    //刪除
                    CardView delete = popupView.findViewById(R.id.delete);
                    delete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            List<ItemsModel_Customize> list_customize = WorkingFragment.list_working_customize;
                            list_customize.remove(position_s);
                            WorkingFragment.list_working_customize = list_customize;
                            popupWindow.dismiss();
                            notifyDataSetChanged();
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

    }

    @Override
    public int getItemCount() {
        //若無資料時使用關鍵字查詢會null 所以這邊要做判斷
        if(itemsModel_customize_Filtered == null){
            return 0;
        }else {
            return itemsModel_customize_Filtered.size();
        }
    }

    /**設置點擊方法，使點擊後取得到的內容能傳回MainActivity*/
    public interface OnTransClick{
        void OnTransButtonClick(HashMap<String,String> hashMap);
    }

    public interface OnLabelClickListener{
        /**
         * 点击label
         *
         * @param position 点击位置
         */
        void onClick(int position, List<ItemsModel_Customize> itemsModel);
    }
    private CustomizeAdapter.OnLabelClickListener onLabelClickListener;
    public void setOnLabelClickListener(CustomizeAdapter.OnLabelClickListener onLabelClickListener){
        this.onLabelClickListener = onLabelClickListener;

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
                    filterResults.count = itemsModel_customize.size();
                    filterResults.values = itemsModel_customize;
                }else{
                    String searchStr = constraint.toString();
                    Log.e("searchStr",searchStr);
                    List<ItemsModel_Customize> resultData = new ArrayList<>();

                    for(ItemsModel_Customize itemsModel:itemsModel_customize){
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
                itemsModel_customize_Filtered = (List<ItemsModel_Customize>) results.values;
                if(itemsModel_customize_Filtered == null) {
                    Log.e("Filtered size", "null");
                }else{
                    Log.e("Filtered size", "" + itemsModel_customize_Filtered.size());
                }

                notifyDataSetChanged();
            }
        };

        return filter;
    }
}
