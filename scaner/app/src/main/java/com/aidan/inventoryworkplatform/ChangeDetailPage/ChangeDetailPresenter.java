package com.aidan.inventoryworkplatform.ChangeDetailPage;

import android.content.DialogInterface;

import com.aidan.inventoryworkplatform.Dialog.SearchItemAdapter;
import com.aidan.inventoryworkplatform.Dialog.SearchableItem;
import com.aidan.inventoryworkplatform.Entity.Item;
import com.aidan.inventoryworkplatform.Entity.SelectableItem.ChangeTarget;
import com.aidan.inventoryworkplatform.Model.SelecetableSingleton.ChangeTargetSingleton;

/**
 * Created by Aidan on 2018/4/18.
 */

public class ChangeDetailPresenter implements ChangeDetailContract.presenter{
    private ChangeDetailContract.view view;
    private ChangeDetailModel model;


    public ChangeDetailPresenter(ChangeDetailContract.view view, Item item){
        this.model = new ChangeDetailModel(item);
        this.view = view;
    }

    @Override
    public void start() {
        view.findView();
        view.reloadInfo(model.getItem());
        view.setViewClick();
    }

    @Override
    public void changeTargetClick() {
        view.showSetDialog(new SearchItemAdapter.OnClickListener() {
            @Override
            public void onClick(SearchableItem i) {
                model.setChangeTarget((ChangeTarget)i);
                if(model.getChangeTarget().getType() == ChangeTarget.TYPE_SCRAPPED){
                    view.showScrappedViews();
                }else{
                    view.showMoveViews();
                }
            }
        },"異動項目", ChangeTargetSingleton.getInstance().getDataList());
    }

    @Override
    public void confirmButtonClick() {

    }
}
