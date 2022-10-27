package com.ecmaker.shiftapp.working;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ecmaker.shiftapp.MainActivity;
import com.ecmaker.shiftapp.R;

import java.util.List;

public class FilterSelectorAdapter extends RecyclerView.Adapter<FilterSelectorAdapter.ViewHolder>{
    private List<ItemsModel_FilterSelector> list;
    private MainActivity activity;

    public FilterSelectorAdapter(List<ItemsModel_FilterSelector>  list, MainActivity activity) {
        this.list = list;
        this.activity = activity;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CheckBox checkBox;
        TextView content;
        LinearLayout LinearLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.checkBox);
            content = itemView.findViewById(R.id.content);
            LinearLayout = itemView.findViewById(R.id.LinearLayout_bottom);
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    int position = getAdapterPosition();

                    ItemsModel_FilterSelector model = list.get(position);
                    String code = model.getCode();
                    String group = model.getGroup();
                    boolean check = model.getCheck();

                    if(code.length()==0) {  //全選
                        Log.e("test","=========code=="+position);
                        for (int i = position+1; i < list.size(); i++) {
                            ItemsModel_FilterSelector iMode = list.get(i);
                            String iGroup = iMode.getGroup();
                            boolean iCheck = iMode.getCheck();
                            if (iGroup.equals(group)) {
                                if(iCheck==b){
                                    continue;
                                }
                                iMode.setCheck(b);
                                int finalI = i;
                                activity.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Log.e("test","=============finalI:"+finalI);
                                        notifyItemChanged(finalI);
                                    }
                                });
                            }

                        }
                    }else{
                        if(check==b){
                            return;
                        }
                        list.get(position).setCheck(b);
                        boolean isAll_check = true;
                        int parent_position = 0;
                        for(int i=0;i<list.size();i++){
                            ItemsModel_FilterSelector iMode = list.get(i);
                            String iCode = iMode.getCode();
                            String iGroup = iMode.getGroup();
                            boolean iCheck = iMode.getCheck();
                            if(iGroup.equals(group)) {
                                if (iCode.length() == 0) {
                                    parent_position = i;
                                    continue;
                                } else {
                                    if(!iCheck){
                                        isAll_check = false;
                                    }

                                }
                            }
                        }
                        if(isAll_check){
                            ItemsModel_FilterSelector iMode = list.get(parent_position);
                            iMode.setCheck(true);
                            int finalParent_position = parent_position;
                            activity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    notifyItemChanged(finalParent_position);
                                }
                            });
                        }else{
                            ItemsModel_FilterSelector iMode = list.get(parent_position);
                            iMode.setCheck(false);
                            int finalParent_position = parent_position;
                            activity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    notifyItemChanged(finalParent_position);
                                }
                            });
                        }


                    }
                }
            });
        }

    }

    @NonNull
    @Override
    public FilterSelectorAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.filter_selector_itemview, parent, false);
        return new FilterSelectorAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FilterSelectorAdapter.ViewHolder holder, int position) {
        ItemsModel_FilterSelector model = list.get(position);
        String code = model.getCode();
        String group = model.getGroup();
        holder.checkBox.setChecked(model.getCheck());
        holder.content.setText(model.getContent());
        holder.LinearLayout.removeAllViews();
        boolean isEnd = model.getEnd();
        if(isEnd && position!=list.size()-1) {
            holder.itemView.post(new Runnable() {
                @Override
                public void run() {
                    //Log.e("test","============isend==="+position);

                    int cellWidth = holder.itemView.getWidth();// this will give you cell width dynamically
                    int cellHeight = holder.itemView.getHeight();// this will give you cell height dynamically
                    //Log.e("test", "============cellWidth=" + cellWidth);

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
            holder.content.setTextColor(activity.getColor(R.color.dropdown_child));
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
