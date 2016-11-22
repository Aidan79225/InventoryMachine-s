package com.aidan.inventoryworkplatform.ItemListPage;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.aidan.inventoryworkplatform.BaseFragmentManager;
import com.aidan.inventoryworkplatform.Entity.Item;
import com.aidan.inventoryworkplatform.FragmentManager.FragmentManagerActivity;
import com.aidan.inventoryworkplatform.ItemDetailPage.ItemDetailFragment;
import com.aidan.inventoryworkplatform.Model.ItemSingleton;
import com.aidan.inventoryworkplatform.R;

import java.util.List;

/**
 * Created by Aidan on 2016/11/20.
 */

public class ItemListFragment extends DialogFragment implements ItemListContract.view {
    ItemListContract.presenter presenter;
    ViewGroup rootView;
    ListView itemListView;
    BaseFragmentManager baseFragmentManager;
    public static ItemListFragment newInstance(List<Item> itemList,BaseFragmentManager baseFragmentManager){
        ItemListFragment fragment = new ItemListFragment();
        fragment.presenter = new ItemListPresenter(fragment,itemList);
        fragment.baseFragmentManager = baseFragmentManager;
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        rootView = (ViewGroup) inflater.inflate(R.layout.fragment_item_list, container, false);
        if(presenter == null)presenter = new ItemListPresenter(this, ItemSingleton.getInstance().getItemList());
        presenter.start();
        return rootView;
    }

    @Override
    public void findView() {
        itemListView = (ListView) rootView.findViewById(R.id.itemListView);
    }

    @Override
    public void setListView(final ItemListAdapter adapter) {
        itemListView.setAdapter(adapter);
        itemListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                gotoDetailFragment(adapter.getItem(position));
            }
        });
    }
    private void gotoDetailFragment(Item item){
        Fragment fragment = ItemDetailFragment.newInstance(item);
        if(baseFragmentManager != null) baseFragmentManager.loadFragment(fragment);
    }

}
