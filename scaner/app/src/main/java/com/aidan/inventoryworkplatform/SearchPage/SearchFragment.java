package com.aidan.inventoryworkplatform.SearchPage;

import android.app.AlertDialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.aidan.inventoryworkplatform.BaseFragmentManager;
import com.aidan.inventoryworkplatform.Dialog.SearchItemAdapter;
import com.aidan.inventoryworkplatform.Dialog.SearchItemDialog;
import com.aidan.inventoryworkplatform.Dialog.SearchableItem;
import com.aidan.inventoryworkplatform.Entity.Item;
import com.aidan.inventoryworkplatform.Entity.Location;
import com.aidan.inventoryworkplatform.ItemListPage.ItemListFragment;
import com.aidan.inventoryworkplatform.Model.ItemSingleton;
import com.aidan.inventoryworkplatform.Model.LocationSingleton;
import com.aidan.inventoryworkplatform.R;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Aidan on 2017/1/8.
 */

public class SearchFragment extends DialogFragment implements SearchContract.view {
    SearchContract.presenter presenter;
    ViewGroup rootView;
    EditText idEditText, serialNumberEditText;
    TextView locationTextView, agentTextView, departmentTextView;
    TextView searchTextView, clearTextView;
    TextView useGroupTextView, userTextView;
    BaseFragmentManager baseFragmentManager;

    public static SearchFragment newInstance(BaseFragmentManager baseFragmentManager) {
        SearchFragment fragment = new SearchFragment();
        fragment.baseFragmentManager = baseFragmentManager;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        rootView = (ViewGroup) inflater.inflate(R.layout.fragment_search, container, false);
        if (presenter == null) presenter = new SearchPresenter(this);
        presenter.start();
        return rootView;
    }

    @Override
    public void findView() {
        idEditText = (EditText) rootView.findViewById(R.id.idEditText);
        serialNumberEditText = (EditText) rootView.findViewById(R.id.serialNumberEditText);
        locationTextView = (TextView) rootView.findViewById(R.id.locationTextView);
        agentTextView = (TextView) rootView.findViewById(R.id.agentTextView);
        departmentTextView = (TextView) rootView.findViewById(R.id.departmentTextView);
        searchTextView = (TextView) rootView.findViewById(R.id.searchTextView);
        clearTextView = (TextView) rootView.findViewById(R.id.clearTextView);
        useGroupTextView = (TextView) rootView.findViewById(R.id.useGroupTextView);
        userTextView = (TextView) rootView.findViewById(R.id.userTextView);
    }

    @Override
    public void setViewClick() {
        locationTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.locationTextViewClick(locationTextView);
            }
        });
        agentTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.agentTextViewClick(agentTextView);
            }
        });
        departmentTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.departmentTextViewClick(departmentTextView);
            }
        });
        userTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.userTextViewClick(userTextView);
            }
        });
        useGroupTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.useGroupTextViewClick(useGroupTextView);
            }
        });
        clearTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.clearAll();
            }
        });
        searchTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.searchTextViewClick(idEditText.getText().toString(), serialNumberEditText.getText().toString());
            }
        });
        idEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length() == 11){
                    serialNumberEditText.requestFocus();
                }
            }
        });
    }

    @Override
    public void clearViews() {
        idEditText.setText("");
        serialNumberEditText.setText("");
        locationTextView.setText("請點選存置地點");
        agentTextView.setText("請點選保管人");
        departmentTextView.setText("請點選保管單位");
    }

    @Override
    public void showSetDialog(SearchItemAdapter.OnClickListener clickListener, String title, List<SearchableItem> dataList) {
        SearchItemDialog dialog = new SearchItemDialog(getActivity(),dataList);
        dialog.setTitle(title);
        dialog.setOnClickListener(clickListener);
        dialog.show();
    }

    @Override
    public void showFragmentWithResult(List<Item> items) {
        Fragment fragment = ItemListFragment.newInstance(items, baseFragmentManager,true);
        baseFragmentManager.loadFragment(fragment);
    }


}
