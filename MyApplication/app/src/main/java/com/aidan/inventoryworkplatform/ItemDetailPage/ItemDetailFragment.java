package com.aidan.inventoryworkplatform.ItemDetailPage;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aidan.inventoryworkplatform.Entity.Item;
import com.aidan.inventoryworkplatform.R;



/**
 * Created by s352431 on 2016/11/22.
 */
public class ItemDetailFragment extends DialogFragment implements ItemDetailContract.view {
    ItemDetailContract.presenter presenter;
    ViewGroup rootView;
    TextView yearsTextView,buyDateTextView,brandTextView,
            typeTextView,agentTextView,locationTextView,
            nameTextView,itemIdTextView;
    public static ItemDetailFragment newInstance(Item item){
        ItemDetailFragment fragment = new ItemDetailFragment();
        fragment.presenter = new ItemDetailPresenter(fragment,item);
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        rootView = (ViewGroup) inflater.inflate(R.layout.fragment_item_detail, container, false);
        presenter.start();
        return rootView;
    }
    @Override
    public void findView(){
        yearsTextView = (TextView)rootView.findViewById(R.id.yearsTextView);
        buyDateTextView = (TextView)rootView.findViewById(R.id.buyDateTextView);
        brandTextView = (TextView)rootView.findViewById(R.id.brandTextView);
        typeTextView = (TextView)rootView.findViewById(R.id.typeTextView);
        agentTextView = (TextView)rootView.findViewById(R.id.agentTextView);
        locationTextView = (TextView)rootView.findViewById(R.id.locationTextView);
        nameTextView = (TextView)rootView.findViewById(R.id.nameTextView);
        itemIdTextView = (TextView)rootView.findViewById(R.id.itemIdTextView);
    }
    @Override
    public void setViewValue(Item item){
        yearsTextView.setText(item.getYears());
        buyDateTextView.setText(item.getDate());
        brandTextView.setText(item.getBrand());
        typeTextView.setText(item.getType());
        agentTextView.setText(item.getCustodian().name);
        locationTextView.setText(item.getLocation().name);
        nameTextView.setText(item.getName());
        itemIdTextView.setText(item.getIdNumber());
    }
}
