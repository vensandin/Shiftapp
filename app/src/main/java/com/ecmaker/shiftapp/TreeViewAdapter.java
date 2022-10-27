package com.ecmaker.shiftapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class TreeViewAdapter extends BaseAdapter {
    /** 元素資料來源 */
    private ArrayList<Element> elementsData;
    /** 樹中元素 */
    private ArrayList<Element> elements;
    /** LayoutInflater */
    private LayoutInflater inflater;
    /** item的行首縮排基數 */
    private int indentionBase;
    public TreeViewAdapter(ArrayList<Element> elements, ArrayList<Element> elementsData, LayoutInflater inflater) {
        this.elements = elements;
        this.elementsData = elementsData;
        this.inflater = inflater;
        indentionBase = 50;
    }
    public ArrayList<Element> getElements() {
        return elements;
    }
    public ArrayList<Element> getElementsData() {
        return elementsData;
    }
    @Override
    public int getCount() {
        return elements.size();
    }
    @Override
    public Object getItem(int position) {
        return elements.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.treeview_item, null);
            holder.disclosureImg = (ImageView) convertView.findViewById(R.id.disclosureImg);
            holder.contentText = (TextView) convertView.findViewById(R.id.contentText);
            holder.icon = (ImageView)convertView.findViewById(R.id.icon);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Element element = elements.get(position);
        int level = element.getLevel();

        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(holder.icon.getLayoutParams());
        lp.setMargins(indentionBase * (level+1), 0, 0, 0);
        holder.icon.setLayoutParams(lp);
        if(level==0){
            holder.icon.setVisibility(View.VISIBLE);
            holder.icon.setImageResource(element.getIcon());
        }else{
            holder.icon.setVisibility(View.INVISIBLE);
        }
        holder.contentText.setText(element.getContentText());
        if (element.isHasChildren() && !element.isExpanded()) {
            holder.disclosureImg.setImageResource(R.drawable.salary_down_8);
//這裡要主動設定一下icon可見，因為convertView有可能是重用了"設定了不可見"的view，下同。
            holder.disclosureImg.setVisibility(View.VISIBLE);
        } else if (element.isHasChildren() && element.isExpanded()) {
            holder.disclosureImg.setImageResource(R.drawable.salary_up_8);
            holder.disclosureImg.setVisibility(View.VISIBLE);
        } else if (!element.isHasChildren()) {
            holder.disclosureImg.setImageResource(R.drawable.salary_down_8);
            holder.disclosureImg.setVisibility(View.INVISIBLE);
        }
        return convertView;
    }
    /**
     * 優化Holder
     * @author carrey
     *
     */
    static class ViewHolder{
        ImageView icon;
        ImageView disclosureImg;
        TextView contentText;
    }
}
