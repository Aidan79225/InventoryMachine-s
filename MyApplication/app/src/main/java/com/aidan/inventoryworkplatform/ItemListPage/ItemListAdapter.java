package com.aidan.inventoryworkplatform.ItemListPage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.aidan.inventoryworkplatform.Entity.Item;
import com.aidan.inventoryworkplatform.R;

import java.util.List;

/**
 * Created by Aidan on 2016/11/20.
 */

public class ItemListAdapter extends BaseAdapter {
    List<Item> itemList;
    Context context;
    public ItemListAdapter(List<Item> itemList){
        this.itemList = itemList;
    }
    @Override
    public int getCount() {
        return itemList.size();
    }

    @Override
    public Item getItem(int position) {
        return itemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(context == null)setContext(parent);
        ViewHolder viewHolder;
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate( R.layout.item_item,null,false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else viewHolder = (ViewHolder)convertView.getTag();

        if(position == 0 ){
            viewHolder.idTextView.setText(R.string.item_id);
            viewHolder.nameTextView.setText(R.string.name);
            viewHolder.statusTextView.setText(R.string.status);
        }else{
            Item item = itemList.get(position-1);
            viewHolder.idTextView.setText(item.getIdNumber());
            viewHolder.nameTextView.setText(item.getName());
            viewHolder.statusTextView.setText(item.isConfirm()?"Y":"N");
        }
        return convertView;
    }
    private void setContext(ViewGroup parent){
            synchronized (ItemListAdapter.class){
                if(context == null){
                    context = parent.getContext();
                }
            }
    }
    class ViewHolder{
        TextView idTextView;
        TextView nameTextView;
        TextView statusTextView;
        public ViewHolder(View v){
            idTextView = (TextView)v.findViewById(R.id.idTextView);
            nameTextView = (TextView)v.findViewById(R.id.nameTextView);
            statusTextView = (TextView)v.findViewById(R.id.statusTextView);
        }
    }
}