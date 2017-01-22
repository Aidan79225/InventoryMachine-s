package com.aidan.inventoryworkplatform.FragmentManager;

/**
 * Created by Aidan on 2016/10/25.
 */

public class FragmentManagerPresenter implements FragmentManagerContract.presenter {
    FragmentManagerContract.view view;
    public FragmentManagerPresenter(FragmentManagerContract.view view){
        this.view = view;
    }

    @Override
    public void start() {
        view.findView();
        view.setViewClick();
        view.setScanner();
    }
}
