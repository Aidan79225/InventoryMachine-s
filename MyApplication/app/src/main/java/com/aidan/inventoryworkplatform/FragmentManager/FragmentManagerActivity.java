package com.aidan.inventoryworkplatform.FragmentManager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.aidan.inventoryworkplatform.R;

public class FragmentManagerActivity extends AppCompatActivity implements FragmentManagerContract.view {
    FragmentManagerContract.presenter presenter;
    View fragmentContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_maneger);
        presenter = new FragmentManagerPresenter(this);
    }

    @Override
    public void findView() {
        fragmentContainer = findViewById(R.id.fragmentContainer);
        int atLeastMajorVer = 0,majorVersion =0,atLeastMinorVer =0,minorVersion = 0,atLeastPatchVer=0,patchVersion=0;
        boolean forceflag = false;
        forceflag = atLeastMajorVer > majorVersion || atLeastMinorVer > minorVersion || atLeastPatchVer > patchVersion;
        if (atLeastMajorVer > majorVersion) {
            forceflag = true;
        } else if (atLeastMinorVer > minorVersion) {

            forceflag = true;

        } else if (atLeastPatchVer > patchVersion) {
            forceflag = true;

        }
    }
}
