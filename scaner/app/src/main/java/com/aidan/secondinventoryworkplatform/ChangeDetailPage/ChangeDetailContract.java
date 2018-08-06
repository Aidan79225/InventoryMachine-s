package com.aidan.secondinventoryworkplatform.ChangeDetailPage;

import android.content.DialogInterface;

import com.aidan.secondinventoryworkplatform.Dialog.SearchItemAdapter;
import com.aidan.secondinventoryworkplatform.Entity.Item;
import com.aidan.secondinventoryworkplatform.Entity.SelectableItem.ChangeTarget;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Aidan on 2018/4/18.
 */

public interface ChangeDetailContract {
    interface view{
        void findView();
        void reloadInfo(Item item);
        void setViewClick();
        void showSetDialog(DialogInterface.OnClickListener clickListener, String title, final String[] temp);
        void showSetDialog(SearchItemAdapter.OnClickListener clickListener, String title, List dataList);
        void showScrappedViews(ChangeTarget data, Item item);
        void showMoveViews(ChangeTarget data, Item item);
        void setMoveDepartment(String name);
        void setNewAgent(String name);
        void setMoveLocation(String name);
        String getMoveDepartment();
        String getNewAgent();
        String getMoveLocation();
        String getChangeTargetName();
        void setPA3VWW(String name);
        void setPA3VN(String name);
        void setPA3DR(String name);
        void setPA8PD(String name);
        void setPA8A(String name);
        String getPA3VWW();
        String getPA3VN();
        String getPA3DR();
        String getPA8PD();
        String getPA8A();
        Calendar getDate();
        String getChangeOrderId();
        String getChangeId();
        String getNumber();


    }
    interface presenter{
        void start();
        void changeTargetClick();
        void confirmButtonClick();
        void moveDepartmentClick();
        void newAgentClick();
        void moveLocationClick();
        void PA3VWWClick();
        void PA3VNClick();
        void PA3DRClick();
        void PA8PDClick();
        void PA8AClick();
    }

}
