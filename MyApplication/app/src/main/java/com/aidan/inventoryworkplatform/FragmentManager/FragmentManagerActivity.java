package com.aidan.inventoryworkplatform.FragmentManager;


import android.Manifest;

import static android.Manifest.permission.*;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.provider.SyncStateContract;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.graphics.drawable.RippleDrawable;

import com.aidan.inventoryworkplatform.BaseFragmentManager;
import com.aidan.inventoryworkplatform.Database.AgentDAO;
import com.aidan.inventoryworkplatform.Database.DepartmentDAO;
import com.aidan.inventoryworkplatform.Database.ItemDAO;
import com.aidan.inventoryworkplatform.Database.LocationDAO;
import com.aidan.inventoryworkplatform.Entity.Agent;
import com.aidan.inventoryworkplatform.FilePage.FileFragment;
import com.aidan.inventoryworkplatform.ItemListPage.ItemListFragment;
import com.aidan.inventoryworkplatform.Model.AgentSingleton;
import com.aidan.inventoryworkplatform.Model.DepartmentSingleton;
import com.aidan.inventoryworkplatform.Model.ItemSingleton;
import com.aidan.inventoryworkplatform.Model.LocationSingleton;
import com.aidan.inventoryworkplatform.R;
import com.aidan.inventoryworkplatform.ScannerPage.ScannerFragment;
import com.aidan.inventoryworkplatform.SearchPage.SearchFragment;
import com.aidan.inventoryworkplatform.Singleton;
import com.cipherlab.barcode.GeneralString;
import com.cipherlab.barcode.ReaderManager;
import com.cipherlab.barcode.decoder.BcReaderType;
import com.cipherlab.barcode.decoder.Enable_State;
import com.cipherlab.barcode.decoderparams.ReaderOutputConfiguration;
import com.cipherlab.barcode.*;
import com.cipherlab.barcodebase.*;
import com.cipherlab.barcode.decoder.*;
import com.cipherlab.barcode.decoderparams.*;

public class FragmentManagerActivity extends AppCompatActivity implements FragmentManagerContract.view, BaseFragmentManager {
    FragmentManagerContract.presenter presenter;
    View fragmentContainer;
    TextView fileTextView, scanTextView, searchTextView, itemListTextView, itemDetailTextView;

    private IntentFilter filter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ItemDAO.init(getApplicationContext());
        DepartmentDAO.init(getApplicationContext());
        AgentDAO.init(getApplicationContext());
        LocationDAO.init(getApplicationContext());
        Singleton.setPreference(getApplicationContext());
        setContentView(R.layout.activity_fragment_maneger);
        presenter = new FragmentManagerPresenter(this);
        presenter.start();
    }

    @Override
    public void findView() {
        fragmentContainer = findViewById(R.id.fragmentContainer);
        fileTextView = (TextView) findViewById(R.id.fileTextView);
        scanTextView = (TextView) findViewById(R.id.scanTextView);
        searchTextView = (TextView) findViewById(R.id.searchTextView);
        itemListTextView = (TextView) findViewById(R.id.itemListTextView);
        itemDetailTextView = (TextView) findViewById(R.id.itemDetailTextView);
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
        searchTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadSearchFragment();
            }
        });
        scanTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadScannerFragment();
            }
        });
    }

    @Override
    public void setScanner() {
//        readerManager = ReaderManager.InitInstance(getApplicationContext());
//        readerManager.SetActive(true);


        // ***************************************************//
        // Register for the IntentFilter whose content is the
        // com.cipherlab.barcode.GeneralString.Intent_SOFTTRIGGER_DATA string
        // Later, when myDataReceiver, a BroadcastReceiver class, receives the intent coming from service, it will then be able to deal with something else.
        // ***************************************************//
        filter = new IntentFilter();
        filter.addAction(com.cipherlab.barcode.GeneralString.Intent_SOFTTRIGGER_DATA);
        filter.addAction(com.cipherlab.barcode.GeneralString.Intent_PASS_TO_APP);
        filter.addAction(com.cipherlab.barcode.GeneralString.Intent_READERSERVICE_CONNECTED);
//        registerReceiver(scanReceiver, filter);
    }


    @Override
    public void onDestroy() {
//        ItemSingleton.getInstance().saveToDB();
//        DepartmentSingleton.getInstance().saveToDB();
//        AgentSingleton.getInstance().saveToDB();
//        LocationSingleton.getInstance().saveToDB();
        super.onDestroy();
//        unregisterReceiver(scanReceiver);
//        if (readerManager != null) {
//            readerManager.Release();
//        }
    }

    @Override
    public void loadScannerFragment() {
        Fragment fragment = ScannerFragment.newInstance(this);
        loadFragment(fragment);
    }

    @Override
    public void loadItemListFragment() {
        Fragment fragment = ItemListFragment.newInstance(ItemSingleton.getInstance().getItemList(), this,false);
        loadFragment(fragment);
    }

    @Override
    public void loadFileFragment() {
        Fragment fragment = FileFragment.instantiate(this, FileFragment.class.getName());
        loadFragment(fragment);
    }

    @Override
    public void loadSearchFragment() {
        Fragment fragment = SearchFragment.newInstance(this);
        loadFragment(fragment);
    }

    @Override
    public void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        int backStackCount = fragmentManager.getBackStackEntryCount();
        for (int i = 0; i < backStackCount; i++) {
            fragmentManager.popBackStackImmediate();
        }
        transaction.replace(R.id.fragmentContainer, fragment, fragment.getClass().getName());
        transaction.commit();
    }


}
