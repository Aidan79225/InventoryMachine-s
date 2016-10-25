package com.aidan.inventoryworkplatform.FragmentManager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.aidan.inventoryworkplatform.R;

public class FragmentManagerActivity extends AppCompatActivity implements FragmentManagerContract.view{
    FragmentManagerContract.presenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_maneger);
        presenter = new FragmentManagerPresenter(this);
    }

}
