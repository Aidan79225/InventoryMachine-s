package com.aidan.secondinventoryworkplatform.ChangeDetailPage;

import com.aidan.secondinventoryworkplatform.Constants;
import com.aidan.secondinventoryworkplatform.Dialog.SearchItemAdapter;
import com.aidan.secondinventoryworkplatform.Dialog.SearchableItem;
import com.aidan.secondinventoryworkplatform.Entity.Item;
import com.aidan.secondinventoryworkplatform.Entity.SelectableItem.ApprovalNumber;
import com.aidan.secondinventoryworkplatform.Entity.SelectableItem.ChangeTarget;
import com.aidan.secondinventoryworkplatform.Entity.SelectableItem.DepositPlace;
import com.aidan.secondinventoryworkplatform.Entity.SelectableItem.ImpairmentReason;
import com.aidan.secondinventoryworkplatform.Entity.SelectableItem.SummonsNumber;
import com.aidan.secondinventoryworkplatform.Entity.SelectableItem.SummonsTitle;
import com.aidan.secondinventoryworkplatform.Model.SelecetableSingleton.AgentSingleton;
import com.aidan.secondinventoryworkplatform.Model.SelecetableSingleton.ApprovalNumberSingleton;
import com.aidan.secondinventoryworkplatform.Model.SelecetableSingleton.ChangeTargetSingleton;
import com.aidan.secondinventoryworkplatform.Model.SelecetableSingleton.DepartmentSingleton;
import com.aidan.secondinventoryworkplatform.Model.SelecetableSingleton.DepositPlaceSingleton;
import com.aidan.secondinventoryworkplatform.Model.SelecetableSingleton.ImpairmentReasonSingleton;
import com.aidan.secondinventoryworkplatform.Model.SelecetableSingleton.LocationSingleton;
import com.aidan.secondinventoryworkplatform.Model.SelecetableSingleton.SummonsNumberSingleton;
import com.aidan.secondinventoryworkplatform.Model.SelecetableSingleton.SummonsTitleSingleton;
import com.aidan.secondinventoryworkplatform.Utils.LocalCacheHelper;

import java.util.Calendar;

/**
 * Created by Aidan on 2018/4/18.
 */

public class ChangeDetailPresenter implements ChangeDetailContract.presenter{
    private ChangeDetailContract.view view;
    private ChangeDetailModel model;


    public ChangeDetailPresenter(ChangeDetailContract.view view, Item item, ChangeTarget changeTarget){
        this.model = new ChangeDetailModel(item);
        this.model.setChangeTarget(changeTarget);
        this.view = view;
    }

    @Override
    public void start() {
        view.findView();
        view.reloadInfo(model.getItem());
        view.setViewClick();
        view.showScrappedViews(model.getChangeTarget(),model.getItem());
        setChangeType();
    }

    private void setChangeType(){
        if(model.getChangeTarget().getType() == ChangeTarget.TYPE_SCRAPPED){
            view.showScrappedViews(model.getChangeTarget(),model.getItem());
        }else{
            view.showMoveViews(model.getChangeTarget(),model.getItem());
        }
    }

    @Override
    public void changeTargetClick() {
        view.showSetDialog(new SearchItemAdapter.OnClickListener() {
            @Override
            public void onClick(SearchableItem i) {
                model.setChangeTarget((ChangeTarget)i);
                if(model.getChangeTarget().getType() == ChangeTarget.TYPE_SCRAPPED){
                    view.showScrappedViews(model.getChangeTarget(),model.getItem());
                }else{
                    view.showMoveViews(model.getChangeTarget(),model.getItem());
                }
            }
        },"異動項目", ChangeTargetSingleton.getInstance().getDataList());
    }

    @Override
    public void confirmButtonClick() {
        model.getItem().setPA3MONO(view.getChangeOrderId());
        model.getItem().setPA3MOC8(view.getChangeId());
        if(model.getChangeTarget().getType() == ChangeTarget.TYPE_MOVE){
            model.getItem().setPA3OUTN(view.getMoveDepartment());
            model.getItem().setPA3OUN(view.getNewAgent());
            model.getItem().setPA3LOCN(view.getMoveLocation());
            model.getItem().setPA3MOD(transDateString());
            model.getItem().setPA3TI("");
            model.getItem().setPA3MB("移動");
            model.getItem().setConfirm(true);
        }else if(model.getChangeTarget().getType() == ChangeTarget.TYPE_SCRAPPED){
            model.getItem().setPA3VWW(view.getPA3VWW());
            model.getItem().setPA3VN(view.getPA3VN());
            model.getItem().setPA3DR(view.getPA3DR());
            model.getItem().setPA8PD(view.getPA8PD());
            model.getItem().setPA8A(view.getPA8A());
            model.getItem().setPA3DED(transDateString());
            model.getItem().setPA3TI("報廢");
            model.getItem().setPA3MB("");
            model.getItem().setConfirm(true);
            savePA3VWW();
            savePA3VN();
            savePA3DR();
            savePA8PD();
            savePA8A();
        }

    }

