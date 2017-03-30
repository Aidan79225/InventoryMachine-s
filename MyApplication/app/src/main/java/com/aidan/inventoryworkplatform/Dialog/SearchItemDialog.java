package com.aidan.inventoryworkplatform.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ListView;

import com.aidan.inventoryworkplatform.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aidan on 2017/3/26.
 */

public class SearchItemDialog extends Dialog implements SearchItemAdapter.Closeable {
    EditText searchEditText;
    RecyclerView recyclerView;
    SearchItemAdapter adapter;
    List<SearchableItem> dataList = new ArrayList<>();
    SearchItemAdapter.OnClickListener onClickListener;
    public SearchItemDialog( Context context,List<SearchableItem> dataList) {
        super(context);
        this.dataList = dataList;
    }
    @Override
    public void onCreate(Bundle s){
        super.onCreate(s);
        setContentView(R.layout.dialog_search_item);
        findView();
        setRecyclerView();
        setSearchEditText();
    }
    private void findView(){
        searchEditText = (EditText)findViewById(R.id.searchEditText);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
    }
    private void setRecyclerView(){
        adapter = new SearchItemAdapter(dataList,this);
        adapter.setOnClickListener(onClickListener);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }
    private void setSearchEditText(){
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String key = s.toString();
                if(s.length() == 0){
                    adapter.stopSearch();
                }else{
                    adapter.search(key);
                }
            }
        });
    }
    public void setOnClickListener(SearchItemAdapter.OnClickListener onClickListener){
       this.onClickListener = onClickListener;
    }
    @Override
    public void close() {
        dismiss();
    }

}
