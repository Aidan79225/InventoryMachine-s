package com.aidan.inventoryworkplatform.ScannerPage;

import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.aidan.inventoryworkplatform.BaseFragmentManager;
import com.aidan.inventoryworkplatform.Entity.Item;
import com.aidan.inventoryworkplatform.ItemDetailPage.ItemDetailFragment;
import com.aidan.inventoryworkplatform.ItemListPage.ItemListAdapter;
import com.aidan.inventoryworkplatform.ItemListPage.ItemListFragment;
import com.aidan.inventoryworkplatform.R;
import com.aidan.inventoryworkplatform.Singleton;
import com.cipherlab.barcode.GeneralString;
import com.cipherlab.barcode.ReaderManager;
import com.cipherlab.barcode.decoder.BcReaderType;
import com.cipherlab.barcode.decoder.Enable_State;
import com.cipherlab.barcode.decoderparams.ReaderOutputConfiguration;

import java.util.List;

import static android.content.Context.INPUT_METHOD_SERVICE;

/**
 * Created by Aidan on 2017/1/22.
 */

public class ScannerFragment extends DialogFragment implements ScannerContract.view {
    ViewGroup rootView;
    EditText scanEditText;
    ListView itemListView;
    ScannerContract.presenter presenter;
    BaseFragmentManager baseFragmentManager;
    ItemListAdapter adapter;
    private ReaderManager readerManager;
    private IntentFilter filter;

    public static ScannerFragment newInstance(BaseFragmentManager baseFragmentManager) {
        ScannerFragment fragment = new ScannerFragment();
        fragment.baseFragmentManager = baseFragmentManager;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        rootView = (ViewGroup) inflater.inflate(R.layout.fragment_scanner, container, false);
        presenter = new ScannerPresenter(this);
        presenter.start();
        return rootView;
    }

    @Override
    public void findView() {
        scanEditText = (EditText) rootView.findViewById(R.id.scanEditText);
        itemListView = (ListView) rootView.findViewById(R.id.itemListView);
    }

    private static final int scanTextNum = 25;

    @Override
    public void onPause() {
        super.onPause();
        getActivity().unregisterReceiver(scanReceiver);
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().registerReceiver(scanReceiver, filter);
    }

    @Override
    public void setEditTextScan() {

        InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(scanEditText.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

        scanEditText.setShowSoftInputOnFocus(false);

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
        readerManager = ReaderManager.InitInstance(getActivity().getApplicationContext());
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
        getActivity().registerReceiver(scanReceiver, filter);


    }

    @Override
    public void setListView(List<Item> itemList) {
        adapter = new ItemListAdapter(itemList);
        itemListView.setAdapter(adapter);
        itemListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                gotoDetailFragment(adapter.getItem(position-1), new ItemListFragment.RefreshItems() {
                    @Override
                    public void refresh() {
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        });
    }
    private void gotoDetailFragment(Item item,ItemListFragment.RefreshItems refreshItems){
        DialogFragment fragment = ItemDetailFragment.newInstance(item,refreshItems);
//        if(baseFragmentManager != null) baseFragmentManager.loadFragment(fragment);
        fragment.show(getFragmentManager(),ItemDetailFragment.class.getName());
    }


    @Override
    public void refreshList() {
        adapter.notifyDataSetChanged();
    }

    private final BroadcastReceiver scanReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // If intent of the Intent_SOFTTRIGGER_DATA string is received
            if (intent.getAction().equals(GeneralString.Intent_SOFTTRIGGER_DATA)) {

                // fetch the data within the intent
                String data = intent.getStringExtra(GeneralString.BcReaderData);

                // display the fetched data
                Singleton.log(data);
                Toast.makeText(context, data, Toast.LENGTH_SHORT).show();
//                    scanEditText.setText(data);
                presenter.scan(data);

//                if (readerManager != null) {
//                    readerManager.SoftScanTrigger();
//                }

            } else if (intent.getAction().equals(GeneralString.Intent_PASS_TO_APP)) {

                // fetch the data within the intent
                String data = intent.getStringExtra(GeneralString.BcReaderData);

                // display the fetched data
                Toast.makeText(context, data, Toast.LENGTH_SHORT).show();
                Singleton.log(data);

            } else if (intent.getAction().equals(GeneralString.Intent_READERSERVICE_CONNECTED)) {
                try {

                    BcReaderType myReaderType = readerManager.GetReaderType();

                    Toast.makeText(context, myReaderType.toString(), Toast.LENGTH_SHORT).show();
                    ReaderOutputConfiguration settings = new ReaderOutputConfiguration();
                    settings.enableKeyboardEmulation = Enable_State.FALSE;
                    readerManager.Set_ReaderOutputConfiguration(settings);
//                    if (readerManager != null) {
//                        readerManager.SoftScanTrigger();
//                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
    };
}