    private String transDateString(){
        Calendar c = view.getDate();
        int y = c.get(Calendar.YEAR);
        int m = c.get(Calendar.MONTH);
        int d = c.get(Calendar.DAY_OF_MONTH);
        return y +"/"+ (m+1) + "/" + d ;
    }

    private void savePA3VWW(){
        String name = view.getPA3VWW();
        for(SummonsTitle data : SummonsTitleSingleton.getInstance().getDataList()){
            if(name.equals(data.getName()))return;
        }
        SummonsTitleSingleton.getInstance().getDataList().add(new SummonsTitle(name));
        SummonsTitleSingleton.getInstance().saveToDB();
    }
    private void savePA3VN(){
        String name = view.getPA3VN();
        for(SummonsNumber data : SummonsNumberSingleton.getInstance().getDataList()){
            if(name.equals(data.getName()))return;
        }
        SummonsNumberSingleton.getInstance().getDataList().add(new SummonsNumber(name));
        SummonsNumberSingleton.getInstance().saveToDB();
    }
    private void savePA3DR(){
        String name = view.getPA3DR();
        for(ImpairmentReason data : ImpairmentReasonSingleton.getInstance().getDataList()){
            if(name.equals(data.getName()))return;
        }
        ImpairmentReasonSingleton.getInstance().getDataList().add(new ImpairmentReason(name));
        ImpairmentReasonSingleton.getInstance().saveToDB();
    }
    private void savePA8PD(){
        String name = view.getPA8PD();
        for(DepositPlace data : DepositPlaceSingleton.getInstance().getDataList()){
            if(name.equals(data.getName()))return;
        }
        DepositPlaceSingleton.getInstance().getDataList().add(new DepositPlace(name));
        DepositPlaceSingleton.getInstance().saveToDB();
    }
    private void savePA8A(){
        String name = view.getPA8A();
        for(ApprovalNumber data : ApprovalNumberSingleton.getInstance().getDataList()){
            if(name.equals(data.getName()))return;
        }
        ApprovalNumberSingleton.getInstance().getDataList().add(new ApprovalNumber(name));
        ApprovalNumberSingleton.getInstance().saveToDB();
    }


    @Override
    public void moveDepartmentClick() {
        view.showSetDialog(new SearchItemAdapter.OnClickListener() {
            @Override
            public void onClick(SearchableItem i) {
                view.setMoveDepartment(i.getName());
            }
        },"移入單位", DepartmentSingleton.getInstance().getDataList());
    }

    @Override
    public void newAgentClick() {
        view.showSetDialog(new SearchItemAdapter.OnClickListener() {
            @Override
            public void onClick(SearchableItem i) {
                view.setNewAgent(i.getName());
            }
        },"新保管人", AgentSingleton.getInstance().getDataList());
    }

    @Override
    public void moveLocationClick() {
        view.showSetDialog(new SearchItemAdapter.OnClickListener() {
            @Override
            public void onClick(SearchableItem i) {
                view.setMoveLocation(i.getName());
            }
        },"移入地點", LocationSingleton.getInstance().getDataList());
    }

    @Override
    public void PA3VWWClick() {
        view.showSetDialog(new SearchItemAdapter.OnClickListener() {
            @Override
            public void onClick(SearchableItem i) {
                view.setPA3VWW(i.getName());
            }
        },"傳票用字", SummonsTitleSingleton.getInstance().getDataList());
    }

    @Override
    public void PA3VNClick() {
        view.showSetDialog(new SearchItemAdapter.OnClickListener() {
            @Override
            public void onClick(SearchableItem i) {
                view.setPA3VN(i.getName());
            }
        },"傳票號碼", SummonsNumberSingleton.getInstance().getDataList());
    }

    @Override
    public void PA3DRClick() {
        view.showSetDialog(new SearchItemAdapter.OnClickListener() {
            @Override
            public void onClick(SearchableItem i) {
                view.setPA3DR(i.getName());
            }
        },"減損原因", ImpairmentReasonSingleton.getInstance().getDataList());
    }

    @Override
    public void PA8PDClick() {
        view.showSetDialog(new SearchItemAdapter.OnClickListener() {
            @Override
            public void onClick(SearchableItem i) {
                view.setPA8PD(i.getName());
            }
        },"繳存地點", DepositPlaceSingleton.getInstance().getDataList());
    }

    @Override
    public void PA8AClick() {
        view.showSetDialog(new SearchItemAdapter.OnClickListener() {
            @Override
            public void onClick(SearchableItem i) {
                view.setPA8A(i.getName());
            }
        },"核准文號", ApprovalNumberSingleton.getInstance().getDataList());
    }
}
