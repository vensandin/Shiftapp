package com.ecmaker.shiftapp.working;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ecmaker.shiftapp.MainActivity;
import com.ecmaker.shiftapp.R;

import java.util.List;

public class ShopSelectorAdapter extends RecyclerView.Adapter<ShopSelectorAdapter.ViewHolder>{
    private List<ItemsModel_ShopSelector> list;
    private MainActivity activity;
    private View root;
    private PopupWindow popupWindow;

    public ShopSelectorAdapter(List<ItemsModel_ShopSelector>  list, MainActivity activity, View root, PopupWindow popupWindow) {
        this.list = list;
        this.activity = activity;
        this.root = root;
        this.popupWindow = popupWindow;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView content;
        android.widget.LinearLayout LinearLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            content = itemView.findViewById(R.id.content);
            LinearLayout = itemView.findViewById(R.id.LinearLayout_bottom);

        }

    }

    @NonNull
    @Override
    public ShopSelectorAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.shop_selector_itemview, parent, false);
        return new ShopSelectorAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShopSelectorAdapter.ViewHolder holder, int position) {
        ItemsModel_ShopSelector model = list.get(position);
        String code = model.getCode();
        String group = model.getGroup();
        String content = model.getContent();

        holder.content.setText(content);
        holder.LinearLayout.removeAllViews();
        boolean isEnd = model.getEnd();
        if(isEnd && position!=list.size()-1) {
            holder.itemView.post(new Runnable() {
                @Override
                public void run() {
                    //Log.e("test","============isend==="+position);

                    int cellWidth = holder.itemView.getWidth();// this will give you cell width dynamically
                    int cellHeight = holder.itemView.getHeight();// this will give you cell height dynamically
                    Log.e("test", "============cellWidth=" + cellWidth);

                    View view = new View(activity);
                    view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 2));
                    view.setBackgroundColor(activity.getColor(R.color.grey2));
                    holder.LinearLayout.addView(view);

                }
            });
        }
        if(code.length()==0){
            holder.content.setTextColor(activity.getColor(R.color.dropdown_parent));
        }else{
            holder.content.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    TextView storeName = root.findViewById(R.id.shopName);
                    storeName.setText(content);
                    popupWindow.dismiss();
                }
            });
        }


        /*
        View view = new View(activity);
        view.getLayoutParams().width = holder.LinearLayout.getWidth();
        view.setBackgroundColor(activity.getColor(R.color.grey4));
        holder.LinearLayout.addView(view);
        */
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
