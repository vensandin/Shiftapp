package com.ecmaker.shiftapp.working;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ecmaker.shiftapp.MainActivity;
import com.ecmaker.shiftapp.R;

import java.util.HashMap;
import java.util.List;

public class PersonAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private List<ItemsModel_Person> itemsModel_people;
    public OnTransClick onItemClick;
    private MainActivity activity;
    private View view;

    public PersonAdapter(List<ItemsModel_Person> itemsModel_people, MainActivity activity, View view) {
        this.itemsModel_people = itemsModel_people;
        this.activity = activity;
        this.view = view;
    }
    /**設置將資料傳回Activity的接口*/
    public void setOnTransButtonClick(OnTransClick onItemClick){
        this.onItemClick = onItemClick;
    }
    /**取得每個item內的"VIEW_TYPE"*/
    @Override
    public int getItemViewType(int position) {
        int getType = Integer.parseInt(itemsModel_people.get(position).getVIEW_TYPE());
        return getType;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        /**在上面的"getItemViewType"中取得的
         * @see viewType
         * 為基準，判斷每個item需使用哪個介面*/
        if (viewType == 1) {
            return new B_TypePersonView(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.person_item_view, parent, false));
        } else {
            return new A_TypePersonView(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.person_item_view, parent, false));
        }
    }
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final int position_s = holder.getAdapterPosition();
        /**將holder強制轉型為"MyViewHolders"類別，使介面Ａ/Ｂ都可以獲得onBindViewHolder內容*/
        ((PersonViewHolders) holder).bindViewHolder(itemsModel_people.get(position).getList());
        /**判斷該item的介面是處於哪一個介面*/
        if (holder instanceof A_TypePersonView){
            //你可以試著為他加入點擊事件～
        }else if (holder instanceof B_TypePersonView){
            B_TypePersonView bTypeMyView = (B_TypePersonView) holder;
            /**設置翻譯按鈕的點擊事件*/
            bTypeMyView.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }

    public interface OnLabelClickListener {
        /**
         * 点击label
         *
         * @param position 点击位置
         */
        void onClick(int position);
    }
    private OnLabelClickListener onLabelClickListener;
    public void setOnLabelClickListener(OnLabelClickListener onLabelClickListener) {
        this.onLabelClickListener = onLabelClickListener;
    }

    @Override
    public int getItemCount() {
        return itemsModel_people.size();
    }

    /**設置點擊方法，使點擊後取得到的內容能傳回MainActivity*/
    public interface OnTransClick{
        void OnTransButtonClick(HashMap<String,String> hashMap);
    }
}
