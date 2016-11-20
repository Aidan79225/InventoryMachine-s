package com.aidan.inventoryworkplatform.FragmentManager;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.aidan.inventoryworkplatform.FilePage.FileFragment;
import com.aidan.inventoryworkplatform.ItemListPage.ItemListFragment;
import com.aidan.inventoryworkplatform.R;

public class FragmentManagerActivity extends AppCompatActivity implements FragmentManagerContract.view {
    FragmentManagerContract.presenter presenter;
    View fragmentContainer;
    TextView fileTextView,scanTextView,searchTextView,itemListTextView,itemDetailTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_maneger);
        presenter = new FragmentManagerPresenter(this);
        presenter.start();
    }

    @Override
    public void findView() {
        fragmentContainer = findViewById(R.id.fragmentContainer);
        fileTextView = (TextView)findViewById(R.id.fileTextView);
        scanTextView = (TextView)findViewById(R.id.scanTextView);
        searchTextView = (TextView)findViewById(R.id.searchTextView);
        itemListTextView = (TextView)findViewById(R.id.itemListTextView);
        itemDetailTextView = (TextView)findViewById(R.id.itemDetailTextView);
        loadFileFragment();
    }

    @Override
    public void setViewClick() {
        fileTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFileFragment();
            }
        });
        itemListTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadItemListFragment();
            }
        });
    }

    public void loadItemListFragment() {
        Fragment fragment = ItemListFragment.instantiate(this,ItemListFragment.class.getName());
        loadFragment(fragment);
    }
    public void loadFileFragment() {
        Fragment fragment = FileFragment.instantiate(this,FileFragment.class.getName());
        loadFragment(fragment);
    }

    public void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        int backStackCount = fragmentManager.getBackStackEntryCount();
        for (int i = 0; i < backStackCount; i++) {
            fragmentManager.popBackStackImmediate();
        }
        transaction.replace(R.id.fragmentContainer, fragment, fragment.getClass().getName());
        transaction.commit();
//        fragmentList.add(fragment);
    }

}
