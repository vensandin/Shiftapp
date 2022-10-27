package com.ecmaker.shiftapp.partner;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.ecmaker.shiftapp.MainActivity;
import com.ecmaker.shiftapp.R;

import java.util.List;

public class VacationAdapter extends RecyclerView.Adapter<VacationAdapter.ViewHolder>{
    private List<ItemsModel_Vacation> list;
    private MainActivity activity;

    public VacationAdapter(List<ItemsModel_Vacation>  list, MainActivity activity) {
        this.list = list;
        this.activity = activity;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView type;
        TextView startDate;
        TextView endDate;
        TextView days;
        TextView reason;
        TextView agent;
        ImageView arrow;
        LinearLayout ll_reason;
        CardView cardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            type = itemView.findViewById(R.id.type);
            startDate = itemView.findViewById(R.id.startDate);
            endDate = itemView.findViewById(R.id.endDate);
            days = itemView.findViewById(R.id.days);
            reason = itemView.findViewById(R.id.reason);
            agent = itemView.findViewById(R.id.agent);
            arrow = itemView.findViewById(R.id.arrow);
            ll_reason = itemView.findViewById(R.id.ll_reason);
            cardView = itemView.findViewById(R.id.cardView);
        }
    }

    @NonNull
    @Override
    public VacationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_vacation_item, parent, false);
        return new VacationAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VacationAdapter.ViewHolder holder, int position) {
        ItemsModel_Vacation model = list.get(position);
        holder.type.setText(model.getType());
        holder.startDate.setText(model.getStartDate());
        holder.endDate.setText(model.getEndDate());
        holder.days.setText(model.getDays());
        holder.agent.setText(model.getAgent());
        boolean isOpen = model.getIsOpen();
        if(isOpen){
            holder.ll_reason.setVisibility(View.VISIBLE);
            holder.reason.setText(model.getReason());
        }else{
            holder.ll_reason.setVisibility(View.GONE);
            holder.reason.setText("");
        }
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean tempIsOpen = model.getIsOpen();
                if(tempIsOpen){
                    model.setIsOpen(false);
                    holder.ll_reason.setVisibility(View.GONE);
                    holder.reason.setText("");
                    holder.arrow.setImageResource(R.drawable.salary_down_8);
                }else{
                    model.setIsOpen(true);
                    holder.ll_reason.setVisibility(View.VISIBLE);
                    holder.reason.setText(model.getReason());
                    holder.arrow.setImageResource(R.drawable.salary_up_8);
                }
            }

        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
