package com.aidan.inventoryworkplatform.ItemDetailPage;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.aidan.inventoryworkplatform.Entity.Item;
import com.aidan.inventoryworkplatform.ItemListPage.ItemListFragment;
import com.aidan.inventoryworkplatform.R;



/**
 * Created by s352431 on 2016/11/22.
 */
public class ItemDetailFragment extends DialogFragment implements ItemDetailContract.view {
    ItemDetailContract.presenter presenter;
    ViewGroup rootView;
    TextView yearsTextView,buyDateTextView,brandTextView,
            typeTextView,locationTextView,
            nameTextView,itemIdTextView,
            custodyGroupTextView,custodianTextView,
            useGroupTextView,userTextView;
    Button confirmButton,cancelButton;
    ItemListFragment.RefreshItems refreshItems;
    public static ItemDetailFragment newInstance(Item item, ItemListFragment.RefreshItems refreshItems){
        ItemDetailFragment fragment = new ItemDetailFragment();
        fragment.presenter = new ItemDetailPresenter(fragment,item);
        fragment.refreshItems = refreshItems;
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        rootView = (ViewGroup) inflater.inflate(R.layout.fragment_item_detail, container, false);
        presenter.start();
        return rootView;
    }
    @Override
    public void findView(){
        confirmButton = (Button)rootView.findViewById(R.id.confirmButton);
        cancelButton = (Button)rootView.findViewById(R.id.cancelButton);
        yearsTextView = (TextView)rootView.findViewById(R.id.yearsTextView);
        buyDateTextView = (TextView)rootView.findViewById(R.id.buyDateTextView);
        brandTextView = (TextView)rootView.findViewById(R.id.brandTextView);
        typeTextView = (TextView)rootView.findViewById(R.id.typeTextView);

        locationTextView = (TextView)rootView.findViewById(R.id.locationTextView);
        nameTextView = (TextView)rootView.findViewById(R.id.nameTextView);
        itemIdTextView = (TextView)rootView.findViewById(R.id.itemIdTextView);

        custodyGroupTextView = (TextView)rootView.findViewById(R.id.custodyGroupTextView);
        custodianTextView = (TextView)rootView.findViewById(R.id.custodianTextView);
        useGroupTextView = (TextView)rootView.findViewById(R.id.useGroupTextView);
        userTextView = (TextView)rootView.findViewById(R.id.userTextView);
    }
    @Override
    public void setViewValue(Item item){
        yearsTextView.setText(item.getYears());
        buyDateTextView.setText(ADtoCal(item));
        brandTextView.setText(item.getBrand());
        typeTextView.setText(item.getType());
        custodianTextView.setText(item.getCustodian().name);
        custodyGroupTextView.setText(item.getCustodyGroup().name);
        userTextView.setText(item.getUser().name);
        useGroupTextView.setText(item.getUseGroup().name);
        locationTextView.setText(item.getLocation().name);
        nameTextView.setText(item.getName());
        itemIdTextView.setText(item.getIdNumber());
    }
private String ADtoCal(Item item){
    String temp = String.valueOf((Integer.parseInt(item.getDate())-19110000));
    return temp.substring(0,temp.length()-4) + "/" + temp.substring(temp.length()-4,temp.length()-3) + "/" +temp.substring(temp.length()-2);
}
    @Override
    public void setViewClick() {

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.saveItemToChecked(true);
                refreshItems.refresh();
                dismiss();
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.saveItemToChecked(false);
                refreshItems.refresh();
                dismiss();
            }
        });

    }
}
