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
import android.widget.TextView;

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
    ItemListAdapter adapter;
    ViewGroup rootView;
    ListView itemListView;
    TextView contentTextView;
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
        contentTextView = (TextView)rootView.findViewById(R.id.contentTextView);
    }

    @Override
    public void setListView(List<Item> itemList) {
        adapter = new ItemListAdapter(itemList);
        adapter.setContentInformationTextView(contentTextView);
        itemListView.setAdapter(adapter);
        itemListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                gotoDetailFragment(adapter.getItem(position-1), new RefreshItems() {
                    @Override
                    public void refresh() {
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        });
        itemListView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return false;
            }
        });
        adapter.notifyDataSetChanged();
    }
    private void gotoDetailFragment(Item item,ItemListFragment.RefreshItems refreshItems){
        DialogFragment fragment = ItemDetailFragment.newInstance(item,refreshItems);
//        if(baseFragmentManager != null) baseFragmentManager.loadFragment(fragment);
        fragment.show(getFragmentManager(),ItemDetailFragment.class.getName());
    }



    public interface RefreshItems{
        void refresh();
    }


}
