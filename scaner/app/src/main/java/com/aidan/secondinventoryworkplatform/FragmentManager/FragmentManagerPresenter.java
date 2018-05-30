package com.aidan.secondinventoryworkplatform.FragmentManager;

import com.aidan.secondinventoryworkplatform.Model.SelecetableSingleton.AgentSingleton;
import com.aidan.secondinventoryworkplatform.Model.SelecetableSingleton.ApprovalNumberSingleton;
import com.aidan.secondinventoryworkplatform.Model.SelecetableSingleton.ChangeTargetSingleton;
import com.aidan.secondinventoryworkplatform.Model.SelecetableSingleton.DepartmentSingleton;
import com.aidan.secondinventoryworkplatform.Model.ItemSingleton;
import com.aidan.secondinventoryworkplatform.Model.SelecetableSingleton.DepositPlaceSingleton;
import com.aidan.secondinventoryworkplatform.Model.SelecetableSingleton.ImpairmentReasonSingleton;
import com.aidan.secondinventoryworkplatform.Model.SelecetableSingleton.LocationSingleton;
import com.aidan.secondinventoryworkplatform.Model.SelecetableSingleton.SummonsNumberSingleton;
import com.aidan.secondinventoryworkplatform.Model.SelecetableSingleton.SummonsTitleSingleton;

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
        loadFirstPage();
        new Thread(new Runnable() {
            @Override
            public void run() {
                view.showProgress("讀取本地資料");
                ItemSingleton.getInstance().loadFromDB();
                view.updateProgress(10);
                DepartmentSingleton.getInstance().loadFromDB();
                view.updateProgress(20);
                AgentSingleton.getInstance().loadFromDB();
                view.updateProgress(30);
                LocationSingleton.getInstance().loadFromDB();
                view.updateProgress(40);
                ApprovalNumberSingleton.getInstance().loadFromDB();
                view.updateProgress(50);
                ChangeTargetSingleton.getInstance().loadFromDB();
                view.updateProgress(60);
                DepositPlaceSingleton.getInstance().loadFromDB();
                view.updateProgress(70);
                ImpairmentReasonSingleton.getInstance().loadFromDB();
                view.updateProgress(80);
                SummonsTitleSingleton.getInstance().loadFromDB();
                view.updateProgress(90);
                SummonsNumberSingleton.getInstance().loadFromDB();
                view.updateProgress(100);
                view.hideProgress();
            }
        }).start();
    }



    private void loadFirstPage(){
        if(ItemSingleton.getInstance().getItemList().size() > 0){
            view.loadItemListFragment();
        }else{
            view.loadFileFragment();
        }
    }
}
