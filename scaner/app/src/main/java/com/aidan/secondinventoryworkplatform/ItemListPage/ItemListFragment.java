package com.aidan.secondinventoryworkplatform.ItemListPage;

import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.aidan.secondinventoryworkplatform.BaseFragmentManager;
import com.aidan.secondinventoryworkplatform.Entity.Item;
import com.aidan.secondinventoryworkplatform.ItemDetailPage.ItemDetailFragment;
import com.aidan.secondinventoryworkplatform.Model.ItemSingleton;
import com.aidan.secondinventoryworkplatform.R;
import com.aidan.secondinventoryworkplatform.SettingPage.SettingFragment;
import com.aidan.secondinventoryworkplatform.Singleton;
import com.aidan.secondinventoryworkplatform.Utils.SettingsSingleton;

import java.util.List;

import static android.content.Context.INPUT_METHOD_SERVICE;

/**
 * Created by Aidan on 2016/11/20.
 */

public class ItemListFragment extends DialogFragment implements ItemListContract.view {
    private ItemListContract.presenter presenter;
    private ItemListAdapter adapter;
    private ViewGroup rootView;
    private ListView itemListView;
    private TextView contentTextView,settingTextView;
    private BaseFragmentManager baseFragmentManager;
    private EditText scanEditText;
    private boolean showSetAll = false;
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
        itemListView = rootView.findViewById(R.id.itemListView);
        contentTextView = rootView.findViewById(R.id.contentTextView);
        settingTextView = rootView.findViewById(R.id.settingTextView);
        scanEditText = rootView.findViewById(R.id.scanEditText);
        settingTextView.setVisibility(showSetAll ? View.VISIBLE : View.GONE);
        SettingsSingleton.getInstance().getShowScannerInItemList().observe(this, visible -> scanEditText.setVisibility(visible? View.VISIBLE : View.GONE));
    }

    @Override
    public void setListView(List<Item> itemList) {
        adapter = new ItemListAdapter(itemList);
        adapter.setContentInformationTextView(contentTextView);
        itemListView.setAdapter(adapter);
        itemListView.setOnItemClickListener((parent, view, position, id) -> gotoDetailFragment(adapter.getItem(position-1), () -> adapter.notifyDataSetChanged()));
        itemListView.setOnLongClickListener(v -> false);
        adapter.notifyDataSetChanged();
        settingTextView.setOnClickListener(v -> {
            DialogFragment dialogFragment = SettingFragment.newInstance(baseFragmentManager, adapter.getItems(), () -> adapter.notifyDataSetChanged());
            dialogFragment.show(getFragmentManager(),dialogFragment.getClass().getName());
        });
    }
    private void gotoDetailFragment(Item item,ItemListFragment.RefreshItems refreshItems){
        DialogFragment fragment = ItemDetailFragment.newInstance(item,refreshItems);
        fragment.show(getFragmentManager(),ItemDetailFragment.class.getName());
    }

    public interface RefreshItems{
        void refresh();
    }

    @Override
    public void setEditTextScan() {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(scanEditText.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            scanEditText.setShowSoftInputOnFocus(false);
        }
        scanEditText.requestFocus();
        scanEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() != 0) {
                    Singleton.log(s.toString());
                    presenter.scan(s.toString());
                    scanEditText.setText("");
                }
            }
        });
    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void refreshList() {
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showItem(Item item) {
        gotoDetailFragment(item, () -> adapter.notifyDataSetChanged());
    }

}
