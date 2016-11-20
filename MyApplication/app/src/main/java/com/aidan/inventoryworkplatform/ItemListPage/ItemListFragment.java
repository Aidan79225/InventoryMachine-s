package com.aidan.inventoryworkplatform.ItemListPage;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.aidan.inventoryworkplatform.R;

/**
 * Created by Aidan on 2016/11/20.
 */

public class ItemListFragment extends DialogFragment implements ItemListContract.view {
    ItemListContract.presenter presenter;
    ViewGroup rootView;
    ListView itemListView;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        rootView = (ViewGroup) inflater.inflate(R.layout.fragment_item_list, container, false);
        presenter = new ItemListPresenter(this);
        presenter.start();
        return rootView;
    }

    @Override
    public void findView() {
        itemListView = (ListView) rootView.findViewById(R.id.itemListView);
    }

    @Override
    public void setListView(BaseAdapter adapter) {
        itemListView.setAdapter(adapter);
    }

}
