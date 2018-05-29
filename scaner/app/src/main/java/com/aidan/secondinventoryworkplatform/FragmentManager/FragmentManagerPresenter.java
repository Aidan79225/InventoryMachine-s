package com.aidan.secondinventoryworkplatform.FragmentManager;

import com.aidan.secondinventoryworkplatform.Model.SelecetableSingleton.AgentSingleton;
import com.aidan.secondinventoryworkplatform.Model.SelecetableSingleton.ApprovalNumberSingleton;
import com.aidan.secondinventoryworkplatform.Model.SelecetableSingleton.ChangeItemSingleton;
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
                DepartmentSingleton.getInstance().loadFromDB();
                AgentSingleton.getInstance().loadFromDB();
                LocationSingleton.getInstance().loadFromDB();
                ApprovalNumberSingleton.getInstance().loadFromDB();
                ChangeItemSingleton.getInstance().loadFromDB();
                DepositPlaceSingleton.getInstance().loadFromDB();
                ImpairmentReasonSingleton.getInstance().loadFromDB();
                SummonsTitleSingleton.getInstance().loadFromDB();
                SummonsNumberSingleton.getInstance().loadFromDB();
                ChangeTargetSingleton.getInstance().loadFromDB();
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
