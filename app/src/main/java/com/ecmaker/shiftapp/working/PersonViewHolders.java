package com.ecmaker.shiftapp.working;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public abstract class PersonViewHolders extends RecyclerView.ViewHolder {
    public PersonViewHolders(@NonNull @NotNull View itemView) {
        super(itemView);
    }

    /**建立抽象類別，並使onBindViewHolder可分別綁定到介面Ａ與介面Ｂ*/
    public abstract void bindViewHolder(HashMap<String,Object> hashMap);
}
