package com.aidan.secondinventoryworkplatform.ItemListPage;

import android.app.DialogFragment;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.aidan.secondinventoryworkplatform.BaseFragmentManager;
import com.aidan.secondinventoryworkplatform.Entity.Item;
import com.aidan.secondinventoryworkplatform.ItemDetailPage.ItemDetailFragment;
import com.aidan.secondinventoryworkplatform.Model.ItemSingleton;
import com.aidan.secondinventoryworkplatform.R;
import com.aidan.secondinventoryworkplatform.SettingPage.SettingFragment;

import java.util.List;

/**
 * Created by Aidan on 2016/11/20.
 */

public class ItemListFragment extends DialogFragment implements ItemListContract.view {
    ItemListContract.presenter presenter;
    ItemListAdapter adapter;
    ViewGroup rootView;
    ListView itemListView;
    TextView contentTextView,settingTextView;
    BaseFragmentManager baseFragmentManager;
    boolean showSetAll = false;
    public static ItemListFragment newInstance(List<Item> itemList,BaseFragmentManager baseFragmentManager,boolean showSetAll){
        ItemListFragment fragment = new ItemListFragment();
        fragment.presenter = new ItemListPresenter(fragment,itemList);
        fragment.baseFragmentManager = baseFragmentManager;
        fragment.showSetAll = showSetAll;
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
        settingTextView =(TextView)rootView.findViewById(R.id.settingTextView);
        settingTextView.setVisibility(showSetAll ? View.VISIBLE : View.GONE);
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
        settingTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dialogFragment = SettingFragment.newInstance(baseFragmentManager, adapter.getItems(), new SettingFragment.Reload() {
                    @Override
                    public void reload() {
                        adapter.notifyDataSetChanged();
                    }
                });
                dialogFragment.show(getFragmentManager(),dialogFragment.getClass().getName());
            }
        });
    }
    private void gotoDetailFragment(Item item,ItemListFragment.RefreshItems refreshItems){
        DialogFragment fragment = ItemDetailFragment.newInstance(item,refreshItems);
        fragment.show(getFragmentManager(),ItemDetailFragment.class.getName());
    }

    public interface RefreshItems{
        void refresh();
    }
}
