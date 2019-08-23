package com.aidan.secondinventoryworkplatform.FragmentManager;


import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentFilter;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.aidan.secondinventoryworkplatform.BaseFragmentManager;
import com.aidan.secondinventoryworkplatform.Database.ItemDAO;
import com.aidan.secondinventoryworkplatform.FilePage.FileFragment;
import com.aidan.secondinventoryworkplatform.ItemListPage.ItemListFragment;
import com.aidan.secondinventoryworkplatform.Model.ItemSingleton;
import com.aidan.secondinventoryworkplatform.R;
import com.aidan.secondinventoryworkplatform.ScannerPage.ScannerFragment;
import com.aidan.secondinventoryworkplatform.SearchPage.SearchFragment;
import com.aidan.secondinventoryworkplatform.Singleton;

import java.util.Timer;
import java.util.TimerTask;

public class FragmentManagerActivity extends AppCompatActivity implements FragmentManagerContract.view, BaseFragmentManager {
    FragmentManagerContract.presenter presenter;
    View fragmentContainer;
    TextView fileTextView, scanTextView, searchTextView, itemListTextView, itemDetailTextView;
    private Fragment curFragment;
    private IntentFilter filter;
    ProgressDialog mProgressDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ItemDAO.init(getApplicationContext());
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
        curFragment = fragment;
        transaction.replace(R.id.fragmentContainer, fragment, fragment.getClass().getName());
        transaction.commit();

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(curFragment != null){
            curFragment.onActivityResult(requestCode,resultCode,data);
        }
    }

    /* For Back Control */
    private Boolean hasPressBack = false;
    private Timer timer = new Timer(true);
    private class BackTimerTask extends TimerTask {
        public void run() {
            hasPressBack = false;
        }
    }

    @Override
    public void onBackPressed() {
        if(hasPressBack){
            timer.cancel();
        }else {
            hasPressBack = true;
            Toast.makeText(this, "再按一次離開本程式", Toast.LENGTH_SHORT).show();
            //設定時間點刪去back記錄
            timer.schedule(new BackTimerTask(), 2500);
        }
    }

    @Override
    public void showProgress(final String title) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mProgressDialog = new ProgressDialog(FragmentManagerActivity.this);
                mProgressDialog.setCancelable(false);
                mProgressDialog.setTitle(title);
                mProgressDialog.setMessage("正在處理請稍後...");
                mProgressDialog.setMax(100);
                mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                mProgressDialog.show();
            }
        });
    }

    @Override
    public void hideProgress() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mProgressDialog.dismiss();
            }
        });
    }

    @Override
    public void updateProgress(final int value) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mProgressDialog.setProgress(value);
            }
        });
    }


}
